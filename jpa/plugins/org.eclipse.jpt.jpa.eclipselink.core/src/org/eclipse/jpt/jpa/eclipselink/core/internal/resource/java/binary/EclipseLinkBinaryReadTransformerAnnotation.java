/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.binary;

import org.eclipse.jdt.core.IAnnotation;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.ReadTransformerAnnotation;

/**
 * <code>org.eclipse.persistence.annotations.ReadTransformer</code>
 */
public final class EclipseLinkBinaryReadTransformerAnnotation
	extends EclipseLinkBinaryTransformerAnnotation
	implements ReadTransformerAnnotation
{
	public EclipseLinkBinaryReadTransformerAnnotation(JavaResourceAnnotatedElement parent, IAnnotation jdtAnnotation) {
		super(parent, jdtAnnotation);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}


	// ********** BinaryTransformerAnnotation implementation **********

	@Override
	String getTransformerClassElementName() {
		return EclipseLink.READ_TRANSFORMER__TRANSFORMER_CLASS;
	}

	@Override
	String getMethodElementName() {
		return EclipseLink.READ_TRANSFORMER__METHOD;
	}
}
