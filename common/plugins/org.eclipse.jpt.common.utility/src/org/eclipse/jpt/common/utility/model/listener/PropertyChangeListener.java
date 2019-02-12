/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model.listener;

import java.util.EventListener;

import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;

/**
 * A "property change" event gets fired whenever a model changes a "bound"
 * property. You can register a <code>PropertyChangeListener</code> with a source
 * model so as to be notified of any bound property updates.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface PropertyChangeListener extends EventListener {

	/**
	 * This method gets called when a model has changed a bound property.
	 * 
	 * @param event An event describing the event source
	 * and the property's old and new values.
	 */
	void propertyChanged(PropertyChangeEvent event);

}
