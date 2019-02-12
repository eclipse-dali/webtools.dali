/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate.int_;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.IntPredicate;

/**
 * Convenience predicate implementation that evaluates any <code>int</code> to
 * <code>false</code> and provides a helpful {@link #toString()}.
 */
public class IntPredicateAdapter
	implements IntPredicate
{
	public boolean evaluate(int variable) {
		return false;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
