/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformDescription;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model. The core JPA model will provide functionality for JPA
 * spec annotations in Java, <code>persistence.xml</code> and mapping
 * (<code>orm.xml</code>) files.
 * The <code>org.eclipse.jpt.jpa.core.generic</code> extension supplies 
 * resource models for those file types. As another vendor option you 
 * will have to supply those resource models as well or different ones 
 * as necessary. In the extension point you actually provide a
 * {@link JpaPlatformFactory} that will build the JPA platform.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * <p>
 * See the <code>org.eclipse.jpt.jpa.core.jpaPlatforms</code> extension point.
 * @see JpaPlatformFactory
 * @see JpaPlatformProvider
 * 
 * @version 3.0
 * @since 2.0
 */
public interface JpaPlatform
{
	// ********** meta stuff **********

	/**
	 * Get the ID for this platform
	 */
	String getId();
	
	/**
	 * Return the description for this platform
	 */
	JpaPlatformDescription getDescription();
	
	/**
	 * Get the version object for this platform. 
	 */
	Version getJpaVersion();
	
	
	// ********** factory **********

	/**
	 * Return a factory responsible for creating core (e.g. JpaProject), resource
	 * (e.g. PersistenceResource), and context (e.g. PersistenceUnit) model
	 * objects
	 */
	JpaFactory getJpaFactory();


	// ********** JPA files **********

	/**
	 * Return a JPA file corresponding to the specified Eclipse file.
	 * Return null if the file's content is unsupported.
	 */
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file);


	// ********** Java annotations **********

	/**
	 * Return an annotation provider responsible for determining what Java
	 * annotations are supported and constructing java resource model objects.
	 */
	AnnotationProvider getAnnotationProvider();

	/**
	 * Return a formatter that can clean up the Java annotations added to source
	 * code.
	 */
	AnnotationEditFormatter getAnnotationEditFormatter();


	// ********** Java type mappings **********

	/**
	 * Return the Java type mapping definitions that will be used to build Java
	 * type mappings and their corresponding annotations.
	 */
	Iterable<JavaTypeMappingDefinition> getJavaTypeMappingDefinitions();


	// ********** Java attribute mappings **********

	/**
	 * Return the Java attribute mapping definitions that will be used to build
	 * Java attribute mappings and their corresponding annotations.
	 */
	Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions();

	/**
	 * Return the Java attribute mapping definitions that will be used to build
	 * default Java attribute mappings.
	 */
	Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions();

	
	// ********** resource types and definitions **********
	
	/**
	 * Return whether the platform supports the specified resource type.
	 * This method is consistent with {@link #getResourceDefinition(JptResourceType)}.
	 */
	boolean supportsResourceType(JptResourceType resourceType);
	
	/**
	 * Return the platform's resource definition for the specified resource type.
	 * The returned definition describes the platform's corresponding context model.
	 * Throw an {@link IllegalArgumentException} if the resource type is not
	 * supported by the platform.
	 * This method is consistent with {@link #supportsResourceType(JptResourceType)}.
	 */
	ResourceDefinition getResourceDefinition(JptResourceType resourceType);
	
	/**
	 * Return the most recent supported resource type for the specified content
	 * type. Throw an {@link IllegalArgumentException} if the content type is not
	 * supported by the platform.
	 */
	JptResourceType getMostRecentSupportedResourceType(IContentType contentType);
	
	
	// ********** database **********

	/**
	 * Return a connection repository that can be used to query the database
	 * about database metadata.
	 */
	ConnectionProfileFactory getConnectionProfileFactory();

	/**
	 * Return an entity generator database annotation name builder, which is
	 * used by Entity Generation to determine whether and how the entity generator
	 * prints the names of various database objects.
	 */
	EntityGeneratorDatabaseAnnotationNameBuilder getEntityGeneratorDatabaseAnnotationNameBuilder();

	
	// ********** platform variation **********

	/**
	 * Return a platform variation that is used to determine differences platforms and/or JPA specification versions
	 */
	JpaPlatformVariation getJpaVariation();

	
	interface Version {
		
		/**
		 * Return the platform's version.
		 */
		String getVersion();
		
		/**
		 * Return the highest JPA specification version supported by the platform.
		 * @see JpaFacet#VERSION_1_0
		 * @see JpaFacet#VERSION_2_0
		 */
		String getJpaVersion();
		
		/**
		 * Return whether the platform is compatible with the specified JPA
		 * specification version.
		 * @see JpaFacet#VERSION_1_0
		 * @see JpaFacet#VERSION_2_0
		 */
		boolean isCompatibleWithJpaVersion(String jpaVersion);
	}
}
