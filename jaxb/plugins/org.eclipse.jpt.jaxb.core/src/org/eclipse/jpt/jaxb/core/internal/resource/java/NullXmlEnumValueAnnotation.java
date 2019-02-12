/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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

	public TextRange getValueValidationTextRange() {
		return null;
	}
	
	public boolean valueTouches(int pos) {
		return false;
	}
}
