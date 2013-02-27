/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTableRelationship;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;

/**
 * JPA 2.0 association override relationship
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see SpecifiedAssociationOverride
 */
public interface ReadOnlyOverrideRelationship2_0
	extends ReadOnlyOverrideRelationship,
			ReadOnlyJoinTableRelationship
{
	JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner);

	JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner);

	JptValidator buildJoinTableValidator(ReadOnlyJoinTable table);
}
