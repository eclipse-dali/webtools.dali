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
package org.eclipse.jpt.core.context;

public interface PersistentTypeContext extends JpaContextNode
{
	/**
	 * Return the access type that overrides any access specified locally for the 
	 * owned persistent type, null if there is no such access override
	 */
	AccessType getOverridePersistentTypeAccess();
	
	/**
	 * Return the default access type to be applied to the owned persistent type,
	 * null if no default applies
	 */
	AccessType getDefaultPersistentTypeAccess();
}
