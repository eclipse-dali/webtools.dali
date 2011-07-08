/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * <em>Specified</em><ul>
 * <li>attribute override
 * <li>association override
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
// the class name "Override" is sorta taken: java.lang.Override
// maybe if it weren't in the java.lang package we would take it on, but... :)
// very little code will directly refer to this interface, so the underscore
// isn't *too* evil...
public interface Override_
	extends ReadOnlyOverride
{
	void setName(String value);

	/**
	 * Convert the <em>specified</em> override to a <em>virtual</em> override.
	 * Return the new <em>virtual</em> override.
	 * @see #isVirtual()
	 */
	VirtualOverride convertToVirtual();
}
