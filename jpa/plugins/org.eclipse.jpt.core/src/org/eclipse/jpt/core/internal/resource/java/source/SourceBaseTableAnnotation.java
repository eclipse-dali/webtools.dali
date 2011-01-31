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

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.core.resource.java.AnnotationContainer;
import org.eclipse.jpt.core.resource.java.BaseTableAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableUniqueConstraintAnnotation;
import org.eclipse.jpt.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * <ul>
 * <li><code>javax.persistence.Table</code>
 * <li><code>javax.persistence.JoinTable</code>
 * <li><code>javax.persistence.SecondaryTable</code>
 * <li><code>javax.persistence.CollectionTable</code>
 * </ul>
 */
public abstract class SourceBaseTableAnnotation
	extends SourceAnnotation<Member>
	implements BaseTableAnnotation
{
	DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	AnnotationElementAdapter<String> nameAdapter;
	String name;

	DeclarationAnnotationElementAdapter<String> schemaDeclarationAdapter;
	AnnotationElementAdapter<String> schemaAdapter;
	String schema;

	DeclarationAnnotationElementAdapter<String> catalogDeclarationAdapter;
	AnnotationElementAdapter<String> catalogAdapter;
	String catalog;

	final Vector<NestableUniqueConstraintAnnotation> uniqueConstraints = new Vector<NestableUniqueConstraintAnnotation>();
	final UniqueConstraintsAnnotationContainer uniqueConstraintsContainer = new UniqueConstraintsAnnotationContainer();


	protected SourceBaseTableAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new ElementAnnotationAdapter(member, daa));
	}

	protected SourceBaseTableAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.schemaDeclarationAdapter = this.buildSchemaDeclarationAdapter();
		this.schemaAdapter = this.buildSchemaAdapter();
		this.catalogDeclarationAdapter = this.buildCatalogDeclarationAdapter();
		this.catalogAdapter = this.buildCatalogAdapter();
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.schema = this.buildSchema(astRoot);
		this.catalog = this.buildCatalog(astRoot);
		AnnotationContainerTools.initialize(this.uniqueConstraintsContainer, astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncSchema(this.buildSchema(astRoot));
		this.syncCatalog(this.buildCatalog(astRoot));
		AnnotationContainerTools.synchronize(this.uniqueConstraintsContainer, astRoot);
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

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}

	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.nameDeclarationAdapter, pos, astRoot);
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

	private String buildSchema(CompilationUnit astRoot) {
		return this.schemaAdapter.getValue(astRoot);
	}

	public TextRange getSchemaTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.schemaDeclarationAdapter, astRoot);
	}

	public boolean schemaTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.schemaDeclarationAdapter, pos, astRoot);
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

	private String buildCatalog(CompilationUnit astRoot) {
		return this.catalogAdapter.getValue(astRoot);
	}

	public TextRange getCatalogTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.catalogDeclarationAdapter, astRoot);
	}

	public boolean catalogTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.catalogDeclarationAdapter, pos, astRoot);
	}

	/**
	 * Build a declaration element adapter for the table's 'catalog' element
	 */
	protected abstract DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter();

	private AnnotationElementAdapter<String> buildCatalogAdapter() {
		return this.buildAnnotationElementAdapter(this.catalogDeclarationAdapter);
	}

	// ***** unique constraints
	public ListIterator<UniqueConstraintAnnotation> uniqueConstraints() {
		return new CloneListIterator<UniqueConstraintAnnotation>(this.uniqueConstraints);
	}

	Iterable<NestableUniqueConstraintAnnotation> getNestableUniqueConstraints() {
		return new LiveCloneIterable<NestableUniqueConstraintAnnotation>(this.uniqueConstraints);
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

	private NestableUniqueConstraintAnnotation addUniqueConstraint() {
		return this.addUniqueConstraint(this.uniqueConstraints.size());
	}

	public NestableUniqueConstraintAnnotation addUniqueConstraint(int index) {
		return (NestableUniqueConstraintAnnotation) AnnotationContainerTools.addNestedAnnotation(index, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation addUniqueConstraint_() {
		return this.addUniqueConstraint_(this.uniqueConstraints.size());
	}

	private NestableUniqueConstraintAnnotation addUniqueConstraint_(int index) {
		NestableUniqueConstraintAnnotation uniqueConstraint = this.buildUniqueConstraint(index);
		this.uniqueConstraints.add(index, uniqueConstraint);
		return uniqueConstraint;
	}

	void syncAddUniqueConstraint(Annotation astAnnotation) {
		int index = this.uniqueConstraints.size();
		NestableUniqueConstraintAnnotation uniqueConstraint = this.addUniqueConstraint_(index);
		uniqueConstraint.initialize((CompilationUnit) astAnnotation.getRoot());
		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, uniqueConstraint);
	}

	NestableUniqueConstraintAnnotation buildUniqueConstraint(int index) {
		return new SourceUniqueConstraintAnnotation(this, this.annotatedElement, buildUniqueConstraintAnnotationAdapter(index));
	}

	IndexedDeclarationAnnotationAdapter buildUniqueConstraintAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(this.daa, getUniqueConstraintsElementName(), index, JPA.UNIQUE_CONSTRAINT);
	}

	void uniqueConstraintAdded(int index, NestableUniqueConstraintAnnotation constraint) {
		this.fireItemAdded(UNIQUE_CONSTRAINTS_LIST, index, constraint);
	}

	public void moveUniqueConstraint(int targetIndex, int sourceIndex) {
		AnnotationContainerTools.moveNestedAnnotation(targetIndex, sourceIndex, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation moveUniqueConstraint_(int targetIndex, int sourceIndex) {
		return CollectionTools.move(this.uniqueConstraints, targetIndex, sourceIndex).get(targetIndex);
	}

	public void removeUniqueConstraint(int index) {
		AnnotationContainerTools.removeNestedAnnotation(index, this.uniqueConstraintsContainer);
	}

	NestableUniqueConstraintAnnotation removeUniqueConstraint_(int index) {
		return this.uniqueConstraints.remove(index);
	}

	void syncRemoveUniqueConstraints(int index) {
		this.removeItemsFromList(index, this.uniqueConstraints, UNIQUE_CONSTRAINTS_LIST);
	}


	// ********** unique constraint container **********

	/**
	 * adapt the AnnotationContainer interface to the table's unique constraints
	 */
	class UniqueConstraintsAnnotationContainer
		implements AnnotationContainer<NestableUniqueConstraintAnnotation> 
	{
		public Annotation getAstAnnotation(CompilationUnit astRoot) {
			return SourceBaseTableAnnotation.this.getAstAnnotation(astRoot);
		}

		public String getElementName() {
			return SourceBaseTableAnnotation.this.getUniqueConstraintsElementName();
		}

		public String getNestedAnnotationName() {
			return UniqueConstraintAnnotation.ANNOTATION_NAME;
		}

		public Iterable<NestableUniqueConstraintAnnotation> getNestedAnnotations() {
			return SourceBaseTableAnnotation.this.getNestableUniqueConstraints();
		}

		public int getNestedAnnotationsSize() {
			return SourceBaseTableAnnotation.this.uniqueConstraintsSize();
		}

		public NestableUniqueConstraintAnnotation addNestedAnnotation() {
			return SourceBaseTableAnnotation.this.addUniqueConstraint_();
		}

		public void syncAddNestedAnnotation(Annotation astAnnotation) {
			SourceBaseTableAnnotation.this.syncAddUniqueConstraint(astAnnotation);
		}

		public NestableUniqueConstraintAnnotation moveNestedAnnotation(int targetIndex, int sourceIndex) {
			return SourceBaseTableAnnotation.this.moveUniqueConstraint_(targetIndex, sourceIndex);
		}

		public NestableUniqueConstraintAnnotation removeNestedAnnotation(int index) {
			return SourceBaseTableAnnotation.this.removeUniqueConstraint_(index);
		}

		public void syncRemoveNestedAnnotations(int index) {
			SourceBaseTableAnnotation.this.syncRemoveUniqueConstraints(index);
		}

		@Override
		public String toString() {
			return StringTools.buildToStringFor(this);
		}

	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.name == null) &&
				(this.schema == null) &&
				(this.catalog == null) &&
				this.uniqueConstraints.isEmpty();
	}

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildNameAdapter();
		this.schemaDeclarationAdapter = this.buildSchemaDeclarationAdapter();
		this.schemaAdapter = this.buildSchemaAdapter();
		this.catalogDeclarationAdapter = this.buildCatalogDeclarationAdapter();
		this.catalogAdapter = this.buildCatalogAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);

		map.put(NAME_PROPERTY, this.name);
		this.name = null;
		map.put(SCHEMA_PROPERTY, this.schema);
		this.schema = null;
		map.put(CATALOG_PROPERTY, this.catalog);
		this.catalog = null;

		List<Map<String, Object>> constraintsState = this.buildStateList(this.uniqueConstraints.size());
		for (NestableUniqueConstraintAnnotation constraint : this.getNestableUniqueConstraints()) {
			Map<String, Object> constraintState = new HashMap<String, Object>();
			constraint.storeOn(constraintState);
			constraintsState.add(constraintState);
		}
		map.put(UNIQUE_CONSTRAINTS_LIST, constraintsState);
		this.uniqueConstraints.clear();
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);

		this.setName((String) map.get(NAME_PROPERTY));
		this.setSchema((String) map.get(SCHEMA_PROPERTY));
		this.setCatalog((String) map.get(CATALOG_PROPERTY));

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> constraintsState = (List<Map<String, Object>>) map.get(UNIQUE_CONSTRAINTS_LIST);
		for (Map<String, Object> constraintState : constraintsState) {
			this.addUniqueConstraint().restoreFrom(constraintState);
		}
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
}
