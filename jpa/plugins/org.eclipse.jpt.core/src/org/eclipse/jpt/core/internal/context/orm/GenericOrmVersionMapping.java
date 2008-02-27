/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.db.internal.Table;


public class GenericOrmVersionMapping extends AbstractOrmAttributeMapping<XmlVersion>
	implements OrmVersionMapping
{
	protected final OrmColumn column;

	protected TemporalType temporal;
	
	public GenericOrmVersionMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = jpaFactory().buildOrmColumn(this, this);
	}

	public int xmlSequence() {
		return 2;
	}

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmVersionMapping(this);
	}

	@Override
	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromXmlColumnMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	public OrmColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.attributeMapping().setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	protected void setTemporal_(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	public XmlVersion addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlVersion version = OrmFactory.eINSTANCE.createVersionImpl();
		persistentAttribute().initialize(version);
		typeMapping.getAttributes().getVersions().add(version);
		return version;
	}
	
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getVersions().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public String defaultColumnName() {		
		return attributeName();
	}

	public String defaultTableName() {
		return typeMapping().tableName();
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}
	
	@Override
	public void initialize(XmlVersion version) {
		super.initialize(version);
		this.temporal = this.specifiedTemporal(version);
		this.column.initialize(version.getColumn());
	}
	
	@Override
	public void update(XmlVersion version) {
		super.update(version);
		this.setTemporal_(this.specifiedTemporal(version));
		this.column.update(version.getColumn());
	}
	
	protected TemporalType specifiedTemporal(XmlVersion version) {
		return TemporalType.fromOrmResourceModel(version.getTemporal());
	}

	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn columnResource() {
		return this.attributeMapping().getColumn();
	}
	
	public void addColumnResource() {
		this.attributeMapping().setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeMapping().setColumn(null);
	}
}
