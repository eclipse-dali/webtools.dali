/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Gather together just the tiniest bit of common behavior.
 * E is the expression type.
 * T is the type of the object to be converted to and from an expression.
 */
public abstract class AbstractExpressionConverter<T, E extends Expression>
	implements ExpressionConverter<T, E>
{

	protected AbstractExpressionConverter() {
		super();
	}

	public E convert(T object, AST ast) {
		return (object == null) ? null : this.convert_(object, ast);
	}

	/**
	 * The specified object is not null.
	 * @see #convert(T, AST)
	 */
	protected abstract E convert_(T object, AST ast);

	public T convert(E expression) {
		return (expression == null) ? null : this.convert_(expression);
	}

	/**
	 * The specified expression is not null.
	 * @see #convert(Expression)
	 */
	protected abstract T convert_(E expression);

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
