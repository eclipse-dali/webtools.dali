/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
 * Straightforward implementation of the
 * {@link org.eclipse.jpt.common.utility.node.Node.Reference}
 * interface.
 */
public class SimpleNodeReference
	implements Node.Reference
{
	private Node source;
	private Node target;

	public SimpleNodeReference(Node source, Node target) {
		super();
		if (source == null || target == null) {
			throw new NullPointerException();
		}
		this.source = source;
		this.target = target;
	}

	public Node source() {
		return this.source;
	}

	public Node target() {
		return this.target;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.source + " => " + this.target); //$NON-NLS-1$
	}
}
