/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;

public interface JpaFacetDataModelProperties extends IDataModelProperties
{
	/**
	 * Internal, type IRuntime, identifies runtime associated with project
	 * Used only in conjunction with validation of other properties, because this information
	 * is otherwise inaccessible to this data model
	 */
	public static final String RUNTIME = "JpaFacetDataModelProperties.RUNTIME";
	
	/**
	 * Required, type String, identifies Jpa Platform
	 */
	public static final String PLATFORM_ID = "JpaFacetDataModelProperties.PLATFORM_ID";
	
	/**
	 * Required, type LibraryInstallDelegate, the library install delegate used to configure JPA provider library
	 */
    public static final String LIBRARY_PROVIDER_DELEGATE = "JpaFacetDataModelProperties.LIBRARY_PROVIDER_DELEGATE"; //$NON-NLS-1$    
	
    /**
	 * Not required, type String, identifies database connection
	 */
	public static final String CONNECTION = "JpaFacetDataModelProperties.CONNECTION";
	
	/**
	 * Required, type Boolean, identifies whether database connection is active
	 */
	public static final String CONNECTION_ACTIVE = "JpaFacetDataModelProperties.CONNECTION_ACTIVE";
	
	/**
	 * Required, type Boolean, identifies if the user wishes to add the database driver jars to the classpath
	 */
	public static final String USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH = 
		"JpaFacetDataModelProperties.USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH";

	/**
	 * Not required, type String, identifies the database driver library added to the classpath
	 */
	public static final String DB_DRIVER_NAME = "JpaFacetDataModelProperties.DB_DRIVER_NAME";
	
	/**
	 * Required, type Boolean, identifies if the user wishes to override default schema name
	 */
	public static final String USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA = 
		"JpaFacetDataModelProperties.USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA";
	
	/**
	 * Not required, type String, identifies the user overridden default schema name
	 */
	public static final String USER_OVERRIDE_DEFAULT_SCHEMA = 
		"JpaFacetDataModelProperties.USER_OVERRIDE_DEFAULT_SCHEMA";
	
	/**
	 * Required, type boolean, opposite of LIST_ANNOTATED_CLASSES, identifies 
	 * whether all annotated classes are to be automatically included as part of 
	 * all persistence units
	 */
	public static final String DISCOVER_ANNOTATED_CLASSES = "JpaFacetDataModelProperties.DISCOVER_ANNOTATED_CLASSES";
	
	/**
	 * Required, type boolean, opposite of DISCOVER_ANNOTATED_CLASSES, identifies
	 * if annotated classes should be listed in the persistence.xml in order to
	 * be considered part of a persistence unit
	 * (This additional setting is necessary in order to use synchHelper within
	 * the wizard - there must be a unique property for each radio button. Stupid
	 * but true)
	 */
	public static final String LIST_ANNOTATED_CLASSES = "JpaFacetDataModelProperties.LIST_ANNOTATED_CLASSES";
	
	/**
	 * Required, type boolean, details whether orm.xml should be created
	 */
	public static final String CREATE_ORM_XML = "JpaFacetDataModelProperties.CREATE_ORM_XML";
}
