/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.java.mappings;

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.IPersistentType;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.content.java.IJavaAttributeMappingProvider;
import org.eclipse.jpt.core.internal.jdtutility.Attribute;
import org.eclipse.jpt.core.internal.jdtutility.DeclarationAnnotationAdapter;
import org.eclipse.jpt.core.internal.mappings.IEmbeddable;
import org.eclipse.jpt.core.internal.platform.DefaultsContext;

/**
 * 
 */
public class JavaEmbeddedProvider
	implements IJavaAttributeMappingProvider
{

	// singleton
	private static final JavaEmbeddedProvider INSTANCE = new JavaEmbeddedProvider();

	/**
	 * Return the singleton.
	 */
	public static IJavaAttributeMappingProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private JavaEmbeddedProvider() {
		super();
	}

	public String key() {
		return IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY;
	}
	
	public boolean defaultApplies(Attribute attribute, DefaultsContext defaultsContext) {
		return embeddableFor(attribute, defaultsContext) != null;
	}
	
	private IEmbeddable embeddableFor(Attribute attribute, DefaultsContext defaultsContext) {
		String resolvedTypeName = attribute.resolvedTypeName();
		if (resolvedTypeName == null) {
			return null;
		}
		IPersistentType persistentType = defaultsContext.persistentType(resolvedTypeName);
		if (persistentType != null && persistentType.getMappingKey() == IMappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY) {
			return (IEmbeddable) persistentType.getMapping();
		}
		return null;
	}

	public IJavaAttributeMapping buildMapping(Attribute attribute) {
		return JpaJavaMappingsFactory.eINSTANCE.createJavaEmbedded(attribute);
	}

	public DeclarationAnnotationAdapter declarationAnnotationAdapter() {
		return JavaEmbedded.DECLARATION_ANNOTATION_ADAPTER;
	}

}
