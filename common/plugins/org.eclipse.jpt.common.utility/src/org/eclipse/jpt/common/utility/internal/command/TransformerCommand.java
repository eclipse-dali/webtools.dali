/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.command;

import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Adapt a {@link Transformer} to the {@link Command} interface.
 * The transformer is passed <code>null</code> for input and its output is
 * ignored. This really only useful for a transformer that accepts
 * <code>null</code> input and has side-effects.
 * 
 * @see org.eclipse.jpt.common.utility.internal.transformer.TransformerTools#adapt(Command)
 */
public class TransformerCommand
	implements Command
{
	private final Transformer<?, ?> transformer;


	public TransformerCommand(Transformer<?, ?> transformer) {
		super();
		if (transformer == null) {
			throw new NullPointerException();
		}
		this.transformer = transformer;
	}

	public void execute() {
		this.transformer.transform(null);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.transformer);
	}
}
