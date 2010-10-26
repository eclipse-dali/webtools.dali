/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal;

import org.eclipse.jpt.jaxb.core.JaxbPlatformProvider;
import org.eclipse.jpt.jaxb.core.JaxbResourceModelProvider;
import org.eclipse.jpt.utility.internal.iterables.ArrayListIterable;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;

/**
 * All the state in the JAXB platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJaxbPlatformProvider
	implements JaxbPlatformProvider
{
	private JaxbResourceModelProvider[] resourceModelProviders;
//
//	private JavaTypeMappingDefinition[] javaTypeMappingDefinitions;
//
//	private JavaAttributeMappingDefinition[] specifiedJavaAttributeMappingDefinitions;
//
//	private JavaAttributeMappingDefinition[] defaultJavaAttributeMappingDefinitions;
//
//	private ResourceDefinition[] resourceDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJaxbPlatformProvider() {
		super();
	}


	// ********** resource models **********
	public ListIterable<JaxbResourceModelProvider> getResourceModelProviders() {
		return new ArrayListIterable<JaxbResourceModelProvider>(getResourceModelProviders_());
	}
	
	protected synchronized JaxbResourceModelProvider[] getResourceModelProviders_() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}
	
	protected abstract JaxbResourceModelProvider[] buildResourceModelProviders();
	
	
//	// ********** Java type mappings **********
//	
//	public ListIterator<JavaTypeMappingDefinition> javaTypeMappingDefinitions() {
//		return new ArrayListIterator<JavaTypeMappingDefinition>(getJavaTypeMappingDefinitions());
//	}
//	
//	protected synchronized JavaTypeMappingDefinition[] getJavaTypeMappingDefinitions() {
//		if (this.javaTypeMappingDefinitions == null) {
//			this.javaTypeMappingDefinitions = this.buildJavaTypeMappingDefinitions();
//		}
//		return this.javaTypeMappingDefinitions;
//	}
//	
//	/**
//	 * Return an array of mapping definitions to use for analyzing the mapping of a type given all 
//	 * annotations on it.  The order is important, as once a mapping definition tests positive for an 
//	 * attribute, all following mapping definitions are ignored.
//	 * Extenders may either overwrite this method or {@link #buildNonNullJavaTypeMappingDefinitions()}.
//	 * Doing the former places the additional requirement on the extender to provide a "null"
//	 * mapping definition (@see {@link NullJavaTypeMappingDefinition}.)
//	 */
//	protected JavaTypeMappingDefinition[] buildJavaTypeMappingDefinitions() {
//		return ArrayTools.add(
//			buildNonNullJavaTypeMappingDefinitions(), 
//			NullJavaTypeMappingDefinition.instance());
//	}
//	
//	/**
//	 * No-op implementation of this method. 
//	 * @see #buildJavaTypeMappingDefinitions()
//	 */
//	protected JavaTypeMappingDefinition[] buildNonNullJavaTypeMappingDefinitions() {
//		return new JavaTypeMappingDefinition[0];
//	}
//	
//	
//	// ********** Java attribute mappings **********
//	
//	public ListIterator<JavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions() {
//		return new ArrayListIterator<JavaAttributeMappingDefinition>(getDefaultJavaAttributeMappingDefinitions());
//	}
//	
//	protected synchronized JavaAttributeMappingDefinition[] getDefaultJavaAttributeMappingDefinitions() {
//		if (this.defaultJavaAttributeMappingDefinitions == null) {
//			this.defaultJavaAttributeMappingDefinitions = this.buildDefaultJavaAttributeMappingDefinitions();
//		}
//		return this.defaultJavaAttributeMappingDefinitions;
//	}
//	
//	/**
//	 * Return an array of mapping definitions to use for analyzing the default mapping of an attribute
//	 * in the absence of any annotations.  The order is important, as once a mapping definition tests
//	 * positively for a given attribute, all following mapping definitions are ignored.
//	 * Extenders may either overwrite this method or 
//	 * {@link #buildNonNullDefaultJavaAttributeMappingDefinitions()}.
//	 * Doing the former places the additional requirement on the extender to provide a "null"
//	 * mapping definition (@see {@link NullDefaultJavaAttributeMappingDefinition}.)
//	 */
//	protected JavaAttributeMappingDefinition[] buildDefaultJavaAttributeMappingDefinitions() {
//		return ArrayTools.add(
//			buildNonNullDefaultJavaAttributeMappingDefinitions(), 
//			NullDefaultJavaAttributeMappingDefinition.instance());
//	}
//	
//	/**
//	 * No-op implementation of this method. 
//	 * @see #buildDefaultJavaAttributeMappingDefinitions()
//	 */
//	protected JavaAttributeMappingDefinition[] buildNonNullDefaultJavaAttributeMappingDefinitions() {
//		return new JavaAttributeMappingDefinition[0];
//	}
//	
//	public ListIterator<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions() {
//		return new ArrayListIterator<JavaAttributeMappingDefinition>(
//			getSpecifiedJavaAttributeMappingDefinitions());
//	}
//	
//	protected synchronized JavaAttributeMappingDefinition[] getSpecifiedJavaAttributeMappingDefinitions() {
//		if (this.specifiedJavaAttributeMappingDefinitions == null) {
//			this.specifiedJavaAttributeMappingDefinitions = this.buildSpecifiedJavaAttributeMappingDefinitions();
//		}
//		return this.specifiedJavaAttributeMappingDefinitions;
//	}
//	
//	/**
//	 * Return an array of mapping definitions to use for analyzing the specified mapping of an attribute
//	 * given all annotations on it.  The order is important, as once a mapping definition tests
//	 * positively for a given attribute, all following mapping definitions are ignored.
//	 * Extenders may either overwrite this method or 
//	 * {@link #buildNonNullSpecifiedJavaAttributeMappingDefinitions()}.
//	 * Doing the former places the additional requirement on the extender to provide a "null"
//	 * mapping definition (@see {@link NullSpecifiedJavaAttributeMappingDefinition}.)
//	 */
//	protected JavaAttributeMappingDefinition[] buildSpecifiedJavaAttributeMappingDefinitions() {
//		return ArrayTools.add(
//			buildNonNullSpecifiedJavaAttributeMappingDefinitions(), 
//			NullSpecifiedJavaAttributeMappingDefinition.instance());
//	}
//	
//	/**
//	 * No-op implementation of this method. 
//	 * @see #buildSpecifiedJavaAttributeMappingDefinitions()
//	 */
//	protected JavaAttributeMappingDefinition[] buildNonNullSpecifiedJavaAttributeMappingDefinitions() {
//		return new JavaAttributeMappingDefinition[0];
//	}
//	
//	
//	// ********** Mapping Files **********
//	
//	public ListIterator<ResourceDefinition> resourceDefinitions() {
//		return new ArrayListIterator<ResourceDefinition>(getResourceDefinitions());
//	}
//	
//	protected synchronized ResourceDefinition[] getResourceDefinitions() {
//		if (this.resourceDefinitions == null) {
//			this.resourceDefinitions = this.buildResourceDefinitions();
//		}
//		return this.resourceDefinitions;
//	}
//	
//	protected abstract ResourceDefinition[] buildResourceDefinitions();
}
