/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;

/**
 * Java virtual join table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaVirtualJoinTable
	extends VirtualJoinTable, JavaReadOnlyTable
{
	JavaVirtualJoinTableRelationshipStrategy getParent();

	ListIterable<JavaVirtualUniqueConstraint> getUniqueConstraints();
	JavaVirtualUniqueConstraint getUniqueConstraint(int index);

	ListIterable<JavaVirtualJoinColumn> getJoinColumns();
	ListIterable<JavaVirtualJoinColumn> getSpecifiedJoinColumns();
	JavaVirtualJoinColumn getSpecifiedJoinColumn(int index);
	JavaVirtualJoinColumn getDefaultJoinColumn();

	ListIterable<JavaVirtualJoinColumn> getInverseJoinColumns();
	ListIterable<JavaVirtualJoinColumn> getSpecifiedInverseJoinColumns();
	JavaVirtualJoinColumn getSpecifiedInverseJoinColumn(int index);
	JavaVirtualJoinColumn getDefaultInverseJoinColumn();

	/**
	 * The overridden table can be either a Java table
	 * or an <code>orm.xml</code> table; so we don't change the
	 * return type here.
	 */
	public ReadOnlyJoinTable getOverriddenTable();
}
