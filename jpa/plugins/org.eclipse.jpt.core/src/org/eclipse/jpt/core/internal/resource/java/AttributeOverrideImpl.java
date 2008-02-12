/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.AnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;

public class AttributeOverrideImpl 
	extends OverrideImpl
	implements NestableAttributeOverride
{		
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final MemberAnnotationAdapter columnAdapter;
		
	private ColumnImpl column;
	
	
	protected AttributeOverrideImpl(JavaResource parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.columnAdapter = new MemberAnnotationAdapter(getMember(), ColumnImpl.buildAttributeOverrideAnnotationAdapter(getDeclarationAnnotationAdapter()));
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) != null) {
			this.column = ColumnImpl.createAttributeOverrideColumn(this, getMember(), getDeclarationAnnotationAdapter());
			this.column.initialize(astRoot);
		}
	}
	
	public String getAnnotationName() {
		return AttributeOverride.ANNOTATION_NAME;
	}
	
	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		AttributeOverride oldAttributeOverride = (AttributeOverride) oldAnnotation;
		if (oldAttributeOverride.getColumn() != null) {
			Column column = addColumn();
			((NestableAnnotation) column).initializeFrom((NestableAnnotation) oldAttributeOverride.getColumn());
		}
	}
	
	//************ AttriubteOverride implementation ****************
	
	public Column getNonNullColumn() {
		return (getColumn() != null) ? getColumn() : new NullAttributeOverrideColumn(this);
	}
	
	public Column getColumn() {
		return this.column;
	}
	
	public Column addColumn() {
		ColumnImpl column = ColumnImpl.createAttributeOverrideColumn(this, getMember(), getDeclarationAnnotationAdapter());
		column.newAnnotation();
		setColumn(column);
		return column;
	}
	
	public void removeColumn() {
		this.column.removeAnnotation();
		setColumn(null);
	}
	
	protected void setColumn(ColumnImpl newColumn) {
		ColumnImpl oldColumn = this.column;
		this.column = newColumn;
		firePropertyChanged(AttributeOverride.COLUMN_PROPERTY, oldColumn, newColumn);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		if (this.columnAdapter.getAnnotation(astRoot) == null) {
			this.setColumn(null);
		}
		else {
			if (getColumn() != null) {
				getColumn().updateFromJava(astRoot);
			}
			else {
				ColumnImpl column = ColumnImpl.createAttributeOverrideColumn(this, getMember(), getDeclarationAnnotationAdapter());
				column.initialize(astRoot);
				this.setColumn(column);
			}
		}
	}

	// ********** static methods **********
	static AttributeOverrideImpl createAttributeOverride(JavaResource parent, Member member) {
		return new AttributeOverrideImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static AttributeOverrideImpl createNestedAttributeOverride(JavaResource parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new AttributeOverrideImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, JPA.ATTRIBUTE_OVERRIDE);
	}
	
	public static class AttributeOverrideAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final AttributeOverrideAnnotationDefinition INSTANCE = new AttributeOverrideAnnotationDefinition();

		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private AttributeOverrideAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaPersistentResource parent, Member member) {
			return AttributeOverrideImpl.createAttributeOverride(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaPersistentResource parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return AttributeOverride.ANNOTATION_NAME;
		}
	}
}
