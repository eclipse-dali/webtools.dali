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
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualOneToOne is an implementation of OneToOne used when there is 
 * no tag in the orm.xml and an underlying javaOneToOneMapping exists.
 */
public class VirtualXmlOneToOne extends XmlOneToOne
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToOneMapping javaAttributeMapping;
	
	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;

	protected final VirtualCascadeType virtualCascadeType;
	
	
	public VirtualXmlOneToOne(
			OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToOneMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaOneToOneMapping);
		this.virtualCascadeType = 
			new VirtualCascadeType(javaOneToOneMapping.getCascade(), this.isOrmMetadataComplete());
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
	public Boolean getOptional() {
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(this.javaAttributeMapping.isDefaultOptional());
		}
		return Boolean.valueOf(this.javaAttributeMapping.isOptional());
	}

	@Override
	public void setOptional(Boolean newOptional) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		//TODO need to check metadataComplete here
		EList<XmlJoinColumn> joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ONE_TO_ONE__JOIN_COLUMNS);
		//TODO here i'm using joinColumns() while VirtualXmlJoinTable uses specifiedJoinColumns()???
		for (JavaJoinColumn joinColumn : 
				CollectionTools.iterable(
					this.javaAttributeMapping.getRelationshipReference().
						getJoinColumnJoiningStrategy().joinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, this.isOrmMetadataComplete());
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
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
	public Boolean getOrphanRemoval() {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}

	@Override
	public void setOrphanRemoval(Boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("operation not supported in JPA 1.0"); //$NON-NLS-1$
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		//TODO we don't yet support JoinTable in the context model for many-to-one mappings
		return null;//this.virtualJoinTable;
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
		return this.javaAttributeMapping.getRelationshipReference().
			getMappedByJoiningStrategy().getMappedByAttribute();
	}

	@Override
	public void setMappedBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		EList<XmlPrimaryKeyJoinColumn> joinColumns = new EObjectContainmentEList<XmlPrimaryKeyJoinColumn>(XmlPrimaryKeyJoinColumn.class, this, OrmPackage.XML_ONE_TO_ONE__PRIMARY_KEY_JOIN_COLUMNS);
		if (!this.isOrmMetadataComplete()) {
			for (JavaPrimaryKeyJoinColumn joinColumn : 
					CollectionTools.iterable(
						this.javaAttributeMapping.getRelationshipReference().
							getPrimaryKeyJoinColumnJoiningStrategy().primaryKeyJoinColumns())) {
				XmlPrimaryKeyJoinColumn xmlJoinColumn = new VirtualXmlPrimaryKeyJoinColumn(joinColumn/*, this.metadataComplete*/);
				joinColumns.add(xmlJoinColumn);
			}
		}
		return joinColumns;
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
