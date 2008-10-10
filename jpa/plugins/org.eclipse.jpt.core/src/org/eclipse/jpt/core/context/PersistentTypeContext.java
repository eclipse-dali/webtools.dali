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
	 * Return the access type specified for the owned persistent type,
	 * null if none is specified
	 */
	AccessType getSpecifiedPersistentTypeAccess();
	
	/**
	 * Return the default access type for the owned persistent type,
	 * null if no default applies
	 */
	AccessType getDefaultPersistentTypeAccess();
}
