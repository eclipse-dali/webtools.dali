/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.facet;

import org.eclipse.wst.common.frameworks.datamodel.IDataModelProperties;

public interface IJpaFacetDataModelProperties extends IDataModelProperties
{
	/**
	 * Required, type String, identifies Jpa Platform
	 */
	public static final String PLATFORM_ID = "IJpaFacetDataModelProperties.PLATFORM_ID";
	
	/**
	 * Not required, type String, identifies database connection
	 */
	public static final String CONNECTION = "IJpaFacetDataModelProperties.CONNECTION";
	
	/**
	 * Not required, type String, identifies JPA implementation library
	 */
	public static final String JPA_LIBRARY = "IJpaFacetDataModelProperties.JPA_LIBRARY";
	
	/**
	 * Required, type boolean, details whether orm.xml should be created
	 */
	public static final String CREATE_ORM_XML = "IJpaFacetDataModelProperties.CREATE_ORM_XML";
}
