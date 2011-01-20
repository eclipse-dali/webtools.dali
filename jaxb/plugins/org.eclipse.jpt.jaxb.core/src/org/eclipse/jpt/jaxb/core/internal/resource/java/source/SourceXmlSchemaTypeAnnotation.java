/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.source;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.utility.jdt.ASTTools;
import org.eclipse.jpt.core.internal.utility.jdt.AnnotatedElementAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.CombinationIndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ConversionDeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.ElementIndexedAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleDeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.utility.jdt.SimpleTypeStringExpressionConverter;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.core.utility.jdt.IndexedAnnotationAdapter;
import org.eclipse.jpt.core.utility.jdt.IndexedDeclarationAnnotationAdapter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlSchemaType
 */
public class SourceXmlSchemaTypeAnnotation
		extends SourceAnnotation<AnnotatedElement>
		implements XmlSchemaTypeAnnotation {
	
	public static final SimpleDeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);
	public static final SimpleDeclarationAnnotationAdapter CONTAINER_DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_SCHEMA_TYPES);
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	
	private final DeclarationAnnotationElementAdapter<String> typeDeclarationAdapter;
	private final AnnotationElementAdapter<String> typeAdapter;
	private String type;
	private String fullyQualifiedType;
	
	
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
		return ANNOTATION_NAME;
	}
	
	public void initialize(CompilationUnit astRoot) {
		this.name = buildName(astRoot);
		this.namespace = buildNamespace(astRoot);
		this.type = buildType(astRoot);
		this.fullyQualifiedType = buildFullyQualifiedType(astRoot);
	}
	
	public void synchronizeWith(CompilationUnit astRoot) {
		syncName(buildName(astRoot));
		syncNamespace(buildNamespace(astRoot));
		syncType(buildType(astRoot));
		syncFullyQualifiedType(buildFullyQualifiedType(astRoot));
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
	
	public String getFullyQualifiedType() {
		return this.fullyQualifiedType;
	}
	
	private void syncFullyQualifiedType(String name) {
		String old = this.fullyQualifiedType;
		this.fullyQualifiedType = name;
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_PROPERTY, old, name);
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
