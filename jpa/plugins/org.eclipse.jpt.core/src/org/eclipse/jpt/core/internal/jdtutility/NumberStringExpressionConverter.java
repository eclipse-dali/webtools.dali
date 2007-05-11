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
import org.eclipse.jdt.core.dom.NumberLiteral;

/**
 * Convert a number literal to/from a string representation of a number
 * (e.g. "48").
 */
public final class NumberStringExpressionConverter
	extends AbstractExpressionConverter<String, NumberLiteral>
{
	private static ExpressionConverter<String, NumberLiteral> INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String, NumberLiteral> instance() {
		if (INSTANCE == null) {
			INSTANCE = new NumberStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NumberStringExpressionConverter() {
		super();
	}
	
	@Override
	protected NumberLiteral convert_(String string, AST ast) {
		return ast.newNumberLiteral(string);
	}

	@Override
	protected String convert_(NumberLiteral numberLiteral) {
		return (numberLiteral.getNodeType() == ASTNode.NUMBER_LITERAL) ?
			numberLiteral.getToken()
		:
			null;
	}

}
