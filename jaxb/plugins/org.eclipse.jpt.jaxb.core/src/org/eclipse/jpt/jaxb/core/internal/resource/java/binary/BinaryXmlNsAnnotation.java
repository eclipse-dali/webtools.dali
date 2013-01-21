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
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlNsAnnotation;


public class BinaryXmlNsAnnotation
		extends BinaryAnnotation
		implements XmlNsAnnotation {
	
	private String namespaceURI;
	
	private String prefix;
	
	
	public BinaryXmlNsAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.namespaceURI = this.buildNamespaceURI();
		this.prefix = this.buildPrefix();
	}
	
	public String getAnnotationName() {
		return JAXB.XML_NS;
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.namespaceURI);
	}

	
	// ***** namespace *****
	
	public String getNamespaceURI() {
		return this.namespaceURI;
	}
	
	public void setNamespaceURI(String namespaceURI) {
		throw new UnsupportedOperationException();
	}
	
	private String buildNamespaceURI() {
		return (String) this.getJdtMemberValue(JAXB.XML_NS__NAMESPACE_URI);
	}

	public TextRange getNamespaceURITextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean namespaceURITouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** prefix *****
	
	public String getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(String prefix) {
		throw new UnsupportedOperationException();
	}
	
	private String buildPrefix() {
		return (String) this.getJdtMemberValue(JAXB.XML_NS__PREFIX);
	}

	public TextRange getPrefixTextRange() {
		throw new UnsupportedOperationException();
	}

	public boolean prefixTouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
}
