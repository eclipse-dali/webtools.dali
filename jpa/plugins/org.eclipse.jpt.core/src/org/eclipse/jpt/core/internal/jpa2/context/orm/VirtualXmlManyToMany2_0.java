/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlManyToMany;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOrderColumn;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaManyToManyMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlManyToMany2_0 extends XmlManyToMany
{
		
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaManyToManyMapping javaAttributeMapping;

	protected final VirtualXmlManyToMany virtualXmlManyToMany;
	
	protected final XmlClassReference mapKeyClass;
	
	protected VirtualXmlOrderColumn orderColumn;

	protected final VirtualXmlColumn mapKeyColumn;

	public VirtualXmlManyToMany2_0(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping2_0 javaManyToManyMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaManyToManyMapping;
		this.virtualXmlManyToMany = new VirtualXmlManyToMany(ormTypeMapping, javaManyToManyMapping);
		this.mapKeyClass = new VirtualMapKeyClassReference(javaManyToManyMapping);
		this.orderColumn = new VirtualXmlOrderColumn(
			((Orderable2_0) this.javaAttributeMapping.getOrderable()).getOrderColumn(),
			this.ormTypeMapping);
		this.mapKeyColumn = new VirtualXmlColumn(ormTypeMapping, javaManyToManyMapping.getMapKeyColumn());
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
	public XmlClassReference getMapKeyClass() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.mapKeyClass;
	}

	@Override
	public void setMapKeyClass(XmlClassReference newMapKeyClass) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public XmlColumn getMapKeyColumn() {
		return this.mapKeyColumn;
	}

	@Override
	public void setMapKeyColumn(XmlColumn newMapKeyColumn) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		if (((Orderable2_0) this.javaAttributeMapping.getOrderable()).isOrderColumnOrdering()) {
			return this.orderColumn;
		}
		return null;
	}

	@Override
	public void setOrderColumn(XmlOrderColumn newOrderColumn) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
