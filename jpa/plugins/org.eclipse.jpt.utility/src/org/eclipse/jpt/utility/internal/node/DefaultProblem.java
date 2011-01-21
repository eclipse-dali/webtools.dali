/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.node;

import java.util.Arrays;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * This class is a straightforward implementation of the Problem interface.
 */
public class DefaultProblem
	implements Problem
{
	private final Node source;
	private final String messageKey;
	private final int messageType;
	private final Object[] messageArguments;


	DefaultProblem(Node source, String messageKey, int messageType, Object[] messageArguments) {
		super();
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
		if ( ! (o instanceof Problem)) {
			return false;
		}
		Problem other = (Problem) o;
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
		return StringTools.buildToStringFor(this, this.messageKey);
	}

}
