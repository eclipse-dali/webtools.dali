/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.AbstractJavaAttributeMapping;
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkVariableOneToOneAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class EclipseLinkJavaVariableOneToOneMapping 
	extends AbstractJavaAttributeMapping<EclipseLinkVariableOneToOneAnnotation>
	implements EclipseLinkVariableOneToOneMapping
{
	
	public EclipseLinkJavaVariableOneToOneMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EclipseLinkVariableOneToOneAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> supportingAnnotationNames() {
		return EmptyIterator.instance();
	}
}
