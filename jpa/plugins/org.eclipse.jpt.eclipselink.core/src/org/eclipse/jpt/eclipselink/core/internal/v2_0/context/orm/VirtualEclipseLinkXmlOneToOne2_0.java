/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToOneMapping2_0;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.VirtualEclipseLinkXmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 *  VirtualEclipseLinkXmlOneToOne2_0
 */
public class VirtualEclipseLinkXmlOneToOne2_0 extends XmlOneToOne
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToOneMapping2_0 javaAttributeMapping;

	protected final VirtualEclipseLinkXmlOneToOne virtualXmlOneToOne;

	public VirtualEclipseLinkXmlOneToOne2_0(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping2_0 javaOneToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToOneMapping;
		this.virtualXmlOneToOne = new VirtualEclipseLinkXmlOneToOne(ormTypeMapping, javaOneToOneMapping);
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
	public boolean isOrphanRemoval() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.isDefaultOrphanRemoval();
		}
		return this.javaAttributeMapping.isOrphanRemoval();
	}

	@Override
	public void setOrphanRemoval(boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		return this.virtualXmlOneToOne.getJoinFetch();
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		this.virtualXmlOneToOne.setJoinFetch(value);
	}
	
	@Override
	public boolean isPrivateOwned() {
		return this.virtualXmlOneToOne.isPrivateOwned();
	}
	
	@Override
	public void setPrivateOwned(boolean value) {
		this.virtualXmlOneToOne.setPrivateOwned(value);
	}
	
	@Override
	public XmlAccessMethods getAccessMethods() {
		return this.virtualXmlOneToOne.getAccessMethods();
	}
	
	@Override
	public void setAccessMethods(XmlAccessMethods value) {
		this.virtualXmlOneToOne.setAccessMethods(value);
	}
	
	@Override
	public EList<XmlProperty> getProperties() {
		return this.virtualXmlOneToOne.getProperties();
	}
	
	@Override
	public TextRange getJoinFetchTextRange() {
		return this.virtualXmlOneToOne.getJoinFetchTextRange();
	}
	
	@Override
	public TextRange getPrivateOwnedTextRange() {
		return this.virtualXmlOneToOne.getPrivateOwnedTextRange();
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
