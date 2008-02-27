/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.EnumType;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.db.internal.Table;


public class GenericOrmBasicMapping extends AbstractOrmAttributeMapping<XmlBasic>
	implements OrmBasicMapping
{
	protected final OrmColumn column;
	
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
		
	protected TemporalType temporal;
	
	protected boolean lob;
	
	public GenericOrmBasicMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = jpaFactory().buildOrmColumn(this, this);
	}

	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return BasicMapping.DEFAULT_FETCH_TYPE;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.attributeMapping().setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.attributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}
	
	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}

	public boolean isLob() {
		return this.lob;
	}

	public void setLob(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		this.attributeMapping().setLob(newLob);
		firePropertyChanged(BasicMapping.LOB_PROPERTY, oldLob, newLob);
	}
	
	protected void setLob_(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		firePropertyChanged(BasicMapping.LOB_PROPERTY, oldLob, newLob);
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
	
	public EnumType getEnumerated() {
		return (this.getSpecifiedEnumerated() == null) ? this.getDefaultEnumerated() : this.getSpecifiedEnumerated();
	}
	
	public EnumType getDefaultEnumerated() {
		return BasicMapping.DEFAULT_ENUMERATED;
	}
	
	public EnumType getSpecifiedEnumerated() {
		return this.specifiedEnumerated;
	}
	
	public void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		this.attributeMapping().setEnumerated(EnumType.toOrmResourceModel(newSpecifiedEnumerated));
		firePropertyChanged(BasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}
	
	protected void setSpecifiedEnumerated_(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		firePropertyChanged(BasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmBasicMapping(this);
	}


	@Override
	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromXmlColumnMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	public int xmlSequence() {
		return 1;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public OrmColumn getColumn() {
		return this.column;
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
	public void initialize(XmlBasic basic) {
		super.initialize(basic);
		this.specifiedFetch = this.specifiedFetch(basic);
		this.specifiedOptional = this.specifiedOptional(basic);
		this.specifiedEnumerated = this.specifiedEnumerated(basic);
		this.temporal = this.specifiedTemporal(basic);
		this.lob = specifiedLob(basic);
		this.column.initialize(basic.getColumn());
	}
	
	@Override
	public void update(XmlBasic basic) {
		super.update(basic);
		this.setSpecifiedFetch_(this.specifiedFetch(basic));
		this.setSpecifiedOptional_(this.specifiedOptional(basic));
		this.setSpecifiedEnumerated_(this.specifiedEnumerated(basic));
		this.setTemporal_(this.specifiedTemporal(basic));
		this.setLob_(this.specifiedLob(basic));
		this.column.update(basic.getColumn());
	}
	
	protected Boolean specifiedOptional(XmlBasic basic) {
		return basic.getOptional();
	}
	
	protected FetchType specifiedFetch(XmlBasic basic) {
		return FetchType.fromOrmResourceModel(basic.getFetch());
	}
	
	protected EnumType specifiedEnumerated(XmlBasic basic) {
		return EnumType.fromOrmResourceModel(basic.getEnumerated());
	}
	
	protected TemporalType specifiedTemporal(XmlBasic basic) {
		return TemporalType.fromOrmResourceModel(basic.getTemporal());
	}

	protected boolean specifiedLob(XmlBasic basic) {
		return basic.isLob();
	}
	
	public XmlBasic addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlBasic basic = OrmFactory.eINSTANCE.createBasicImpl();
		persistentAttribute().initialize(basic);
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getBasics().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
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
