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
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualManyToMany is an implementation of ManyToMany used when there is 
 * no tag in the orm.xml and an underlying javaManyToManyMapping exists.
 */
public class VirtualXmlManyToMany extends VirtualXmlAttributeMapping<JavaManyToManyMapping> implements XmlManyToMany
{
	protected final VirtualXmlJoinTable virtualJoinTable;
	
	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	//TODO joinColumns not yet supported in the context model
//	protected EList<JoinColumn> virtualJoinColumns;

	public VirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		super(ormTypeMapping, javaManyToManyMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaManyToManyMapping.getCascade(), this.isOrmMetadataComplete());
		this.virtualJoinTable = new VirtualXmlJoinTable(javaManyToManyMapping.getJoinTable(), isOrmMetadataComplete());
		this.mapKey = new VirtualMapKey(javaManyToManyMapping, this.isOrmMetadataComplete());
	}
	
	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(@SuppressWarnings("unused") String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		return this.virtualJoinTable;
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
		return this.javaAttributeMapping.getMappedBy();
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
	
	public TextRange getNameTextRange() {
		return null;
	}

	public TextRange getMappedByTextRange() {
		return null;
	}
}
