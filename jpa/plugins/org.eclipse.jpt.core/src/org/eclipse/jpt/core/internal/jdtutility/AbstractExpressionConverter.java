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
 * T1 is the expression type, while T2 is the type of object the expression
 * is convert to/from.
 */
public abstract class AbstractExpressionConverter<T1 extends Expression, T2>
	implements ExpressionConverter<T1, T2>
{

	protected AbstractExpressionConverter() {
		super();
	}

	public T1 convert(T2 object, AST ast) {
		return (object == null) ? null : this.convert_(object, ast);
	}

	/**
	 * The specified object is not null.
	 * @see #convert(T, AST)
	 */
	protected abstract T1 convert_(T2 object, AST ast);

	public T2 convert(T1 expression) {
		return (expression == null) ? null : this.convert_(expression);
	}

	/**
	 * The specified expression is not null.
	 * @see #convert(Expression)
	 */
	protected abstract T2 convert_(T1 expression);

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
