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

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Wrap a declaration annotation element adapter that deals with AST
 * expressions, converting them to/from various other objects.
 */
public class ConversionDeclarationAnnotationElementAdapter
	implements DeclarationAnnotationElementAdapter
{
	/**
	 * The wrapped adapter that returns and takes AST expressions.
	 */
	private final DeclarationAnnotationElementAdapter adapter;

	/**
	 * The converter that converts AST expressions to other objects
	 * (e.g. Strings).
	 */
	private final ExpressionConverter converter;


	// ********** constructors **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed;
	 * the default expression converter expects string literals.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter) {
		this(annotationAdapter, StringExpressionConverter.instance());
	}

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, ExpressionConverter converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter(annotationAdapter), converter);
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed; the default expression converter expects
	 * string literals.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, StringExpressionConverter.instance());
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter(annotationAdapter, elementName), converter);
	}

	/**
	 * The default expression converter expects string literals.
	 */
	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		this(annotationAdapter, elementName, removeAnnotationWhenEmpty, StringExpressionConverter.instance());
	}

	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty, ExpressionConverter converter) {
		this(new ExpressionDeclarationAnnotationElementAdapter(annotationAdapter, elementName, removeAnnotationWhenEmpty), converter);
	}

	public ConversionDeclarationAnnotationElementAdapter(DeclarationAnnotationElementAdapter adapter, ExpressionConverter converter) {
		super();
		this.adapter = adapter;
		this.converter = converter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public Object getValue(ModifiedDeclaration declaration) {
		return this.converter.convert((Expression) this.adapter.getValue(declaration));
	}

	public void setValue(Object value, ModifiedDeclaration declaration) {
		this.adapter.setValue(this.converter.convert(value, this.adapter.astNode(declaration).getAST()), declaration);
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

}
