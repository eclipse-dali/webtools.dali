/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructureMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.EclipseLinkStructureAnnotation2_3;

public class JavaEclipseLinkStructureMapping2_3
	extends AbstractJavaAttributeMapping<EclipseLinkStructureAnnotation2_3>
	implements EclipseLinkStructureMapping2_3
{

	public JavaEclipseLinkStructureMapping2_3(JavaPersistentAttribute parent) {
		super(parent);
	}


	// ********** misc **********

	public String getKey() {
		return EclipseLinkMappingKeys.STRUCTURE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return EclipseLinkStructureAnnotation2_3.ANNOTATION_NAME;
	}
}
