/*******************************************************************************
 * Copyright (c) 2013, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaManagedTypeDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;

public class JavaPersistentTypeDefinition
	implements JavaManagedTypeDefinition
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

	public Class<PersistentType> getManagedTypeType() {
		return PersistentType.class;
	}

	public Iterable<String> getAnnotationNames(JpaProject jpaProject) {
		return jpaProject.getTypeMappingAnnotationNames();		
	}

	public JavaPersistentType buildContextManagedType(JpaContextModel parent, JavaResourceType jrt, JpaFactory factory) {
		return factory.buildJavaPersistentType((JavaPersistentType.Parent) parent, jrt);
	}
}
