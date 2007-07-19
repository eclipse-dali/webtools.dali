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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;

/**
 * 
 */
public class JavaVersionProvider
	implements IJavaAttributeMappingProvider
{

	// singleton
	private static final JavaVersionProvider INSTANCE = new JavaVersionProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaVersionProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public IJavaAttributeMapping buildMapping(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaVersion(attribute);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return JavaVersion.DECLARATION_ANNOTATION_ADAPTER;
	}

}
