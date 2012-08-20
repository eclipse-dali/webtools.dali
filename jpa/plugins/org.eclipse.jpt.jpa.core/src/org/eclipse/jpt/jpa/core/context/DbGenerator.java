/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Sequence and table generators.
 * <p>
 * Sequences can be defined on<ul>
 * <li>Java and <code>orm.xml</code> entities
 * <li>Java and <code>orm.xml</code> ID mappings
 * <li><code>orm.xml</code> entity mappings elements
 * </ul>
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 3.3
 */
public interface DbGenerator
	extends Generator
{

	// ********** initial value **********

	/**
	 * Return the specified initial value if present, otherwise return the
	 * default initial value.
	 */
	int getInitialValue();
	Integer getSpecifiedInitialValue();
	void setSpecifiedInitialValue(Integer value);
		String SPECIFIED_INITIAL_VALUE_PROPERTY = "specifiedInitialValue"; //$NON-NLS-1$
	int getDefaultInitialValue();
		String DEFAULT_INITIAL_VALUE_PROPERTY = "defaultInitialValue"; //$NON-NLS-1$


	// ********** allocation size **********

	/**
	 * Return the specified allocation size if present, otherwise return the
	 * default allocation size.
	 */
	int getAllocationSize();
	Integer getSpecifiedAllocationSize();
	void setSpecifiedAllocationSize(Integer value);
		String SPECIFIED_ALLOCATION_SIZE_PROPERTY = "specifiedAllocationSize"; //$NON-NLS-1$
	int getDefaultAllocationSize();
		String DEFAULT_ALLOCATION_SIZE_PROPERTY = "defaultAllocationSize"; //$NON-NLS-1$
	int DEFAULT_ALLOCATION_SIZE = 50;


	// ********** database stuff **********

	/**
	 * Return the schema container that holds the relevant schemata.
	 */
	SchemaContainer getDbSchemaContainer();

	/**
	 * Return the generator's database catalog.
	 * Return null if the generator's catalog (name) is invalid.
	 */
	Catalog getDbCatalog();

	/**
	 * Return the generator's database schema.
	 * Return null if the generator's schema (name) is invalid.
	 */
	Schema getDbSchema();
}
