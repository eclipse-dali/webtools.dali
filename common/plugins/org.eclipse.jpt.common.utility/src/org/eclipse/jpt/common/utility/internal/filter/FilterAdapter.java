/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.filter;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.predicate.Predicate;

/**
 * Convenience filter that "accepts" every object.
 * 
 * @param <T> the type of objects to be filtered
 */
public class FilterAdapter<T>
	implements Predicate<T>
{
	public boolean evaluate(T o) {
		return true;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
