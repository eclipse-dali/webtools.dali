/*******************************************************************************
 *  Copyright (c) 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlInverseReferenceAnnotation;


public class BinaryXmlInverseReferenceAnnotation
		extends BinaryAnnotation
		implements XmlInverseReferenceAnnotation {
	
	private String mappedBy;
	
	
	public BinaryXmlInverseReferenceAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.mappedBy = buildMappedBy();
	}
	
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	public void update() {
		super.update();
		setMappedBy_(buildMappedBy());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.mappedBy);
	}
	
	
	// ***** mappedBy
	
	public String getMappedBy() {
		return this.mappedBy;
	}
	
	public void setMappedBy(String mappedBy) {
		throw new UnsupportedOperationException();
	}
	
	private void setMappedBy_(String mappedBy) {
		String old = this.mappedBy;
		this.mappedBy = mappedBy;
		this.firePropertyChanged(MAPPED_BY_PROPERTY, old, mappedBy);
	}
	
	private String buildMappedBy() {
		return (String) this.getJdtMemberValue(ELJaxb.XML_INVERSE_REFERENCE__MAPPED_BY);
	}
	
	public TextRange getMappedByTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
