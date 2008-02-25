/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.ColumnMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.TemporalType;
import org.eclipse.jpt.core.context.orm.OrmColumnMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.db.internal.Table;


public class GenericOrmIdMapping extends AbstractOrmAttributeMapping<XmlId>
	implements IdMapping, OrmColumnMapping
{
	protected final GenericOrmColumn column;

	protected GenericOrmGeneratedValue generatedValue;
	
	protected TemporalType temporal;
	
	protected GenericOrmTableGenerator tableGenerator;
	protected GenericOrmSequenceGenerator sequenceGenerator;

	
	protected GenericOrmIdMapping(OrmPersistentAttribute parent) {
		super(parent);
		this.column = new GenericOrmColumn(this, this);
	}


	public String getKey() {
		return MappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public int xmlSequence() {
		return 0;
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlIdMapping(this);
	}

	@Override
	public void initializeFromXmlColumnMapping(OrmColumnMapping oldMapping) {
		super.initializeFromXmlColumnMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
		getColumn().initializeFrom(oldMapping.getColumn());
	}

	
	public GenericOrmColumn getColumn() {
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

	public GenericOrmGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = new GenericOrmGeneratedValue(this);
		this.attributeMapping().setGeneratedValue(OrmFactory.eINSTANCE.createGeneratedValueImpl());
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		GenericOrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.attributeMapping().setGeneratedValue(null);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public GenericOrmGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(GenericOrmGeneratedValue newGeneratedValue) {
		GenericOrmGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}

	public GenericOrmSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = new GenericOrmSequenceGenerator(this);
		this.attributeMapping().setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGeneratorImpl());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		GenericOrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.attributeMapping().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public GenericOrmSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(GenericOrmSequenceGenerator newSequenceGenerator) {
		GenericOrmSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public GenericOrmTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = new GenericOrmTableGenerator(this);
		this.attributeMapping().setTableGenerator(OrmFactory.eINSTANCE.createTableGeneratorImpl());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		GenericOrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.attributeMapping().setTableGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldTableGenerator, null);	
	}
	
	public GenericOrmTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(GenericOrmTableGenerator newTableGenerator) {
		GenericOrmTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
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
	
	@Override
	public XmlId addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlId id = OrmFactory.eINSTANCE.createIdImpl();
		persistentAttribute().initialize(id);
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
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

	public TextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initialize(XmlId id) {
		super.initialize(id);
		this.temporal = this.specifiedTemporal(id);
		this.column.initialize(id.getColumn());
		this.initializeSequenceGenerator(id);
		this.initializeTableGenerator(id);
		this.initializeGeneratedValue(id);
	}
	
	protected void initializeSequenceGenerator(XmlId id) {
		if (id.getSequenceGenerator() != null) {
			this.sequenceGenerator = new GenericOrmSequenceGenerator(this);
			this.sequenceGenerator.initialize(id.getSequenceGenerator());
		}
	}
	
	protected void initializeTableGenerator(XmlId id) {
		if (id.getTableGenerator() != null) {
			this.tableGenerator = new GenericOrmTableGenerator(this);
			this.tableGenerator.initialize(id.getTableGenerator());
		}
	}
	
	protected void initializeGeneratedValue(XmlId id) {
		if (id.getGeneratedValue() != null) {
			this.generatedValue = new GenericOrmGeneratedValue(this);
			this.generatedValue.initialize(id.getGeneratedValue());
		}
	}
	
	@Override
	public void update(XmlId id) {
		super.update(id);
		this.setTemporal_(this.specifiedTemporal(id));
		this.column.update(id.getColumn());
		this.updateSequenceGenerator(id);
		this.updateTableGenerator(id);
		this.updateGeneratedValue(id);
	}
	
	protected void updateSequenceGenerator(XmlId id) {
		if (id.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(new GenericOrmSequenceGenerator(this));
				getSequenceGenerator().initialize(id.getSequenceGenerator());
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
				setTableGenerator(new GenericOrmTableGenerator(this));
				getTableGenerator().initialize(id.getTableGenerator());
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
				setGeneratedValue(new GenericOrmGeneratedValue(this));
				getGeneratedValue().initialize(id.getGeneratedValue());
			}
			else {
				getGeneratedValue().update(id.getGeneratedValue());
			}
		}
	}
	

	protected TemporalType specifiedTemporal(XmlId id) {
		return TemporalType.fromOrmResourceModel(id.getTemporal());
	}

	//***************** IXmlColumn.Owner implementation ****************
	
	public XmlColumn columnResource() {
		return this.attributeMapping().getColumn();
	}
	
	public void addColumnResource() {
		this.attributeMapping().setColumn(OrmFactory.eINSTANCE.createColumnImpl());
	}
	
	public void removeColumnResource() {
		this.attributeMapping().setColumn(null);
	}
}
