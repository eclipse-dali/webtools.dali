/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.ConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.ConverterAnnotation2_1;

public class JavaConverterTypeDefinition2_1
	implements JavaManagedTypeDefinition
{
	// singleton
	private static final JavaManagedTypeDefinition INSTANCE = new JavaConverterTypeDefinition2_1();

	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private JavaConverterTypeDefinition2_1() {
		super();
	}

	public Class<ConverterType2_1> getManagedTypeType() {
		return ConverterType2_1.class;
	}

	public Iterable<String> getAnnotationNames(JpaProject jpaProject) {
		return IterableTools.singletonIterable(ConverterAnnotation2_1.ANNOTATION_NAME);
	}

	public JavaConverterType2_1 buildContextManagedType(JpaContextModel parent, JavaResourceType jrt, JpaFactory factory) {
		return ((JpaFactory2_1) factory).buildJavaConverterType(parent, jrt);
	}
}
