/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JpaPlatformFactory;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
import org.eclipse.osgi.util.NLS;

/**
 * Singleton registry for storing all the registered JPA platform configuration
 * elements and instantiating JPA platforms from them.
 */
public class JpaPlatformRegistry {

	private final HashMap<String, IConfigurationElement> jpaPlatformConfigurationElements;


	// singleton
	private static final JpaPlatformRegistry INSTANCE = new JpaPlatformRegistry();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformRegistry instance() {
		return INSTANCE;
	}

	private static final String EXTENSION_ID = "jpaPlatforms"; //$NON-NLS-1$
	private static final String PLATFORM_ELEMENT_NAME = "jpaPlatform"; //$NON-NLS-1$
	private static final String ID_ATTRIBUTE_NAME = "id"; //$NON-NLS-1$
	private static final String LABEL_ATTRIBUTE_NAME = "label"; //$NON-NLS-1$
	private static final String FACTORY_CLASS_ATTRIBUTE_NAME = "factoryClass"; //$NON-NLS-1$
	private static final String JPA_FACET_VERSION_ATTRIBUTE_NAME = "jpaFacetVersion"; //$NON-NLS-1$
	private static final String DEFAULT_ATTRIBUTE_NAME = "default"; //$NON-NLS-1$


	// ********** constructor/initialization **********

	/**
	 * ensure single instance
	 */
	private JpaPlatformRegistry() {
		super();
		this.jpaPlatformConfigurationElements = this.buildJpaPlatformConfigurationElements();
	}


	private HashMap<String, IConfigurationElement> buildJpaPlatformConfigurationElements() {
		HashMap<String, IConfigurationElement> configElements = new HashMap<String, IConfigurationElement>();
		for (IConfigurationElement configElement : this.getConfigElements()) {
			this.addConfigElementTo(configElement, configElements);
		}
		return configElements;
	}

	/**
	 * Return the configuration elements from the Eclipse platform extension
	 * registry.
	 */
	private Iterable<IConfigurationElement> getConfigElements() {
		return new CompositeIterable<IConfigurationElement>(
				new TransformationIterable<IExtension, Iterable<IConfigurationElement>>(this.getExtensions()) {
					@Override
					protected Iterable<IConfigurationElement> transform(IExtension extension) {
						return CollectionTools.iterable(extension.getConfigurationElements());
					}
				}
		);
	}

	private Iterable<IExtension> getExtensions() {
		return CollectionTools.iterable(this.getExtensionPoint().getExtensions());
	}

	private IExtensionPoint getExtensionPoint() {
		return Platform.getExtensionRegistry().getExtensionPoint(JptCorePlugin.PLUGIN_ID, EXTENSION_ID);
	}

	private void addConfigElementTo(IConfigurationElement configElement, HashMap<String, IConfigurationElement> configElements) {
		if ( ! configElement.getName().equals(PLATFORM_ELEMENT_NAME)) {
			return;
		}
		if ( ! this.configElementIsValid(configElement)) {
			return;
		}

		String id = configElement.getAttribute(ID_ATTRIBUTE_NAME);
		IConfigurationElement prev = configElements.put(id, configElement);
		if (prev != null) {
			configElements.put(id, prev);  // replace previous(?)
			this.logDuplicatePlatform(prev, configElement, id);
		}
	}

	/**
	 * check *all* attributes before returning
	 */
	private boolean configElementIsValid(IConfigurationElement configElement) {
		boolean valid = true;
		if (configElement.getAttribute(ID_ATTRIBUTE_NAME) == null) {
			this.logMissingAttribute(configElement, ID_ATTRIBUTE_NAME);
			valid = false;
		}
		if (configElement.getAttribute(LABEL_ATTRIBUTE_NAME) == null) {
			this.logMissingAttribute(configElement, LABEL_ATTRIBUTE_NAME);
			valid = false;
		}
		if (configElement.getAttribute(FACTORY_CLASS_ATTRIBUTE_NAME) == null) {
			this.logMissingAttribute(configElement, FACTORY_CLASS_ATTRIBUTE_NAME);
			valid = false;
		}
		return valid;
	}


	// ********** public methods **********

	/**
	 * Return the IDs for the registered JPA platforms.
	 * This does not activate any of the JPA platforms' plug-ins.
	 */
	public Iterable<String> getJpaPlatformIds() {
		return this.jpaPlatformConfigurationElements.keySet();
	}

	/**
	 * Return whether the platform id is registered
	 */
	public boolean containsPlatform(String platformId) {
		return this.jpaPlatformConfigurationElements.containsKey(platformId);
	}

	/**
	 * Return the label for the JPA platform with the specified ID.
	 * This does not activate the JPA platform's plug-in.
	 */
	public String getJpaPlatformLabel(String id) {
		return this.jpaPlatformConfigurationElements.get(id).getAttribute(LABEL_ATTRIBUTE_NAME);
	}
	
	/**
	 * Return whether the platform represented by the given id supports the specified JPA facet version.
	 * This does not active the JPA platform's plug-in.
	 */
	public boolean platformSupportsJpaFacetVersion(String platformId, String jpaFacetVersion) {
		IConfigurationElement configElement = this.jpaPlatformConfigurationElements.get(platformId);
		return configElementSupportsJpaFacetVersion(configElement, jpaFacetVersion);
	}
	
	private boolean configElementSupportsJpaFacetVersion(
			IConfigurationElement configElement, String jpaFacetVersion) {
		
		// config element supports version if it explicitly sets it to that version
		// or if it specifies no version at all
		String ver = configElement.getAttribute(JPA_FACET_VERSION_ATTRIBUTE_NAME);
		return (ver == null) || ver.equals(jpaFacetVersion);
	}
	
	/**
	 * Return the IDs for the registered JPA platforms that support the
	 * specified JPA facet version.
	 * This does not activate the JPA platforms' plug-in.
	 */
	public Iterable<String> getJpaPlatformIdsForJpaFacetVersion(final String jpaFacetVersion) {
		return new TransformationIterable<IConfigurationElement, String>(this.getConfigurationElementsForJpaFacetVersion(jpaFacetVersion)) {
				@Override
				protected String transform(IConfigurationElement configElement) {
					return configElement.getAttribute(ID_ATTRIBUTE_NAME);
				}
			};
	}
	
	private Iterable<IConfigurationElement> getConfigurationElementsForJpaFacetVersion(final String jpaFacetVersion) {
		return new FilteringIterable<IConfigurationElement, IConfigurationElement>(this.jpaPlatformConfigurationElements.values()) {
				@Override
				protected boolean accept(IConfigurationElement configElement) {
					return configElementSupportsJpaFacetVersion(configElement, jpaFacetVersion);
				}
			};
	}
	
	/**
	 * Return the ID for a JPA platform registered as a default platform.
	 * Returns null if there are no such registered platforms.
	 * Returns the first platform ID if there are multiple such registered platforms.
	 */
	public String getDefaultJpaPlatformId() {
		for (Map.Entry<String, IConfigurationElement> entry: this.jpaPlatformConfigurationElements.entrySet()) {
			String defaultFlag = entry.getValue().getAttribute(DEFAULT_ATTRIBUTE_NAME);
			if ((defaultFlag != null) && defaultFlag.equals("true")) { //$NON-NLS-1$
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Return a new JPA platform for the specified ID.
	 * NB: This should only be called when instantiating a JPA platform
	 * when building a new JPA project.
	 * Unlike other registry methods, invoking this method may activate 
	 * the plug-in.
	 */
	public JpaPlatform getJpaPlatform(IProject project) {
		String id = JptCorePlugin.getJpaPlatformId(project);
		IConfigurationElement configElement = this.jpaPlatformConfigurationElements.get(id);
		if (configElement == null) {
			this.log(JptCoreMessages.PLATFORM_ID_DOES_NOT_EXIST, id, project.getName());
			return null;
		}
		JpaPlatformFactory platformFactory;
		try {
			platformFactory = (JpaPlatformFactory) configElement.createExecutableExtension(FACTORY_CLASS_ATTRIBUTE_NAME);
		} catch (CoreException ex) {
			this.logFailedInstantiation(configElement, ex);
			throw new IllegalArgumentException(id);
		}
		return platformFactory.buildJpaPlatform(id);
	}


	// ********** errors **********

	private void logMissingAttribute(IConfigurationElement configElement, String attributeName) {
		this.log(JptCoreMessages.REGISTRY_MISSING_ATTRIBUTE,
						configElement.getName(),
						configElement.getContributor().getName(),
						attributeName
					);
	}

	private void logDuplicatePlatform(IConfigurationElement prevConfigElement, IConfigurationElement newConfigElement, String id) {
		this.log(JptCoreMessages.REGISTRY_DUPLICATE,
						prevConfigElement.getContributor().getName(),
						newConfigElement.getContributor().getName(),
						ID_ATTRIBUTE_NAME,
						PLATFORM_ELEMENT_NAME,
						id
					);
	}

	private void logFailedInstantiation(IConfigurationElement configElement, CoreException ex) {
		this.log(JptCoreMessages.REGISTRY_FAILED_INSTANTIATION,
						configElement.getAttribute(FACTORY_CLASS_ATTRIBUTE_NAME),
						configElement.getName(),
						configElement.getContributor().getName()
					);
		JptCorePlugin.log(ex);
	}

	private void log(String msg, Object... bindings) {
		JptCorePlugin.log(NLS.bind(msg, bindings));
	}

}
