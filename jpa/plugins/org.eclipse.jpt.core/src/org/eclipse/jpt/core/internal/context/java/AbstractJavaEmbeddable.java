/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;


public abstract class AbstractJavaEmbeddable
	extends AbstractJavaTypeMapping
	implements JavaEmbeddable
{
	protected AbstractJavaEmbeddable(JavaPersistentType parent) {
		super(parent);
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}

	public String getAnnotationName() {
		return EmbeddableAnnotation.ANNOTATION_NAME;
	}

	public Iterator<String> supportingAnnotationNames() {
		return EmptyIterator.instance();
	}
	
	public boolean isMapped() {
		return true;
	}
	
	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}
	
	@Override
	public boolean shouldValidateAgainstDatabase() {
		return false;
	}
}
