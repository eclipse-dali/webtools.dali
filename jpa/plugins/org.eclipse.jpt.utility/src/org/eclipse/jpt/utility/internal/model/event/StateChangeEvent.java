/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.event;

import org.eclipse.jpt.utility.internal.model.Model;

/**
 * A generic "state change" event gets delivered whenever a model changes to 
 * such extent that it cannot be delineated all aspects of it that have changed. 
 * A StateChangeEvent is sent as an argument to the StateChangeListener.
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
