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
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementWrapperAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementWrapper
 */
public final class BinaryXmlElementWrapperAnnotation
	extends BinaryAnnotation
	implements XmlElementWrapperAnnotation
{
	private String name;
	private String namespace;
	private Boolean nillable;
	private Boolean required;


	public BinaryXmlElementWrapperAnnotation(JavaResourceAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
		this.nillable = this.buildNillable();
		this.required = this.buildRequired();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setNamespace_(this.buildNamespace());
		this.setNillable_(this.buildNillable());
		this.setRequired_(this.buildRequired());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlElementWrapperAnnotation implementation **********
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_WRAPPER__NAME);
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_WRAPPER__NAMESPACE);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
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
		return (Boolean) this.getJdtMemberValue(JAXB.XML_ELEMENT_WRAPPER__NILLABLE);
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
		return (Boolean) this.getJdtMemberValue(JAXB.XML_ELEMENT_WRAPPER__REQUIRED);
	}

	public TextRange getRequiredTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
