/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.platform.AbstractJpaAnnotationDefintionProvider;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.BasicCollectionAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.BasicMapAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.CacheAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ChangeTrackingAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ConvertAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.CustomizerAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ExistenceCheckingAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.JoinFetchAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.MutableAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ObjectTypeConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.PrivateOwnedAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ReadOnlyAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.ReadTransformerAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.StructConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.TransformationAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.TypeConverterAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.VariableOneToOneAnnotationDefinition;
import org.eclipse.jpt.eclipselink.core.internal.resource.java.WriteTransformerAnnotationDefinition;

/**
 * Provides annotations for 1.0 EclipseLink platform which includes JPA annotations and
 * EclipseLink 1.0 annotations
 */
public class EclipseLinkJpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefintionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new EclipseLinkJpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaAnnotationDefinitionProvider() {
		super();
	}
	
	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		//none
	}
	
	@Override
	protected void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(CacheAnnotationDefinition.instance());
		definitions.add(ChangeTrackingAnnotationDefinition.instance());
		definitions.add(ConverterAnnotationDefinition.instance());
		definitions.add(CustomizerAnnotationDefinition.instance());
		definitions.add(ExistenceCheckingAnnotationDefinition.instance());
		definitions.add(ObjectTypeConverterAnnotationDefinition.instance());
		definitions.add(ReadOnlyAnnotationDefinition.instance());
		definitions.add(StructConverterAnnotationDefinition.instance());		
		definitions.add(TypeConverterAnnotationDefinition.instance());		
	}

	// 245996 addresses how the attribute mapping annotations should be ordered
	@Override
	protected void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(BasicCollectionAnnotationDefinition.instance());
		definitions.add(BasicMapAnnotationDefinition.instance());
		definitions.add(TransformationAnnotationDefinition.instance());
		definitions.add(VariableOneToOneAnnotationDefinition.instance());
	}
	
	@Override
	protected void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(ConvertAnnotationDefinition.instance());
		definitions.add(ConverterAnnotationDefinition.instance());
		definitions.add(JoinFetchAnnotationDefinition.instance());
		definitions.add(MutableAnnotationDefinition.instance());
		definitions.add(ObjectTypeConverterAnnotationDefinition.instance());
		definitions.add(PrivateOwnedAnnotationDefinition.instance());
		definitions.add(ReadTransformerAnnotationDefinition.instance());		
		definitions.add(StructConverterAnnotationDefinition.instance());		
		definitions.add(TypeConverterAnnotationDefinition.instance());		
		definitions.add(WriteTransformerAnnotationDefinition.instance());		
	}
}
