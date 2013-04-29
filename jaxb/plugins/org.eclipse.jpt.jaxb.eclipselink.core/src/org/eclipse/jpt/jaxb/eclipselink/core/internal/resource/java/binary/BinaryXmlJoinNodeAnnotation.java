/*******************************************************************************
 *  Copyright (c) 2012  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlJoinNodeAnnotation;


public class BinaryXmlJoinNodeAnnotation
		extends BinaryAnnotation
		implements XmlJoinNodeAnnotation {
	
	private String xmlPath;
	
	private String referencedXmlPath;
	
	
	public BinaryXmlJoinNodeAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.xmlPath = buildXmlPath();
		this.referencedXmlPath = buildReferencedXmlPath();
	}
	
	
	public String getAnnotationName() {
		return ELJaxb.XML_JOIN_NODE;
	}
	
	@Override
	public void update() {
		super.update();
		setXmlPath_(buildXmlPath());
		setReferencedXmlPath_(buildReferencedXmlPath());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.xmlPath + " -> " + this.referencedXmlPath); //$NON-NLS-1$
	}
	
	
	// ***** xmlPath *****
	
	public String getXmlPath() {
		return this.xmlPath;
	}
	
	public void setXmlPath(String xmlPath) {
		throw new UnsupportedOperationException();
	}
	
	private void setXmlPath_(String xmlPath) {
		String old = this.xmlPath;
		this.xmlPath = xmlPath;
		this.firePropertyChanged(XML_PATH_PROPERTY, old, xmlPath);
	}
	
	private String buildXmlPath() {
		return (String) this.getJdtMemberValue(ELJaxb.XML_JOIN_NODE__XML_PATH);
	}
	
	public TextRange getXmlPathTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getXmlPathValidationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean xmlPathTouches(int pos) {
		throw new UnsupportedOperationException();
	}
	
	
	// ***** xmlPath *****
	
	public String getReferencedXmlPath() {
		return this.referencedXmlPath;
	}
	
	public void setReferencedXmlPath(String referencedXmlPath) {
		throw new UnsupportedOperationException();
	}
	
	private void setReferencedXmlPath_(String referencedXmlPath) {
		String old = this.referencedXmlPath;
		this.referencedXmlPath = referencedXmlPath;
		this.firePropertyChanged(REFERENCED_XML_PATH_PROPERTY, old, referencedXmlPath);
	}
	
	private String buildReferencedXmlPath() {
		return (String) this.getJdtMemberValue(ELJaxb.XML_JOIN_NODE__REFERENCED_XML_PATH);
	}
	
	public TextRange getReferencedXmlPathTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public TextRange getReferencedXmlPathValidationTextRange() {
		throw new UnsupportedOperationException();
	}
	
	public boolean referencedXmlPathTouches(int pos) {
		throw new UnsupportedOperationException();
	}
}
