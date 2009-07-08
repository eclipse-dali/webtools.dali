/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.listener;

import java.util.EventListener;

import org.eclipse.jpt.utility.model.event.StateChangeEvent;

/**
 * A generic "state change" event gets delivered whenever a model changes to 
 * such extent that it cannot be delineated all aspects of it that have changed. 
 * You can register a StateChangeListener with a source model so as to be notified 
 * of any such changes.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface StateChangeListener extends EventListener {

	/**
	 * This method gets called when a model has changed in some general fashion.
	 * 
	 * @param event A StateChangeEvent describing the event source.
	 */
	void stateChanged(StateChangeEvent event);

}
