/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.internal.utility.JDTTools;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlSchemaTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlSchemaType
 */
public class BinaryXmlSchemaTypeAnnotation
		extends BinaryAnnotation
		implements XmlSchemaTypeAnnotation {
	
	private String name;

	private String namespace;

	private String type;
	private String fullyQualifiedType;
	
	
	private BinaryXmlSchemaTypeAnnotation(
			JavaResourceAnnotatedElement parent,
			IAnnotation jdtAnnotation) {
		
		super(parent, jdtAnnotation);
		this.name = this.buildName();
		this.namespace= buildNamespace();
		this.type = buildType();
		this.fullyQualifiedType = buildFullyQualifiedType(jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_SCHEMA_TYPE;
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
		throw new UnsupportedOperationException();
	}
	
	private String buildName() {
		return (String) this.getJdtMemberValue(JAXB.XML_SCHEMA_TYPE__NAME);
	}

	public TextRange getNameTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean nameTouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
	// ***** namespace
	public String getNamespace() {
		return this.namespace;
	}
	
	public void setNamespace(String namespace) {
		throw new UnsupportedOperationException();
	}
	
	private String buildNamespace() {
		return (String) this.getJdtMemberValue(JAXB.XML_SCHEMA_TYPE__NAMESPACE);
	}
	
	public TextRange getNamespaceTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean namespaceTouches(int pos) {
		throw new UnsupportedOperationException();
	}

	// ***** type
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		throw new UnsupportedOperationException();
	}
	
	private String buildType() {
		return (String) this.getJdtMemberValue(JAXB.XML_SCHEMA_TYPE__TYPE);
	}

	public TextRange getTypeTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public String getFullyQualifiedType() {
		return this.fullyQualifiedType;
	}
	
	private String buildFullyQualifiedType(IAnnotation jdtAnnotation) {
		return JDTTools.resolveType((IType)jdtAnnotation.getAncestor(IJavaElement.TYPE), this.type);
	}
	
}
