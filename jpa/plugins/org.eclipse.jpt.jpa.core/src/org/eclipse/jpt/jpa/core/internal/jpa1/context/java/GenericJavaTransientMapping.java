/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelField2_0;
import org.eclipse.jpt.jpa.core.resource.java.TransientAnnotation;

/**
 * Java transient mapping
 */
public class GenericJavaTransientMapping
	extends AbstractJavaAttributeMapping<TransientAnnotation>
	implements JavaTransientMapping
{
	public GenericJavaTransientMapping(JavaSpecifiedPersistentAttribute parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	protected String getAnnotationName() {
		return TransientAnnotation.ANNOTATION_NAME;
	}


	// ********** metamodel **********  

	@Override
	public MetamodelField2_0 getMetamodelField() {
		return null;
	}
}
