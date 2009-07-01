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
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToOne;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.JoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLinkJavaManyToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class EclipseLinkVirtualXmlManyToOne extends XmlManyToOne
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToOneMapping javaAttributeMapping;

	protected final VirtualXmlManyToOne virtualXmlManyToOne;
		
	public EclipseLinkVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToOneMapping;
		this.virtualXmlManyToOne = new VirtualXmlManyToOne(ormTypeMapping, javaManyToOneMapping);
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
		if (isOrmMetadataComplete()) {
			return null; //don't return default value, it only applies for an empty @JoinFetch
		}
		return JoinFetchType.toOrmResourceModel(((EclipseLinkJavaManyToOneMapping) this.javaAttributeMapping).getJoinFetch().getValue());
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
