/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPlatformDefinition {
	
	JaxbFactory getFactory();
	
	AnnotationDefinition[] getAnnotationDefinitions();
	
	/**
	 * Return the resource model providers that apply to this platform.
	 */
	ListIterable<JaxbResourceModelProvider> getResourceModelProviders();
	
	/**
	 * Return the most recent resource type for the given content type supported by this platform
	 */
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType);
	
//	/**
//	 * Return the resource definitions supported by this platform.
//	 */
//	ListIterator<ResourceDefinition> resourceDefinitions();
//
//	/**
//	 * Return the java type mapping definitions that apply to this platform.
//	 */
//	ListIterator<JavaTypeMappingDefinition> javaTypeMappingDefinitions();
//	
//	/**
//	 * Return the mapping definitions to use for default java attribute mappings for this platform.
//	 */
//	ListIterator<JavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions();
//
//	/**
//	 * Return the mapping definitions to use for specified java attribute mappings for this platform.
//	 */
//	ListIterator<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions();
}
