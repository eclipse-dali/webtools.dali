/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.internal.resource.java.binary.BinaryAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlMimeTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlMimeType
 */
public final class BinaryXmlMimeTypeAnnotation
	extends BinaryAnnotation
	implements XmlMimeTypeAnnotation
{

	private String value;

	public BinaryXmlMimeTypeAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
		this.value = this.buildValue();
	}

	public String getAnnotationName() {
		return JAXB.XML_MIME_TYPE;
	}

	@Override
	public void update() {
		super.update();
		this.setValue_(this.buildValue());
	}


	// ********** XmlMimeTypeAnnotation implementation **********
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
	}

	private String buildValue() {
		return (String) this.getJdtMemberValue(JAXB.XML_MIME_TYPE__VALUE);
	}

	public TextRange getValueTextRange() {
		throw new UnsupportedOperationException();
	}

}
