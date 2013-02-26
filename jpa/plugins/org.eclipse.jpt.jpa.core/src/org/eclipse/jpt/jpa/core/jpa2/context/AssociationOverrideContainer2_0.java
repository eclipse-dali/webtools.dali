/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;

/**
 * JPA 2.0
 * Association override container.
 * Used by entities, embedded mappings, and element collection mappings.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface AssociationOverrideContainer2_0
	extends AssociationOverrideContainer
{
	JptValidator buildJoinTableValidator(AssociationOverride override, ReadOnlyTable table);

	JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owne);

	JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner);


	// ********** owner **********

	interface Owner
		extends AssociationOverrideContainer.Owner
	{
		JptValidator buildJoinTableValidator(AssociationOverride override, ReadOnlyTable table);

		JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner);

		JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, ReadOnlyJoinColumn column, ReadOnlyJoinColumn.Owner owner);
	}
}
