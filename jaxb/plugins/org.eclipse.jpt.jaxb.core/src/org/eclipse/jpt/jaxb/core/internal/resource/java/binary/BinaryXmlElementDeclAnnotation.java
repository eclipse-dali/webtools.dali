/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementDeclAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementDecl
 */
public final class BinaryXmlElementDeclAnnotation
	extends BinaryAnnotation
	implements XmlElementDeclAnnotation
{
	private String name;
	private String namespace;
	private String defaultValue;
	private String scope;
	private String substitutionHeadName;
	private String substitutionHeadNamespace;


	public BinaryXmlElementDeclAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
		this.defaultValue = this.buildDefaultValue();
		this.scope = this.buildScope();
		this.substitutionHeadName = this.buildSubstitutionHeadName();
		this.substitutionHeadNamespace = this.buildSubstitutionHeadNamespace();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setNamespace_(this.buildNamespace());
		this.setDefaultValue_(this.buildDefaultValue());
		this.setScope_(this.buildScope());
		this.setSubstitutionHeadName_(this.buildSubstitutionHeadName());
		this.setSubstitutionHeadNamespace_(this.buildSubstitutionHeadNamespace());
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
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__NAME);
	}

	public TextRange getNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}

	public void setNamespace(String namespace) {
		throw new UnsupportedOperationException();
	}

	private void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}

	private String buildNamespace() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__NAMESPACE);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** default value
	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		throw new UnsupportedOperationException();
	}

	private void setDefaultValue_(String defaultValue) {
		String old = this.defaultValue;
		this.defaultValue = defaultValue;
		this.firePropertyChanged(DEFAULT_VALUE_PROPERTY, old, defaultValue);
	}

	private String buildDefaultValue() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__DEFAULT_VALUE);
	}

	public TextRange getDefaultValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** scope
	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		throw new UnsupportedOperationException();
	}

	private void setScope_(String scope) {
		String old = this.scope;
		this.scope = scope;
		this.firePropertyChanged(SCOPE_PROPERTY, old, scope);
		this.firePropertyChanged(FULLY_QUALIFIED_SCOPE_CLASS_NAME_PROPERTY, old, scope);
	}

	private String buildScope() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__SCOPE);
	}

	public TextRange getScopeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified scope class name
	public String getFullyQualifiedScopeClassName() {
		return this.scope;
	}

	// ***** substitutionHeadName
	public String getSubstitutionHeadName() {
		return this.substitutionHeadName;
	}

	public void setSubstitutionHeadName(String substitutionHeadName) {
		throw new UnsupportedOperationException();
	}

	private void setSubstitutionHeadName_(String substitutionHeadName) {
		String old = this.substitutionHeadName;
		this.substitutionHeadName = substitutionHeadName;
		this.firePropertyChanged(SUBSTITUTION_HEAD_NAME_PROPERTY, old, substitutionHeadName);
	}

	private String buildSubstitutionHeadName() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAME);
	}

	public TextRange getSubstitutionHeadNameTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** substitutionHeadNamespace
	public String getSubstitutionHeadNamespace() {
		return this.substitutionHeadNamespace;
	}

	public void setSubstitutionHeadNamespace(String substitutionHeadNamespace) {
		throw new UnsupportedOperationException();
	}

	private void setSubstitutionHeadNamespace_(String substitutionHeadNamespace) {
		String old = this.substitutionHeadNamespace;
		this.substitutionHeadNamespace = substitutionHeadNamespace;
		this.firePropertyChanged(SUBSTITUTION_HEAD_NAMESPACE_PROPERTY, old, substitutionHeadNamespace);
	}

	private String buildSubstitutionHeadNamespace() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_DECL__SUBSTITUTION_HEAD_NAMESPACE);
	}

	public TextRange getSubstitutionHeadNamespaceTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
