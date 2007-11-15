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
import org.eclipse.jpt.core.internal.resource.java.Basic;

public class JavaBasicMappingProvider
	implements IDefaultJavaAttributeMappingProvider
{

	// singleton
	private static final JavaBasicMappingProvider INSTANCE = new JavaBasicMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static IDefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaBasicMappingProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String annotationName() {
		return Basic.ANNOTATION_NAME;
	}

	public IJavaAttributeMapping buildMapping(IJavaPersistentAttribute parent, IJpaBaseContextFactory factory) {
		return factory.createJavaBasicMapping(parent);
	}
	
	public boolean defaultApplies(IJavaPersistentAttribute persistentAttribute) {
		return persistentAttribute.getPersistentAttributeResource().typeIsBasic();
	}

}
