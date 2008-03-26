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

import org.eclipse.jpt.core.context.TableGenerator;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;


public class GenericJavaTableGenerator extends AbstractJavaGenerator
	implements JavaTableGenerator
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

	public GenericJavaTableGenerator(JavaJpaContextNode parent) {
		super(parent);
	}

	@Override
	protected TableGeneratorAnnotation getGeneratorResource() {
		return (TableGeneratorAnnotation) super.getGeneratorResource();
	}
	
	public void initializeFromResource(TableGeneratorAnnotation tableGenerator) {
		super.initializeFromResource(tableGenerator);
		this.specifiedTable = this.specifiedTable(tableGenerator);
		this.specifiedCatalog = this.specifiedCatalog(tableGenerator);
		this.specifiedSchema = this.specifiedSchema(tableGenerator);
		this.specifiedPkColumnName = this.specifiedPkColumnName(tableGenerator);
		this.specifiedValueColumnName = this.specifiedValueColumnName(tableGenerator);
		this.specifiedPkColumnValue = this.specifiedPkColumnValue(tableGenerator);
		//this.updateUniqueConstraintsFromJava(astRoot);
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


//	public EList<IUniqueConstraint> getUniqueConstraints() {
//		if (uniqueConstraints == null) {
//			uniqueConstraints = new EObjectContainmentEList<IUniqueConstraint>(IUniqueConstraint.class, this, JpaJavaMappingsPackage.JAVA_TABLE_GENERATOR__UNIQUE_CONSTRAINTS);
//		}
//		return uniqueConstraints;
//	}



	// ********** java resource model -> context model model **********
	public void update(TableGeneratorAnnotation tableGenerator) {
		super.update(tableGenerator);
		this.setSpecifiedTable_(this.specifiedTable(tableGenerator));
		this.setSpecifiedCatalog_(this.specifiedCatalog(tableGenerator));
		this.setSpecifiedSchema_(this.specifiedSchema(tableGenerator));
		this.setSpecifiedPkColumnName_(this.specifiedPkColumnName(tableGenerator));
		this.setSpecifiedValueColumnName_(this.specifiedValueColumnName(tableGenerator));
		this.setSpecifiedPkColumnValue_(this.specifiedPkColumnValue(tableGenerator));
		//this.updateUniqueConstraintsFromJava(astRoot);
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


//	/**
//	 * here we just worry about getting the unique constraints lists the same size;
//	 * then we delegate to the unique constraints to synch themselves up
//	 */
//	private void updateUniqueConstraintsFromJava(CompilationUnit astRoot) {
//		// synchronize the model join columns with the Java source
//		List<IUniqueConstraint> constraints = this.getUniqueConstraints();
//		int persSize = constraints.size();
//		int javaSize = 0;
//		boolean allJavaAnnotationsFound = false;
//		for (int i = 0; i < persSize; i++) {
//			JavaUniqueConstraint uniqueConstraint = (JavaUniqueConstraint) constraints.get(i);
//			if (uniqueConstraint.annotation(astRoot) == null) {
//				allJavaAnnotationsFound = true;
//				break; // no need to go any further
//			}
//			uniqueConstraint.updateFromJava(astRoot);
//			javaSize++;
//		}
//		if (allJavaAnnotationsFound) {
//			// remove any model join columns beyond those that correspond to the Java annotations
//			while (persSize > javaSize) {
//				persSize--;
//				constraints.remove(persSize);
//			}
//		}
//		else {
//			// add new model join columns until they match the Java annotations
//			while (!allJavaAnnotationsFound) {
//				JavaUniqueConstraint uniqueConstraint = this.createJavaUniqueConstraint(javaSize);
//				if (uniqueConstraint.annotation(astRoot) == null) {
//					allJavaAnnotationsFound = true;
//				}
//				else {
//					this.getUniqueConstraints().add(uniqueConstraint);
//					uniqueConstraint.updateFromJava(astRoot);
//					javaSize++;
//				}
//			}
//		}
//	}
//
//	public void refreshDefaults(DefaultsContext defaultsContext) {
//		setDefaultSchema((String) defaultsContext.getDefault(GenericJpaPlatform.DEFAULT_TABLE_GENERATOR_SCHEMA_KEY));
//	}
//
//	public IUniqueConstraint createUniqueConstraint(int index) {
//		return createJavaUniqueConstraint(index);
//	}
//
//	protected JavaUniqueConstraint createJavaUniqueConstraint(int index) {
//		return JavaUniqueConstraint.createTableGeneratorUniqueConstraint(new UniqueConstraintOwner(this), this.getMember(), index);
//	}

	public Table getDbTable() {
		Schema schema = this.getDbSchema();
		return (schema == null) ? null : schema.tableNamed(this.getTable());
	}

	public Schema getDbSchema() {
		return this.getDatabase().schemaNamed(this.getSchema());
	}

}
