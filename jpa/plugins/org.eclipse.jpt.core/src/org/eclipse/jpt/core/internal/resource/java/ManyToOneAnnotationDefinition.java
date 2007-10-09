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

public class ManyToOneAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final ManyToOneAnnotationDefinition INSTANCE = new ManyToOneAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static ManyToOneAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private ManyToOneAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResource parent, Member member) {
		return new ManyToOneImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}

	public String getAnnotationName() {
		return JPA.MANY_TO_ONE;
	}
}
