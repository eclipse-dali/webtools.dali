/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
 * @param <E> the type of objects to be compared
 */
public class ComparatorAdapter<E>
	implements Comparator<E>
{
	public int compare(E o1, E o2) {
		return 0;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
