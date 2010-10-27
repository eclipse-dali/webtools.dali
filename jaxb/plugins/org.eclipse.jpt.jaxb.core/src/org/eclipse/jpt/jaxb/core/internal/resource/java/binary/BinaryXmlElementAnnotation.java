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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlElement
 */
public final class BinaryXmlElementAnnotation
	extends BinaryAnnotation
	implements XmlElementAnnotation
{
	private String name;
	private String namespace;
	private String defaultValue;
	private Boolean nillable;
	private Boolean required;
	private String type;


	public BinaryXmlElementAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
		this.defaultValue = this.buildDefaultValue();
		this.nillable = this.buildNillable();
		this.required = this.buildRequired();
		this.type = this.buildType();
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
		this.setNillable_(this.buildNillable());
		this.setRequired_(this.buildRequired());
		this.setType_(this.buildType());
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
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT__NAME);
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT__NAMESPACE);
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT__DEFAULT_VALUE);
	}

	public TextRange getDefaultValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** nillable
	public Boolean getNillable() {
		return this.nillable;
	}

	public void setNillable(Boolean nillable) {
		throw new UnsupportedOperationException();
	}

	private void setNillable_(Boolean nillable) {
		Boolean old = this.nillable;
		this.nillable = nillable;
		this.firePropertyChanged(NILLABLE_PROPERTY, old, nillable);
	}

	private Boolean buildNillable() {
		return (Boolean) this.getJdtMemberValue(JAXB.XML_ELEMENT__NILLABLE);
	}

	public TextRange getNillableTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** required
	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		throw new UnsupportedOperationException();
	}

	private void setRequired_(Boolean required) {
		Boolean old = this.required;
		this.required = required;
		this.firePropertyChanged(REQUIRED_PROPERTY, old, required);
	}

	private Boolean buildRequired() {
		return (Boolean) this.getJdtMemberValue(JAXB.XML_ELEMENT__REQUIRED);
	}

	public TextRange getRequiredTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** type
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		throw new UnsupportedOperationException();
	}

	private void setType_(String type) {
		String old = this.type;
		this.type = type;
		this.firePropertyChanged(TYPE_PROPERTY, old, type);
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_NAME_PROPERTY, old, type);
	}

	private String buildType() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT__TYPE);
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName() {
		return this.type;
	}

}
