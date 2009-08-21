/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;


public abstract class AbstractOrmVersionMapping<T extends XmlVersion>
	extends AbstractOrmAttributeMapping<T>
	implements OrmVersionMapping
{
	protected final OrmColumn column;

	protected OrmConverter defaultConverter;
	protected OrmConverter specifiedConverter;
	
	protected AbstractOrmVersionMapping(OrmPersistentAttribute parent, T resourceMapping) {
		super(parent, resourceMapping);
		this.column = getJpaFactory().buildOrmColumn(this, this);
		this.column.initialize(this.getResourceColumn());//TODO pass in to constructor
		this.defaultConverter = getJpaFactory().buildOrmNullConverter(this);
		this.specifiedConverter = this.buildSpecifiedConverter(this.getResourceConverterType());
	}

	public int getXmlSequence() {
		return 30;
	}

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmVersionMapping(this);
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
		return getSpecifiedConverter() == null ? getDefaultConverter() : getSpecifiedConverter();
	}
	
	public OrmConverter getDefaultConverter() {
		return this.defaultConverter;
	}
	
	public OrmConverter getSpecifiedConverter() {
		return this.specifiedConverter;
	}
	
	protected String getSpecifedConverterType() {
		if (this.specifiedConverter == null) {
			return Converter.NO_CONVERTER;
		}
		return this.specifiedConverter.getType();
	}
	
	public void setSpecifiedConverter(String converterType) {
		if (this.valuesAreEqual(getSpecifedConverterType(), converterType)) {
			return;
		}
		OrmConverter oldConverter = this.specifiedConverter;
		OrmConverter newConverter = buildSpecifiedConverter(converterType);
		this.specifiedConverter = null;
		if (oldConverter != null) {
			oldConverter.removeFromResourceModel();
		}
		this.specifiedConverter = newConverter;
		if (newConverter != null) {
			newConverter.addToResourceModel();
		}
		firePropertyChanged(SPECIFIED_CONVERTER_PROPERTY, oldConverter, newConverter);
	}
	
	protected void setSpecifiedConverter(OrmConverter newConverter) {
		OrmConverter oldConverter = this.specifiedConverter;
		this.specifiedConverter = newConverter;
		firePropertyChanged(SPECIFIED_CONVERTER_PROPERTY, oldConverter, newConverter);
	}	

	public void addToResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getVersions().add(this.resourceAttributeMapping);
	}
	
	public void removeFromResourceModel(Attributes resourceAttributes) {
		resourceAttributes.getVersions().remove(this.resourceAttributeMapping);
	}

	public String getDefaultColumnName() {		
		return getName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}
	
	@Override
	public void update() {
		super.update();
		this.column.update(this.getResourceColumn());
		if (this.valuesAreEqual(getResourceConverterType(), getSpecifedConverterType())) {
			getSpecifiedConverter().update();
		}
		else {
			setSpecifiedConverter(buildSpecifiedConverter(getResourceConverterType()));
		}
	}
	
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		if (this.valuesAreEqual(converterType, Converter.TEMPORAL_CONVERTER)) {
			return getJpaFactory().buildOrmTemporalConverter(this, this.resourceAttributeMapping);
		}
		return null;
	}
	
	protected String getResourceConverterType() {
		if (this.resourceAttributeMapping.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		
		return null;
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
