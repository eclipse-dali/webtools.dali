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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
public final class SourceXmlJavaTypeAdapterAnnotation
	extends SourceAnnotation
	implements XmlJavaTypeAdapterAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_JAVA_TYPE_ADAPTERS);

	private final DeclarationAnnotationElementAdapter<String> valueDeclarationAdapter;
	private final AnnotationElementAdapter<String> valueAdapter;
	private String value;
	private String fullyQualifiedValue;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private String fullyQualifiedType;
	
	/*
	 * We want these events fired when the fq classes change by themselves, not as a result
	 * of the non-fq classes changing.
	 */
	private boolean suppressFQClassesEventNotification = false;
	
	
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
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.value = buildValue(astRoot);
		this.fullyQualifiedValue = buildFullyQualifiedValue(astRoot);
		this.type = buildType(astRoot);
		this.fullyQualifiedType = buildFullyQualifiedType(astRoot);
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		syncValue(buildValue(astRoot));
		syncType(buildType(astRoot));
		syncFullyQualifiedValue(buildFullyQualifiedValue(astRoot));
		syncFullyQualifiedType(buildFullyQualifiedType(astRoot));
		
		this.suppressFQClassesEventNotification = false;
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
		if (this.attributeValueHasChanged(this.value, value)) {
			this.value = value;
			this.suppressFQClassesEventNotification = true;
			this.valueAdapter.setValue(value);
		}
	}
	
	private void syncValue(String astValue) {
		String old = this.value;
		this.value = astValue;
		this.suppressFQClassesEventNotification |= StringTools.stringsAreEqual(old, astValue);
		this.firePropertyChanged(VALUE_PROPERTY, old, astValue);
	}
	
	private String buildValue(CompilationUnit astRoot) {
		return this.valueAdapter.getValue(astRoot);
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.valueDeclarationAdapter, astRoot);
	}
	
	public String getFullyQualifiedValue() {
		return this.fullyQualifiedValue;
	}
	
	private void syncFullyQualifiedValue(String name) {
		String old = this.fullyQualifiedValue;
		this.fullyQualifiedValue = name;
		if (! this.suppressFQClassesEventNotification) {
			this.firePropertyChanged(FULLY_QUALIFIED_VALUE_PROPERTY, old, name);
		}
	}
	
	private String buildFullyQualifiedValue(CompilationUnit astRoot) {
		return (this.value == null) ? null : ASTTools.resolveFullyQualifiedName(this.valueAdapter.getExpression(astRoot));
	}
	
	// ***** type
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		if (this.attributeValueHasChanged(this.type, type)) {
			this.type = type;
			this.suppressFQClassesEventNotification = true;
			this.typeAdapter.setValue(type);
		}
	}
	
	private void syncType(String astType) {
		String old = this.type;
		this.type = astType;
		this.suppressFQClassesEventNotification |= StringTools.stringsAreEqual(old, astType);
		this.firePropertyChanged(TYPE_PROPERTY, old, astType);
	}
	
	private String buildType(CompilationUnit astRoot) {
		return this.typeAdapter.getValue(astRoot);
	}
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.typeDeclarationAdapter, astRoot);
	}
	
	public String getFullyQualifiedType() {
		return this.fullyQualifiedType;
	}
	
	private void syncFullyQualifiedType(String name) {
		String old = this.fullyQualifiedType;
		this.fullyQualifiedType = name;
		if (! this.suppressFQClassesEventNotification) {
			this.firePropertyChanged(FULLY_QUALIFIED_TYPE_PROPERTY, old, name);
		}
	}
	
	private String buildFullyQualifiedType(CompilationUnit astRoot) {
		return (this.type == null) ? null : ASTTools.resolveFullyQualifiedName(this.typeAdapter.getExpression(astRoot));
	}
	
	
	//*********** NestableAnnotation implementation ****************
	
	/**
	 * convenience implementation of method from NestableAnnotation interface
	 * for subclasses
	 */
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
