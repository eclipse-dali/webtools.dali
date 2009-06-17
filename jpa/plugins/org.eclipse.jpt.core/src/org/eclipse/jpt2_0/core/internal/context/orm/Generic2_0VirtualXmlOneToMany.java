/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.context.orm;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToMany;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt2_0.core.resource.orm.XmlOneToMany;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class Generic2_0VirtualXmlOneToMany extends XmlOneToMany
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToManyMapping javaAttributeMapping;

	protected final VirtualXmlOneToMany virtualXmlOneToMany;
		
	public Generic2_0VirtualXmlOneToMany(
			OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToManyMapping;
		this.virtualXmlOneToMany = new VirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
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
	public String getOrderBy() {
		return this.virtualXmlOneToMany.getOrderBy();
	}
	
	@Override
	public void setOrderBy(String value) {
		this.virtualXmlOneToMany.setOrderBy(value);
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
		return this.virtualXmlOneToMany.getJoinColumns();
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
