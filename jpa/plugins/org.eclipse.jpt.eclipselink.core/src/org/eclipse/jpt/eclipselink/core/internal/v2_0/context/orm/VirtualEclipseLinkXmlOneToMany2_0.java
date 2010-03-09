/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlOneToMany2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkOneToManyMapping;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.VirtualEclipseLinkXmlOneToMany1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualEclipseLinkXmlOneToMany2_0 is an implementation of XmlOneToMany used when there is 
 * no tag in the orm.xml and an underlying javaOneToMany exists.
 */
public class VirtualEclipseLinkXmlOneToMany2_0 extends XmlOneToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaEclipseLinkOneToManyMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlOneToMany1_1 eclipseLinkVirtualXmlOneToMany;
	
	protected final VirtualXmlOneToMany2_0 virtualXmlOneToMany;
		
	public VirtualEclipseLinkXmlOneToMany2_0(
			OrmTypeMapping ormTypeMapping, JavaEclipseLinkOneToManyMapping javaOneToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToManyMapping;
		this.eclipseLinkVirtualXmlOneToMany = new VirtualEclipseLinkXmlOneToMany1_1(ormTypeMapping, javaOneToManyMapping);
		this.virtualXmlOneToMany = new VirtualXmlOneToMany2_0(ormTypeMapping, javaOneToManyMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
		
	@Override
	public String getMappingKey() {
		return this.virtualXmlOneToMany.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlOneToMany.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlOneToMany.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlOneToMany.getNameTextRange();
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlOneToMany.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlOneToMany.setFetch(newFetch);
	}

	@Override
	public CascadeType getCascade() {
		return this.virtualXmlOneToMany.getCascade();
	}
	
	@Override
	public void setCascade(CascadeType value) {
		this.virtualXmlOneToMany.setCascade(value);
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		return this.virtualXmlOneToMany.getJoinTable();
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		this.virtualXmlOneToMany.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlOneToMany.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlOneToMany.setTargetEntity(value);
	}
	
	@Override
	public String getMappedBy() {
		return this.virtualXmlOneToMany.getMappedBy();
	}
	
	@Override
	public void setMappedBy(String value) {
		this.virtualXmlOneToMany.setMappedBy(value);
	}

	
	@Override
	public MapKey getMapKey() {
		return this.virtualXmlOneToMany.getMapKey();
	}
	
	@Override
	public void setMapKey(MapKey value) {
		this.virtualXmlOneToMany.setMapKey(value);
	}
	
	@Override
	public XmlClassReference getMapKeyClass() {
		return this.virtualXmlOneToMany.getMapKeyClass();
	}
	
	@Override
	public void setMapKeyClass(XmlClassReference value) {
		this.virtualXmlOneToMany.setMapKeyClass(value);
	}

	@Override
	public XmlColumn getMapKeyColumn() {
		return this.virtualXmlOneToMany.getMapKeyColumn();
	}

	@Override
	public void setMapKeyColumn(XmlColumn newMapKeyColumn) {
		this.virtualXmlOneToMany.setMapKeyColumn(newMapKeyColumn);
	}

	@Override
	public String getOrderBy() {
		return this.virtualXmlOneToMany.getOrderBy();
	}
	
	@Override
	public void setOrderBy(String value) {
		this.virtualXmlOneToMany.setOrderBy(value);
	}
	
	@Override
	public XmlOrderColumn getOrderColumn() {
		return this.virtualXmlOneToMany.getOrderColumn();
	}
	
	@Override
	public TextRange getMappedByTextRange() {
		return this.virtualXmlOneToMany.getMappedByTextRange();
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlOneToMany.getTargetEntityTextRange();
	}	
	
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.eclipseLinkVirtualXmlOneToMany.getJoinColumns();
	}

	@Override
	public XmlJoinFetchType getJoinFetch() {
		return this.eclipseLinkVirtualXmlOneToMany.getJoinFetch();
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		this.eclipseLinkVirtualXmlOneToMany.setJoinFetch(value);
	}
	
	@Override
	public boolean isPrivateOwned() {
		return this.eclipseLinkVirtualXmlOneToMany.isPrivateOwned();
	}
	
	@Override
	public void setPrivateOwned(boolean value) {
		this.eclipseLinkVirtualXmlOneToMany.setPrivateOwned(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.eclipseLinkVirtualXmlOneToMany.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.eclipseLinkVirtualXmlOneToMany.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.eclipseLinkVirtualXmlOneToMany.getProperties();
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return this.eclipseLinkVirtualXmlOneToMany.getJoinFetchTextRange();
	}
	
	@Override
	public TextRange getPrivateOwnedTextRange() {
		return this.eclipseLinkVirtualXmlOneToMany.getPrivateOwnedTextRange();
	}
	
	@Override
	public AccessType getAccess() {
		return this.eclipseLinkVirtualXmlOneToMany.getAccess();
	}
	
	@Override
	public void setAccess(AccessType value) {
		this.eclipseLinkVirtualXmlOneToMany.setAccess(value);
	}

	@Override
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides() {
		return this.virtualXmlOneToMany.getMapKeyAttributeOverrides();
	}
}
