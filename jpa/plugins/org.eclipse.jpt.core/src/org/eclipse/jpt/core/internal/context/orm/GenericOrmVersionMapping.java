/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmVersionMapping extends AbstractOrmAttributeMapping<XmlVersion>
	implements OrmVersionMapping
{
	protected final OrmColumn column;

	protected OrmConverter defaultConverter;
	protected OrmConverter specifiedConverter;
	
	public GenericOrmVersionMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = getJpaFactory().buildOrmColumn(this, this);
	}

	public int getXmlSequence() {
		return 3;
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
		if (getSpecifedConverterType() == converterType) {
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

	public XmlVersion addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlVersion version = OrmFactory.eINSTANCE.createXmlVersionImpl();
		getPersistentAttribute().initialize(version);
		typeMapping.getAttributes().getVersions().add(version);
		return version;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getVersions().remove(this.getAttributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public String getDefaultColumnName() {		
		return getAttributeName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}
	
	@Override
	public void initialize(XmlVersion version) {
		super.initialize(version);
		this.column.initialize(version.getColumn());
		this.defaultConverter = new GenericOrmNullConverter(this);
		this.specifiedConverter = this.buildSpecifiedConverter(this.specifiedConverterType(version));
	}
	
	@Override
	public void update(XmlVersion version) {
		super.update(version);
		this.column.update(version.getColumn());
		if (specifiedConverterType(version) == getSpecifedConverterType()) {
			getSpecifiedConverter().update(version);
		}
		else {
			setSpecifiedConverter(buildSpecifiedConverter(specifiedConverterType(version)));
		}
	}
	
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		if (converterType == Converter.TEMPORAL_CONVERTER) {
			return new GenericOrmTemporalConverter(this, this.attributeMapping);
		}
		return null;
	}
	
	protected String specifiedConverterType(XmlVersion resourceVersion) {
		if (resourceVersion.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		
		return null;
	}

	//***************** XmlColumn.Owner implementation ****************
	
	public XmlColumn getResourceColumn() {
		return this.getAttributeMapping().getColumn();
	}
	
	public void addResourceColumn() {
		this.getAttributeMapping().setColumn(OrmFactory.eINSTANCE.createXmlColumnImpl());
	}
	
	public void removeResourceColumn() {
		this.getAttributeMapping().setColumn(null);
	}
	
	// ****************** validation ****************
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (this.connectionProfileIsActive() && this.entityOwned()) {
			this.addColumnMessages(messages);
		}
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		if (this.getTypeMapping().tableNameIsInvalid(this.column.getTable())) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {getPersistentAttribute().getName(), this.column.getTable(), this.column.getName()},
						this.column,
						this.column.getTableTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {this.column.getTable(), this.column.getName()}, 
						this.column,
						this.column.getTableTextRange())
				);
			}
			return;
		}
		
		if ( ! this.column.isResolved()) {
			if (getPersistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {getPersistentAttribute().getName(), this.column.getName()}, 
						this.column,
						this.column.getNameTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {this.column.getName()}, 
						this.column,
						this.column.getNameTextRange())
				);
			}
		}
	}
}
