/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.transformer;

import org.eclipse.jpt.common.utility.command.InterruptibleCommand;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.InterruptibleTransformer;

/**
 * @see CommandTransformer
 */
public class InterruptibleCommandTransformer<I, O>
	implements InterruptibleTransformer<I, O>
{
	private final InterruptibleCommand command;

	public InterruptibleCommandTransformer(InterruptibleCommand command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public O transform(I input) throws InterruptedException {
		this.command.execute();
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
