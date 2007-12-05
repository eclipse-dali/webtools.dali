/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.resource.java.Embeddable;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public class JavaEmbeddable extends JavaTypeMapping implements IJavaEmbeddable
{
	public JavaEmbeddable(IJavaPersistentType parent) {
		super(parent);
	}

	public String getKey() {
		return IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public String annotationName() {
		return Embeddable.ANNOTATION_NAME;
	}

	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}
	
	public boolean isMapped() {
		return true;
	}
	
	@Override
	public boolean attributeMappingKeyAllowed(String attributeMappingKey) {
		return attributeMappingKey == IMappingKeys.BASIC_ATTRIBUTE_MAPPING_KEY || attributeMappingKey == IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}
}
