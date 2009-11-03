/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import java.util.ListIterator;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model.  The core JPA model will provide functionality for JPA
 * spec annotations in java, persistence.xml and mapping (orm.xml) files.
 * The org.eclipse.jpt.core.generic extension supplies 
 * resource models for those file types in GenericJpaPlatformProvider. 
 * 
 * This JpaPlatformProvider implementation most likely only returns providers
 * that are extensions of other platforms.  Then in the GenericJpaPlatform implementation
 * you pass in 1 or more JpaPlatformProviders.
 * 
 * See the org.eclipse.jpt.core.jpaPlatforms extension point
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatformProvider
{
	/**
	 * Return the most recent resource type for the given content type supported by this platform
	 */
	public JpaResourceType getMostRecentSupportedResourceType(IContentType contentType);
	
	/**
	 * Return the resource model providers that apply to this platform.
	 */
	ListIterator<JpaResourceModelProvider> resourceModelProviders();

	/**
	 * Return the resource definitions supported by this platform.
	 */
	ListIterator<ResourceDefinition> resourceDefinitions();

	/**
	 * Return the java type mapping definitions that apply to this platform.
	 */
	ListIterator<JavaTypeMappingDefinition> javaTypeMappingDefinitions();
	
	/**
	 * Return the mapping definitions to use for default java attribute mappings for this platform.
	 */
	ListIterator<JavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions();

	/**
	 * Return the mapping definitions to use for specified java attribute mappings for this platform.
	 */
	ListIterator<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions();
}
