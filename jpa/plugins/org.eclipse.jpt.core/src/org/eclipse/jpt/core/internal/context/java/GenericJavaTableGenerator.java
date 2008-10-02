/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.Filter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.iterators.FilteringIterator;

/**
 * 
 */
public class GenericJavaTableGenerator
	extends AbstractJavaGenerator
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
	
	protected final List<JavaUniqueConstraint> uniqueConstraints;


	// ********** constructor **********

	public GenericJavaTableGenerator(JavaJpaContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<JavaUniqueConstraint>();
	}


	// ********** table **********

	public String getTable() {
		return (this.specifiedTable != null) ? this.specifiedTable : this.defaultTable;
	}
	
	public String getSpecifiedTable() {
		return this.specifiedTable;
	}

	public void setSpecifiedTable(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.getResourceGenerator().setTable(table);
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	protected void setSpecifiedTable_(String table) {
		String old = this.specifiedTable;
		this.specifiedTable = table;
		this.firePropertyChanged(SPECIFIED_TABLE_PROPERTY, old, table);
	}

	/**
	 * The default table is determined by the JPA implementation.
	 */
	public String getDefaultTable() {
		return this.defaultTable;
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
		String old = this.specifiedSchema;
		this.specifiedSchema = schema;
		this.getResourceGenerator().setSchema(schema);
		this.firePropertyChanged(SPECIFIED_SCHEMA_PROPERTY, old, schema);
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


	// ********** catalog **********

	@Override
	public String getCatalog() {
		return (this.specifiedCatalog != null) ? this.specifiedCatalog : this.defaultCatalog;
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String catalog) {
		String old = this.specifiedCatalog;
		this.specifiedCatalog = catalog;
		this.getResourceGenerator().setCatalog(catalog);
		this.firePropertyChanged(SPECIFIED_CATALOG_PROPERTY, old, catalog);
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
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, old, catalog);
	}


	// ********** primary key column name **********

	public String getPkColumnName() {
		return (this.specifiedPkColumnName != null) ? this.specifiedPkColumnName : this.defaultPkColumnName;
	}

	public String getSpecifiedPkColumnName() {
		return this.specifiedPkColumnName;
	}

	public void setSpecifiedPkColumnName(String name) {
		String old = this.specifiedPkColumnName;
		this.specifiedPkColumnName = name;
		this.getResourceGenerator().setPkColumnName(name);
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	protected void setSpecifiedPkColumnName_(String name) {
		String old = this.specifiedPkColumnName;
		this.specifiedPkColumnName = name;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_NAME_PROPERTY, old, name);
	}

	/**
	 * The default primary key column name is determined by the JPA
	 * implementation.
	 */
	public String getDefaultPkColumnName() {
		return this.defaultPkColumnName;
	}


	// ********** value column name **********

	public String getValueColumnName() {
		return (this.specifiedValueColumnName != null) ? this.specifiedValueColumnName : this.defaultValueColumnName;
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String name) {
		String old = this.specifiedValueColumnName;
		this.specifiedValueColumnName = name;
		this.getResourceGenerator().setValueColumnName(name);
		this.firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	protected void setSpecifiedValueColumnName_(String name) {
		String old = this.specifiedValueColumnName;
		this.specifiedValueColumnName = name;
		this.firePropertyChanged(SPECIFIED_VALUE_COLUMN_NAME_PROPERTY, old, name);
	}

	public String getDefaultValueColumnName() {
		return this.defaultValueColumnName;
	}


	// ********** primary key column value **********

	public String getPkColumnValue() {
		return (this.specifiedPkColumnValue != null) ? this.specifiedPkColumnValue : this.defaultPkColumnValue;
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.getResourceGenerator().setPkColumnValue(value);
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	protected void setSpecifiedPkColumnValue_(String value) {
		String old = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = value;
		this.firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, old, value);
	}

	public String getDefaultPkColumnValue() {
		return this.defaultPkColumnValue;
	}


	// ********** unique constraints **********
	
	public ListIterator<JavaUniqueConstraint> uniqueConstraints() {
		return new CloneListIterator<JavaUniqueConstraint>(this.uniqueConstraints);
	}
	
	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}
	
	public JavaUniqueConstraint addUniqueConstraint(int index) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		this.uniqueConstraints.add(index, uniqueConstraint);
		UniqueConstraintAnnotation uniqueConstraintAnnotation = this.getResourceGenerator().addUniqueConstraint(index);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
		
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		JavaUniqueConstraint uniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceGenerator().removeUniqueConstraint(index);
		this.fireItemRemoved(UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceGenerator().moveUniqueConstraint(targetIndex, sourceIndex);
		this.fireItemMoved(UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addUniqueConstraint(int index, JavaUniqueConstraint uniqueConstraint) {
		this.addItemToList(index, uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}
	
	protected void addUniqueConstraint(JavaUniqueConstraint uniqueConstraint) {
		this.addUniqueConstraint(this.uniqueConstraints.size(), uniqueConstraint);
	}
	
	protected void removeUniqueConstraint_(JavaUniqueConstraint uniqueConstraint) {
		this.removeItemFromList(uniqueConstraint, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}


	//******************* UniqueConstraint.Owner implementation ******************

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = this.getDbTable();
		return (dbTable != null) ? dbTable.sortedColumnIdentifiers() : EmptyIterator.<String>instance();
	}


	// ********** resource => context **********

	public void initialize(TableGeneratorAnnotation tableGeneratorAnnotation) {
		super.initialize(tableGeneratorAnnotation);
		this.specifiedTable = tableGeneratorAnnotation.getTable();
		this.defaultSchema = this.buildDefaultSchema();
		this.specifiedSchema = tableGeneratorAnnotation.getSchema();
		this.defaultCatalog = this.buildDefaultCatalog();
		this.specifiedCatalog = tableGeneratorAnnotation.getCatalog();
		this.specifiedPkColumnName = tableGeneratorAnnotation.getPkColumnName();
		this.specifiedValueColumnName = tableGeneratorAnnotation.getValueColumnName();
		this.specifiedPkColumnValue = tableGeneratorAnnotation.getPkColumnValue();
		this.initializeUniqueConstraints(tableGeneratorAnnotation);
	}

	protected void initializeUniqueConstraints(TableGeneratorAnnotation tableGeneratorAnnotation) {
		for (Iterator<UniqueConstraintAnnotation> stream = tableGeneratorAnnotation.uniqueConstraints(); stream.hasNext(); ) {
			this.uniqueConstraints.add(this.buildUniqueConstraint(stream.next()));
		}
	}

	public void update(TableGeneratorAnnotation tableGeneratorAnnotation) {
		super.update(tableGeneratorAnnotation);
		this.setSpecifiedTable_(tableGeneratorAnnotation.getTable());
		this.setDefaultSchema(this.buildDefaultSchema());
		this.setSpecifiedSchema_(tableGeneratorAnnotation.getSchema());
		this.setDefaultCatalog(this.buildDefaultCatalog());
		this.setSpecifiedCatalog_(tableGeneratorAnnotation.getCatalog());
		this.setSpecifiedPkColumnName_(tableGeneratorAnnotation.getPkColumnName());
		this.setSpecifiedValueColumnName_(tableGeneratorAnnotation.getValueColumnName());
		this.setSpecifiedPkColumnValue_(tableGeneratorAnnotation.getPkColumnValue());
		this.updateUniqueConstraints(tableGeneratorAnnotation);
	}
	
	protected String buildDefaultSchema() {
		return this.getContextDefaultSchema();
	}

	protected String buildDefaultCatalog() {
		return this.getContextDefaultCatalog();
	}

	protected void updateUniqueConstraints(TableGeneratorAnnotation tableGeneratorAnnotation) {
		ListIterator<JavaUniqueConstraint> contextConstraints = this.uniqueConstraints();
		ListIterator<UniqueConstraintAnnotation> resourceConstraints = tableGeneratorAnnotation.uniqueConstraints();
		
		while (contextConstraints.hasNext()) {
			JavaUniqueConstraint uniqueConstraint = contextConstraints.next();
			if (resourceConstraints.hasNext()) {
				uniqueConstraint.update(resourceConstraints.next());
			}
			else {
				removeUniqueConstraint_(uniqueConstraint);
			}
		}
		
		while (resourceConstraints.hasNext()) {
			addUniqueConstraint(buildUniqueConstraint(resourceConstraints.next()));
		}
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		return uniqueConstraint;
	}


	// ********** database stuff **********

	public Table getDbTable() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema == null) ? null : dbSchema.getTableForIdentifier(this.getTable());
	}


	// ********** Java completion proposals **********

	@Override
	public Iterator<String> javaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.javaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		for (JavaUniqueConstraint constraint : CollectionTools.iterable(this.uniqueConstraints())) {
			result = constraint.javaCompletionProposals(pos, filter, astRoot);
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
	public Iterator<String> connectedJavaCompletionProposals(int pos, Filter<String> filter, CompilationUnit astRoot) {
		Iterator<String> result = super.connectedJavaCompletionProposals(pos, filter, astRoot);
		if (result != null) {
			return result;
		}
		if (this.tableTouches(pos, astRoot)) {
			return this.javaCandidateTables(filter);
		}
		if (this.schemaTouches(pos, astRoot)) {
			return this.javaCandidateSchemata(filter);
		}
		if (this.catalogTouches(pos, astRoot)) {
			return this.javaCandidateCatalogs(filter);
		}
		if (this.pkColumnNameTouches(pos, astRoot)) {
			return this.javaCandidateColumnNames(filter);
		}
		if (this.valueColumnNameTouches(pos, astRoot)) {
			return this.javaCandidateColumnNames(filter);
		}
		return null;
	}

	protected boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().tableTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateTables(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateTables(filter));
	}

	protected Iterator<String> candidateTables(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateTables(), filter);
	}

	protected Iterator<String> candidateTables() {
		Schema dbSchema = this.getDbSchema();
		return (dbSchema != null) ? dbSchema.sortedTableIdentifiers() : EmptyIterator.<String> instance();
	}

	protected boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().schemaTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateSchemata(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateSchemata(filter));
	}

	protected Iterator<String> candidateSchemata(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateSchemata(), filter);
	}

	protected Iterator<String> candidateSchemata() {
		return this.getDbSchemaContainer().sortedSchemaIdentifiers();
	}

	protected boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().catalogTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateCatalogs(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateCatalogs(filter));
	}

	protected Iterator<String> candidateCatalogs(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateCatalogs(), filter);
	}

	protected Iterator<String> candidateCatalogs() {
		Database db = this.getDatabase();
		return (db != null) ? db.sortedCatalogIdentifiers() : EmptyIterator.<String> instance();
	}

	protected boolean pkColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().pkColumnNameTouches(pos, astRoot);
	}

	protected Iterator<String> javaCandidateColumnNames(Filter<String> filter) {
		return StringTools.convertToJavaStringLiterals(this.candidateColumnNames(filter));
	}

	protected Iterator<String> candidateColumnNames(Filter<String> filter) {
		return new FilteringIterator<String, String>(this.candidateColumnNames(), filter);
	}

	protected Iterator<String> candidateColumnNames() {
		Table table = this.getDbTable();
		return (table != null) ? table.sortedColumnIdentifiers() : EmptyIterator.<String> instance();
	}

	protected boolean valueColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.getResourceGenerator().valueColumnNameTouches(pos, astRoot);
	}


	// ********** misc **********

	@Override
	protected TableGeneratorAnnotation getResourceGenerator() {
		return (TableGeneratorAnnotation) super.getResourceGenerator();
	}

	public Integer getDefaultInitialValue() {
		return DEFAULT_INITIAL_VALUE;
	}

}
