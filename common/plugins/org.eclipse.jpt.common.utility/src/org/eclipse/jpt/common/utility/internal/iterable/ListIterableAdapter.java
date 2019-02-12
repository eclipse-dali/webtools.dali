/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.iterable;

import java.util.ListIterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;

/**
 * Convenience list iterable that returns an empty iterator.
 */
public class ListIterableAdapter<E>
	implements ListIterable<E>
{
	public ListIterator<E> iterator() {
		return IteratorTools.emptyListIterator();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
