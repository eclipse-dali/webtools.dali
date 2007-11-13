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

import java.util.EventObject;

import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.Model;

/**
 * Abstract class for all the change events that can be fired by models.
 */
public abstract class ChangeEvent extends EventObject {

	private static final long serialVersionUID = 1L;


	/**
	 * Construct a new change event.
	 *
	 * @param source The object on which the event initially occurred.
	 */
	protected ChangeEvent(Model source) {
		super(source);
	}

	@Override
	public Model getSource() {
		return (Model) super.getSource();
	}

	/**
	 * Return the name of the aspect of the source that changed.
	 * May be null if inappropriate.
	 */
	public abstract String aspectName();

	/**
	 * Return a copy of the event with the specified source
	 * replacing the current source.
	 */
	public abstract ChangeEvent cloneWithSource(Model newSource);

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.aspectName());
	}

}
