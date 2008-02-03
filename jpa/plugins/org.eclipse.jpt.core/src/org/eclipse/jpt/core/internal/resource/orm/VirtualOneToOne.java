/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.context.java.IJavaJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaOneToOneMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;
import org.eclipse.jpt.core.internal.resource.orm.FetchType;

/**
 * VirtualOneToOne is an implementation of OneToOne used when there is 
 * no tag in the orm.xml and an underlying javaOneToOneMapping exists.
 */
public class VirtualOneToOne extends JpaEObject implements OneToOne
{
	IJavaOneToOneMapping javaOneToOneMapping;

	protected boolean metadataComplete;
	
//	protected VirtualJoinTable virtualJoinTable;
		
	protected final VirtualCascadeType virtualCascadeType;

	protected EList<JoinColumn> virtualJoinColumns;
	
//	protected EList<PrimaryKeyJoinColumn> virtualPrimaryKeyJoinColumns;

	public VirtualOneToOne(IJavaOneToOneMapping javaOneToOneMapping, boolean metadataComplete) {
		super();
		this.javaOneToOneMapping = javaOneToOneMapping;
		this.metadataComplete = metadataComplete;
		this.initializeJoinColumns(javaOneToOneMapping);
//		this.initializePrimaryKeyJoinColumns(javaOneToOneMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaOneToOneMapping.getCascade(), this.metadataComplete);
	}
	
	protected void initializeJoinColumns(IJavaOneToOneMapping javaOneToOneMapping) {
		this.virtualJoinColumns = new BasicEList<JoinColumn>();
		ListIterator<IJavaJoinColumn> javaJoinColumns;
		if (this.metadataComplete) {
			javaJoinColumns = this.javaOneToOneMapping.defaultJoinColumns();
		}
		else {
			javaJoinColumns = this.javaOneToOneMapping.joinColumns();			
		}
		
		while (javaJoinColumns.hasNext()) {
			this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumns.next(), this.metadataComplete));
		}
	}
	
//	protected void initializePrimaryKeyJoinColumns(IJavaOneToOneMapping javaOneToOneMapping) {
//		this.virtualPrimaryKeyJoinColumns = new BasicEList<PrimaryKeyJoinColumn>();
//		ListIterator<IJavaPrimaryKeyJoinColumn> javaJoinColumns;
//		if (this.metadataComplete) {
//			javaJoinColumns = this.javaOneToOneMapping.defaultJoinColumns();
//		}
//		else {
//			javaJoinColumns = this.javaOneToOneMapping.joinColumns();			
//		}
//		
//		while (javaJoinColumns.hasNext()) {
//			this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumns.next(), this.metadataComplete));
//		}
//	}
	
	public String getName() {
		return this.javaOneToOneMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.internal.context.base.FetchType.toOrmResourceModel(this.javaOneToOneMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.internal.context.base.FetchType.toOrmResourceModel(this.javaOneToOneMapping.getFetch());
	}

	public void setFetch(org.eclipse.jpt.core.internal.resource.orm.FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getOptional() {
		if (this.metadataComplete) {
			return this.javaOneToOneMapping.getDefaultOptional();
		}
		return this.javaOneToOneMapping.getOptional();
	}

	public void setOptional(Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EList<JoinColumn> getJoinColumns() {
		return this.virtualJoinColumns;
	}

	public CascadeType getCascade() {
		return this.virtualCascadeType;
	}
	
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public JoinTable getJoinTable() {
		//TODO we don't yet support JoinTable in the context model for many-to-one mappings
		return null;//this.virtualJoinTable;
	}

	public void setJoinTable(JoinTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public String getTargetEntity() {
		if (this.metadataComplete) {
			return this.javaOneToOneMapping.getDefaultTargetEntity();
		}
		return this.javaOneToOneMapping.getTargetEntity();
	}

	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public String getMappedBy() {
		if (this.metadataComplete) {
			return null;
		}
		return this.javaOneToOneMapping.getMappedBy();
	}

	public void setMappedBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EList<PrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		// TODO we don't yet support primary key join columns in the context model
		return null;
	}
	
	public void update(IJavaOneToOneMapping javaOneToOneMapping) {
		this.javaOneToOneMapping = javaOneToOneMapping;
		this.virtualCascadeType.update(javaOneToOneMapping.getCascade());
		this.updateJoinColumns(javaOneToOneMapping);
	}
	
	protected void updateJoinColumns(IJavaOneToOneMapping javaOneToOneMapping) {
		ListIterator<IJavaJoinColumn> javaJoinColumns;
		ListIterator<JoinColumn> virtualJoinColumns = this.virtualJoinColumns.listIterator();
		if (this.metadataComplete) {
			javaJoinColumns = this.javaOneToOneMapping.defaultJoinColumns();
		}
		else {
			javaJoinColumns = this.javaOneToOneMapping.joinColumns();			
		}
		
		while (javaJoinColumns.hasNext()) {
			IJavaJoinColumn javaJoinColumn = javaJoinColumns.next();
			if (virtualJoinColumns.hasNext()) {
				VirtualJoinColumn virtualJoinColumn = (VirtualJoinColumn) virtualJoinColumns.next();
				virtualJoinColumn.update(javaJoinColumn);
			}
			else {
				this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumn, this.metadataComplete));
			}
		}
		
		while(virtualJoinColumns.hasNext()) {
			this.virtualJoinColumns.remove(virtualJoinColumns.next());
		}
	}

}
