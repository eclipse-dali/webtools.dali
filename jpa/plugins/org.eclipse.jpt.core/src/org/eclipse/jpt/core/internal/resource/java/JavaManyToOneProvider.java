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

public class JavaManyToOneProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaManyToOneProvider INSTANCE = new JavaManyToOneProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaManyToOneProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaManyToOneProvider() {
		super();
	}

	public ManyToOne buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new ManyToOneImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.JOIN_COLUMN,
			JPA.JOIN_COLUMNS,
			JPA.JOIN_TABLE);
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return ManyToOne.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.MANY_TO_ONE;
	}
}
