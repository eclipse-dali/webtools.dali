/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.model;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.BooleanSetClosure;

/**
 * Convenience boolean "set" closure that does nothing.
 * 
 * @param <M> the type of the model (i.e. first object) passed to the closure
 */
public class BooleanSetClosureAdapter<M>
	implements BooleanSetClosure<M>
{
	public void execute(M model, boolean value) {
		// NOP
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
