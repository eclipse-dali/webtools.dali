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
import org.eclipse.jpt.core.internal.resource.java.ManyToOne;

public class JavaManyToOneMappingProvider
	implements IJavaAttributeMappingProvider
{

	// singleton
	private static final JavaManyToOneMappingProvider INSTANCE = new JavaManyToOneMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaManyToOneMappingProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return ManyToOne.ANNOTATION_NAME;
	}

	public IJavaAttributeMapping buildMapping(IJavaPersistentAttribute parent, IJpaBaseContextFactory factory) {
		return factory.createJavaManyToOneMapping(parent);
	}
}
