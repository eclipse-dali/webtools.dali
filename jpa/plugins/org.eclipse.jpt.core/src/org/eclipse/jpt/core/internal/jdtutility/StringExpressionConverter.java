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
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.StringLiteral;

/**
 * Convert a string literal to/from a string (e.g. "text").
 */
public final class StringExpressionConverter
	extends AbstractExpressionConverter<String, StringLiteral>
{
	private static ExpressionConverter<String, StringLiteral> INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String, StringLiteral> instance() {
		if (INSTANCE == null) {
			INSTANCE = new StringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private StringExpressionConverter() {
		super();
	}

	@Override
	protected StringLiteral convert_(String string, AST ast) {
		StringLiteral stringLiteral = ast.newStringLiteral();
		stringLiteral.setLiteralValue(string);
		return stringLiteral;
	}

	@Override
	protected String convert_(StringLiteral stringLiteral) {
		return (stringLiteral.getNodeType() == ASTNode.STRING_LITERAL) ?
			stringLiteral.getLiteralValue()
		:
			null;
	}

}
