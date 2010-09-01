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
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.NamedColumnValidator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.jpa2.context.IdMapping2_0;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.osgi.util.NLS;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmIdMapping<T extends XmlId>
	extends AbstractOrmAttributeMapping<T>
	implements OrmIdMapping, IdMapping2_0
{
	protected final OrmColumn column;
	
	/* 2.0 feature - a relationship may map this id */
	protected boolean mappedByRelationship;
	
	protected OrmGeneratedValue generatedValue;
	
	protected OrmConverter converter;
	
	protected final OrmConverter nullConverter;
	
	protected final OrmGeneratorContainer generatorContainer;
	
	
	protected AbstractOrmIdMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.column = getXmlContextNodeFactory().buildOrmColumn(this, this);
		this.column.initialize(this.resourceAttributeMapping.getColumn());//TODO pass in to constructor
		this.mappedByRelationship = calculateMappedByRelationship();
		this.generatorContainer = buildGeneratorContainer();
		this.initializeGeneratedValue();
		this.nullConverter = this.getXmlContextNodeFactory().buildOrmNullConverter(this);
		this.converter = this.buildConverter(this.getResourceConverterType());
	}
	
	
	protected OrmGeneratorContainer buildGeneratorContainer() {
		return getXmlContextNodeFactory().buildOrmGeneratorContainer(this, this.resourceAttributeMapping);
	}
	
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
	public void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		getColumn().initializeFrom(oldMapping.getColumn());
	}
	
	public OrmColumn getColumn() {
		return this.column;
	}
	
	public OrmConverter getConverter() {
		return this.converter;
	}
	
	protected String getConverterType() {
		return this.converter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		OrmConverter oldConverter = this.converter;
		OrmConverter newConverter = buildConverter(converterType);
		this.converter = this.nullConverter;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.converter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(OrmConverter newConverter) {
		OrmConverter oldConverter = this.converter;
		this.converter = newConverter;
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	public OrmGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists"); //$NON-NLS-1$
		}
		XmlGeneratedValue resourceGeneratedValue = OrmFactory.eINSTANCE.createXmlGeneratedValue();
		this.generatedValue = buildGeneratedValue(resourceGeneratedValue);
		this.resourceAttributeMapping.setGeneratedValue(resourceGeneratedValue);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed"); //$NON-NLS-1$
		}
		OrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.resourceAttributeMapping.setGeneratedValue(null);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public OrmGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(OrmGeneratedValue newGeneratedValue) {
		OrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}
	
	public OrmGeneratorContainer getGeneratorContainer() {
		return this.generatorContainer;
	}
	
	@Override
	public String getPrimaryKeyColumnName() {
		return this.getColumn().getName();
	}
	
	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getIds().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getIds().remove(this.resourceAttributeMapping);
	}
	
	
	//***************** XmlColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.resourceAttributeMapping.getColumn();
	}
	
	protected boolean isColumnSpecified() {
		if (! isVirtual()) {
			return getResourceColumn() != null;
		}
		else {
			return getJavaResourcePersistentAttribute().getAnnotation(JPA.COLUMN) != null;
		}
	}
	
	public void addResourceColumn() {
		this.resourceAttributeMapping.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
	}
	
	public void removeResourceColumn() {
		this.resourceAttributeMapping.setColumn(null);
	}
	
	
	//************** NamedColumn.Owner implementation ***************
	
	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}
	
	public String getDefaultColumnName() {		
		return (isMappedByRelationship() && ! isColumnSpecified()) ? null : getName();
	}
	
	
	//************** BaseColumn.Owner implementation ***************
	
	public String getDefaultTableName() {
		return (isMappedByRelationship() && ! isColumnSpecified()) ? null : getTypeMapping().getPrimaryTableName();
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return getTypeMapping().tableNameIsInvalid(tableName);
	}
	
	public Iterator<String> candidateTableNames() {
		return getTypeMapping().associatedTableNamesIncludingInherited();
	}
	
	
	// **************** IdColumn2_0 impl **************************************
	
	public boolean isMappedByRelationship() {
		return this.mappedByRelationship;
	}
	
	protected void setMappedByRelationship(boolean newValue) {
		boolean oldValue = this.mappedByRelationship;
		this.mappedByRelationship = newValue;
		firePropertyChanged(MAPPED_BY_RELATIONSHIP_PROPERTY, oldValue, newValue);
	}
	
	protected boolean calculateMappedByRelationship() {
		for (SingleRelationshipMapping2_0 each : getMapsIdRelationships()) {
			if (each.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getResolvedAttributeMappingValue() == this) {
				return true;
			}
		}
		return false;
	}
	
	protected Iterable<SingleRelationshipMapping2_0> getMapsIdRelationships() {
		return new FilteringIterable<SingleRelationshipMapping2_0>(
				new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(
					new CompositeIterable<AttributeMapping>(
						getTypeMapping().getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
						getTypeMapping().getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)))) {
			@Override
			protected boolean accept(SingleRelationshipMapping2_0 o) {
				return o.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
			}
		};
	}
	
	
	protected void initializeGeneratedValue() {
		if (this.resourceAttributeMapping.getGeneratedValue() != null) {
			this.generatedValue = buildGeneratedValue(this.resourceAttributeMapping.getGeneratedValue());
		}
	}
	
	protected OrmGeneratedValue buildGeneratedValue(XmlGeneratedValue resourceGeneratedValue) {
		return getXmlContextNodeFactory().buildOrmGeneratedValue(this, resourceGeneratedValue);
	}
	
	@Override
	public void update() {
		super.update();
		this.column.update(getResourceColumn());
		setMappedByRelationship(calculateMappedByRelationship());
		this.generatorContainer.update();
		this.updateGeneratedValue();
		if (this.valuesAreEqual(getResourceConverterType(), getConverterType())) {
			getConverter().update();
		}
		else {
			setConverter(buildConverter(getResourceConverterType()));
		}
	}
	
	protected void updateGeneratedValue() {
		if (this.resourceAttributeMapping.getGeneratedValue() == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(buildGeneratedValue(this.resourceAttributeMapping.getGeneratedValue()));
			}
			else {
				getGeneratedValue().update(this.resourceAttributeMapping.getGeneratedValue());
			}
		}
	}
	
	protected OrmConverter buildConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.NO_CONVERTER)) {
			return this.nullConverter;
		}
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getXmlContextNodeFactory().buildOrmTemporalConverter(this, this.resourceAttributeMapping);
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.resourceAttributeMapping.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		return Converter.NO_CONVERTER;
	}


	//************ refactoring ************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createConverterRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenameTypeEdits(IType originalType, String newName) {
		if (getConverter() != null) {
			return getConverter().createRenameTypeEdits(originalType, newName);
		}
		return EmptyIterable.instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createConverterMoveTypeEdits(originalType, newPackage));
	}
	
	protected Iterable<ReplaceEdit> createConverterMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		if (getConverter() != null) {
			return getConverter().createMoveTypeEdits(originalType, newPackage);
		}
		return EmptyIterable.instance();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createConverterRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createConverterRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		if (getConverter() != null) {
			return getConverter().createRenamePackageEdits(originalPackage, newName);
		}
		return EmptyIterable.instance();
	}
	
	
	// ****************** validation ****************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		// [JPA 2.0] if the column is specified, or if the id is not mapped by a relationship,
		// then the column is validated.
		// (In JPA 1.0, the column will always be validated, since the id is never mapped by a
		//  relationship)
		if (isColumnSpecified() || ! isMappedByRelationship()) {
			getColumn().validate(messages, reporter);
		}
		
		// [JPA 2.0] if the column is specified and the id is mapped by a relationship, 
		// then that is an error
		// (In JPA 1.0, this will never be the case, since the id is never mapped by a relationship)
		if (isColumnSpecified() && isMappedByRelationship()) {
			messages.add(
					buildMessage(
						JpaValidationMessages.ID_MAPPING_MAPPED_BY_RELATIONSHIP_AND_COLUMN_SPECIFIED,
						new String[] {},
						getColumn().getValidationTextRange()));
		}
		
		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
	}
	
	public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
		return new NamedColumnValidator(getPersistentAttribute(), (BaseColumn) column, (BaseColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
	}
	
	/* TODO - move to AbstractOrmAttributeMapping? */
	protected IMessage buildMessage(String msgID, String[] params, TextRange textRange) {
		String attributeDescString;
		PersistentAttribute attribute = getPersistentAttribute();
		if (attribute.isVirtual()) {
			attributeDescString = NLS.bind(JpaValidationDescriptionMessages.VIRTUAL_ATTRIBUTE_DESC, attribute.getName());
		}
		else {
			attributeDescString = NLS.bind(JpaValidationDescriptionMessages.ATTRIBUTE_DESC, attribute.getName());
		}
		return DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY, msgID, ArrayTools.add(params, 0, attributeDescString), this, textRange);
	}
}
