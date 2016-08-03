/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.model;

/**
 * Simple interface for implementing a boolean "set" command that takes two
 * arguments, the model and the new value of the model's boolean attribute.
 * The expectation is the closure will have side effects.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @param <M> the type of the first object passed to the command
 */
public interface BooleanSetClosure<M> {

	/**
	 * The intent of this method is to set the value of the specified
	 * model's attribute to the specified boolean value.
	 */
	void execute(M model, boolean value);
}
