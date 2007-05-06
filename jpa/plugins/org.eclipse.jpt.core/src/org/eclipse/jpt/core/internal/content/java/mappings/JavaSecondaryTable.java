/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.MemberIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Secondary Table</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaSecondaryTable()
 * @model kind="class"
 * @generated
 */
public class JavaSecondaryTable extends AbstractJavaTable
	implements ISecondaryTable
{
	private final IndexedAnnotationAdapter annotationAdapter;

	protected JavaSecondaryTable() {
		super();
		throw new UnsupportedOperationException("Use JavaSecondaryTable(Owner, Member, int) instead");
	}

	public JavaSecondaryTable(Owner owner, Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(owner, member, idaa);
		this.annotationAdapter = new MemberIndexedAnnotationAdapter(member, idaa);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_SECONDARY_TABLE;
	}

	/**
	 * allow owners to verify the annotation
	 */
	public Annotation annotation(CompilationUnit astRoot) {
		return this.annotationAdapter.getAnnotation(astRoot);
	}

	@Override
	protected void setDefaultName(String newDefaultName) {
		throw new UnsupportedOperationException("No default name for a secondary table");
	}

	// ********** AbstractJavaTable implementation **********
	@Override
	protected DeclarationAnnotationElementAdapter nameAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter(daa, JPA.SECONDARY_TABLE__NAME);
	}

	@Override
	protected DeclarationAnnotationElementAdapter schemaAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter(daa, JPA.SECONDARY_TABLE__SCHEMA);
	}

	@Override
	protected DeclarationAnnotationElementAdapter catalogAdapter(DeclarationAnnotationAdapter daa) {
		return new ConversionDeclarationAnnotationElementAdapter(daa, JPA.SECONDARY_TABLE__CATALOG);
	}

	// ********** persistence model -> java annotations **********
	void moveAnnotation(int newIndex) {
		this.annotationAdapter.moveAnnotation(newIndex);
	}

	void newAnnotation() {
		this.annotationAdapter.newMarkerAnnotation();
	}

	void removeAnnotation() {
		this.annotationAdapter.removeAnnotation();
	}

	// ********** static methods **********
	static JavaSecondaryTable createJavaSecondaryTable(Owner owner, Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaSecondaryTable(owner, member, buildDeclarationAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildDeclarationAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(JPA.SECONDARY_TABLE, JPA.SECONDARY_TABLES, index);
	}
} // JavaSecondaryTable
