/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Wrap a declaration annotation element adapter and simply
 * add an import for the enums when necessary.
 */
public class EnumArrayDeclarationAnnotationElementAdapter
	implements DeclarationAnnotationElementAdapter<String[]>
{
	/**
	 * The wrapped adapter that returns and takes name strings (enums).
	 */
	private final ConversionDeclarationAnnotationElementAdapter<String[]> adapter;

	private static final String[] EMPTY_STRING_ARRAY = new String[0];


	// ********** constructors **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter) {
		this(annotationAdapter, "value");
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed and remove the array initializer if it is empty.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, true);
	}

	/**
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		this(annotationAdapter, elementName, removeAnnotationWhenEmpty, true);
	}

	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty, boolean removeArrayInitializerWhenEmpty) {
		this(new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, removeAnnotationWhenEmpty, buildExpressionConverter(removeArrayInitializerWhenEmpty)));
	}

	private static ExpressionConverter<String[]> buildExpressionConverter(boolean removeArrayInitializerWhenEmpty) {
		return new AnnotationStringArrayExpressionConverter(NameStringExpressionConverter.instance(), removeArrayInitializerWhenEmpty);
	}

	protected EnumArrayDeclarationAnnotationElementAdapter(ConversionDeclarationAnnotationElementAdapter<String[]> adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public String[] getValue(ModifiedDeclaration declaration) {
		// ignore the adapter's getValue() - we want the expression
		return this.resolve(this.adapter.expression(declaration), declaration);
	}

	public void setValue(String[] value, ModifiedDeclaration declaration) {
		this.adapter.setValue(this.convertToShortNames(value, declaration), declaration);
	}

	public Expression expression(ModifiedDeclaration declaration) {
		return this.adapter.expression(declaration);
	}

	public ASTNode astNode(ModifiedDeclaration declaration) {
		return this.adapter.astNode(declaration);
	}


	// ********** internal methods **********

	/**
	 * resolve the enums, which can be
	 *     null
	 * or
	 *     {FOO, BAR, BAZ}
	 * or
	 *     FOO
	 */
	protected String[] resolve(Expression expression, ModifiedDeclaration declaration) {
		if (expression == null) {
			return EMPTY_STRING_ARRAY;
		} else if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			return this.resolveArray((ArrayInitializer) expression, declaration);
		} else {
			return this.resolveSingleElement(expression, declaration);
		}
	}

	protected String[] resolveArray(ArrayInitializer ai, ModifiedDeclaration declaration) {
		List<Expression> expressions = this.expressions(ai);
		int len = expressions.size();
		String[] enums = new String[len];
		for (int i = len; i-- > 0; ) {
			enums[i] = this.resolveEnum(expressions.get(i));
		}
		return enums;
	}

	protected String[] resolveSingleElement(Expression enumExpression, ModifiedDeclaration declaration) {
		return new String[] {this.resolveEnum(enumExpression)};
	}

	protected String resolveEnum(Expression expression) {
		return JDTTools.resolveEnum(expression);
	}

	@SuppressWarnings("unchecked")
	private List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	/**
	 * convert the fully-qualified enums to static imports and short names;
	 * NB: the imports are added as a side-effect :-(
	 */
	protected String[] convertToShortNames(String[] enums, ModifiedDeclaration declaration) {
		if (enums == null) {
			return null;
		}
		int len = enums.length;
		String[] shortNames = new String[len];
		for (int i = len; i-- > 0; ) {
			declaration.addStaticImport(enums[i]);  // e.g. "javax.persistence.CascadeType.REFRESH"
			shortNames[i] = this.shortName(enums[i]);  // e.g. "EAGER"
		}
		return shortNames;
	}

	protected String shortTypeName(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	protected String shortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

}
