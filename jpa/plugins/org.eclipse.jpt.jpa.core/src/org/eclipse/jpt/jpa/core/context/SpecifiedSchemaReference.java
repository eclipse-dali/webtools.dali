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
 * @version 2.2
 * @since 2.2
 */
public interface SpecifiedSchemaReference
	extends SchemaReference
{
	String getSpecifiedSchema();
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$

	String getSpecifiedCatalog();
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$
}
