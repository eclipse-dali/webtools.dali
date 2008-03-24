/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestablePrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class PrimaryKeyJoinColumnImpl extends AbstractNamedColumn implements NestablePrimaryKeyJoinColumn
{

	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;

	private String referencedColumnName;
	
	protected PrimaryKeyJoinColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.PRIMARY_KEY_JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}
	
	protected PrimaryKeyJoinColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	protected PrimaryKeyJoinColumnImpl(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.referencedColumnName = this.referencedColumnName(astRoot);
	}
	
	@Override
	protected String nameElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.PRIMARY_KEY_JOIN_COLUMN__COLUMN_DEFINITION;
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) super.getAnnotationAdapter();
	}

	public void moveAnnotation(int newIndex) {
		getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	@Override
	public void initializeFrom(NestableAnnotation oldAnnotation) {
		super.initializeFrom(oldAnnotation);
		PrimaryKeyJoinColumnAnnotation oldColumn = (PrimaryKeyJoinColumnAnnotation) oldAnnotation;
		setReferencedColumnName(oldColumn.getReferencedColumnName());
	}
	
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}
	
	public void setReferencedColumnName(String newReferencedColumnName) {
		String oldReferencedColumnName = this.referencedColumnName;
		this.referencedColumnName = newReferencedColumnName;
		this.referencedColumnNameAdapter.setValue(newReferencedColumnName);
		firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, oldReferencedColumnName, newReferencedColumnName);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setReferencedColumnName(this.referencedColumnName(astRoot));
	}
	
	protected String referencedColumnName(CompilationUnit astRoot) {
		return this.referencedColumnNameAdapter.getValue(astRoot);
	}
	
	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.referencedColumnNameDeclarationAdapter, pos, astRoot);
	}

	public TextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.referencedColumnNameDeclarationAdapter, astRoot);
	}
	
	// ********** static methods **********

	static PrimaryKeyJoinColumnImpl createPrimaryKeyJoinColumn(JavaResourceNode parent, Member member) {
		return new PrimaryKeyJoinColumnImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	static PrimaryKeyJoinColumnImpl createNestedPrimaryKeyJoinColumn(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter pkJoinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, pkJoinColumnsAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new PrimaryKeyJoinColumnImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter pkJoinColumnsAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(pkJoinColumnsAdapter, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
	}


	static NestablePrimaryKeyJoinColumn createSecondaryTablePrimaryKeyJoinColumn(DeclarationAnnotationAdapter secondaryTableAdapter, JavaResourceNode parent, Member member, int index) {
		return new PrimaryKeyJoinColumnImpl(parent, member, buildSecondaryTableAnnotationAdapter(secondaryTableAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildSecondaryTableAnnotationAdapter(DeclarationAnnotationAdapter secondaryTableAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(secondaryTableAdapter, JPA.SECONDARY_TABLE__PK_JOIN_COLUMNS, index, JPA.PRIMARY_KEY_JOIN_COLUMN);
	}

	public static class PrimaryKeyJoinColumnAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final PrimaryKeyJoinColumnAnnotationDefinition INSTANCE = new PrimaryKeyJoinColumnAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private PrimaryKeyJoinColumnAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return PrimaryKeyJoinColumnImpl.createPrimaryKeyJoinColumn(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
