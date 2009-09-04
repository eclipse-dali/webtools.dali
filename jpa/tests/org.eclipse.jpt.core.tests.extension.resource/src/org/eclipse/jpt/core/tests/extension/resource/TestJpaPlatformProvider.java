/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.extension.resource;

import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.internal.AbstractJpaPlatformProvider;
import org.eclipse.jpt.core.internal.JarResourceModelProvider;
import org.eclipse.jpt.core.internal.JavaResourceModelProvider;
import org.eclipse.jpt.core.internal.OrmResourceModelProvider;
import org.eclipse.jpt.core.internal.PersistenceResourceModelProvider;
import org.eclipse.jpt.core.internal.context.java.JavaBasicMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddableProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEmbeddedMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaEntityProvider;
import org.eclipse.jpt.core.internal.context.java.JavaIdMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaManyToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaMappedSuperclassProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToManyMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaOneToOneMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaTransientMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaVersionMappingProvider;
import org.eclipse.jpt.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;

public class TestJpaPlatformProvider extends AbstractJpaPlatformProvider
{
	public static final String ID = "core.testJpaPlatform"; //$NON-NLS-1$
	
	// singleton
	private static final JpaPlatformProvider INSTANCE = 
			new TestJpaPlatformProvider();
	
	
	/**
	 * Return the singleton.
	 */
	public static JpaPlatformProvider instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private TestJpaPlatformProvider() {
		super();
	}
	
	
	// ********** resource models **********
	
	@Override
	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		// order should not be important here
		return new JpaResourceModelProvider[] {
			JavaResourceModelProvider.instance(),
			JarResourceModelProvider.instance(),
			PersistenceResourceModelProvider.instance(),
			OrmResourceModelProvider.instance()};
	}
	
	
	// ********** Java type mappings **********
	
	@Override
	protected JavaTypeMappingProvider[] buildNonNullJavaTypeMappingProviders() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaTypeMappingProvider[] {
			JavaEntityProvider.instance(),
			JavaEmbeddableProvider.instance(),
			JavaMappedSuperclassProvider.instance(),
			JavaTestTypeMappingProvider.instance()};  // added
	}


	// ********** Java attribute mappings **********
	@Override
	protected JavaAttributeMappingProvider[] buildNonNullDefaultJavaAttributeMappingProviders() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaAttributeMappingProvider[] {
			JavaEmbeddedMappingProvider.instance(),
			JavaBasicMappingProvider.instance()};
	}
	
	@Override
	protected JavaAttributeMappingProvider[] buildNonNullSpecifiedJavaAttributeMappingProviders() {
		// order determined by analyzing order that reference implementation (toplink) uses
		return new JavaAttributeMappingProvider[] {
			JavaTransientMappingProvider.instance(),
			JavaIdMappingProvider.instance(),
			JavaVersionMappingProvider.instance(),
			JavaBasicMappingProvider.instance(),
			JavaEmbeddedMappingProvider.instance(),
			JavaEmbeddedIdMappingProvider.instance(),
			JavaManyToManyMappingProvider.instance(),
			JavaManyToOneMappingProvider.instance(),
			JavaOneToManyMappingProvider.instance(),
			JavaOneToOneMappingProvider.instance(),
			JavaTestAttributeMappingProvider.instance()}; // added
	}
	
	// ********** Mapping Files **********
	
	@Override
	protected MappingFileDefinition[] buildMappingFileDefinitions() {
		return new MappingFileDefinition[] {
			GenericOrmXmlDefinition.instance()};
	}
}
