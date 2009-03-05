/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.core.utility.jdt.AnnotationEditFormatter;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.DatabaseFinder;

/**
 * This interface is to be implemented by a JPA vendor to provide extensions to 
 * the core JPA model.  The core JPA model will provide functionality for JPA
 * spec annotations in java, persistence.xml and mapping (orm.xml) files.
 * The org.eclipse.jpt.core.generic extension supplies 
 * resource models for those file types.  As another vendor option you 
 * will have to supply those resource models as well or different ones 
 * as necessary. In the extension point you actually provide a JpaPlatformFactory
 * class used to build the JpaPlatform.
 * 
 * See the org.eclipse.jpt.core.jpaPlatforms extension point
 * @see JpaPlatformFactory
 * @see JpaPlatformProvider
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaPlatform
{
	/**
	 * Get the ID for this platform
	 */
	String getId();

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
	JpaAnnotationProvider getAnnotationProvider();

	/**
	 * Return a formatter that can clean up the Java annotations added to source
	 * code.
	 */
	AnnotationEditFormatter getAnnotationEditFormatter();


	// ********** Java type mappings **********

	/**
	 * Build a Java type mapping for the specified key and persistent type.
	 * Use identity when comparing keys; so clients must use the same key
	 * constants as the providers.
	 * Throw an IllegalArgumentException if the key is not supported by the
	 * platform.
	 */
	JavaTypeMapping buildJavaTypeMappingFromMappingKey(String key, JavaPersistentType type);

	/**
	 * Build a Java type mapping for the specified annotation and persistent
	 * type. Use identity when comparing annotation names; so clients must
	 * use the same name constants as the providers.
	 * Throw an IllegalArgumentException if the mapping annotation is not
	 * supported by the platform.
	 */
	JavaTypeMapping buildJavaTypeMappingFromAnnotation(String annotationName, JavaPersistentType type);


	// ********** Java attribute mappings **********

	/**
	 * Build a Java attribute mapping for the specified key and persistent attribute.
	 * Use identity when comparing keys; so clients must use the same key
	 * constants as the providers.
	 * Throw an IllegalArgumentException if the key is not supported by the
	 * platform.
	 */
	JavaAttributeMapping buildJavaAttributeMappingFromMappingKey(String key, JavaPersistentAttribute attribute);

	/**
	 * Build a Java attribute mapping for the specified annotation and persistent
	 * attribute. Use identity when comparing annotation names; so clients must
	 * use the same name constants as the providers.
	 * Throw an IllegalArgumentException if the mapping annotation is not
	 * supported by the platform.
	 */
	JavaAttributeMapping buildJavaAttributeMappingFromAnnotation(String annotationName, JavaPersistentAttribute attribute);

	/**
	 * Build a default Java attribute mapping for the specified persistent attribute.
	 */
	JavaAttributeMapping buildDefaultJavaAttributeMapping(JavaPersistentAttribute attribute);

	/**
	 * Return the Java attribute mapping key for the default mapping for the
	 * specified attribute.
	 * @see org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider#defaultApplies(JavaPersistentAttribute)
	 */
	String getDefaultJavaAttributeMappingKey(JavaPersistentAttribute attribute);


	// ********** Mapping File **********

	MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource);


	// ********** ORM type mappings **********

	XmlTypeMapping buildOrmResourceTypeMapping(String key, IContentType contentType);

	/**
	 * Build an ORM type mapping for the specified mapping key and persistent type.
	 * Use identity when comparing keys; so clients must use the same key
	 * constants as the providers.
	 */
	OrmTypeMapping buildOrmTypeMappingFromMappingKey(OrmPersistentType type, XmlTypeMapping resourceMapping);


	// ********** ORM attribute mappings **********

	XmlAttributeMapping buildOrmResourceAttributeMapping(String key, IContentType contentType);

	/**
	 * Build an ORM attribute mapping for the specified key and persistent attribute.
	 * Use identity when comparing keys; so clients must use the same key
	 * constants as the providers.
	 */
	OrmAttributeMapping buildOrmAttributeMappingFromMappingKey(OrmPersistentAttribute parent, XmlAttributeMapping resourceMapping);

	/**
	 * Build a virtual resource attribute mapping to be used when the mapping is not specified in the orm.xml
	 * file.  There is no XmlAttributeMapping in this case, so we build one that delegates to the 
	 * JavaAttributeMapping as necessary
	 */
	XmlAttributeMapping buildVirtualOrmResourceMappingFromMappingKey(String key, OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping);


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

	/**
	 * Return a finder that can be used to look up various objects (schemata,
	 * tables, columns, etc.) on the database, respecting the platform's and
	 * database's case-sensitivity.
	 */
	DatabaseFinder getDatabaseFinder();

	
	// ********** validation **********
	
	JpaValidation getJpaValidation();
}
