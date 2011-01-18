/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v1_2;

import java.util.ArrayList;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.jpa2.resource.java.Access2_0AnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;

/**
 * Provides annotations for 1.2 EclipseLink platform
 */
public class EclipseLink1_2JpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new EclipseLink1_2JpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLink1_2JpaAnnotationDefinitionProvider() {
		super();
	}

	@Override
	protected void addTypeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		definitions.add(Access2_0AnnotationDefinition.instance());
	}

	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		// nothing new for EclipseLink 1.2
	}

	@Override
	protected void addAttributeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		// nothing new for EclipseLink 1.2
	}

	@Override
	protected void addPackageAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		// no package annotations
	}
}
