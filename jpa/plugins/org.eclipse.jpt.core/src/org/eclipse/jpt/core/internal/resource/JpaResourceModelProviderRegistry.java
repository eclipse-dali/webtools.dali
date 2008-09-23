/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.resource.JpaResourceModelProviderFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Singleton registry for storing all the registered JPA resource model provider
 * elements and instantiating JPA resource model provider factories from them.
 */
public class JpaResourceModelProviderRegistry
{
	// singleton
	private static final JpaResourceModelProviderRegistry INSTANCE = new JpaResourceModelProviderRegistry();
	
	private static final String EXTENSION_ID = "resourceModelProviders"; //$NON-NLS-1$
	private static final String EL_MODEL_PROVIDER = "modelProvider"; //$NON-NLS-1$
	private static final String AT_FILE_CONTENT_TYPE = "fileContentType"; //$NON-NLS-1$
	private static final String AT_FACTORY_CLASS = "factoryClass"; //$NON-NLS-1$
	
	
	/**
	 * Return the singleton.
	 */
	public static JpaResourceModelProviderRegistry instance() {
		return INSTANCE;
	}
	
	
	private final HashMap<String, IConfigurationElement> configElements;
	
	
	public JpaResourceModelProviderRegistry() {
		super();
		this.configElements = this.buildConfigElements();
	}
	
	
	private HashMap<String, IConfigurationElement> buildConfigElements() {
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
		if ( ! configElement.getName().equals(EL_MODEL_PROVIDER)) {
			return;
		}
		if ( ! this.configElementIsValid(configElement)) {
			return;
		}
		
		String fileContentType = configElement.getAttribute(AT_FILE_CONTENT_TYPE);
		IConfigurationElement prev = configElements.get(fileContentType);
		if (prev == null) {
			configElements.put(fileContentType, configElement);
		} else {
			this.logDuplicateFileContentType(prev, configElement);
		}
	}
	
	/**
	 * check *all* attributes before returning
	 */
	private boolean configElementIsValid(IConfigurationElement configElement) {
		boolean valid = true;
		if (configElement.getAttribute(AT_FILE_CONTENT_TYPE) == null) {
			this.logMissingAttribute(configElement, AT_FILE_CONTENT_TYPE);
			valid = false;
		}
		if (configElement.getAttribute(AT_FACTORY_CLASS) == null) {
			logMissingAttribute(configElement, AT_FACTORY_CLASS);
			valid = false;
		}
		return valid;
	}
	
	
	// **************** public methods *****************************************
	
	/**
	 * Return a new JPA Resource Model Provider Factory for the specified file
	 * content type.
	 * NB: Invoking this method may activate the plug-in.
	 */
	public JpaResourceModelProviderFactory getResourceModelProviderFactory(String fileContentType) {
		IConfigurationElement configElement = configElements.get(fileContentType);
		if (configElement == null) {
			throw new IllegalArgumentException(fileContentType);
		}
		JpaResourceModelProviderFactory factory;
		try {
			factory = (JpaResourceModelProviderFactory) configElement.createExecutableExtension(AT_FACTORY_CLASS);
		} catch (CoreException ex) {
			this.logFailedInstantiation(configElement, ex);
			throw new IllegalArgumentException(fileContentType);
		}
		return factory;
	}
	
	// **************** errors *************************************************
	
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
	private void logDuplicateFileContentType(IConfigurationElement prevConfigElement, IConfigurationElement newConfigElement) {
		String message =
			"The plugins \""
			+ prevConfigElement.getContributor().getName()
			+ "\" and \""
			+ newConfigElement.getContributor().getName()
			+ "\" have registered a duplicate attribute \"" 
			+ AT_FILE_CONTENT_TYPE
			+ "\" "
			+ "for the extension element \""
			+ EL_MODEL_PROVIDER
			+ "\".";
		JptCorePlugin.log(message);
	}
	
	// TODO externalize strings
	private void logFailedInstantiation(IConfigurationElement configElement, CoreException ex) {
		String message =
			"Could not instantiate the class \""
			+ configElement.getAttribute(AT_FACTORY_CLASS)
			+ "\" for the extension element \""
			+ configElement.getName()
			+ "\" in the plugin \""
			+ configElement.getContributor().getName()
			+ "\".";
		JptCorePlugin.log(ex);
		JptCorePlugin.log(message);
	}
}
