/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Interface extended or implemented by any type that represents either
 * <em>specified</em> or <em>virtual</em> objects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface SpecifiedOrVirtual {

	/**
	 * Return whether the object is <em>specified</em>
	 * (as opposed to <em>virtual</em>),
	 * with a context-specific meaning.
	 * @see #isVirtual()
	 */
	default boolean isSpecified() {
		return ! this.isVirtual();
	}
		Predicate<SpecifiedOrVirtual> SPECIFIED_PREDICATE = new SpecifiedPredicate();
	class SpecifiedPredicate
		extends PredicateAdapter<SpecifiedOrVirtual>
	{
		@Override
		public boolean evaluate(SpecifiedOrVirtual object) {
			return object.isSpecified();
		}
	}

	/**
	 * Return whether the object is <em>virtual</em>
	 * (as opposed to <em>specified</em>),
	 * with a context-specific meaning.
	 * @see #isSpecified()
	 */
	boolean isVirtual();
		Predicate<SpecifiedOrVirtual> VIRTUAL_PREDICATE = PredicateTools.not(SPECIFIED_PREDICATE);
}
