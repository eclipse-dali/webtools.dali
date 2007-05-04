/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NullDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Null Type Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullTypeMapping()
 * @model kind="class"
 * @generated
 */
public class JavaNullTypeMapping extends JavaTypeMapping
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = NullDeclarationAnnotationAdapter.instance();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaNullTypeMapping() {
		super();
	}

	protected JavaNullTypeMapping(Type type) {
		super(type);
	}

	@Override
	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_NULL_TYPE_MAPPING;
	}

	public String getKey() {
		return null;
	}
}
