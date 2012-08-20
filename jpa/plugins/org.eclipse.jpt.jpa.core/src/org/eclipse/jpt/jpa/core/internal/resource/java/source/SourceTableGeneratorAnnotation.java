/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

/**
 * <code>javax.persistence.TableGenerator</code>
 */
public final class SourceTableGeneratorAnnotation
	extends SourceDbGeneratorAnnotation
	implements TableGeneratorAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__NAME);

	private static final DeclarationAnnotationElementAdapter<Integer> INITIAL_VALUE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__INITIAL_VALUE);

	private static final DeclarationAnnotationElementAdapter<Integer> ALLOCATION_SIZE_ADAPTER = buildIntegerAdapter(JPA.TABLE_GENERATOR__ALLOCATION_SIZE);

	private static final DeclarationAnnotationElementAdapter<String> TABLE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__TABLE);
	private final AnnotationElementAdapter<String> tableAdapter;
	private String table;
	private TextRange tableTextRange;

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__SCHEMA);
	private final AnnotationElementAdapter<String> schemaAdapter;
	private String schema;
	private TextRange schemaTextRange;

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__CATALOG);
	private final AnnotationElementAdapter<String> catalogAdapter;
	private String catalog;
	private TextRange catalogTextRange;

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_NAME);
	private final AnnotationElementAdapter<String> pkColumnNameAdapter;
	private String pkColumnName;
	private TextRange pkColumnNameTextRange;

	private static final DeclarationAnnotationElementAdapter<String> VALUE_COLUMN_NAME_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__VALUE_COLUMN_NAME);
	private final AnnotationElementAdapter<String> valueColumnNameAdapter;
	private String valueColumnName;
	private TextRange valueColumnNameTextRange;

	private static final DeclarationAnnotationElementAdapter<String> PK_COLUMN_VALUE_ADAPTER = buildAdapter(JPA.TABLE_GENERATOR__PK_COLUMN_VALUE);
	private final AnnotationElementAdapter<String> pkColumnValueAdapter;
	private String pkColumnValue;
	private TextRange pkColumnValueTextRange;

	private final UniqueConstraintsAnnotationContainer uniqueConstraintsContainer = new UniqueConstraintsAnnotationContainer();


	public SourceTableGeneratorAnnotation(JavaResourceNode parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
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
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.table = this.buildTable(astAnnotation);
		this.tableTextRange = this.buildTableTextRange(astAnnotation);

		this.schema = this.buildSchema(astAnnotation);
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);

		this.catalog = this.buildCatalog(astAnnotation);
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);

		this.pkColumnName = this.buildPkColumnName(astAnnotation);
		this.pkColumnNameTextRange = this.buildPkColumnNameTextRange(astAnnotation);

		this.valueColumnName = this.buildValueColumnName(astAnnotation);
		this.valueColumnNameTextRange = this.buildValueColumnNameTextRange(astAnnotation);

		this.pkColumnValue = this.buildPkColumnValue(astAnnotation);
		this.pkColumnValueTextRange = this.buildPkColumnValueTextRange(astAnnotation);

		this.uniqueConstraintsContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncTable(this.buildTable(astAnnotation));
		this.tableTextRange = this.buildTableTextRange(astAnnotation);

		this.syncSchema(this.buildSchema(astAnnotation));
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);

		this.syncCatalog(this.buildCatalog(astAnnotation));
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);

		this.syncPkColumnName(this.buildPkColumnName(astAnnotation));
		this.pkColumnNameTextRange = this.buildPkColumnNameTextRange(astAnnotation);

		this.syncValueColumnName(this.buildValueColumnName(astAnnotation));
		this.valueColumnNameTextRange = this.buildValueColumnNameTextRange(astAnnotation);

		this.syncPkColumnValue(this.buildPkColumnValue(astAnnotation));
		this.pkColumnValueTextRange = this.buildPkColumnValueTextRange(astAnnotation);

		this.uniqueConstraintsContainer.synchronize(astAnnotation);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.table == null) &&
				(this.schema == null) &&
				(this.catalog == null) &&
				(this.pkColumnName == null) &&
				(this.valueColumnName == null) &&
				(this.pkColumnValue == null);
	}


	// ********** AbstractGeneratorAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> getNameAdapter() {
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

	private String buildTable(Annotation astAnnotation) {
		return this.tableAdapter.getValue(astAnnotation);
	}

	public TextRange getTableTextRange() {
		return this.tableTextRange;
	}

	private TextRange buildTableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(TABLE_ADAPTER, astAnnotation);
	}

	public boolean tableTouches(int pos) {
		return this.textRangeTouches(this.tableTextRange, pos);
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

	private String buildSchema(Annotation astAnnotation) {
		return this.schemaAdapter.getValue(astAnnotation);
	}

	public TextRange getSchemaTextRange() {
		return this.schemaTextRange;
	}

	private TextRange buildSchemaTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(SCHEMA_ADAPTER, astAnnotation);
	}

	public boolean schemaTouches(int pos) {
		return this.textRangeTouches(this.schemaTextRange, pos);
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

	private String buildCatalog(Annotation astAnnotation) {
		return this.catalogAdapter.getValue(astAnnotation);
	}

	public TextRange getCatalogTextRange() {
		return this.catalogTextRange;
	}

	private TextRange buildCatalogTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(CATALOG_ADAPTER, astAnnotation);
	}

	public boolean catalogTouches(int pos) {
		return this.textRangeTouches(this.catalogTextRange, pos);
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

	private String buildPkColumnName(Annotation astAnnotation) {
		return this.pkColumnNameAdapter.getValue(astAnnotation);
	}

	public TextRange getPkColumnNameTextRange() {
		return this.pkColumnNameTextRange;
	}

	private TextRange buildPkColumnNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(PK_COLUMN_NAME_ADAPTER, astAnnotation);
	}

	public boolean pkColumnNameTouches(int pos) {
		return this.textRangeTouches(this.pkColumnNameTextRange, pos);
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

	private String buildValueColumnName(Annotation astAnnotation) {
		return this.valueColumnNameAdapter.getValue(astAnnotation);
	}

	public TextRange getValueColumnNameTextRange() {
		return this.valueColumnNameTextRange;
	}

	private TextRange buildValueColumnNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(VALUE_COLUMN_NAME_ADAPTER, astAnnotation);
	}

	public boolean valueColumnNameTouches(int pos) {
		return this.textRangeTouches(this.valueColumnNameTextRange, pos);
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

	private String buildPkColumnValue(Annotation astAnnotation) {
		return this.pkColumnValueAdapter.getValue(astAnnotation);
	}

	public TextRange getPkColumnValueTextRange() {
		return this.pkColumnValueTextRange;
	}

	private TextRange buildPkColumnValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(PK_COLUMN_VALUE_ADAPTER, astAnnotation);
	}

	public boolean pkColumnValueTouches(int pos) {
		return this.textRangeTouches(this.pkColumnValueTextRange, pos);
	}

	// ***** unique constraints

	public ListIterable<UniqueConstraintAnnotation> getUniqueConstraints() {
		return this.uniqueConstraintsContainer.getNestedAnnotations();
	}

	public int getUniqueConstraintsSize() {
		return this.uniqueConstraintsContainer.getNestedAnnotationsSize();
	}

	public UniqueConstraintAnnotation uniqueConstraintAt(int index) {
		return this.uniqueConstraintsContainer.getNestedAnnotation(index);
	}

	public UniqueConstraintAnnotation addUniqueConstraint(int index) {
		return this.uniqueConstraintsContainer.addNestedAnnotation(index);
	}

	/* CU private */ UniqueConstraintAnnotation buildUniqueConstraint(int index) {
		return new SourceUniqueConstraintAnnotation(
				this, this.annotatedElement, buildUniqueConstraintIndexedDeclarationAnnotationAdapter(index));
	}

	private IndexedDeclarationAnnotationAdapter buildUniqueConstraintIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS, index, JPA.UNIQUE_CONSTRAINT);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		this.uniqueConstraintsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeUniqueConstraint(int index) {
		this.uniqueConstraintsContainer.removeNestedAnnotation(index);
	}
	

	/**
	 * adapt the AnnotationContainer interface to the table's unique constraints
	 */
	class UniqueConstraintsAnnotationContainer 
		extends AnnotationContainer<UniqueConstraintAnnotation>
	{
		@Override
		protected String getNestedAnnotationsListName() {
			return UNIQUE_CONSTRAINTS_LIST;
		}
		@Override
		protected String getElementName() {
			return JPA.TABLE_GENERATOR__UNIQUE_CONSTRAINTS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return UniqueConstraintAnnotation.ANNOTATION_NAME;
		}
		@Override
		protected UniqueConstraintAnnotation buildNestedAnnotation(int index) {
			return SourceTableGeneratorAnnotation.this.buildUniqueConstraint(index);
		}
	}



	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildAdapter(String elementName) {
		return buildAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

	private static DeclarationAnnotationElementAdapter<Integer> buildIntegerAdapter(String elementName) {
		return buildIntegerAdapter(DECLARATION_ANNOTATION_ADAPTER, elementName);
	}

}
