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
import org.eclipse.jpt.jaxb.core.resource.java.NestableXmlJavaTypeAdapterAnnotation;

/**
 * javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
public final class BinaryXmlJavaTypeAdapterAnnotation
	extends BinaryAnnotation
	implements NestableXmlJavaTypeAdapterAnnotation
{
	private String value;


	public BinaryXmlJavaTypeAdapterAnnotation(JavaResourceNode parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
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
		this.firePropertyChanged(FULLY_QUALIFIED_VALUE_CLASS_NAME_PROPERTY, old, value);
	}

	private String buildValue() {
		return (String) this.getJdtMemberValue(JAXB.XML_JAVA_TYPE_ADAPTER__VALUE);
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		throw new UnsupportedOperationException();
	}

	// ***** fully-qualified value class name
	public String getFullyQualifiedValueClassName() {
		return this.value;
	}
}
