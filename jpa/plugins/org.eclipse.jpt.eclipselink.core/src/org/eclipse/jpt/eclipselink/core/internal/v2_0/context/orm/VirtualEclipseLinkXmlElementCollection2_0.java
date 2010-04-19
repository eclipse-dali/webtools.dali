/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.jpa2.context.orm.VirtualXmlElementCollection2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.EnumType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.TemporalType;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlClassReference;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkJoinFetchType;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkElementCollectionMapping2_0;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlAccessMethods;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlProperty;

/**
 * VirtualBasic is an implementation of Basic used when there is 
 * no tag in the orm.xml and an underlying javaBasicMapping exists.
 */
public class VirtualEclipseLinkXmlElementCollection2_0 extends XmlElementCollection
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaElementCollectionMapping2_0 javaAttributeMapping;

	protected final VirtualXmlElementCollection2_0 virtualXmlElementCollection;
	
	public VirtualEclipseLinkXmlElementCollection2_0(OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaMapping;
		this.virtualXmlElementCollection = new VirtualXmlElementCollection2_0(ormTypeMapping, javaMapping);
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlElementCollection.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlElementCollection.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlElementCollection.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlElementCollection.getNameTextRange();
	}
	@Override
	public FetchType getFetch() {
		return this.virtualXmlElementCollection.getFetch();
	}

	@Override
	public void setFetch(FetchType newFetch) {
		this.virtualXmlElementCollection.setFetch(newFetch);
	}

	@Override
	public XmlColumn getColumn() {
		return this.virtualXmlElementCollection.getColumn();
	}

	@Override
	public void setColumn(XmlColumn value) {
		this.virtualXmlElementCollection.setColumn(value);
	}

	@Override
	public XmlColumn getMapKeyColumn() {
		return this.virtualXmlElementCollection.getMapKeyColumn();
	}

	@Override
	public void setMapKeyColumn(XmlColumn newMapKeyColumn) {
		this.virtualXmlElementCollection.setMapKeyColumn(newMapKeyColumn);
	}

	@Override
	public TemporalType getTemporal() {
		return this.virtualXmlElementCollection.getTemporal();
	}

	@Override
	public void setTemporal(TemporalType newTemporal){
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public EnumType getEnumerated() {
		return this.virtualXmlElementCollection.getEnumerated();
	}
	
	@Override
	public void setEnumerated(EnumType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
	
	@Override
	public boolean isLob() {
		return this.virtualXmlElementCollection.isLob();
	}
	
	@Override
	public void setLob(boolean value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	@Override
	public TextRange getEnumeratedTextRange() {
		return this.virtualXmlElementCollection.getEnumeratedTextRange();
	}
	
	@Override
	public TextRange getLobTextRange() {
		return this.virtualXmlElementCollection.getLobTextRange();
	}
	
	@Override
	public TextRange getTemporalTextRange() {
		return this.virtualXmlElementCollection.getTemporalTextRange();
	}

	@Override
	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualXmlElementCollection.getAttributeOverrides();
	}

	@Override
	public EList<XmlAssociationOverride> getAssociationOverrides() {
		return this.virtualXmlElementCollection.getAssociationOverrides();
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
	public AccessType getAccess() {
		return this.virtualXmlElementCollection.getAccess();
	}
	
	@Override
	public void setAccess(AccessType value) {
		this.virtualXmlElementCollection.setAccess(value);
	}
	
	@Override
	public String getTargetClass() {
		return this.virtualXmlElementCollection.getTargetClass();
	}
	
	@Override
	public void setTargetClass(String newTargetClass) {
		this.virtualXmlElementCollection.setTargetClass(newTargetClass);
	}
	
	@Override
	public XmlCollectionTable getCollectionTable() {
		return this.virtualXmlElementCollection.getCollectionTable();
	}

	@Override
	public void setCollectionTable(XmlCollectionTable value) {
		this.virtualXmlElementCollection.setCollectionTable(value);
	}

	@Override
	public MapKey getMapKey() {
		return this.virtualXmlElementCollection.getMapKey();
	}
	
	@Override
	public void setMapKey(MapKey newMapKey) {
		this.virtualXmlElementCollection.setMapKey(newMapKey);
	}
	
	@Override
	public XmlClassReference getMapKeyClass() {
		return this.virtualXmlElementCollection.getMapKeyClass();
	}
	
	@Override
	public void setMapKeyClass(XmlClassReference newMapKeyClass) {
		this.virtualXmlElementCollection.setMapKeyClass(newMapKeyClass);
	}
	
	@Override
	public String getOrderBy() {
		return this.virtualXmlElementCollection.getOrderBy();
	}
	
	@Override
	public void setOrderBy(String newOrderBy) {
		this.virtualXmlElementCollection.setOrderBy(newOrderBy);
	}
	
	@Override
	public XmlOrderColumn getOrderColumn() {
		return this.virtualXmlElementCollection.getOrderColumn();
	}
	
	@Override
	public void setOrderColumn(XmlOrderColumn newOrderColumn) {
		this.virtualXmlElementCollection.setOrderColumn(newOrderColumn);
	}

	@Override
	public EList<XmlAttributeOverride> getMapKeyAttributeOverrides() {
		return this.virtualXmlElementCollection.getMapKeyAttributeOverrides();
	}
	
	
	@Override
	public XmlJoinFetchType getJoinFetch() {
		if (isOrmMetadataComplete()) {
			return null; //don't return default value, it only applies for an empty @JoinFetch
		}
		return EclipseLinkJoinFetchType.toOrmResourceModel(((JavaEclipseLinkElementCollectionMapping2_0) this.javaAttributeMapping).getJoinFetch().getValue());
	}
	
	@Override
	public void setJoinFetch(XmlJoinFetchType value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

}
