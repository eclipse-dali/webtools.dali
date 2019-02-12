/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;

/**
 * Convenience implementation of {@link PropertyChangeListener}.
 * This is probably of limited use, since there only a single method to implement;
 * maybe it is useful as a null implementation. One minor benefit is the
 * implementation of {@link #toString()}.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class PropertyChangeAdapter
	implements PropertyChangeListener
{
	public PropertyChangeAdapter() {
		super();
	}

	public void propertyChanged(PropertyChangeEvent event) {
		// do nothing
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
