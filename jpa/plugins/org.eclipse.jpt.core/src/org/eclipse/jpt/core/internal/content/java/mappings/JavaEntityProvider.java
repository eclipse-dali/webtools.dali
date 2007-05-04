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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;

/**
 * 
 */
public class JavaEntityProvider
	implements IJavaTypeMappingProvider
{

	// singleton
	private static final JavaEntityProvider INSTANCE = new JavaEntityProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEntityProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.ENTITY_TYPE_MAPPING_KEY;
	}

	public IJavaTypeMapping buildMapping(Type type) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEntity(type);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return JavaEntity.DECLARATION_ANNOTATION_ADAPTER;
	}

}
