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
import org.eclipse.jpt.core.internal.resource.java.Embeddable;

public class JavaEmbeddableProvider
	implements IJavaTypeMappingProvider
{

	// singleton
	private static final JavaEmbeddableProvider INSTANCE = new JavaEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEmbeddableProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Embeddable.ANNOTATION_NAME;
	}

	public IJavaTypeMapping buildMapping(IJavaPersistentType parent, IJpaBaseContextFactory factory) {
		return factory.createJavaEmbeddable(parent);
	}

}
