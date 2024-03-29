/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;
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
 * @version 3.3
 * @since 2.3
 */
public interface OverrideContainer
	extends JpaContextModel
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
	Iterable<String> getAllOverridableNames();

	/**
	 * Return whether the specified table cannot be explicitly specified
	 * as the table for an override's column or join column.
	 */
	boolean tableNameIsInvalid(String tableName);

	/**
	 * Return the names of tables that are valid for an override's
	 * column or join column.
	 */
	Iterable<String> getCandidateTableNames();

	/**
	 * Return the database table for the specified table name.
	 */
	Table resolveDbTable(String tableName);

	/**
	 * Return the name of the default table for an override's column or join column.
	 */
	String getDefaultTableName();

	JpaValidator buildOverrideValidator(Override_ override);

	JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter parentAdapter);


	// ********** overrides **********

	/**
	 * Return the overrides, both <em>specified</em> and <em>virtual</em>.
	 */
	ListIterable<? extends Override_> getOverrides();

	/**
	 * Return the number of overrides, both <em>specified</em> and <em>virtual</em>.
	 */
	int getOverridesSize();

	/**
	 * Return the override with the specified name,
	 * whether <em>specified</em> or <em>virtual</em>.
	 */
	// TODO look into getting rid of this;
	// we should probably use #getSpecifiedOverrideNamed(String)
	Override_ getOverrideNamed(String name);


	// ********** specified overrides **********

	/**
	 * Return the <em>specified</em> overrides. The container has no API for
	 * adding or removing <em>specified</em> overrides. The container's
	 * <em>virtual</em> overrides are built according to the list of overridable
	 * attribute names returned by the container's parent. <em>Specified</em>
	 * overrides can be created via {@link VirtualOverride#convertToSpecified()}.
	 * <em>Specified</em> overrides can be remvoed via
	 * {@link SpecifiedOverride#convertToVirtual()}.
	 */
	ListIterable<? extends SpecifiedOverride> getSpecifiedOverrides();
		String SPECIFIED_OVERRIDES_LIST = "specifiedOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of <em>specified</em> overrides.
	 */
	int getSpecifiedOverridesSize();

	/**
	 * Return the <em>specified</em> override at the specified index.
	 */
	SpecifiedOverride getSpecifiedOverride(int index);

	/**
	 * Move the <em>specified</em> override from the source index to the
	 * target index.
	 */
	void moveSpecifiedOverride(int targetIndex, int sourceIndex);

	/**
	 * Return the <em>specified</em> override at the specified index.
	 */
	SpecifiedOverride getSpecifiedOverrideNamed(String name);

	/**
	 * Convert the specified <em>specified</em> override to <em>virtual</em>.
	 * Return the new override.
	 */
	VirtualOverride convertOverrideToVirtual(SpecifiedOverride specifiedOverride);


	// ********** virtual overrides **********

	/**
	 * Return the <em>virtual</em> overrides (i.e. those not <em>specified</em>).
	 */
	// TODO change to a Iterable instead of ListIterable?
	ListIterable<? extends VirtualOverride> getVirtualOverrides();
		String VIRTUAL_OVERRIDES_LIST = "virtualOverrides"; //$NON-NLS-1$

	/**
	 * Return the number of <em>virtual</em> overrides.
	 */
	int getVirtualOverridesSize();

	/**
	 * Convert the specified <em>virtual</em> override to <em>specified</em>.
	 * Return the new override.
	 */
	SpecifiedOverride convertOverrideToSpecified(VirtualOverride virtualOverride);


	// ********** container parent adapter **********

	interface ParentAdapter {

		JpaContextModel getOverrideContainerParent();

		/**
		 * @see OverrideContainer#getTypeMapping()
		 */
		TypeMapping getTypeMapping();

		class AttributeIsOverridable
			extends CriterionPredicate<String, ParentAdapter>
		{
			public AttributeIsOverridable(ParentAdapter parentAdapter) {
				super(parentAdapter);
			}
			public boolean evaluate(String attributeName) {
				return ! this.criterion.getTypeMapping().attributeIsDerivedId(attributeName);
			}
		}

		/**
		 * @see OverrideContainer#getOverridableTypeMapping()
		 */
		TypeMapping getOverridableTypeMapping();

		/**
		 * @see OverrideContainer#getAllOverridableNames()
		 */
		Iterable<String> getAllOverridableNames();

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
		 * @see OverrideContainer#getCandidateTableNames()
		 */
		Iterable<String> getCandidateTableNames();

		TextRange getValidationTextRange();

		JpaValidator buildOverrideValidator(Override_ override, OverrideContainer container);

		JpaValidator buildColumnValidator(Override_ override, BaseColumn column, TableColumn.ParentAdapter parentAdapter);
	}
}
