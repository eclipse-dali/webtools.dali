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

import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualMapKey;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlAttributeMapping;
import org.eclipse.jpt.core.internal.context.orm.VirtualXmlOrderColumn;
import org.eclipse.jpt.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.resource.orm.AccessType;
import org.eclipse.jpt.core.resource.orm.FetchType;
import org.eclipse.jpt.core.resource.orm.MapKey;
import org.eclipse.jpt.core.resource.orm.XmlCollectionTable;
import org.eclipse.jpt.core.resource.orm.XmlElementCollection;
import org.eclipse.jpt.core.resource.orm.XmlMapKeyClass;
import org.eclipse.jpt.core.resource.orm.XmlOrderColumn;
import org.eclipse.jpt.core.utility.TextRange;

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

	protected VirtualXmlOrderColumn orderColumn;

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
		if (this.javaAttributeMapping.getCollectionTable() != null) {
			return 	new VirtualXmlCollectionTable(
				this.ormTypeMapping, 
				this.javaAttributeMapping.getCollectionTable());
		}
		return null;
	}

	@Override
	public void setCollectionTable(XmlCollectionTable value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
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
