/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.AbstractJpaAnnotationDefintionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkBasicCollectionAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkBasicMapAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkCacheAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkChangeTrackingAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkConvertAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkCustomizerAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkExistenceCheckingAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkJoinFetchAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkMutableAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkObjectTypeConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkPrimaryKeyAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkPrivateOwnedAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkReadOnlyAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkReadTransformerAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkStructConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkTransformationAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkTypeConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkVariableOneToOneAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.EclipseLinkWriteTransformerAnnotationDefinition;

/**
 * Provides annotations for 1.0 EclipseLink platform
 */
public class EclipseLinkJpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefintionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = 
			new EclipseLinkJpaAnnotationDefinitionProvider();
	
	
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
	protected void addTypeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(EclipseLinkCacheAnnotationDefinition.instance());
		definitions.add(EclipseLinkChangeTrackingAnnotationDefinition.instance());
		definitions.add(EclipseLinkConverterAnnotationDefinition.instance());
		definitions.add(EclipseLinkCustomizerAnnotationDefinition.instance());
		definitions.add(EclipseLinkExistenceCheckingAnnotationDefinition.instance());
		definitions.add(EclipseLinkObjectTypeConverterAnnotationDefinition.instance());
		definitions.add(EclipseLinkPrimaryKeyAnnotationDefinition.instance());
		definitions.add(EclipseLinkReadOnlyAnnotationDefinition.instance());
		definitions.add(EclipseLinkStructConverterAnnotationDefinition.instance());		
		definitions.add(EclipseLinkTypeConverterAnnotationDefinition.instance());		
	}
	
	@Override
	protected void addAttributeAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(EclipseLinkBasicCollectionAnnotationDefinition.instance());
		definitions.add(EclipseLinkBasicMapAnnotationDefinition.instance());
		definitions.add(EclipseLinkConvertAnnotationDefinition.instance());
		definitions.add(EclipseLinkConverterAnnotationDefinition.instance());
		definitions.add(EclipseLinkJoinFetchAnnotationDefinition.instance());
		definitions.add(EclipseLinkMutableAnnotationDefinition.instance());
		definitions.add(EclipseLinkObjectTypeConverterAnnotationDefinition.instance());
		definitions.add(EclipseLinkPrivateOwnedAnnotationDefinition.instance());
		definitions.add(EclipseLinkReadTransformerAnnotationDefinition.instance());		
		definitions.add(EclipseLinkStructConverterAnnotationDefinition.instance());		
		definitions.add(EclipseLinkTransformationAnnotationDefinition.instance());
		definitions.add(EclipseLinkTypeConverterAnnotationDefinition.instance());		
		definitions.add(EclipseLinkVariableOneToOneAnnotationDefinition.instance());
		definitions.add(EclipseLinkWriteTransformerAnnotationDefinition.instance());		
	}
}
