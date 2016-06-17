/*******************************************************************************
 * Copyright (c) 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.closure;

import org.eclipse.jpt.common.utility.closure.Closure;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Adapt a <code>boolean</code> "closure" to the {@link Closure} interface.
 * The closure's argument can never be <code>null</code>.
 */
public class BooleanClosure
	implements Closure<Boolean>
{
	private final Adapter adapter;


	public BooleanClosure(Adapter adapter) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.adapter = adapter;
	}

	public void execute(Boolean argument) {
		// a null argument will result in a NPE
		this.adapter.execute(argument.booleanValue());
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.adapter);
	}

	public interface Adapter {
		void execute(boolean argument);
	}
}
