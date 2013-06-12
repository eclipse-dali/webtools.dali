/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
import org.eclipse.jpt.jpa.db.Table;

/**
 * <em>Specified</em> or <em>virtual</em><ul>
 * <li>attribute override
 * <li>association override
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
// the class name "Override" is sorta taken: java.lang.Override
// maybe if it weren't in the java.lang package we would take it on, but... :)
// very little code will directly refer to this interface, so the underscore
// isn't *too* evil...
public interface Override_
	extends JpaContextModel
{
	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	Transformer<Override_, String> NAME_TRANSFORMER = new NameTransformer();
	class NameTransformer
		extends TransformerAdapter<Override_, String>
	{
		@Override
		public String transform(Override_ override) {
			return override.getName();
		}
	}

	/**
	 * Return <code>true</code> if the override is not explicitly specified on
	 * the override's parent (i.e. it occurs by default); return <code>false</code>
	 * if the override is explicitly specified on the override's parent.
	 * 
	 * @see SpecifiedOverride#convertToVirtual()
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

	/**
	 * Return the (best guess) text location of the override's name.
	 */
	TextRange getNameTextRange();

	JpaValidator buildColumnValidator(BaseColumn column, TableColumn.ParentAdapter parentAdapter);
}
