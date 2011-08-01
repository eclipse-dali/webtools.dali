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
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullAttributeOverrideColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * <code>javax.persistence.AttributeOverride</code>
 */
public final class SourceAttributeOverrideAnnotation
	extends SourceOverrideAnnotation
	implements AttributeOverrideAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	private static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.ATTRIBUTE_OVERRIDES);

	private ElementAnnotationAdapter columnAdapter;
	private ColumnAnnotation column;
	private final ColumnAnnotation nullColumn;

	
	public static SourceAttributeOverrideAnnotation buildSourceAttributeOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element) {
		
		return new SourceAttributeOverrideAnnotation(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceAttributeOverrideAnnotation buildSourceAttributeOverrideAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildAttributeOverrideDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildAttributeOverrideAnnotationAdapter(annotatedElement, idaa);
		return new SourceAttributeOverrideAnnotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}
	
	public static SourceAttributeOverrideAnnotation buildNestedSourceAttributeOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceAttributeOverrideAnnotation(parent, element, idaa);
	}
	
	private SourceAttributeOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			DeclarationAnnotationAdapter daa) {
		
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}
	
	private SourceAttributeOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceAttributeOverrideAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.columnAdapter = this.buildColumnAdapter();
		this.nullColumn = this.buildNullColumn();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) != null) {
			this.column = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.annotatedElement, this.daa);
			this.column.initialize(astRoot);
		}
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncColumn(astRoot);
	}


	// ********** SourceOverrideAnnotation implementation **********

	@Override
	protected String getNameElementName() {
		return JPA.ATTRIBUTE_OVERRIDE__NAME;
	}


	//************ AttributeOverride implementation ****************

	// ***** column
	public ColumnAnnotation getColumn() {
		return this.column;
	}

	public ColumnAnnotation getNonNullColumn() {
		return (this.column != null) ? this.column : this.nullColumn;
	}

	public ColumnAnnotation addColumn() {
		if (this.column != null) {
			throw new IllegalStateException("'column' element already exists: " + this.column); //$NON-NLS-1$
		}
		this.column = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.annotatedElement, this.daa);
		this.column.newAnnotation();
		return this.column;
	}

	public void removeColumn() {
		if (this.column == null) {
			throw new IllegalStateException("'column' element does not exist"); //$NON-NLS-1$
		}
		ColumnAnnotation old = this.column;
		this.column = null;
		old.removeAnnotation();
	}

	private void syncColumn(CompilationUnit astRoot) {
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			this.syncColumn_(null);
		} else {
			if (this.column == null) {
				ColumnAnnotation col = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.annotatedElement, this.daa);
				col.initialize(astRoot);
				this.syncColumn_(col);
			} else {
				this.column.synchronizeWith(astRoot);
			}
		}
	}

	private void syncColumn_(ColumnAnnotation astColumn) {
		ColumnAnnotation old = this.column;
		this.column = astColumn;
		this.firePropertyChanged(COLUMN_PROPERTY, old, astColumn);
	}

	private ElementAnnotationAdapter buildColumnAdapter() {
		return new ElementAnnotationAdapter(this.annotatedElement, SourceColumnAnnotation.buildAttributeOverrideAnnotationAdapter(this.daa));
	}

	private ColumnAnnotation buildNullColumn() {
		return new NullAttributeOverrideColumnAnnotation(this);
	}


	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.column == null);
	}


	// ********** static methods **********

	protected static IndexedAnnotationAdapter buildAttributeOverrideAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	protected static IndexedDeclarationAnnotationAdapter buildAttributeOverrideDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				ANNOTATION_NAME);
		return idaa;
	}
}
