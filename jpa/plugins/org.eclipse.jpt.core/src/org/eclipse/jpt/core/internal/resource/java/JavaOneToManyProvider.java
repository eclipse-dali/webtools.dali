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
import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaOneToManyProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaOneToManyProvider INSTANCE = new JavaOneToManyProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaOneToManyProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaOneToManyProvider() {
		super();
	}

	public OneToMany buildAnnotation(Member member, JpaPlatform jpaPlatform) {
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

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return OneToMany.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.ONE_TO_MANY;
	}
}
