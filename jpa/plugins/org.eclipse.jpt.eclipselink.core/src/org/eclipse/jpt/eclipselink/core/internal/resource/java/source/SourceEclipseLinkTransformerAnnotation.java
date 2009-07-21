/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ShortCircuitAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.Attribute;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkTransformerAnnotation;

/**
 * org.eclipse.persistence.annotations.ReadTransformer
 * org.eclipse.persistence.annotations.WriteTransformer
 */
abstract class SourceEclipseLinkTransformerAnnotation
	extends SourceAnnotation<Attribute>
	implements EclipseLinkTransformerAnnotation
{
	final DeclarationAnnotationElementAdapter<String> transformerClassDeclarationAdapter;
	final AnnotationElementAdapter<String> transformerClassAdapter;
	String transformerClass;

	final DeclarationAnnotationElementAdapter<String> methodDeclarationAdapter;
	final AnnotationElementAdapter<String> methodAdapter;
	String method;


	SourceEclipseLinkTransformerAnnotation(JavaResourcePersistentAttribute parent, Attribute attribute, DeclarationAnnotationAdapter daa) {
		super(parent, attribute, daa);
		this.transformerClassDeclarationAdapter = new ConversionDeclarationAnnotationElementAdapter<String>(daa, this.getTransformerClassElementName(), false, SimpleTypeStringExpressionConverter.instance());
		this.transformerClassAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, this.transformerClassDeclarationAdapter);

		this.methodDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, this.getMethodElementName(), false);
		this.methodAdapter = new ShortCircuitAnnotationElementAdapter<String>(attribute, this.methodDeclarationAdapter);
	}

	public void initialize(CompilationUnit astRoot) {
		this.transformerClass = this.buildTransformerClass(astRoot);
		this.method = this.buildMethod(astRoot);
	}

	public void update(CompilationUnit astRoot) {
		this.setTransformerClass(this.buildTransformerClass(astRoot));
		this.setMethod(this.buildMethod(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.transformerClass);
	}


	// ********** TransformerAnnotation implementation **********

	// ***** transformer class
	public String getTransformerClass() {
		return this.transformerClass;
	}

	public void setTransformerClass(String transformerClass) {
		if (this.attributeValueHasNotChanged(this.transformerClass, transformerClass)) {
			return;
		}
		String old = this.transformerClass;
		this.transformerClass = transformerClass;
		this.transformerClassAdapter.setValue(transformerClass);
		this.firePropertyChanged(TRANSFORMER_CLASS_PROPERTY, old, transformerClass);
	}

	private String buildTransformerClass(CompilationUnit astRoot) {
		return this.transformerClassAdapter.getValue(astRoot);
	}

	public TextRange getTransformerClassTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.transformerClassDeclarationAdapter, astRoot);
	}

	abstract String getTransformerClassElementName();

	// ***** method
	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		if (this.attributeValueHasNotChanged(this.method, method)) {
			return;
		}
		String old = this.method;
		this.method = method;
		this.methodAdapter.setValue(method);
		this.firePropertyChanged(METHOD_PROPERTY, old, method);
	}

	private String buildMethod(CompilationUnit astRoot) {
		return this.methodAdapter.getValue(astRoot);
	}

	public TextRange getMethodTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.methodDeclarationAdapter, astRoot);
	}

	abstract String getMethodElementName();

}
