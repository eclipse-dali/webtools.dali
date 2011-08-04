/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2.context.java;

import org.eclipse.jpt.common.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.common.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaAttributeMappingDefinitionWrapper;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaIdMappingDefinition;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.Access2_0Annotation;

/**
 * JPA 2.0 Id mapping
 */
public class JavaIdMappingDefinition2_0
	extends JavaAttributeMappingDefinitionWrapper
{
	private static final JavaAttributeMappingDefinition DELEGATE = JavaIdMappingDefinition.instance();

	// singleton
	private static final JavaAttributeMappingDefinition INSTANCE = new JavaIdMappingDefinition2_0();

	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private JavaIdMappingDefinition2_0() {
		super();
	}

	@Override
	protected JavaAttributeMappingDefinition getDelegate() {
		return DELEGATE;
	}

	/**
	 * The annotation is "specified" only if it is not "derived" (i.e.
	 * accompanied by a M-1 or 1-1 annotation).
	 */
	@Override
	public boolean isSpecified(JavaPersistentAttribute persistentAttribute) {
		boolean idSpecified = super.isSpecified(persistentAttribute);
		return idSpecified && ! this.isDerivedId(persistentAttribute);
	}

	/**
	 * Return whether the specified attribute's <code>Id</code> annotation is
	 * a supporting annotation for M-1 or 1-1 mapping, as opposed to a primary
	 * mapping annotation.
	 * <p>
	 * This might produce confusing behavior if the annotations look something
	 * like:<pre>
	 *     @Id @Basic @ManyToOne private int foo;
	 * </pre>
	 */
	private boolean isDerivedId(JavaPersistentAttribute persistentAttribute) {
		return this.attributeHasManyToOneMapping(persistentAttribute) ||
			this.attributeHasOneToOneMapping(persistentAttribute);
	}

	private boolean attributeHasManyToOneMapping(JavaPersistentAttribute persistentAttribute) {
		return JavaManyToOneMappingDefinition2_0.instance().isSpecified(persistentAttribute);
	}

	private boolean attributeHasOneToOneMapping(JavaPersistentAttribute persistentAttribute) {
		return JavaOneToOneMappingDefinition2_0.instance().isSpecified(persistentAttribute);
	}

	@Override
	public Iterable<String> getSupportingAnnotationNames() {
		return COMBINED_SUPPORTING_ANNOTATION_NAMES;
	}

	public static final String[] SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0 = new String[] {
		Access2_0Annotation.ANNOTATION_NAME,
	};
	private static final Iterable<String> SUPPORTING_ANNOTATION_NAMES_2_0 = new ArrayIterable<String>(SUPPORTING_ANNOTATION_NAMES_ARRAY_2_0);

	@SuppressWarnings("unchecked")
	private static final Iterable<String> COMBINED_SUPPORTING_ANNOTATION_NAMES = new CompositeIterable<String>(
		DELEGATE.getSupportingAnnotationNames(),
		SUPPORTING_ANNOTATION_NAMES_2_0
	);

}
