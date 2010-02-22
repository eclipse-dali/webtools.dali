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
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualMapKey;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeOverride;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlColumn;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOrderColumn;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualXmlElementCollection2_0 is an implementation of XmlElementCollection used when there is 
 * no tag in the orm.xml and an underlying java element collection exists.
 */
public class VirtualXmlElementCollection2_0 extends XmlElementCollection
{
	protected OrmTypeMapping ormTypeMapping;
	
	protected final JavaElementCollectionMapping2_0 javaAttributeMapping;
	
	protected final VirtualXmlAttributeMapping virtualXmlAttributeMapping;
	
	protected final MapKey mapKey;
	
	protected final XmlMapKeyClass mapKeyClass;

	protected final VirtualXmlOrderColumn orderColumn;

	protected final VirtualXmlCollectionTable collectionTable;

	protected final VirtualXmlColumn valueColumn;

	protected final VirtualXmlColumn mapKeyColumn;

	public VirtualXmlElementCollection2_0(
			OrmTypeMapping ormTypeMapping, JavaElementCollectionMapping2_0 javaMapping) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaAttributeMapping = javaMapping;
		this.virtualXmlAttributeMapping = new VirtualXmlAttributeMapping(ormTypeMapping, javaMapping);
		this.mapKey = new VirtualMapKey(javaMapping);
		this.mapKeyClass = new VirtualMapKeyClass(javaMapping);
		this.orderColumn = new VirtualXmlOrderColumn(
			((Orderable2_0) this.javaAttributeMapping.getOrderable()).getOrderColumn(),
			this.ormTypeMapping);
		this.collectionTable = new VirtualXmlCollectionTable(
			this.ormTypeMapping, 
			this.javaAttributeMapping.getCollectionTable());
		this.valueColumn = new VirtualXmlColumn(ormTypeMapping, javaMapping.getValueColumn());
		this.mapKeyColumn = new VirtualXmlColumn(ormTypeMapping, javaMapping.getMapKeyColumn());
	}
	
	protected boolean isOrmMetadataComplete() {
		return this.ormTypeMapping.isMetadataComplete();
	}
	
	@Override
	public String getMappingKey() {
		return this.virtualXmlAttributeMapping.getMappingKey();
	}
	
	@Override
	public String getName() {
		return this.virtualXmlAttributeMapping.getName();
	}

	@Override
	public void setName(String newName) {
		this.virtualXmlAttributeMapping.setName(newName);
	}
	
	@Override
	public TextRange getNameTextRange() {
		return this.virtualXmlAttributeMapping.getNameTextRange();
	}

	@Override
	public FetchType getFetch() {
		if (this.isOrmMetadataComplete()) {
			return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getDefaultFetch());
		}
		return org.eclipse.jpt.core.context.FetchType.toOrmResourceModel(this.javaAttributeMapping.getFetch());
	}

	@Override
	public void setFetch(FetchType newFetch) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
	public String getTargetClass() {
		if (this.isOrmMetadataComplete()) {
			return this.javaAttributeMapping.getDefaultTargetClass();
		}
		return this.javaAttributeMapping.getTargetClass();
	}

	@Override
	public void setTargetClass(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public TextRange getTargetClassTextRange() {
		return null;
	}
	
	
	@Override
	public XmlCollectionTable getCollectionTable() {
		return this.collectionTable;
	}

	@Override
	public void setCollectionTable(XmlCollectionTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public XmlColumn getColumn() {
		return this.valueColumn;
	}

	@Override
	public void setColumn(XmlColumn newColumn) {
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
	public EList<XmlAttributeOverride> getAttributeOverrides() {
		EList<XmlAttributeOverride> attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__ATTRIBUTE_OVERRIDES);
		ListIterator<JavaAttributeOverride> javaAttributeOverrides;
		if (!this.isOrmMetadataComplete()) {
			javaAttributeOverrides = this.javaAttributeMapping.getValueAttributeOverrideContainer().attributeOverrides();
		}
		else {
			javaAttributeOverrides = this.javaAttributeMapping.getValueAttributeOverrideContainer().virtualAttributeOverrides();
		}
		for (JavaAttributeOverride javaAttributeOverride : CollectionTools.iterable(javaAttributeOverrides)) {
			XmlColumn xmlColumn = new VirtualXmlColumn(this.ormTypeMapping, javaAttributeOverride.getColumn());
			XmlAttributeOverride xmlAttributeOverride = new VirtualXmlAttributeOverride(javaAttributeOverride.getName(), xmlColumn);
			attributeOverrides.add(xmlAttributeOverride);
		}
		return attributeOverrides;
	}

	@Override
	public EList<XmlAssociationOverride> getAssociationOverrides() {
		EList<XmlAssociationOverride> associationOverrides = new EObjectContainmentEList<XmlAssociationOverride>(XmlAssociationOverride.class, this, OrmPackage.XML_ELEMENT_COLLECTION__ASSOCIATION_OVERRIDES);
		ListIterator<JavaAssociationOverride> javaAssociationOverrides;
		if (!this.isOrmMetadataComplete()) {
			javaAssociationOverrides = this.javaAttributeMapping.getValueAssociationOverrideContainer().associationOverrides();
		}
		else {
			javaAssociationOverrides = this.javaAttributeMapping.getValueAssociationOverrideContainer().virtualAssociationOverrides();
		}
		for (JavaAssociationOverride javaAssociationOverride : CollectionTools.iterable(javaAssociationOverrides)) {
			XmlAssociationOverride xmlAssociationOverride = new VirtualXmlAssociationOverride2_0(javaAssociationOverride.getName(), this.ormTypeMapping, javaAssociationOverride.getRelationshipReference().getPredominantJoiningStrategy());
			associationOverrides.add(xmlAssociationOverride);
		}
		return associationOverrides;
	}

	@Override
	public MapKey getMapKey() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		if (this.javaAttributeMapping.isNoMapKey()) {
			return null;
		}
		return this.mapKey;
	}

	@Override
	public void setMapKey(MapKey value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}

	@Override
	public XmlMapKeyClass getMapKeyClass() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.mapKeyClass;
	}

	@Override
	public void setMapKeyClass(XmlMapKeyClass newMapKeyClass) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}	
	
	@Override
	public String getOrderBy() {
		if (this.isOrmMetadataComplete()) {
			return null;
		}
		return this.javaAttributeMapping.getOrderable().getSpecifiedOrderBy();
	}

	@Override
	public void setOrderBy(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
}
