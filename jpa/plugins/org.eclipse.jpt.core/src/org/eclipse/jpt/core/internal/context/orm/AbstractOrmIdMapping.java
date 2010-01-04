/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.List;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGeneratorContainer;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public abstract class AbstractOrmIdMapping<T extends XmlId>
	extends AbstractOrmAttributeMapping<T>
	implements OrmIdMapping
{
	protected final OrmColumn column;

	protected OrmGeneratedValue generatedValue;
	
	protected OrmConverter specifiedConverter;
	
	protected final OrmGeneratorContainer generatorContainer;

	
	protected AbstractOrmIdMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.column = getXmlContextNodeFactory().buildOrmColumn(this, this);
		this.column.initialize(this.resourceAttributeMapping.getColumn());//TODO pass in to constructor
		this.generatorContainer = buildGeneratorContainer();
		this.initializeGeneratedValue();
		this.specifiedConverter = this.buildConverter(this.getResourceConverterType());
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
		return this.specifiedConverter;
	}
	
	protected String getConverterType() {
		return this.specifiedConverter.getType();
	}
	
	public void setConverter(String converterType) {
		if (this.valuesAreEqual(getConverterType(), converterType)) {
			return;
		}
		OrmConverter oldConverter = this.specifiedConverter;
		OrmConverter newConverter = buildConverter(converterType);
		this.specifiedConverter = null;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.specifiedConverter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setConverter(OrmConverter newConverter) {
		OrmConverter oldConverter = this.specifiedConverter;
		this.specifiedConverter = newConverter;
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

	@Override
	public boolean isIdMapping() {
		return true;
	}
	
	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getIds().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getIds().remove(this.resourceAttributeMapping);
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	public String getDefaultColumnName() {		
		return getName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
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
			return getXmlContextNodeFactory().buildOrmNullConverter(this);
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

	//***************** XmlColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.resourceAttributeMapping.getColumn();
	}
	
	public void addResourceColumn() {
		this.resourceAttributeMapping.setColumn(OrmFactory.eINSTANCE.createXmlColumn());
	}
	
	public void removeResourceColumn() {
		this.resourceAttributeMapping.setColumn(null);
	}
	
	// ****************** validation ****************

	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		
		if (this.shouldValidateAgainstDatabase()) {
			this.validateColumn(messages);
		}
		if (this.generatedValue != null) {
			this.generatedValue.validate(messages, reporter);
		}
		this.generatorContainer.validate(messages, reporter);
	}
	
	protected void validateColumn(List<IMessage> messages) {
		OrmPersistentAttribute pa = this.getPersistentAttribute();
		String tableName = this.column.getTable();
		if (this.getTypeMapping().tableNameIsInvalid(tableName)) {
			if (pa.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {pa.getName(), tableName, this.column.getName()},
						this.column, 
						this.column.getTableTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {tableName, this.column.getName()}, 
						this.column, 
						this.column.getTableTextRange()
					)
				);
			}
			return;
		}
		
		if ( ! this.column.isResolved() && this.column.getDbTable() != null) {
			if (pa.isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {pa.getName(), this.column.getName()}, 
						this.column, 
						this.column.getNameTextRange()
					)
				);
			} else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {this.column.getName()}, 
						this.column, 
						this.column.getNameTextRange()
					)
				);
			}
		}
	}

}
