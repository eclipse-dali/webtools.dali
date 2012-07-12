/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.resource.java;

import java.util.HashMap;
import org.eclipse.jpt.common.utility.internal.SimpleIntReference;

public class CounterMap {
	
	private final HashMap<Object, SimpleIntReference> counters;
	
	
	public CounterMap(int initialCapacity) {
		super();
		this.counters = new HashMap<Object, SimpleIntReference>(initialCapacity);
	}
	
	
	/**
	 * Return the incremented count for the specified object.
	 */
	public int increment(Object o) {
		SimpleIntReference counter = this.counters.get(o);
		if (counter == null) {
			counter = new SimpleIntReference();
			this.counters.put(o, counter);
		}
		counter.increment();
		return counter.getValue();
	}
}