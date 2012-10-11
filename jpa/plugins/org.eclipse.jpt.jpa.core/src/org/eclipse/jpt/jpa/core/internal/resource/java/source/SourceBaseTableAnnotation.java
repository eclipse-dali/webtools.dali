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
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

/**
 * <ul>
 * <li><code>javax.persistence.Table</code>
 * <li><code>javax.persistence.JoinTable</code>
 * <li><code>javax.persistence.SecondaryTable</code>
 * <li><code>javax.persistence.CollectionTable</code>
 * </ul>
 */
public abstract class SourceBaseTableAnnotation
	extends SourceAnnotation
	implements BaseTableAnnotation
{
	DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	AnnotationElementAdapter<String> nameAdapter;
	String name;
	TextRange nameTextRange;

	DeclarationAnnotationElementAdapter<String> schemaDeclarationAdapter;
	AnnotationElementAdapter<String> schemaAdapter;
	String schema;
	TextRange schemaTextRange;

	DeclarationAnnotationElementAdapter<String> catalogDeclarationAdapter;
	AnnotationElementAdapter<String> catalogAdapter;
	String catalog;
	TextRange catalogTextRange;

	
	final UniqueConstraintsAnnotationContainer uniqueConstraintsContainer = new UniqueConstraintsAnnotationContainer();


	protected SourceBaseTableAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	protected SourceBaseTableAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.schemaDeclarationAdapter = this.buildSchemaDeclarationAdapter();
		this.schemaAdapter = this.buildSchemaAdapter();
		this.catalogDeclarationAdapter = this.buildCatalogDeclarationAdapter();
		this.catalogAdapter = this.buildCatalogAdapter();
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);

		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.schema = this.buildSchema(astAnnotation);
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);

		this.catalog = this.buildCatalog(astAnnotation);
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);

		this.uniqueConstraintsContainer.initializeFromContainerAnnotation(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);

		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);

		this.syncSchema(this.buildSchema(astAnnotation));
		this.schemaTextRange = this.buildSchemaTextRange(astAnnotation);

		this.syncCatalog(this.buildCatalog(astAnnotation));
		this.catalogTextRange = this.buildCatalogTextRange(astAnnotation);

		this.uniqueConstraintsContainer.synchronize(astAnnotation);
	}

	/**
	 * Return the uniqueConstraints element name
	 */
	protected abstract String getUniqueConstraintsElementName();


	// ********** BaseTableAnnotation implementation **********

	public boolean isSpecified() {
		return true;
	}
	
	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}

	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}

	/**
	 * Build a declaration element adapter for the table's 'name' element.
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter();

	private AnnotationElementAdapter<String> buildNameAdapter() {
		return this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
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
		return this.getElementTextRange(this.schemaDeclarationAdapter, astAnnotation);
	}

	public boolean schemaTouches(int pos) {
		return this.textRangeTouches(this.schemaTextRange, pos);
	}

	/**
	 * Build a declaration element adapter for the table's 'schema' element.
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter();

	private AnnotationElementAdapter<String> buildSchemaAdapter() {
		return this.buildAnnotationElementAdapter(this.schemaDeclarationAdapter);
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
		return this.getElementTextRange(this.catalogDeclarationAdapter, astAnnotation);
	}

	public boolean catalogTouches(int pos) {
		return this.textRangeTouches(this.catalogTextRange, pos);
	}

	/**
	 * Build a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter();

	private AnnotationElementAdapter<String> buildCatalogAdapter() {
		return this.buildAnnotationElementAdapter(this.catalogDeclarationAdapter);
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
				this.daa, this.getUniqueConstraintsElementName(), index, JPA.UNIQUE_CONSTRAINT);
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
			return SourceBaseTableAnnotation.this.getUniqueConstraintsElementName();
		}
		@Override
		protected String getNestedAnnotationName() {
			return UniqueConstraintAnnotation.ANNOTATION_NAME;
		}
		@Override
		protected UniqueConstraintAnnotation buildNestedAnnotation(int index) {
			return SourceBaseTableAnnotation.this.buildUniqueConstraint(index);
		}
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.schema == null) &&
				(this.catalog == null) &&
				this.uniqueConstraintsContainer.isEmpty();
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
