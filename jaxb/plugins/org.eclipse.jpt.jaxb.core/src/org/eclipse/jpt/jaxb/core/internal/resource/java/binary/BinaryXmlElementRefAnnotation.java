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
import org.eclipse.jpt.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementRef
 */
public final class BinaryXmlElementRefAnnotation
	extends BinaryAnnotation
	implements XmlElementRefAnnotation
{
	private String name;
	private String namespace;
	private String type;


	public BinaryXmlElementRefAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
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
		this.setType_(this.buildType());
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
		throw new UnsupportedOperationException();
	}

	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}

	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_REF__NAME);
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_REF__NAMESPACE);
	}

	public TextRange getNamespaceTextRange(CompilationUnit astRoot) {
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
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_REF__TYPE);
	}

	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName() {
		return this.type;
	}

}
