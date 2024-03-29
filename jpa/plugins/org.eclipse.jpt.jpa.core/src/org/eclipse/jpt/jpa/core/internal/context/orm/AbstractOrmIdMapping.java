/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.jpa.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.jpa.core.resource.orm.XmlId;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationArgumentMessages;
import org.eclipse.jpt.jpa.core.validation.JptJpaCoreValidationMessages;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.osgi.util.NLS;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ID mapping
 */
public abstract class AbstractOrmIdMapping<X extends XmlId>
	extends AbstractOrmAttributeMapping<X>
	implements OrmIdMapping, IdMapping2_0
{
	protected final OrmSpecifiedColumn column;

	protected final OrmGeneratorContainer generatorContainer;

	protected OrmGeneratedValue generatedValue;

	protected OrmConverter converter;  // never null

	/* JPA 2.0 - the embedded id may be derived from a relationship */
	protected boolean derived;

	protected final OrmConverter nullConverter = new NullOrmConverter(this);

	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseTemporalConverter.BasicAdapter.instance(),
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);


	protected AbstractOrmIdMapping(OrmSpecifiedPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.column = this.buildColumn();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.column.synchronizeWithResourceModel(monitor);
		this.generatorContainer.synchronizeWithResourceModel(monitor);
		this.syncGeneratedValue(monitor);
		this.syncConverter(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.column.update(monitor);
		this.generatorContainer.update(monitor);
		if (this.generatedValue != null) {
			this.generatedValue.update(monitor);
		}
		this.converter.update(monitor);
		this.setDerived(this.buildDerived());
	}


	// ********** column **********

	public OrmSpecifiedColumn getColumn() {
		return this.column;
	}

	protected OrmSpecifiedColumn buildColumn() {
		return this.getContextModelFactory().buildOrmColumn(this);
	}


	// ********** generator container **********

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return this.getContextModelFactory().buildOrmGeneratorContainer(this, this.xmlAttributeMapping);
	}

	@Override
	public Iterable<Generator> getGenerators() {
		return this.generatorContainer.getGenerators();
	}


	// ********** generated value **********

	public OrmGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}

	public OrmGeneratedValue addGeneratedValue() {
		if (this.generatedValue != null) {
			throw new IllegalStateException("generated value already exists: " + this.generatedValue); //$NON-NLS-1$
		}
		XmlGeneratedValue xmlGeneratedValue = this.buildXmlGeneratedValue();
		OrmGeneratedValue value = this.buildGeneratedValue(xmlGeneratedValue);
		this.setGeneratedValue(value);
		this.xmlAttributeMapping.setGeneratedValue(xmlGeneratedValue);
		return value;
	}

	protected XmlGeneratedValue buildXmlGeneratedValue() {
		return OrmFactory.eINSTANCE.createXmlGeneratedValue();
	}

	public void removeGeneratedValue() {
		if (this.generatedValue == null) {
			throw new IllegalStateException("generated value does not exist"); //$NON-NLS-1$
		}
		this.setGeneratedValue(null);
		this.xmlAttributeMapping.setGeneratedValue(null);
	}

	protected void setGeneratedValue(OrmGeneratedValue value) {
		OrmGeneratedValue old = this.generatedValue;
		this.generatedValue = value;
		this.firePropertyChanged(GENERATED_VALUE_PROPERTY, old, value);
	}

	protected OrmGeneratedValue buildGeneratedValue() {
		XmlGeneratedValue xmlGeneratedValue = this.xmlAttributeMapping.getGeneratedValue();
		return (xmlGeneratedValue == null) ? null : this.buildGeneratedValue(xmlGeneratedValue);
	}

	protected OrmGeneratedValue buildGeneratedValue(XmlGeneratedValue xmlGeneratedValue) {
		return this.getContextModelFactory().buildOrmGeneratedValue(this, xmlGeneratedValue);
	}

	protected void syncGeneratedValue(IProgressMonitor monitor) {
		XmlGeneratedValue xmlGeneratedValue = this.xmlAttributeMapping.getGeneratedValue();
		if (xmlGeneratedValue == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		} else {
			if ((this.generatedValue != null) && (this.generatedValue.getXmlGeneratedValue() == xmlGeneratedValue)) {
				this.generatedValue.synchronizeWithResourceModel(monitor);
			} else {
				this.setGeneratedValue(this.buildGeneratedValue(xmlGeneratedValue));
			}
		}
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


	// ********** derived **********

	public boolean isDerived() {
		return this.derived;
	}

	protected void setDerived(boolean derived) {
		boolean old = this.derived;
		this.derived = derived;
		this.firePropertyChanged(DERIVED_PROPERTY, old, derived);
	}

	protected boolean buildDerived() {
		return this.isJpa2_0Compatible() && this.buildDerived_();
	}

	protected boolean buildDerived_() {
		return this.getTypeMapping().attributeIsDerivedId(this.name);
	}


	// ********** misc **********

	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 0;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmIdMapping(this);
	}

	@Override
	protected void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		this.column.initializeFrom(oldMapping.getColumn());
	}

	@Override
	public String getPrimaryKeyColumnName() {
		return this.column.getName();
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public void addXmlAttributeMappingTo(Attributes resourceAttributes) {
		resourceAttributes.getIds().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes resourceAttributes) {
		resourceAttributes.getIds().remove(this.xmlAttributeMapping);
	}


	// ********** column parent adapter **********

	public JpaContextModel getColumnParent() {
		return this;  // no adapter
	}

	public String getDefaultColumnName(NamedColumn col) {
		return (this.derived && ! this.isColumnSpecified()) ? null : this.name;
	}

	public String getDefaultTableName() {
		return (this.derived && ! this.isColumnSpecified()) ? null : this.getTypeMapping().getPrimaryTableName();
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

	protected boolean isColumnSpecified() {
		return this.getXmlColumn() != null;
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// JPA 2.0: If the column is specified or if the ID is not mapped by a relationship,
		// the column is validated.
		// JPA 1.0: The column is always be validated, since the ID is never mapped by a
		// relationship.
		if (this.isColumnSpecified() || ! this.derived) {
			this.column.validate(messages, reporter);
		}

		// JPA 2.0: If the column is specified and the ID is mapped by a relationship,
		// we have an error.
		// JPA 1.0: The ID cannot be mapped by a relationship.
		if (this.isColumnSpecified() && this.derived) {
			messages.add(this.buildColumnSpecifiedAndDerivedMessage());
		}

		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
		this.converter.validate(messages, reporter);
	}

	protected IMessage buildColumnSpecifiedAndDerivedMessage() {
		return this.buildValidationMessage(
				this.column.getValidationTextRange(),
				JptJpaCoreValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
				this.buildAttributeDescription()
			);
	}

	protected String buildAttributeDescription() {
		return NLS.bind(this.getAttributeDescriptionTemplate(), this.getPersistentAttribute().getName());
	}

	protected String getAttributeDescriptionTemplate() {
		return JptJpaCoreValidationArgumentMessages.ATTRIBUTE_DESC;
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
		result = this.generatorContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.generatedValue != null) {
			result = this.generatedValue.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		result = this.converter.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		return null;
	}
}
