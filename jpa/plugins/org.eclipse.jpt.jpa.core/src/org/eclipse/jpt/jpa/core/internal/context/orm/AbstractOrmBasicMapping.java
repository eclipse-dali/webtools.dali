/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.FetchType;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmLobConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlBasic;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> basic mapping
 */
public abstract class AbstractOrmBasicMapping<X extends XmlBasic>
	extends AbstractOrmAttributeMapping<X>
	implements OrmBasicMapping
{
	protected final OrmSpecifiedColumn column;

	protected FetchType specifiedFetch;
	protected FetchType defaultFetch = DEFAULT_FETCH_TYPE;

	protected Boolean specifiedOptional;
	protected boolean defaultOptional = DEFAULT_OPTIONAL;

	protected OrmConverter converter;  // never null

	protected final OrmConverter nullConverter = new NullOrmConverter(this);

	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseEnumeratedConverter.BasicAdapter.instance(),
		OrmBaseTemporalConverter.BasicAdapter.instance(),
		OrmLobConverter.Adapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);


	protected AbstractOrmBasicMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.column = this.buildColumn();
		this.specifiedFetch = this.buildSpecifiedFetch();
		this.specifiedOptional = this.buildSpecifiedOptional();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.column.synchronizeWithResourceModel(monitor);
		this.setSpecifiedFetch_(this.buildSpecifiedFetch());
		this.setSpecifiedOptional_(this.buildSpecifiedOptional());
		this.syncConverter(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.column.update(monitor);
		this.setDefaultFetch(this.buildDefaultFetch());
		this.setDefaultOptional(this.buildDefaultOptional());
		this.converter.update(monitor);
	}


	// ********** column **********

	public OrmSpecifiedColumn getColumn() {
		return this.column;
	}

	protected OrmSpecifiedColumn buildColumn() {
		return this.getContextModelFactory().buildOrmColumn(this);
	}


	// ********** fetch **********

	public FetchType getFetch() {
		return (this.specifiedFetch != null) ? this.specifiedFetch : this.defaultFetch;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}

	public void setSpecifiedFetch(FetchType fetch) {
		this.setSpecifiedFetch_(fetch);
		this.xmlAttributeMapping.setFetch(FetchType.toOrmResourceModel(fetch));
	}

	protected void setSpecifiedFetch_(FetchType fetch) {
		FetchType old = this.specifiedFetch;
		this.specifiedFetch = fetch;
		this.firePropertyChanged(SPECIFIED_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildSpecifiedFetch() {
		return FetchType.fromOrmResourceModel(this.xmlAttributeMapping.getFetch());
	}

	public FetchType getDefaultFetch() {
		return this.defaultFetch;
	}

	protected void setDefaultFetch(FetchType fetch) {
		FetchType old = this.defaultFetch;
		this.defaultFetch = fetch;
		this.firePropertyChanged(DEFAULT_FETCH_PROPERTY, old, fetch);
	}

	protected FetchType buildDefaultFetch() {
		return DEFAULT_FETCH_TYPE;
	}


	// ********** optional **********

	public boolean isOptional() {
		return (this.specifiedOptional != null) ? this.specifiedOptional.booleanValue() : this.isDefaultOptional();
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}

	public void setSpecifiedOptional(Boolean optional) {
		this.setSpecifiedOptional_(optional);
		this.xmlAttributeMapping.setOptional(optional);
	}

	protected void setSpecifiedOptional_(Boolean optional) {
		Boolean old = this.specifiedOptional;
		this.specifiedOptional = optional;
		this.firePropertyChanged(SPECIFIED_OPTIONAL_PROPERTY, old, optional);
	}

	protected Boolean buildSpecifiedOptional() {
		return this.xmlAttributeMapping.getOptional();
	}

	public boolean isDefaultOptional() {
		return this.defaultOptional;
	}

	protected void setDefaultOptional(boolean optional) {
		boolean old = this.defaultOptional;
		this.defaultOptional = optional;
		this.firePropertyChanged(DEFAULT_OPTIONAL_PROPERTY, old, optional);
	}

	protected boolean buildDefaultOptional() {
		return DEFAULT_OPTIONAL;
	}


	// ********** converter **********

	public OrmConverter getConverter() {
		return this.converter;
	}

	public void setConverter(Class<? extends Converter> converterType) {
		if (this.converter.getConverterType() != converterType) {
			// Make the old value is the real old one when firing property changed event below
			OrmConverter old = this.converter;
			// Set the new value of the converter to a NullOrmConverter to prevent the following
			// step from synchronizing through a separate thread when setting converters to null
			// Through this way timing issue between different thread may be eliminated.
			this.converter = this.nullConverter;
			// note: we may also clear the XML value we want;
			// but if we leave it, the resulting sync will screw things up...
			this.clearXmlConverterValues();
			OrmConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.converter = this.buildConverter(converterAdapter);
			this.converter.initialize();
			this.firePropertyChanged(CONVERTER_PROPERTY, old, this.converter);
		}
	}

	protected OrmConverter buildConverter(OrmConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getContextModelFactory()) :
				this.nullConverter;
	}

	protected void setConverter_(OrmConverter converter) {
		Converter old = this.converter;
		this.converter = converter;
		this.firePropertyChanged(CONVERTER_PROPERTY, old, converter);
	}

	protected void clearXmlConverterValues() {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			adapter.clearXmlValue(this.xmlAttributeMapping);
		}
	}

	protected OrmConverter buildConverter() {
		OrmXmlContextModelFactory factory = this.getContextModelFactory();
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			OrmConverter ormConverter = adapter.buildConverter(this, factory);
			if (ormConverter != null) {
				return ormConverter;
			}
		}
		return this.nullConverter;
	}

	protected void syncConverter(IProgressMonitor monitor) {
		OrmConverter.Adapter adapter = this.getXmlConverterAdapter();
		if (adapter == null) {
			if (this.converter.getConverterType() != null) {
				this.setConverter_(this.nullConverter);
			}
		} else {
			if (this.converter.getConverterType() == adapter.getConverterType()) {
				this.converter.synchronizeWithResourceModel(monitor);
			} else {
				this.setConverter_(adapter.buildNewConverter(this, this.getContextModelFactory()));
			}
		}
	}

	/**
	 * Return the first adapter whose converter value is set in the XML mapping.
	 * Return <code>null</code> if there are no converter values in the XML.
	 */
	protected OrmConverter.Adapter getXmlConverterAdapter() {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.isActive(this.xmlAttributeMapping)) {
				return adapter;
			}
		}
		return null;
	}


	// ********** converter adapters **********

	/**
	 * Return the converter adapter for the specified converter type.
	 */
	protected OrmConverter.Adapter getConverterAdapter(Class<? extends Converter> converterType) {
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			if (adapter.getConverterType() == converterType) {
				return adapter;
			}
		}
		return null;
	}

	protected Iterable<OrmConverter.Adapter> getConverterAdapters() {
		return CONVERTER_ADAPTERS;
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmBasicMapping(this);
	}

	@Override
	protected void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		this.column.initializeFrom(oldMapping.getColumn());
	}

	public int getXmlSequence() {
		return 20;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public void addXmlAttributeMappingTo(Attributes xmlAttributes) {
		xmlAttributes.getBasics().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes xmlAttributes) {
		xmlAttributes.getBasics().remove(this.xmlAttributeMapping);
	}


	// ********** column parent adapter **********

	public JpaContextModel getColumnParent() {
		return this;  // no adapter
	}

	public String getDefaultColumnName(NamedColumn col) {
		return this.name;
	}

	public String getDefaultTableName() {
		return this.getTypeMapping().getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getTypeMapping().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterable<String> getCandidateTableNames() {
		return this.getTypeMapping().getAllAssociatedTableNames();
	}

	public XmlColumn getXmlColumn() {
		return this.xmlAttributeMapping.getColumn();
	}

	public XmlColumn buildXmlColumn() {
		XmlColumn xmlColumn = OrmFactory.eINSTANCE.createXmlColumn();
		this.xmlAttributeMapping.setColumn(xmlColumn);
		return xmlColumn;
	}

	public void removeXmlColumn() {
		this.xmlAttributeMapping.setColumn(null);
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.column.validate(messages, reporter);
		this.converter.validate(messages, reporter);
	}
	
	public JpaValidator buildColumnValidator(NamedColumn col) {
		return new NamedColumnValidator(this.getPersistentAttribute(), (BaseColumn) col, new EntityTableDescriptionProvider());
	}
	
	// ********** completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.column.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		result = this.converter.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}
