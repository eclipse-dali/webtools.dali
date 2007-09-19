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
import org.eclipse.jpt.core.internal.jdtutility.Type;

public class JavaSecondaryTableProvider implements TypeAnnotationProvider
{
	// singleton
	private static final JavaSecondaryTableProvider INSTANCE = new JavaSecondaryTableProvider();

	/**
	 * Return the singleton.
	 */
	public static TypeAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaSecondaryTableProvider() {
		super();
	}


	public SecondaryTable buildJavaTypeAnnotation(Type type, JpaPlatform jpaPlatform) {
		return SecondaryTableImpl.createJavaSecondaryTable(jpaPlatform, type);
	}

	public String getAnnotationName() {
		return JPA.SECONDARY_TABLE;
	}

	public DeclarationAnnotationAdapter getDeclarationAnnotationAdapter() {
		return SecondaryTable.DECLARATION_ANNOTATION_ADAPTER;
	}
}
