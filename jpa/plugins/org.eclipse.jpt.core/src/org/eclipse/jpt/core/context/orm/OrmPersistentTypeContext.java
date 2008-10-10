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
package org.eclipse.jpt.core.context.orm;

import org.eclipse.jpt.core.context.PersistentTypeContext;

public interface OrmPersistentTypeContext extends PersistentTypeContext
{
	/**
	 * Return the default package to be used for persistent types in this context
	 */
	String getDefaultPersistentTypePackage();
	
	/**
	 * Return the default metadata complete value for persistent types in this context
	 */
	boolean isDefaultPersistentTypeMetadataComplete();
	
	/**
	 * Change the type mapping for the persistent type in this context
	 */
	void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping);
}
