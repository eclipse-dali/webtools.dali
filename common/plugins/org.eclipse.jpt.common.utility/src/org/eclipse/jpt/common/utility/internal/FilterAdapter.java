/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal;

import org.eclipse.jpt.common.utility.Filter;

/**
 * Convenience filter implementation that accepts everything.
 * 
 * @see org.eclipse.jpt.common.utility.Filter.Transparent
 */
public class FilterAdapter<T>
	implements Filter<T>
{
	public boolean accept(T o) {
		return true;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
