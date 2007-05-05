/*******************************************************************************
 *  Copyright (c) 2005, 2007 Oracle. All rights reserved.  This program and 
 *  the accompanying materials are made available under the terms of the 
 *  Eclipse Public License v1.0 which accompanies this distribution, and is 
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

public interface IJpaCoreConstants
{
	/**
	 * Configuration option
	 */
	public static final String JPA_PLATFORM = JptCorePlugin.PLUGIN_ID + ".platform";  //$NON-NLS-1$
	
	/**
	 * Configuration option
	 */
	public static final String DATA_SOURCE_CONNECTION_NAME = JptCorePlugin.PLUGIN_ID + ".dataSource.connectionName";  //$NON-NLS-1$
	
	/**
	 * Configuration option
	 */
	public static final String DISCOVER_ANNOTATED_CLASSES = JptCorePlugin.PLUGIN_ID + ".discoverAnnotatedClasses";  //$NON-NLS-1$
	
	/**
	 * Name of META-INF directory
	 */
	public static final String META_INF = "META-INF";
}
