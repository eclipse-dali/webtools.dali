/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.utility.Filter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaGenerator;
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
	extends AbstractJavaGenerator<TableGeneratorAnnotation>
	implements JavaTableGenerator, UniqueConstraint.Owner
{
	protected String specifiedTable;
	protected String defaultTable;

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

	protected final UniqueConstraintContainer uniqueConstraintContainer;


	// ********** constructor **********

	public GenericJavaTableGenerator(JavaJpaContextNode parent, TableGeneratorAnnotation generatorAnnotation) {
		super(parent, generatorAnnotation);
		this.specifiedTable = generatorAnnotation.getTable();
		this.specifiedSchema = generatorAnnotation.getSchema();
		this.specifiedCatalog = generatorAnnotation.getCatalog();
		this.specifiedPkColumnName = generatorAnnotation.getPkColumnName();
		this.specifiedValueColumnName = generatorAnnotation.getValueColumnName();
		this.specifiedPkColumnValue = generatorAnnotation.getPkColumnValue();
		this.uniqueConstraintContainer = new UniqueConstraintContainer();
	}


	// ********** synchronize/update **********

	@Override
	public void synchronizeWithResourceModel() {
		super.synchronizeWithResourceModel();
		this.setSpecifiedTable_(this.generatorAnnotation.getTable());
		this.setSpecifiedSchema_(this.generatorAnnotation.getSchema());
		this.setSpecifiedCatalog_(this.generatorAnnotation.getCatalog());
		this.setSpecifiedPkColumnName_(this.generatorAnnotation.getPkColumnName());
		this.setSpecifiedValueColumnName_(this.generatorAnnotation.getValueColumnName());
		this.setSpecifiedPkColumnValue_(this.generatorAnnotation.getPkColumnValue());
		this.syncUniqueConstraints();
	}

	@Override
	public void update() {
		super.update();
		this.setDefaultTable(this.buildDefaultTable());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setDefaultPkColumnName(this.buildDefaultPkColumnName());
		this.setDefaultValueColumnName(this.buildDefaultValueColumnName());
		this.setDefaultPkColumnValue(this.buildDefaultPkColumnValue());
		this.updateNodes(this.getUniqueConstraints());
	}


	// ********** initial value **********

	@Override
	protected int buildDefaultInitialValue() {
		return DEFAULT_INITIAL_VALUE;
	}
	

	// ********** table **********

	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}

	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		this.generatorAnnotation.setTable(table);
		this.setSpecifiedTable_(table);
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	public String getDefaultTable() {
		return this.defaultTable;
	}

	protected void setDefaultTable(String table) {
		String old = this.defaultTable;
		this.defaultTable = table;
		this.firePropertyChanged(DEFAULT_TABLE_PROPERTY, old, table);
	}

	protected String buildDefaultTable() {
		return null; // TODO the default table is determined by the runtime provider...
	}

	public Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getTable());
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
	public ListIterable<JavaUniqueConstraint> getUniqueConstraints() {
		return this.uniqueConstraintContainer.getContextElements();
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintContainer.getContextElementsSize();
	}

	public JavaUniqueConstraint getUniqueConstraint(int index) {
		return this.uniqueConstraintContainer.getContextElement(index);
	}

	public JavaUniqueConstraint addUniqueConstraint() {
		return this.addUniqueConstraint(this.getUniqueConstraintsSize());
	}

	public JavaUniqueConstraint addUniqueConstraint(int index) {
		UniqueConstraintAnnotation annotation = this.getGeneratorAnnotation().addUniqueConstraint(index);
		return this.uniqueConstraintContainer.addContextElement(index, annotation);
	}

	public void removeUniqueConstraint(int index) {
		this.getGeneratorAnnotation().removeUniqueConstraint(index);
		this.uniqueConstraintContainer.removeContextElement(index);
	}

	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraintContainer.indexOfContextElement((JavaUniqueConstraint) uniqueConstraint));
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.getGeneratorAnnotation().moveUniqueConstraint(targetIndex, sourceIndex);
		this.uniqueConstraintContainer.moveContextElement(targetIndex, sourceIndex);
	}

	protected void syncUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected void updateUniqueConstraints() {
		this.uniqueConstraintContainer.synchronizeWithResourceModel();
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation constraintAnnotation) {
		return this.getJpaFactory().buildJavaUniqueConstraint(this, this, constraintAnnotation);
	}

	protected ListIterable<UniqueConstraintAnnotation> getUniqueConstraintAnnotations() {
		return this.getGeneratorAnnotation().getUniqueConstraints();
	}


	/**
	 * unique constraint container
	 */
	protected class UniqueConstraintContainer
		extends ContextListContainer<JavaUniqueConstraint, UniqueConstraintAnnotation>
	{
		@Override
		protected String getContextElementsPropertyName() {
			return UNIQUE_CONSTRAINTS_LIST;
		}
		@Override
		protected JavaUniqueConstraint buildContextElement(UniqueConstraintAnnotation resourceElement) {
			return GenericJavaTableGenerator.this.buildUniqueConstraint(resourceElement);
		}
		@Override
		protected ListIterable<UniqueConstraintAnnotation> getResourceElements() {
			return GenericJavaTableGenerator.this.getUniqueConstraintAnnotations();
		}
		@Override
		protected UniqueConstraintAnnotation getResourceElement(JavaUniqueConstraint contextElement) {
			return contextElement.getUniqueConstraintAnnotation();
		}
	}


	// ********** UniqueConstraint.Owner implementation **********

	public Iterable<String> getCandidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.jpa.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.getSortedColumnIdentifiers() : EmptyIterable.<String>instance();
	}


	// ********** Java completion proposals **********

	@Override
	public Iterable<String> getJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaUniqueConstraint constraint : this.getUniqueConstraints()) {
			result = constraint.getJavaCompletionProposals(pos, filter, astRoot);
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
	protected Iterable<String> getConnectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterable<String> result = super.getConnectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos, astRoot)) {
			return this.getJavaCandidateTables(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.getJavaCandidateSchemata(filter);
		}
		if (this.pkColumnNameTouches(pos, astRoot)) {
			return this.getJavaCandidateColumnNames(filter);
		}
		if (this.valueColumnNameTouches(pos, astRoot)) {
			return this.getJavaCandidateColumnNames(filter);
		}
		return null;
	}

	// ********** code assist: table

	protected boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.tableTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateTables(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateTables(filter));
	}

	protected Iterable<String> getCandidateTables(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateTables(), filter);
	}

	protected Iterable<String> getCandidateTables() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.getSortedTableIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: schema

	protected boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.schemaTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateSchemata(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateSchemata(filter));
	}

	protected Iterable<String> getCandidateSchemata(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateSchemata(), filter);
	}

	protected Iterable<String> getCandidateSchemata() {
		SchemaContainer schemaContainer = this.getDbSchemaContainer();
		return (schemaContainer != null) ? schemaContainer.getSortedSchemaIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: catalog

	protected boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.catalogTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateCatalogs(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateCatalogs(filter));
	}

	protected Iterable<String> getCandidateCatalogs(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateCatalogs(), filter);
	}

	protected Iterable<String> getCandidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: pkColumnName

	protected boolean pkColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.pkColumnNameTouches(pos, astRoot);
	}

	protected Iterable<String> getJavaCandidateColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.getCandidateColumnNames(filter));
	}

	protected Iterable<String> getCandidateColumnNames(Filter<String> filter) {
		return new FilteringIterable<String>(this.getCandidateColumnNames(), filter);
	}

	protected Iterable<String> getCandidateColumnNames() {
		Table table = this.getDbTable();
		return (table != null) ? table.getSortedColumnIdentifiers() : EmptyIterable.<String> instance();
	}

	// ********** code assist: valueColumnName

	protected boolean valueColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.generatorAnnotation.valueColumnNameTouches(pos, astRoot);
	}

}
