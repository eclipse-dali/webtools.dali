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

public class OneToManyAnnotationDefinition implements MappingAnnotationDefinition
{
	// singleton
	private static final OneToManyAnnotationDefinition INSTANCE = new OneToManyAnnotationDefinition();

	/**
	 * Return the singleton.
	 */
	public static OneToManyAnnotationDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private OneToManyAnnotationDefinition() {
		super();
	}

	public OneToMany buildAnnotation(Member member, IJpaPlatform jpaPlatform) {
		return new OneToManyImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE,
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS);
	}

	public String getAnnotationName() {
		return JPA.ONE_TO_MANY;
	}
}
