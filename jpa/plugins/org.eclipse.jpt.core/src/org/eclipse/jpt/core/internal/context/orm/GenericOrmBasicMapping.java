/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.FetchType;
import org.eclipse.jpt.core.context.Fetchable;
import org.eclipse.jpt.core.context.Nullable;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmBasicMapping extends AbstractOrmAttributeMapping<XmlBasic>
	implements OrmBasicMapping
{
	protected final OrmColumn column;
	
	protected FetchType specifiedFetch;

	protected Boolean specifiedOptional;
	
	protected OrmConverter defaultConverter;
	
	protected OrmConverter specifiedConverter;
	
	public GenericOrmBasicMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = getJpaFactory().buildOrmColumn(this, this);
	}

	public FetchType getFetch() {
		return (this.getSpecifiedFetch() == null) ? this.getDefaultFetch() : this.getSpecifiedFetch();
	}

	public FetchType getDefaultFetch() {
		return BasicMapping.DEFAULT_FETCH_TYPE;
	}

	public FetchType getSpecifiedFetch() {
		return this.specifiedFetch;
	}
	
	public void setSpecifiedFetch(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		this.getAttributeMapping().setFetch(FetchType.toOrmResourceModel(newSpecifiedFetch));
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	protected void setSpecifiedFetch_(FetchType newSpecifiedFetch) {
		FetchType oldFetch = this.specifiedFetch;
		this.specifiedFetch = newSpecifiedFetch;
		firePropertyChanged(Fetchable.SPECIFIED_FETCH_PROPERTY, oldFetch, newSpecifiedFetch);
	}

	public Boolean getOptional() {
		return (this.getSpecifiedOptional() == null) ? this.getDefaultOptional() : this.getSpecifiedOptional();
	}
	
	public Boolean getDefaultOptional() {
		return Nullable.DEFAULT_OPTIONAL;
	}

	public Boolean getSpecifiedOptional() {
		return this.specifiedOptional;
	}
	
	public void setSpecifiedOptional(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		this.getAttributeMapping().setOptional(newSpecifiedOptional);
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
	}
	
	protected void setSpecifiedOptional_(Boolean newSpecifiedOptional) {
		Boolean oldOptional = this.specifiedOptional;
		this.specifiedOptional = newSpecifiedOptional;
		firePropertyChanged(Nullable.SPECIFIED_OPTIONAL_PROPERTY, oldOptional, newSpecifiedOptional);
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

	public String getKey() {
		return MappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmBasicMapping(this);
	}


	@Override
	public void initializeFromOrmColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromOrmColumnMapping(oldMapping);
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	public int getXmlSequence() {
		return 2;
	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	public OrmColumn getColumn() {
		return this.column;
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
	public void initialize(XmlBasic basic) {
		super.initialize(basic);
		this.specifiedFetch = this.specifiedFetch(basic);
		this.specifiedOptional = this.specifiedOptional(basic);
		this.column.initialize(basic.getColumn());
		this.defaultConverter = new GenericOrmNullConverter(this);
		this.specifiedConverter = this.buildSpecifiedConverter(this.specifiedConverterType(basic));
	}
	
	@Override
	public void update(XmlBasic basic) {
		super.update(basic);
		this.setSpecifiedFetch_(this.specifiedFetch(basic));
		this.setSpecifiedOptional_(this.specifiedOptional(basic));
		this.column.update(basic.getColumn());
		if (specifiedConverterType(basic) == getSpecifedConverterType()) {
			getSpecifiedConverter().update(basic);
		}
		else {
			setSpecifiedConverter(buildSpecifiedConverter(specifiedConverterType(basic)));
		}
	}
	
	protected Boolean specifiedOptional(XmlBasic basic) {
		return basic.getOptional();
	}
	
	protected FetchType specifiedFetch(XmlBasic basic) {
		return FetchType.fromOrmResourceModel(basic.getFetch());
	}
	
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		if (converterType == Converter.ENUMERATED_CONVERTER) {
			return new GenericOrmEnumeratedConverter(this, this.attributeMapping);
		}
		else if (converterType == Converter.TEMPORAL_CONVERTER) {
			return new GenericOrmTemporalConverter(this, this.attributeMapping);
		}
		else if (converterType == Converter.LOB_CONVERTER) {
			return new GenericOrmLobConverter(this, this.attributeMapping);
		}
		return null;
	}
	
	protected String specifiedConverterType(XmlBasic xmlBasic) {
		if (xmlBasic.getEnumerated() != null) {
			return Converter.ENUMERATED_CONVERTER;
		}
		else if (xmlBasic.getTemporal() != null) {
			return Converter.TEMPORAL_CONVERTER;
		}
		else if (xmlBasic.isLob()) {
			return Converter.LOB_CONVERTER;
		}
		
		return null;
	}
	
	public XmlBasic addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlBasic basic = OrmFactory.eINSTANCE.createXmlBasicImpl();
		getPersistentAttribute().initialize(basic);
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getBasics().remove(this.getAttributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
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
						new String[] {getName(), this.column.getTable(), this.column.getName()},
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
						new String[] {getName(), this.column.getName()}, 
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
