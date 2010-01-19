/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import java.util.ListIterator;
import java.util.Vector;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraintAnnotation;
import org.eclipse.jpt.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * javax.persistence.TableGenerator
 */
public final class SourceTableGeneratorAnnotation
	extends SourceGeneratorAnnotation
	implements TableGeneratorAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> TABLE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__TABLE);
	private final AnnotationElementAdapter<String> tableAdapter;
	private String table;

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__SCHEMA);
	private final AnnotationElementAdapter<String> schemaAdapter;
	private String schema;

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__CATALOG);
	private final AnnotationElementAdapter<String> catalogAdapter;
	private String catalog;

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_NAME);
	private final AnnotationElementAdapter<String> pkColumnNameAdapter;
	private String pkColumnName;

	private static final DeclarationAnnotationElementAdapter<String> VALUE_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__VALUE_COLUMN_NAME);
	private final AnnotationElementAdapter<String> valueColumnNameAdapter;
	private String valueColumnName;

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_VALUE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_VALUE);
	private final AnnotationElementAdapter<String> pkColumnValueAdapter;
	private String pkColumnValue;

	private final Vector<NestableUniqueConstraintAnnotation> uniqueConstraints = new Vector<NestableUniqueConstraintAnnotation>();
	private final UniqueConstraintsAnnotationContainer uniqueConstraintsContainer = new UniqueConstraintsAnnotationContainer();


	public SourceTableGeneratorAnnotation(JavaResourceNode parent, Member member) {
		super(parent, member, DECLARATION_ANNOTATION_ADAPTER);
		this.tableAdapter = this.buildAdapter(TABLE_ADAPTER);
		this.catalogAdapter = this.buildAdapter(CATALOG_ADAPTER);
		this.schemaAdapter = this.buildAdapter(SCHEMA_ADAPTER);
		this.pkColumnNameAdapter = this.buildAdapter(PK_COLUMN_NAME_ADAPTER);
		this.valueColumnNameAdapter = this.buildAdapter(VALUE_COLUMN_NAME_ADAPTER);
		this.pkColumnValueAdapter = this.buildAdapter(PK_COLUMN_VALUE_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.table = this.buildTable(astRoot);
		this.schema = this.buildSchema(astRoot);
		this.catalog = this.buildCatalog(astRoot);
		this.pkColumnName = this.buildPkColumnName(astRoot);
		this.valueColumnName = this.buildValueColumnName(astRoot);
		this.pkColumnValue = this.buildPkColumnValue(astRoot);
		AnnotationContainerTools.initialize(this.uniqueConstraintsContainer, astRoot);
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncTable(this.buildTable(astRoot));
		this.syncSchema(this.buildSchema(astRoot));
		this.syncCatalog(this.buildCatalog(astRoot));
		this.syncPkColumnName(this.buildPkColumnName(astRoot));
		this.syncValueColumnName(this.buildValueColumnName(astRoot));
		this.syncPkColumnValue(this.buildPkColumnValue(astRoot));
		AnnotationContainerTools.synchronize(this.uniqueConstraintsContainer, astRoot);
	}


	// ********** AbstractGeneratorAnnotation implementation **********

	@Override
	DeclarationAnnotationElementAdapter<String> getNameAdapter() {
		return NAME_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<Integer> getInitialValueAdapter() {
		return INITIAL_VALUE_ADAPTER;
	}

	@Override
	DeclarationAnnotationElementAdapter<Integer> getAllocationSizeAdapter() {
		return ALLOCATION_SIZE_ADAPTER;
	}


	// ********** TableGeneratorAnnotation implementation **********

	// ***** table
	public String getTable() {
		return this.table;
	}

	public void setTable(String table) {
		if (this.attributeValueHasChanged(this.table, table)) {
			this.table = table;
			this.tableAdapter.setValue(table);
		}
	}

	private void syncTable(String astTable) {
		String old = this.table;
		this.table = astTable;
		this.firePropertyChanged(TABLE_PROPERTY, old, astTable);
	}

	private String buildTable(CompilationUnit astRoot) {
		return this.tableAdapter.getValue(astRoot);
	}

	public TextRange getTableTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(TABLE_ADAPTER, astRoot);
	}

	public boolean tableTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(TABLE_ADAPTER, pos, astRoot);
	}

	// ***** schema
	public String getSchema() {
		return this.schema;
	}

	public void setSchema(String schema) {
		if (this.attributeValueHasChanged(this.schema, schema)) {
			this.schema = schema;
			this.schemaAdapter.setValue(schema);
		}
	}

	private void syncSchema(String astSchema) {
		String old = this.schema;
		this.schema = astSchema;
		this.firePropertyChanged(SCHEMA_PROPERTY, old, astSchema);
	}

	private String buildSchema(CompilationUnit astRoot) {
		return this.schemaAdapter.getValue(astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SCHEMA_ADAPTER, astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(SCHEMA_ADAPTER, pos, astRoot);
	}

	// ***** catalog
	public String getCatalog() {
		return this.catalog;
	}

	public void setCatalog(String catalog) {
		if (this.attributeValueHasChanged(this.catalog, catalog)) {
			this.catalog = catalog;
			this.catalogAdapter.setValue(catalog);
		}
	}

	private void syncCatalog(String astCatalog) {
		String old = this.catalog;
		this.catalog = astCatalog;
		this.firePropertyChanged(CATALOG_PROPERTY, old, astCatalog);
	}

	private String buildCatalog(CompilationUnit astRoot) {
		return this.catalogAdapter.getValue(astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(CATALOG_ADAPTER, astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(CATALOG_ADAPTER, pos, astRoot);
	}

	// ***** pk column name
	public String getPkColumnName() {
		return this.pkColumnName;
	}

	public void setPkColumnName(String pkColumnName) {
		if (this.attributeValueHasChanged(this.pkColumnName, pkColumnName)) {
			this.pkColumnName = pkColumnName;
			this.pkColumnNameAdapter.setValue(pkColumnName);
		}
	}

	private void syncPkColumnName(String astPkColumnName) {
		String old = this.pkColumnName;
		this.pkColumnName = astPkColumnName;
		this.firePropertyChanged(PK_COLUMN_NAME_PROPERTY, old, astPkColumnName);
	}

	private String buildPkColumnName(CompilationUnit astRoot) {
		return this.pkColumnNameAdapter.getValue(astRoot);
	}

	public TextRange getPkColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(PK_COLUMN_NAME_ADAPTER, astRoot);
	}

	public boolean pkColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(PK_COLUMN_NAME_ADAPTER, pos, astRoot);
	}

	// ***** value column name
	public String getValueColumnName() {
		return this.valueColumnName;
	}

	public void setValueColumnName(String valueColumnName) {
		if (this.attributeValueHasChanged(this.valueColumnName, valueColumnName)) {
			this.valueColumnName = valueColumnName;
			this.valueColumnNameAdapter.setValue(valueColumnName);
		}
	}

	private void syncValueColumnName(String astValueColumnName) {
		String old = this.valueColumnName;
		this.valueColumnName = astValueColumnName;
		this.firePropertyChanged(VALUE_COLUMN_NAME_PROPERTY, old, astValueColumnName);
	}

	private String buildValueColumnName(CompilationUnit astRoot) {
		return this.valueColumnNameAdapter.getValue(astRoot);
	}

	public TextRange getValueColumnNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(VALUE_COLUMN_NAME_ADAPTER, astRoot);
	}

	public boolean valueColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(VALUE_COLUMN_NAME_ADAPTER, pos, astRoot);
	}

	// ***** pk column value
	public String getPkColumnValue() {
		return this.pkColumnValue;
	}

	public void setPkColumnValue(String pkColumnValue) {
		if (this.attributeValueHasChanged(this.pkColumnValue, pkColumnValue)) {
			this.pkColumnValue = pkColumnValue;
			this.pkColumnValueAdapter.setValue(pkColumnValue);
		}
	}

	private void syncPkColumnValue(String astPkColumnValue) {
		String old = this.pkColumnValue;
		this.pkColumnValue = astPkColumnValue;
		this.firePropertyChanged(PK_COLUMN_VALUE_PROPERTY, old, astPkColumnValue);
	}

	private String buildPkColumnValue(CompilationUnit astRoot) {
		return this.pkColumnValueAdapter.getValue(astRoot);
	}

	public TextRange getPkColumnValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(PK_COLUMN_VALUE_ADAPTER, astRoot);
	}

	public boolean pkColumnValueTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(PK_COLUMN_VALUE_ADAPTER, pos, astRoot);
	}

	// ***** unique constraints
	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraintAnnotation>(this.uniqueConstraints);
	}

	ListIterator<NestableUniqueConstraintAnnotation> nestableUniqueConstraints() {
		return new CloneListIterator<NestableUniqueConstraintAnnotation>(this.uniqueConstraints);
	}

	public int uniqueConstraintsSize() {
		return this.uniqueConstraints.size();
	}

	public NestableUniqueConstraintAnnotation uniqueConstraintAt(int index) {
		return this.uniqueConstraints.get(index);
	}

	public int indexOfUniqueConstraint(UniqueConstraintAnnotation uniqueConstraint) {
		return this.uniqueConstraints.indexOf(uniqueConstraint);
	}

	public NestableUniqueConstraintAnnotation addUniqueConstraint(int index) {
		return (NestableUniqueConstraintAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation addUniqueConstraintInternal() {
		NestableUniqueConstraintAnnotation uniqueConstraint = this.buildUniqueConstraint(this.uniqueConstraints.size());
		this.uniqueConstraints.add(uniqueConstraint);
		return uniqueConstraint;
	}
	
	NestableUniqueConstraintAnnotation buildUniqueConstraint(int index) {
		return new SourceUniqueConstraintAnnotation(this, this.member, buildUniqueConstraintAnnotationAdapter(index));
	}

	IndexedDeclarationAnnotationAdapter buildUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(this.daa, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	void uniqueConstraintAdded(int index, NestableUniqueConstraintAnnotation constraint) {
		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, constraint);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation moveUniqueConstraintInternal(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex).get(targetIndex);
	}

	void uniqueConstraintMoved(int targetIndex, int sourceIndex) {
		this.fireItemMoved(UNIQUE_CONSTRAINTS_LIST, targetIndex, sourceIndex);
	}

	public void removeUniqueConstraint(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation removeUniqueConstraintInternal(int index) {
		return this.uniqueConstraints.remove(index);
	}

	void uniqueConstraintRemoved(int index, NestableUniqueConstraintAnnotation constraint) {
		this.fireItemRemoved(UNIQUE_CONSTRAINTS_LIST, index, constraint);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}


	// ********** unique constraint container **********

	/**
	 * adapt the AnnotationContainer interface to the table generator's unique constraints
	 */
	class UniqueConstraintsAnnotationContainer
		implements AnnotationContainer<NestableUniqueConstraintAnnotation> 
	{
		public String getContainerAnnotationName() {
			return SourceTableGeneratorAnnotation.this.getAnnotationName();
		}

		public org.eclipse.jdt.core.dom.Annotation getContainerAstAnnotation(CompilationUnit astRoot) {
			return SourceTableGeneratorAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS;
		}

		public String getNestableAnnotationName() {
			return UniqueConstraintAnnotation.ANNOTATION_NAME;
		}

		public ListIterator<NestableUniqueConstraintAnnotation> nestedAnnotations() {
			return SourceTableGeneratorAnnotation.this.nestableUniqueConstraints();
		}

		public int nestedAnnotationsSize() {
			return SourceTableGeneratorAnnotation.this.uniqueConstraintsSize();
		}

		public NestableUniqueConstraintAnnotation addNestedAnnotationInternal() {
			return SourceTableGeneratorAnnotation.this.addUniqueConstraintInternal();
		}

		public void nestedAnnotationAdded(int index, NestableUniqueConstraintAnnotation nestedAnnotation) {
			SourceTableGeneratorAnnotation.this.uniqueConstraintAdded(index, nestedAnnotation);
		}

		public NestableUniqueConstraintAnnotation moveNestedAnnotationInternal(int targetIndex, int sourceIndex) {
			return SourceTableGeneratorAnnotation.this.moveUniqueConstraintInternal(targetIndex, sourceIndex);
		}

		public void nestedAnnotationMoved(int targetIndex, int sourceIndex) {
			SourceTableGeneratorAnnotation.this.uniqueConstraintMoved(targetIndex, sourceIndex);
		}

		public NestableUniqueConstraintAnnotation removeNestedAnnotationInternal(int index) {
			return SourceTableGeneratorAnnotation.this.removeUniqueConstraintInternal(index);
		}

		public void nestedAnnotationRemoved(int index, NestableUniqueConstraintAnnotation nestedAnnotation) {
			SourceTableGeneratorAnnotation.this.uniqueConstraintRemoved(index, nestedAnnotation);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}

}
