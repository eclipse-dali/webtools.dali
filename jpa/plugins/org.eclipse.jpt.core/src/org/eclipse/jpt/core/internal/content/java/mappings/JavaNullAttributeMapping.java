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
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.NullDeclarationAnnotationAdapter;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Java Null Attribute Mapping</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see org.eclipse.jpt.core.internal.content.java.mappings.JpaJavaMappingsPackage#getJavaNullAttributeMapping()
 * @model kind="class"
 * @generated
 */
public class JavaNullAttributeMapping extends JavaAttributeMapping
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = NullDeclarationAnnotationAdapter.instance();

	protected JavaNullAttributeMapping() {
		throw new UnsupportedOperationException("Use JavaNullAttributeMapping(Attribute) instead");
	}

	protected JavaNullAttributeMapping(Attribute attribute) {
		super(attribute);
	}

	@Override
	protected DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return DECLARATION_ANNOTATION_ADAPTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JpaJavaMappingsPackage.Literals.JAVA_NULL_ATTRIBUTE_MAPPING;
	}

	public String getKey() {
		return null;
	}
}