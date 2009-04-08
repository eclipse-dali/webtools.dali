/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.utility.internal.StringTools;

public class JavaEmbeddableProvider
	implements JavaTypeMappingProvider
{

	// singleton
	private static final JavaEmbeddableProvider INSTANCE = new JavaEmbeddableProvider();

	/**
	 * Return the singleton.
	 */
	public static JavaTypeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private JavaEmbeddableProvider() {
		super();
	}

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}
	
	public String getAnnotationName() {
		return EmbeddableAnnotation.ANNOTATION_NAME;
	}

	public JavaEmbeddable buildMapping(JavaPersistentType parent, JpaFactory factory) {
		return factory.buildJavaEmbeddable(parent);
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this, this.getAnnotationName());
	}

}
