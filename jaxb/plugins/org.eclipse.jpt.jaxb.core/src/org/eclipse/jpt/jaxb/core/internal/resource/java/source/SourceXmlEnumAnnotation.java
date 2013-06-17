/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;

/**
 * javax.xml.bind.annotation.XmlEnum
 */
public final class SourceXmlEnumAnnotation
		extends SourceAnnotation
		implements XmlEnumAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ENUM);

	private static final DeclarationAnnotationElementAdapter<String> VALUE_ADAPTER = buildValueAdapter();
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private TextRange valueTextRange;
	private TextRange valueValidationTextRange;
	
	private String fullyQualifiedValueClassName;

	public SourceXmlEnumAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
		this.valueAdapter = this.buildAnnotationElementAdapter(VALUE_ADAPTER);
	}

	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_ENUM;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.value = buildValue(astAnnotation);
		this.valueTextRange = buildValueTextRange(astAnnotation);
		this.valueValidationTextRange = buildValueValidationTextRange(astAnnotation);
		this.fullyQualifiedValueClassName = buildFullyQualifiedValueClassName(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncValue(buildValue(astAnnotation));
		this.valueTextRange = buildValueTextRange(astAnnotation);
		this.valueValidationTextRange = buildValueValidationTextRange(astAnnotation);
		syncFullyQualifiedValueClassName(buildFullyQualifiedValueClassName(astAnnotation));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}


	// ********** XmlEnumAnnotation implementation **********

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
	
	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(VALUE_ADAPTER, astAnnotation);
	}
	
	private TextRange buildValueValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(VALUE_ADAPTER, astAnnotation);
	}
	
	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}
	
	public TextRange getValueValidationTextRange() {
		return this.valueValidationTextRange;
	}
	
	public boolean valueTouches(int pos) {
		return this.textRangeTouches(this.valueTextRange, pos);
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
	
	
	//*********** static methods ****************
	
	private static DeclarationAnnotationElementAdapter<String> buildValueAdapter() {
		return buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ENUM__VALUE, SimpleTypeStringExpressionConverter.instance());
	}
	
	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}
}
