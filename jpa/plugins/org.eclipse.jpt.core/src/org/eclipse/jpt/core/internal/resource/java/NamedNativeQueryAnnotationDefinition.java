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

import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class NamedNativeQueryAnnotationDefinition implements AnnotationDefinition
{
	// singleton
	private static final NamedNativeQueryAnnotationDefinition INSTANCE = new NamedNativeQueryAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NamedNativeQueryAnnotationDefinition() {
		super();
	}

	public Annotation buildAnnotation(JavaResource parent, Member member) {
		return NamedNativeQueryImpl.createNamedNativeQuery(parent, (Type) member);
	}

	public String getAnnotationName() {
		return JPA.NAMED_NATIVE_QUERY;
	}
}
