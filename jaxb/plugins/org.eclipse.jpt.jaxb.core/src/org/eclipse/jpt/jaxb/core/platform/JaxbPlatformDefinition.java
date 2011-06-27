/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jaxb.core.JaxbFactory;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.jaxb.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jaxb.core.context.java.JavaAttributeMappingDefinition;

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
	
	JaxbPlatformDescription getDescription();
	
	JaxbFactory getFactory();
	
	AnnotationDefinition[] getAnnotationDefinitions();

	NestableAnnotationDefinition[] getNestableAnnotationDefinitions();
	
	/**
	 * Return the resource model providers that apply to this platform.
	 */
	ListIterable<JaxbResourceModelProvider> getResourceModelProviders();
	
	/**
	 * Return the most recent resource type for the given content type supported by this platform
	 */
	public JptResourceType getMostRecentSupportedResourceType(IContentType contentType);
	
//	/**
//	 * Return the resource definitions supported by this platform.
//	 */
//	ListIterator<ResourceDefinition> resourceDefinitions();

	/**
	 * Return the mapping definitions to use for default java attribute mappings for this platform.
	 */
	Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions();

	/**
	 * Return the mapping definitions to use for specified java attribute mappings for this platform.
	 */
	Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions();
}
