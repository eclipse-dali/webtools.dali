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
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;

/**
 * Convert a name to/from a string representation of a name/identifier
 * (e.g. "com.xxx.Foo.VALUE1" or "value").
 */
public final class NameStringExpressionConverter
	extends AbstractExpressionConverter<String>
{
	private static final ExpressionConverter<String> INSTANCE = new NameStringExpressionConverter();

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<String> instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NameStringExpressionConverter() {
		super();
	}

	@Override
	protected Name convertObject(String string, AST ast) {
		return ast.newName(string);
	}

	@Override
	protected String convertExpression(Expression expression) {
		switch (expression.getNodeType()) {
			case ASTNode.QUALIFIED_NAME:
			case ASTNode.SIMPLE_NAME:
				return ((Name) expression).getFullyQualifiedName();
			default:
				return null;
		}
	}

}
