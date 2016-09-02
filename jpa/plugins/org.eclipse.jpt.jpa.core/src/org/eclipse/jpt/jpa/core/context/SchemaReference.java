/*******************************************************************************
 * Copyright (c) 2010, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Common interface that can be used by clients interested in only an object's
 * schema and/or catalog (e.g. a UI composite).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.7
 * @since 3.7
 */
public interface SchemaReference
	extends JpaContextModel
{
	/**
	 * Return the schema, whether specified or defaulted.
	 */
	String getSchema();
		String SCHEMA_PROPERTY = "schema"; //$NON-NLS-1$

	/**
	 * Return the catalog, whether specified or defaulted.
	 */
	String getCatalog();
		String CATALOG_PROPERTY = "catalog"; //$NON-NLS-1$
}
