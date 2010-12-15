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
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.AbstractJavaResourceType;
import org.eclipse.jpt.jaxb.core.resource.java.XmlEnumAnnotation;

/**
 * javax.xml.bind.annotation.XmlEnum
 */
public final class NullXmlEnumAnnotation
	extends NullAnnotation
	implements XmlEnumAnnotation
{
	protected NullXmlEnumAnnotation(AbstractJavaResourceType parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}

	@Override
	protected XmlEnumAnnotation addAnnotation() {
		return (XmlEnumAnnotation) super.addAnnotation();
	}


	// ********** XmlEnumAnnotation implementation **********

	// ***** value
	public String getValue() {
		return null;
	}

	public String getFullyQualifiedValueClassName() {
		return null;
	}

	public void setValue(String value) {
		if (value != null) {
			this.addAnnotation().setValue(value);
		}
	}

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}
}
