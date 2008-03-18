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
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
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
	
	protected TemporalType temporal;
	
	protected OrmTableGenerator tableGenerator;
	protected OrmSequenceGenerator sequenceGenerator;

	
	public GenericOrmIdMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = jpaFactory().buildOrmColumn(this, this);
	}


	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	public int xmlSequence() {
		return 0;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmIdMapping(this);
	}

	@Override
	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromXmlColumnMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	
	public OrmColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.attributeMapping().setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}
	
	protected void setTemporal_(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		firePropertyChanged(ColumnMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	public OrmGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = jpaFactory().buildOrmGeneratedValue(this);
		this.attributeMapping().setGeneratedValue(OrmFactory.eINSTANCE.createXmlGeneratedValueImpl());
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		OrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.attributeMapping().setGeneratedValue(null);
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
		this.sequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		this.attributeMapping().setSequenceGenerator(OrmFactory.eINSTANCE.createXmlSequenceGeneratorImpl());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		OrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.attributeMapping().setSequenceGenerator(null);
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
		this.tableGenerator = jpaFactory().buildOrmTableGenerator(this);
		this.attributeMapping().setTableGenerator(OrmFactory.eINSTANCE.createXmlTableGeneratorImpl());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		OrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.attributeMapping().setTableGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldTableGenerator, null);	
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
	public String primaryKeyColumnName() {
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
		persistentAttribute().initialize(id);
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getIds().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}

	public String defaultColumnName() {		
		return attributeName();
	}

	public String defaultTableName() {
		return typeMapping().tableName();
	}
	
	@Override
	public void initialize(XmlId id) {
		super.initialize(id);
		this.temporal = this.specifiedTemporal(id);
		this.column.initialize(id.getColumn());
		this.initializeSequenceGenerator(id);
		this.initializeTableGenerator(id);
		this.initializeGeneratedValue(id);
		this.updatePersistenceUnitGenerators();
	}
	
	protected TemporalType specifiedTemporal(XmlId id) {
		return TemporalType.fromOrmResourceModel(id.getTemporal());
	}
	
	protected void initializeSequenceGenerator(XmlId id) {
		if (id.getSequenceGenerator() != null) {
			this.sequenceGenerator = buildSequenceGenerator(id.getSequenceGenerator());
		}
	}
	
	protected OrmSequenceGenerator buildSequenceGenerator(XmlSequenceGenerator xmlSequenceGenerator) {
		OrmSequenceGenerator sequenceGenerator = jpaFactory().buildOrmSequenceGenerator(this);
		sequenceGenerator.initialize(xmlSequenceGenerator);
		return sequenceGenerator;
	}

	protected void initializeTableGenerator(XmlId id) {
		if (id.getTableGenerator() != null) {
			this.tableGenerator = buildTableGenerator(id.getTableGenerator());
		}
	}
	
	protected OrmTableGenerator buildTableGenerator(XmlTableGenerator tableGeneratorResource) {
		OrmTableGenerator tableGenerator = jpaFactory().buildOrmTableGenerator(this);
		tableGenerator.initialize(tableGeneratorResource);
		return tableGenerator;
	}

	protected void initializeGeneratedValue(XmlId id) {
		if (id.getGeneratedValue() != null) {
			this.generatedValue = buildGeneratedValue(id.getGeneratedValue());
		}
	}
	
	protected OrmGeneratedValue buildGeneratedValue(XmlGeneratedValue xmlGeneratedValue) {
		OrmGeneratedValue ormGeneratedValue = jpaFactory().buildOrmGeneratedValue(this);
		ormGeneratedValue.initialize(xmlGeneratedValue);
		return ormGeneratedValue;
	}
	@Override
	public void update(XmlId id) {
		super.update(id);
		this.setTemporal_(this.specifiedTemporal(id));
		this.column.update(id.getColumn());
		this.updateSequenceGenerator(id);
		this.updateTableGenerator(id);
		this.updateGeneratedValue(id);
		this.updatePersistenceUnitGenerators();
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
				getGeneratedValue().initialize(id.getGeneratedValue());
			}
			else {
				getGeneratedValue().update(id.getGeneratedValue());
			}
		}
	}
	
	protected void updatePersistenceUnitGenerators() {
		if (getTableGenerator() != null && getTableGenerator().getName() != null) {
			persistenceUnit().addGenerator(getTableGenerator());
		}
		
		if (getSequenceGenerator() != null && getSequenceGenerator().getName() != null) {
			persistenceUnit().addGenerator(getSequenceGenerator());
		}
	}

	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn columnResource() {
		return this.attributeMapping().getColumn();
	}
	
	public void addColumnResource() {
		this.attributeMapping().setColumn(OrmFactory.eINSTANCE.createXmlColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeMapping().setColumn(null);
	}
	
	// ****************** validation ****************

	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		if (entityOwned()) {
			addColumnMessages(messages);
		}
		addGeneratedValueMessages(messages);
		addGeneratorMessages(messages);
	}
	
	protected void addColumnMessages(List<IMessage> messages) {
		OrmColumn column = getColumn();
		String table = column.getTable();
		boolean doContinue = this.connectionProfileIsActive();
		
		if (doContinue && typeMapping().tableNameIsInvalid(table)) {
			if (persistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_TABLE,
						new String[] {persistentAttribute().getName(), table, column.getName()},
						column, 
						column.tableTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_TABLE,
						new String[] {table, column.getName()}, 
						column, 
						column.tableTextRange())
				);
			}
			doContinue = false;
		}
		
		if (doContinue && ! column.isResolved()) {
			if (persistentAttribute().isVirtual()) {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.VIRTUAL_ATTRIBUTE_COLUMN_UNRESOLVED_NAME,
						new String[] {persistentAttribute().getName(), column.getName()}, 
						column, 
						column.nameTextRange())
				);
			}
			else {
				messages.add(
					DefaultJpaValidationMessages.buildMessage(
						IMessage.HIGH_SEVERITY,
						JpaValidationMessages.COLUMN_UNRESOLVED_NAME,
						new String[] {column.getName()}, 
						column, 
						column.nameTextRange())
				);
			}
		}
	}
	
	protected void addGeneratedValueMessages(List<IMessage> messages) {
		OrmGeneratedValue generatedValue = getGeneratedValue();
		if (generatedValue == null) {
			return;
		}
		String generatorName = generatedValue.getGenerator();
		if (generatorName == null) {
			return;
		}
		
		for (Generator nextMasterGenerator : CollectionTools.iterable(persistenceUnit().allGenerators())) {
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
				generatedValue.generatorTextRange())
			);
	}
	
	protected void addGeneratorMessages(List<IMessage> messages) {
		List<Generator> masterList = CollectionTools.list(persistenceUnit().allGenerators());
		
		for (Iterator<OrmGenerator> stream = this.generators(); stream.hasNext() ; ) {
			OrmGenerator current = stream.next();
			masterList.remove(current);
			
			for (Generator each : masterList) {
				if (! each.overrides(current) && each.getName().equals(current.getName())) {
					messages.add(
						DefaultJpaValidationMessages.buildMessage(
							IMessage.HIGH_SEVERITY,
							JpaValidationMessages.GENERATOR_DUPLICATE_NAME,
							new String[] {current.getName()},
							current,
							current.nameTextRange())
					);
				}
			}
			
			masterList.add(current);
		}
	}
}