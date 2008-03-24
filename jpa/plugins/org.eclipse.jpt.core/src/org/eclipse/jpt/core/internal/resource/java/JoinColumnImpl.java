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
import org.eclipse.jpt.core.internal.jdtutility.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.resource.java.Annotation;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.NestableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinColumn;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

public class JoinColumnImpl extends AbstractColumnImpl implements NestableJoinColumn
{
	private static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	// hold this so we can get the 'referenced column name' text range
	private final DeclarationAnnotationElementAdapter<String> referencedColumnNameDeclarationAdapter;

	private final AnnotationElementAdapter<String> referencedColumnNameAdapter;

	private String referencedColumnName;
	
	public JoinColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.referencedColumnNameDeclarationAdapter = this.buildStringElementAdapter(JPA.JOIN_COLUMN__REFERENCED_COLUMN_NAME);
		this.referencedColumnNameAdapter = this.buildShortCircuitElementAdapter(this.referencedColumnNameDeclarationAdapter);
	}
	
	public JoinColumnImpl(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa) {
		this(parent, member, daa, new MemberAnnotationAdapter(member, daa));
	}
	
	public JoinColumnImpl(JavaResourceNode parent, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, member, idaa, new MemberIndexedAnnotationAdapter(member, idaa));
	}
	
	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		this.referencedColumnName = this.referencedColumnName(astRoot);
	}
	
	@Override
	protected String nameElementName() {
		return JPA.JOIN_COLUMN__NAME;
	}
	
	@Override
	protected String columnDefinitionElementName() {
		return JPA.JOIN_COLUMN__COLUMN_DEFINITION;
	}
	
	@Override
	protected String tableElementName() {
		return JPA.JOIN_COLUMN__TABLE;
	}

	@Override
	protected String uniqueElementName() {
		return JPA.JOIN_COLUMN__UNIQUE;
	}

	@Override
	protected String nullableElementName() {
		return JPA.JOIN_COLUMN__NULLABLE;
	}

	@Override
	protected String insertableElementName() {
		return JPA.JOIN_COLUMN__INSERTABLE;
	}

	@Override
	protected String updatableElementName() {
		return JPA.JOIN_COLUMN__UPDATABLE;
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
		JoinColumnAnnotation oldColumn = (JoinColumnAnnotation) oldAnnotation;
		setReferencedColumnName(oldColumn.getReferencedColumnName());
	}
	
	//************ JoinColumn implementation ***************
	public String getReferencedColumnName() {
		return this.referencedColumnName;
	}
	
	public void setReferencedColumnName(String newReferencedColumnName) {
		String oldReferencedColumnName = this.referencedColumnName;
		this.referencedColumnName = newReferencedColumnName;
		this.referencedColumnNameAdapter.setValue(newReferencedColumnName);
		firePropertyChanged(REFERENCED_COLUMN_NAME_PROPERTY, oldReferencedColumnName, newReferencedColumnName);
	}
	
	public TextRange referencedColumnNameTextRange(CompilationUnit astRoot) {
		return this.elementTextRange(this.referencedColumnNameDeclarationAdapter, astRoot);
	}

	public boolean referencedColumnNameTouches(int pos, CompilationUnit astRoot) {
		return this.elementTouches(this.referencedColumnNameDeclarationAdapter, pos, astRoot);
	}
	
	@Override
	public void updateFromJava(CompilationUnit astRoot) {
		super.updateFromJava(astRoot);
		this.setReferencedColumnName(this.referencedColumnName(astRoot));
	}
	
	protected String referencedColumnName(CompilationUnit astRoot) {
		return this.referencedColumnNameAdapter.getValue(astRoot);
	}
	
	// ********** static methods **********

	static JoinColumnImpl createJoinColumn(JavaResourceNode parent, Member member) {
		return new JoinColumnImpl(parent, member, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	static JoinColumnImpl createNestedJoinColumn(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, joinColumnsAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new JoinColumnImpl(parent, member, idaa, annotationAdapter);
	}

	private static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter joinColumnsAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(joinColumnsAdapter, index, JPA.JOIN_COLUMN);
	}
	static NestableJoinColumn createJoinTableJoinColumn(JavaResourceNode parent, Member member, int index) {
		return new JoinColumnImpl(parent, member, buildJoinTableAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}

	static NestableJoinColumn createJoinTableInverseJoinColumn(JavaResourceNode parent, Member member, int index) {
		return new JoinColumnImpl(parent, member, buildJoinTableInverseAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildJoinTableInverseAnnotationAdapter(int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(JoinTableImpl.DECLARATION_ANNOTATION_ADAPTER, JPA.JOIN_TABLE__INVERSE_JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}
	
	
	static NestableJoinColumn createAssociationOverrideJoinColumn(DeclarationAnnotationAdapter associationOverrideAdapter, JavaResourceNode parent, Member member, int index) {
		return new JoinColumnImpl(parent, member, buildAssociationOverrideAnnotationAdapter(associationOverrideAdapter, index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAssociationOverrideAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAdapter, int index) {
		return new NestedIndexedDeclarationAnnotationAdapter(associationOverrideAdapter, JPA.ASSOCIATION_OVERRIDE__JOIN_COLUMNS, index, JPA.JOIN_COLUMN);
	}


	public static class JoinColumnAnnotationDefinition implements AnnotationDefinition
	{
		// singleton
		private static final JoinColumnAnnotationDefinition INSTANCE = new JoinColumnAnnotationDefinition();


		/**
		 * Return the singleton.
		 */
		public static AnnotationDefinition instance() {
			return INSTANCE;
		}

		/**
		 * Ensure non-instantiability.
		 */
		private JoinColumnAnnotationDefinition() {
			super();
		}

		public Annotation buildAnnotation(JavaResourcePersistentMember parent, Member member) {
			return JoinColumnImpl.createJoinColumn(parent, member);
		}
		
		public Annotation buildNullAnnotation(JavaResourcePersistentMember parent, Member member) {
			return null;
		}

		public String getAnnotationName() {
			return ANNOTATION_NAME;
		}
	}
}
