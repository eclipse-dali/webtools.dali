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
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Convert a boolean literal to/from a string representation of a boolean
 * (e.g. "true").
 */
public final class BooleanStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new BooleanStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private BooleanStringExpressionConverter() {
		super();
	}

	@Override
	protected BooleanLiteral convertObject(String string, AST ast) {
		return ast.newBooleanLiteral(Boolean.parseBoolean(string));
	}

	@Override
	protected String convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		return (value instanceof Boolean) ? ((Boolean) value).toString() : null;
	}

}
