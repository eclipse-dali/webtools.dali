/*******************************************************************************
 * Copyright (c) 2005, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.predicate;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Convenience predicate implementation that evaluates any object to
 * <code>false</code> and provides a helpful {@link #toString()}.
 * 
 * @param <V> the type of objects to be evaluated by the predicate
 */
public class PredicateAdapter<V>
	implements Predicate<V>
{
	public boolean evaluate(V variable) {
		return false;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
