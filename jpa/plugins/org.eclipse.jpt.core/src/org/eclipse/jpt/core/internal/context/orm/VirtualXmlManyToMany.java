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
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;

/**
 * VirtualManyToMany is an implementation of ManyToMany used when there is 
 * no tag in the orm.xml and an underlying javaManyToManyMapping exists.
 */
public class VirtualXmlManyToMany extends AbstractJpaEObject implements XmlManyToMany
{
	JavaManyToManyMapping javaManyToManyMapping;

	protected boolean metadataComplete;
	
	protected final VirtualXmlJoinTable virtualJoinTable;
	
	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	//TODO joinColumns not yet supported in the context model
//	protected EList<JoinColumn> virtualJoinColumns;

	public VirtualXmlManyToMany(JavaManyToManyMapping javaManyToManyMapping, boolean metadataComplete) {
		super();
		this.javaManyToManyMapping = javaManyToManyMapping;
		this.metadataComplete = metadataComplete;
//		this.initializeJoinColumns(javaOneToManyMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaManyToManyMapping.getCascade(), this.metadataComplete);
		this.virtualJoinTable = new VirtualXmlJoinTable(javaManyToManyMapping.getJoinTable(), metadataComplete);
		this.mapKey = new VirtualMapKey(javaManyToManyMapping, metadataComplete);
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
		return this.javaManyToManyMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaManyToManyMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaManyToManyMapping.getFetch());
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
		//TODO we don't yet support JoinTable in the context model for many-to-one mappings
		return null;//this.virtualJoinTable;
	}

	public void setJoinTable(XmlJoinTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getTargetEntity() {
		if (this.metadataComplete) {
			return this.javaManyToManyMapping.getDefaultTargetEntity();
		}
		return this.javaManyToManyMapping.getTargetEntity();
	}

	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getMappedBy() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaManyToManyMapping.getMappedBy();
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
		if (this.metadataComplete) {
			return null;
		}
		return this.javaManyToManyMapping.getOrderBy();
	}
	
	public void setOrderBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(JavaManyToManyMapping javaManyToManyMapping) {
		this.javaManyToManyMapping = javaManyToManyMapping;
		this.virtualCascadeType.update(javaManyToManyMapping.getCascade());
		this.virtualJoinTable.update(javaManyToManyMapping.getJoinTable());
//		this.updateJoinColumns(javaOneToManyMapping);
	}
	
//	protected void updateJoinColumns(IJavaOneToManyMapping javaOneToManyMapping) {
//		ListIterator<IJavaJoinColumn> javaJoinColumns;
//		ListIterator<JoinColumn> virtualJoinColumns = this.virtualJoinColumns.listIterator();
//		if (this.metadataComplete) {
//			javaJoinColumns = this.javaOneToManyMapping.defaultJoinColumns();
//		}
//		else {
//			javaJoinColumns = this.javaOneToManyMapping.joinColumns();			
//		}
//		
//		while (javaJoinColumns.hasNext()) {
//			IJavaJoinColumn javaJoinColumn = javaJoinColumns.next();
//			if (virtualJoinColumns.hasNext()) {
//				VirtualJoinColumn virtualJoinColumn = (VirtualJoinColumn) virtualJoinColumns.next();
//				virtualJoinColumn.update(javaJoinColumn);
//			}
//			else {
//				this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumn, this.metadataComplete));
//			}
//		}
//		
//		while(virtualJoinColumns.hasNext()) {
//			this.virtualJoinColumns.remove(virtualJoinColumns.next());
//		}
//	}
	
	public TextRange nameTextRange() {
		return null;
	}

}
