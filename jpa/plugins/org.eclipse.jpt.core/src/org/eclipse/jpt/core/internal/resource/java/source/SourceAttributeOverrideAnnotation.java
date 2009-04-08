/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.NullAttributeOverrideColumnAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.ColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAttributeOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.NestableColumnAnnotation;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 * javax.persistence.AttributeOverride
 */
public final class SourceAttributeOverrideAnnotation
	extends SourceOverrideAnnotation
	implements NestableAttributeOverrideAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final MemberAnnotationAdapter columnAdapter;
	private NestableColumnAnnotation column;


	// ********** construction/initialization **********

	public SourceAttributeOverrideAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.columnAdapter = new MemberAnnotationAdapter(this.member, SourceColumnAnnotation.buildAttributeOverrideAnnotationAdapter(this.daa));
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) != null) {
			this.column = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.member, this.daa);
			this.column.initialize(astRoot);
		}
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.updateColumn(astRoot);
	}


	// ********** SourceOverrideAnnotation implementation **********

	@Override
	String getNameElementName() {
		return JPA.ATTRIBUTE_OVERRIDE__NAME;
	}
	

	//************ AttributeOverride implementation ****************

	// ***** column
	public ColumnAnnotation getColumn() {
		return this.column;
	}

	public NestableColumnAnnotation addColumn() {
		NestableColumnAnnotation col = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.member, this.daa);
		col.newAnnotation();
		this.setColumn(col);
		return col;
	}

	public void removeColumn() {
		this.column.removeAnnotation();
		setColumn(null);
	}

	private void setColumn(NestableColumnAnnotation column) {
		ColumnAnnotation old = this.column;
		this.column = column;
		this.firePropertyChanged(COLUMN_PROPERTY, old, column);
	}

	public ColumnAnnotation getNonNullColumn() {
		return (this.column != null) ? this.column : new NullAttributeOverrideColumnAnnotation(this);
	}

	private void updateColumn(CompilationUnit astRoot) {
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			this.setColumn(null);
		} else {
			if (this.column == null) {
				NestableColumnAnnotation col = SourceColumnAnnotation.createAttributeOverrideColumn(this, this.member, this.daa);
				col.initialize(astRoot);
				this.setColumn(col);
			} else {
				this.column.update(astRoot);
			}
		}
	}


	// ********** NestableAnnotation implementation **********

	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AttributeOverrideAnnotation oldOverride = (AttributeOverrideAnnotation) oldAnnotation;
		ColumnAnnotation oldColumn = oldOverride.getColumn();
		if (oldColumn != null) {
			NestableColumnAnnotation newColumn = this.addColumn();
			newColumn.initializeFrom((NestableAnnotation) oldColumn);
		}
	}


	// ********** static methods **********

	public static SourceAttributeOverrideAnnotation createAttributeOverride(JavaResourceNode parent, Member member) {
		return new SourceAttributeOverrideAnnotation(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static SourceAttributeOverrideAnnotation createNestedAttributeOverride(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new SourceAttributeOverrideAnnotation(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, JPA.ATTRIBUTE_OVERRIDE);
	}

}
