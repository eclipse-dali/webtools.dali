/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.jpa.core.internal.context.JptValidator;

/**
 * Read-only join table relationship strategy.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see AssociationOverride
 * @see JoinTableRelationship
 */
public interface ReadOnlyJoinTableRelationshipStrategy
	extends RelationshipStrategy
{
	/**
	 * Change notification identifier for "joinTable" property
	 */
	String JOIN_TABLE_PROPERTY = "joinTable"; //$NON-NLS-1$

	/**
	 * Return the strategy's join table. This will be the 
	 * specified or default join table if one is specified or a default 
	 * join table applies, otherwise <code>null</code>.
	 */
	ReadOnlyJoinTable getJoinTable();

	/**
	 * Return the default name of the strategy's join table
	 */
	String getJoinTableDefaultName();

	boolean validatesAgainstDatabase();

	JptValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner);

	JptValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.Owner owner);
}
