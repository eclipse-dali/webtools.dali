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

import org.eclipse.jpt.jpa.core.internal.context.JpaValidator;

/**
 * Join table relationship strategy.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @see AssociationOverride
 * @see SpecifiedJoinTableRelationship
 */
public interface JoinTableRelationshipStrategy
	extends RelationshipStrategy
{
	/**
	 * Change notification identifier for "joinTable" property
	 */
	String JOIN_TABLE_PROPERTY = "joinTable"; //$NON-NLS-1$

	/**
	 * Return the strategy's join table. This will be the specified join table
	 * or the default join table if a default join table is possible;
	 * otherwise return <code>null</code>.
	 */
	JoinTable getJoinTable();

	/**
	 * Return the default name of the strategy's join table
	 */
	String getJoinTableDefaultName();

	boolean validatesAgainstDatabase();

	JpaValidator buildJoinTableJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter);

	JpaValidator buildJoinTableInverseJoinColumnValidator(JoinColumn column, JoinColumn.ParentAdapter parentAdapter);
}
