/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa2.context.java.AbstractJavaIdMappingDefinition2_0;
import org.eclipse.jpt.utility.internal.StringTools;

public class JavaEclipseLinkIdMappingDefinition2_0
	extends AbstractJavaIdMappingDefinition2_0
{
	// singleton
	private static final JavaEclipseLinkIdMappingDefinition2_0 INSTANCE = 
		new JavaEclipseLinkIdMappingDefinition2_0();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaEclipseLinkIdMappingDefinition2_0() {
		super();
	}
	
	
	@Override
	protected boolean testDerivedId(JavaPersistentAttribute persistentAttribute) {
		String defaultMappingKey = persistentAttribute.getDefaultMappingKey();
		return super.testDerivedId(persistentAttribute)
				|| StringTools.stringsAreEqual(defaultMappingKey, MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				|| StringTools.stringsAreEqual(defaultMappingKey, MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY);
	}
}
