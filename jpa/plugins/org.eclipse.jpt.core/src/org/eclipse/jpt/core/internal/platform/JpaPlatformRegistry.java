/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JpaPlatform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.ReadOnlyIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

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

	private static final String EXTENSION_ID = "jpaPlatform"; //$NON-NLS-1$
	private static final String EL_PLATFORM = "jpaPlatform"; //$NON-NLS-1$	
	private static final String AT_ID = "id"; //$NON-NLS-1$	
	private static final String AT_LABEL = "label"; //$NON-NLS-1$	
	private static final String AT_CLASS = "class"; //$NON-NLS-1$	


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
		for (Iterator<IConfigurationElement> stream = this.configElements(); stream.hasNext(); ) {
			this.addConfigElementTo(stream.next(), configElements);
		}
		return configElements;
	}

	/**
	 * Return the configuration elements from the Eclipse platform extension
	 * registry.
	 */
	private Iterator<IConfigurationElement> configElements() {
		return new CompositeIterator<IConfigurationElement>(
				new TransformationIterator<IExtension, Iterator<IConfigurationElement>>(this.extensions()) {
					@Override
					protected Iterator<IConfigurationElement> transform(IExtension extension) {
						return CollectionTools.iterator(extension.getConfigurationElements());
					}
				}
		);
	}

	private Iterator<IExtension> extensions() {
		return CollectionTools.iterator(this.extensionPoint().getExtensions());
	}

	private IExtensionPoint extensionPoint() {
		return Platform.getExtensionRegistry().getExtensionPoint(JptCorePlugin.PLUGIN_ID, EXTENSION_ID);
	}

	private void addConfigElementTo(IConfigurationElement configElement, HashMap<String, IConfigurationElement> configElements) {
		if ( ! configElement.getName().equals(EL_PLATFORM)) {
			return;
		}
		if ( ! this.configElementIsValid(configElement)) {
			return;
		}

		String id = configElement.getAttribute(AT_ID);
		IConfigurationElement prev = configElements.get(id);
		if (prev == null) {
			configElements.put(id, configElement);
		} else {
			this.logDuplicatePlatform(prev, configElement);
		}
	}

	/**
	 * check *all* attributes before returning
	 */
	private boolean configElementIsValid(IConfigurationElement configElement) {
		boolean valid = true;
		if (configElement.getAttribute(AT_ID) == null) {
			this.logMissingAttribute(configElement, AT_ID);
			valid = false;
		}
		if (configElement.getAttribute(AT_LABEL) == null) {
			logMissingAttribute(configElement, AT_LABEL);
			valid = false;
		}
		if (configElement.getAttribute(AT_CLASS) == null) {
			logMissingAttribute(configElement, AT_CLASS);
			valid = false;
		}
		return valid;
	}


	// ********** public methods **********

	/**
	 * Return the IDs for the registered JPA platforms.
	 * This does not activate any of the JPA platforms' plug-ins.
	 */
	public Iterator<String> jpaPlatformIds() {
		return new ReadOnlyIterator<String>(this.jpaPlatformConfigurationElements.keySet());
	}

	/**
	 * Return the label for the JPA platform with the specified ID.
	 * This does not activate the JPA platform's plug-in.
	 */
	public String jpaPlatformLabel(String id) {
		return this.jpaPlatformConfigurationElements.get(id).getAttribute(AT_LABEL);
	}

	/**
	 * Return a new JPA platform for the specified ID.
	 * NB: This should only be called when instantiating a JPA platform
	 * when building a new JPA project.
	 * Unlike other registry methods, invoking this method may activate 
	 * the plug-in.
	 */
	public JpaPlatform jpaPlatform(String id) {
		IConfigurationElement configElement = this.jpaPlatformConfigurationElements.get(id);
		if (configElement == null) {
			throw new IllegalArgumentException(id);
		}
		JpaPlatform platform;
		try {
			platform = (JpaPlatform) configElement.createExecutableExtension(AT_CLASS);
		} catch (CoreException ex) {
			this.logFailedInstantiation(configElement, ex);
			throw new IllegalArgumentException(id);
		}
		platform.setId(id);
		return platform;
	}


	// ********** errors **********

	// TODO externalize strings
	private void logMissingAttribute(IConfigurationElement configElement, String attributeName) {
		String message = 
			"An extension element \""
			+ configElement.getName()
			+ "\" in plugin \""
			+ configElement.getContributor().getName()
			+ "\" is missing a required attribute \""
			+ attributeName
			+ "\".";
		JptCorePlugin.log(message);
	}

	// TODO externalize strings
	private void logDuplicatePlatform(IConfigurationElement prevConfigElement, IConfigurationElement newConfigElement) {
		String message =
			"The plugins \""
			+ prevConfigElement.getContributor().getName()
			+ "\" and \""
			+ newConfigElement.getContributor().getName()
			+ "\" have registered a duplicate attribute \"id\" "
			+ "for the extension element \"jpaPlatform\".";
		JptCorePlugin.log(message);
	}

	// TODO externalize strings
	private void logFailedInstantiation(IConfigurationElement configElement, CoreException ex) {
		String message =
			"Could not instantiate the class \""
			+ configElement.getAttribute(AT_CLASS)
			+ "\" for the extension element \""
			+ configElement.getName()
			+ "\" in the plugin \""
			+ configElement.getContributor().getName()
			+ "\".";
		JptCorePlugin.log(message);
		JptCorePlugin.log(ex);
	}

}
