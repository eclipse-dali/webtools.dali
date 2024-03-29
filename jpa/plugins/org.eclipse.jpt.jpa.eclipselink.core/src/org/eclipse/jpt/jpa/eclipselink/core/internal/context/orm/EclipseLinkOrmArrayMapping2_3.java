/*******************************************************************************
 * Copyright (c) 2011, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmLobConverter;
import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlContextModelFactory;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.NullOrmConverter;
import org.eclipse.jpt.jpa.core.resource.orm.Attributes;
import org.eclipse.jpt.jpa.core.resource.orm.OrmFactory;
import org.eclipse.jpt.jpa.core.resource.orm.XmlColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkArrayMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConvertibleMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.XmlAttributes_2_3;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

//TODO canonical metamodel generation, need to override getMetamodelTypeName() and use the target-class element
public class EclipseLinkOrmArrayMapping2_3
	extends AbstractOrmAttributeMapping<XmlArray>
	implements EclipseLinkArrayMapping2_3, EclipseLinkOrmConvertibleMapping, OrmColumnMapping
{
	protected final OrmSpecifiedColumn column;

	protected OrmConverter converter;  // never null

	protected final OrmConverter nullConverter = new NullOrmConverter(this);

	protected static final OrmConverter.Adapter[] CONVERTER_ADAPTER_ARRAY = new OrmConverter.Adapter[] {
		OrmBaseEnumeratedConverter.BasicAdapter.instance(),
		OrmBaseTemporalConverter.BasicAdapter.instance(),
		OrmLobConverter.Adapter.instance(),
		EclipseLinkOrmConvert.Adapter.instance()
	};
	protected static final Iterable<OrmConverter.Adapter> CONVERTER_ADAPTERS = IterableTools.iterable(CONVERTER_ADAPTER_ARRAY);

	protected final EclipseLinkOrmConverterContainer converterContainer;


	public EclipseLinkOrmArrayMapping2_3(OrmSpecifiedPersistentAttribute parent, XmlArray xmlMapping) {
		super(parent, xmlMapping);
		this.column = this.buildColumn();
		this.converterContainer = this.buildConverterContainer();
		this.converter = this.buildConverter();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.column.synchronizeWithResourceModel(monitor);
		this.converterContainer.synchronizeWithResourceModel(monitor);
		this.syncConverter(monitor);
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.column.update(monitor);
		this.converterContainer.update(monitor);
		this.converter.update(monitor);
	}


	// ********** attribute type **********

	@Override
	protected String buildSpecifiedAttributeType() {
		return this.xmlAttributeMapping.getAttributeType();
	}

	@Override
	protected void setSpecifiedAttributeTypeInXml(String attributeType) {
		this.xmlAttributeMapping.setAttributeType(attributeType);
	}


	// ********** column **********

	public OrmSpecifiedColumn getColumn() {
		return this.column;
	}

	protected OrmSpecifiedColumn buildColumn() {
		return this.getContextModelFactory().buildOrmColumn(this);
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

	// ********** converters **********

	public EclipseLinkOrmConverterContainer getConverterContainer() {
		return this.converterContainer;
	}

	protected EclipseLinkOrmConverterContainer buildConverterContainer() {
		return new EclipseLinkOrmMappingConverterContainer(this, this.xmlAttributeMapping);
	}

	public int getMaximumAllowedConverters() {
		return 2; //TODO EclipseLink supports 2, but really should be just 1, see bug 365114
	}


	// ********** misc **********

	public String getKey() {
		return EclipseLinkMappingKeys.ARRAY_ATTRIBUTE_MAPPING_KEY;
	}

	public int getXmlSequence() {
		return 110;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	@Override
	protected void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		this.column.initializeFrom(oldMapping.getColumn());
	}

	public void addXmlAttributeMappingTo(Attributes xmlAttributes) {
		((XmlAttributes_2_3) xmlAttributes).getArrays().add(this.xmlAttributeMapping);
	}

	public void removeXmlAttributeMappingFrom(Attributes xmlAttributes) {
		((XmlAttributes_2_3) xmlAttributes).getArrays().remove(this.xmlAttributeMapping);
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


	//************ refactoring ************

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return IterableTools.concatenate(
			super.createMoveTypeEdits(originalType, newPackage),
			this.converterContainer.createMoveTypeEdits(originalType, newPackage)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return IterableTools.concatenate(
			super.createRenamePackageEdits(originalPackage, newName),
			this.converterContainer.createRenamePackageEdits(originalPackage, newName)
		);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return IterableTools.concatenate(
			super.createRenameTypeEdits(originalType, newName),
			this.converterContainer.createRenameTypeEdits(originalType, newName)
		);
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
		result = this.converterContainer.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.targetClassTouches(pos)) {
			return this.getCandidateTargetClassNames();
		}
		if (this.attributeTypeTouches(pos)) {
			return this.getcandidateAttributeTypeNames();
		}
		return null;
	}
	
	protected boolean attributeTypeTouches(int pos) {
		return this.xmlAttributeMapping.attributeTypeTouches(pos);
	}

	protected boolean targetClassTouches(int pos) {
		return this.xmlAttributeMapping.targetClassTouches(pos);
	}
	
	@SuppressWarnings("unchecked")
	protected Iterable<String> getCandidateTargetClassNames() {
		return IterableTools.concatenate(
				JavaProjectTools.getJavaClassNames(getJavaProject()),
				MappingTools.getPrimaryBasicTypeNames(),
				IterableTools.sort(((EclipseLinkPersistenceUnit) this.getPersistenceUnit()).getEclipseLinkDynamicPersistentTypeNames())
				);
	}
	
	protected Iterable<String> getcandidateAttributeTypeNames() {
		return MappingTools.getCollectionTypeNames();
	}
}
