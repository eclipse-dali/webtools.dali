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

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.VirtualColumn;
import org.eclipse.jpt.core.context.XmlContextNode;

/**
 * <code>orm.xml</code> virtual column
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmVirtualColumn
	extends VirtualColumn, XmlContextNode
{
	/**
	 * The overridden column can be either a Java join column or an
	 * <code>orm.xml</code> join column; so we don't change the return type
	 * here.
	 */
	Column getOverriddenColumn();


	// ********** owner **********

	interface Owner
		extends VirtualColumn.Owner
	{
		/**
		 * The overridden column can be either a Java column or an
		 * <code>orm.xml</code> column; so we don't change the return type here.
		 */
		Column resolveOverriddenColumn();
	}
}
