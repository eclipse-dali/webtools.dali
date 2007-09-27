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

public class JavaIdProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaIdProvider INSTANCE = new JavaIdProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaIdProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaIdProvider() {
		super();
	}

	public Id buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new IdImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.GENERATED_VALUE,
			JPA.TEMPORAL,
			JPA.TABLE_GENERATOR,
			JPA.SEQUENCE_GENERATOR);
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return Id.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.ID;
	}
}
