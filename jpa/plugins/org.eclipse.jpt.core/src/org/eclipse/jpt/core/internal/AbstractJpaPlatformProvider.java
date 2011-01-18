/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.ResourceDefinition;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformProvider
	implements JpaPlatformProvider
{
	protected ArrayList<JpaResourceModelProvider> resourceModelProviders;

	protected ArrayList<JavaTypeMappingDefinition> javaTypeMappingDefinitions;

	protected ArrayList<DefaultJavaAttributeMappingDefinition> defaultJavaAttributeMappingDefinitions;

	protected ArrayList<JavaAttributeMappingDefinition> specifiedJavaAttributeMappingDefinitions;

	protected ArrayList<ResourceDefinition> resourceDefinitions;


	/**
	 * zero-argument constructor
	 */
	protected AbstractJpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	public synchronized Iterable<JpaResourceModelProvider> getResourceModelProviders() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}

	protected ArrayList<JpaResourceModelProvider> buildResourceModelProviders() {
		ArrayList<JpaResourceModelProvider> providers = new ArrayList<JpaResourceModelProvider>();
		this.addResourceModelProvidersTo(providers);
		return providers;
	}

	protected abstract void addResourceModelProvidersTo(ArrayList<JpaResourceModelProvider> providers);


	// ********** Java type mappings **********

	public synchronized Iterable<JavaTypeMappingDefinition> getJavaTypeMappingDefinitions() {
		if (this.javaTypeMappingDefinitions == null) {
			this.javaTypeMappingDefinitions = this.buildJavaTypeMappingDefinitions();
		}
		return this.javaTypeMappingDefinitions;
	}

	protected ArrayList<JavaTypeMappingDefinition> buildJavaTypeMappingDefinitions() {
		ArrayList<JavaTypeMappingDefinition> definitions = new ArrayList<JavaTypeMappingDefinition>();
		this.addJavaTypeMappingDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * To the specified list, add mapping definitions to use for analyzing the
	 * mapping of a type given all annotations on it. The order is important,
	 * as once a mapping definition tests positive for a
	 * type, all following mapping definitions are ignored.
	 */
	protected abstract void addJavaTypeMappingDefinitionsTo(ArrayList<JavaTypeMappingDefinition> definitions);


	// ********** Java attribute mappings **********

	public synchronized Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions() {
		if (this.defaultJavaAttributeMappingDefinitions == null) {
			this.defaultJavaAttributeMappingDefinitions = this.buildDefaultJavaAttributeMappingDefinitions();
		}
		return this.defaultJavaAttributeMappingDefinitions;
	}

	protected ArrayList<DefaultJavaAttributeMappingDefinition> buildDefaultJavaAttributeMappingDefinitions() {
		ArrayList<DefaultJavaAttributeMappingDefinition> definitions = new ArrayList<DefaultJavaAttributeMappingDefinition>();
		this.addDefaultJavaAttributeMappingDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * To the specified list, add mapping definitions to use for analyzing the
	 * default mapping of an attribute. The order is important,
	 * as once a mapping definition tests positive for an attribute,
	 * all following mapping definitions are ignored.
	 */
	protected abstract void addDefaultJavaAttributeMappingDefinitionsTo(ArrayList<DefaultJavaAttributeMappingDefinition> definitions);

	public synchronized Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions() {
		if (this.specifiedJavaAttributeMappingDefinitions == null) {
			this.specifiedJavaAttributeMappingDefinitions = this.buildSpecifiedJavaAttributeMappingDefinitions();
		}
		return this.specifiedJavaAttributeMappingDefinitions;
	}

	protected ArrayList<JavaAttributeMappingDefinition> buildSpecifiedJavaAttributeMappingDefinitions() {
		ArrayList<JavaAttributeMappingDefinition> definitions = new ArrayList<JavaAttributeMappingDefinition>();
		this.addSpecifiedJavaAttributeMappingDefinitionsTo(definitions);
		return definitions;
	}

	/**
	 * To the specified list, add mapping definitions to use for analyzing the
	 * specified mapping of an attribute given all annotations on it. The order
	 * is important, as once a mapping definition tests positive for an
	 * attribute, all following mapping definitions are ignored.
	 */
	protected abstract void addSpecifiedJavaAttributeMappingDefinitionsTo(ArrayList<JavaAttributeMappingDefinition> definitions);


	// ********** resource definitions **********

	public synchronized Iterable<ResourceDefinition> getResourceDefinitions() {
		if (this.resourceDefinitions == null) {
			this.resourceDefinitions = this.buildResourceDefinitions();
		}
		return this.resourceDefinitions;
	}

	protected ArrayList<ResourceDefinition> buildResourceDefinitions() {
		ArrayList<ResourceDefinition> definitions = new ArrayList<ResourceDefinition>();
		this.addResourceDefinitionsTo(definitions);
		return definitions;
	}

	protected abstract void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions);
}
