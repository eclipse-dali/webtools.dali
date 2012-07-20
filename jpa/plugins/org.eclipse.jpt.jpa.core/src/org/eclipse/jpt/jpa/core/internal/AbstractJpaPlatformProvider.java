/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.common.core.ContentTypeReference;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.jpa.core.JpaPlatformProvider;
import org.eclipse.jpt.jpa.core.JpaResourceModelProvider;
import org.eclipse.jpt.jpa.core.ResourceDefinition;
import org.eclipse.jpt.jpa.core.context.java.DefaultJavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.jpa.core.internal.plugin.JptJpaCorePlugin;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformProvider
	implements JpaPlatformProvider
{
	protected ArrayList<JptResourceType> mostRecentSupportedResourceTypes;

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


	// ********** most recent supported resource types **********

	public synchronized ArrayList<JptResourceType> getMostRecentSupportedResourceTypes() {
		if (this.mostRecentSupportedResourceTypes == null) {
			this.mostRecentSupportedResourceTypes = this.buildMostRecentSupportedResourceTypes();
		}
		return this.mostRecentSupportedResourceTypes;
	}

	protected ArrayList<JptResourceType> buildMostRecentSupportedResourceTypes() {
		ArrayList<JptResourceType> types = new ArrayList<JptResourceType>();
		this.addMostRecentSupportedResourceTypesTo(types);
		this.validateMostRecentSupportedResourceTypes(types);
		return types;
	}

	protected abstract void addMostRecentSupportedResourceTypesTo(ArrayList<JptResourceType> types);

	protected void validateMostRecentSupportedResourceTypes(ArrayList<JptResourceType> resourceTypes) {
		String message = "Duplicate resource types listed as most recent for content type ''{0}'': {1}"; //$NON-NLS-1$
		this.validate(resourceTypes, ContentTypeReference.CONTENT_TYPE_TRANSFORMER, message);
	}


	// ********** resource models **********

	public final synchronized Iterable<JpaResourceModelProvider> getResourceModelProviders() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}

	protected ArrayList<JpaResourceModelProvider> buildResourceModelProviders() {
		ArrayList<JpaResourceModelProvider> providers = new ArrayList<JpaResourceModelProvider>();
		this.addResourceModelProvidersTo(providers);
		this.validateResourceModelProviders(providers);
		return providers;
	}

	protected abstract void addResourceModelProvidersTo(ArrayList<JpaResourceModelProvider> providers);

	protected void validateResourceModelProviders(ArrayList<JpaResourceModelProvider> providers) {
		String message = "Duplicate resource model providers listed for content type ''{0}'': {1}"; //$NON-NLS-1$
		this.validate(providers, ContentTypeReference.CONTENT_TYPE_TRANSFORMER, message);
	}


	// ********** Java type mappings **********

	public final synchronized Iterable<JavaTypeMappingDefinition> getJavaTypeMappingDefinitions() {
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

	public final synchronized Iterable<DefaultJavaAttributeMappingDefinition> getDefaultJavaAttributeMappingDefinitions() {
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

	public final synchronized Iterable<JavaAttributeMappingDefinition> getSpecifiedJavaAttributeMappingDefinitions() {
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

	public final synchronized Iterable<ResourceDefinition> getResourceDefinitions() {
		if (this.resourceDefinitions == null) {
			this.resourceDefinitions = this.buildResourceDefinitions();
		}
		return this.resourceDefinitions;
	}

	protected ArrayList<ResourceDefinition> buildResourceDefinitions() {
		ArrayList<ResourceDefinition> definitions = new ArrayList<ResourceDefinition>();
		this.addResourceDefinitionsTo(definitions);
		this.validateResourceDefinitions(definitions);
		return definitions;
	}

	protected abstract void addResourceDefinitionsTo(ArrayList<ResourceDefinition> definitions);

	protected void validateResourceDefinitions(ArrayList<ResourceDefinition> definitions) {
		String message = "Duplicate resource definitions listed for resource type ''{0}'': {1}"; //$NON-NLS-1$
		this.validate(definitions, ResourceDefinition.RESOURCE_TYPE_TRANSFORMER, message);
	}


	// ********** misc **********

	protected <K, V> void validate(ArrayList<? extends V> values, Transformer<V, K> keyTransformer, String message) {
		HashMap<K, ArrayList<V>> map = new HashMap<K, ArrayList<V>>(values.size());
		for (V element : values) {
			K key = keyTransformer.transform(element);
			ArrayList<V> dupes = map.get(key);
			if (dupes == null) {
				dupes = new ArrayList<V>();
				map.put(key, dupes);
			}
			dupes.add(element);
		}
		for (Map.Entry<K, ArrayList<V>> entry : map.entrySet()) {
			ArrayList<V> dupes = entry.getValue();
			if (dupes.size() > 1) {
				JptJpaCorePlugin.instance().logError(message, entry.getKey(), dupes);
			}
		}
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
