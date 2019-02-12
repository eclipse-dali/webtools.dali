/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlElementRefAnnotation;

/**
 * javax.xml.bind.annotation.XmlElementRef
 */
public final class BinaryXmlElementRefAnnotation
		extends BinaryQNameAnnotation
		implements XmlElementRefAnnotation {
	
	private String name;
	private String namespace;
	private Boolean required;
	private String type;


	public BinaryXmlElementRefAnnotation(JavaResourceModel parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
		this.required = this.buildRequired();
		this.type = this.buildType();
	}

	public String getAnnotationName() {
		return JAXB.XML_ELEMENT_REF;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setNamespace_(this.buildNamespace());
		this.setRequired_(this.buildRequired());
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
	
	private void setName_(String name) {
		String old = this.name;
		this.name = name;
		this.firePropertyChanged(NAME_PROPERTY, old, name);
	}
	
	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_REF__NAME);
	}
	
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}
	
	private void setNamespace_(String namespace) {
		String old = this.namespace;
		this.namespace = namespace;
		this.firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
	}
	
	private String buildNamespace() {
		return (String) this.getJdtMemberValue(JAXB.XML_ELEMENT_REF__NAMESPACE);
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

	public TextRange getRequiredTextRange() {
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

	public TextRange getTypeTextRange() {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified type name
	public String getFullyQualifiedTypeName() {
		return this.type;
	}
}
