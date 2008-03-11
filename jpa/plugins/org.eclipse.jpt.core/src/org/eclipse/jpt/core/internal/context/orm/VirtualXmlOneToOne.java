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
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualOneToOne is an implementation of OneToOne used when there is 
 * no tag in the orm.xml and an underlying javaOneToOneMapping exists.
 */
public class VirtualXmlOneToOne extends AbstractJpaEObject implements XmlOneToOne
{
	JavaOneToOneMapping javaOneToOneMapping;

	protected boolean metadataComplete;
	
//	protected VirtualJoinTable virtualJoinTable;
		
	protected final VirtualCascadeType virtualCascadeType;

	public VirtualXmlOneToOne(JavaOneToOneMapping javaOneToOneMapping, boolean metadataComplete) {
		super();
		this.javaOneToOneMapping = javaOneToOneMapping;
		this.metadataComplete = metadataComplete;
		this.virtualCascadeType = new VirtualCascadeType(javaOneToOneMapping.getCascade(), this.metadataComplete);
	}

	public String getName() {
		return this.javaOneToOneMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}
	
	public FetchType getFetch() {
		if (this.metadataComplete) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaOneToOneMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaOneToOneMapping.getFetch());
	}

	public void setFetch(FetchType newFetch) {
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

	public EList<XmlJoinColumn> getJoinColumns() {
		EList<XmlJoinColumn> joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_JOIN_TABLE__JOIN_COLUMNS);
		//TODO here i'm using joinColumns() while VirtualXmlJoinTable uses specifiedJoinColumns()???
		for (JavaJoinColumn joinColumn : CollectionTools.iterable(this.javaOneToOneMapping.joinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.metadataComplete);
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
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

	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		// TODO we don't yet support primary key join columns in the context model
		return null;
	}
	
	public void update(JavaOneToOneMapping javaOneToOneMapping) {
		this.javaOneToOneMapping = javaOneToOneMapping;
		this.virtualCascadeType.update(javaOneToOneMapping.getCascade());
	}
	
	public TextRange nameTextRange() {
		return null;
	}
}
