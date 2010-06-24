/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlJoinColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOneToMany;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOrderColumn;
import org.eclipse.jpt.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOneToManyMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.CascadeType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlJoinTable;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualXmlOneToMany2_0 extends XmlOneToMany
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaOneToManyMapping2_0 javaAttributeMapping;
	
	protected final VirtualXmlOneToMany virtualXmlOneToMany;
	
	protected final VirtualXmlCascadeType2_0 virtualXmlCascadeType;
	
	protected final XmlClassReference mapKeyClass;
	
	protected VirtualXmlOrderColumn orderColumn;
	
	protected final VirtualXmlColumn mapKeyColumn;
	
	
	public VirtualXmlOneToMany2_0(
			OrmTypeMapping ormTypeMapping, JavaOneToManyMapping2_0 javaOneToManyMapping) {
	
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaOneToManyMapping;
		this.virtualXmlOneToMany = new VirtualXmlOneToMany(ormTypeMapping, javaOneToManyMapping);
		this.virtualXmlCascadeType = new VirtualXmlCascadeType2_0(javaOneToManyMapping.getCascade());
		this.mapKeyClass = new VirtualMapKeyClassReference(javaOneToManyMapping);
		this.orderColumn = new VirtualXmlOrderColumn(
			((Orderable2_0) this.javaAttributeMapping.getOrderable()).getOrderColumn(),
			this.ormTypeMapping);
		this.mapKeyColumn = new VirtualXmlColumn(ormTypeMapping, javaOneToManyMapping.getMapKeyColumn());
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
		if (isOrmMetadataComplete()) {
			return null;
		}
		return this.virtualXmlCascadeType;
	}
	
	@Override
	public void setCascade(CascadeType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	private JavaOrphanRemovable2_0 getOrphanRemovalOf(OneToManyMapping2_0 oneToManyMapping) {
		return ((JavaOrphanRemovalHolder2_0) oneToManyMapping).getOrphanRemoval();
	}
	
	@Override
	public Boolean getOrphanRemoval() {
		JavaOrphanRemovable2_0 mappingsOrphanRemoval = this.getOrphanRemovalOf(this.javaAttributeMapping);
		if (this.isOrmMetadataComplete()) {
			return Boolean.valueOf(mappingsOrphanRemoval.isDefaultOrphanRemoval());
		}
		return Boolean.valueOf(mappingsOrphanRemoval.isOrphanRemoval());
	}

	@Override
	public void setOrphanRemoval(Boolean newOrphanRemoval) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
		return this.virtualXmlOneToMany.getOrderBy();
	}
	
	@Override
	public void setOrderBy(String value) {
		this.virtualXmlOneToMany.setOrderBy(value);
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
		return this.virtualXmlOneToMany.getMappedByTextRange();
	}
	
	@Override
	public TextRange getTargetEntityTextRange() {
		return this.virtualXmlOneToMany.getTargetEntityTextRange();
	}	
	
	@Override
	public EList<XmlJoinColumn> getJoinColumns() {
		EList<XmlJoinColumn> joinColumns = new EObjectContainmentEList<XmlJoinColumn>(XmlJoinColumn.class, this, OrmPackage.XML_ONE_TO_MANY__JOIN_COLUMNS);
		if (isOrmMetadataComplete()) {
			return joinColumns;
		}
		for (JoinColumn joinColumn : 
			CollectionTools.iterable(
				this.javaAttributeMapping.getRelationshipReference().
					getJoinColumnJoiningStrategy().specifiedJoinColumns())) {
			XmlJoinColumn xmlJoinColumn = new VirtualXmlJoinColumn(joinColumn, isOrmMetadataComplete());
			joinColumns.add(xmlJoinColumn);
		}
		return joinColumns;
	}
	
	@Override
	public AccessType getAccess() {
		return org.eclipse.jpt.core.context.AccessType.toOrmResourceModel(this.javaAttributeMapping.getPersistentAttribute().getAccess());
	}
	
	@Override
	public void setAccess(AccessType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides() {
		EList<XmlAttributeOverride> attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_ONE_TO_MANY__MAP_KEY_ATTRIBUTE_OVERRIDES);
		ListIterator<JavaAttributeOverride> javaAttributeOverrides;
		if (!this.isOrmMetadataComplete()) {
			javaAttributeOverrides = this.javaAttributeMapping.getMapKeyAttributeOverrideContainer().attributeOverrides();
		}
		else {
			javaAttributeOverrides = this.javaAttributeMapping.getMapKeyAttributeOverrideContainer().virtualAttributeOverrides();
		}
		for (JavaAttributeOverride javaAttributeOverride : CollectionTools.iterable(javaAttributeOverrides)) {
			XmlColumn xmlColumn = new VirtualXmlColumn(this.ormTypeMapping, javaAttributeOverride.getColumn());
			XmlAttributeOverride xmlAttributeOverride = new VirtualXmlAttributeOverride(javaAttributeOverride.getName(), xmlColumn);
			attributeOverrides.add(xmlAttributeOverride);
		}
		return attributeOverrides;
	}
}
