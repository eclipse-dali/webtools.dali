/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.java;

import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructureMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.core.resource.java.StructureAnnotation2_3;

public class EclipseLinkJavaStructureMapping2_3
	extends AbstractJavaAttributeMapping<StructureAnnotation2_3>
	implements EclipseLinkStructureMapping2_3
{

	public EclipseLinkJavaStructureMapping2_3(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
	}


	// ********** misc **********

	public String getKey() {
		return EclipseLinkMappingKeys.STRUCTURE_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return StructureAnnotation2_3.ANNOTATION_NAME;
	}
}
