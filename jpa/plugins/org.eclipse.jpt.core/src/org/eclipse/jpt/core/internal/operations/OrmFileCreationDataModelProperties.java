/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

public interface OrmFileCreationDataModelProperties
{
	/**
	 * Required, type String, identifies the name of the project in which to create the file
	 */
	public static final String PROJECT_NAME = "OrmFileCreationDataModelProperties.PROJECT_NAME";
	
	/**
	 * Required, type String, identifies the fully pathed source folder in which to create the file
	 */
	public static final String SOURCE_FOLDER = "OrmFileCreationDataModelProperties.SOURCE_FOLDER";
	
	/**
	 * Required, type String, identifies the file path relative to the source folder
	 */
	public static final String FILE_PATH = "OrmFileCreationDataModelProperties.FILE_PATH";
	
	/**
	 * Optional, type AccessType, specifies the default access type (or null)
	 */
	public static final String DEFAULT_ACCESS = "OrmFileCreationDataModelProperties.DEFAULT_ACCESS";
	
	/**
	 * Required, type Boolean, specifies whether to add a reference to the file
	 * in the persistence unit
	 */
	public static final String ADD_TO_PERSISTENCE_UNIT = "OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT";
	
	/**
	 * Optional (unless ADD_TO_PERSISTENCE_UNIT property is true), type String, 
	 * identifies the persistence unit to which to add a reference to the file
	 */
	public static final String PERSISTENCE_UNIT = "OrmFileCreationDataModelProperties.PERSISTENCE_UNIT";
}
