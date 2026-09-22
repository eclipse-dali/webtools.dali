/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TextBlock;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a string literal to/from a string (e.g. "text").
 */
public final class StringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new StringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private StringExpressionConverter() {
		super();
	}

	@Override
	protected StringLiteral convertObject(String string, AST ast) {
		StringLiteral stringLiteral = ast.newStringLiteral();
		stringLiteral.setLiteralValue(string);
		return stringLiteral;
	}

	@Override
	protected String convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		if (value instanceof String) {
			return (String) value;
		}
		if (expression.getNodeType() == ASTNode.TEXT_BLOCK) {
			return ((TextBlock) expression).getLiteralValue();
		}
		if (expression.getNodeType() == ASTNode.STRING_LITERAL) {
			return ((StringLiteral) expression).getLiteralValue();
		}
		return null;
	}

}
