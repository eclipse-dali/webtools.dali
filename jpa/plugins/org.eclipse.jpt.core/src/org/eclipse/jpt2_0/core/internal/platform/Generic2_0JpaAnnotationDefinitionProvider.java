/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt2_0.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaAnnotationDefintionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt2_0.core.internal.resource.java.AccessAnnotationDefinition;

/**
 * Provides annotations only for JPA 2.0
 */
public class Generic2_0JpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefintionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new Generic2_0JpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0JpaAnnotationDefinitionProvider() {
		super();
	}

	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		//none
	}
	
	@Override
	protected void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AccessAnnotationDefinition.instance());
	}

	@Override
	protected void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		//none
	}
	
	@Override
	protected void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AccessAnnotationDefinition.instance());
	}
}
