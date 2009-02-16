/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import java.util.List;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkEntityMappingsDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEmbeddableUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmEntityUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmMappedSuperclassUiProvider;
import org.eclipse.jpt.eclipselink.ui.internal.orm.details.EclipseLinkOrmPersistentAttributeDetailsProvider;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkOrmResourceModelStructureProvider;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

public class EclipseLinkJpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLinkJpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLinkJpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		providers.add(EclipseLinkEntityMappingsDetailsProvider.instance());
		providers.add(EclipseLinkOrmPersistentAttributeDetailsProvider.instance());
	}
	
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		providers.add(EclipseLinkOrmResourceModelStructureProvider.instance());
	}
	
	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		providers.add(EclipseLinkOrmEntityUiProvider.instance());
		providers.add(EclipseLinkOrmMappedSuperclassUiProvider.instance());
		providers.add(EclipseLinkOrmEmbeddableUiProvider.instance());
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink
	}
}
