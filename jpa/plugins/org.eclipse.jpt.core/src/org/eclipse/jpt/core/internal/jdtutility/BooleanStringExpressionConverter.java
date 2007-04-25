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
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Convert an expression to/from a string representation of a boolean
 * (e.g. "true").
 */
public final class BooleanStringExpressionConverter extends AbstractExpressionConverter {
	private static ExpressionConverter INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter instance() {
		if (INSTANCE == null) {
			INSTANCE = new BooleanStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private BooleanStringExpressionConverter() {
		super();
	}

	@Override
	protected Expression convert_(Object o, AST ast) {
		return ast.newBooleanLiteral(Boolean.valueOf((String) o).booleanValue());
	}

	@Override
	protected Object convert_(Expression expression) {
		return (expression.getNodeType() == ASTNode.BOOLEAN_LITERAL) ?
			Boolean.toString(((BooleanLiteral) expression).booleanValue())
		:
			null;
	}

}
