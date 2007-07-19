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

/**
 * Convert an array initializer to/from an array of strings (e.g. {"text0", "text1"}).
 * E is the type of the expressions to be found in the array initializer.
 * 
 * Do NOT use this class for converting array initializers in annotation elements.
 * Java5 has a bit of syntactic sugar that allows a single-element array
 * initializer to not have curly braces. This converter will barf if it encounters
 * anything other than an array initializer.
 */
public class StringArrayExpressionConverter<E extends Expression>
	extends AbstractExpressionConverter<String[], ArrayInitializer>
{
	private final ExpressionConverter<String, E> elementConverter;
	private final boolean removeArrayInitializerWhenEmpty;

	private static final String[] EMPTY_STRING_ARRAY = new String[0];


	/**
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public StringArrayExpressionConverter(ExpressionConverter<String, E> elementConverter) {
		this(elementConverter, true);
	}

	public StringArrayExpressionConverter(ExpressionConverter<String, E> elementConverter, boolean removeArrayInitializerWhenEmpty) {
		super();
		this.elementConverter = elementConverter;
		this.removeArrayInitializerWhenEmpty = removeArrayInitializerWhenEmpty;
	}

	@Override
	/*
	 * this method is 'public' so it can be called by
	 * AnnotationStringArrayExpressionConverter
	 */
	public ArrayInitializer convert_(String[] strings, AST ast) {
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

	@Override
	/*
	 * this method is 'public' so it can be called by
	 * AnnotationStringArrayExpressionConverter
	 */
	public String[] convertNull() {
		return EMPTY_STRING_ARRAY;
	}

	@Override
	/*
	 * 'public' in support of AnnotationStringArrayExpressionConverter
	 */
	public String[] convert_(ArrayInitializer arrayInitializer) {
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

}
