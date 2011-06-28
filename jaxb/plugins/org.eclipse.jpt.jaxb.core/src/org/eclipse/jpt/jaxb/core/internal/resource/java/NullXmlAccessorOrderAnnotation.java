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
import org.eclipse.jpt.common.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceNode;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessOrder;
import org.eclipse.jpt.jaxb.core.resource.java.XmlAccessorOrderAnnotation;

/**
 * javax.xml.bind.annotation.XmlAccessorOrder
 */
public final class NullXmlAccessorOrderAnnotation
	extends NullAnnotation
	implements XmlAccessorOrderAnnotation
{
	protected NullXmlAccessorOrderAnnotation(JavaResourceNode parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
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

	public TextRange getValueTextRange(CompilationUnit astRoot) {
		return null;
	}

}
