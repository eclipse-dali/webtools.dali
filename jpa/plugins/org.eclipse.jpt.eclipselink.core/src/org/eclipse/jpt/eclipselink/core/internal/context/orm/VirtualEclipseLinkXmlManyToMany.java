/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToMany;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkManyToManyMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlManyToMany extends XmlManyToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToManyMapping javaAttributeMapping;

	protected final VirtualXmlManyToMany virtualXmlManyToMany;
		
	public VirtualEclipseLinkXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToManyMapping;
		this.virtualXmlManyToMany = new VirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
	}

	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlManyToMany.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlManyToMany.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlManyToMany.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlManyToMany.getNameTextRange();
	}	
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlManyToMany.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlManyToMany.setFetch(newFetch);
	}

	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlManyToMany.getJoinColumns();
	}

	@Override
	public CascadeType getCascade() {
		return this.virtualXmlManyToMany.getCascade();
	}
	
	@Override
	public void setCascade(CascadeType value) {
		this.virtualXmlManyToMany.setCascade(value);
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		return this.virtualXmlManyToMany.getJoinTable();
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		this.virtualXmlManyToMany.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlManyToMany.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlManyToMany.setTargetEntity(value);
	}
	
	@Override
	public String getMappedBy() {
		return this.virtualXmlManyToMany.getMappedBy();
	}
	
	@Override
	public void setMappedBy(String value) {
		this.virtualXmlManyToMany.setMappedBy(value);
	}

	
	@Override
	public MapKey getMapKey() {
		return this.virtualXmlManyToMany.getMapKey();
	}
	
	@Override
	public void setMapKey(MapKey value) {
		this.virtualXmlManyToMany.setMapKey(value);
	}
	
	@Override
	public String getOrderBy() {
		return this.virtualXmlManyToMany.getOrderBy();
	}
	
	@Override
	public void setOrderBy(String value) {
		this.virtualXmlManyToMany.setOrderBy(value);
	}
	
	@Override
	public XmlOrderColumn getOrderColumn() {
		return this.virtualXmlManyToMany.getOrderColumn();
	}
	
	@Override
	public TextRange getMappedByTextRange() {
		return this.virtualXmlManyToMany.getMappedByTextRange();
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlManyToMany.getTargetEntityTextRange();
	}
	
	@Override
	public XmlJoinFetchType getJoinFetch() {
		if (isOrmMetadataComplete()) {
			return null; //don't return default value, it only applies for an empty @JoinFetch
		}
		return EclipseLinkJoinFetchType.toOrmResourceModel(((JavaEclipseLinkManyToManyMapping) this.javaAttributeMapping).getJoinFetch().getValue());
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return null;
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$		
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		// TODO get from java annotations
		return null;
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return null;
	}
}
