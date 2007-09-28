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
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class EmbeddableAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final EmbeddableAnnotationDefinition INSTANCE = new EmbeddableAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static EmbeddableAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private EmbeddableAnnotationDefinition() {
		super();
	}

	public Embeddable buildAnnotation(Member member, IJpaPlatform jpaPlatform) {
		return new EmbeddableImpl((Type) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}
	public String getAnnotationName() {
		return JPA.EMBEDDABLE;
	}
}
