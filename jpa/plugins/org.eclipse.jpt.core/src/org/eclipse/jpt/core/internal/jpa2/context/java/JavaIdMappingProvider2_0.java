/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.java;

import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaIdMappingProvider;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.ManyToOneAnnotation;
import org.eclipse.jpt.core.resource.java.OneToOneAnnotation;

public class JavaIdMappingProvider2_0
	extends AbstractJavaIdMappingProvider
{
	// singleton
	private static final JavaIdMappingProvider2_0 INSTANCE = 
		new JavaIdMappingProvider2_0();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private JavaIdMappingProvider2_0() {
		super();
	}
	
	
	@Override
	public boolean testSpecified(JavaPersistentAttribute persistentAttribute) {
		return super.testSpecified(persistentAttribute) && ! testDerivedId(persistentAttribute);
	}
	
	/**
	 * Return whether the Id annotation, if present, is used as a supporting annotation for
	 * 1-1 or M-1 mapping, rather than as a primary mapping annotation
	 */
	protected boolean testDerivedId(JavaPersistentAttribute persistentAttribute) {
		JavaResourcePersistentAttribute resourceAttribute = 
				persistentAttribute.getResourcePersistentAttribute();
		return resourceAttribute.getAnnotation(OneToOneAnnotation.ANNOTATION_NAME) != null
				|| resourceAttribute.getAnnotation(ManyToOneAnnotation.ANNOTATION_NAME) != null;
	}
}
