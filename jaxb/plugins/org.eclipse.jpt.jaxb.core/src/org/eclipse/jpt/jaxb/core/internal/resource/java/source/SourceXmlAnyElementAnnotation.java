/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.Attribute;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlAnyElement
 */
public final class SourceXmlAnyElementAnnotation
	extends SourceAnnotation<Attribute>
	implements XmlAnyElementAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private final DeclarationAnnotationElementAdapter<Boolean> laxDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> laxAdapter;
	private Boolean lax;

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private String fullyQualifiedValueClassName;


	// ********** constructors **********
	public SourceXmlAnyElementAnnotation(JavaResourceMember parent, Attribute attribute) {
		this(parent, attribute, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(attribute, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlAnyElementAnnotation(JavaResourceMember parent, Attribute attribute, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, attribute, daa, annotationAdapter);
		this.laxDeclarationAdapter = this.buildLaxAdapter(daa);
		this.laxAdapter = this.buildShortCircuitBooleanElementAdapter(this.laxDeclarationAdapter);
		this.valueDeclarationAdapter = this.buildValueAdapter(daa);
		this.valueAdapter = this.buildAnnotationElementAdapter(this.valueDeclarationAdapter);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildLaxAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ANY_ELEMENT__LAX);
	}

	private DeclarationAnnotationElementAdapter<String> buildValueAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_ANY_ELEMENT__VALUE, SimpleTypeStringExpressionConverter.instance());
	}

	private DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	private AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.lax = this.buildLax(astRoot);
		this.value = this.buildValue(astRoot);
		this.fullyQualifiedValueClassName = this.buildFullyQualifiedValueClassName(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncLax(this.buildLax(astRoot));
		this.syncValue(this.buildValue(astRoot));
		this.syncFullyQualifiedValueClassName(this.buildFullyQualifiedValueClassName(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** XmlAnyElementAnnotation implementation **********

	// ***** lax
	public Boolean getLax() {
		return this.lax;
	}

	public void setLax(Boolean lax) {
		if (this.attributeValueHasChanged(this.lax, lax)) {
			this.lax = lax;
			this.laxAdapter.setValue(lax);
		}
	}

	private void syncLax(Boolean astLax) {
		Boolean old = this.lax;
		this.lax = astLax;
		this.firePropertyChanged(LAX_PROPERTY, old, astLax);
	}

	private Boolean buildLax(CompilationUnit astRoot) {
		return this.laxAdapter.getValue(astRoot);
	}

	public TextRange getLaxTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.laxDeclarationAdapter, astRoot);
	}

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}
	
	// ***** fully-qualified value class name
	public String getFullyQualifiedValueClassName() {
		return this.fullyQualifiedValueClassName;
	}

	private void syncFullyQualifiedValueClassName(String name) {
		String old = this.fullyQualifiedValueClassName;
		this.fullyQualifiedValueClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_VALUE_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedValueClassName(CompilationUnit astRoot) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}
}
