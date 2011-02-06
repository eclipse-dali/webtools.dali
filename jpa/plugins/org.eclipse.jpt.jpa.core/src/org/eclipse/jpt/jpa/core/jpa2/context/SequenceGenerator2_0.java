/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.SequenceGenerator;

/**
 * JPA 2.0 sequence generator
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface SequenceGenerator2_0
	extends SequenceGenerator
{
	// ********** catalog **********

	/**
	 * Return the specified catalog if present, otherwise return the default
	 * catalog.
	 */
	String getCatalog();
	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String value);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$


	// ********** schema **********

	/**
	 * Return the specified schema if present, otherwise return the default
	 * schema.
	 */
	String getSchema();
	String getSpecifiedSchema();
	void setSpecifiedSchema(String value);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$

}
