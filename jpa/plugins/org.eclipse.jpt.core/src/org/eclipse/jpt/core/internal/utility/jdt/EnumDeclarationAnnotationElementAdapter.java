/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.utility.jdt.ModifiedDeclaration;

/**
 * Wrap a declaration annotation element adapter and simply
 * add an import for the enum when necessary.
 */
public class EnumDeclarationAnnotationElementAdapter
	implements DeclarationAnnotationElementAdapter<String>
{
	/**
	 * The wrapped adapter that returns and takes name strings (enums).
	 */
	private final ConversionDeclarationAnnotationElementAdapter<String> adapter;


	// ********** constructors **********

	/**
	 * The default element name is "value"; the default behavior is to
	 * remove the annotation when the last element is removed.
	 */
	public EnumDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter) {
		this(annotationAdapter, VALUE);
	}

	/**
	 * The default behavior is to remove the annotation when the last
	 * element is removed.
	 */
	public EnumDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName) {
		this(annotationAdapter, elementName, true);
	}

	public EnumDeclarationAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, boolean removeAnnotationWhenEmpty) {
		this(new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, removeAnnotationWhenEmpty, NameStringExpressionConverter.instance()));
	}

	protected EnumDeclarationAnnotationElementAdapter(ConversionDeclarationAnnotationElementAdapter<String> adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public String getValue(ModifiedDeclaration declaration) {
		return this.resolve(this.adapter.getExpression(declaration));
	}

	public void setValue(String value, ModifiedDeclaration declaration) {
		this.adapter.setValue(convertToSourceCodeName(value, declaration), declaration);
	}

	public Expression getExpression(ModifiedDeclaration declaration) {
		return this.adapter.getExpression(declaration);
	}

	public ASTNode getAstNode(ModifiedDeclaration declaration) {
		return this.adapter.getAstNode(declaration);
	}


	// ********** internal methods **********

	/**
	 * resolve the enum
	 */
	protected String resolve(Expression expression) {
		return ASTTools.resolveEnum(expression);
	}

	/**
	 * convert the fully-qualified enum constant to a static import and the constant's short name, e.g.
	 *     static import javax.persistence.FetchType.EAGER;
	 *     return "EAGER"
	 * if that doesn't work, convert to a normal import and the constant's partially-qualified name, e.g.
	 *     import javax.persistence.FetchType;
	 *     return "FetchType.EAGER"
	 * if that doesn't work, simply return the constant's fully-qualified name, e.g.
	 *     return "javax.persistence.FetchType.EAGER"
	 * NB: an import may be added as a side-effect :-(
	 */
	protected static String convertToSourceCodeName(String enumConstantName, ModifiedDeclaration declaration) {
		return (enumConstantName == null) ? null : convertToSourceCodeName_(enumConstantName, declaration);
	}

	/**
	 * pre-condition: enum constant name is non-null;
	 * convert it to its short version if we can add a static import etc.
	 */
	protected static String convertToSourceCodeName_(String enumConstantName, ModifiedDeclaration declaration) {
		if (declaration.addStaticImport(enumConstantName)) {
			return convertToShortName(enumConstantName);
		}
		if (declaration.addImport(convertToTypeName(enumConstantName))) {
			return convertToPartiallyQualifiedName(enumConstantName);
		}
		return enumConstantName;
	}

	protected static String convertToShortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

	protected static String convertToTypeName(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	protected static String convertToPartiallyQualifiedName(String name) {
		return name.substring(name.lastIndexOf('.', name.lastIndexOf('.') - 1) + 1);
	}

}
