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
 */
public abstract class AbstractExpressionConverter implements ExpressionConverter {

	protected AbstractExpressionConverter() {
		super();
	}

	public Expression convert(Object o, AST ast) {
		return (o == null) ? null : this.convert_(o, ast);
	}

	/**
	 * The specified object is not null.
	 * @see #convert(Object, AST)
	 */
	protected abstract Expression convert_(Object o, AST ast);

	public Object convert(Expression expression) {
		return (expression == null) ? null : this.convert_(expression);
	}

	/**
	 * The specified expression is not null.
	 * @see #convert(Expression)
	 */
	protected abstract Object convert_(Expression expression);

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
