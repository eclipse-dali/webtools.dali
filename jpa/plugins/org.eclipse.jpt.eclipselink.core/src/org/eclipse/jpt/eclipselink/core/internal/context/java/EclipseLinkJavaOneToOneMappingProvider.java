/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingProvider;

public class EclipseLinkJavaOneToOneMappingProvider
	implements DefaultJavaAttributeMappingProvider
{

	// singleton
	private static final EclipseLinkJavaOneToOneMappingProvider INSTANCE = new EclipseLinkJavaOneToOneMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EclipseLinkJavaOneToOneMappingProvider() {
		super();
	}

	public String getKey() {
		return JavaOneToOneMappingProvider.instance().getKey();
	}
	
	public String getAnnotationName() {
		return JavaOneToOneMappingProvider.instance().getAnnotationName();
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return JavaOneToOneMappingProvider.instance().buildMapping(parent, factory);
	}
	
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		if (persistentAttribute.getResourcePersistentAttribute().typeIsContainer()) {
			return false;
		}
		String targetEntity = persistentAttribute.getResourcePersistentAttribute().getQualifiedReferenceEntityTypeName();
		if (targetEntity == null) {
			return false;
		}
		return persistentAttribute.getPersistenceUnit().getEntity(targetEntity) != null;
	}
}
