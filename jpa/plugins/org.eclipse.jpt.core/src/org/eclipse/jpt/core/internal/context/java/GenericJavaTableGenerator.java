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
	protected TableGeneratorAnnotation getResourceGenerator() {
		return (TableGeneratorAnnotation) super.getResourceGenerator();
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
		getResourceGenerator().setTable(newSpecifiedTable);
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
		getResourceGenerator().setCatalog(newSpecifiedCatalog);
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
		getResourceGenerator().setSchema(newSpecifiedSchema);
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
		getResourceGenerator().setPkColumnName(newSpecifiedPkColumnName);
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
		getResourceGenerator().setValueColumnName(newSpecifiedValueColumnName);
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
		getResourceGenerator().setPkColumnValue(newSpecifiedPkColumnValue);
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
		UniqueConstraintAnnotation uniqueConstraintAnnotation = this.getResourceGenerator().addUniqueConstraint(index);
		uniqueConstraint.initialize(uniqueConstraintAnnotation);
		fireItemAdded(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
		return uniqueConstraint;
	}
		
	public void removeUniqueConstraint(UniqueConstraint uniqueConstraint) {
		this.removeUniqueConstraint(this.uniqueConstraints.indexOf(uniqueConstraint));
	}

	public void removeUniqueConstraint(int index) {
		JavaUniqueConstraint removedUniqueConstraint = this.uniqueConstraints.remove(index);
		this.getResourceGenerator().removeUniqueConstraint(index);
		fireItemRemoved(TableGenerator.UNIQUE_CONSTRAINTS_LIST, index, removedUniqueConstraint);
	}
	
	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex);
		this.getResourceGenerator().moveUniqueConstraint(targetIndex, sourceIndex);
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
	
	public void initialize(TableGeneratorAnnotation resourceTableGenerator) {
		super.initialize(resourceTableGenerator);
		this.specifiedTable = this.specifiedTable(resourceTableGenerator);
		this.specifiedCatalog = this.specifiedCatalog(resourceTableGenerator);
		this.defaultCatalog = this.defaultCatalog();
		this.specifiedSchema = this.specifiedSchema(resourceTableGenerator);
		this.defaultSchema = this.defaultSchema();
		this.specifiedPkColumnName = this.specifiedPkColumnName(resourceTableGenerator);
		this.specifiedValueColumnName = this.specifiedValueColumnName(resourceTableGenerator);
		this.specifiedPkColumnValue = this.specifiedPkColumnValue(resourceTableGenerator);
		this.initializeUniqueConstraints(resourceTableGenerator);
	}

	protected void initializeUniqueConstraints(TableGeneratorAnnotation resourceTableGenerator) {
		for (UniqueConstraintAnnotation resourceUniqueConstraint : CollectionTools.iterable(resourceTableGenerator.uniqueConstraints())) {
			this.uniqueConstraints.add(buildUniqueConstraint(resourceUniqueConstraint));
		}
	}

	public void update(TableGeneratorAnnotation resourceTableGenerator) {
		super.update(resourceTableGenerator);
		this.setSpecifiedTable_(this.specifiedTable(resourceTableGenerator));
		this.setSpecifiedCatalog_(this.specifiedCatalog(resourceTableGenerator));
		this.setDefaultCatalog(this.defaultCatalog());
		this.setSpecifiedSchema_(this.specifiedSchema(resourceTableGenerator));
		this.setDefaultSchema(this.defaultSchema());
		this.setSpecifiedPkColumnName_(this.specifiedPkColumnName(resourceTableGenerator));
		this.setSpecifiedValueColumnName_(this.specifiedValueColumnName(resourceTableGenerator));
		this.setSpecifiedPkColumnValue_(this.specifiedPkColumnValue(resourceTableGenerator));
		this.updateUniqueConstraints(resourceTableGenerator);
	}
	
	protected void updateUniqueConstraints(TableGeneratorAnnotation resourceTableGenerator) {
		ListIterator<JavaUniqueConstraint> uniqueConstraints = uniqueConstraints();
		ListIterator<UniqueConstraintAnnotation> resourceUniqueConstraints = resourceTableGenerator.uniqueConstraints();
		
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

	protected JavaUniqueConstraint buildUniqueConstraint(UniqueConstraintAnnotation resourceUniqueConstraint) {
		JavaUniqueConstraint uniqueConstraint = getJpaFactory().buildJavaUniqueConstraint(this, this);
		uniqueConstraint.initialize(resourceUniqueConstraint);
		return uniqueConstraint;
	}
	
	protected String specifiedTable(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getTable();
	}
	
	protected String specifiedCatalog(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getCatalog();
	}
	
	protected String specifiedSchema(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getSchema();
	}
	
	protected String specifiedPkColumnName(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getPkColumnName();
	}
	
	protected String specifiedValueColumnName(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getValueColumnName();
	}
	
	protected String specifiedPkColumnValue(TableGeneratorAnnotation resourceTableGenerator) {
		return resourceTableGenerator.getPkColumnValue();
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
		return (schema == null) ? null : schema.getTableNamed(this.getTable());
	}

	public Schema getDbSchema() {
		return this.getDataSource().getSchemaNamed(this.getSchema());
	}

}
