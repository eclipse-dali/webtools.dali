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
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.Name;

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
	private final ConversionDeclarationAnnotationElementAdapter<String[], ArrayInitializer> adapter;


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
	 * element is removed.
	 */
	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, true);
	}

	public EnumArrayDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		this(new ConversionDeclarationAnnotationElementAdapter<String[], ArrayInitializer>(annotationAdapter, elementName, removeAnnotationWhenEmpty, buildExpressionConverter()));
	}

	private static ExpressionConverter<String[], ArrayInitializer> buildExpressionConverter() {
		return new StringArrayExpressionConverter<Name>(NameStringExpressionConverter.instance());
	}

	protected EnumArrayDeclarationAnnotationElementAdapter(ConversionDeclarationAnnotationElementAdapter<String[], ArrayInitializer> adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public String[] getValue(ModifiedDeclaration declaration) {
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
	 * resolve the enums
	 */
	protected String[] resolve(Expression enumsExpression, ModifiedDeclaration declaration) {
		if (enumsExpression == null) {
			return null;
		}
		ArrayInitializer ai = (ArrayInitializer) enumsExpression;
		List<Expression> expressions = this.expressions(ai);
		int len = expressions.size();
		String[] enums = new String[len];
		for (int i = len; i-- > 0; ) {
			enums[i] = JDTTools.resolveEnum(declaration.iCompilationUnit(), expressions.get(i));
		}
		return enums;
	}

	@SuppressWarnings("unchecked")
	private List<Expression> expressions(ArrayInitializer arrayInitializer) {
		return arrayInitializer.expressions();
	}

	/**
	 * convert the fully-qualified enums to static imports and short names
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
