/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.platform;

import java.util.List;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.AbstractJpaPlatformUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;

/**
 *  EclipseLink2_0JpaPlatformUiProvider
 */
public class EclipseLink2_0JpaPlatformUiProvider extends AbstractJpaPlatformUiProvider
{

	// singleton
	private static final JpaPlatformUiProvider INSTANCE = new EclipseLink2_0JpaPlatformUiProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLink2_0JpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********
	
	@Override
	protected void addDetailsProvidersTo(List<JpaDetailsProvider> providers) {
		//none specific to EclipseLink 2.0
	}
	
	// ********** structure providers **********
	
	@Override
	protected void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers) {
		//none specific to EclipseLink 2.0
	}

	
	// ********** type mapping ui providers **********

	@Override
	protected void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink 2.0
	}
	
	@Override
	protected void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers) {
		//none specific to EclipseLink 2.0
	}
	
	
	// ********** attribute mapping ui providers **********
	
	@Override
	protected void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		//none specific to EclipseLink 2.0
	}
	
	@Override
	protected void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers) {
		//none specific to EclipseLink 2.0
	}
}