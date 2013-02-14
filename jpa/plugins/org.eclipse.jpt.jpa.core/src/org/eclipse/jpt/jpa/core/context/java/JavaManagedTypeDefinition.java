/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.jpa.core.JpaFactory;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.ManagedType;

/**
 * Map a type to a context managed type and its corresponding
 * Java annotations.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 3.3
 * @since 3.3
 */
public interface JavaManagedTypeDefinition
{
	/**
	 * Return a class that corresponds to the managed type.
	 * @see ManagedType#getType()
	 */
	Class<? extends JavaManagedType> getType();

	/**
	 * Return the names of the annotations applicable to this managed type
	 */
	Iterable<String> getAnnotationNames(JpaProject jpaProject);

	class AnnotationNameTransformer
		extends TransformerAdapter<JavaManagedTypeDefinition, Iterable<String>>
	{
		private JpaProject jpaProject;

		public AnnotationNameTransformer(JpaProject jpaProject) {
			super();
			this.jpaProject = jpaProject;
		}
		@Override
		public Iterable<String> transform(JavaManagedTypeDefinition def) {
			return def.getAnnotationNames(this.jpaProject);
		}
	}

	/**
	 * Return a new <code>JavaManagedType</code>
	 */
	JavaManagedType buildContextManagedType(JpaContextNode parent, JavaResourceType jrt, JpaFactory factory);
}
