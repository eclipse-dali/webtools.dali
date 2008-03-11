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

import org.eclipse.jpt.core.context.orm.OrmJpaContextNode;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;

public class GenericOrmTableGenerator extends AbstractOrmGenerator<XmlTableGenerator> implements OrmTableGenerator
{
	protected String specifiedTable;
	protected String defaultTable;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedPkColumnName;
	protected String defaultPkColumnName;

	protected String specifiedValueColumnName;
	protected String defaultValueColumnName;

	protected String specifiedPkColumnValue;
	protected String defaultPkColumnValue;

//	protected EList<IUniqueConstraint> uniqueConstraints;

	
	public GenericOrmTableGenerator(OrmJpaContextNode parent) {
		super(parent);
	}

	public String getTable() {
		return (this.getSpecifiedTable() == null) ? getDefaultTable() : this.getSpecifiedTable();
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		generatorResource().setTable(newSpecifiedTable);
		firePropertyChanged(SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}
	
	protected void setSpecifiedTable_(String newSpecifiedTable) {
		String oldSpecifiedTable = this.specifiedTable;
		this.specifiedTable = newSpecifiedTable;
		firePropertyChanged(SPECIFIED_TABLE_PROPERTY, oldSpecifiedTable, newSpecifiedTable);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}
	
	protected void setDefaultTable(String newDefaultTable) {
		String oldDefaultTable = this.defaultTable;
		this.defaultTable = newDefaultTable;
		firePropertyChanged(DEFAULT_TABLE_PROPERTY, oldDefaultTable, newDefaultTable);
	}

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		generatorResource().setCatalog(newSpecifiedCatalog);
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}
	
	protected void setSpecifiedCatalog_(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, oldSpecifiedCatalog, newSpecifiedCatalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	public String getSchema() {
		return (this.getSpecifiedSchema() == null) ? getDefaultSchema() : this.getSpecifiedSchema();
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		generatorResource().setSchema(newSpecifiedSchema);
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	protected void setSpecifiedSchema_(String newSpecifiedSchema) {
		String oldSpecifiedSchema = this.specifiedSchema;
		this.specifiedSchema = newSpecifiedSchema;
		firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, oldSpecifiedSchema, newSpecifiedSchema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}
	
	protected void setDefaultSchema(String newDefaultSchema) {
		String oldDefaultSchema = this.defaultSchema;
		this.defaultSchema = newDefaultSchema;
		firePropertyChanged(this.defaultSchema, oldDefaultSchema, newDefaultSchema);
	}

	public String getPkColumnName() {
		return (this.getSpecifiedPkColumnName() == null) ? getDefaultPkColumnName() : this.getSpecifiedPkColumnName();
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}
	
	public void setSpecifiedPkColumnName(String newSpecifiedPkColumnName) {
		String oldSpecifiedPkColumnName = this.specifiedPkColumnName;
		this.specifiedPkColumnName = newSpecifiedPkColumnName;
		generatorResource().setPkColumnName(newSpecifiedPkColumnName);
		firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, oldSpecifiedPkColumnName, newSpecifiedPkColumnName);
	}
	
	protected void setSpecifiedPkColumnName_(String newSpecifiedPkColumnName) {
		String oldSpecifiedPkColumnName = this.specifiedPkColumnName;
		this.specifiedPkColumnName = newSpecifiedPkColumnName;
		firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, oldSpecifiedPkColumnName, newSpecifiedPkColumnName);
	}

	public String getDefaultPkColumnName() {
		return this.defaultPkColumnName;
	}
	
	protected void setDefaultPkColumnName(String newDefaultPkColumnName) {
		String oldDefaultPkColumnName = this.defaultPkColumnName;
		this.defaultPkColumnName = newDefaultPkColumnName;
		firePropertyChanged(DEFAULT_PK_COLUMN_NAME_PROPERTY, oldDefaultPkColumnName, newDefaultPkColumnName);
	}

	public String getValueColumnName() {
		return (this.getSpecifiedValueColumnName() == null) ? getDefaultValueColumnName() : this.getSpecifiedValueColumnName();
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = this.specifiedValueColumnName;
		this.specifiedValueColumnName = newSpecifiedValueColumnName;
		generatorResource().setValueColumnName(newSpecifiedValueColumnName);
		firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, oldSpecifiedValueColumnName, newSpecifiedValueColumnName);
	}

	protected void setSpecifiedValueColumnName_(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = this.specifiedValueColumnName;
		this.specifiedValueColumnName = newSpecifiedValueColumnName;
		firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, oldSpecifiedValueColumnName, newSpecifiedValueColumnName);
	}

	public String getDefaultValueColumnName() {
		return this.defaultValueColumnName;
	}
	
	protected void setDefaultValueColumnName(String newDefaultValueColumnName) {
		String oldDefaultValueColumnName = this.defaultValueColumnName;
		this.defaultValueColumnName = newDefaultValueColumnName;
		firePropertyChanged(DEFAULT_VALUE_COLUMN_NAME_PROPERTY, oldDefaultValueColumnName, newDefaultValueColumnName);
	}

	public String getPkColumnValue() {
		return (this.getSpecifiedPkColumnValue() == null) ? getDefaultPkColumnValue() : this.getSpecifiedPkColumnValue();
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		generatorResource().setPkColumnValue(newSpecifiedPkColumnValue);
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
	}

	protected void setSpecifiedPkColumnValue_(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}
	
	public void setDefaultPkColumnValue(String newDefaultPkColumnValue) {
		String oldDefaultPkColumnValue = this.defaultPkColumnValue;
		this.defaultPkColumnValue = newDefaultPkColumnValue;
		firePropertyChanged(DEFAULT_PK_COLUMN_VALUE_PROPERTY, oldDefaultPkColumnValue, newDefaultPkColumnValue);
	}

//	public EList<IUniqueConstraint> getUniqueConstraints() {
//		if (uniqueConstraints == null) {
//			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, OrmPackage.XML_TABLE_GENERATOR__UNIQUE_CONSTRAINTS);
//		}
//		return uniqueConstraints;
//	}


//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		setDefaultSchema((String) defaultsContext.getDefault(GenericJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY));
//	}
//
//	public IUniqueConstraint createUniqueConstraint(int index) {
//		return createXmlJavaUniqueConstraint(index);
//	}
//
//	protected XmlUniqueConstraint createXmlJavaUniqueConstraint(int index) {
//		return OrmFactory.eINSTANCE.createXmlUniqueConstraint();
//	}

	public Table dbTable() {
		Schema schema = this.dbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getTable());
	}

	public Schema dbSchema() {
		return this.database().schemaNamed(this.getSchema());
	}
	
	// ********** orm resource model -> context model **********

	@Override
	public void initialize(XmlTableGenerator tableGenerator) {
		super.initialize(tableGenerator);
		this.specifiedTable = this.specifiedTable(tableGenerator);
		this.specifiedCatalog = this.specifiedCatalog(tableGenerator);
		this.specifiedSchema = this.specifiedSchema(tableGenerator);
		this.specifiedPkColumnName = this.specifiedPkColumnName(tableGenerator);
		this.specifiedValueColumnName = this.specifiedValueColumnName(tableGenerator);
		this.specifiedPkColumnValue = this.specifiedPkColumnValue(tableGenerator);
		//TODO defaults
		//this.updateUniqueConstraintsFromJava(astRoot);
	}
	
	@Override
	public void update(XmlTableGenerator tableGenerator) {
		super.update(tableGenerator);
		this.setSpecifiedTable_(this.specifiedTable(tableGenerator));
		this.setSpecifiedCatalog_(this.specifiedCatalog(tableGenerator));
		this.setSpecifiedSchema_(this.specifiedSchema(tableGenerator));
		this.setSpecifiedPkColumnName_(this.specifiedPkColumnName(tableGenerator));
		this.setSpecifiedValueColumnName_(this.specifiedValueColumnName(tableGenerator));
		this.setSpecifiedPkColumnValue_(this.specifiedPkColumnValue(tableGenerator));
		//TODO defaults
		//this.updateUniqueConstraintsFromJava(astRoot);
	}
	
	protected String specifiedTable(XmlTableGenerator tableGenerator) {
		return tableGenerator.getTable();
	}
	
	protected String specifiedCatalog(XmlTableGenerator tableGenerator) {
		return tableGenerator.getCatalog();
	}
	
	protected String specifiedSchema(XmlTableGenerator tableGenerator) {
		return tableGenerator.getSchema();
	}
	
	protected String specifiedPkColumnName(XmlTableGenerator tableGenerator) {
		return tableGenerator.getPkColumnName();
	}
	
	protected String specifiedValueColumnName(XmlTableGenerator tableGenerator) {
		return tableGenerator.getValueColumnName();
	}
	
	protected String specifiedPkColumnValue(XmlTableGenerator tableGenerator) {
		return tableGenerator.getPkColumnValue();
	}

}
