/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import java.util.ListIterator;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;

/**
 * Association override container.
 * Used by entities, embedded mappings, and element collection mappings.
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
public interface AssociationOverrideContainer
	extends OverrideContainer
{
	ListIterator<? extends ReadOnlyAssociationOverride> overrides();
	ReadOnlyAssociationOverride getOverrideNamed(String name);
	ListIterator<? extends AssociationOverride> specifiedOverrides();
	AssociationOverride getSpecifiedOverride(int index);
	AssociationOverride getSpecifiedOverrideNamed(String name);
	ListIterator<? extends VirtualAssociationOverride> virtualOverrides();
	VirtualAssociationOverride convertOverrideToVirtual(Override_ specifiedOverride);
	AssociationOverride convertOverrideToSpecified(VirtualOverride virtualOverride);

	/**
	 * Return the relationship mapping with the given attribute name.
	 * Return null if it does not exist.  This relationship mapping
	 * will be found in the mapped superclass, not in the owning entity
	 */
	RelationshipMapping getRelationshipMapping(String attributeName);

	/**
	 * Return the relationship with the specified attribute name.
	 */
	Relationship resolveOverriddenRelationship(String attributeName);

	JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

	JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

	JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver);


	// ********** owner **********

	interface Owner
		extends OverrideContainer.Owner
	{
		Relationship resolveOverriddenRelationship(String attributeName);

		JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

		JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, JoinColumn.Owner owner, JoinColumnTextRangeResolver textRangeResolver);

		JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver);
	}
}
