/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToManyRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.resource.java.JPA;
import org.eclipse.jpt.core.resource.java.OneToManyAnnotation;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;


public abstract class AbstractJavaOneToManyMapping
	extends AbstractJavaMultiRelationshipMapping<OneToManyAnnotation>
	implements JavaOneToManyMapping
{
	
	protected AbstractJavaOneToManyMapping(JavaPersistentAttribute parent) {
		super(parent);
	}
	
	
	public String getAnnotationName() {
		return OneToManyAnnotation.ANNOTATION_NAME;
	}
	
	public Iterator<String> supportingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE,
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS);
	}

	@Override
	public OneToManyAnnotation getMappingAnnotation() {
		return super.getMappingAnnotation();
	}
	
	public String getKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public JavaOneToManyRelationshipReference getRelationshipReference() {
		return (JavaOneToManyRelationshipReference) super.getRelationshipReference();
	}
}
