/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaOverrideContainer;

/**
 * JPA 2.0
 * Java attribute or association override container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaOverrideContainer2_0
	extends JavaOverrideContainer
{
	/**
	 * Return a prefix (<em>without</em> the following <code>'.'</code>)
	 * that may be prepended to the override name.
	 * Return <code>null</code> if no prefix is supported.
	 * <p>
	 * JPA 2.0 supports the pre-defined prefixes
	 * <code>"value"</code> and <code>"key"</code>.
	 */
	// TODO better name?
	String getPossiblePrefix();

	/**
	 * Return the prefix (<em>without</em> the following <code>'.'</code>)
	 * to be prepended to the override name.
	 */
	// TODO better name?
	String getWritePrefix();


	// ********** owner **********

	interface Owner
		extends JavaOverrideContainer.Owner
	{
		/**
		 * @see JavaOverrideContainer2_0#getPossiblePrefix()
		 */
		String getPossiblePrefix();

		/**
		 * @see JavaOverrideContainer2_0#getWritePrefix()
		 */
		String getWritePrefix();

		/**
		 * This is necessary for JPA 2.0 because an override annotation for an
		 * element collection can have a name with a prefix that indicates
		 * whether the override applies to element collection's embedded key or
		 * value. Return whether the specified override name, which may have a
		 * prefix, is relevant to the override container.
		 */
		// TODO better name?
		boolean isRelevant(String overrideName);
	}
}
