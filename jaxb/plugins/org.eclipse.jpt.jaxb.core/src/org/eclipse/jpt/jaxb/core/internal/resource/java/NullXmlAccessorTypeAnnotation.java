/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.resource.java;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorTypeAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorType
 */
public final class NullXmlAccessorTypeAnnotation
	extends NullAnnotation
	implements XmlAccessorTypeAnnotation
{
	protected NullXmlAccessorTypeAnnotation(JavaResourceAnnotatedElement parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected XmlAccessorTypeAnnotation addAnnotation() {
		return (XmlAccessorTypeAnnotation) super.addAnnotation();
	}


	// ********** XmlAccessorTypeAnnotation implementation **********

	// ***** value
	public XmlAccessType getValue() {
		return null;
	}

	public void setValue(XmlAccessType value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}

}
