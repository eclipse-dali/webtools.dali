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
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.jdtutility.Member;

public class JavaSecondaryTablesProvider implements AnnotationProvider
{
	// singleton
	private static final JavaSecondaryTablesProvider INSTANCE = new JavaSecondaryTablesProvider();

	/**
	 * Return the singleton.
	 */
	public static AnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaSecondaryTablesProvider() {
		super();
	}


	public SecondaryTables buildAnnotation(Member member, JpaPlatform jpaPlatform) {
		return new SecondaryTablesImpl(member, jpaPlatform);
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLES;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return SecondaryTables.DECLARATION_ANNOTATION_ADAPTER;
	}
}
