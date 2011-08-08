/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkBasicCollectionAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkBasicMapAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkCacheAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkChangeTrackingAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkConvertAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkCustomizerAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkExistenceCheckingAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkJoinFetchAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkMutableAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkObjectTypeConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkPrimaryKeyAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkPrivateOwnedAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkReadOnlyAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkReadTransformerAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkStructConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkTransformationAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkTypeConverterAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkVariableOneToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.resource.java.EclipseLinkWriteTransformerAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_1.resource.java.EclipseLinkClassExtractor2_1AnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.EclipseLinkMultitenantAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_3.resource.java.EclipseLinkTenantDiscriminatorColumnsAnnotationDefinition;

/**
 * Provides annotations for 2.3 EclipseLink platform
 */
public class EclipseLink2_3JpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new EclipseLink2_3JpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private EclipseLink2_3JpaAnnotationDefinitionProvider() {
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
		EclipseLinkClassExtractor2_1AnnotationDefinition.instance(),
		EclipseLinkConvertAnnotationDefinition.instance(),
		EclipseLinkConverterAnnotationDefinition.instance(),
		EclipseLinkCustomizerAnnotationDefinition.instance(),
		EclipseLinkExistenceCheckingAnnotationDefinition.instance(),
		EclipseLinkJoinFetchAnnotationDefinition.instance(),
		EclipseLinkMultitenantAnnotationDefinition.instance(),
		EclipseLinkMutableAnnotationDefinition.instance(),
		EclipseLinkObjectTypeConverterAnnotationDefinition.instance(),
		EclipseLinkPrimaryKeyAnnotationDefinition.instance(),
		EclipseLinkPrivateOwnedAnnotationDefinition.instance(),
		EclipseLinkReadOnlyAnnotationDefinition.instance(),
		EclipseLinkReadTransformerAnnotationDefinition.instance(),
		EclipseLinkStructConverterAnnotationDefinition.instance(),
		EclipseLinkTenantDiscriminatorColumnsAnnotationDefinition.instance(),
		EclipseLinkTransformationAnnotationDefinition.instance(),
		EclipseLinkTypeConverterAnnotationDefinition.instance(),
		EclipseLinkVariableOneToOneAnnotationDefinition.instance(),
		EclipseLinkWriteTransformerAnnotationDefinition.instance()
	};

	@Override
	protected void addNestableAnnotationDefinitionsTo(ArrayList<NestableAnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, NESTABLE_ANNOTATION_DEFINITIONS);
	}

	protected static final NestableAnnotationDefinition[]  NESTABLE_ANNOTATION_DEFINITIONS = new NestableAnnotationDefinition[] {
		EclipseLinkTenantDiscriminatorColumnAnnotationDefinition.instance()
	};

}
