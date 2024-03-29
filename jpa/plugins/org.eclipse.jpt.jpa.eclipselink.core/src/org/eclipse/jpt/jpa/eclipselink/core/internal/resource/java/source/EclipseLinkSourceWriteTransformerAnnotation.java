/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkNullWriteTransformerColumnAnnotation;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.WriteTransformerAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.WriteTransformer</code>
 */
public final class EclipseLinkSourceWriteTransformerAnnotation
	extends EclipseLinkSourceTransformerAnnotation
	implements WriteTransformerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final ElementAnnotationAdapter columnAdapter;
	private ColumnAnnotation column;
	private final ColumnAnnotation nullColumn;


	public EclipseLinkSourceWriteTransformerAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element) {
		super(parent, element, DECLARATION_ANNOTATION_ADAPTER);
		this.columnAdapter = new ElementAnnotationAdapter(this.annotatedElement, buildColumnAnnotationAdapter(this.daa));
		this.nullColumn = this.buildNullColumn();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		Annotation columnAnnotation = this.columnAdapter.getAnnotation(astRoot);
		if (columnAnnotation != null) {
			this.column = createColumn(this, this.annotatedElement, this.daa);
			this.column.initialize(columnAnnotation);
		}
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncColumn(astRoot);
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.column == null);
	}


	// ********** SourceTransformerAnnotation implementation **********

	@Override
	String getTransformerClassElementName() {
		return EclipseLink.WRITE_TRANSFORMER__TRANSFORMER_CLASS;
	}

	@Override
	String getMethodElementName() {
		return EclipseLink.WRITE_TRANSFORMER__METHOD;
	}


	// ********** WriteTransformerAnnotation implementation **********

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
		this.column = createColumn(this, this.annotatedElement, this.daa);
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
		Annotation columnAnnotation = this.columnAdapter.getAnnotation(astRoot);
		if (columnAnnotation == null) {
			this.syncColumn_(null);
		} else {
			if (this.column == null) {
				ColumnAnnotation col = createColumn(this, this.annotatedElement, this.daa);
				col.initialize(columnAnnotation);
				this.syncColumn_(col);
			} else {
				this.column.synchronizeWith(columnAnnotation);
			}
		}
	}

	private void syncColumn_(ColumnAnnotation astColumn) {
		ColumnAnnotation old = this.column;
		this.column = astColumn;
		this.firePropertyChanged(COLUMN_PROPERTY, old, astColumn);
	}

	private ColumnAnnotation buildNullColumn() {
		return new EclipseLinkNullWriteTransformerColumnAnnotation(this);
	}

	public TextRange getColumnTextRange() {
		if (this.column != null) {
			return this.column.getTextRange();
		}
		return this.getTextRange();
	}

	// ********** static methods **********

	private static DeclarationAnnotationAdapter buildColumnAnnotationAdapter(DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(writeTransformerAnnotationAdapter, EclipseLink.WRITE_TRANSFORMER__COLUMN, JPA.COLUMN);
	}

	private static ColumnAnnotation createColumn(JavaResourceModel parent, AnnotatedElement element, DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new SourceColumnAnnotation(parent, element, buildColumnAnnotationAdapter(writeTransformerAnnotationAdapter));
	}
}
