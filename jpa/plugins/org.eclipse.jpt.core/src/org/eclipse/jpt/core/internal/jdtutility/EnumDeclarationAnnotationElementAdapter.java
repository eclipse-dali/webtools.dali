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

/**
 * Wrap a declaration annotation element adapter and simply
 * add an import for the enum when necessary.
 */
public class EnumDeclarationAnnotationElementAdapter
	implements DeclarationAnnotationElementAdapter
{
	/**
	 * The wrapped adapter that returns and takes name strings (enums).
	 */
	private final ConversionDeclarationAnnotationElementAdapter adapter;


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
		this(new ConversionDeclarationAnnotationElementAdapter(annotationAdapter, elementName, removeAnnotationWhenEmpty, NameStringExpressionConverter.instance()));
	}

	protected EnumDeclarationAnnotationElementAdapter(ConversionDeclarationAnnotationElementAdapter adapter) {
		super();
		this.adapter = adapter;
	}


	// ********** DeclarationAnnotationElementAdapter implementation **********

	public Object getValue(ModifiedDeclaration declaration) {
		return this.resolve(this.adapter.expression(declaration), declaration);
	}

	public void setValue(Object value, ModifiedDeclaration declaration) {
		this.adapter.setValue(this.convertToShortName(value, declaration), declaration);
	}

	public Expression expression(ModifiedDeclaration declaration) {
		return this.adapter.expression(declaration);
	}

	public ASTNode astNode(ModifiedDeclaration declaration) {
		return this.adapter.astNode(declaration);
	}


	// ********** internal methods **********

	/**
	 * resolve the enum's short name
	 */
	protected String resolve(Expression enumExpression, ModifiedDeclaration declaration) {
		return (enumExpression == null) ? null : JDTTools.resolveEnum(declaration.iCompilationUnit(), enumExpression);
	}

	/**
	 * convert the fully-qualified enum to a static import and its short name
	 */
	protected String convertToShortName(Object value, ModifiedDeclaration declaration) {
		if (value == null) {
			return null;
		}
		String enum_ = (String) value;
		declaration.addStaticImport(enum_);  // e.g. "javax.persistence.FetchType.EAGER"
		return this.shortName(enum_);  // e.g. "EAGER"
	}

	protected String shortTypeName(String name) {
		return name.substring(0, name.lastIndexOf('.'));
	}

	protected String shortName(String name) {
		return name.substring(name.lastIndexOf('.') + 1);
	}

}
