/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.node;

import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This implementation of the PluggableValidator.Delegate interface
 * will validate the node immediately.
 * 
 * This is useful for debugging in a single thread or generating
 * problem reports.
 */
public class SynchronousValidator
	implements PluggableValidator.Delegate
{
	private Node node;

	/**
	 * Construct a validator that will immediately validate the
	 * specified node.
	 */
	public SynchronousValidator(Node node) {
		super();
		this.node = node;
	}

	public void validate() {
		this.node.validateBranch();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.node);
	}

}
