/*******************************************************************************
 *  Copyright (c) 2010, 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
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
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlSchemaType
 */
public class SourceXmlSchemaTypeAnnotation
		extends SourceAnnotation
		implements XmlSchemaTypeAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_SCHEMA_TYPE);
	public static final DeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_SCHEMA_TYPES);
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;
	private TextRange nameValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	private TextRange namespaceValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private String fullyQualifiedType;
	private TextRange typeTextRange;
	
	
	// ********** constructors **********
	
	public static SourceXmlSchemaTypeAnnotation buildSourceXmlSchemaTypeAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement, 
			int index) {
		
		IndexedDeclarationAnnotationAdapter idaa = buildXmlSchemaTypeDeclarationAnnotationAdapter(index);
		IndexedAnnotationAdapter iaa = buildXmlSchemaTypeAnnotationAdapter(annotatedElement, idaa);
		return new SourceXmlSchemaTypeAnnotation(
			parent,
			annotatedElement,
			idaa,
			iaa);
	}
	
	private SourceXmlSchemaTypeAnnotation(
			JavaResourceAnnotatedElement parent,
			AnnotatedElement annotatedElement, 
			IndexedDeclarationAnnotationAdapter daa,
			IndexedAnnotationAdapter annotationAdapter) {
		
		super(parent, annotatedElement, daa, annotationAdapter);
		this.nameDeclarationAdapter = buildNameAdapter(daa);
		this.nameAdapter = this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.namespaceDeclarationAdapter = buildNamespaceAdapter(daa);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.typeDeclarationAdapter = buildTypeAdapter(daa);
		this.typeAdapter = this.buildAnnotationElementAdapter(this.typeDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildNameAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_SCHEMA_TYPE__NAME);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(daa, JAXB.XML_SCHEMA_TYPE__NAMESPACE);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildTypeAdapter(DeclarationAnnotationAdapter daa) {
		return buildAnnotationElementAdapter(daa, JAXB.XML_SCHEMA_TYPE__TYPE, 
				SimpleTypeStringExpressionConverter.instance());
	}
	
	static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationAdapter annotationAdapter, String elementName, 
			ExpressionConverter<String> converter) {
		
		return new ConversionDeclarationAnnotationElementAdapter<String>(
				annotationAdapter, elementName, converter);
	}
	
	protected AnnotationElementAdapter<String> buildAnnotationElementAdapter(
			DeclarationAnnotationElementAdapter<String> daea) {
		
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_SCHEMA_TYPE;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.name = buildName(astAnnotation);
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		this.type = buildType(astAnnotation);
		this.fullyQualifiedType = buildFullyQualifiedType(astAnnotation);
		this.typeTextRange = buildTypeTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncName(buildName(astAnnotation));
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		syncNamespace(buildNamespace(astAnnotation));
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		syncType(buildType(astAnnotation));
		syncFullyQualifiedType(buildFullyQualifiedType(astAnnotation));
		this.typeTextRange = this.buildTypeTextRange(astAnnotation);
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
	
	// **************** XmlSchemaTypeAnnotation impl **************************
	
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
		return getAnnotationElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildNameValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.nameDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getNameTextRange() {
		return this.nameTextRange;
	}
	
	public TextRange getNameValidationTextRange() {
		return this.nameValidationTextRange;
	}
	
	public boolean nameTouches(int pos) {
		return textRangeTouches(this.nameTextRange, pos);
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
		return getAnnotationElementTextRange(this.namespaceDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildNamespaceValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.namespaceDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getNamespaceTextRange() {
		return this.namespaceTextRange;
	}
	
	public TextRange getNamespaceValidationTextRange() {
		return this.namespaceValidationTextRange;
	}
	
	public boolean namespaceTouches(int pos) {
		return this.textRangeTouches(this.namespaceTextRange, pos);
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
	
	public String getFullyQualifiedType() {
		return this.fullyQualifiedType;
	}
	
	private void syncFullyQualifiedType(String name) {
		String old = this.fullyQualifiedType;
		this.fullyQualifiedType = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_PROPERTY, old, name);
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

	private static IndexedAnnotationAdapter buildXmlSchemaTypeAnnotationAdapter(AnnotatedElement annotatedElement, IndexedDeclarationAnnotationAdapter idaa) {
		return new ElementIndexedAnnotationAdapter(annotatedElement, idaa);
	}

	private static IndexedDeclarationAnnotationAdapter buildXmlSchemaTypeDeclarationAnnotationAdapter(int index) {
		IndexedDeclarationAnnotationAdapter idaa = 
			new CombinationIndexedDeclarationAnnotationAdapter(
				DECLARATION_ANNOTATION_ADAPTER,
				CONTAINER_DECLARATION_ANNOTATION_ADAPTER,
				index,
				JAXB.XML_SCHEMA_TYPE);
		return idaa;
	}
}
