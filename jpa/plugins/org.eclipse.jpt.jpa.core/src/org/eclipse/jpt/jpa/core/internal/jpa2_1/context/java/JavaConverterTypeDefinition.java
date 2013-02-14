/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.jpa2_1.JpaFactory2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.java.JavaConverterType2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.Converter2_1Annotation;

public class JavaConverterTypeDefinition implements JavaManagedTypeDefinition
{	
	// singleton
	private static final JavaManagedTypeDefinition INSTANCE = new JavaConverterTypeDefinition();

	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private JavaConverterTypeDefinition() {
		super();
	}

	public Class<? extends JavaConverterType2_1> getType() {
		return JavaConverterType2_1.class;
	}

	public Iterable<String> getAnnotationNames(JpaProject jpaProject) {
		return IterableTools.singletonIterable(Converter2_1Annotation.ANNOTATION_NAME);
	}

	public JavaConverterType2_1 buildContextManagedType(JpaContextNode parent, JavaResourceType jrt, JpaFactory factory) {
		return ((JpaFactory2_1) factory).buildJavaConverterType(parent, jrt);
	}
}
