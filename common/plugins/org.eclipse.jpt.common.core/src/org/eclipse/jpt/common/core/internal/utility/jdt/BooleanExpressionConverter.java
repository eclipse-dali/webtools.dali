/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a boolean literal to/from a Boolean
 * (e.g. Boolean.TRUE).
 */
public final class BooleanExpressionConverter
	extends AbstractExpressionConverter<Boolean>
{
	private static final ExpressionConverter<Boolean> INSTANCE = new BooleanExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<Boolean> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private BooleanExpressionConverter() {
		super();
	}

	@Override
	protected BooleanLiteral convertObject(Boolean booleanObject, AST ast) {
		return ast.newBooleanLiteral(booleanObject.booleanValue());
	}

	@Override
	protected Boolean convertExpression(Expression expression) {
		Object value = expression.resolveConstantExpressionValue();
		return (value instanceof Boolean) ? ((Boolean) value) : null;
	}

}
