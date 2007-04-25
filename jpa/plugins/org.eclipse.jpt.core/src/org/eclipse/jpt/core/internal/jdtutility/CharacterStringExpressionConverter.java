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
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Convert an expression to/from a string representation of a character
 * (e.g. "A").
 */
public final class CharacterStringExpressionConverter extends AbstractExpressionConverter {
	private static ExpressionConverter INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter instance() {
		if (INSTANCE == null) {
			INSTANCE = new CharacterStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private CharacterStringExpressionConverter() {
		super();
	}
	
	@Override
	protected Expression convert_(Object o, AST ast) {
		CharacterLiteral characterLiteral = ast.newCharacterLiteral();
		characterLiteral.setCharValue(((String) o).charAt(0));
		return characterLiteral;
	}

	@Override
	protected Object convert_(Expression expression) {
		return (expression.getNodeType() == ASTNode.CHARACTER_LITERAL) ?
			Character.toString(((CharacterLiteral) expression).charValue())
		:
			null;
	}

}
