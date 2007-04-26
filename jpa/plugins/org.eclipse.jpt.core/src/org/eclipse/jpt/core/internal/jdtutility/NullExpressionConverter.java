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
 * No conversion.
 */
public final class NullExpressionConverter implements ExpressionConverter {
	private static ExpressionConverter INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter instance() {
		if (INSTANCE == null) {
			INSTANCE = new NullExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullExpressionConverter() {
		super();
	}

	public Expression convert(Object o, AST ast) {
		return (Expression) o;
	}

	public Object convert(Expression expression) {
		return expression;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
