/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a number literal to/from an Integer
 * (e.g. 5).
 */
public final class NumberIntegerExpressionConverter
	extends AbstractExpressionConverter<Integer>
{
	private static final ExpressionConverter<Integer> INSTANCE = new NumberIntegerExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<Integer> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NumberIntegerExpressionConverter() {
		super();
	}
	
	@Override
	protected NumberLiteral convertObject(Integer integer, AST ast) {
		return ast.newNumberLiteral(integer.toString());
	}

	@Override
	protected Integer convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		return (value instanceof Integer) ? ((Integer) value) : null;
	}

}
