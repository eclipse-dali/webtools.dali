/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility.jdt;

/**
 * Attributes are either represented by fields ('foo') or properties/method
 * pairs ('getFoo()'/'setFoo()').
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * This interface is not intended to be implemented by clients.
 */
public interface Attribute extends Member {

	/**
	 * Return the attribute's name, as opposed to the member's name
	 * (e.g. "getFoo()" returns "foo").
	 */
	String getAttributeName();

	/**
	 * Return whether the attribute is a field.
	 */
	boolean isField();

}
