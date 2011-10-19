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
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementRef
 */
public final class SourceXmlElementRefAnnotation
		extends SourceAnnotation
		implements XmlElementRefAnnotation {
	
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENT_REF);
	
	
	private String fullyQualifiedTypeName;
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	
	private final DeclarationAnnotationElementAdapter<Boolean> requiredDeclarationAdapter;
	private final AnnotationElementAdapter<Boolean> requiredAdapter;
	private Boolean required;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	
	
	public static SourceXmlElementRefAnnotation buildSourceXmlElementRefAnnotation(
			JavaResourceAnnotatedElement parent, 
			AnnotatedElement annotatedElement) {
		
		return new SourceXmlElementRefAnnotation(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER);
	}
	
	public static SourceXmlElementRefAnnotation buildNestedSourceXmlElementRefAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement annotatedElement, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		return new SourceXmlElementRefAnnotation(parent, annotatedElement, idaa);
	}
	
	
	private SourceXmlElementRefAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement annotatedElement, 
			DeclarationAnnotationAdapter daa) {
		
		this(parent, annotatedElement, daa, new ElementAnnotationAdapter(annotatedElement, daa));
	}
	
	private SourceXmlElementRefAnnotation(
			JavaResourceNode parent, 
			AnnotatedElement annotatedElement, 
			IndexedDeclarationAnnotationAdapter idaa) {
		
		this(parent, annotatedElement, idaa, new ElementIndexedAnnotationAdapter(annotatedElement, idaa));
	}
	
	private SourceXmlElementRefAnnotation(
			JavaResourceNode parent,
			AnnotatedElement annotatedElement, 
			DeclarationAnnotationAdapter daa,
			AnnotationAdapter annotationAdapter) {
		
		super(parent, annotatedElement, daa, annotationAdapter);
		this.nameDeclarationAdapter = this.buildNameDeclarationAdapter();
		this.nameAdapter = this.buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.namespaceDeclarationAdapter = this.buildNamespaceDeclarationAdapter();
		this.namespaceAdapter = this.buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.requiredDeclarationAdapter = this.buildRequiredAdapter(daa);
		this.requiredAdapter = this.buildShortCircuitBooleanElementAdapter(this.requiredDeclarationAdapter);
		this.typeDeclarationAdapter = this.buildTypeDeclarationAdapter();
		this.typeAdapter = this.buildAnnotationElementAdapter(this.typeDeclarationAdapter);
	}
	
	
	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JAXB.XML_ELEMENT_REF__NAME);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildNamespaceDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(this.daa, JAXB.XML_ELEMENT_REF__NAMESPACE);
	}

	private DeclarationAnnotationElementAdapter<Boolean> buildRequiredAdapter(DeclarationAnnotationAdapter daa) {
		return ConversionDeclarationAnnotationElementAdapter.forBooleans(daa, JAXB.XML_ELEMENT_REF__REQUIRED);
	}

	private DeclarationAnnotationElementAdapter<String> buildTypeDeclarationAdapter() {
		return buildAnnotationElementAdapter(this.daa, JAXB.XML_ELEMENT_REF__TYPE, SimpleTypeStringExpressionConverter.instance());
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
		return JAXB.XML_ELEMENT_REF;
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.namespace = this.buildNamespace(astRoot);
		this.required = this.buildRequired(astRoot);
		this.type = this.buildType(astRoot);
		this.fullyQualifiedTypeName = this.buildFullyQualifiedTypeName(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncNamespace(this.buildNamespace(astRoot));
		this.syncRequired(this.buildRequired(astRoot));
		this.syncType(this.buildType(astRoot));
		this.syncFullyQualifiedTypeName(this.buildFullyQualifiedTypeName(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlElementRefAnnotation implementation **********

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

	private String buildName(CompilationUnit astRoot) {
		return this.nameAdapter.getValue(astRoot);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.nameDeclarationAdapter, pos, astRoot);
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

	private String buildNamespace(CompilationUnit astRoot) {
		return this.namespaceAdapter.getValue(astRoot);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.namespaceDeclarationAdapter, astRoot);
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.namespaceDeclarationAdapter, pos, astRoot);
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

	private Boolean buildRequired(CompilationUnit astRoot) {
		return this.requiredAdapter.getValue(astRoot);
	}
	
	public TextRange getRequiredTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.requiredDeclarationAdapter, astRoot);
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

	private String buildType(CompilationUnit astRoot) {
		return this.typeAdapter.getValue(astRoot);
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(this.typeDeclarationAdapter, astRoot);
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

	private String buildFullyQualifiedTypeName(CompilationUnit astRoot) {
		return (this.type == null) ? null : ASTTools.resolveFullyQualifiedName(this.typeAdapter.getExpression(astRoot));
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