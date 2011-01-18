/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Named context node that may have collisions with or override other nodes
 * with the same name defined elsewhere in the persistence unit
 * (e.g. sequence generators).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaNamedContextNode<T>
	extends JpaContextNode
{
	// ********** name **********

	String getName();
		String NAME_PROPERTY = "name"; //$NON-NLS-1$
	void setName(String name);


	// ********** validation **********

	/**
	 * Return whether the node "overrides" the specified node
	 * within the nodes' persistence unit.
	 * (e.g. a query defined in <code>orm.xml</code>
	 * "overrides" one defined in a Java annotation).
	 * 
	 * @see #duplicates(Object)
	 */
	boolean overrides(T other);

	/**
	 * Return whether the node is a "duplicate" of the specified node.
	 * Two nodes are duplicates if they both have the same,
	 * non-null name and neither node "overrides" the other.
	 * 
	 * @see #overrides(Object)
	 */
	boolean duplicates(T other);
}
