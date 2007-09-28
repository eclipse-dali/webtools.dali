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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaTransientProvider implements MappingAnnotationProvider
{
	// singleton
	private static final JavaTransientProvider INSTANCE = new JavaTransientProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTransientProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaTransientProvider() {
		super();
	}

	public Transient buildAnnotation(Member member, IJpaPlatform jpaPlatform) {
		return new TransientImpl((Attribute) member, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return Transient.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.TRANSIENT;
	}
}
