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

public class JavaVersionProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaVersionProvider INSTANCE = new JavaVersionProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaVersionProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaVersionProvider() {
		super();
	}

	public Version buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new VersionImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return new ArrayIterator<String>(
			JPA.COLUMN,
			JPA.TEMPORAL);
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return Version.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.VERSION;
	}
}
