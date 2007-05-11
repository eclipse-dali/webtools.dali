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
import org.eclipse.jdt.core.dom.BooleanLiteral;

/**
 * Convert a boolean literal to/from a string representation of a boolean
 * (e.g. "true").
 */
public final class BooleanStringExpressionConverter
	extends AbstractExpressionConverter<String, BooleanLiteral>
{
	private static ExpressionConverter<String, BooleanLiteral> INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String, BooleanLiteral> instance() {
		if (INSTANCE == null) {
			INSTANCE = new BooleanStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private BooleanStringExpressionConverter() {
		super();
	}

	@Override
	protected BooleanLiteral convert_(String string, AST ast) {
		return ast.newBooleanLiteral(Boolean.valueOf(string).booleanValue());
	}

	@Override
	protected String convert_(BooleanLiteral booleanLiteral) {
		return (booleanLiteral.getNodeType() == ASTNode.BOOLEAN_LITERAL) ?
			Boolean.toString(booleanLiteral.booleanValue())
		:
			null;
	}

}
