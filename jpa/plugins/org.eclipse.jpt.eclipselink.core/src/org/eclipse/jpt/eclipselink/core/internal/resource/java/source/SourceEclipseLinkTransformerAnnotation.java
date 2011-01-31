/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
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
		this.transformerClassDeclarationAdapter = new ConversionDeclarationAnnotationElementAdapter<String>(daa, this.getTransformerClassElementName(), SimpleTypeStringExpressionConverter.instance());
		this.transformerClassAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, this.transformerClassDeclarationAdapter);

		this.methodDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, this.getMethodElementName());
		this.methodAdapter = new AnnotatedElementAnnotationElementAdapter<String>(attribute, this.methodDeclarationAdapter);
	}

	public void initialize(CompilationUnit astRoot) {
		this.transformerClass = this.buildTransformerClass(astRoot);
		this.method = this.buildMethod(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncTransformerClass(this.buildTransformerClass(astRoot));
		this.syncMethod(this.buildMethod(astRoot));
	}

	@Override
	public boolean isUnset() {
		return super.isUnset() &&
				(this.transformerClass == null) &&
				(this.method == null);
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
		if (this.attributeValueHasChanged(this.transformerClass, transformerClass)) {
			this.transformerClass = transformerClass;
			this.transformerClassAdapter.setValue(transformerClass);
		}
	}

	private void syncTransformerClass(String astTransformerClass) {
		String old = this.transformerClass;
		this.transformerClass = astTransformerClass;
		this.firePropertyChanged(TRANSFORMER_CLASS_PROPERTY, old, astTransformerClass);
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
		if (this.attributeValueHasChanged(this.method, method)) {
			this.method = method;
			this.methodAdapter.setValue(method);
		}
	}

	private void syncMethod(String astMethod) {
		String old = this.method;
		this.method = astMethod;
		this.firePropertyChanged(METHOD_PROPERTY, old, astMethod);
	}

	private String buildMethod(CompilationUnit astRoot) {
		return this.methodAdapter.getValue(astRoot);
	}

	public TextRange getMethodTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.methodDeclarationAdapter, astRoot);
	}

	abstract String getMethodElementName();

}
