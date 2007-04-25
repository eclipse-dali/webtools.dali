/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;

/**
 * Convert an expression to/from a string (e.g. "text").
 */
public final class StringExpressionConverter extends AbstractExpressionConverter {
	private static ExpressionConverter INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter instance() {
		if (INSTANCE == null) {
			INSTANCE = new StringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private StringExpressionConverter() {
		super();
	}

	@Override
	protected Expression convert_(Object o, AST ast) {
		StringLiteral stringLiteral = ast.newStringLiteral();
		stringLiteral.setLiteralValue((String) o);
		return stringLiteral;
	}

	@Override
	protected Object convert_(Expression expression) {
		return (expression.getNodeType() == ASTNode.STRING_LITERAL) ?
			((StringLiteral) expression).getLiteralValue()
		:
			null;
	}

}
