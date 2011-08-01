/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.NestedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NullAssociationOverrideJoinTableAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceAssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.internal.resource.java.source.SourceJoinTableAnnotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.AssociationOverride2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JoinTableAnnotation;

/**
 * <code>javax.persistence.AssociationOverride</code>
 */
public final class SourceAssociationOverride2_0Annotation
	extends SourceAssociationOverrideAnnotation
	implements AssociationOverride2_0Annotation
{
	private ElementAnnotationAdapter joinTableAdapter;
	private JoinTableAnnotation joinTable;
	private final JoinTableAnnotation nullJoinTable;

	
	public static SourceAssociationOverride2_0Annotation buildSourceAssociationOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element) {
		
		return new SourceAssociationOverride2_0Annotation(parent, element, DECLARATION_ANNOTATION_ADAPTER);
	}

	public static SourceAssociationOverride2_0Annotation buildSourceAssociationOverrideAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildAssociationOverrideDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildAssociationOverrideAnnotationAdapter(annotatedElement, idaa);
		return new SourceAssociationOverride2_0Annotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}

	public static SourceAssociationOverride2_0Annotation buildNestedSourceAssociationOverrideAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceAssociationOverride2_0Annotation(parent, element, idaa);
	}

	private SourceAssociationOverride2_0Annotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			DeclarationAnnotationAdapter daa) {
		
		this(parent, element, daa, new ElementAnnotationAdapter(element, daa));
	}

	private SourceAssociationOverride2_0Annotation(
			JavaResourceNode parent, 
			AnnotatedElement element, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		this(parent, element, idaa, new ElementIndexedAnnotationAdapter(element, idaa));
	}

	private SourceAssociationOverride2_0Annotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, element, daa, annotationAdapter);
		this.joinTableAdapter = this.buildJoinTableAdapter();
		this.nullJoinTable = this.buildNullJoinTable();
	}

	@Override
	public void initialize(CompilationUnit astRoot) {
		super.initialize(astRoot);
		if (this.joinTableAdapter.getAnnotation(astRoot) != null) {
			this.joinTable = buildJoinTableAnnotation(this, this.annotatedElement, this.daa);
			this.joinTable.initialize(astRoot);
		}
	}

	@Override
	public void synchronizeWith(CompilationUnit astRoot) {
		super.synchronizeWith(astRoot);
		this.syncJoinTable(astRoot);
	}


	//************ AssociationOverride2_0Annotation implementation ****************

	// ***** joinTable
	public JoinTableAnnotation getJoinTable() {
		return this.joinTable;
	}

	public JoinTableAnnotation getNonNullJoinTable() {
		return (this.joinTable != null) ? this.joinTable : this.nullJoinTable;
	}

	public JoinTableAnnotation addJoinTable() {
		if (this.joinTable != null) {
			throw new IllegalStateException("'joinTable' element already exists: " + this.joinTable); //$NON-NLS-1$
		}
		this.joinTable = buildJoinTableAnnotation(this, this.annotatedElement, this.daa);
		this.joinTable.newAnnotation();
		return this.joinTable;
	}

	public void removeJoinTable() {
		if (this.joinTable == null) {
			throw new IllegalStateException("'joinTable' element does not exist"); //$NON-NLS-1$
		}
		JoinTableAnnotation old = this.joinTable;
		this.joinTable = null;
		old.removeAnnotation();
	}

	private void syncJoinTable(CompilationUnit astRoot) {
		if (this.joinTableAdapter.getAnnotation(astRoot) == null) {
			this.syncJoinTable_(null);
		} else {
			if (this.joinTable == null) {
				JoinTableAnnotation table = buildJoinTableAnnotation(this, this.annotatedElement, this.daa);
				table.initialize(astRoot);
				this.syncJoinTable_(table);
			} else {
				this.joinTable.synchronizeWith(astRoot);
			}
		}
	}
	
	private void syncJoinTable_(JoinTableAnnotation astJoinTable) {
		JoinTableAnnotation old = this.joinTable;
		this.joinTable = astJoinTable;
		this.firePropertyChanged(JOIN_TABLE_PROPERTY, old, astJoinTable);
	}

	private ElementAnnotationAdapter buildJoinTableAdapter() {
		return new ElementAnnotationAdapter(this.annotatedElement, buildJoinTableAnnotationAdapter(this.daa));
	}

	private JoinTableAnnotation buildNullJoinTable() {
		return new NullAssociationOverrideJoinTableAnnotation(this);
	}
	

	// ********** misc **********

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.joinTable == null);
	}

	
	// ********** static methods **********

	static JoinTableAnnotation buildJoinTableAnnotation(JavaResourceNode parent, AnnotatedElement element, DeclarationAnnotationAdapter associationOverrideAnnotationAdapter) {
		return new SourceJoinTableAnnotation(parent, element, buildJoinTableAnnotationAdapter(associationOverrideAnnotationAdapter));
	}

	static DeclarationAnnotationAdapter buildJoinTableAnnotationAdapter(DeclarationAnnotationAdapter associationOverrideAnnotationAdapter) {
		return new NestedDeclarationAnnotationAdapter(associationOverrideAnnotationAdapter, JPA2_0.ASSOCIATION_OVERRIDE__JOIN_TABLE, JPA.JOIN_TABLE);
	}
}
