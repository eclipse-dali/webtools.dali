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
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public abstract class SourceAssociationOverrideAnnotation
	extends SourceOverrideAnnotation
	implements AssociationOverrideAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ASSOCIATION_OVERRIDES);

	private final JoinColumnsAnnotationContainer joinColumnsContainer = new JoinColumnsAnnotationContainer();

	

	protected SourceAssociationOverrideAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
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

	
	// ********** SourceOverrideAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.ASSOCIATION_OVERRIDE__NAME;
	}


	// ********** AssociationOverrideAnnotation implementation **********

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
				this, this.annotatedElement, this.buildJoinColumnIndexedDeclarationAnnotationAdapter(index));
	}

	private IndexedDeclarationAnnotationAdapter buildJoinColumnIndexedDeclarationAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(
				this.daa, JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	public void moveJoinColumn(int targetIndex, int sourceIndex) {
		this.joinColumnsContainer.moveNestedAnnotation(targetIndex, sourceIndex);
	}

	public void removeJoinColumn(int index) {
		this.joinColumnsContainer.removeNestedAnnotation(index);
	}
	
	/**
	 * adapt the AnnotationContainer interface to the association override's join columns
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
			return JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS;
		}
		@Override
		protected String getNestedAnnotationName() {
			return JPA.JOIN_COLUMN;
		}
		@Override
		protected JoinColumnAnnotation buildNestedAnnotation(int index) {
			return SourceAssociationOverrideAnnotation.this.buildJoinColumn(index);
		}
	}

	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				this.joinColumnsContainer.isEmpty();
	}

	// ********** static methods **********

	protected static IndexedAnnotationAdapter buildAssociationOverrideAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	protected static IndexedDeclarationAnnotationAdapter buildAssociationOverrideDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
