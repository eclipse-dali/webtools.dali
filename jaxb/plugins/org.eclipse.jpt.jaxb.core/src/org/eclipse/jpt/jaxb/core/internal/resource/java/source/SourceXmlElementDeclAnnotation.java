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
import org.eclipse.jdt.core.dom.CompilationUnit;
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
import org.eclipse.jpt.common.core.utility.jdt.AnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationAdapter;
import org.eclipse.jpt.common.core.utility.jdt.DeclarationAnnotationElementAdapter;
import org.eclipse.jpt.common.core.utility.jdt.ExpressionConverter;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementDecl
 */
public final class SourceXmlElementDeclAnnotation
		extends SourceAnnotation
		implements XmlElementDeclAnnotation {
	
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(JAXB.XML_ELEMENT_DECL);
	
	private final DeclarationAnnotationElementAdapter<String> scopeDeclarationAdapter;
	private final AnnotationElementAdapter<String> scopeAdapter;
	private String scope;
	private String fullyQualifiedScopeClassName;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	
	private final DeclarationAnnotationElementAdapter<String> substitutionHeadNamespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> substitutionHeadNamespaceAdapter;
	private String substitutionHeadNamespace;
	
	private final DeclarationAnnotationElementAdapter<String> substitutionHeadNameDeclarationAdapter;
	private final AnnotationElementAdapter<String> substitutionHeadNameAdapter;
	private String substitutionHeadName;
	
	private final DeclarationAnnotationElementAdapter<String> defaultValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> defaultValueAdapter;
	private String defaultValue;
	
	
	// ********** constructors **********

	public SourceXmlElementDeclAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(annotatedElement, DECLARATION_ANNOTATION_ADAPTER));
		this.scopeDeclarationAdapter = buildScopeDeclarationAdapter();
		this.scopeAdapter = buildAnnotationElementAdapter(this.scopeDeclarationAdapter);
		this.namespaceDeclarationAdapter = buildNamespaceDeclarationAdapter();
		this.namespaceAdapter = buildAnnotationElementAdapter(this.namespaceDeclarationAdapter);
		this.nameDeclarationAdapter = buildNameDeclarationAdapter();
		this.nameAdapter = buildAnnotationElementAdapter(this.nameDeclarationAdapter);
		this.substitutionHeadNamespaceDeclarationAdapter = buildSubstitutionHeadNamespaceDeclarationAdapter();
		this.substitutionHeadNamespaceAdapter = buildAnnotationElementAdapter(this.substitutionHeadNamespaceDeclarationAdapter);
		this.substitutionHeadNameDeclarationAdapter = buildSubstitutionHeadNameDeclarationAdapter();
		this.substitutionHeadNameAdapter = buildAnnotationElementAdapter(this.substitutionHeadNameDeclarationAdapter);
		this.defaultValueDeclarationAdapter = buildDefaultValueDeclarationAdapter();
		this.defaultValueAdapter = buildAnnotationElementAdapter(this.defaultValueDeclarationAdapter);
	}
	
	
	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildScopeDeclarationAdapter() {
		return buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SCOPE, SimpleTypeStringExpressionConverter.instance());
	}

	private DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildNamespaceDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__NAMESPACE);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__NAME);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildSubstitutionHeadNamespaceDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildSubstitutionHeadNameDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME);
	}
	
	private DeclarationAnnotationElementAdapter<String> buildDefaultValueDeclarationAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__DEFAULT_VALUE);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_ELEMENT_DECL;
	}
	
	@Override
	public void initialize(Annotation astAnnotation) {
		super.initialize(astAnnotation);
		this.scope = buildScope(astAnnotation);
		this.fullyQualifiedScopeClassName = buildFullyQualifiedScopeClassName(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.name = buildName(astAnnotation);
		this.substitutionHeadNamespace = buildSubstitutionHeadNamespace(astAnnotation);
		this.substitutionHeadName = buildSubstitutionHeadName(astAnnotation);
		this.defaultValue = buildDefaultValue(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncScope(buildScope(astAnnotation));
		syncFullyQualifiedScopeClassName(buildFullyQualifiedScopeClassName(astAnnotation));
		syncNamespace(buildNamespace(astAnnotation));
		syncName(buildName(astAnnotation));
		syncSubstitutionHeadNamespace(buildSubstitutionHeadNamespace(astAnnotation));
		syncSubstitutionHeadName(buildSubstitutionHeadName(astAnnotation));
		syncDefaultValue(buildDefaultValue(astAnnotation));
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}
	
	
	// ***** scope *****
	
	public String getScope() {
		return this.scope;
	}
	
	public void setScope(String scope) {
		if (attributeValueHasChanged(this.scope, scope)) {
			this.scope = scope;
			this.scopeAdapter.setValue(scope);
		}
	}
	
	private void syncScope(String astScope) {
		String old = this.scope;
		this.scope = astScope;
		firePropertyChanged(SCOPE_PROPERTY, old, astScope);
	}
	
	private String buildScope(Annotation astAnnotation) {
		return this.scopeAdapter.getValue(astAnnotation);
	}
	
	public TextRange getScopeTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.scopeDeclarationAdapter, astRoot);
	}
	
	
	// ***** fully-qualified scope class name *****
	
	public String getFullyQualifiedScopeClassName() {
		return this.fullyQualifiedScopeClassName;
	}
	
	private void syncFullyQualifiedScopeClassName(String name) {
		String old = this.fullyQualifiedScopeClassName;
		this.fullyQualifiedScopeClassName = name;
		firePropertyChanged(FULLY_QUALIFIED_SCOPE_CLASS_NAME_PROPERTY, old, name);
	}
	
	private String buildFullyQualifiedScopeClassName(Annotation astAnnotation) {
		return (this.scope == null) ? null : ASTTools.resolveFullyQualifiedName(this.scopeAdapter.getExpression(astAnnotation));
	}
	
	
	// ***** namespace *****
	
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		if (attributeValueHasChanged(this.namespace, namespace)) {
			this.namespace = namespace;
			this.namespaceAdapter.setValue(namespace);
		}
	}
	
	private void syncNamespace(String astNamespace) {
		String old = this.namespace;
		this.namespace = astNamespace;
		firePropertyChanged(NAMESPACE_PROPERTY, old, astNamespace);
	}
	
	private String buildNamespace(Annotation astAnnotation) {
		return this.namespaceAdapter.getValue(astAnnotation);
	}
	
	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.namespaceDeclarationAdapter, astRoot);
	}
	
	public boolean namespaceTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.namespaceDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** name *****
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (attributeValueHasChanged(this.name, name)) {
			this.name = name;
			this.nameAdapter.setValue(name);
		}
	}
	
	private void syncName(String astName) {
		String old = this.name;
		this.name = astName;
		firePropertyChanged(NAME_PROPERTY, old, astName);
	}
	
	private String buildName(Annotation astAnnotation) {
		return this.nameAdapter.getValue(astAnnotation);
	}
	
	public TextRange getNameTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.nameDeclarationAdapter, astRoot);
	}
	
	public boolean nameTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.nameDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** substitutionHeadNamespace *****
	
	public String getSubstitutionHeadNamespace() {
		return this.substitutionHeadNamespace;
	}
	
	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		if (attributeValueHasChanged(this.substitutionHeadNamespace, substitutionHeadNamespace)) {
			this.substitutionHeadNamespace = substitutionHeadNamespace;
			this.substitutionHeadNamespaceAdapter.setValue(substitutionHeadNamespace);
		}
	}
	
	private void syncSubstitutionHeadNamespace(String astSubstitutionHeadNamespace) {
		String old = this.substitutionHeadNamespace;
		this.substitutionHeadNamespace = astSubstitutionHeadNamespace;
		firePropertyChanged(SUBSTITUTION_HEAD_NAMESPACE_PROPERTY, old, astSubstitutionHeadNamespace);
	}
	
	private String buildSubstitutionHeadNamespace(Annotation astAnnotation) {
		return this.substitutionHeadNamespaceAdapter.getValue(astAnnotation);
	}
	
	public TextRange getSubstitutionHeadNamespaceTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.substitutionHeadNamespaceDeclarationAdapter, astRoot);
	}
	
	public boolean substitutionHeadNamespaceTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.substitutionHeadNamespaceDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** substitutionHeadName *****
	
	public String getSubstitutionHeadName() {
		return this.substitutionHeadName;
	}
	
	public void setSubstitutionHeadName(String substitutionHeadName) {
		if (attributeValueHasChanged(this.substitutionHeadName, substitutionHeadName)) {
			this.substitutionHeadName = substitutionHeadName;
			this.substitutionHeadNameAdapter.setValue(substitutionHeadName);
		}
	}
	
	private void syncSubstitutionHeadName(String astSubstitutionHeadName) {
		String old = this.substitutionHeadName;
		this.substitutionHeadName = astSubstitutionHeadName;
		firePropertyChanged(SUBSTITUTION_HEAD_NAME_PROPERTY, old, astSubstitutionHeadName);
	}
	
	private String buildSubstitutionHeadName(Annotation astAnnotation) {
		return this.substitutionHeadNameAdapter.getValue(astAnnotation);
	}
	
	public TextRange getSubstitutionHeadNameTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.substitutionHeadNameDeclarationAdapter, astRoot);
	}
	
	public boolean substitutionHeadNameTouches(int pos, CompilationUnit astRoot) {
		return elementTouches(this.substitutionHeadNameDeclarationAdapter, pos, astRoot);
	}
	
	
	// ***** defaultValue *****
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		if (attributeValueHasChanged(this.defaultValue, defaultValue)) {
			this.defaultValue = defaultValue;
			this.defaultValueAdapter.setValue(defaultValue);
		}
	}
	
	private void syncDefaultValue(String astDefaultValue) {
		String old = this.defaultValue;
		this.defaultValue = astDefaultValue;
		firePropertyChanged(DEFAULT_VALUE_PROPERTY, old, astDefaultValue);
	}
	
	private String buildDefaultValue(Annotation astAnnotation) {
		return this.defaultValueAdapter.getValue(astAnnotation);
	}
	
	public TextRange getDefaultValueTextRange(CompilationUnit astRoot) {
		return getElementTextRange(this.defaultValueDeclarationAdapter, astRoot);
	}
}
