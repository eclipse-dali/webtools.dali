/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * Gather together the common implementation behavior.
 * 
 * We're still figuring out Java Generics here.... The methods in this abstract
 * class work fine with any subclass of Expression E; but a ClassCastException
 * will occur as soon as we call any method implemented by a subclass
 * (e.g. StringExpressionConverter) that expects a particular subclass of
 * Expression (e.g. StringLiteral).
 * 
 * @param <T> the type of the object to be converted to and from an expression
 */
public abstract class AbstractExpressionConverter<T>
	implements ExpressionConverter<T>
{
	protected AbstractExpressionConverter() {
		super();
	}


	// ********** object -> expression **********

	public Expression convert(T object, AST ast) {
		return (object == null) ? this.convertNull(ast) : this.convertObject(object, ast);
	}

	/**
	 * Return the expression for a <code>null</code> object.
	 * By default, a <code>null</code> object will
	 * be converted into a <code>null</code> expression.
	 */
	protected Expression convertNull(@SuppressWarnings("unused") AST ast) {
		return null;
	}

	/**
	 * The specified object is not <code>null</code>.
	 * @see #convert(Object, AST)
	 */
	protected abstract Expression convertObject(T object, AST ast);


	// ********** expression -> object **********

	public T convert(Expression expression) {
		return (expression == null) ? this.convertNull() : this.convertExpression(expression);
	}

	/**
	 * Return the object for a <code>null</code> expression.
	 * By default, a <code>null</code> expression will
	 * be converted into a <code>null</code> object.
	 */
	protected T convertNull() {
		return null;
	}

	/**
	 * The specified expression is not <code>null</code>.
	 * @see #convert(Expression)
	 */
	protected abstract T convertExpression(Expression expression);

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
