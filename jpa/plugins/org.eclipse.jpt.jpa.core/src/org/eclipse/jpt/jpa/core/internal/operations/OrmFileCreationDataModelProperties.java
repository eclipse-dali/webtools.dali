/*******************************************************************************
 *  Copyright (c) 2008, 2011  Oracle. 
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

public interface OrmFileCreationDataModelProperties
	extends JpaFileCreationDataModelProperties
{
	/**
	 * Optional, type AccessType, specifies the default access type (or null)
	 */
	String DEFAULT_ACCESS = "OrmFileCreationDataModelProperties.DEFAULT_ACCESS"; //$NON-NLS-1$
	
	/**
	 * Required, type Boolean, specifies whether to add a reference to the file
	 * in the persistence unit
	 */
	String ADD_TO_PERSISTENCE_UNIT = "OrmFileCreationDataModelProperties.ADD_TO_PERSISTENCE_UNIT"; //$NON-NLS-1$
	
	/**
	 * Optional (unless ADD_TO_PERSISTENCE_UNIT property is true), type String, 
	 * identifies the persistence unit to which to add a reference to the file
	 */
	String PERSISTENCE_UNIT = "OrmFileCreationDataModelProperties.PERSISTENCE_UNIT"; //$NON-NLS-1$
}
