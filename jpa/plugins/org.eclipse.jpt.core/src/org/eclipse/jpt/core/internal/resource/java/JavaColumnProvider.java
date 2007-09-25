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

import org.eclipse.jpt.core.internal.content.java.mappings.JPA;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public class JavaColumnProvider implements AnnotationProvider
{
	// singleton
	private static final JavaColumnProvider INSTANCE = new JavaColumnProvider();

	/**
	 * Return the singleton.
	 */
	public static AnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaColumnProvider() {
		super();
	}


	public Column buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new ColumnImpl((Attribute) member, jpaPlatform);
	}

	public String getAnnotationName() {
		return JPA.COLUMN;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return Column.DECLARATION_ANNOTATION_ADAPTER;
	}
}
