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

import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmConverter;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmGenerator;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.internal.validation.DefaultJpaValidationMessages;
import org.eclipse.jpt.core.internal.validation.JpaValidationMessages;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.SingleElementIterator;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;


public class GenericOrmIdMapping extends AbstractOrmAttributeMapping<XmlId>
	implements OrmIdMapping
{
	protected final OrmColumn column;

	protected OrmGeneratedValue generatedValue;
	
	protected OrmConverter defaultConverter;
	protected OrmConverter specifiedConverter;
	
	protected OrmTableGenerator tableGenerator;
	protected OrmSequenceGenerator sequenceGenerator;

	
	public GenericOrmIdMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = getJpaFactory().buildOrmColumn(this, this);
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

	public OrmGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		XmlGeneratedValue resourceGeneratedValue = OrmFactory.eINSTANCE.createXmlGeneratedValueImpl();
		this.generatedValue = buildGeneratedValue(resourceGeneratedValue);
		this.getAttributeMapping().setGeneratedValue(resourceGeneratedValue);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		OrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.getAttributeMapping().setGeneratedValue(null);
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

	public OrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		XmlSequenceGenerator resourceSequenceGenerator = OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl();
		this.sequenceGenerator = buildSequenceGenerator(resourceSequenceGenerator);
		this.getAttributeMapping().setSequenceGenerator(resourceSequenceGenerator);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.getAttributeMapping().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public OrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(OrmSequenceGenerator newSequenceGenerator) {
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public OrmTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		XmlTableGenerator resourceTableGenerator = OrmFactory.eINSTANCE.createXmlTableGeneratorImpl();
		this.tableGenerator = buildTableGenerator(resourceTableGenerator);
		this.getAttributeMapping().setTableGenerator(resourceTableGenerator);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.getAttributeMapping().setTableGenerator(null);
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, null);	
	}
	
	public OrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(OrmTableGenerator newTableGenerator) {
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}
	
	@SuppressWarnings("unchecked")
	protected Iterator<OrmGenerator> generators() {
		return new CompositeIterator<OrmGenerator>(
			(getSequenceGenerator() == null) ? EmptyIterator.instance() : new SingleElementIterator(getSequenceGenerator()),
			(getTableGenerator() == null) ? EmptyIterator.instance() : new SingleElementIterator(getTableGenerator()));
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
	
	public XmlId addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlId id = OrmFactory.eINSTANCE.createXmlIdImpl();
		getPersistentAttribute().initialize(id);
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getIds().remove(this.getAttributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public Table getDbTable(String tableName) {
		return getTypeMapping().getDbTable(tableName);
	}

	public String getDefaultColumnName() {		
		return getAttributeName();
	}

	public String getDefaultTableName() {
		return getTypeMapping().getPrimaryTableName();
	}
	
	@Override
	public void initialize(XmlId id) {
		super.initialize(id);
		this.column.initialize(id.getColumn());
		this.initializeSequenceGenerator(id);
		this.initializeTableGenerator(id);
		this.initializeGeneratedValue(id);
		this.updatePersistenceUnitGenerators();
		this.defaultConverter = new GenericOrmNullConverter(this);
		this.specifiedConverter = this.buildSpecifiedConverter(this.specifiedConverterType(id));
	}
	
	protected void initializeSequenceGenerator(XmlId id) {
		if (id.getSequenceGenerator() != null) {
			this.sequenceGenerator = buildSequenceGenerator(id.getSequenceGenerator());
		}
	}
	
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator resourceSequenceGenerator) {
		return getJpaFactory().buildOrmSequenceGenerator(this, resourceSequenceGenerator);
	}

	protected void initializeTableGenerator(XmlId id) {
		if (id.getTableGenerator() != null) {
			this.tableGenerator = buildTableGenerator(id.getTableGenerator());
		}
	}
	
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator resourceTableGenerator) {
		return getJpaFactory().buildOrmTableGenerator(this, resourceTableGenerator);
	}

	protected void initializeGeneratedValue(XmlId id) {
		if (id.getGeneratedValue() != null) {
			this.generatedValue = buildGeneratedValue(id.getGeneratedValue());
		}
	}
	
	protected OrmGeneratedValue buildGeneratedValue(XmlGeneratedValue resourceGeneratedValue) {
		return getJpaFactory().buildOrmGeneratedValue(this, resourceGeneratedValue);
	}
	@Override
	public void update(XmlId id) {
		super.update(id);
		this.column.update(id.getColumn());
		this.updateSequenceGenerator(id);
		this.updateTableGenerator(id);
		this.updateGeneratedValue(id);
		this.updatePersistenceUnitGenerators();
		if (specifiedConverterType(id) == getSpecifedConverterType()) {
			getSpecifiedConverter().update(id);
		}
		else {
			setSpecifiedConverter(buildSpecifiedConverter(specifiedConverterType(id)));
		}
	}
	
	protected void updateSequenceGenerator(XmlId id) {
		if (id.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(buildSequenceGenerator(id.getSequenceGenerator()));
			}
			else {
				getSequenceGenerator().update(id.getSequenceGenerator());
			}
		}
	}
	
	protected void updateTableGenerator(XmlId id) {
		if (id.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(buildTableGenerator(id.getTableGenerator()));
			}
			else {
				getTableGenerator().update(id.getTableGenerator());
			}
		}
	}
	
	protected void updateGeneratedValue(XmlId id) {
		if (id.getGeneratedValue() == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(buildGeneratedValue(id.getGeneratedValue()));
			}
			else {
				getGeneratedValue().update(id.getGeneratedValue());
			}
		}
	}
	
	protected void updatePersistenceUnitGenerators() {
		if (getTableGenerator() != null) {
			getPersistenceUnit().addGenerator(getTableGenerator());
		}
		
		if (getSequenceGenerator() != null) {
			getPersistenceUnit().addGenerator(getSequenceGenerator());
		}
	}
	
	protected OrmConverter buildSpecifiedConverter(String converterType) {
		if (converterType == Converter.TEMPORAL_CONVERTER) {
			return new GenericOrmTemporalConverter(this, this.attributeMapping);
		}
		return null;
	}
	
	protected String specifiedConverterType(XmlId xmlId) {
		if (xmlId.getTemporal() != null) {
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
		this.addGeneratedValueMessages(messages);
		this.addGeneratorMessages(messages);
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
	
	protected void addGeneratedValueMessages(List<IMessage> messages) {
		if (this.generatedValue == null) {
			return;
		}
		String generatorName = this.generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
		
		for (Generator nextMasterGenerator : CollectionTools.iterable(getPersistenceUnit().allGenerators())) {
			if (generatorName.equals(nextMasterGenerator.getName())) {
				return;
			}
		}
		
		messages.add(
			DefaultJpaValidationMessages.buildMessage(
				IMessage.HIGH_SEVERITY,
				JpaValidationMessages.ID_MAPPING_UNRESOLVED_GENERATOR_NAME,
				new String[] {generatorName},
				this,
				this.generatedValue.getGeneratorTextRange())
			);
	}
	
	protected void addGeneratorMessages(List<IMessage> messages) {
		List<Generator> masterList = CollectionTools.list(getPersistenceUnit().allGenerators());
		
		for (Iterator<OrmGenerator> stream = this.generators(); stream.hasNext() ; ) {
			OrmGenerator current = stream.next();
			if (current.isVirtual()) {
				return;
			}
			masterList.remove(current);
			
			for (Generator each : masterList) {
				if (! each.overrides(current) && each.getName() != null && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.getNameTextRange())
					);
				}
			}
			
			masterList.add(current);
		}
	}
}