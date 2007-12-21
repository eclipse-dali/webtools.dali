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
import org.eclipse.jpt.core.internal.context.base.IFetchable;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Table;


public class XmlBasicMapping extends XmlAttributeMapping<Basic>
	implements IBasicMapping, IXmlColumnMapping
{
	protected final XmlColumn column;
	
	protected FetchType specifiedFetch;
	
	protected FetchType defaultFetch;	
	
	protected Boolean defaultOptional;

	protected Boolean specifiedOptional;
	
	protected EnumType specifiedEnumerated;
	
	protected EnumType defaultEnumerated;
	
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
		return this.defaultFetch;
	}
	
	protected void setDefaultFetch(FetchType newDefaultFetch) {
		FetchType oldFetch = this.defaultFetch;
		this.defaultFetch = newDefaultFetch;
		firePropertyChanged(IFetchable.DEFAULT_FETCH_PROPERTY, oldFetch, newDefaultFetch);
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


	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return this.defaultOptional;
	}
	
	protected void setDefaultOptional(Boolean newDefaultOptional) {
		Boolean oldOptional = this.defaultOptional;
		this.defaultOptional = newDefaultOptional;
		firePropertyChanged(IBasicMapping.DEFAULT_OPTIONAL_PROPERTY, oldOptional, newDefaultOptional);
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.attributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(IBasicMapping.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
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

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.attributeMapping().setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(IBasicMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
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

	protected void setDefaultEnumerated(EnumType newDefaultEnumerated) {
		EnumType oldEnumerated = this.defaultEnumerated;
		this.defaultEnumerated = newDefaultEnumerated;
		firePropertyChanged(IBasicMapping.DEFAULT_ENUMERATED_PROPERTY, oldEnumerated, newDefaultEnumerated);
	}

	public String getKey() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlBasicMapping(this);
	}

	@Override
	public void initializeFromXmlIdMapping(XmlIdMapping oldMapping) {
		super.initializeFromXmlIdMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
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

	public String attributeName() {
		return this.persistentAttribute().getName();
	}

	public String defaultColumnName() {		
		//TODO check java column for the case where this is a virtual mapping
		return attributeName();
	}

	public String defaultTableName() {
		//TODO check java column for the case where this is a virtual mapping
		return typeMapping().getTableName();
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
		this.column.initialize(basic);
	}
	
	@Override
	public void update(Basic basic) {
		super.update(basic);
		this.setSpecifiedFetch(this.specifiedFetch(basic));
		this.setSpecifiedOptional(this.specifiedOptional(basic));
		this.setSpecifiedEnumerated(this.specifiedEnumerated(basic));
		this.setTemporal(this.specifiedTemporal(basic));
		this.setLob(this.specifiedLob(basic));
		this.column.update(basic);
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
		Basic basic = OrmFactory.eINSTANCE.createBasic();
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
}
