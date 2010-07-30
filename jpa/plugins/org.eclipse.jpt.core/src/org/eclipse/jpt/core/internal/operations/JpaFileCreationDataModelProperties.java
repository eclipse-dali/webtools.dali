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
package org.eclipse.jpt.core.internal.operations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public interface JpaFileCreationDataModelProperties
{
	/**
	 * Optional, type {@link IProject}, gives a clue as to the default container path
	 */
	public static final String PROJECT = "JpaFileCreationDataModelProperties.PROJECT";
	
	/**
	 * Required, type {@link IPath}, identifies the full path to the container in which 
	 * the file should be created
	 */
	public static final String CONTAINER_PATH = "JpaFileCreationDataModelProperties.CONTAINER_PATH";
	
	/**
	 * Required, type {@link String}, identifies the file name
	 */
	public static final String FILE_NAME = "JpaFileCreationDataModelProperties.FILE_NAME";
	
	/**
	 * Required, type {@link String}, identifies the version of the file to create
	 */
	public static final String VERSION = "JpaFileCreationDataModelProperties.VERSION";
}
