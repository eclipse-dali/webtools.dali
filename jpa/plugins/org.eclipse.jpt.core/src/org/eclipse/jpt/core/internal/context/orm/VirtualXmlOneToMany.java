/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
public class VirtualXmlOneToMany<T extends JavaOneToManyMapping>
	extends VirtualXmlAttributeMapping<T> 
	implements XmlOneToMany
{
	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	
	public VirtualXmlOneToMany(
			OrmTypeMapping ormTypeMapping, T javaOneToManyMapping) {
		super(ormTypeMapping, javaOneToManyMapping);
		this.virtualCascadeType = 
			new VirtualCascadeType(javaOneToManyMapping.getCascade(), this.isOrmMetadataComplete());
		this.mapKey = new VirtualMapKey(javaOneToManyMapping, this.isOrmMetadataComplete());
	}
	
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	public void setFetch(@SuppressWarnings("unused") FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	public EList<XmlJoinColumn> getJoinColumns() {
		return null;
	}

	public CascadeType getCascade() {
		return this.virtualCascadeType;
	}
	
	public void setCascade(@SuppressWarnings("unused") CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public XmlJoinTable getJoinTable() {
		if (this.javaAttributeMapping.getRelationshipReference().getJoinTableJoiningStrategy().getJoinTable() != null) {
			return 	new VirtualXmlJoinTable(
				ormTypeMapping, 
				this.javaAttributeMapping.getRelationshipReference().
					getJoinTableJoiningStrategy().getJoinTable());
		}
		else {
			return null;
		}
	}

	public void setJoinTable(@SuppressWarnings("unused") XmlJoinTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public String getTargetEntity() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getDefaultTargetEntity();
		}
		return this.javaAttributeMapping.getTargetEntity();
	}

	public void setTargetEntity(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public String getMappedBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getRelationshipReference().
			getMappedByJoiningStrategy().getMappedByAttribute();
	}
	
	public void setMappedBy(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	
	public MapKey getMapKey() {
		return this.mapKey;
	}
	
	public void setMapKey(@SuppressWarnings("unused") MapKey value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public String getOrderBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getOrderBy();
	}
	
	public void setOrderBy(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	public TextRange getMappedByTextRange() {
		return null;
	}
	
	public TextRange getTargetEntityTextRange() {
		return null;
	}
}
