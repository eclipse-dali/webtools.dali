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
public class NullableBooleanClosure
	implements Closure<Boolean>
{
	private final BooleanClosure.Adapter adapter;
	private final boolean nullArgumentValue;


	public NullableBooleanClosure(BooleanClosure.Adapter adapter, boolean nullArgumentValue) {
		super();
		if (adapter == null) {
			throw new NullPointerException();
		}
		this.adapter = adapter;
		this.nullArgumentValue = nullArgumentValue;
	}

	public void execute(Boolean argument) {
		this.adapter.execute((argument != null) ? argument.booleanValue() : this.nullArgumentValue);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.adapter);
	}
}
