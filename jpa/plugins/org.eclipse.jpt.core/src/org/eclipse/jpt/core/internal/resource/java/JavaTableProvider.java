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

import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public class JavaTableProvider implements AnnotationProvider
{
	// singleton
	private static final JavaTableProvider INSTANCE = new JavaTableProvider();

	/**
	 * Return the singleton.
	 */
	public static AnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaTableProvider() {
		super();
	}


	public Table buildAnnotation(Member member, IJpaPlatform jpaPlatform) {
		return new TableImpl(jpaPlatform, member);
	}

	public String getAnnotationName() {
		return JPA.TABLE;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return TableImpl.DECLARATION_ANNOTATION_ADAPTER;
	}
}
