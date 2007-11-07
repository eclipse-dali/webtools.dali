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
import org.eclipse.jdt.core.dom.NumberLiteral;

/**
 * Convert a number literal to/from a string representation of a number
 * (e.g. "48").
 */
public final class NumberStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new NumberStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NumberStringExpressionConverter() {
		super();
	}
	
	@Override
	protected NumberLiteral convertObject(String string, AST ast) {
		return ast.newNumberLiteral(string);
	}

	@Override
	protected String convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		return (value instanceof Number) ? ((Number) value).toString() : null;
	}

}
