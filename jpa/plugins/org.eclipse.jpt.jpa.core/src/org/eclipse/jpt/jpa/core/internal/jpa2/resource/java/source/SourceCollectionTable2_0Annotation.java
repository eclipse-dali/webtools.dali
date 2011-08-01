/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceBaseTableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.CollectionTable2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * <code>javax.persistence.CollectionTable</code>
 */
public final class SourceCollectionTable2_0Annotation
	extends SourceBaseTableAnnotation
	implements CollectionTable2_0Annotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(CollectionTable2_0Annotation.ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__NAME);

	private static final DeclarationAnnotationElementAdapter<String> SCHEMA_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__SCHEMA);

	private static final DeclarationAnnotationElementAdapter<String> CATALOG_ADAPTER = ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JPA2_0.COLLECTION_TABLE__CATALOG);


	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();


	public SourceCollectionTable2_0Annotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.joinColumnsContainer.initialize(this.getAstAnnotation(astRoot));
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.joinColumnsContainer.synchronize(this.getAstAnnotation(astRoot));
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return NAME_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return SCHEMA_ADAPTER;
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
		return CATALOG_ADAPTER;
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA2_0.COLLECTION_TABLE__UNIQUE_CONSTRAINTS;
	}

	// ********** CollectionTable2_0Annotation implementation **********

	// **************** join columns *************************************************

	public ListIterable<JoinColumnAnnotation> getJoinColumns() {
		return this.joinColumnsContainer.getNestedAnnotations();
	}

	public int getJoinColumnsSize() {
		return this.joinColumnsContainer.getNestedAnnotationsSize();
	}

	public JoinColumnAnnotation joinColumnAt(int index) {
		return this.joinColumnsContainer.nestedAnnotationAt(index);
	}

	public JoinColumnAnnotation addJoinColumn(int index) {
		return this.joinColumnsContainer.addNestedAnnotation(index);
	}
	
	private JoinColumnAnnotation buildJoinColumn(int index) {
		return SourceJoinColumnAnnotation.buildNestedSourceJoinColumnAnnotation(
				this, this.annotatedElement, buildJoinColumnIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildJoinColumnIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA2_0.COLLECTION_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		this.joinColumnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeJoinColumn(int index) {
		this.joinColumnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the collection table's join columns
	 */
	class JoinColumnsAnnotationContainer 
		extends AnnotationContainer<JoinColumnAnnotation>
	{
		@Override
		protected String getAnnotationsPropertyName() {
			return JOIN_COLUMNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JPA2_0.COLLECTION_TABLE__JOIN_COLUMNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JPA.JOIN_COLUMN;
		}
		@Override
		protected JoinColumnAnnotation buildNestedAnnotation(int index) {
			return SourceCollectionTable2_0Annotation.this.buildJoinColumn(index);
		}
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumnsContainer.isEmpty();
	}
}
