/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;

/**
 * <code>orm.xml</code> virtual join table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmVirtualJoinTable
	extends VirtualJoinTable, OrmReadOnlyTable
{
	OrmVirtualJoinTableRelationshipStrategy getParent();

	ListIterable<OrmVirtualUniqueConstraint> getUniqueConstraints();
	OrmVirtualUniqueConstraint getUniqueConstraint(int index);

	ListIterable<OrmVirtualJoinColumn> getJoinColumns();
	ListIterable<OrmVirtualJoinColumn> getSpecifiedJoinColumns();
	OrmVirtualJoinColumn getSpecifiedJoinColumn(int index);
	OrmVirtualJoinColumn getDefaultJoinColumn();

	ListIterable<OrmVirtualJoinColumn> getInverseJoinColumns();
	ListIterable<OrmVirtualJoinColumn> getSpecifiedInverseJoinColumns();
	OrmVirtualJoinColumn getSpecifiedInverseJoinColumn(int index);
	OrmVirtualJoinColumn getDefaultInverseJoinColumn();

	/**
	 * The overridden table can be either a Java table
	 * or an <code>orm.xml</code> table; so we don't change the
	 * return type here.
	 */
	public ReadOnlyJoinTable getOverriddenTable();
}
