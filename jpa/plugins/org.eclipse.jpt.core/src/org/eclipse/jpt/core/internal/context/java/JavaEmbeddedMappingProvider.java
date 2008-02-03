/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.platform.base.IJpaBaseContextFactory;
import org.eclipse.jpt.core.internal.resource.java.Embedded;

public class JavaEmbeddedMappingProvider
	implements IDefaultJavaAttributeMappingProvider
{

	// singleton
	private static final JavaEmbeddedMappingProvider INSTANCE = new JavaEmbeddedMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IDefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEmbeddedMappingProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Embedded.ANNOTATION_NAME;
	}

	public IJavaAttributeMapping buildMapping(IJavaPersistentAttribute parent, IJpaBaseContextFactory factory) {
		return factory.createJavaEmbeddedMapping(parent);
	}
	
	public boolean defaultApplies(IJavaPersistentAttribute persistentAttribute) {
		return JavaEmbeddedMapping.embeddableFor(persistentAttribute) != null;
	}
}
