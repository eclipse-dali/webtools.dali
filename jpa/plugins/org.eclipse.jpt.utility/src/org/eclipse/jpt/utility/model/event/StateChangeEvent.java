/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import org.eclipse.jpt.utility.model.Model;

/**
 * A generic "state change" event gets delivered whenever a model changes to 
 * such extent that it cannot be delineated all aspects of it that have changed. 
 * A StateChangeEvent is sent as an argument to the StateChangeListener.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public class StateChangeEvent extends ChangeEvent {

	private static final long serialVersionUID = 1L;


	// ********** constructors **********

	/**
	 * Construct a new state change event.
	 *
	 * @param source The object on which the event initially occurred.
	 */
	public StateChangeEvent(Model source) {
		super(source);
	}


	// ********** standard state **********

	@Override
	public String aspectName() {
		return null;  // the point of the event is that the name is unknown...
	}


	// ********** cloning **********

	@Override
	public StateChangeEvent cloneWithSource(Model newSource) {
		return new StateChangeEvent(newSource);
	}

}
