/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
	 * Return the type mapping that contains the override container.
	 * For example:<ul>
	 * <li>for an entity, this would be the entity itself
	 * <li>for an embedded, this would be type mapping where the embedded is
	 *     declared, as opposed to the type mapping the embedded references
	 * </ul>
	 */
	TypeMapping getTypeMapping();

	/**
	 * Return the type mapping that contains the attributes/associations to
	 * be overridden; though the type mapping may not <em>directly</em>
	 * own them (e.g. they may be owned by a supertype mapping).
	 * For example:<ul>
	 * <li>for an entity, this would be the entity's supertype mapping
	 * <li>for an embedded, this would be the embedded's target type mapping
	 * </ul>
	 */
	TypeMapping getOverridableTypeMapping();

	/**
	 * Return the names of all the attributes that can be overridden
	 * (i.e. an override must have a name from this list).
	 * This is usually just all of the overridable names of the overridable
	 * type mapping.
	 * @see #getOverridableTypeMapping()
	 */
	Iterator<String> allOverridableNames();

	/**
	 * Return whether the specified table cannot be explicitly specified
	 * as the table for an override's column or join column.
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return the names of tables that are valid for an override's
	 * column or join column.
	 */
	Iterator<String> candidateTableNames();

	/**
	 * Return the database table for the specified table name.
	 */
	Table resolveDbTable(String tableName);

	/**
	 * Return the name of the default table for an override's column or join column.
	 */
	String getDefaultTableName();

	JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideTextRangeResolver textRangeResolver);

	JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner owner, BaseColumnTextRangeResolver textRangeResolver);


	// ********** overrides **********

	/**
	 * Return the overrides, both <em>specified</em> and <em>virtual</em>.
	 */
	// TODO bjv change to an iterable
	ListIterator<? extends ReadOnlyOverride> overrides();

	/**
	 * Return the number of overrides, both <em>specified</em> and <em>virtual</em>.
	 */
	int overridesSize();

	/**
	 * Return the override with the specified name,
	 * whether <em>specified</em> or <em>virtual</em>.
	 */
	// TODO look into getting rid of this;
	// we should probably use #getSpecifiedOverrideNamed(String)
	ReadOnlyOverride getOverrideNamed(String name);


	// ********** specified overrides **********

	/**
	 * Return the <em>specified</em> overrides. The container has no API for
	 * adding or removing <em>specified</em> overrides. The container's
	 * <em>virtual</em> overrides are built according to the list of overridable
	 * attribute names returned by the container's parent. <em>Specified</em>
	 * overrides can be created via {@link VirtualOverride#convertToSpecified()}.
	 * <em>Specified</em> overrides can be remvoed via
	 * {@link Override_#convertToVirtual()}.
	 */
	ListIterator<? extends Override_> specifiedOverrides();
		String SPECIFIED_OVERRIDES_LIST = "specifiedOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of <em>specified</em> overrides.
	 */
	int specifiedOverridesSize();

	/**
	 * Return the <em>specified</em> override at the specified index.
	 */
	Override_ getSpecifiedOverride(int index);

	/**
	 * Move the <em>specified</em> override from the source index to the
	 * target index.
	 */
	void moveSpecifiedOverride(int targetIndex, int sourceIndex);

	/**
	 * Return the <em>specified</em> override at the specified index.
	 */
	Override_ getSpecifiedOverrideNamed(String name);

	/**
	 * Convert the specified <em>specified</em> override to <em>virtual</em>.
	 * Return the new override.
	 */
	VirtualOverride convertOverrideToVirtual(Override_ specifiedOverride);


	// ********** virtual overrides **********

	/**
	 * Return the <em>virtual</em> overrides (i.e. those not <em>specified</em>).
	 */
	// TODO change to a collection?
	ListIterator<? extends VirtualOverride> virtualOverrides();
		String VIRTUAL_OVERRIDES_LIST = "virtualOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of <em>virtual</em> overrides.
	 */
	int virtualOverridesSize();

	/**
	 * Convert the specified <em>virtual</em> override to <em>specified</em>.
	 * Return the new override.
	 */
	Override_ convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** container owner **********

	interface Owner
	{
		/**
		 * @see OverrideContainer#getTypeMapping()
		 */
		TypeMapping getTypeMapping();

		/**
		 * @see OverrideContainer#getOverridableTypeMapping()
		 */
		TypeMapping getOverridableTypeMapping();

		/**
		 * @see OverrideContainer#allOverridableNames()
		 */
		Iterator<String> allOverridableNames();

		/**
		 * @see OverrideContainer#getDefaultTableName()
		 */
		String getDefaultTableName();

		/**
		 * @see OverrideContainer#tableNameIsInvalid(String)
		 */
		boolean tableNameIsInvalid(String tableName);

		/**
		 * @see OverrideContainer#resolveDbTable(String)
		 */
		org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName);

		/**
		 * @see OverrideContainer#candidateTableNames()
		 */
		Iterator<String> candidateTableNames();

		JptValidator buildOverrideValidator(ReadOnlyOverride override, OverrideContainer container, OverrideTextRangeResolver textRangeResolver);

		JptValidator buildColumnValidator(ReadOnlyOverride override, ReadOnlyBaseColumn column, ReadOnlyBaseColumn.Owner columnOwner, BaseColumnTextRangeResolver textRangeResolver);
	}
}
