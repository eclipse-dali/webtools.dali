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
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlManyToOne2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToOneMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlManyToOne2_0 extends XmlManyToOne
{
	protected OrmTypeMapping ormTypeMapping;

	protected final JavaManyToOneMapping javaAttributeMapping;

	protected final VirtualEclipseLinkXmlManyToOne eclipseLinkVirtualXmlManyToOne;

	protected final VirtualXmlManyToOne2_0 virtualXmlManyToOne;

	public VirtualEclipseLinkXmlManyToOne2_0(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping2_0 javaManyToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToOneMapping;
		this.eclipseLinkVirtualXmlManyToOne = new VirtualEclipseLinkXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
		this.virtualXmlManyToOne = new VirtualXmlManyToOne2_0(ormTypeMapping, javaManyToOneMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlManyToOne.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlManyToOne.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlManyToOne.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlManyToOne.getNameTextRange();
	}

	@Override
	public FetchType getFetch() {
		return this.virtualXmlManyToOne.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlManyToOne.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlManyToOne.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlManyToOne.setOptional(newOptional);
	}
	
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlManyToOne.getJoinColumns();
	}

	@Override
	public CascadeType getCascade() {
		return this.virtualXmlManyToOne.getCascade();
	}
	
	@Override
	public void setCascade(CascadeType value) {
		this.virtualXmlManyToOne.setCascade(value);
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		return this.virtualXmlManyToOne.getJoinTable();
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		this.virtualXmlManyToOne.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlManyToOne.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlManyToOne.setTargetEntity(value);
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlManyToOne.getTargetEntityTextRange();
	}
	
	@Override
	public XmlJoinFetchType getJoinFetch() {
		return this.eclipseLinkVirtualXmlManyToOne.getJoinFetch();
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		this.eclipseLinkVirtualXmlManyToOne.setJoinFetch(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.eclipseLinkVirtualXmlManyToOne.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.eclipseLinkVirtualXmlManyToOne.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.eclipseLinkVirtualXmlManyToOne.getProperties();
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return this.eclipseLinkVirtualXmlManyToOne.getJoinFetchTextRange();
	}
	
	@Override
	public AccessType getAccess() {
		return this.virtualXmlManyToOne.getAccess();
	}
	
	@Override
	public void setAccess(AccessType value) {
		this.virtualXmlManyToOne.setAccess(value);
	}
	
	@Override
	public Boolean getId() {
		return this.virtualXmlManyToOne.getId();
	}
	
	@Override
	public void setId(Boolean newId) {
		this.virtualXmlManyToOne.setId(newId);
	}
	
	@Override
	public String getMapsId() {
		return this.virtualXmlManyToOne.getMapsId();
	}
	
	@Override
	public void setMapsId(String newMapsId) {
		this.virtualXmlManyToOne.setMapsId(newMapsId);
	}
}
