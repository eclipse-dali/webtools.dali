/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jpa2.resource.java.NullAssociationOverrideJoinTableAnnotation;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAssociationOverrideAnnotation;
import org.eclipse.jpt.core.internal.resource.java.source.SourceJoinTableAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.MemberAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.NestedIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.core.jpa2.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.core.resource.java.JoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.NestableJoinTableAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember.AnnotationInitializer;
import org.eclipse.jpt.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.Member;

/**
 *  SourceSequenceGenerator2_0Annotation
 */
public final class SourceAssociationOverride2_0Annotation
	extends SourceAssociationOverrideAnnotation
	implements AssociationOverride2_0Annotation
{
	private final MemberAnnotationAdapter joinTableAdapter;
	private NestableJoinTableAnnotation joinTable;


	// ********** constructor **********
	public SourceAssociationOverride2_0Annotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, member, daa, annotationAdapter);
		this.joinTableAdapter = new MemberAnnotationAdapter(this.member, buildJoinTableAnnotationAdapter(this.daa));
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.joinTableAdapter.getAnnotation(astRoot) != null) {
			this.joinTable = buildJoinTableAnnotation(this, this.member, this.daa);
			this.joinTable.initialize(astRoot);
		}
	}

	@Override
	public void update(CompilationUnit astRoot) {
		super.update(astRoot);
		this.updateJoinTable(astRoot);
	}

	//************ AssociationOverride2_0Annotation implementation ****************

	// ***** joinTable
	public JoinTableAnnotation getJoinTable() {
		return this.joinTable;
	}

	public NestableJoinTableAnnotation addJoinTable() {
		NestableJoinTableAnnotation table = buildJoinTableAnnotation(this, this.member, this.daa);
		table.newAnnotation();
		this.setJoinTable(table);
		return table;
	}

	public JoinColumnAnnotation addJoinTable(AnnotationInitializer initializer) {
		NestableJoinTableAnnotation table = buildJoinTableAnnotation(this, this.member, this.daa);
		JoinColumnAnnotation joinColumn = (JoinColumnAnnotation) initializer.initializeAnnotation(table);
		table.newAnnotation();
		this.setJoinTable(table);
		return joinColumn;
	}
	
	public void removeJoinTable() {
		this.joinTable.removeAnnotation();
		setJoinTable(null);
	}

	private void setJoinTable(NestableJoinTableAnnotation joinTable) {
		JoinTableAnnotation old = this.joinTable;
		this.joinTable = joinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, joinTable);
	}

	public JoinTableAnnotation getNonNullJoinTable() {
		return (this.joinTable != null) ? this.joinTable : new NullAssociationOverrideJoinTableAnnotation(this);
	}
	
	private void updateJoinTable(CompilationUnit astRoot) {
		if (this.joinTableAdapter.getAnnotation(astRoot) == null) {
			this.setJoinTable(null);
		} else {
			if (this.joinTable == null) {
				NestableJoinTableAnnotation table = buildJoinTableAnnotation(this, this.member, this.daa);
				table.initialize(astRoot);
				this.setJoinTable(table);
			} else {
				this.joinTable.update(astRoot);
			}
		}
	}
	
	
	// ********** static methods **********

	public static SourceAssociationOverride2_0Annotation buildAssociationOverride(JavaResourceNode parent, Member member) {
		return new SourceAssociationOverride2_0Annotation(parent, member, DECLARATION_ANNOTATION_ADAPTER, new MemberAnnotationAdapter(member, DECLARATION_ANNOTATION_ADAPTER));
	}

	static NestableJoinTableAnnotation buildJoinTableAnnotation(JavaResourceNode parent, Member member, DeclarationAnnotationAdapter associationOverrideAnnotationAdapter) {
		return new SourceJoinTableAnnotation(parent, member, buildJoinTableAnnotationAdapter(associationOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(associationOverrideAnnotationAdapter, JPA.ASSOCIATION_OVERRIDE__JOIN_TABLE, org.eclipse.jpt.core.resource.java.JPA.JOIN_TABLE);
	}

	
	static SourceAssociationOverrideAnnotation buildNestedAssociationOverride(JavaResourceNode parent, Member member, int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		IndexedDeclarationAnnotationAdapter idaa = buildNestedDeclarationAnnotationAdapter(index, attributeOverridesAdapter);
		IndexedAnnotationAdapter annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
		return new SourceAssociationOverride2_0Annotation(parent, member, idaa, annotationAdapter);
	}

	protected static IndexedDeclarationAnnotationAdapter buildNestedDeclarationAnnotationAdapter(int index, DeclarationAnnotationAdapter attributeOverridesAdapter) {
		return new NestedIndexedDeclarationAnnotationAdapter(attributeOverridesAdapter, index, org.eclipse.jpt.core.resource.java.JPA.ASSOCIATION_OVERRIDE);
	}

}