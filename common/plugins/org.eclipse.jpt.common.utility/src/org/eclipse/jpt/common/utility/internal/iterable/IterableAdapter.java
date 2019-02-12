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

import java.util.Iterator;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;

/**
 * Convenience iterable that returns an empty iterator.
 */
public class IterableAdapter<E>
	implements Iterable<E>
{
	public Iterator<E> iterator() {
		return IteratorTools.emptyIterator();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
