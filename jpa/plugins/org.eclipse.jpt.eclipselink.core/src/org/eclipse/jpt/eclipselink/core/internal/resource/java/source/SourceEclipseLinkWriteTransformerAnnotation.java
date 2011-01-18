/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceColumnAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.NullEclipseLinkWriteTransformerColumnAnnotation;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkWriteTransformerAnnotation;

/**
 * org.eclipse.persistence.annotations.WriteTransformer
 */
public final class SourceEclipseLinkWriteTransformerAnnotation
	extends SourceEclipseLinkTransformerAnnotation
	implements EclipseLinkWriteTransformerAnnotation
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final ElementAnnotationAdapter columnAdapter;
	private ColumnAnnotation column;
	private final ColumnAnnotation nullColumn;


	public SourceEclipseLinkWriteTransformerAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute) {
		super(parent, attribute, DECLARATION_ANNOTATION_ADAPTER);
		this.columnAdapter = new ElementAnnotationAdapter(this.annotatedElement, buildColumnAnnotationAdapter(this.daa));
		this.nullColumn = this.buildNullColumn();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) != null) {
			this.column = createColumn(this, this.annotatedElement, this.daa);
			this.column.initialize(astRoot);
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
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			this.syncColumn_(null);
		} else {
			if (this.column == null) {
				ColumnAnnotation col = createColumn(this, this.annotatedElement, this.daa);
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

	private ColumnAnnotation buildNullColumn() {
		return new NullEclipseLinkWriteTransformerColumnAnnotation(this);
	}

	public TextRange getColumnTextRange(CompilationUnit astRoot) {
		if (this.column != null) {
			return this.column.getTextRange(astRoot);
		}
		return getTextRange(astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationAdapter buildColumnAnnotationAdapter(DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(writeTransformerAnnotationAdapter, EclipseLink.WRITE_TRANSFORMER__COLUMN, JPA.COLUMN);
	}

	private static ColumnAnnotation createColumn(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter writeTransformerAnnotationAdapter) {
		return new SourceColumnAnnotation(parent, member, buildColumnAnnotationAdapter(writeTransformerAnnotationAdapter));
	}

}
