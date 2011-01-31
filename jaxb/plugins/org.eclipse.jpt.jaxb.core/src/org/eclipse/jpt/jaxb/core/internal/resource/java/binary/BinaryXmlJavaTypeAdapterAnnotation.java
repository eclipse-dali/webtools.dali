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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.jaxb.core.resource.java.XmlJavaTypeAdapterAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
public final class BinaryXmlJavaTypeAdapterAnnotation
		extends BinaryAnnotation
		implements XmlJavaTypeAdapterAnnotation {
	
	private String value;
	
	private String type;
	
	
	public BinaryXmlJavaTypeAdapterAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = buildValue();
		this.type = buildType();
	}
	
	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	public void update() {
		super.update();
		setValue_(buildValue());
		setType_(buildType());
	}
	
	@Override
	public void toString(StringBuilder sb) {
		sb.append(this.value);
	}
	
	
	// ********** XmlJavaTypeAdapterAnnotation implementation **********
	
	// ***** value
	public String getValue() {
		return this.value;
	}
	
	public void setValue(String value) {
		throw new UnsupportedOperationException();
	}
	
	private void setValue_(String value) {
		String old = this.value;
		this.value = value;
		this.firePropertyChanged(VALUE_PROPERTY, old, value);
		this.firePropertyChanged(FULLY_QUALIFIED_VALUE_PROPERTY, old, value);
	}
	
	private String buildValue() {
		return (String) this.getJdtMemberValue(JAXB.XML_JAVA_TYPE_ADAPTER__VALUE);
	}
	
	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public String getFullyQualifiedValue() {
		return this.value;
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
		this.firePropertyChanged(FULLY_QUALIFIED_TYPE_PROPERTY, old, type);
	}
	
	private String buildType() {
		return (String) this.getJdtMemberValue(JAXB.XML_JAVA_TYPE_ADAPTER__TYPE);
	}
	
	public TextRange getTypeTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}
	
	public String getFullyQualifiedType() {
		return this.type;
	}
}
