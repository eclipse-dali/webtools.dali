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

public class AttributeOverridesAnnotationDefinition implements AnnotationDefinition
{
	// singleton
	private static final AttributeOverridesAnnotationDefinition INSTANCE = new AttributeOverridesAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static AnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private AttributeOverridesAnnotationDefinition() {
		super();
	}

	public AttributeOverrides buildAnnotation(JavaResource parent, Member member) {
		return new AttributeOverridesImpl(parent, member);
	}

	public String getAnnotationName() {
		return JPA.ATTRIBUTE_OVERRIDES;
	}
}
