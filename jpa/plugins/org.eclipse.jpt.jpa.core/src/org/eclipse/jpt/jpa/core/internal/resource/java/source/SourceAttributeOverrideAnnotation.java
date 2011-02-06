/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.resource.java.source;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Member;
import org.eclipse.jpt.jpa.core.internal.resource.java.NullAttributeOverrideColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jpa.core.resource.java.NestableAttributeOverrideAnnotation;

/**
 * <code>javax.persistence.AttributeOverride</code>
 */
public final class SourceAttributeOverrideAnnotation
	extends SourceOverrideAnnotation
	implements NestableAttributeOverrideAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private ElementAnnotationAdapter columnAdapter;
	private ColumnAnnotation column;
	private final ColumnAnnotation nullColumn;


	public SourceAttributeOverrideAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
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

	@Override
	protected void rebuildAdapters() {
		super.rebuildAdapters();
		this.columnAdapter = this.buildColumnAdapter();
	}

	@Override
	public void storeOn(Map<String, Object> map) {
		super.storeOn(map);
		if (this.column != null) {
			Map<String, Object> columnState = new HashMap<String, Object>();
			this.column.storeOn(columnState);
			map.put(COLUMN_PROPERTY, columnState);
			this.column = null;
		}
	}

	@Override
	public void restoreFrom(Map<String, Object> map) {
		super.restoreFrom(map);
		@SuppressWarnings("unchecked")
		Map<String, Object> columnState = (Map<String, Object>) map.get(COLUMN_PROPERTY);
		if (columnState != null) {
			this.addColumn().restoreFrom(columnState);
		}
	}


	// ********** static methods **********

	public static SourceAttributeOverrideAnnotation buildAttributeOverride(JavaResourceNode parent, Member member) {
		return new SourceAttributeOverrideAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SourceAttributeOverrideAnnotation buildNestedAttributeOverride(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter, ANNOTATION_NAME);
		IndexedAnnotationAdapter annotationAdapter = new ElementIndexedAnnotationAdapter(member, idaa);
		return new SourceAttributeOverrideAnnotation(parent, member, idaa, annotationAdapter);
	}
}
