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
import org.eclipse.jpt.core.internal.jdtutility.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.INamedQuery;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Named Query</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNamedQuery()
 * @model kind="class"
 * @generated
 */
public class JavaNamedQuery extends JavaAbstractQuery implements INamedQuery
{
	public static final SimpleDeclarationAnnotationAdapter SINGLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_QUERY);

	public static final SimpleDeclarationAnnotationAdapter MULTIPLE_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JPA.NAMED_QUERIES);

	protected JavaNamedQuery() {
		throw new UnsupportedOperationException("Use JavaNamedQuery(Member) instead");
	}

	protected JavaNamedQuery(Member member, IndexedDeclarationAnnotationAdapter idaa) {
		super(member, idaa);
	}

	@Override
	protected String nameElementName() {
		return JPA.NAMED_QUERY__NAME;
	}

	@Override
	protected String queryElementName() {
		return JPA.NAMED_QUERY__QUERY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_NAMED_QUERY;
	}

	protected JavaQueryHint createJavaQueryHint(int index) {
		return JavaQueryHint.createNamedQueryQueryHint(this.getMember(), index);
	}

	// ********** static methods **********
	static JavaNamedQuery createJavaNamedQuery(Member member, int index) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaNamedQuery(member, buildAnnotationAdapter(index));
	}

	private static IndexedDeclarationAnnotationAdapter buildAnnotationAdapter(int index) {
		return new CombinationIndexedDeclarationAnnotationAdapter(SINGLE_DECLARATION_ANNOTATION_ADAPTER, MULTIPLE_DECLARATION_ANNOTATION_ADAPTER, index, JPA.NAMED_QUERY);
	}
} // JavaNamedQuery
