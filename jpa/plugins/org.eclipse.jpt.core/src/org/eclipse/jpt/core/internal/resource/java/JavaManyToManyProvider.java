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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;

public class JavaManyToManyProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaManyToManyProvider INSTANCE = new JavaManyToManyProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaManyToManyProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaManyToManyProvider() {
		super();
	}

	public ManyToMany buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new ManyToManyImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.ORDER_BY,
			JPA.MAP_KEY,
			JPA.JOIN_TABLE);
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return ManyToMany.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.MANY_TO_MANY;
	}
}
