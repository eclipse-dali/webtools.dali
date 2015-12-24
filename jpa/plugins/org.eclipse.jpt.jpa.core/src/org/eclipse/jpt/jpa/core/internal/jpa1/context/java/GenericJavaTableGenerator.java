/*******************************************************************************
 * Copyright (c) 2007, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import java.util.ArrayList;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.ListTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.DatabaseGenerator;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.TableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaDatabaseGenerator;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Java table generator
 */
public class GenericJavaTableGenerator
	extends AbstractJavaDatabaseGenerator<TableGeneratorAnnotation>
	implements JavaTableGenerator, SpecifiedUniqueConstraint.Parent
{
	protected String specifiedTableName;
	protected String defaultTableName;

	protected String specifiedSchema;
	protected String defaultSchema;

	protected String specifiedCatalog;
	protected String defaultCatalog;

	protected String specifiedPkColumnName;
	protected String defaultPkColumnName;

	protected String specifiedValueColumnName;
	protected String defaultValueColumnName;

	protected String specifiedPkColumnValue;
	protected String defaultPkColumnValue;

	protected final ContextListContainer<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation> uniqueConstraintContainer;


	// ********** constructor **********

	public GenericJavaTableGenerator(JavaGeneratorContainer parent, TableGeneratorAnnotation generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedTableName = generatorAnnotation.getTable();
		this.specifiedSchema = generatorAnnotation.getSchema();
		this.specifiedCatalog = generatorAnnotation.getCatalog();
		this.specifiedPkColumnName = generatorAnnotation.getPkColumnName();
		this.specifiedValueColumnName = generatorAnnotation.getValueColumnName();
		this.specifiedPkColumnValue = generatorAnnotation.getPkColumnValue();
		this.uniqueConstraintContainer = this.buildUniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel(IProgressMonitor monitor) {
		super.synchronizeWithResourceModel(monitor);
		this.setSpecifiedTableName_(this.generatorAnnotation.getTable());
		this.setSpecifiedSchema_(this.generatorAnnotation.getSchema());
		this.setSpecifiedCatalog_(this.generatorAnnotation.getCatalog());
		this.setSpecifiedPkColumnName_(this.generatorAnnotation.getPkColumnName());
		this.setSpecifiedValueColumnName_(this.generatorAnnotation.getValueColumnName());
		this.setSpecifiedPkColumnValue_(this.generatorAnnotation.getPkColumnValue());
		this.syncUniqueConstraints();
	}

	@Override
	public void update(IProgressMonitor monitor) {
		super.update(monitor);
		this.setDefaultTableName(this.buildDefaultTableName());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultPkColumnName(this.buildDefaultPkColumnName());
		this.setDefaultValueColumnName(this.buildDefaultValueColumnName());
		this.setDefaultPkColumnValue(this.buildDefaultPkColumnValue());
		this.updateModels(this.getUniqueConstraints(), monitor);
	}


	// ********** initial value **********

	@Override
	protected int buildDefaultInitialValue() {
		return DEFAULT_INITIAL_VALUE;
	}
	

	// ********** table name **********

	public String getTableName() {
		return (this.specifiedTableName != null) ? this.specifiedTableName : this.defaultTableName;
	}

	public String getSpecifiedTableName() {
		return this.specifiedTableName;
	}

	public void setSpecifiedTableName(String tableName) {
		this.generatorAnnotation.setTable(tableName);
		this.setSpecifiedTableName_(tableName);
	}

	protected void setSpecifiedTableName_(String tableName) {
		String old = this.specifiedTableName;
		this.specifiedTableName = tableName;
		this.firePropertyChanged(SPECIFIED_TABLE_NAME_PROPERTY, old, tableName);
	}

	public String getDefaultTableName() {
		return this.defaultTableName;
	}

	protected void setDefaultTableName(String tableName) {
		String old = this.defaultTableName;
		this.defaultTableName = tableName;
		this.firePropertyChanged(DEFAULT_TABLE_NAME_PROPERTY, old, tableName);
	}

	protected String buildDefaultTableName() {
		return null; // TODO the default table is determined by the runtime provider...
	}

	public Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getTableName());
	}


	// ********** schema **********

	@Override
	public String getSchema() {
		return (this.specifiedSchema != null) ? this.specifiedSchema : this.defaultSchema;
	}

	public String getSpecifiedSchema() {
		return this.specifiedSchema;
	}

	public void setSpecifiedSchema(String schema) {
		this.generatorAnnotation.setSchema(schema);
		this.setSpecifiedSchema_(schema);
	}

	protected void setSpecifiedSchema_(String schema) {
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
	}

	public String getDefaultSchema() {
		return this.defaultSchema;
	}

	protected void setDefaultSchema(String schema) {
		String old = this.defaultSchema;
		this.defaultSchema = schema;
		this.firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, old, schema);
	}

	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}


	// ********** catalog **********

	@Override
	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		this.generatorAnnotation.setCatalog(catalog);
		this.setSpecifiedCatalog_(catalog);
	}

	protected void setSpecifiedCatalog_(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
	}

	public String getDefaultCatalog() {
		return this.defaultCatalog;
	}

	protected void setDefaultCatalog(String catalog) {
		String old = this.defaultCatalog;
		this.defaultCatalog = catalog;
		this.firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}

	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}


	// ********** primary key column name **********

	public String getPkColumnName() {
		return (this.specifiedPkColumnName != null) ? this.specifiedPkColumnName : this.defaultPkColumnName;
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}

	public void setSpecifiedPkColumnName(String name) {
		this.generatorAnnotation.setPkColumnName(name);
		this.setSpecifiedPkColumnName_(name);
	}

	protected void setSpecifiedPkColumnName_(String name) {
		String old = this.specifiedPkColumnName;
		this.specifiedPkColumnName = name;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	public String getDefaultPkColumnName() {
		return this.defaultPkColumnName;
	}

	protected void setDefaultPkColumnName(String name) {
		String old = this.defaultPkColumnName;
		this.defaultPkColumnName = name;
		this.firePropertyChanged(DEFAULT_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultPkColumnName() {
		return null; // TODO the default pk column name is determined by the runtime provider...
	}


	// ********** value column name **********

	public String getValueColumnName() {
		return (this.specifiedValueColumnName != null) ? this.specifiedValueColumnName : this.defaultValueColumnName;
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String name) {
		this.generatorAnnotation.setValueColumnName(name);
		this.setSpecifiedValueColumnName_(name);
	}

	protected void setSpecifiedValueColumnName_(String name) {
		String old = this.specifiedValueColumnName;
		this.specifiedValueColumnName = name;
		this.firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	public String getDefaultValueColumnName() {
		return this.defaultValueColumnName;
	}

	protected void setDefaultValueColumnName(String name) {
		String old = this.defaultValueColumnName;
		this.defaultValueColumnName = name;
		this.firePropertyChanged(DEFAULT_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	protected String buildDefaultValueColumnName() {
		return null; // TODO the default value column name is determined by the runtime provider...
	}


	// ********** primary key column value **********

	public String getPkColumnValue() {
		return (this.specifiedPkColumnValue != null) ? this.specifiedPkColumnValue : this.defaultPkColumnValue;
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String value) {
		this.generatorAnnotation.setPkColumnValue(value);
		this.setSpecifiedPkColumnValue_(value);
	}

	protected void setSpecifiedPkColumnValue_(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}

	protected void setDefaultPkColumnValue(String value) {
		String old = this.defaultPkColumnValue;
		this.defaultPkColumnValue = value;
		this.firePropertyChanged(DEFAULT_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	protected String buildDefaultPkColumnValue() {
		return null; // TODO the default pk column value is determined by the runtime provider...
	}


	// ********** unique constraints **********
	public ListIterable<JavaSpecifiedUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer;
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.size();
	}

	public JavaSpecifiedUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.get(index);
	}

	public JavaSpecifiedUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public JavaSpecifiedUniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraintAnnotation annotation = this.getGeneratorAnnotation().addUniqueConstraint(index);
		return this.uniqueConstraintContainer.addContextElement(index, annotation);
	}

	public void removeUniqueConstraint(int index) {
		this.getGeneratorAnnotation().removeUniqueConstraint(index);
		this.uniqueConstraintContainer.remove(index);
	}

	public void removeUniqueConstraint(SpecifiedUniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOf((JavaSpecifiedUniqueConstraint) uniqueConstraint));
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.getGeneratorAnnotation().moveUniqueConstraint(targetIndex, sourceIndex);
		this.uniqueConstraintContainer.move(targetIndex, sourceIndex);
	}

	protected void syncUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected void updateUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected JavaSpecifiedUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation constraintAnnotation) {
		return this.getJpaFactory().buildJavaUniqueConstraint(this, constraintAnnotation);
	}

	protected ListIterable<UniqueConstraintAnnotation> getUniqueConstraintAnnotations() {
		return this.getGeneratorAnnotation().getUniqueConstraints();
	}

	protected ContextListContainer<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation> buildUniqueConstraintContainer() {
		return this.buildSpecifiedContextListContainer(UNIQUE_CONSTRAINTS_LIST, new UniqueConstraintContainerAdapter());
	}

	/**
	 * unique constraint container adapter
	 */
	public class UniqueConstraintContainerAdapter
		extends AbstractContainerAdapter<JavaSpecifiedUniqueConstraint, UniqueConstraintAnnotation>
	{
		public JavaSpecifiedUniqueConstraint buildContextElement(UniqueConstraintAnnotation resourceElement) {
			return GenericJavaTableGenerator.this.buildUniqueConstraint(resourceElement);
		}
		public ListIterable<UniqueConstraintAnnotation> getResourceElements() {
			return GenericJavaTableGenerator.this.getUniqueConstraintAnnotations();
		}
		public UniqueConstraintAnnotation extractResourceElement(JavaSpecifiedUniqueConstraint contextElement) {
			return contextElement.getUniqueConstraintAnnotation();
		}
	}


	// ********** SpecifiedUniqueConstraint.Parent implementation **********

	public Iterable<String> getCandidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.jpa.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getCompletionProposals(int pos) {
		Iterable<String> result = super.getCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		for (JavaSpecifiedUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = constraint.getCompletionProposals(pos);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * called if the database is connected:
	 * table, schema, catalog, pkColumnName, valueColumnName
	 */
	@Override
	protected Iterable<String> getConnectedCompletionProposals(int pos) {
		Iterable<String> result = super.getConnectedCompletionProposals(pos);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos)) {
			return this.getJavaCandidateTables();
		}
		if (this.schemaTouches(pos)) {
			return this.getJavaCandidateSchemata();
		}
		if (this.pkColumnNameTouches(pos)) {
			return this.getJavaCandidateColumnNames();
		}
		if (this.valueColumnNameTouches(pos)) {
			return this.getJavaCandidateColumnNames();
		}
		return null;
	}

	// ********** code assist: table

	protected boolean tableTouches(int pos) {
		return this.generatorAnnotation.tableTouches(pos);
	}

	protected Iterable<String> getJavaCandidateTables() {
		return new TransformationIterable<String, String>(this.getCandidateTables(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateTables() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: schema

	protected boolean schemaTouches(int pos) {
		return this.generatorAnnotation.schemaTouches(pos);
	}

	protected Iterable<String> getJavaCandidateSchemata() {
		return new TransformationIterable<String, String>(this.getCandidateSchemata(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateSchemata() {
		SchemaContainer schemaContainer = this.getDbSchemaContainer();
		return (schemaContainer != null) ? schemaContainer.getSortedSchemaIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: catalog

	protected boolean catalogTouches(int pos) {
		return this.generatorAnnotation.catalogTouches(pos);
	}

	protected Iterable<String> getJavaCandidateCatalogs(Predicate<String> filter) {
		return new TransformationIterable<String, String>(this.getCandidateCatalogs(filter),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateCatalogs(Predicate<String> filter) {
		return IterableTools.filter(this.getCandidateCatalogs(), filter);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: pkColumnName

	protected boolean pkColumnNameTouches(int pos) {
		return this.generatorAnnotation.pkColumnNameTouches(pos);
	}

	protected Iterable<String> getJavaCandidateColumnNames() {
		return new TransformationIterable<String, String>(this.getCandidateColumnNames(),
				StringTools.JAVA_STRING_LITERAL_CONTENT_TRANSFORMER);
	}

	protected Iterable<String> getCandidateColumnNames() {
		Table table = this.getDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: valueColumnName

	protected boolean valueColumnNameTouches(int pos) {
		return this.generatorAnnotation.valueColumnNameTouches(pos);
	}

	// ********** metadata conversion **********
	
	public void convertTo(EntityMappings entityMappings) {
		entityMappings.addTableGenerator().convertFrom(this);
	}

	public void delete() {
		this.parent.removeTableGenerator();
	}
	
	// ********** misc **********

	public Class<TableGenerator> getGeneratorType() {
		return TableGenerator.class;
	}

	// ********** validation **********

	@Override
	protected boolean isEquivalentTo_(DatabaseGenerator other) {
		return super.isEquivalentTo_(other)
				&& this.isEquivalentTo_((TableGenerator) other);
	}

	protected boolean isEquivalentTo_(TableGenerator other) {
		return ObjectTools.equals(this.specifiedTableName, other.getSpecifiedTableName()) &&
				ObjectTools.equals(this.specifiedSchema, other.getSpecifiedSchema()) &&
				ObjectTools.equals(this.specifiedCatalog, other.getSpecifiedCatalog()) &&
				ObjectTools.equals(this.specifiedPkColumnName, other.getSpecifiedPkColumnName()) &&
				ObjectTools.equals(this.specifiedPkColumnValue, other.getSpecifiedPkColumnValue()) &&
				ObjectTools.equals(this.specifiedValueColumnName, other.getSpecifiedValueColumnName()) &&
				this.uniqueConstraintsAreEquivalentTo(other);
	}

	protected boolean uniqueConstraintsAreEquivalentTo(TableGenerator generator) {
		// get fixed lists of the unique constraints
		ArrayList<JavaSpecifiedUniqueConstraint> uniqueConstraints1 = ListTools.arrayList(this.getUniqueConstraints());
		ArrayList<SpecifiedUniqueConstraint> uniqueConstraints2 = ListTools.arrayList(generator.getUniqueConstraints());
		if (uniqueConstraints1.size() != uniqueConstraints2.size()) {
			return false;
		}
		for (int i = 0; i < uniqueConstraints1.size(); i++) {
			if ( ! uniqueConstraints1.get(i).isEquivalentTo(uniqueConstraints2.get(i))) {
				return false;
			}
		}
		return true;
	}
}
