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
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class MappedSuperclassAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final MappedSuperclassAnnotationDefinition INSTANCE = new MappedSuperclassAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static MappingAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private MappedSuperclassAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResource parent, Member member) {
		return new MappedSuperclassImpl((JavaPersistentTypeResource) parent, (Type) member);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ID_CLASS,
			JPA.EXCLUDE_DEFAULT_LISTENERS,
			JPA.EXCLUDE_SUPERCLASS_LISTENERS,
			JPA.ENTITY_LISTENERS,
			JPA.PRE_PERSIST,
			JPA.POST_PERSIST,
			JPA.PRE_REMOVE,
			JPA.POST_REMOVE,
			JPA.PRE_UPDATE,
			JPA.POST_UPDATE,
			JPA.POST_LOAD);
	}

	public String getAnnotationName() {
		return JPA.MAPPED_SUPERCLASS;
	}
}
