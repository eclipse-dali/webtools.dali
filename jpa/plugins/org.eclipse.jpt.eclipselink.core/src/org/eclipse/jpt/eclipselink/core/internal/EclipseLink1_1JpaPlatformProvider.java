/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import java.util.List;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;
import org.eclipse.jpt.eclipselink.core.internal.context.EclipseLink1_1MappingFileProvider;

/**
 * EclipseLink platform
 */
public class EclipseLink1_1JpaPlatformProvider
	extends AbstractJpaPlatformProvider
{
	public static final String ID = "eclipselink1_1"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = new EclipseLink1_1JpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private EclipseLink1_1JpaPlatformProvider() {
		super();
	}

	
	// ********* JPA files *********	
	
	@Override
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(EclipseLink1_1OrmResourceModelProvider.instance());
	}

	
	// ********* java *********	

	@Override
	protected void addJavaTypeMappingProvidersTo(@SuppressWarnings("unused") List<JavaTypeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addJavaAttributeMappingProvidersTo(@SuppressWarnings("unused") List<JavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(@SuppressWarnings("unused") List<DefaultJavaAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}
	
	
	// ********* ORM *********
	
	@Override
	protected void addOrmTypeMappingProvidersTo(@SuppressWarnings("unused") List<OrmTypeMappingProvider> providers) {
		//none specific to EclipseLink		
	}
	
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(EclipseLink1_1MappingFileProvider.instance());
	}

	@Override
	protected void addOrmAttributeMappingProvidersTo(@SuppressWarnings("unused") List<OrmAttributeMappingProvider> providers) {
		//none specific to EclipseLink1.1
	}

}
