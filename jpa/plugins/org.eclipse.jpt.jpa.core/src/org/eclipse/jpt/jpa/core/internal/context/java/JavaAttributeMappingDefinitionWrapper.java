/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.java;

import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;

/**
 * Simplify delegation to another definition.
 * A subclass need only implement {@link #getDelegate()} and override the
 * appropriate method(s).
 */
public abstract class JavaAttributeMappingDefinitionWrapper
	implements JavaAttributeMappingDefinition
{
	protected JavaAttributeMappingDefinitionWrapper() {
		super();
	}

	protected abstract JavaAttributeMappingDefinition getDelegate();

	public String getKey() {
		return this.getDelegate().getKey();
	}

	public String getAnnotationName() {
		return this.getDelegate().getAnnotationName();
	}

	public boolean isSpecified(JavaPersistentAttribute persistentAttribute) {
		return this.getDelegate().isSpecified(persistentAttribute);
	}

	public Iterable<String> getSupportingAnnotationNames() {
		return this.getDelegate().getSupportingAnnotationNames();
	}

	public JavaAttributeMapping buildMapping(JavaPersistentAttribute persistentAttribute, JpaFactory factory) {
		return this.getDelegate().buildMapping(persistentAttribute, factory);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
