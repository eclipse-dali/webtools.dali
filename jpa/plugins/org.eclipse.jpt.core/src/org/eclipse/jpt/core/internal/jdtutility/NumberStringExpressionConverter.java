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
import org.eclipse.jdt.core.dom.NumberLiteral;

/**
 * Convert an expression to/from a string representation of a number
 * (e.g. "48").
 */
public final class NumberStringExpressionConverter extends AbstractExpressionConverter {
	private static ExpressionConverter INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter instance() {
		if (INSTANCE == null) {
			INSTANCE = new NumberStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NumberStringExpressionConverter() {
		super();
	}
	
	@Override
	protected Expression convert_(Object o, AST ast) {
		return ast.newNumberLiteral((String) o);
	}

	@Override
	protected Object convert_(Expression expression) {
		return (expression.getNodeType() == ASTNode.NUMBER_LITERAL) ?
			((NumberLiteral) expression).getToken()
		:
			null;
	}

}
