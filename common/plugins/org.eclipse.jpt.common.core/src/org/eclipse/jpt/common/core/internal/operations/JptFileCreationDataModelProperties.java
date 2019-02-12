/*******************************************************************************
 *  Copyright (c) 2009, 2011  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License 2.0 which 
 *  accompanies this distribution, and is available at 
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.operations;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public interface JptFileCreationDataModelProperties
{
	/**
	 * Optional, type {@link IProject}, gives a clue as to the default container path
	 */
	String PROJECT = "JptFileCreationDataModelProperties.PROJECT"; //$NON-NLS-1$

	/**
	 * Required, type {@link IPath}, identifies the full path to the container in which 
	 * the file should be created
	 */
	String CONTAINER_PATH = "JptFileCreationDataModelProperties.CONTAINER_PATH"; //$NON-NLS-1$

	/**
	 * Required, type {@link String}, identifies the file name
	 */
	String FILE_NAME = "JptFileCreationDataModelProperties.FILE_NAME"; //$NON-NLS-1$
}
