/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a type literal to/from a string representation of a 
 * simple type (e.g. "java.lang.Object") or primitive type (e.g. "int").
 */
public final class TypeStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new TypeStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private TypeStringExpressionConverter() {
		super();
	}

	@Override
	protected TypeLiteral convertObject(String string, AST ast) {
		if (PrimitiveType.toCode(string) != null) {
			return (TypeLiteral) PrimitiveTypeStringExpressionConverter.instance().convert(string, ast);
		}
		return (TypeLiteral) SimpleTypeStringExpressionConverter.instance().convert(string, ast);
	}

	@Override
	protected String convertExpression(Expression expression) {
		String name = SimpleTypeStringExpressionConverter.instance().convert(expression);
		return name != null ? name : PrimitiveTypeStringExpressionConverter.instance().convert(expression);
	}

}
