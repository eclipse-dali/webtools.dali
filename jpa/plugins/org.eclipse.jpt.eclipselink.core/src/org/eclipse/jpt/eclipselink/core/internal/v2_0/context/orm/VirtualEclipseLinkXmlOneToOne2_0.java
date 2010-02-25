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
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlOneToOne2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlOneToOne;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkOneToOneMapping2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlOneToOne2_0 extends XmlOneToOne
{
	protected OrmTypeMapping ormTypeMapping;

	protected final JavaEclipseLinkOneToOneMapping2_0 javaAttributeMapping;

	protected final VirtualEclipseLinkXmlOneToOne eclipseLinkVirtualXmlOneToOne;

	protected final VirtualXmlOneToOne2_0 virtualXmlOneToOne;

	public VirtualEclipseLinkXmlOneToOne2_0(OrmTypeMapping ormTypeMapping, JavaEclipseLinkOneToOneMapping2_0 javaOneToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToOneMapping;
		this.eclipseLinkVirtualXmlOneToOne = new VirtualEclipseLinkXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
		this.virtualXmlOneToOne = new VirtualXmlOneToOne2_0(ormTypeMapping, javaOneToOneMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlOneToOne.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlOneToOne.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlOneToOne.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlOneToOne.getNameTextRange();
	}
	
	@Override
	public FetchType getFetch() {
		return this.virtualXmlOneToOne.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlOneToOne.setFetch(newFetch);
	}

	@Override
	public Boolean getOptional() {
		return this.virtualXmlOneToOne.getOptional();
	}

	@Override
	public void setOptional(Boolean newOptional) {
		this.virtualXmlOneToOne.setOptional(newOptional);
	}

	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		return this.virtualXmlOneToOne.getJoinColumns();
	}

	@Override
	public CascadeType getCascade() {
		return this.virtualXmlOneToOne.getCascade();
	}
	
	@Override
	public void setCascade(CascadeType value) {
		this.virtualXmlOneToOne.setCascade(value);
	}
	
	@Override
	public XmlJoinTable getJoinTable() {
		return this.virtualXmlOneToOne.getJoinTable();
	}

	@Override
	public void setJoinTable(XmlJoinTable value) {
		this.virtualXmlOneToOne.setJoinTable(value);
	}
	
	@Override
	public String getTargetEntity() {
		return this.virtualXmlOneToOne.getTargetEntity();
	}

	@Override
	public void setTargetEntity(String value) {
		this.virtualXmlOneToOne.setTargetEntity(value);
	}

	@Override
	public String getMappedBy() {
		return this.virtualXmlOneToOne.getMappedBy();
	}

	@Override
	public void setMappedBy(String value) {
		this.virtualXmlOneToOne.setMappedBy(value);
	}

	@Override
	public EList<XmlPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns() {
		return this.virtualXmlOneToOne.getPrimaryKeyJoinColumns();
	}
	
	@Override
	public TextRange getMappedByTextRange() {
		return this.virtualXmlOneToOne.getMappedByTextRange();
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlOneToOne.getTargetEntityTextRange();
	}
	
	@Override
	public XmlJoinFetchType getJoinFetch() {
		return this.eclipseLinkVirtualXmlOneToOne.getJoinFetch();
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		this.eclipseLinkVirtualXmlOneToOne.setJoinFetch(value);
	}
	
	@Override
	public boolean isPrivateOwned() {
		return this.eclipseLinkVirtualXmlOneToOne.isPrivateOwned();
	}
	
	@Override
	public void setPrivateOwned(boolean value) {
		this.eclipseLinkVirtualXmlOneToOne.setPrivateOwned(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.eclipseLinkVirtualXmlOneToOne.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.eclipseLinkVirtualXmlOneToOne.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.eclipseLinkVirtualXmlOneToOne.getProperties();
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return this.eclipseLinkVirtualXmlOneToOne.getJoinFetchTextRange();
	}
	
	@Override
	public TextRange getPrivateOwnedTextRange() {
		return this.eclipseLinkVirtualXmlOneToOne.getPrivateOwnedTextRange();
	}	
	
	@Override
	public AccessType getAccess() {
		return this.virtualXmlOneToOne.getAccess();
	}
	
	@Override
	public void setAccess(AccessType value) {
		this.virtualXmlOneToOne.setAccess(value);
	}
	
	@Override
	public Boolean getId() {
		return this.virtualXmlOneToOne.getId();
	}
	
	@Override
	public void setId(Boolean newId) {
		this.virtualXmlOneToOne.setId(newId);
	}
	
	@Override
	public String getMapsId() {
		return this.virtualXmlOneToOne.getMapsId();
	}
	
	@Override
	public void setMapsId(String newMapsId) {
		this.virtualXmlOneToOne.setMapsId(newMapsId);
	}
}
