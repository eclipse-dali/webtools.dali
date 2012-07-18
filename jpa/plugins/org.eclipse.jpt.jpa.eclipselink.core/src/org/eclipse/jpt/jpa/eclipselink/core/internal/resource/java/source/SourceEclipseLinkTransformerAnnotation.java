/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkTransformerAnnotation;

/**
 * <code><ul>
 * <li>org.eclipse.persistence.annotations.ReadTransformer
 * <li>org.eclipse.persistence.annotations.WriteTransformer
 * </ul></code>
 */
abstract class SourceEclipseLinkTransformerAnnotation
	extends SourceAnnotation
	implements EclipseLinkTransformerAnnotation
{
	final DeclarationAnnotationElementAdapter<String> transformerClassDeclarationAdapter;
	final AnnotationElementAdapter<String> transformerClassAdapter;
	String transformerClass;
	TextRange transformerClassTextRange;

	final DeclarationAnnotationElementAdapter<String> methodDeclarationAdapter;
	final AnnotationElementAdapter<String> methodAdapter;
	String method;
	TextRange methodTextRange;


	SourceEclipseLinkTransformerAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement element, DeclarationAnnotationAdapter daa) {
		super(parent, element, daa);
		this.transformerClassDeclarationAdapter = new ConversionDeclarationAnnotationElementAdapter<String>(daa, this.getTransformerClassElementName(), SimpleTypeStringExpressionConverter.instance());
		this.transformerClassAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, this.transformerClassDeclarationAdapter);

		this.methodDeclarationAdapter = ConversionDeclarationAnnotationElementAdapter.forStrings(daa, this.getMethodElementName());
		this.methodAdapter = new AnnotatedElementAnnotationElementAdapter<String>(element, this.methodDeclarationAdapter);
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.transformerClass = this.buildTransformerClass(astAnnotation);
		this.transformerClassTextRange = this.buildTransformerClassTextRange(astAnnotation);

		this.method = this.buildMethod(astAnnotation);
		this.methodTextRange = this.buildMethodTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncTransformerClass(this.buildTransformerClass(astAnnotation));
		this.transformerClassTextRange = this.buildTransformerClassTextRange(astAnnotation);

		this.syncMethod(this.buildMethod(astAnnotation));
		this.methodTextRange = this.buildMethodTextRange(astAnnotation);
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

	private String buildTransformerClass(Annotation astAnnotation) {
		return this.transformerClassAdapter.getValue(astAnnotation);
	}

	public TextRange getTransformerClassTextRange() {
		return this.transformerClassTextRange;
	}

	private TextRange buildTransformerClassTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.transformerClassDeclarationAdapter, astAnnotation);
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

	private String buildMethod(Annotation astAnnotation) {
		return this.methodAdapter.getValue(astAnnotation);
	}

	public TextRange getMethodTextRange() {
		return this.methodTextRange;
	}

	private TextRange buildMethodTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.methodDeclarationAdapter, astAnnotation);
	}

	abstract String getMethodElementName();
}
