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

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

/**
 * This mapping provider implementation is used to create a JavaNullAttributeMapping.
 * A JavaNullAttributeMapping should be used when no "mapping" annotation
 * exists on a Java type. 
 */
public class JavaNullTypeMappingProvider
	implements IJavaTypeMappingProvider
{

	// singleton
	private static final JavaNullTypeMappingProvider INSTANCE = new JavaNullTypeMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaNullTypeMappingProvider() {
		super();
	}

	public String key() {
		return null;
	}

	public IJavaTypeMapping buildMapping(Type type, IJpaFactory factory) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaNullTypeMapping(type);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return JavaNullTypeMapping.DECLARATION_ANNOTATION_ADAPTER;
	}

}
