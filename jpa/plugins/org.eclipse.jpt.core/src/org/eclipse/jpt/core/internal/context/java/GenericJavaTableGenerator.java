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
import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class GenericJavaTableGenerator extends AbstractJavaGenerator
	implements JavaTableGenerator, UniqueConstraint.Owner
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
	
	protected final List<JavaUniqueConstraint> uniqueConstraints;

	public GenericJavaTableGenerator(JavaJpaContextNode parent) {
		super(parent);
		this.uniqueConstraints = new ArrayList<JavaUniqueConstraint>();
	}

	@Override
	protected TableGeneratorAnnotation getGeneratorResource() {
		return (TableGeneratorAnnotation) super.getGeneratorResource();
	}

	public Integer getDefaultInitialValue() {
		return TableGenerator.DEFAULT_INITIAL_VALUE;
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
		getGeneratorResource().setTable(newSpecifiedTable);
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

	public String getCatalog() {
		return (this.getSpecifiedCatalog() == null) ? getDefaultCatalog() : this.getSpecifiedCatalog();
	}

	public String getSpecifiedCatalog() {
		return this.specifiedCatalog;
	}

	public void setSpecifiedCatalog(String newSpecifiedCatalog) {
		String oldSpecifiedCatalog = this.specifiedCatalog;
		this.specifiedCatalog = newSpecifiedCatalog;
		getGeneratorResource().setCatalog(newSpecifiedCatalog);
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

	protected void setDefaultCatalog(String newDefaultCatalog) {
		String oldDefaultCatalog = this.defaultCatalog;
		this.defaultCatalog = newDefaultCatalog;
		firePropertyChanged(DEFAULT_CATALOG_PROPERTY, oldDefaultCatalog, newDefaultCatalog);
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
		getGeneratorResource().setSchema(newSpecifiedSchema);
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
		firePropertyChanged(DEFAULT_SCHEMA_PROPERTY, oldDefaultSchema, newDefaultSchema);
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
		getGeneratorResource().setPkColumnName(newSpecifiedPkColumnName);
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

	public String getValueColumnName() {
		return (this.getSpecifiedValueColumnName() == null) ? getDefaultValueColumnName() : this.getSpecifiedValueColumnName();
	}

	public String getSpecifiedValueColumnName() {
		return this.specifiedValueColumnName;
	}

	public void setSpecifiedValueColumnName(String newSpecifiedValueColumnName) {
		String oldSpecifiedValueColumnName = this.specifiedValueColumnName;
		this.specifiedValueColumnName = newSpecifiedValueColumnName;
		getGeneratorResource().setValueColumnName(newSpecifiedValueColumnName);
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

	public String getPkColumnValue() {
		return (this.getSpecifiedPkColumnValue() == null) ? getDefaultPkColumnValue() : this.getSpecifiedPkColumnValue();
	}

	public String getSpecifiedPkColumnValue() {
		return this.specifiedPkColumnValue;
	}

	public void setSpecifiedPkColumnValue(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		getGeneratorResource().setPkColumnValue(newSpecifiedPkColumnValue);
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
	}

	public void setSpecifiedPkColumnValue_(String newSpecifiedPkColumnValue) {
		String oldSpecifiedPkColumnValue = this.specifiedPkColumnValue;
		this.specifiedPkColumnValue = newSpecifiedPkColumnValue;
		firePropertyChanged(SPECIFIED_PK_COLUMN_VALUE_PROPERTY, oldSpecifiedPkColumnValue, newSpecifiedPkColumnValue);
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
		UniqueConstraintAnnotation uniqueConstraintAnnotation = this.getGeneratorResource().addUniqueConstraint(index);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		fireItemAdded(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
		
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		JavaUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		this.getGeneratorResource().removeUniqueConstraint(index);
		fireItemRemoved(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getGeneratorResource().moveUniqueConstraint(targetIndex, sourceIndex);
		fireItemMoved(TableGenerator.UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);		
	}
	
	protected void addUniqueConstraint(int index, JavaUniqueConstraint uniqueConstraint) {
		addItemToList(index, uniqueConstraint, this.uniqueConstraints, TableGenerator.UNIQUE_CONSTRAINTS_LIST);
	}
	
	protected void removeUniqueConstraint_(JavaUniqueConstraint uniqueConstraint) {
		removeItemFromList(uniqueConstraint, this.uniqueConstraints, TableGenerator.UNIQUE_CONSTRAINTS_LIST);
	}
	
	
	//******************* UniqueConstraint.Owner implementation ******************

	public Iterator<String> candidateUniqueConstraintColumnNames() {
		org.eclipse.jpt.db.Table dbTable = getDbTable();
		if (dbTable != null) {
			return dbTable.columnNames();
		}
		return EmptyIterator.instance();
	}


	// ********** java resource model -> java context model **********
	
	public void initializeFromResource(TableGeneratorAnnotation tableGenerator) {
		super.initializeFromResource(tableGenerator);
		this.specifiedTable = this.specifiedTable(tableGenerator);
		this.specifiedCatalog = this.specifiedCatalog(tableGenerator);
		this.specifiedSchema = this.specifiedSchema(tableGenerator);
		this.specifiedPkColumnName = this.specifiedPkColumnName(tableGenerator);
		this.specifiedValueColumnName = this.specifiedValueColumnName(tableGenerator);
		this.specifiedPkColumnValue = this.specifiedPkColumnValue(tableGenerator);
		this.initializeUniqueConstraints(tableGenerator);
	}

	protected void initializeUniqueConstraints(TableGeneratorAnnotation tableGenerator) {
		for (UniqueConstraintAnnotation uniqueConstraintAnnotation : CollectionTools.iterable(tableGenerator.uniqueConstraints())) {
			this.uniqueConstraints.add(buildUniqueConstraint(uniqueConstraintAnnotation));
		}
	}

	public void update(TableGeneratorAnnotation tableGenerator) {
		super.update(tableGenerator);
		this.setSpecifiedTable_(this.specifiedTable(tableGenerator));
		this.setSpecifiedCatalog_(this.specifiedCatalog(tableGenerator));
		this.setDefaultCatalog(this.defaultCatalog());
		this.setSpecifiedSchema_(this.specifiedSchema(tableGenerator));
		this.setDefaultSchema(this.defaultSchema());
		this.setSpecifiedPkColumnName_(this.specifiedPkColumnName(tableGenerator));
		this.setSpecifiedValueColumnName_(this.specifiedValueColumnName(tableGenerator));
		this.setSpecifiedPkColumnValue_(this.specifiedPkColumnValue(tableGenerator));
		this.updateUniqueConstraints(tableGenerator);
	}
	
	protected void updateUniqueConstraints(TableGeneratorAnnotation tableGenerator) {
		ListIterator<JavaUniqueConstraint> uniqueConstraints = uniqueConstraints();
		ListIterator<UniqueConstraintAnnotation> resourceUniqueConstraints = tableGenerator.uniqueConstraints();
		
		while (uniqueConstraints.hasNext()) {
			JavaUniqueConstraint uniqueConstraint = uniqueConstraints.next();
			if (resourceUniqueConstraints.hasNext()) {
				uniqueConstraint.update(resourceUniqueConstraints.next());
			}
			else {
				removeUniqueConstraint_(uniqueConstraint);
			}
		}
		
		while (resourceUniqueConstraints.hasNext()) {
			addUniqueConstraint(uniqueConstraintsSize(), buildUniqueConstraint(resourceUniqueConstraints.next()));
		}
	}

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation uniqueConstraintAnnotation) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		return uniqueConstraint;
	}
	
	protected String specifiedTable(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getTable();
	}
	
	protected String specifiedCatalog(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getCatalog();
	}
	
	protected String specifiedSchema(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getSchema();
	}
	
	protected String specifiedPkColumnName(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getPkColumnName();
	}
	
	protected String specifiedValueColumnName(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getValueColumnName();
	}
	
	protected String specifiedPkColumnValue(TableGeneratorAnnotation tableGenerator) {
		return tableGenerator.getPkColumnValue();
	}

	protected String defaultSchema() {
		if (getEntityMappings() != null) {
			return getEntityMappings().getSchema();
		}
		return getPersistenceUnit().getDefaultSchema();
	}
	
	protected String defaultCatalog() {
		if (getEntityMappings() != null) {
			return getEntityMappings().getCatalog();
		}
		return getPersistenceUnit().getDefaultCatalog();
	}

	public Table getDbTable() {
		Schema schema = this.getDbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getTable());
	}

	public Schema getDbSchema() {
		return this.getDatabase().schemaNamed(this.getSchema());
	}

}
