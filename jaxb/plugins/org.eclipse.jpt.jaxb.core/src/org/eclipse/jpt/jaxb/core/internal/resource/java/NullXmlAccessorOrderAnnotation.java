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
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorOrder
 */
public final class NullXmlAccessorOrderAnnotation
	extends NullAnnotation
	implements XmlAccessorOrderAnnotation
{
	protected NullXmlAccessorOrderAnnotation(JavaResourceModel parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return JAXB.XML_ACCESSOR_ORDER;
	}

	@Override
	protected XmlAccessorOrderAnnotation addAnnotation() {
		return (XmlAccessorOrderAnnotation) super.addAnnotation();
	}


	// ********** XmlAccessorTOrderAnnotation implementation **********

	// ***** value
	public XmlAccessOrder getValue() {
		return null;
	}

	public void setValue(XmlAccessOrder value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange() {
		return null;
	}

}
