/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMappingDefinition;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingDefinition;

public class JavaEclipseLinkOneToOneMappingDefinition
	extends AbstractJavaAttributeMappingDefinition
{
	// singleton
	private static final JavaEclipseLinkOneToOneMappingDefinition INSTANCE = 
			new JavaEclipseLinkOneToOneMappingDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkOneToOneMappingDefinition() {
		super();
	}
	
	
	public String getKey() {
		return JavaOneToOneMappingDefinition.instance().getKey();
	}
	
	public String getAnnotationName() {
		return JavaOneToOneMappingDefinition.instance().getAnnotationName();
	}
	
	public JavaAttributeMapping buildMapping(JavaPersistentAttribute parent, JpaFactory factory) {
		return JavaOneToOneMappingDefinition.instance().buildMapping(parent, factory);
	}
	
	@Override
	public boolean testDefault(JavaPersistentAttribute persistentAttribute) {
		String targetEntity = persistentAttribute.getSingleReferenceEntityTypeName();
		return (targetEntity != null)
				&& (persistentAttribute.getPersistenceUnit().getEntity(targetEntity) != null);
	}
}
