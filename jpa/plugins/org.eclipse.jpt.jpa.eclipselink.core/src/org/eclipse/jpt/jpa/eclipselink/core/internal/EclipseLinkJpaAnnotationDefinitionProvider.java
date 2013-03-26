/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal;

import java.util.ArrayList;

import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkBasicCollectionAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkBasicMapAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkCacheAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkChangeTrackingAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkConvertAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkConvertersAnnotationDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkCustomizerAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkExistenceCheckingAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkJoinFetchAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkMutableAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkObjectTypeConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkObjectTypeConvertersAnnotationDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkPrimaryKeyAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkPrivateOwnedAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkReadOnlyAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkReadTransformerAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkStructConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkStructConvertersAnnotationDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkTransformationAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkTypeConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkTypeConvertersAnnotationDefinition2_2;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkVariableOneToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkWriteTransformerAnnotationDefinition;

/**
 * Provides annotations for 1.0 EclipseLink platform
 */
public class EclipseLinkJpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new EclipseLinkJpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkJpaAnnotationDefinitionProvider() {
		super();
	}

	@Override
	protected void addAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		EclipseLinkBasicCollectionAnnotationDefinition.instance(),
		EclipseLinkBasicMapAnnotationDefinition.instance(),
		EclipseLinkCacheAnnotationDefinition.instance(),
		EclipseLinkChangeTrackingAnnotationDefinition.instance(),
		EclipseLinkConvertAnnotationDefinition.instance(),
		EclipseLinkConvertersAnnotationDefinition2_2.instance(), //Bug 380182 - not supported before EL 2.2, but can handle with validation
		EclipseLinkCustomizerAnnotationDefinition.instance(),
		EclipseLinkExistenceCheckingAnnotationDefinition.instance(),
		EclipseLinkJoinFetchAnnotationDefinition.instance(),
		EclipseLinkMutableAnnotationDefinition.instance(),
		EclipseLinkObjectTypeConvertersAnnotationDefinition2_2.instance(), //Bug 380182 - not supported before EL 2.2, but can handle with validation
		EclipseLinkPrimaryKeyAnnotationDefinition.instance(),
		EclipseLinkPrivateOwnedAnnotationDefinition.instance(),
		EclipseLinkReadOnlyAnnotationDefinition.instance(),
		EclipseLinkReadTransformerAnnotationDefinition.instance(),
		EclipseLinkStructConvertersAnnotationDefinition2_2.instance(), //Bug 380182 - not supported before EL 2.2, but can handle with validation
		EclipseLinkTransformationAnnotationDefinition.instance(),
		EclipseLinkTypeConvertersAnnotationDefinition2_2.instance(), //Bug 380182 - not supported before EL 2.2, but can handle with validation
		EclipseLinkVariableOneToOneAnnotationDefinition.instance(),
		EclipseLinkWriteTransformerAnnotationDefinition.instance()
	};


	@Override
	protected void addNestableAnnotationDefinitionsTo(ArrayList<NestableAnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, NESTABLE_ANNOTATION_DEFINITIONS);
	}

	protected static final NestableAnnotationDefinition[] NESTABLE_ANNOTATION_DEFINITIONS = new NestableAnnotationDefinition[] {
		EclipseLinkConverterAnnotationDefinition.instance(),
		EclipseLinkObjectTypeConverterAnnotationDefinition.instance(),
		EclipseLinkStructConverterAnnotationDefinition.instance(),
		EclipseLinkTypeConverterAnnotationDefinition.instance(),
	};

}
