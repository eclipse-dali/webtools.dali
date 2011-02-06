/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;

/**
 * <ul>
 * <li>table
 * <li>secondary table
 * <li>join table
 * <li>collection table
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface Table
	extends ReadOnlyTable
{
	void setSpecifiedName(String value);

	void setSpecifiedSchema(String value);

	void setSpecifiedCatalog(String value);

	ListIterator<? extends UniqueConstraint> uniqueConstraints();
	UniqueConstraint getUniqueConstraint(int index);
	UniqueConstraint addUniqueConstraint();
	UniqueConstraint addUniqueConstraint(int index);
	void removeUniqueConstraint(int index);
	void removeUniqueConstraint(UniqueConstraint uniqueConstraint);
	void moveUniqueConstraint(int targetIndex, int sourceIndex);


	// ********** misc **********

	/**
	 * Return whether the table is specified in the
	 * (Java or XML) resource.
	 */
	boolean isSpecifiedInResource();

	/**
	 * Return whether the table can be resolved to a table on the database.
	 */
	boolean isResolved();

	/**
	 * Return whether the table's schema can be resolved to a schema on the
	 * database.
	 */
	boolean schemaIsResolved();

	/**
	 * Return whether the table has a catalog and it can be resolved to a
	 * catalog on the database.
	 */
	boolean catalogIsResolved();

	/**
	 * Return whether the table is validated against a live database connection.
	 */
	boolean validatesAgainstDatabase();

	/**
	 * interface allowing columns to be used in multiple places
	 * (e.g. basic mappings and attribute overrides)
	 */
	interface Owner
	{
		JptValidator buildTableValidator(Table table, TableTextRangeResolver textRangeResolver);
	}
}
