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

import java.util.ListIterator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.utility.internal.iterators.EmptyListIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementListIterator;

/**
 * VirtualManyToOne is an implementation of ManyToOne used when there is 
 * no tag in the orm.xml and an underlying javaManyToOneMapping exists.
 */
public class VirtualManyToOne extends AbstractJpaEObject implements XmlManyToOne
{
	JavaManyToOneMapping javaManyToOneMapping;

	protected boolean metadataComplete;
	
//	protected VirtualJoinTable virtualJoinTable;
	
	protected final VirtualCascadeType virtualCascadeType;
	
	protected EList<XmlJoinColumn> virtualJoinColumns;

	public VirtualManyToOne(JavaManyToOneMapping javaManyToOneMapping, boolean metadataComplete) {
		super();
		this.javaManyToOneMapping = javaManyToOneMapping;
		this.metadataComplete = metadataComplete;
		this.initializeJoinColumns(javaManyToOneMapping);
		this.virtualCascadeType = new VirtualCascadeType(javaManyToOneMapping.getCascade(), this.metadataComplete);
	}
	
	protected void initializeJoinColumns(JavaManyToOneMapping javaManyToOneMapping) {
		this.virtualJoinColumns = new BasicEList<XmlJoinColumn>();
		ListIterator<JavaJoinColumn> javaJoinColumns;
		if (this.metadataComplete) {
			//TODO still need a default join column in xml
			if (javaManyToOneMapping.getDefaultJoinColumn() == null) {
				javaJoinColumns = EmptyListIterator.instance();
			}
			else {
				javaJoinColumns = new SingleElementListIterator<JavaJoinColumn>(javaManyToOneMapping.getDefaultJoinColumn());
			}
		}
		else {
			javaJoinColumns = this.javaManyToOneMapping.joinColumns();			
		}
		
		while (javaJoinColumns.hasNext()) {
			this.virtualJoinColumns.add(new VirtualJoinColumn(javaJoinColumns.next(), this.metadataComplete));
		}
	}
	
	public String getName() {
		return this.javaManyToOneMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaManyToOneMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaManyToOneMapping.getFetch());
	}

	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public Boolean getOptional() {
		if (this.metadataComplete) {
			return this.javaManyToOneMapping.getDefaultOptional();
		}
		return this.javaManyToOneMapping.getOptional();
	}

	public void setOptional(Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualJoinColumns;
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
			return this.javaManyToOneMapping.getDefaultTargetEntity();
		}
		return this.javaManyToOneMapping.getTargetEntity();
	}

	public void setTargetEntity(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public void update(JavaManyToOneMapping javaManyToOneMapping) {
		this.javaManyToOneMapping = javaManyToOneMapping;
		this.virtualCascadeType.update(javaManyToOneMapping.getCascade());
		this.updateJoinColumns(javaManyToOneMapping);
	}
	
	protected void updateJoinColumns(JavaManyToOneMapping javaManyToOneMapping) {
		ListIterator<JavaJoinColumn> javaJoinColumns;
		ListIterator<XmlJoinColumn> virtualJoinColumns = this.virtualJoinColumns.listIterator();
		if (this.metadataComplete) {
			//TODO still need a default join column in xml
			if (javaManyToOneMapping.getDefaultJoinColumn() == null) {
				javaJoinColumns = EmptyListIterator.instance();
			}
			else {
				javaJoinColumns = new SingleElementListIterator<JavaJoinColumn>(javaManyToOneMapping.getDefaultJoinColumn());
			}
		}
		else {
			javaJoinColumns = this.javaManyToOneMapping.joinColumns();			
		}
		
		while (javaJoinColumns.hasNext()) {
			JavaJoinColumn javaJoinColumn = javaJoinColumns.next();
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
