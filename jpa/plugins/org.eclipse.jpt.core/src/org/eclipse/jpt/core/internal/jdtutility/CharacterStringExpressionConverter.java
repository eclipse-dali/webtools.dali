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
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.Expression;

/**
 * Convert a character literal to/from a string representation of a character
 * (e.g. "A").
 */
public final class CharacterStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new CharacterStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private CharacterStringExpressionConverter() {
		super();
	}
	
	@Override
	protected CharacterLiteral convertObject(String string, AST ast) {
		CharacterLiteral characterLiteral = ast.newCharacterLiteral();
		characterLiteral.setCharValue(string.charAt(0));
		return characterLiteral;
	}

	@Override
	protected String convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		return (value instanceof Character) ? ((Character) value).toString() : null;
	}

}
