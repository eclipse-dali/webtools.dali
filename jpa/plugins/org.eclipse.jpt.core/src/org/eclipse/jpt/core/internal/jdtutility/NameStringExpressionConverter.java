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
import org.eclipse.jdt.core.dom.Name;

/**
 * Convert a name to/from a string representation of a name/identifier
 * (e.g. "com.xxx.Foo.VALUE1" or "value").
 */
public final class NameStringExpressionConverter
	extends AbstractExpressionConverter<Name, String>
{
	private static ExpressionConverter<Name, String> INSTANCE;

	/**
	 * Return the singleton.
	 */
	public static ExpressionConverter<Name, String> instance() {
		if (INSTANCE == null) {
			INSTANCE = new NameStringExpressionConverter();
		}
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private NameStringExpressionConverter() {
		super();
	}

	@Override
	protected Name convert_(String string, AST ast) {
		return ast.newName(string);
	}

	@Override
	protected String convert_(Name name) {
		switch (name.getNodeType()) {
			case ASTNode.QUALIFIED_NAME:
			case ASTNode.SIMPLE_NAME:
				return name.getFullyQualifiedName();
			default:
				return null;
		}
	}

}
