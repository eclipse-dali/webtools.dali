/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Generator
	extends JpaContextNode
{
	String SEQUENCE_GENERATOR = "sequenceGenerator"; //$NON-NLS-1$
	String TABLE_GENERATOR = "tableGenerator"; //$NON-NLS-1$

	// ********** name **********

	String getName();
	void setName(String value);
		String NAME_PROPERTY = "name"; //$NON-NLS-1$


	// ********** initial value **********

	int getInitialValue();
	Integer getSpecifiedInitialValue();
	void setSpecifiedInitialValue(Integer value);
		String SPECIFIED_INITIAL_VALUE_PROPERTY = "specifiedInitialValue"; //$NON-NLS-1$
	int getDefaultInitialValue();
		String DEFAULT_INITIAL_VALUE_PROPERTY = "defaultInitialValue"; //$NON-NLS-1$


	// ********** allocation size **********
	
	int getAllocationSize();
	Integer getSpecifiedAllocationSize();
	void setSpecifiedAllocationSize(Integer value);
		String SPECIFIED_ALLOCATION_SIZE_PROPERTY = "specifiedAllocationSize"; //$NON-NLS-1$
	int getDefaultAllocationSize();
	int DEFAULT_ALLOCATION_SIZE = 50;
		String DEFAULT_ALLOCATION_SIZE_PROPERTY = "defaultAllocationSize"; //$NON-NLS-1$


	// ********** validation **********
	
	/**
	 * Return whether the generator overrides the definition of the given generator
	 * (e.g. a generator defined in orm.xml overrides one defined in java).
	 */
	boolean overrides(Generator generator);
	
	/**
	 * Return whether the generator is a duplicate of the given generator.
	 * A generator is not a duplicate of another generator if is the same exact generator,
	 * if it is a nameless generator (which is an error condition), or if it overrides 
	 * or is overridden by the other generator. 
	 */
	boolean duplicates(Generator generator);

	boolean isVirtual();


	// ********** database stuff **********

	/**
	 * Return a db Schema object with the specified/default schema name.
	 * This can return null if no Schema exists on the database with that name.
	 */
	Schema getDbSchema();

	/**
	 * Return a db Catalog object with the specified/default catalog name.
	 * This can return null if no Catalog exists on the database with that name.
	 */
	Catalog getDbCatalog();

	/**
	 * Return a db container object that hold the relevant schemata.
	 */
	SchemaContainer getDbSchemaContainer();

}
