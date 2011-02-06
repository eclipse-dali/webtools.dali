/*******************************************************************************
 *  Copyright (c) 2009, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;

public interface JpaFacetDataModelProperties
	extends IDataModelProperties
{
	String PREFIX = "JpaFacetDataModelProperties";
	String PREFIX_ = PREFIX + '.';
	
	/**
	 * Internal, type IRuntime, identifies runtime associated with project
	 * Used only in conjunction with validation of other properties, because this information
	 * is otherwise inaccessible to this data model
	 */
	String RUNTIME = PREFIX_ + "RUNTIME";
	
	/**
	 * Required, type JpaPlatformDescription, identifies Jpa Platform
	 */
	String PLATFORM = PREFIX_ + "PLATFORM";
	
	/**
	 * Required, type LibraryInstallDelegate, the library install delegate used to configure JPA provider library
	 */
    String LIBRARY_PROVIDER_DELEGATE = PREFIX_ + "LIBRARY_PROVIDER_DELEGATE";
	
    /**
	 * Not required, type String, identifies database connection
	 */
	String CONNECTION = PREFIX_ + "CONNECTION";
	
	/**
	 * Required, type Boolean, identifies whether database connection is active
	 */
	String CONNECTION_ACTIVE = PREFIX_ + "CONNECTION_ACTIVE";
	
	/**
	 * Required, type Boolean, identifies if the user wishes to override default Catalog name
	 */
	String USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG = PREFIX_ + "USER_WANTS_TO_OVERRIDE_DEFAULT_CATALOG";
	
	/**
	 * Not required, type String, identifies the user overridden default Catalog name
	 */
	String USER_OVERRIDE_DEFAULT_CATALOG = PREFIX_ + "USER_OVERRIDE_DEFAULT_CATALOG";

	/**
	 * Required, type Boolean, identifies if the user wishes to override default schema name
	 */
	String USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA = PREFIX_ + "USER_WANTS_TO_OVERRIDE_DEFAULT_SCHEMA";
	
	/**
	 * Not required, type String, identifies the user overridden default schema name
	 */
	String USER_OVERRIDE_DEFAULT_SCHEMA = PREFIX_ + "USER_OVERRIDE_DEFAULT_SCHEMA";
	
	/**
	 * Required, type boolean, opposite of LIST_ANNOTATED_CLASSES, identifies 
	 * whether all annotated classes are to be automatically included as part of 
	 * all persistence units
	 */
	String DISCOVER_ANNOTATED_CLASSES = PREFIX_ + "DISCOVER_ANNOTATED_CLASSES";
	
	/**
	 * Required, type boolean, opposite of DISCOVER_ANNOTATED_CLASSES, identifies
	 * if annotated classes should be listed in the persistence.xml in order to
	 * be considered part of a persistence unit
	 * (This additional setting is necessary in order to use synchHelper within
	 * the wizard - there must be a unique property for each radio button. Stupid
	 * but true)
	 */
	String LIST_ANNOTATED_CLASSES = PREFIX_ + "LIST_ANNOTATED_CLASSES";
}
