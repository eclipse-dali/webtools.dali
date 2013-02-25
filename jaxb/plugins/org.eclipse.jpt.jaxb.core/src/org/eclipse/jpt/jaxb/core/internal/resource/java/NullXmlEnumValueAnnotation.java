/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceModel;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumValueAnnotation;

/**
 * javax.xml.bind.annotation.XmlEnumValue
 */
public final class NullXmlEnumValueAnnotation
	extends NullAnnotation<XmlEnumValueAnnotation>
	implements XmlEnumValueAnnotation
{
	protected NullXmlEnumValueAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return JAXB.XML_ENUM_VALUE;
	}


	// ********** XmlEnumValueAnnotation implementation **********

	// ***** value
	public String getValue() {
		return null;
	}

	public void setValue(String value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange() {
		return null;
	}
	
	public boolean valueTouches(int pos) {
		return false;
	}
}
