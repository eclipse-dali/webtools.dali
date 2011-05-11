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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceMember;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.ELJaxb;
import org.eclipse.jpt.jaxb.eclipselink.core.resource.java.XmlTransformationAnnotation;


public class BinaryXmlTransformationAnnotation
		extends BinaryAnnotation
		implements XmlTransformationAnnotation {
	
	private Boolean optional;
	
	
	public BinaryXmlTransformationAnnotation(JavaResourceMember parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.optional = buildOptional();
	}
	
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	public void update() {
		super.update();
		setOptional_(buildOptional());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.optional);
	}
	
	
	// ***** optional 
	
	public Boolean getOptional() {
		return this.optional;
	}
	
	public void setOptional(Boolean optional) {
		throw new UnsupportedOperationException();
	}
	
	private void setOptional_(Boolean optional) {
		Boolean old = this.optional;
		this.optional = optional;
		this.firePropertyChanged(OPTIONAL_PROPERTY, old, optional);
	}
	
	private Boolean buildOptional() {
		return (Boolean) this.getJdtMemberValue(ELJaxb.XML_TRANSFORMATION__OPTIONAL);
	}
	
	public TextRange getOptionalTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
}
