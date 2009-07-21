/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.context.BasicCollectionMapping;
import org.eclipse.jpt.eclipselink.core.resource.java.EclipseLinkBasicCollectionAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaBasicCollectionMapping
	extends AbstractJavaAttributeMapping<EclipseLinkBasicCollectionAnnotation>
	implements BasicCollectionMapping
{
	
	public JavaBasicCollectionMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EclipseLinkBasicCollectionAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> supportingAnnotationNames() {
		return EmptyIterator.instance();
	}
}
