/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.node;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.node.Node;

/**
 * This implementation of the {@link PluggableValidator.Delegate} interface
 * will validate the node immediately.
 * <p>
 * This is useful for debugging in a single thread or generating
 * problem reports.
 */
public class SynchronousValidator
	implements PluggableValidator.Delegate
{
	private final Node node;

	/**
	 * Construct a validator that will immediately validate the
	 * specified node.
	 */
	public SynchronousValidator(Node node) {
		super();
		if (node == null) {
			throw new NullPointerException();
		}
		this.node = node;
	}

	public void validate() {
		this.node.validateBranch();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.node);
	}

}
