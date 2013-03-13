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

import java.util.Arrays;

import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.node.Node;

/**
 * This class is a straightforward implementation of 
 * {@link org.eclipse.jpt.common.utility.node.Node.Problem}.
 */
public class DefaultNodeProblem
	implements Node.Problem
{
	private final Node source;
	private final String messageKey;
	private final int messageType;
	private final Object[] messageArguments;


	DefaultNodeProblem(Node source, String messageKey, int messageType, Object[] messageArguments) {
		super();
		if ((source == null) || (messageKey == null) || (messageArguments == null)) {
			throw new NullPointerException();
		}
		this.source = source;
		this.messageKey = messageKey;
		this.messageType = messageType;
		this.messageArguments = messageArguments;
	}


	// ********** Problem implementation **********

	public Node source() {
		return this.source;
	}

	public String messageKey() {
		return this.messageKey;
	}

	public int messageType() {
		return this.messageType;
	}

	public Object[] messageArguments() {
		return this.messageArguments;
	}


	// ********** Object overrides **********

	/**
	 * We implement #equals(Object) because problems are repeatedly
	 * re-calculated and the resulting problems merged with the existing
	 * set of problems; and we want to keep the original problems and
	 * ignore any freshly-generated duplicates.
	 * Also, problems are not saved to disk....
	 */
	@Override
	public boolean equals(Object o) {
		if ( ! (o instanceof Node.Problem)) {
			return false;
		}
		Node.Problem other = (Node.Problem) o;
		return this.source == other.source()
				&& this.messageKey.equals(other.messageKey())
				&& Arrays.equals(this.messageArguments, other.messageArguments());
	}

	@Override
	public int hashCode() {
		return this.source.hashCode() ^ this.messageKey.hashCode();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.messageKey);
	}

}
