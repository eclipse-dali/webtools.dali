/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import org.eclipse.jpt.common.utility.internal.BitTools;

/**
 * This predicate evaluates to <code>true</code> if the variable
 * has cleared <em>any</em> of the flags specified during construction.
 */
public class AnyFlagsAreOff
	extends IntCriterionIntPredicate
{
	/**
	 * Construct a predicate that will evaluate to <code>true</code> if the
	 * variable has cleared <em>any</em> of the specified flags.
	 */
	public AnyFlagsAreOff(int flags) {
		super(flags);
	}

	public boolean evaluate(int variable) {
		return BitTools.anyFlagsAreOff(variable, this.criterion);
	}
}
