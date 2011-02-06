/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.jpa.db.Table;

/**
 * attribute or association override container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.0
 * @since 2.3
 */
public interface OverrideContainer
	extends JpaContextNode
{
	/**
	 * Return the type mapping that this override is contained in
	 */
	TypeMapping getTypeMapping();

	/**
	 * Return the type mapping that contains the attributes/associations to
	 * be overridden. (Though the type mapping may not <em>directly</em>
	 * own them (i.e. they may be on a supertype mapping).
	 * (For example: for an entity, this would be the supertype mapping of
	 * that entity; for an embedded, this would be the target type mapping
	 * of the embedded.)
	 */
	TypeMapping getOverridableTypeMapping();

	/**
	 * Return the names of all attributes that can be overridden
	 */
	Iterator<String> allOverridableNames();

	/**
	 * Convert the specified specified override to <em>virtual</em>.
	 * Return the new override.
	 */
	VirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);

	/**
	 * Convert the specified virtual override to <em>specified</em>.
	 * Return the new override.
	 */
	Override_ convertOverrideToSpecified(VirtualOverride virtualOverride);

	/**
	 * return whether the given table cannot be explicitly specified
	 * in the column or join column's 'table' element
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return the names of tables that are valid for the overrides column or join columns.
	 */
	Iterator<String> candidateTableNames();

	/**
	 * Return the database table for the specified table name
	 */
	Table resolveDbTable(String tableName);

	/**
	 * Return the name of the table which the column belongs to by default
	 */
	String getDefaultTableName();

	JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver);

	JptValidator buildValidator(Override_ override, OverrideTextRangeResolver textRangeResolver);


	// ********** overrides **********

	/**
	 * Return the overrides, both specified and virtual.
	 */
	// TODO bjv change to a collection?
	ListIterator<? extends ReadOnlyOverride> overrides();

	/**
	 * Return the number of overrides, both specified and default.
	 */
	int overridesSize();

	/**
	 * Return the override with the specified name,
	 * whether specified or virtual.
	 */
	// TODO look into getting rid of this;
	// we should probably use #getSpecifiedOverrideNamed(String)
	ReadOnlyOverride getOverrideNamed(String name);


	// ********** specified overrides **********

	/**
	 * Return the specified overrides.
	 * No add/remove for specified overrides, the
	 * virtual overrides will be populated from the owner, then use
	 * {@link VirtualOverride#convertToSpecified()} to add/remove the
	 * override from the container.
	 */
	ListIterator<? extends Override_> specifiedOverrides();
		String SPECIFIED_OVERRIDES_LIST = "specifiedOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of specified overrides.
	 */
	int specifiedOverridesSize();

	/**
	 * Return the specified override at the specified index.
	 */
	Override_ getSpecifiedOverride(int index);

	/**
	 * Move the specified override from the source index to the target index.
	 */
	void moveSpecifiedOverride(int targetIndex, int sourceIndex);

	Override_ getSpecifiedOverrideNamed(String name);


	// ********** virtual overrides **********

	/**
	 * Return the virtual overrides, those not specified.
	 */
	// TODO change to a collection?
	ListIterator<? extends VirtualOverride> virtualOverrides();
		String VIRTUAL_OVERRIDES_LIST = "virtualOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of virtual overrides.
	 */
	int virtualOverridesSize();


	// ********** owner **********

	interface Owner
	{
		/**
		 * Return the mapping of the persistent type where the container is defined.
		 * (For example: for an entity, this would be the entity; for an embedded,
		 * this would be the type mapping where the embedded is defined.)
		 */
		TypeMapping getTypeMapping();

		/**
		 * Return the type mapping that contains the attributes/associations to
		 * be overridden. (Though the type mapping may not <em>directly</em>
		 * own them (i.e. they may be on a supertype mapping).
		 * (For example: for an entity, this would be the supertype mapping of
		 * that entity; for an embedded, this would be the target type mapping
		 * of the embedded.)
		 */
		TypeMapping getOverridableTypeMapping();

		/**
		 * Return all the names of the attributes/associations to be overridden.
		 * This is usually just all of the overridable names of the overridable
		 * type mapping.
		 * @see #getOverridableTypeMapping()
		 */
		Iterator<String> allOverridableNames();

		/**
		 * Return the name of an override column's/join column's default table.
		 */
		String getDefaultTableName();

		/**
		 * Return whether the specified table cannot be explicitly specified
		 * by a column/join column.
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * Return the database table for the specified table name.
		 */
		org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName);

		/**
		 * Return the table names that are valid for the override's column
		 * or join columns
		 */
		Iterator<String> candidateTableNames();

		JptValidator buildValidator(Override_ override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver);

		JptValidator buildColumnValidator(Override_ override, BaseColumn column, BaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver);
	}

}
