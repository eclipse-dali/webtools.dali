/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.model.event;

import java.util.EventObject;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.Model;

/**
 * Abstract class for all the change events that can be fired by models.
 * <p>
 * Provisional API: This class is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
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

	/**
	 * Covariant override.
	 */
	@Override
	public Model getSource() {
		return (Model) super.getSource();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		StringTools.buildSimpleToStringOn(this, sb);
		sb.append('(');
		int len = sb.length();
		this.toString(sb);
		if (sb.length() == len) {
			sb.deleteCharAt(len - 1);
		} else {
			sb.append(')');
		}
		return sb.toString();
	}

	protected void toString(@SuppressWarnings("unused") StringBuilder sb) {
		// subclasses should override this to do something a bit more helpful
	}

}
