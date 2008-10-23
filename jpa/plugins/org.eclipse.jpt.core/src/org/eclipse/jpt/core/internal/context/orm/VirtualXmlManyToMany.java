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
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
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

	public VirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, OrmPersistentAttribute ormPersistentAttribute, JavaManyToManyMapping javaManyToManyMapping) {
		super(ormTypeMapping, javaManyToManyMapping);
//		this.initializeJoinColumns(javaOneToManyMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaManyToManyMapping.getCascade(), this.isOrmMetadataComplete());
		this.virtualJoinTable = new VirtualXmlJoinTable(ormPersistentAttribute, javaManyToManyMapping.getJoinTable(), isOrmMetadataComplete());
		this.mapKey = new VirtualMapKey(javaManyToManyMapping, this.isOrmMetadataComplete());
	}
	
//	protected void initializeJoinColumns(IJavaOneToManyMapping javaOneToManyMapping) {
//		this.virtualJoinColumns = new BasicEList<JoinColumn>();
//		ListIterator<IJavaJoinColumn> javaJoinColumns;
//		if (this.metadataComplete) {
//			javaJoinColumns = this.javaOneToManyMapping.defaultJoinColumns();
//		}
//		else {
//			javaJoinColumns = this.javaOneToManyMapping.joinColumns();			
//		}
//		
//		while (javaJoinColumns.hasNext()) {
//			this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumns.next(), this.metadataComplete));
//		}
//	}
	
	public String getName() {
		return this.javaAttributeMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EList<XmlJoinColumn> getJoinColumns() {
		return null;
	}

	public CascadeType getCascade() {
		return this.virtualCascadeType;
	}
	
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public XmlJoinTable getJoinTable() {
		return this.virtualJoinTable;
	}

	public void setJoinTable(XmlJoinTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getTargetEntity() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getDefaultTargetEntity();
		}
		return this.javaAttributeMapping.getTargetEntity();
	}

	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getMappedBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getMappedBy();
	}
	
	public void setMappedBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	
	public MapKey getMapKey() {
		return this.mapKey;
	}
	
	public void setMapKey(MapKey value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getOrderBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getOrderBy();
	}
	
	public void setOrderBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public TextRange getNameTextRange() {
		return null;
	}

	public TextRange getMappedByTextRange() {
		return null;
	}
}
