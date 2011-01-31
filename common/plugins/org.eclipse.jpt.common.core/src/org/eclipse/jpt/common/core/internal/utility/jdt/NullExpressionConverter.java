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
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * No conversion.
 */
public final class NullExpressionConverter
	implements ExpressionConverter<Expression>
{

	// singleton
	private static final ExpressionConverter<Expression> INSTANCE = new NullExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<Expression> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NullExpressionConverter() {
		super();
	}

	public Expression convert(Expression expression, AST ast) {
		return expression;
	}

	public Expression convert(Expression expression) {
		return expression;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
