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
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlManyToMany2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualEclipseLinkXmlManyToMany2_0 is an implementation of XmlManyToMany used when there is 
 * no tag in the orm.xml and an underlying java many to many exists.
 */
public class VirtualEclipseLinkXmlManyToMany2_0 extends XmlManyToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToManyMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlManyToMany eclipseLinkVirtualXmlManyToMany;
	
	protected final VirtualXmlManyToMany2_0 virtualXmlManyToMany;
		
	public VirtualEclipseLinkXmlManyToMany2_0(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToManyMapping;
		this.eclipseLinkVirtualXmlManyToMany = new VirtualEclipseLinkXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
		this.virtualXmlManyToMany = new VirtualXmlManyToMany2_0(ormTypeMapping, javaManyToManyMapping);
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
	public XmlMapKeyClass getMapKeyClass() {
		return this.virtualXmlManyToMany.getMapKeyClass();
	}
	
	@Override
	public void setMapKeyClass(XmlMapKeyClass value) {
		this.virtualXmlManyToMany.setMapKeyClass(value);
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
		return this.eclipseLinkVirtualXmlManyToMany.getJoinFetch();
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		this.eclipseLinkVirtualXmlManyToMany.setJoinFetch(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.eclipseLinkVirtualXmlManyToMany.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.eclipseLinkVirtualXmlManyToMany.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.eclipseLinkVirtualXmlManyToMany.getProperties();
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return this.eclipseLinkVirtualXmlManyToMany.getJoinFetchTextRange();
	}
	
	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
