/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a type literal to/from a string representation of a primitive type
 * (e.g. "int").
 */
public final class PrimitiveTypeStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new PrimitiveTypeStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PrimitiveTypeStringExpressionConverter() {
		super();
	}

	@Override
	protected TypeLiteral convertObject(String string, AST ast) {
		org.eclipse.jdt.core.dom.Type type = ast.newPrimitiveType(PrimitiveType.toCode(string));
		TypeLiteral typeLiteral = ast.newTypeLiteral();
		typeLiteral.setType(type);
		return typeLiteral;
	}

	@Override
	protected String convertExpression(Expression expression) {
		if (expression.getNodeType() == ASTNode.TYPE_LITERAL) {
			org.eclipse.jdt.core.dom.Type type = ((TypeLiteral) expression).getType();
			if (type.getNodeType() == ASTNode.PRIMITIVE_TYPE) {
				return ((PrimitiveType) type).getPrimitiveTypeCode().toString();
			}
		}
		return null;
	}

}
