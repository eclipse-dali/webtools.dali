/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jpt.common.core.internal.resource.java.source.SourceAnnotation;
import org.eclipse.jpt.common.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.common.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAnyElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlAnyElement
 */
public final class SourceXmlAnyElementAnnotation
	extends SourceAnnotation
	implements XmlAnyElementAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ANY_ELEMENT);

	private final DeclarationAnnotationElementAdapter<Boolean> laxDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> laxAdapter;
	private Boolean lax;
	private TextRange laxTextRange;

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;
	private String fullyQualifiedValueClassName;


	// ********** constructors **********
	public SourceXmlAnyElementAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		this(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(annotatedElement, DECLARATION_ANNOTATION_ADAPTER));
	}

	public SourceXmlAnyElementAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa, AnnotationAdapter annotationAdapter) {
		super(parent, annotatedElement, daa, annotationAdapter);
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
		return JAXB.XML_ANY_ELEMENT;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.lax = this.buildLax(astAnnotation);
		this.laxTextRange = this.buildLaxTextRange(astAnnotation);
		this.value = this.buildValue(astAnnotation);
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		this.fullyQualifiedValueClassName = this.buildFullyQualifiedValueClassName(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncLax(this.buildLax(astAnnotation));
		this.laxTextRange = this.buildLaxTextRange(astAnnotation);
		this.syncValue(this.buildValue(astAnnotation));
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		this.syncFullyQualifiedValueClassName(this.buildFullyQualifiedValueClassName(astAnnotation));
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
		if (ObjectTools.notEquals(this.lax, lax)) {
			this.lax = lax;
			this.laxAdapter.setValue(lax);
		}
	}

	private void syncLax(Boolean astLax) {
		Boolean old = this.lax;
		this.lax = astLax;
		this.firePropertyChanged(LAX_PROPERTY, old, astLax);
	}

	private Boolean buildLax(Annotation astAnnotation) {
		return this.laxAdapter.getValue(astAnnotation);
	}

	public TextRange getLaxTextRange() {
		return this.laxTextRange;
	}

	private TextRange buildLaxTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.laxDeclarationAdapter, astAnnotation);
	}

	// ***** value
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		if (ObjectTools.notEquals(this.value, value)) {
			this.value = value;
			this.valueAdapter.setValue(value);
		}
	}

	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}

	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astAnnotation);
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

	private String buildFullyQualifiedValueClassName(Annotation astAnnotation) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astAnnotation));
	}
}
