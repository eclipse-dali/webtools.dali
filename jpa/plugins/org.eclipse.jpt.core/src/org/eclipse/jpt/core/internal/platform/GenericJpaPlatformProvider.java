/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.GenericMappingFileProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmEntityProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmIdMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.orm.OrmVersionMappingProvider;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public class GenericJpaPlatformProvider extends AbstractJpaPlatformProvider
{
	public static final String ID = "generic"; //$NON-NLS-1$

	// singleton
	private static final JpaPlatformProvider INSTANCE = new GenericJpaPlatformProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	@Override
	protected void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers) {
		providers.add(JavaResourceModelProvider.instance());
		providers.add(PersistenceResourceModelProvider.instance());
		providers.add(OrmResourceModelProvider.instance());
	}


	// ********** Java type mappings **********
	
	@Override
	protected void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers) {
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaNullTypeMappingProvider.instance());
	}


	// ********** Java attribute mappings **********

	@Override
	protected void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers) {
		providers.add(JavaBasicMappingProvider.instance());
		providers.add(JavaEmbeddedMappingProvider.instance());
		providers.add(JavaEmbeddedIdMappingProvider.instance());
		providers.add(JavaIdMappingProvider.instance());
		providers.add(JavaManyToManyMappingProvider.instance());
		providers.add(JavaManyToOneMappingProvider.instance());
		providers.add(JavaOneToManyMappingProvider.instance());
		providers.add(JavaOneToOneMappingProvider.instance());
		providers.add(JavaTransientMappingProvider.instance());
		providers.add(JavaVersionMappingProvider.instance());
	}


	// ********** default Java attribute mappings **********

	@Override
	protected void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers) {
		providers.add(JavaEmbeddedMappingProvider.instance());  // bug 190344 need to test default embedded before basic
		providers.add(JavaBasicMappingProvider.instance());
	}

	// ********** Mapping File **********

	/**
	 * Override this to specify more or different mapping file providers.
	 */
	@Override
	protected void addMappingFileProvidersTo(List<MappingFileProvider> providers) {
		providers.add(GenericMappingFileProvider.instance());
	}


	// ********** ORM type mappings **********

	@Override
	protected void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers) {
		providers.add(OrmEmbeddableProvider.instance());
		providers.add(OrmEntityProvider.instance());
		providers.add(OrmMappedSuperclassProvider.instance());
	}


	// ********** ORM attribute mappings **********

	@Override
	protected void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers) {
		providers.add(OrmEmbeddedMappingProvider.instance());  // bug 190344 need to test default embedded before basic
		providers.add(OrmBasicMappingProvider.instance());
		providers.add(OrmTransientMappingProvider.instance());
		providers.add(OrmIdMappingProvider.instance());
		providers.add(OrmManyToManyMappingProvider.instance());
		providers.add(OrmOneToManyMappingProvider.instance());
		providers.add(OrmManyToOneMappingProvider.instance());
		providers.add(OrmOneToOneMappingProvider.instance());
		providers.add(OrmVersionMappingProvider.instance());
		providers.add(OrmEmbeddedIdMappingProvider.instance());
	}
}
