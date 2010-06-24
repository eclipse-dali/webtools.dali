/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualOneToMany is an implementation of OneToMany used when there is 
 * no tag in the orm.xml and an underlying javaOneToManyMapping exists.
 */
public class VirtualXmlOneToMany
	extends XmlOneToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToManyMapping javaAttributeMapping;

	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	public VirtualXmlOneToMany(
			OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToManyMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaOneToManyMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaOneToManyMapping.getCascade());
		this.mapKey = new VirtualMapKey(javaOneToManyMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
		
	@Override
	public String getMappingKey() {
		return this.virtualXmlAttributeMapping.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlAttributeMapping.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlAttributeMapping.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlAttributeMapping.getNameTextRange();
	}
	
	@Override
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	@Override
	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return null;
	}

	@Override
	public CascadeType getCascade() {
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.virtualCascadeType;
	}
	
	@Override
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	

	@Override
	public Boolean getOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	@Override
	public void setOrphanRemoval(Boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		if (this.javaAttributeMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable() != null) {
			return 	new VirtualXmlJoinTable(
				this.ormTypeMapping, 
				this.javaAttributeMapping.getRelationshipReference().
					getJoinTableJoiningStrategy().getJoinTable());
		}
		return null;
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getTargetEntity() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getDefaultTargetEntity();
		}
		return this.javaAttributeMapping.getFullyQualifiedTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getMappedBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getRelationshipReference().
			getMappedByJoiningStrategy().getMappedByAttribute();
	}
	
	@Override
	public void setMappedBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	
	@Override
	public MapKey getMapKey() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.isNoMapKey()) {
			return null;
		}
		return this.mapKey;
	}
	
	@Override
	public void setMapKey(MapKey value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public String getOrderBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getOrderable().getSpecifiedOrderBy();
	}
	
	@Override
	public void setOrderBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public TextRange getMappedByTextRange() {
		return null;
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return null;
	}
}
