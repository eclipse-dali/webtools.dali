/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.java;

import org.eclipse.jpt.core.internal.resource.java.NullAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkVariableOneToOneAnnotation;

/**
 * org.eclipse.persistence.annotations.VariableOneToOne
 */
public class NullEclipseLinkVariableOneToOneAnnotation
	extends NullAnnotation
	implements EclipseLinkVariableOneToOneAnnotation
{	
	protected NullEclipseLinkVariableOneToOneAnnotation(JavaResourcePersistentAttribute parent) {
		super(parent);
	}

	public String getAnnotationName() {
		return ANNOTATION_NAME;
	}
	
	@Override
	protected EclipseLinkVariableOneToOneAnnotation setMappingAnnotation() {
		return (EclipseLinkVariableOneToOneAnnotation) super.setMappingAnnotation();
	}

}
