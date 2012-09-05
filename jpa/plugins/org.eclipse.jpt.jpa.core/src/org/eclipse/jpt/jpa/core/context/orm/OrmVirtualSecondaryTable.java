/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.VirtualSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;

/**
 * <code>orm.xml</code> virtual secondary table
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmVirtualSecondaryTable
	extends VirtualSecondaryTable
{
	ListIterable<OrmVirtualPrimaryKeyJoinColumn> getPrimaryKeyJoinColumns();
	ListIterable<OrmVirtualPrimaryKeyJoinColumn> getSpecifiedPrimaryKeyJoinColumns();
	OrmVirtualPrimaryKeyJoinColumn getDefaultPrimaryKeyJoinColumn();

	JavaSecondaryTable getOverriddenTable();
}
