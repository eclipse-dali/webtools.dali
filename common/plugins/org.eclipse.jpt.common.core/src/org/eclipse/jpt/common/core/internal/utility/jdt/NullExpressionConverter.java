/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

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
		return ObjectTools.toString(this);
	}

}
