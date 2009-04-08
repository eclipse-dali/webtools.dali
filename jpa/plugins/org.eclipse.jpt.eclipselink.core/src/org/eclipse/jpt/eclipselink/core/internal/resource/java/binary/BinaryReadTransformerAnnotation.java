/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkJPA;
import org.eclipse.jpt.eclipselink.core.resource.java.ReadTransformerAnnotation;

/**
 * org.eclipse.persistence.annotations.ReadTransformer
 */
public final class BinaryReadTransformerAnnotation
	extends BinaryTransformerAnnotation
	implements ReadTransformerAnnotation
{

	public BinaryReadTransformerAnnotation(JavaResourcePersistentAttribute parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryTransformerAnnotation implementation **********

	@Override
	String getTransformerClassElementName() {
		return EclipseLinkJPA.READ_TRANSFORMER__TRANSFORMER_CLASS;
	}

	@Override
	String getMethodElementName() {
		return EclipseLinkJPA.READ_TRANSFORMER__METHOD;
	}

}
