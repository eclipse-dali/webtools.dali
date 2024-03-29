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
package org.eclipse.jpt.jpa.core.internal.operations;

import org.eclipse.jpt.common.core.internal.operations.JptFileCreationDataModelProperties;

public interface JpaFileCreationDataModelProperties extends JptFileCreationDataModelProperties
{

	/**
	 * Required, type {@link String}, identifies the version of the file to create
	 */
	String VERSION = "JpaFileCreationDataModelProperties.VERSION"; //$NON-NLS-1$
}
