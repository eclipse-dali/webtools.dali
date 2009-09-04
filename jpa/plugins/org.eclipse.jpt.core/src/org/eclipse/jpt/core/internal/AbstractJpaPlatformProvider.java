/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ListIterator;

import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.java.NullDefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.NullJavaTypeMappingProvider;
import org.eclipse.jpt.core.context.java.NullSpecifiedJavaAttributeMappingProvider;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformProvider
	implements JpaPlatformProvider
{
	private JpaResourceModelProvider[] resourceModelProviders;

	private JavaTypeMappingProvider[] javaTypeMappingProviders;

	private JavaAttributeMappingProvider[] specifiedJavaAttributeMappingProviders;

	private JavaAttributeMappingProvider[] defaultJavaAttributeMappingProviders;

	private MappingFileDefinition[] mappingFileDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJpaPlatformProvider() {
		super();
	}


	// ********** resource models **********
	
	public ListIterator<JpaResourceModelProvider> resourceModelProviders() {
		return new ArrayListIterator<JpaResourceModelProvider>(getResourceModelProviders());
	}
	
	protected synchronized JpaResourceModelProvider[] getResourceModelProviders() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}
	
	protected abstract JpaResourceModelProvider[] buildResourceModelProviders();
	
	
	// ********** Java type mappings **********
	
	public ListIterator<JavaTypeMappingProvider> javaTypeMappingProviders() {
		return new ArrayListIterator<JavaTypeMappingProvider>(getJavaTypeMappingProviders());
	}
	
	protected synchronized JavaTypeMappingProvider[] getJavaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = this.buildJavaTypeMappingProviders();
		}
		return this.javaTypeMappingProviders;
	}
	
	/**
	 * Return an array of mapping providers to use for analyzing the mapping of a type given all 
	 * annotations on it.  The order is important, as once a mapping provider tests positive for an 
	 * attribute, all following mapping providers are ignored.
	 * Extenders may either overwrite this method or {@link #buildNonNullJavaTypeMappingProviders()}.
	 * Doing the former places the additional requirement on the extender to provide a "null"
	 * mapping provider (@see {@link NullJavaTypeMappingProvider}.)
	 */
	protected JavaTypeMappingProvider[] buildJavaTypeMappingProviders() {
		return ArrayTools.add(
			buildNonNullJavaTypeMappingProviders(), 
			NullJavaTypeMappingProvider.instance());
	}
	
	/**
	 * No-op implementation of this method. 
	 * @see #buildJavaTypeMappingProviders()
	 */
	protected JavaTypeMappingProvider[] buildNonNullJavaTypeMappingProviders() {
		return new JavaTypeMappingProvider[0];
	}
	
	
	// ********** Java attribute mappings **********
	
	public ListIterator<JavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		return new ArrayListIterator<JavaAttributeMappingProvider>(getDefaultJavaAttributeMappingProviders());
	}

	protected synchronized JavaAttributeMappingProvider[] getDefaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = this.buildDefaultJavaAttributeMappingProviders();
		}
		return this.defaultJavaAttributeMappingProviders;
	}
	
	/**
	 * Return an array of mapping providers to use for analyzing the default mapping of an attribute
	 * in the absence of any annotations.  The order is important, as once a mapping provider tests
	 * positively for a given attribute, all following mapping providers are ignored.
	 * Extenders may either overwrite this method or 
	 * {@link #buildNonNullDefaultJavaAttributeMappingProviders()}.
	 * Doing the former places the additional requirement on the extender to provide a "null"
	 * mapping provider (@see {@link NullDefaultJavaAttributeMappingProvider}.)
	 */
	protected JavaAttributeMappingProvider[] buildDefaultJavaAttributeMappingProviders() {
		return ArrayTools.add(
			buildNonNullDefaultJavaAttributeMappingProviders(), 
			NullDefaultJavaAttributeMappingProvider.instance());
	}
	
	/**
	 * No-op implementation of this method. 
	 * @see #buildDefaultJavaAttributeMappingProviders()
	 */
	protected JavaAttributeMappingProvider[] buildNonNullDefaultJavaAttributeMappingProviders() {
		return new JavaAttributeMappingProvider[0];
	}
	
	public ListIterator<JavaAttributeMappingProvider> specifiedJavaAttributeMappingProviders() {
		return new ArrayListIterator<JavaAttributeMappingProvider>(
			getSpecifiedJavaAttributeMappingProviders());
	}
	
	protected synchronized JavaAttributeMappingProvider[] getSpecifiedJavaAttributeMappingProviders() {
		if (this.specifiedJavaAttributeMappingProviders == null) {
			this.specifiedJavaAttributeMappingProviders = this.buildSpecifiedJavaAttributeMappingProviders();
		}
		return this.specifiedJavaAttributeMappingProviders;
	}
	
	/**
	 * Return an array of mapping providers to use for analyzing the specified mapping of an attribute
	 * given all annotations on it.  The order is important, as once a mapping provider tests
	 * positively for a given attribute, all following mapping providers are ignored.
	 * Extenders may either overwrite this method or 
	 * {@link #buildNonNullSpecifiedJavaAttributeMappingProviders()}.
	 * Doing the former places the additional requirement on the extender to provide a "null"
	 * mapping provider (@see {@link NullSpecifiedJavaAttributeMappingProvider}.)
	 */
	protected JavaAttributeMappingProvider[] buildSpecifiedJavaAttributeMappingProviders() {
		return ArrayTools.add(
			buildNonNullSpecifiedJavaAttributeMappingProviders(), 
			NullSpecifiedJavaAttributeMappingProvider.instance());
	}
	
	/**
	 * No-op implementation of this method. 
	 * @see #buildSpecifiedJavaAttributeMappingProviders()
	 */
	protected JavaAttributeMappingProvider[] buildNonNullSpecifiedJavaAttributeMappingProviders() {
		return new JavaAttributeMappingProvider[0];
	}
	
	
	// ********** Mapping Files **********
	
	public ListIterator<MappingFileDefinition> mappingFileDefinitions() {
		return new ArrayListIterator<MappingFileDefinition>(getMappingFileDefinitions());
	}
	
	protected synchronized MappingFileDefinition[] getMappingFileDefinitions() {
		if (this.mappingFileDefinitions == null) {
			this.mappingFileDefinitions = this.buildMappingFileDefinitions();
		}
		return this.mappingFileDefinitions;
	}
	
	protected abstract MappingFileDefinition[] buildMappingFileDefinitions();
}
