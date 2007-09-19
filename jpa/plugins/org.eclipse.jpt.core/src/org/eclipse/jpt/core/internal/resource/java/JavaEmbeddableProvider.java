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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Type;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class JavaEmbeddableProvider implements TypeMappingAnnotationProvider
{
	// singleton
	private static final JavaEmbeddableProvider INSTANCE = new JavaEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaEmbeddableProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEmbeddableProvider() {
		super();
	}

	public Embeddable buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return new EmbeddableImpl(type, jpaPlatform);
	}

	public Iterator<String> correspondingAnnotationNames() {
		return EmptyIterator.instance();
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return Embeddable.DECLARATION_ANNOTATION_ADAPTER;
	}

	public String getAnnotationName() {
		return JPA.EMBEDDABLE;
	}
}
