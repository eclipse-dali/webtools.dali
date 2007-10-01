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
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class BasicAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final BasicAnnotationDefinition INSTANCE = new BasicAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static BasicAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private BasicAnnotationDefinition() {
		super();
	}

	public MappingAnnotation buildAnnotation(JavaResource parent, Member member) {
		return new BasicImpl((JavaPersistentAttributeResource) parent, (Attribute) member);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.LOB,
			JPA.TEMPORAL,
			JPA.ENUMERATED);
	}

	public String getAnnotationName() {
		return JPA.BASIC;
	}
}
