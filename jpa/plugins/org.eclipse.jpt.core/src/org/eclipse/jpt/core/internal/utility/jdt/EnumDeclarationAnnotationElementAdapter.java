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
		this(annotationAdapter, "value");
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
		this.adapter.setValue(this.convertToShortName(value, declaration), declaration);
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
		return JDTTools.resolveEnum(expression);
	}

	/**
	 * convert the fully-qualified enum to a static import and its short name
	 */
	protected String convertToShortName(String string, ModifiedDeclaration declaration) {
		if (string == null) {
			return null;
		}
		declaration.addStaticImport(string);  // e.g. "javax.persistence.FetchType.EAGER"
		return this.shortName(string);  // e.g. "EAGER"
	}

	protected String shortTypeName(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	protected String shortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

}
