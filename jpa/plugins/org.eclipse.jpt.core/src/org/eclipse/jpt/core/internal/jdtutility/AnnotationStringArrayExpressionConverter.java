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

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.StringLiteral;

/**
 * Convert an array initializer or single expression to/from an array of
 * strings (e.g. {"text0", "text1"}).
 * E is the type of the expressions to be found either standalone or
 * as elements in the array initializer.
 */
public class AnnotationStringArrayExpressionConverter<E extends Expression>
	extends AbstractExpressionConverter<String[], Expression>
{
	private final ExpressionConverter<String, E> elementConverter;
	private final StringArrayExpressionConverter<E> arrayConverter;


	/**
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public AnnotationStringArrayExpressionConverter(ExpressionConverter<String, E> elementConverter) {
		this(elementConverter, true);
	}

	public AnnotationStringArrayExpressionConverter(ExpressionConverter<String, E> elementConverter, boolean removeArrayInitializerWhenEmpty) {
		super();
		this.elementConverter = elementConverter;
		this.arrayConverter = new StringArrayExpressionConverter<E>(elementConverter, removeArrayInitializerWhenEmpty);
	}

	/**
	 * if we only have a single string in the array return the single expression,
	 * without braces, instead of an array initializer
	 */
	@Override
	protected Expression convert_(String[] strings, AST ast) {
		return (strings.length == 1) ?
				this.elementConverter.convert(strings[0], ast)
			:
				this.arrayConverter.convert_(strings, ast);
	}

	@Override
	protected String[] convertNull() {
		return this.arrayConverter.convertNull();
	}

	/**
	 * check for a single expression with no surrounding braces, implying a
	 * single-entry array
	 */
	@Override
	protected String[] convert_(Expression expression) {
		return (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) ?
				this.arrayConverter.convert_((ArrayInitializer) expression)
			:
				new String[] {this.elementConverter.convert(this.downcastExpression(expression))};
	}

	@SuppressWarnings("unchecked")
	private E downcastExpression(Expression expression) {
		return (E) expression;
	}


	// ********** factory methods **********

	/**
	 * Build an expression converter for an annotation element of type String[].
	 *     @Foo(bar={"text0", "text1"})
	 * or
	 *     @Foo(bar="text0")
	 */
	public static AnnotationStringArrayExpressionConverter<StringLiteral> forStringLiterals() {
		return new AnnotationStringArrayExpressionConverter<StringLiteral>(StringExpressionConverter.instance());
	}

	/**
	 * Build an expression converter for an annotation element of type <enum>[].
	 *     @Foo(bar={BAZ, BAT})
	 * or
	 *     @Foo(bar=BAZ)
	 */
	public static AnnotationStringArrayExpressionConverter<Name> forNames() {
		return new AnnotationStringArrayExpressionConverter<Name>(NameStringExpressionConverter.instance());
	}

}
