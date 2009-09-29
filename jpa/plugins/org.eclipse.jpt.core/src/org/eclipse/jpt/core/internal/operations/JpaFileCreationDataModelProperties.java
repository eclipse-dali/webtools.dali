/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.operations;

public interface JpaFileCreationDataModelProperties
{
	/**
	 * Required, type String, identifies the name of the project in which to create the file
	 */
	public static final String PROJECT_NAME = "JpaFileCreationDataModelProperties.PROJECT_NAME";
	
	/**
	 * Required, type String, identifies the fully pathed source folder in which to create the file
	 */
	public static final String SOURCE_FOLDER = "JpaFileCreationDataModelProperties.SOURCE_FOLDER";
	
	/**
	 * Required, type String, identifies the file path relative to the source folder
	 */
	public static final String FILE_PATH = "JpaFileCreationDataModelProperties.FILE_PATH";
	
	/**
	 * Required, type String, identifies the version of the file to create
	 */
	public static final String VERSION = "JpaFileCreationDataModelProperties.VERSION";
}
