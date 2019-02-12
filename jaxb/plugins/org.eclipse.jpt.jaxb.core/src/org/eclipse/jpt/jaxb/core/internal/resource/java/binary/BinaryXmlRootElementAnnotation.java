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
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlRootElementAnnotation;

/**
 * javax.xml.bind.annotation.XmlRootElement
 */
public final class BinaryXmlRootElementAnnotation
		extends BinaryQNameAnnotation
		implements XmlRootElementAnnotation {
	
	private String name;
	private String namespace;


	public BinaryXmlRootElementAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace = this.buildNamespace();
	}
	
	
	public String getAnnotationName() {
		return JAXB.XML_ROOT_ELEMENT;
	}

	@Override
	public void update() {
		super.update();
		this.setName_(this.buildName());
		this.setNamespace_(this.buildNamespace());
	}

	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.name);
	}


	// ********** XmlRootElementAnnotation implementation **********

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
		return (String) this.getJdtMemberValue(JAXB.XML_ROOT_ELEMENT__NAME);
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
		return (String) this.getJdtMemberValue(JAXB.XML_ROOT_ELEMENT__NAMESPACE);
	}
}
