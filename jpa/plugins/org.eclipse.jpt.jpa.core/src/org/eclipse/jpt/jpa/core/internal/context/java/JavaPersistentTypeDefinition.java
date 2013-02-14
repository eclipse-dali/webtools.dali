/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.persistence.ClassRef;

public class JavaPersistentTypeDefinition implements JavaManagedTypeDefinition
{
	// singleton
	private static final JavaManagedTypeDefinition INSTANCE = new JavaPersistentTypeDefinition();

	/**
	 * Return the singleton.
	 */
	public static JavaManagedTypeDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private JavaPersistentTypeDefinition() {
		super();
	}

	public Class<? extends JavaPersistentType> getType() {
		return JavaPersistentType.class;
	}

	public Iterable<String> getAnnotationNames(JpaProject jpaProject) {
		return jpaProject.getTypeMappingAnnotationNames();		
	}

	public JavaPersistentType buildContextManagedType(JpaContextNode parent, JavaResourceType jrt, JpaFactory factory) {
		return factory.buildJavaPersistentType((ClassRef) parent, jrt);
	}
}
