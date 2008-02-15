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

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.EnumType;
import org.eclipse.jpt.core.internal.context.base.FetchType;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IColumnMapping;
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.core.internal.context.base.INullable;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.Column;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Table;


public class XmlBasicMapping extends XmlAttributeMapping<Basic>
	implements IBasicMapping, IXmlColumnMapping
{
	protected final XmlColumn column;
	
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
		
	protected TemporalType temporal;
	
	protected boolean lob;
	
	protected XmlBasicMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.column = new XmlColumn(this, this);
	}

	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return IBasicMapping.DEFAULT_FETCH_TYPE;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.attributeMapping().setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(IFetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return INullable.DEFAULT_OPTIONAL;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.attributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(INullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}
	
	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(INullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}

	public boolean isLob() {
		return this.lob;
	}

	public void setLob(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		this.attributeMapping().setLob(newLob);
		firePropertyChanged(IBasicMapping.LOB_PROPERTY, oldLob, newLob);
	}
	
	protected void setLob_(boolean newLob) {
		boolean oldLob = this.lob;
		this.lob = newLob;
		firePropertyChanged(IBasicMapping.LOB_PROPERTY, oldLob, newLob);
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.attributeMapping().setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	protected void setTemporal_(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		firePropertyChanged(IColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	public EnumType getEnumerated() {
		return (this.getSpecifiedEnumerated() == null) ? this.getDefaultEnumerated() : this.getSpecifiedEnumerated();
	}
	
	public EnumType getDefaultEnumerated() {
		return IBasicMapping.DEFAULT_ENUMERATED;
	}
	
	public EnumType getSpecifiedEnumerated() {
		return this.specifiedEnumerated;
	}
	
	public void setSpecifiedEnumerated(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		this.attributeMapping().setEnumerated(EnumType.toOrmResourceModel(newSpecifiedEnumerated));
		firePropertyChanged(IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}
	
	protected void setSpecifiedEnumerated_(EnumType newSpecifiedEnumerated) {
		EnumType oldEnumerated = this.specifiedEnumerated;
		this.specifiedEnumerated = newSpecifiedEnumerated;
		firePropertyChanged(IBasicMapping.SPECIFIED_ENUMERATED_PROPERTY, oldEnumerated, newSpecifiedEnumerated);
	}

	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlBasicMapping(this);
	}


	@Override
	public void initializeFromXmlColumnMapping(IXmlColumnMapping oldMapping) {
		super.initializeFromXmlColumnMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	@Override
	public int xmlSequence() {
		return 1;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public XmlColumn getColumn() {
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

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initialize(Basic basic) {
		super.initialize(basic);
		this.specifiedFetch = this.specifiedFetch(basic);
		this.specifiedOptional = this.specifiedOptional(basic);
		this.specifiedEnumerated = this.specifiedEnumerated(basic);
		this.temporal = this.specifiedTemporal(basic);
		this.lob = specifiedLob(basic);
		this.column.initialize(basic.getColumn());
	}
	
	@Override
	public void update(Basic basic) {
		super.update(basic);
		this.setSpecifiedFetch_(this.specifiedFetch(basic));
		this.setSpecifiedOptional_(this.specifiedOptional(basic));
		this.setSpecifiedEnumerated_(this.specifiedEnumerated(basic));
		this.setTemporal_(this.specifiedTemporal(basic));
		this.setLob_(this.specifiedLob(basic));
		this.column.update(basic.getColumn());
	}
	
	protected Boolean specifiedOptional(Basic basic) {
		return basic.getOptional();
	}
	
	protected FetchType specifiedFetch(Basic basic) {
		return FetchType.fromOrmResourceModel(basic.getFetch());
	}
	
	protected EnumType specifiedEnumerated(Basic basic) {
		return EnumType.fromOrmResourceModel(basic.getEnumerated());
	}
	
	protected TemporalType specifiedTemporal(Basic basic) {
		return TemporalType.fromOrmResourceModel(basic.getTemporal());
	}

	protected boolean specifiedLob(Basic basic) {
		return basic.isLob();
	}
	
	@Override
	public Basic addToResourceModel(TypeMapping typeMapping) {
		Basic basic = OrmFactory.eINSTANCE.createBasicImpl();
		persistentAttribute().initialize(basic);
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getBasics().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	//***************** IXmlColumn.Owner implementation ****************
	
	public Column columnResource() {
		return this.attributeMapping().getColumn();
	}
	
	public void addColumnResource() {
		this.attributeMapping().setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeMapping().setColumn(null);
	}

}
