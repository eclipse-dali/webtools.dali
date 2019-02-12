/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
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
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.XmlInlineBinaryDataAnnotation;

/**
 * javax.xml.bind.annotation.XmlInlineBinaryDataAnnotation
 */
public final class BinaryXmlInlineBinaryDataAnnotation
		extends BinaryAnnotation
		implements XmlInlineBinaryDataAnnotation {
	
	public BinaryXmlInlineBinaryDataAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}
	
	public String getAnnotationName() {
		return JAXB.XML_INLINE_BINARY_DATA;
	}
}
