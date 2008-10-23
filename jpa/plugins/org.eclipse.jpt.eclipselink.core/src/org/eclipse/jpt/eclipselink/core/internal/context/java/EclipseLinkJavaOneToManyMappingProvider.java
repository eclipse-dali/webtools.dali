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
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingProvider;

public class EclipseLinkJavaOneToManyMappingProvider
	implements DefaultJavaAttributeMappingProvider
{

	// singleton
	private static final EclipseLinkJavaOneToManyMappingProvider INSTANCE = new EclipseLinkJavaOneToManyMappingProvider();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJavaOneToManyMappingProvider() {
		super();
	}

	public String getKey() {
		return JavaOneToManyMappingProvider.instance().getKey();
	}
	
	public String getAnnotationName() {
		return JavaOneToManyMappingProvider.instance().getAnnotationName();
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return JavaOneToManyMappingProvider.instance().buildMapping(parent, factory);
	}
	
	public boolean defaultApplies(JavaPersistentAttribute persistentAttribute) {
		if (!persistentAttribute.getResourcePersistentAttribute().typeIsContainer()) {
			return false;
		}
		String targetEntity = persistentAttribute.getResourcePersistentAttribute().getQualifiedReferenceEntityElementTypeName();
		if (targetEntity == null) {
			return false;
		}
		return persistentAttribute.getPersistenceUnit().getEntity(targetEntity) != null;
	}
}
