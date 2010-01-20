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

import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualManyToMany is an implementation of ManyToMany used when there is 
 * no tag in the orm.xml and an underlying javaManyToManyMapping exists.
 */
public class VirtualXmlManyToMany 
	extends XmlManyToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToManyMapping javaAttributeMapping;

	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	
	public VirtualXmlManyToMany(
			OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToManyMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaManyToManyMapping);
		this.virtualCascadeType = 
			new VirtualCascadeType(javaManyToManyMapping.getCascade(), this.isOrmMetadataComplete());
		this.mapKey = new VirtualMapKey(javaManyToManyMapping);
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
	public CascadeType getCascade() {
		return this.virtualCascadeType;
	}
	
	@Override
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		return this.javaAttributeMapping.getTargetEntity();
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
		return this.javaAttributeMapping.getRelationshipReference().getMappedByJoiningStrategy().getMappedByAttribute();
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
	public XmlOrderColumn getOrderColumn() {
		if (((Orderable2_0) this.javaAttributeMapping.getOrderable()).isOrderColumnOrdering()) {
			return 	new VirtualXmlOrderColumn(
				((Orderable2_0) this.javaAttributeMapping.getOrderable()).getOrderColumn(),
				this.ormTypeMapping.isMetadataComplete());
		}
		return null;
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
