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

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link Command} to the {@link Transformer} interface.
 * The transformer will always return <code>null</code>.
 * 
 * @param <I> input: the type of the object passed to the transformer;
 *     ignored
 * @param <O> output: the type of the object returned by the transformer;
 *     always <code>null</code>
 * 
 * @see org.eclipse.jpt.common.utility.internal.command.TransformerCommand
 */
public class CommandTransformer<I, O>
	implements Transformer<I, O>
{
	private final Command command;

	public CommandTransformer(Command command) {
		super();
		if (command == null) {
			throw new NullPointerException();
		}
		this.command = command;
	}

	public O transform(I input) {
		this.command.execute();
		return null;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.command);
	}
}
