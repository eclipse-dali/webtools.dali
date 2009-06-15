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
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;
import org.eclipse.jpt2_0.core.internal.Orm2_0ResourceModelProvider;
import org.eclipse.jpt2_0.core.internal.Persistence2_0ResourceModelProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformProvider extends AbstractJpaPlatformProvider
{
	public static final String ID = "generic"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = new Generic2_0JpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private Generic2_0JpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	@Override
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(Persistence2_0ResourceModelProvider.instance());
		providers.add(Orm2_0ResourceModelProvider.instance());
	}


	// ********** Java type mappings **********
	
	@Override
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
	}


	// ********** Java attribute mappings **********

	@Override
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		//providers.add(JavaElementCollectionMappingProvider.instance());
	}


	// ********** default Java attribute mappings **********

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
	}

	// ********** Mapping File **********

	/**
	 * Override this to specify more or different mapping file providers.
	 */
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		//providers.add(Generic2_0MappingFileProvider.instance());
	}


	// ********** ORM type mappings **********

	@Override
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
	}


	// ********** ORM attribute mappings **********

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		//providers.add(OrmElementCollectionMappingProvider.instance());
	}
}
