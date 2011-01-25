/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.VirtualJoinTable;
import org.eclipse.jpt.core.context.XmlContextNode;

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
	extends VirtualJoinTable, XmlContextNode
{
	OrmVirtualJoinTableRelationshipStrategy getParent();

	ListIterator<OrmVirtualUniqueConstraint> uniqueConstraints();
	OrmVirtualUniqueConstraint getUniqueConstraint(int index);

	ListIterator<OrmVirtualJoinColumn> joinColumns();
	ListIterator<OrmVirtualJoinColumn> specifiedJoinColumns();
	OrmVirtualJoinColumn getSpecifiedJoinColumn(int index);
	OrmVirtualJoinColumn getDefaultJoinColumn();

	ListIterator<OrmVirtualJoinColumn> inverseJoinColumns();
	ListIterator<OrmVirtualJoinColumn> specifiedInverseJoinColumns();
	OrmVirtualJoinColumn getSpecifiedInverseJoinColumn(int index);
	OrmVirtualJoinColumn getDefaultInverseJoinColumn();
}
