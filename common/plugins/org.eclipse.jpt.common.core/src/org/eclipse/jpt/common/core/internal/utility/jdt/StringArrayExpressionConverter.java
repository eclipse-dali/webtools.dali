/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Convert an array initializer to/from an array of strings (e.g. {"text0", "text1"}).
 * 
 * Do NOT use this class for converting array initializers in annotation elements.
 * Java5 has a bit of syntactic sugar that allows a single-element array
 * initializer to not have curly braces. This converter will return null if it encounters
 * anything other than an array initializer.
 * 
 * Invalid entries in the array initializer will result in null elements in the
 * resulting string array. This allows clients to manipulate elements at
 * the appropriate index.
 */
public class StringArrayExpressionConverter
	extends AbstractExpressionConverter<String[]>
{
	private final ExpressionConverter<String> elementConverter;
	private final boolean removeArrayInitializerWhenEmpty;


	/**
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public StringArrayExpressionConverter(ExpressionConverter<String> elementConverter) {
		this(elementConverter, true);
	}

	public StringArrayExpressionConverter(ExpressionConverter<String> elementConverter, boolean removeArrayInitializerWhenEmpty) {
		super();
		this.elementConverter = elementConverter;
		this.removeArrayInitializerWhenEmpty = removeArrayInitializerWhenEmpty;
	}

	/*
	 * this method is 'public' so it can be called by
	 * AnnotationStringArrayExpressionConverter
	 */
	@Override
	public ArrayInitializer convertObject(String[] strings, AST ast) {
		if ((strings.length == 0) && this.removeArrayInitializerWhenEmpty) {
			return null;
		}
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

	/*
	 * this method is 'public' so it can be called by
	 * AnnotationStringArrayExpressionConverter
	 */
	@Override
	public String[] convertNull() {
		return StringTools.EMPTY_STRING_ARRAY;
	}

	@Override
	protected String[] convertExpression(Expression expression) {
		return (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) ?
				this.convertArrayInitializer((ArrayInitializer) expression) :
				StringTools.EMPTY_STRING_ARRAY;
	}

	/*
	 * this method is 'public' so it can be called by
	 * AnnotationStringArrayExpressionConverter
	 */
	public String[] convertArrayInitializer(ArrayInitializer arrayInitializer) {
		List<Expression> expressions = this.expressions(arrayInitializer);
		int len = expressions.size();
		String[] strings = new String[len];
		for (int i = len; i-- > 0; ) {
			strings[i] = this.elementConverter.convert(expressions.get(i));
		}
		return strings;
	}

}
