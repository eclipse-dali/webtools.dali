/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Read-only (<em>specified</em> or <em>virtual</em>)<ul>
 * <li>attribute override
 * <li>association override
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ReadOnlyOverride
	extends JpaContextNode
{
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$

	/**
	 * Return <code>true</code> if the override is not explicitly specified on
	 * the override's parent (i.e. it occurs by default); return <code>false</code>
	 * if the override is explicitly specified on the override's parent.
	 * 
	 * @see Override_#convertToVirtual()
	 * @see VirtualOverride#convertToSpecified()
	 */
	// TODO bjv rename?
	boolean isVirtual();

	/**
	 * @see OverrideContainer#getTypeMapping()
	 */
	TypeMapping getTypeMapping();

	/**
	 * Return whether the specified table cannot be explicitly specified
	 * as the table for the override's column or join column.
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return the names of tables that are valid for the override's
	 * column or join column.
	 */
	Iterable<String> getCandidateTableNames();

	/**
	 * Return the database table for the specified table name.
	 */
	Table resolveDbTable(String tableName);

	/**
	 * Return the name of the default table for the override's column or join column.
	 */
	String getDefaultTableName();

	JptValidator buildColumnValidator(ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver);
}
