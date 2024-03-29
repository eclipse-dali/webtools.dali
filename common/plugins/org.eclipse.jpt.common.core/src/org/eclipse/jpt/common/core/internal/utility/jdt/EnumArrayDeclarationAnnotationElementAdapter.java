/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.jdt;

import java.util.List;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.ModifiedDeclaration;
import org.eclipse.jpt.common.utility.internal.StringTools;

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


	// ********** constructors **********

	/**
	 * The default element name is <code>value</code>.
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter) {
		this(annotationAdapter, VALUE);
	}

	/**
	 * The default behavior is to remove the array initializer if it is empty.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, true);
	}

	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeArrayInitializerWhenEmpty) {
		this(new ConversionDeclarationAnnotationElementAdapter<String[]>(annotationAdapter, elementName, buildExpressionConverter(removeArrayInitializerWhenEmpty)));
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
		return this.resolve(this.getExpression(declaration));
	}

	public String[] getValue(Annotation astAnnotation) {
		// ignore the adapter's getValue() - we want the expression
		return this.resolve(this.getExpression(astAnnotation));
	}

	public void setValue(String[] value, ModifiedDeclaration declaration) {
		this.adapter.setValue(this.convertToSourceCodeNames(value, declaration), declaration);
	}

	public Expression getExpression(ModifiedDeclaration declaration) {
		return this.adapter.getExpression(declaration);
	}

	public Expression getExpression(Annotation astAnnotation) {
		return this.adapter.getExpression(astAnnotation);
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		return this.adapter.getAstNode(declaration);
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
	protected String[] resolve(Expression expression) {
		if (expression == null) {
			return StringTools.EMPTY_STRING_ARRAY;
		} else if (expression.getNodeType() == ASTNode.ARRAY_INITIALIZER) {
			return this.resolveArray((ArrayInitializer) expression);
		} else {
			return this.resolveSingleElement(expression);
		}
	}

	protected String[] resolveArray(ArrayInitializer ai) {
		List<Expression> expressions = this.expressions(ai);
		int len = expressions.size();
		String[] enums = new String[len];
		for (int i = len; i-- > 0; ) {
			enums[i] = this.resolveEnum(expressions.get(i));
		}
		return enums;
	}

	protected String[] resolveSingleElement(Expression enumExpression) {
		return new String[] {this.resolveEnum(enumExpression)};
	}

	protected String resolveEnum(Expression expression) {
		return ASTTools.resolveEnum(expression);
	}

	// minimize scope of suppressed warnings
	@SuppressWarnings("unchecked")
	private List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	/**
	 * convert the fully-qualified enums to names that can be inserted in source code
	 * NB: imports may be added as a side-effect :-(
	 */
	protected String[] convertToSourceCodeNames(String[] enums, ModifiedDeclaration declaration) {
		if (enums == null) {
			return null;
		}
		int len = enums.length;
		String[] sourceCodeNames = new String[len];
		for (int i = 0; i < len; i++) {
			sourceCodeNames[i] = this.convertToSourceCodeName(enums[i], declaration);
		}
		return sourceCodeNames;
	}

	protected String convertToSourceCodeName(String enum_, ModifiedDeclaration declaration) {
		return EnumDeclarationAnnotationElementAdapter.convertToSourceCodeName(enum_, declaration);
	}

}
