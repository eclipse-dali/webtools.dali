/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.model.listener;

import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;

/**
 * A "property change" event gets fired whenever a model changes a "bound"
 * property. You can register a PropertyChangeListener with a source
 * model so as to be notified of any bound property updates.
 */
public interface PropertyChangeListener extends ChangeListener {

	/**
	 * This method gets called when a model has changed a bound property.
	 * 
	 * @param event A PropertyChangeEvent describing the event source
	 * and the property's old and new values.
	 */
	void propertyChanged(PropertyChangeEvent event);

}
