/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;

/**
 * <code>javax.persistence.SecondaryTable</code>
 */
public final class SourceSecondaryTableAnnotation
	extends SourceBaseTableAnnotation
	implements SecondaryTableAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.SECONDARY_TABLES);

	private final PkJoinColumnsAnnotationContainer pkJoinColumnsContainer = new PkJoinColumnsAnnotationContainer();

	public static SourceSecondaryTableAnnotation buildSourceSecondaryTableAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildSecondaryTableDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildSecondaryTableAnnotationAdapter(element, idaa);
		return new SourceSecondaryTableAnnotation(
			parent,
			element,
			idaa,
			iaa);
	}

	private SourceSecondaryTableAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement element,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.pkJoinColumnsContainer.initialize(this.getAstAnnotation(astRoot));
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.pkJoinColumnsContainer.synchronize(this.getAstAnnotation(astRoot));
	}


	// ********** SourceBaseTableAnnotation implementation **********

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildSchemaDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter<String> buildCatalogDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JPA.SECONDARY_TABLE__CATALOG);
	}

	@Override
	protected String getUniqueConstraintsElementName() {
		return JPA.SECONDARY_TABLE__UNIQUE_CONSTRAINTS;
	}


	// ************* SecondaryTableAnnotation implementation *******************

	// ***** pk join columns

	public ListIterable<PrimaryKeyJoinColumnAnnotation> getPkJoinColumns() {
		return this.pkJoinColumnsContainer.getNestedAnnotations();
	}

	public int getPkJoinColumnsSize() {
		return this.pkJoinColumnsContainer.getNestedAnnotationsSize();
	}

	public PrimaryKeyJoinColumnAnnotation pkJoinColumnAt(int index) {
		return this.pkJoinColumnsContainer.nestedAnnotationAt(index);
	}

	public PrimaryKeyJoinColumnAnnotation addPkJoinColumn(int index) {
		return this.pkJoinColumnsContainer.addNestedAnnotation(index);
	}
	
	private PrimaryKeyJoinColumnAnnotation buildPkJoinColumn(int index) {
		return SourcePrimaryKeyJoinColumnAnnotation.buildNestedSourcePrimaryKeyJoinColumnAnnotation(
				this, this.annotatedElement, this.buildPkJoinColumnIndexedDeclarationAnnotationAdapter(index));
	}
	
	private IndexedDeclarationAnnotationAdapter buildPkJoinColumnIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
	}

	public void movePkJoinColumn(int targetIndex, int sourceIndex) {
		this.pkJoinColumnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removePkJoinColumn(int index) {
		this.pkJoinColumnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the collection table's join columns
	 */
	class PkJoinColumnsAnnotationContainer 
		extends AnnotationContainer<PrimaryKeyJoinColumnAnnotation>
	{
		@Override
		protected String getAnnotationsPropertyName() {
			return PK_JOIN_COLUMNS_LIST;
		}
		@Override
		protected String getElementName() {
			return JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JPA.PRIMARY_KEY_JOIN_COLUMN;
		}
		@Override
		protected PrimaryKeyJoinColumnAnnotation buildNestedAnnotation(int index) {
			return SourceSecondaryTableAnnotation.this.buildPkJoinColumn(index);
		}
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.pkJoinColumnsContainer.isEmpty();
	}

	// ********** static methods **********

	private static IndexedAnnotationAdapter buildSecondaryTableAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
