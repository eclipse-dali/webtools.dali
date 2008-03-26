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
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
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
public class VirtualXmlOneToMany extends AbstractJpaEObject implements XmlOneToMany
{
	JavaOneToManyMapping javaOneToManyMapping;

	protected boolean metadataComplete;
	
	protected final VirtualXmlJoinTable virtualJoinTable;
	
	protected final VirtualCascadeType virtualCascadeType;
	
	protected final MapKey mapKey;
	
	//TODO joinColumns not yet supported in the context model
//	protected EList<JoinColumn> virtualJoinColumns;

	public VirtualXmlOneToMany(OrmPersistentAttribute ormPersistentAttribute, JavaOneToManyMapping javaOneToManyMapping, boolean metadataComplete) {
		super();
		this.javaOneToManyMapping = javaOneToManyMapping;
		this.metadataComplete = metadataComplete;
//		this.initializeJoinColumns(javaOneToManyMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaOneToManyMapping.getCascade(), this.metadataComplete);
		this.virtualJoinTable = new VirtualXmlJoinTable(ormPersistentAttribute, javaOneToManyMapping.getJoinTable(), metadataComplete);
		this.mapKey = new VirtualMapKey(javaOneToManyMapping, metadataComplete);
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
		return this.javaOneToManyMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaOneToManyMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaOneToManyMapping.getFetch());
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
		if (this.metadataComplete) {
			return this.javaOneToManyMapping.getDefaultTargetEntity();
		}
		return this.javaOneToManyMapping.getTargetEntity();
	}

	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getMappedBy() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaOneToManyMapping.getMappedBy();
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
		return this.javaOneToManyMapping.getOrderBy();
	}
	
	public void setOrderBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public void update(JavaOneToManyMapping javaOneToManyMapping) {
		this.javaOneToManyMapping = javaOneToManyMapping;
		this.virtualCascadeType.update(javaOneToManyMapping.getCascade());
		this.virtualJoinTable.update(javaOneToManyMapping.getJoinTable());
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
	
	public TextRange getNameTextRange() {
		return null;
	}
	
	public TextRange getMappedByTextRange() {
		return null;
	}
}
