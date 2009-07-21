/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.platform;

import java.util.List;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.jpa2.Orm2_0ResourceModelProvider;
import org.eclipse.jpt.core.internal.jpa2.Persistence2_0ResourceModelProvider;
import org.eclipse.jpt.core.internal.jpa2.context.Generic2_0MappingFileProvider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmBasicMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddableMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddedIdMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEmbeddedMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmEntityMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmIdMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmManyToManyMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmManyToOneMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmMappedSuperclassMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmNullAttributeMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToManyMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToOneMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmTransientMapping2_0Provider;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmVersionMapping2_0Provider;
import org.eclipse.jpt.core.internal.platform.AbstractJpaPlatformProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class Generic2_0JpaPlatformProvider extends AbstractJpaPlatformProvider
{
	public static final String ID = "generic2_0"; //$NON-NLS-1$

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
		providers.add(Generic2_0MappingFileProvider.instance());
	}


	// ********** ORM type mappings **********

	@Override
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
		providers.add(GenericOrmEmbeddableMapping2_0Provider.instance());
		providers.add(GenericOrmEntityMapping2_0Provider.instance());
		providers.add(GenericOrmMappedSuperclassMapping2_0Provider.instance());
	}


	// ********** ORM attribute mappings **********

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(GenericOrmBasicMapping2_0Provider.instance());
		providers.add(GenericOrmIdMapping2_0Provider.instance());
		//providers.add(OrmElementCollectionMappingProvider.instance());
		providers.add(GenericOrmEmbeddedIdMapping2_0Provider.instance());
		providers.add(GenericOrmEmbeddedMapping2_0Provider.instance());
		providers.add(GenericOrmManyToManyMapping2_0Provider.instance());
		providers.add(GenericOrmManyToOneMapping2_0Provider.instance());
		providers.add(GenericOrmOneToManyMapping2_0Provider.instance());
		providers.add(GenericOrmOneToOneMapping2_0Provider.instance());
		providers.add(GenericOrmVersionMapping2_0Provider.instance());
		providers.add(GenericOrmTransientMapping2_0Provider.instance());
		providers.add(GenericOrmNullAttributeMapping2_0Provider.instance());
	}
}
