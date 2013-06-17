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
	private TextRange scopeTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> namespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> namespaceAdapter;
	private String namespace;
	private TextRange namespaceTextRange;
	private TextRange namespaceValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> nameDeclarationAdapter;
	private final AnnotationElementAdapter<String> nameAdapter;
	private String name;
	private TextRange nameTextRange;
	private TextRange nameValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> substitutionHeadNamespaceDeclarationAdapter;
	private final AnnotationElementAdapter<String> substitutionHeadNamespaceAdapter;
	private String substitutionHeadNamespace;
	private TextRange substitutionHeadNamespaceTextRange;
	private TextRange substitutionHeadNamespaceValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> substitutionHeadNameDeclarationAdapter;
	private final AnnotationElementAdapter<String> substitutionHeadNameAdapter;
	private String substitutionHeadName;
	private TextRange substitutionHeadNameTextRange;
	private TextRange substitutionHeadNameValidationTextRange;
	
	private final DeclarationAnnotationElementAdapter<String> defaultValueDeclarationAdapter;
	private final AnnotationElementAdapter<String> defaultValueAdapter;
	private String defaultValue;
	private TextRange defaultValueTextRange;
	
	
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
		this.scopeTextRange = buildScopeTextRange(astAnnotation);
		this.fullyQualifiedScopeClassName = buildFullyQualifiedScopeClassName(astAnnotation);
		this.namespace = buildNamespace(astAnnotation);
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		this.name = buildName(astAnnotation);
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		this.substitutionHeadNamespace = buildSubstitutionHeadNamespace(astAnnotation);
		this.substitutionHeadNamespaceTextRange = buildSubstitutionHeadNamespaceTextRange(astAnnotation);
		this.substitutionHeadNamespaceValidationTextRange = buildSubstitutionHeadNamespaceValidationTextRange(astAnnotation);
		this.substitutionHeadName = buildSubstitutionHeadName(astAnnotation);
		this.substitutionHeadNameTextRange = buildSubstitutionHeadNameTextRange(astAnnotation);
		this.substitutionHeadNameValidationTextRange = buildSubstitutionHeadNameValidationTextRange(astAnnotation);
		this.defaultValue = buildDefaultValue(astAnnotation);
		this.defaultValueTextRange = buildDefaultValueTextRange(astAnnotation);
	}
	
	@Override
	public void synchronizeWith(Annotation astAnnotation) {
		super.synchronizeWith(astAnnotation);
		syncScope(buildScope(astAnnotation));
		this.scopeTextRange = buildScopeTextRange(astAnnotation);
		syncFullyQualifiedScopeClassName(buildFullyQualifiedScopeClassName(astAnnotation));
		syncNamespace(buildNamespace(astAnnotation));
		this.namespaceTextRange = buildNamespaceTextRange(astAnnotation);
		this.namespaceValidationTextRange = buildNamespaceValidationTextRange(astAnnotation);
		syncName(buildName(astAnnotation));
		this.nameTextRange = buildNameTextRange(astAnnotation);
		this.nameValidationTextRange = buildNameValidationTextRange(astAnnotation);
		syncSubstitutionHeadNamespace(buildSubstitutionHeadNamespace(astAnnotation));
		this.substitutionHeadNamespaceTextRange = buildSubstitutionHeadNamespaceTextRange(astAnnotation);
		this.substitutionHeadNamespaceValidationTextRange = buildSubstitutionHeadNamespaceValidationTextRange(astAnnotation);
		syncSubstitutionHeadName(buildSubstitutionHeadName(astAnnotation));
		this.substitutionHeadNameTextRange = buildSubstitutionHeadNameTextRange(astAnnotation);
		this.substitutionHeadNameValidationTextRange = buildSubstitutionHeadNameValidationTextRange(astAnnotation);
		syncDefaultValue(buildDefaultValue(astAnnotation));
		this.defaultValueTextRange = buildDefaultValueTextRange(astAnnotation);
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
		if (ObjectTools.notEquals(this.scope, scope)) {
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

	private TextRange buildScopeTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.scopeDeclarationAdapter, astAnnotation);
	}

	public TextRange getScopeTextRange() {
		return this.scopeTextRange;
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
		if (ObjectTools.notEquals(this.namespace, namespace)) {
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
	
	
	// ***** name *****
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		if (ObjectTools.notEquals(this.name, name)) {
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
		return this.textRangeTouches(this.nameTextRange, pos);
	}
	
	
	// ***** substitutionHeadNamespace *****
	
	public String getSubstitutionHeadNamespace() {
		return this.substitutionHeadNamespace;
	}
	
	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		if (ObjectTools.notEquals(this.substitutionHeadNamespace, substitutionHeadNamespace)) {
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
	
	private TextRange buildSubstitutionHeadNamespaceTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.substitutionHeadNamespaceDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildSubstitutionHeadNamespaceValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.substitutionHeadNamespaceDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getSubstitutionHeadNamespaceTextRange() {
		return this.substitutionHeadNamespaceTextRange;
	}
	
	public TextRange getSubstitutionHeadNamespaceValidationTextRange() {
		return this.substitutionHeadNamespaceValidationTextRange;
	}
	
	public boolean substitutionHeadNamespaceTouches(int pos) {
		return this.textRangeTouches(this.substitutionHeadNamespaceTextRange, pos);
	}
	
	
	// ***** substitutionHeadName *****
	
	public String getSubstitutionHeadName() {
		return this.substitutionHeadName;
	}
	
	public void setSubstitutionHeadName(String substitutionHeadName) {
		if (ObjectTools.notEquals(this.substitutionHeadName, substitutionHeadName)) {
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
	
	private TextRange buildSubstitutionHeadNameTextRange(Annotation astAnnotation) {
		return getAnnotationElementTextRange(this.substitutionHeadNameDeclarationAdapter, astAnnotation);
	}
	
	private TextRange buildSubstitutionHeadNameValidationTextRange(Annotation astAnnotation) {
		return getElementTextRange(this.substitutionHeadNameDeclarationAdapter, astAnnotation);
	}
	
	public TextRange getSubstitutionHeadNameTextRange() {
		return this.substitutionHeadNameTextRange;
	}
	
	public TextRange getSubstitutionHeadNameValidationTextRange() {
		return this.substitutionHeadNameValidationTextRange;
	}
	
	public boolean substitutionHeadNameTouches(int pos) {
		return this.textRangeTouches(this.substitutionHeadNameTextRange, pos);
	}
	
	
	// ***** defaultValue *****
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
	
	public void setDefaultValue(String defaultValue) {
		if (ObjectTools.notEquals(this.defaultValue, defaultValue)) {
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
	

	private TextRange buildDefaultValueTextRange(Annotation astAnnotation) {
		return this.getElementTextRange(this.defaultValueDeclarationAdapter, astAnnotation);
	}

	public TextRange getDefaultValueTextRange() {
		return this.defaultValueTextRange;
	}
}
