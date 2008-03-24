/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap a declaration annotation element adapter that deals with AST
 * expressions, converting them to/from various other objects.
 * T is the type of the object to be passed to and returned by the adapter.
 */
public class ConversionDeclarationAnnotationElementAdapter<T>
	implements DeclarationAnnotationElementAdapter<T>
{
	/**
	 * The wrapped adapter that returns and takes AST expressions.
	 */
	private final DeclarationAnnotationElementAdapter<Expression> adapter;

	/**
	 * The converter that converts AST expressions to other objects
	 * (e.g. Strings).
	 */
	private final ExpressionConverter<T> converter;


	// ********** constructors **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, ExpressionConverter<T> converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter<Expression>(annotationAdapter), converter);
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<T> converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter<Expression>(annotationAdapter, elementName), converter);
	}

	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty, ExpressionConverter<T> converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter<Expression>(annotationAdapter, elementName, removeAnnotationWhenEmpty), converter);
	}

	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationElementAdapter<Expression> adapter, ExpressionConverter<T> converter) {
		super();
		this.adapter = adapter;
		this.converter = converter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public T getValue(ModifiedDeclaration declaration) {
		Expression expression = this.adapter.getValue(declaration);
		return this.converter.convert(expression);
	}

	public void setValue(T value, ModifiedDeclaration declaration) {
		Expression expression;
		try {
			expression = this.converter.convert(value, declaration.getAST());
		} catch (IllegalArgumentException ex) {
			// if there is a problem converting the 'value' to an Expression we get this exception
			return;  // don't set the value if it is "illegal"
		}
		this.adapter.setValue(expression, declaration);
	}

	public Expression expression(ModifiedDeclaration declaration) {
		return this.adapter.expression(declaration);
	}

	public ASTNode astNode(ModifiedDeclaration declaration) {
		return this.adapter.astNode(declaration);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.adapter);
	}


	// ********** factory static methods **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed;
	 * the default expression converter expects string constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forStrings(DeclarationAnnotationAdapter annotationAdapter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, StringExpressionConverter.instance());
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed; the default expression converter expects
	 * string constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forStrings(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, StringExpressionConverter.instance());
	}

	/**
	 * The default expression converter expects string constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forStrings(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, removeAnnotationWhenEmpty, StringExpressionConverter.instance());
	}

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed;
	 * the default expression converter expects number constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Integer> forNumbers(DeclarationAnnotationAdapter annotationAdapter) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(annotationAdapter, NumberIntegerExpressionConverter.instance());
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed; the default expression converter expects
	 * number constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Integer> forNumbers(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(annotationAdapter, elementName, NumberIntegerExpressionConverter.instance());
	}

	/**
	 * The default expression converter expects number constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Integer> forNumbers(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		return new ConversionDeclarationAnnotationElementAdapter<Integer>(annotationAdapter, elementName, removeAnnotationWhenEmpty, NumberIntegerExpressionConverter.instance());
	}

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed;
	 * the default expression converter expects boolean constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Boolean> forBooleans(DeclarationAnnotationAdapter annotationAdapter) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, BooleanExpressionConverter.instance());
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed; the default expression converter expects
	 * boolean constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Boolean> forBooleans(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, BooleanExpressionConverter.instance());
	}

	/**
	 * The default expression converter expects boolean constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<Boolean> forBooleans(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		return new ConversionDeclarationAnnotationElementAdapter<Boolean>(annotationAdapter, elementName, removeAnnotationWhenEmpty, BooleanExpressionConverter.instance());
	}

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed;
	 * the default expression converter expects character constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forCharacters(DeclarationAnnotationAdapter annotationAdapter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, CharacterStringExpressionConverter.instance());
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed; the default expression converter expects
	 * character constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forCharacters(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, CharacterStringExpressionConverter.instance());
	}

	/**
	 * The default expression converter expects character constant expressions.
	 */
	public static ConversionDeclarationAnnotationElementAdapter<String> forCharacters(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, removeAnnotationWhenEmpty, CharacterStringExpressionConverter.instance());
	}

}
