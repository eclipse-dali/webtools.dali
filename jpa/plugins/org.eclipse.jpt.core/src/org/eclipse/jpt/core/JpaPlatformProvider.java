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
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;

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
	 * Return the resource model providers that apply to this platform.
	 */
	ListIterator<JpaResourceModelProvider> resourceModelProviders();

	/**
	 * Return the java type mapping providers that apply to this platform.
	 */
	ListIterator<JavaTypeMappingProvider> javaTypeMappingProviders();

	/**
	 * Return the java attribute mapping providers that apply to this platform.
	 */
	ListIterator<JavaAttributeMappingProvider> javaAttributeMappingProviders();

	/**
	 * Return the mapping file providers that apply to this platform.
	 */
	ListIterator<MappingFileProvider> mappingFileProviders();

	/**
	 * Return the default java attribute mapping providers that apply to this platform.
	 */
	ListIterator<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders();

	/**
	 * Return the orm type mapping providers that apply to this platform.
	 */
	ListIterator<OrmTypeMappingProvider> ormTypeMappingProviders();

	/**
	 * Return the orm attribute mapping providers that apply to this platform.
	 */
	ListIterator<OrmAttributeMappingProvider> ormAttributeMappingProviders();

}
