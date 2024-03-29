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
import org.eclipse.jpt.common.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
public final class SourceXmlJavaTypeAdapterAnnotation
	extends SourceAnnotation
	implements XmlJavaTypeAdapterAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_JAVA_TYPE_ADAPTER);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_JAVA_TYPE_ADAPTERS);

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private String fullyQualifiedValue;
	private TextRange valueTextRange;
	
	/*
	 * We want this event fired when the fq class changes by itself, not as a result
	 * of the non-fq class changing.
	 */
	private boolean suppressFQValueEventNotification = false;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private String fullyQualifiedType;
	private TextRange typeTextRange;
	
	/*
	 * We want this event fired when the fq class changes by itself, not as a result
	 * of the non-fq class changing.
	 */
	private boolean suppressFQTypeEventNotification = false;
	
	
	// ********** constructors **********
	public static SourceXmlJavaTypeAdapterAnnotation buildSourceXmlJavaTypeAdapterAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement, int index) {
		IndexedDeclarationAnnotationAdapter idaa = buildXmlJavaTypeAdapterDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildXmlJavaTypeAdapterAnnotationAdapter(annotatedElement, idaa);
		return new SourceXmlJavaTypeAdapterAnnotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}

	private SourceXmlJavaTypeAdapterAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement,
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		super(parent, annotatedElement, daa, annotationAdapter);
		this.valueDeclarationAdapter = buildValueAdapter(daa);
		this.valueAdapter = this.buildAnnotationElementAdapter(this.valueDeclarationAdapter);
		this.typeDeclarationAdapter = buildTypeAdapter(daa);
		this.typeAdapter = this.buildAnnotationElementAdapter(this.typeDeclarationAdapter);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildValueAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_JAVA_TYPE_ADAPTER__VALUE, SimpleTypeStringExpressionConverter.instance());
	}
	
	private DeclarationAnnotationElementAdapter<String> buildTypeAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_JAVA_TYPE_ADAPTER__TYPE, SimpleTypeStringExpressionConverter.instance());
	}
	
	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_JAVA_TYPE_ADAPTER;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.value = buildValue(astAnnotation);
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		this.fullyQualifiedValue = buildFullyQualifiedValue(astAnnotation);
		this.type = buildType(astAnnotation);
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
		this.fullyQualifiedType = buildFullyQualifiedType(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncValue(buildValue(astAnnotation));
		this.valueTextRange = this.buildValueTextRange(astAnnotation);
		syncType(buildType(astAnnotation));
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
		syncFullyQualifiedValue(buildFullyQualifiedValue(astAnnotation));
		syncFullyQualifiedType(buildFullyQualifiedType(astAnnotation));
		
		this.suppressFQValueEventNotification = false;
		this.suppressFQTypeEventNotification = false;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
	
	
	// ********** XmlJavaTypeAdapterAnnotation implementation **********
	
	// ***** value
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		if (ObjectTools.notEquals(this.value, value)) {
			this.value = value;
			this.suppressFQValueEventNotification = true;
			this.valueAdapter.setValue(value);
		}
	}
	
	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.suppressFQValueEventNotification |= ! ObjectTools.equals(old, astValue);
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}
	
	private String buildValue(Annotation astAnnotation) {
		return this.valueAdapter.getValue(astAnnotation);
	}

	private TextRange buildValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astAnnotation);
	}

	public TextRange getValueTextRange() {
		return this.valueTextRange;
	}
	
	public String getFullyQualifiedValue() {
		return this.fullyQualifiedValue;
	}
	
	private void syncFullyQualifiedValue(String name) {
		String old = this.fullyQualifiedValue;
		this.fullyQualifiedValue = name;
		if (! this.suppressFQValueEventNotification) {
			this.firePropertyChanged(FULLY_QUALIFIED_VALUE_PROPERTY, old, name);
		}
	}
	
	private String buildFullyQualifiedValue(Annotation astAnnotation) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astAnnotation));
	}
	
	// ***** type
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		if (ObjectTools.notEquals(this.type, type)) {
			this.type = type;
			this.suppressFQTypeEventNotification = true;
			this.typeAdapter.setValue(type);
		}
	}
	
	private void syncType(String astType) {
		String old = this.type;
		this.type = astType;
		this.suppressFQTypeEventNotification |= ! ObjectTools.equals(old, astType);
		this.firePropertyChanged(TYPE_PROPERTY, old, astType);
	}
	
	private String buildType(Annotation astAnnotation) {
		return this.typeAdapter.getValue(astAnnotation);
	}

	private TextRange buildTypeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.typeDeclarationAdapter, astAnnotation);
	}

	public TextRange getTypeTextRange() {
		return this.typeTextRange;
	}
	
	public String getFullyQualifiedType() {
		return this.fullyQualifiedType;
	}
	
	private void syncFullyQualifiedType(String name) {
		String old = this.fullyQualifiedType;
		this.fullyQualifiedType = name;
		if (! this.suppressFQTypeEventNotification) {
			this.firePropertyChanged(FULLY_QUALIFIED_TYPE_PROPERTY, old, name);
		}
	}
	
	private String buildFullyQualifiedType(Annotation astAnnotation) {
		return (this.type == null) ? null : ASTTools.resolveFullyQualifiedName(this.typeAdapter.getExpression(astAnnotation));
	}
	
	
	//*********** NestableAnnotation implementation ****************
	
	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
	@Override
	public void moveAnnotation(int newIndex) {
		this.getIndexedAnnotationAdapter().moveAnnotation(newIndex);
	}
	
	private IndexedAnnotationAdapter getIndexedAnnotationAdapter() {
		return (IndexedAnnotationAdapter) this.annotationAdapter;
	}
	
	
	// ********** static methods **********

	private static IndexedAnnotationAdapter buildXmlJavaTypeAdapterAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildXmlJavaTypeAdapterDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				JAXB.XML_JAVA_TYPE_ADAPTER);
		return idaa;
	}
}
