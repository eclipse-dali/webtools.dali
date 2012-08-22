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
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlElement
 */
public final class SourceXmlElementAnnotation
		extends SourceAnnotation
		implements XmlElementAnnotation {
	
	private static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENT);
	
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	
	private final DeclarationAnnotationElementAdapter<Boolean> nillableDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> nillableAdapter;
	private Boolean nillable;
	private TextRange nillableTextRange;
	
	private final DeclarationAnnotationElementAdapter<Boolean> requiredDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> requiredAdapter;
	private Boolean required;
	private TextRange requiredTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> defaultValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> defaultValueAdapter;
	private String defaultValue;
	private TextRange defaultValueTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private String fullyQualifiedTypeName;
	private TextRange typeTextRange;
	
	
	public static SourceXmlElementAnnotation buildSourceXmlElementAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement) {
		
		return new SourceXmlElementAnnotation(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public static SourceXmlElementAnnotation buildNestedSourceXmlElementAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement annotatedElement, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceXmlElementAnnotation(parent, annotatedElement, idaa);
	}
	
	
	private SourceXmlElementAnnotation(JavaResourceNode parent, AnnotatedElement annotatedElement, DeclarationAnnotationAdapter daa) {
		this(parent, annotatedElement, daa, new ElementAnnotationAdapter(annotatedElement, daa));
	}
	
	private SourceXmlElementAnnotation(JavaResourceNode parent, AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		this(parent, annotatedElement, idaa, new ElementIndexedAnnotationAdapter(annotatedElement, idaa));
	}
	
	private SourceXmlElementAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement annotatedElement, 
			DeclarationAnnotationAdapter daa, 
			AnnotationAdapter annotationAdapter) {
		
		super(parent, annotatedElement, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameAdapter(daa);
		this.nameAdapter = this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.namespaceDeclarationAdapter = this.buildNamespaceAdapter(daa);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.nillableDeclarationAdapter = this.buildNillableAdapter(daa);
		this.nillableAdapter = this.buildShortCircuitBooleanElementAdapter(this.nillableDeclarationAdapter);
		this.requiredDeclarationAdapter = this.buildRequiredAdapter(daa);
		this.requiredAdapter = this.buildShortCircuitBooleanElementAdapter(this.requiredDeclarationAdapter);
		this.defaultValueDeclarationAdapter = this.buildDefaultValueAdapter(daa);
		this.defaultValueAdapter = this.buildAnnotationElementAdapter(this.defaultValueDeclarationAdapter);
		this.typeDeclarationAdapter = this.buildTypeAdapter(daa);
		this.typeAdapter = this.buildAnnotationElementAdapter(this.typeDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ELEMENT__NAME);
	}

	private DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ELEMENT__NAMESPACE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildNillableAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ELEMENT__NILLABLE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildRequiredAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ELEMENT__REQUIRED);
	}

	private DeclarationAnnotationElementAdapter<String> buildDefaultValueAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_ELEMENT__DEFAULT_VALUE);
	}

	private DeclarationAnnotationElementAdapter<String> buildTypeAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_ELEMENT__TYPE, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	private AnnotationElementAdapter<Boolean> buildShortCircuitBooleanElementAdapter(DeclarationAnnotationElementAdapter<Boolean> daea) {
		return new AnnotatedElementAnnotationElementAdapter<Boolean>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return JAXB.XML_ELEMENT;
	}

	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = this.buildName(astAnnotation);
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.namespace = this.buildNamespace(astAnnotation);
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.nillable = this.buildNillable(astAnnotation);
		this.nillableTextRange = this.buildNillableTextRange(astAnnotation);
		this.required = this.buildRequired(astAnnotation);
		this.requiredTextRange = this.buildRequiredTextRange(astAnnotation);
		this.defaultValue = this.buildDefaultValue(astAnnotation);
		this.defaultValueTextRange = this.buildDefaultValueTextRange(astAnnotation);
		this.type = this.buildType(astAnnotation);
		this.fullyQualifiedTypeName = this.buildFullyQualifiedTypeName(astAnnotation);
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
	}

	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		this.syncName(this.buildName(astAnnotation));
		this.nameTextRange = this.buildNameTextRange(astAnnotation);
		this.syncNamespace(this.buildNamespace(astAnnotation));
		this.namespaceTextRange = this.buildNamespaceTextRange(astAnnotation);
		this.syncNillable(this.buildNillable(astAnnotation));
		this.nillableTextRange = this.buildNillableTextRange(astAnnotation);
		this.syncRequired(this.buildRequired(astAnnotation));
		this.requiredTextRange = this.buildRequiredTextRange(astAnnotation);
		this.syncDefaultValue(this.buildDefaultValue(astAnnotation));
		this.defaultValueTextRange = this.buildDefaultValueTextRange(astAnnotation);
		this.syncType(this.buildType(astAnnotation));
		this.syncFullyQualifiedTypeName(this.buildFullyQualifiedTypeName(astAnnotation));
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlElementAnnotation implementation **********

	// ***** name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (this.attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}

	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		this.firePropertyChanged(NAME_PROPERTY, old, astName);
	}

	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}

	private TextRange buildNameTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}

	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}
	
	public boolean nameTouches(int pos) {
		return this.textRangeTouches(this.nameTextRange, pos);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		if (this.attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}

	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}

	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}

	private TextRange buildNamespaceTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.namespaceDeclarationAdapter, astAnnotation);
	}

	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
	}
	
	
	// ***** nillable
	public Boolean getNillable() {
		return this.nillable;
	}

	public void setNillable(Boolean nillable) {
		if (this.attributeValueHasChanged(this.nillable, nillable)) {
			this.nillable = nillable;
			this.nillableAdapter.setValue(nillable);
		}
	}

	private void syncNillable(Boolean astNillable) {
		Boolean old = this.nillable;
		this.nillable = astNillable;
		this.firePropertyChanged(NILLABLE_PROPERTY, old, astNillable);
	}

	private Boolean buildNillable(Annotation astAnnotation) {
		return this.nillableAdapter.getValue(astAnnotation);
	}

	private TextRange buildNillableTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.nillableDeclarationAdapter, astAnnotation);
	}

	public TextRange getNillableTextRange() {
		return this.nillableTextRange;
	}


	// ***** required
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		if (this.attributeValueHasChanged(this.required, required)) {
			this.required = required;
			this.requiredAdapter.setValue(required);
		}
	}

	private void syncRequired(Boolean astRequired) {
		Boolean old = this.required;
		this.required = astRequired;
		this.firePropertyChanged(REQUIRED_PROPERTY, old, astRequired);
	}

	private Boolean buildRequired(Annotation astAnnotation) {
		return this.requiredAdapter.getValue(astAnnotation);
	}

	private TextRange buildRequiredTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.requiredDeclarationAdapter, astAnnotation);
	}

	public TextRange getRequiredTextRange() {
		return this.requiredTextRange;
	}

	// ***** defaultValue
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		if (this.attributeValueHasChanged(this.defaultValue, defaultValue)) {
			this.defaultValue = defaultValue;
			this.defaultValueAdapter.setValue(defaultValue);
		}
	}

	private void syncDefaultValue(String astDefaultValue) {
		String old = this.defaultValue;
		this.defaultValue = astDefaultValue;
		this.firePropertyChanged(DEFAULT_VALUE_PROPERTY, old, astDefaultValue);
	}

	private String buildDefaultValue(Annotation astAnnotation) {
		return this.defaultValueAdapter.getValue(astAnnotation);
	}

	private TextRange buildDefaultValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.defaultValueDeclarationAdapter, astAnnotation);
	}

	public TextRange getDefaultValueTextRange() {
		return this.defaultValueTextRange;
	}

	// ***** type
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		if (this.attributeValueHasChanged(this.type, type)) {
			this.type = type;
			this.typeAdapter.setValue(type);
		}
	}

	private void syncType(String astType) {
		String old = this.type;
		this.type = astType;
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
	
	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName() {
		return this.fullyQualifiedTypeName;
	}

	private void syncFullyQualifiedTypeName(String name) {
		String old = this.fullyQualifiedTypeName;
		this.fullyQualifiedTypeName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedTypeName(Annotation astAnnotation) {
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
}
