/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jdtutility;

import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.StringLiteral;

/**
 * Convert an array initializer to/from an array of strings (e.g. {"text0", "text1"}).
 * E is the type of the expressions to be found in the array initializer.
 */
public class StringArrayExpressionConverter<E extends Expression>
	extends AbstractExpressionConverter<String[], ArrayInitializer>
{
	private final ExpressionConverter<String, E> elementConverter;

	public StringArrayExpressionConverter(ExpressionConverter<String, E> elementConverter) {
		super();
		this.elementConverter = elementConverter;
	}

	@Override
	protected ArrayInitializer convert_(String[] strings, AST ast) {
		ArrayInitializer arrayInitializer = ast.newArrayInitializer();
		List<Expression> expressions = this.expressions(arrayInitializer);
		for (String string : strings) {
			expressions.add(this.elementConverter.convert(string, ast));
		}
		return arrayInitializer;
	}

	@SuppressWarnings("unchecked")
	private List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	@Override
	protected String[] convert_(ArrayInitializer arrayInitializer) {
		List<E> expressions = this.downcastExpressions(arrayInitializer);
		int len = expressions.size();
		String[] strings = new String[len];
		for (int i = len; i-- > 0; ) {
			strings[i] = this.elementConverter.convert(expressions.get(i));
		}
		return strings;
	}

	@SuppressWarnings("unchecked")
	private List<E> downcastExpressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	/**
	 * Build an expression converter for an annotation element of type String[].
	 *     @Foo(bar={"text0", "text1"})
	 */
	public static StringArrayExpressionConverter<StringLiteral> forStringLiterals() {
		return new StringArrayExpressionConverter<StringLiteral>(StringExpressionConverter.instance());
	}

}
