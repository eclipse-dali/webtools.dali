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
	implements XmlElementDeclAnnotation
{
	public static final DeclarationAnnotationAdapter DECLARATION_ANNOTATION_ADAPTER = new SimpleDeclarationAnnotationAdapter(ANNOTATION_NAME);

	private static final DeclarationAnnotationElementAdapter<String> NAME_ADAPTER = buildNameAdapter();
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;

	private static final DeclarationAnnotationElementAdapter<String> NAMESPACE_ADAPTER = buildNamespaceAdapter();
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;

	private static final DeclarationAnnotationElementAdapter<String> DEFAULT_VALUE_ADAPTER = buildDefaultValueAdapter();
	private final AnnotationElementAdapter<String> defaultValueAdapter;
	private String defaultValue;

	private static final DeclarationAnnotationElementAdapter<String> SCOPE_ADAPTER = buildScopeAdapter();
	private final AnnotationElementAdapter<String> scopeAdapter;
	private String scope;
	private String fullyQualifiedScopeClassName;

	private static final DeclarationAnnotationElementAdapter<String> SUBSTITUTION_HEAD_NAME_ADAPTER = buildSubstitutionHeadNameAdapter();
	private final AnnotationElementAdapter<String> substitutionHeadNameAdapter;
	private String substitutionHeadName;

	private static final DeclarationAnnotationElementAdapter<String> SUBSTITUTION_HEAD_NAMESPACE_ADAPTER = buildSubstitutionHeadNamespaceAdapter();
	private final AnnotationElementAdapter<String> substitutionHeadNamespaceAdapter;
	private String substitutionHeadNamespace;


	// ********** constructors **********

	public SourceXmlElementDeclAnnotation(JavaResourceAnnotatedElement parent, AnnotatedElement annotatedElement) {
		super(parent, annotatedElement, DECLARATION_ANNOTATION_ADAPTER, new ElementAnnotationAdapter(annotatedElement, DECLARATION_ANNOTATION_ADAPTER));
		this.nameAdapter = this.buildAnnotationElementAdapter(NAME_ADAPTER);
		this.namespaceAdapter = this.buildAnnotationElementAdapter(NAMESPACE_ADAPTER);
		this.defaultValueAdapter = this.buildAnnotationElementAdapter(DEFAULT_VALUE_ADAPTER);
		this.scopeAdapter = this.buildAnnotationElementAdapter(SCOPE_ADAPTER);
		this.substitutionHeadNameAdapter = this.buildAnnotationElementAdapter(SUBSTITUTION_HEAD_NAME_ADAPTER);
		this.substitutionHeadNamespaceAdapter = this.buildAnnotationElementAdapter(SUBSTITUTION_HEAD_NAMESPACE_ADAPTER);
	}

	private AnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationElementAdapter<String> daea) {
		return new AnnotatedElementAnnotationElementAdapter<String>(this.annotatedElement, daea);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	public void initialize(CompilationUnit astRoot) {
		this.name = this.buildName(astRoot);
		this.namespace = this.buildNamespace(astRoot);
		this.defaultValue = this.buildDefaultValue(astRoot);
		this.scope = this.buildScope(astRoot);
		this.fullyQualifiedScopeClassName = this.buildFullyQualifiedScopeClassName(astRoot);
		this.substitutionHeadName = this.buildSubstitutionHeadName(astRoot);
		this.substitutionHeadNamespace = this.buildSubstitutionHeadNamespace(astRoot);
	}

	public void synchronizeWith(CompilationUnit astRoot) {
		this.syncName(this.buildName(astRoot));
		this.syncNamespace(this.buildNamespace(astRoot));
		this.syncDefaultValue(this.buildDefaultValue(astRoot));
		this.syncScope(this.buildScope(astRoot));
		this.syncFullyQualifiedScopeClassName(this.buildFullyQualifiedScopeClassName(astRoot));
		this.syncSubstitutionHeadName(this.buildSubstitutionHeadName(astRoot));
		this.syncSubstitutionHeadNamespace(this.buildSubstitutionHeadNamespace(astRoot));
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlElementDeclAnnotation implementation **********

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
		return this.getElementTextRange(NAME_ADAPTER, astRoot);
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
		return this.getElementTextRange(NAMESPACE_ADAPTER, astRoot);
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

	private String buildDefaultValue(CompilationUnit astRoot) {
		return this.defaultValueAdapter.getValue(astRoot);
	}

	public TextRange getDefaultValueTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(DEFAULT_VALUE_ADAPTER, astRoot);
	}

	// ***** scope
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		if (this.attributeValueHasChanged(this.scope, scope)) {
			this.scope = scope;
			this.scopeAdapter.setValue(scope);
		}
	}

	private void syncScope(String astScope) {
		String old = this.scope;
		this.scope = astScope;
		this.firePropertyChanged(SCOPE_PROPERTY, old, astScope);
	}

	private String buildScope(CompilationUnit astRoot) {
		return this.scopeAdapter.getValue(astRoot);
	}

	public TextRange getScopeTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SCOPE_ADAPTER, astRoot);
	}

	// ***** fully-qualified scope class name
	public String getFullyQualifiedScopeClassName() {
		return this.fullyQualifiedScopeClassName;
	}

	private void syncFullyQualifiedScopeClassName(String name) {
		String old = this.fullyQualifiedScopeClassName;
		this.fullyQualifiedScopeClassName = name;
		this.firePropertyChanged(FULLY_QUALIFIED_SCOPE_CLASS_NAME_PROPERTY, old, name);
	}

	private String buildFullyQualifiedScopeClassName(CompilationUnit astRoot) {
		return (this.scope == null) ? null : ASTTools.resolveFullyQualifiedName(this.scopeAdapter.getExpression(astRoot));
	}

	// ***** substitutionHeadName
	public String getSubstitutionHeadName() {
		return this.substitutionHeadName;
	}

	public void setSubstitutionHeadName(String substitutionHeadName) {
		if (this.attributeValueHasChanged(this.substitutionHeadName, substitutionHeadName)) {
			this.substitutionHeadName = substitutionHeadName;
			this.substitutionHeadNameAdapter.setValue(substitutionHeadName);
		}
	}

	private void syncSubstitutionHeadName(String astSubstitutionHeadName) {
		String old = this.substitutionHeadName;
		this.substitutionHeadName = astSubstitutionHeadName;
		this.firePropertyChanged(SUBSTITUTION_HEAD_NAME_PROPERTY, old, astSubstitutionHeadName);
	}

	private String buildSubstitutionHeadName(CompilationUnit astRoot) {
		return this.substitutionHeadNameAdapter.getValue(astRoot);
	}

	public TextRange getSubstitutionHeadNameTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SUBSTITUTION_HEAD_NAME_ADAPTER, astRoot);
	}

	// ***** substitutionHeadNamespace
	public String getSubstitutionHeadNamespace() {
		return this.substitutionHeadNamespace;
	}

	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		if (this.attributeValueHasChanged(this.substitutionHeadNamespace, substitutionHeadNamespace)) {
			this.substitutionHeadNamespace = substitutionHeadNamespace;
			this.substitutionHeadNamespaceAdapter.setValue(substitutionHeadNamespace);
		}
	}

	private void syncSubstitutionHeadNamespace(String astSubstitutionHeadNamespace) {
		String old = this.substitutionHeadNamespace;
		this.substitutionHeadNamespace = astSubstitutionHeadNamespace;
		this.firePropertyChanged(SUBSTITUTION_HEAD_NAMESPACE_PROPERTY, old, astSubstitutionHeadNamespace);
	}

	private String buildSubstitutionHeadNamespace(CompilationUnit astRoot) {
		return this.substitutionHeadNamespaceAdapter.getValue(astRoot);
	}

	public TextRange getSubstitutionHeadNamespaceTextRange(CompilationUnit astRoot) {
		return this.getElementTextRange(SUBSTITUTION_HEAD_NAMESPACE_ADAPTER, astRoot);
	}


	// ********** static methods **********

	private static DeclarationAnnotationElementAdapter<String> buildNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__NAME);
	}

	private static DeclarationAnnotationElementAdapter<String> buildNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__NAMESPACE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildDefaultValueAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__DEFAULT_VALUE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildScopeAdapter() {
		return buildAnnotationElementAdapter(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SCOPE, SimpleTypeStringExpressionConverter.instance());
	}

	private static DeclarationAnnotationElementAdapter<String> buildSubstitutionHeadNameAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME);
	}

	private static DeclarationAnnotationElementAdapter<String> buildSubstitutionHeadNamespaceAdapter() {
		return ConversionDeclarationAnnotationElementAdapter.forStrings(DECLARATION_ANNOTATION_ADAPTER, JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE);
	}

	private static DeclarationAnnotationElementAdapter<String> buildAnnotationElementAdapter(DeclarationAnnotationAdapter annotationAdapter, String elementName, ExpressionConverter<String> converter) {
		return new ConversionDeclarationAnnotationElementAdapter<String>(annotationAdapter, elementName, converter);
	}
}
