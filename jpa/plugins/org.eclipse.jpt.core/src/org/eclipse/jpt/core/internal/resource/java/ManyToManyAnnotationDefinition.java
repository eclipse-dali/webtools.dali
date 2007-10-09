/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class ManyToManyAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final ManyToManyAnnotationDefinition INSTANCE = new ManyToManyAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static ManyToManyAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private ManyToManyAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResource parent, Member member) {
		return new ManyToManyImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE);
	}

	public String getAnnotationName() {
		return JPA.MANY_TO_MANY;
	}
}
