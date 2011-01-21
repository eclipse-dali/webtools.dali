/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTemporalConverter;
import org.eclipse.jpt.core.context.orm.OrmXmlContextNodeFactory;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.TypeMappingTools;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.osgi.util.NLS;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

/**
 * <code>orm.xml</code> ID mapping
 */
public abstract class AbstractOrmIdMapping<X extends XmlId>
	extends AbstractOrmAttributeMapping<X>
	implements OrmIdMapping, IdMapping2_0
{
	protected final OrmColumn column;

	protected final OrmGeneratorContainer generatorContainer;

	protected OrmGeneratedValue generatedValue;

	protected OrmConverter converter;  // never null

	/* 2.0 feature - a relationship may map this ID */
	protected boolean mappedByRelationship;


	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmTemporalConverter.Adapter.instance(),
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = new ArrayIterable<OrmConverter.Adapter>(CONVERTER_ADAPTER_ARRAY);


	protected AbstractOrmIdMapping(OrmPersistentAttribute parent, X xmlMapping) {
		super(parent, xmlMapping);
		this.column = this.buildColumn();
		this.generatorContainer = this.buildGeneratorContainer();
		this.generatedValue = this.buildGeneratedValue();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.column.synchronizeWithResourceModel();
		this.generatorContainer.synchronizeWithResourceModel();
		this.syncGeneratedValue();
		this.syncConverter();
	}

	@Override
	public void update() {
		super.update();
		this.column.update();
		this.generatorContainer.update();
		if (this.generatedValue != null) {
			this.generatedValue.update();
		}
		this.converter.update();
		this.setMappedByRelationship(this.buildMappedByRelationship());
	}


	// ********** column **********

	public OrmColumn getColumn() {
		return this.column;
	}

	protected OrmColumn buildColumn() {
		return this.getContextNodeFactory().buildOrmColumn(this, this);
	}


	// ********** generator container **********

	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}

	protected OrmGeneratorContainer buildGeneratorContainer() {
		return this.getContextNodeFactory().buildOrmGeneratorContainer(this, this.xmlAttributeMapping);
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
		return this.getContextNodeFactory().buildOrmGeneratedValue(this, xmlGeneratedValue);
	}

	protected void syncGeneratedValue() {
		XmlGeneratedValue xmlGeneratedValue = this.xmlAttributeMapping.getGeneratedValue();
		if (xmlGeneratedValue == null) {
			if (this.generatedValue != null) {
				this.setGeneratedValue(null);
			}
		} else {
			if ((this.generatedValue != null) && (this.generatedValue.getXmlGeneratedValue() == xmlGeneratedValue)) {
				this.generatedValue.synchronizeWithResourceModel();
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
		if (this.converter.getType() != converterType) {
			// note: we may also clear the XML value we want;
			// but if we leave it, the resulting sync will screw things up...
			this.clearXmlConverterValues();
			OrmConverter.Adapter converterAdapter = this.getConverterAdapter(converterType);
			this.setConverter_(this.buildConverter(converterAdapter));
			this.converter.initialize();
		}
	}

	protected OrmConverter buildConverter(OrmConverter.Adapter converterAdapter) {
		 return (converterAdapter != null) ?
				converterAdapter.buildNewConverter(this, this.getContextNodeFactory()) :
				this.buildNullConverter();
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
		OrmXmlContextNodeFactory factory = this.getContextNodeFactory();
		for (OrmConverter.Adapter adapter : this.getConverterAdapters()) {
			OrmConverter ormConverter = adapter.buildConverter(this, factory);
			if (ormConverter != null) {
				return ormConverter;
			}
		}
		return this.buildNullConverter();
	}

	protected void syncConverter() {
		OrmConverter.Adapter adapter = this.getXmlConverterAdapter();
		if (adapter == null) {
			if (this.converter.getType() != null) {
				this.setConverter_(this.buildNullConverter());
			}
		} else {
			if (this.converter.getType() == adapter.getConverterType()) {
				this.converter.synchronizeWithResourceModel();
			} else {
				this.setConverter_(adapter.buildNewConverter(this, this.getContextNodeFactory()));
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

	protected OrmConverter buildNullConverter() {
		return new NullOrmConverter(this);
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


	// ********** mapped by relationship **********

	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}

	protected void setMappedByRelationship(boolean value) {
		boolean old = this.mappedByRelationship;
		this.mappedByRelationship = value;
		this.firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, old, value);
	}

	protected boolean buildMappedByRelationship() {
		return this.isJpa2_0Compatible() && this.buildMappedByRelationship_();
	}

	protected boolean buildMappedByRelationship_() {
		return CollectionTools.contains(this.getMappedByRelationshipAttributeNames(), this.getName());
	}

	protected Iterable<String> getMappedByRelationshipAttributeNames() {
		return TypeMappingTools.getMappedByRelationshipAttributeNames(this.getTypeMapping());
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


	// ********** OrmColumn.Owner implementation **********

	public String getDefaultColumnName() {
		return (this.mappedByRelationship && ! this.isColumnSpecified()) ? null : this.name;
	}

	public String getDefaultTableName() {
		return (this.mappedByRelationship && ! this.isColumnSpecified()) ? null : this.getTypeMapping().getPrimaryTableName();
	}

	public Table resolveDbTable(String tableName) {
		return this.getTypeMapping().resolveDbTable(tableName);
	}

	public boolean tableNameIsInvalid(String tableName) {
		return this.getTypeMapping().tableNameIsInvalid(tableName);
	}

	public Iterator<String> candidateTableNames() {
		return this.getTypeMapping().allAssociatedTableNames();
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


	// ********** refactoring **********

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenameTypeEdits(originalType, newName),
				this.createConverterRenameTypeEdits(originalType, newName)
			);
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		return (this.converter != null) ?
				this.converter.createRenameTypeEdits(originalType, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
				super.createMoveTypeEdits(originalType, newPackage),
				this.createConverterMoveTypeEdits(originalType, newPackage)
			);
	}

	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return (this.converter != null) ?
				this.converter.createMoveTypeEdits(originalType, newPackage) :
				EmptyIterable.<ReplaceEdit>instance();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
				super.createRenamePackageEdits(originalPackage, newName),
				this.createConverterRenamePackageEdits(originalPackage, newName)
			);
	}

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return (this.converter != null) ?
				this.converter.createRenamePackageEdits(originalPackage, newName) :
				EmptyIterable.<ReplaceEdit>instance();
	}


	// ********** validation **********

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);

		// [JPA 2.0] if the column is specified, or if the id is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the id is never mapped by a
		//  relationship)
		if (this.isColumnSpecified() || ! this.isMappedByRelationship()) {
			this.column.validate(messages, reporter);
		}

		// [JPA 2.0] if the column is specified and the id is mapped by a relationship,
		// then that is an error
		// (In JPA 1.0, this will never be the case, since the id is never mapped by a relationship)
		if (this.isColumnSpecified() && this.isMappedByRelationship()) {
			messages.add(this.buildMappedByRelationshipAndColumnSpecifiedMessage());
		}

		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
		this.converter.validate(messages, reporter);
	}

	protected IMessage buildMappedByRelationshipAndColumnSpecifiedMessage() {
		return this.buildMessage(
				JpaValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
				EMPTY_STRING_ARRAY,
				this.column.getValidationTextRange()
			);
	}

	protected IMessage buildMessage(String msgID, String[] parms, TextRange textRange) {
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				msgID,
				ArrayTools.add(parms, 0, this.buildAttributeDescription()),
				this,
				textRange
			);
	}

	protected String buildAttributeDescription() {
		return NLS.bind(this.getAttributeDescriptionTemplate(), this.getPersistentAttribute().getName());
	}

	protected String getAttributeDescriptionTemplate() {
		return this.getPersistentAttribute().isVirtual() ?
				JpaValidationDescriptionMessages.VIRTUAL_ATTRIBUTE_DESC :
				JpaValidationDescriptionMessages.ATTRIBUTE_DESC;
	}

	public JptValidator buildColumnValidator(NamedColumn col, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator(this.getPersistentAttribute(), (BaseColumn) col, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}
}
