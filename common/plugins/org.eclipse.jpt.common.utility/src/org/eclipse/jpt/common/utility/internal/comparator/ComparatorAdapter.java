/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.comparator;

import java.util.Comparator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Convenience comparator that always returns 0;
 * 
 * @param <T> the type of objects to be compared
 */
public class ComparatorAdapter<T>
	implements Comparator<T>
{
	public int compare(T o1, T o2) {
		return 0;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
