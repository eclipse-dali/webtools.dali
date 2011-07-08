/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Virtual primary key join column. There are only <em>orm.xml</em> virtual
 * primary key join columns; used to represent the default columns taken from
 * the Java entity. Therefore, it's arguable whether we need this interface; but
 * it makes the inheritance hierarchies a bit more consistent....
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface VirtualPrimaryKeyJoinColumn
	extends VirtualBaseJoinColumn, ReadOnlyPrimaryKeyJoinColumn
{
	/**
	 * This is not the best method name; we are simply overloading the meaning
	 * of the inherited method.
	 */
	PrimaryKeyJoinColumn getOverriddenColumn();
}
