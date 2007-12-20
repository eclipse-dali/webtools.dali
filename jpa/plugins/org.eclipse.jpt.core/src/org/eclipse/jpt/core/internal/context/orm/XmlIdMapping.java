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
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.core.internal.context.base.IIdMapping;
import org.eclipse.jpt.core.internal.context.base.TemporalType;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Id;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.db.internal.Table;


public class XmlIdMapping extends XmlAttributeMapping<Id>
	implements IIdMapping, IXmlColumnMapping
{
	protected final XmlColumn column;

	protected XmlGeneratedValue generatedValue;
	
	protected TemporalType temporal;
	
	protected XmlTableGenerator tableGenerator;
	protected XmlSequenceGenerator sequenceGenerator;

	
	protected XmlIdMapping(XmlPersistentAttribute parent) {
		super(parent);
		this.column = new XmlColumn(this, this);
	}

	public XmlColumn getColumn() {
		return this.column;
	}

	public TemporalType getTemporal() {
		return this.temporal;
	}

	public void setTemporal(TemporalType newTemporal) {
		TemporalType oldTemporal = this.temporal;
		this.temporal = newTemporal;
		this.attributeMapping().setTemporal(TemporalType.toOrmResourceModel(newTemporal));
		firePropertyChanged(IBasicMapping.TEMPORAL_PROPERTY, oldTemporal, newTemporal);
	}

	public XmlGeneratedValue addGeneratedValue() {
		if (getGeneratedValue() != null) {
			throw new IllegalStateException("gemeratedValue already exists");
		}
		this.generatedValue = new XmlGeneratedValue(this);
		this.attributeMapping().setGeneratedValue(OrmFactory.eINSTANCE.createGeneratedValue());
		firePropertyChanged(GENERATED_VALUE_PROPERTY, null, this.generatedValue);
		return this.generatedValue;
	}
	
	public void removeGeneratedValue() {
		if (getGeneratedValue() == null) {
			throw new IllegalStateException("gemeratedValue does not exist, cannot be removed");
		}
		XmlGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = null;
		this.attributeMapping().setGeneratedValue(null);
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, null);
	}
	
	public XmlGeneratedValue getGeneratedValue() {
		return this.generatedValue;
	}
	
	protected void setGeneratedValue(XmlGeneratedValue newGeneratedValue) {
		XmlGeneratedValue oldGeneratedValue = this.generatedValue;
		this.generatedValue = newGeneratedValue;
		firePropertyChanged(GENERATED_VALUE_PROPERTY, oldGeneratedValue, newGeneratedValue);
	}

	public XmlSequenceGenerator addSequenceGenerator() {
		if (getSequenceGenerator() != null) {
			throw new IllegalStateException("sequenceGenerator already exists");
		}
		this.sequenceGenerator = new XmlSequenceGenerator(this);
		this.attributeMapping().setSequenceGenerator(OrmFactory.eINSTANCE.createSequenceGenerator());
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, null, this.sequenceGenerator);
		return this.sequenceGenerator;
	}
	
	public void removeSequenceGenerator() {
		if (getSequenceGenerator() == null) {
			throw new IllegalStateException("sequenceGenerator does not exist, cannot be removed");
		}
		XmlSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = null;
		this.attributeMapping().setSequenceGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, null);
	}
	
	public XmlSequenceGenerator getSequenceGenerator() {
		return this.sequenceGenerator;
	}

	protected void setSequenceGenerator(XmlSequenceGenerator newSequenceGenerator) {
		XmlSequenceGenerator oldSequenceGenerator = this.sequenceGenerator;
		this.sequenceGenerator = newSequenceGenerator;
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldSequenceGenerator, newSequenceGenerator);
	}

	public XmlTableGenerator addTableGenerator() {
		if (getTableGenerator() != null) {
			throw new IllegalStateException("tableGenerator already exists");
		}
		this.tableGenerator = new XmlTableGenerator(this);
		this.attributeMapping().setTableGenerator(OrmFactory.eINSTANCE.createTableGenerator());
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, null, this.tableGenerator);
		return this.tableGenerator;
	}
	
	public void removeTableGenerator() {
		if (getTableGenerator() == null) {
			throw new IllegalStateException("tableGenerator does not exist, cannot be removed");
		}
		XmlTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = null;
		this.attributeMapping().setTableGenerator(null);
		firePropertyChanged(SEQUENCE_GENERATOR_PROPERTY, oldTableGenerator, null);	
	}
	
	public XmlTableGenerator getTableGenerator() {
		return this.tableGenerator;
	}

	protected void setTableGenerator(XmlTableGenerator newTableGenerator) {
		XmlTableGenerator oldTableGenerator = this.tableGenerator;
		this.tableGenerator = newTableGenerator;
		if (newTableGenerator != null) {
			this.attributeMapping().setTableGenerator(OrmFactory.eINSTANCE.createTableGenerator());
		}
		else {
			this.attributeMapping().setTableGenerator(null);
		}
		firePropertyChanged(TABLE_GENERATOR_PROPERTY, oldTableGenerator, newTableGenerator);
	}


	public String getKey() {
		return IMappingKeys.ID_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected void initializeOn(XmlAttributeMapping<? extends AttributeMapping> newMapping) {
		newMapping.initializeFromXmlIdMapping(this);
	}

	@Override
	public void initializeFromXmlBasicMapping(XmlBasicMapping oldMapping) {
		super.initializeFromXmlBasicMapping(oldMapping);
		setTemporal(oldMapping.getTemporal());
	}

	@Override
	public int xmlSequence() {
		return 0;
	}

	@Override
	public String primaryKeyColumnName() {
		return this.getColumn().getName();
	}

//	public IGeneratedValue createGeneratedValue() {
//		return OrmFactory.eINSTANCE.createXmlGeneratedValue();
//	}
//
//	public ISequenceGenerator createSequenceGenerator() {
//		return OrmFactory.eINSTANCE.createXmlSequenceGenerator();
//	}
//
//	public ITableGenerator createTableGenerator() {
//		return OrmFactory.eINSTANCE.createXmlTableGenerator();
//	}

	@Override
	public boolean isOverridableAttributeMapping() {
		return true;
	}

	@Override
	public boolean isIdMapping() {
		return true;
	}
	
	@Override
	public Id addToResourceModel(TypeMapping typeMapping) {
		Id id = OrmFactory.eINSTANCE.createId();
		typeMapping.getAttributes().getIds().add(id);
		return id;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getIds().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}

	public String defaultTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Table dbTable(String tableName) {
		return typeMapping().dbTable(tableName);
	}

	public String defaultColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	public ITextRange validationTextRange(CompilationUnit astRoot) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initialize(Id id) {
		super.initialize(id);
		this.temporal = this.specifiedTemporal(id);
		this.column.initialize(id);
		this.initializeSequenceGenerator(id);
		this.initializeTableGenerator(id);
		this.initializeGeneratedValue(id);
	}
	
	protected void initializeSequenceGenerator(Id id) {
		if (id.getSequenceGenerator() != null) {
			this.sequenceGenerator = new XmlSequenceGenerator(this);
			this.sequenceGenerator.initialize(id.getSequenceGenerator());
		}
	}
	
	protected void initializeTableGenerator(Id id) {
		if (id.getTableGenerator() != null) {
			this.tableGenerator = new XmlTableGenerator(this);
			this.tableGenerator.initialize(id.getTableGenerator());
		}
	}
	
	protected void initializeGeneratedValue(Id id) {
		if (id.getGeneratedValue() != null) {
			this.generatedValue = new XmlGeneratedValue(this);
			this.generatedValue.initialize(id.getGeneratedValue());
		}
	}
	
	@Override
	public void update(Id id) {
		super.update(id);
		this.setTemporal(this.specifiedTemporal(id));
		this.column.update(id);
		this.updateSequenceGenerator(id);
		this.updateTableGenerator(id);
		this.updateGeneratedValue(id);
	}
	
	protected void updateSequenceGenerator(Id id) {
		if (id.getSequenceGenerator() == null) {
			if (getSequenceGenerator() != null) {
				setSequenceGenerator(null);
			}
		}
		else {
			if (getSequenceGenerator() == null) {
				setSequenceGenerator(new XmlSequenceGenerator(this));
				getSequenceGenerator().initialize(id.getSequenceGenerator());
			}
			else {
				getSequenceGenerator().update(id.getSequenceGenerator());
			}
		}
	}
	
	protected void updateTableGenerator(Id id) {
		if (id.getTableGenerator() == null) {
			if (getTableGenerator() != null) {
				setTableGenerator(null);
			}
		}
		else {
			if (getTableGenerator() == null) {
				setTableGenerator(new XmlTableGenerator(this));
				getTableGenerator().initialize(id.getTableGenerator());
			}
			else {
				getTableGenerator().update(id.getTableGenerator());
			}
		}
	}
	
	protected void updateGeneratedValue(Id id) {
		if (id.getGeneratedValue() == null) {
			if (getGeneratedValue() != null) {
				setGeneratedValue(null);
			}
		}
		else {
			if (getGeneratedValue() == null) {
				setGeneratedValue(new XmlGeneratedValue(this));
				getGeneratedValue().initialize(id.getGeneratedValue());
			}
			else {
				getGeneratedValue().update(id.getGeneratedValue());
			}
		}
	}
	

	protected TemporalType specifiedTemporal(Id id) {
		return TemporalType.fromOrmResourceModel(id.getTemporal());
	}

}
