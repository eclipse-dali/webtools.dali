/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

@SuppressWarnings("nls")
public interface JpaFacetInstallDataModelProperties
	extends JpaFacetDataModelProperties
{
	String PREFIX = "JpaFacetInstallDataModelProperties";
	String PREFIX_ = PREFIX + '.';
	
	/**
	 * Required, type Boolean, identifies if the user wishes to add the database driver jars to the classpath
	 */
	String USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH = PREFIX_ + "USER_WANTS_TO_ADD_DB_DRIVER_JARS_TO_CLASSPATH";

	/**
	 * Not required, type String, identifies the database driver library added to the classpath
	 */
	String DB_DRIVER_NAME = PREFIX_ + "DB_DRIVER_NAME";

	/**
	 * Required, type boolean, details whether orm.xml should be created
	 */
	String CREATE_ORM_XML = PREFIX_ + "CREATE_ORM_XML";
}
