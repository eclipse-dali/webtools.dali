/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model.  The core JPA model will provide functionality for JPA
 * spec annotations in java, persistence.xml and mapping (orm.xml) files.
 * The org.eclipse.jpt.jpa.core.generic extension supplies 
 * resource models for those file types in GenericJpaPlatformProvider. 
 * 
 * This JpaPlatformProvider implementation most likely only returns providers
 * that are extensions of other platforms.  Then in the GenericJpaPlatform implementation
 * you pass in 1 or more JpaPlatformProviders.
 * <p>
 * See the org.eclipse.jpt.jpa.core.jpaPlatforms extension point
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JpaPlatformProvider {
	/**
	 * Return the most recent resource type for the specified content type
	 * supported by the JPA platform
	 */
	Iterable<JptResourceType> getMostRecentSupportedResourceTypes();
	
	/**
	 * Return the JPA platform's resource model providers.
	 */
	Iterable<JpaResourceModelProvider> getResourceModelProviders();

	/**
	 * Return the JPA platform's resource definitions.
	 */
	Iterable<JpaResourceDefinition> getResourceDefinitions();

	/**
	 * Return the JPA platform's Java managed type definitions.
	 */
	Iterable<JavaManagedTypeDefinition> getJavaManagedTypeDefinitions();

	/**
	 * Return the JPA platform's Java type mapping definitions.
	 */
	Iterable<JavaTypeMappingDefinition> getJavaTypeMappingDefinitions();
	
	/**
	 * Return the JPA platform's default Java attribute mapping definitions.
	 */
	Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions();

	/**
	 * Return the JPA platform's specified Java attribute mapping definitions.
	 */
	Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions();
}
