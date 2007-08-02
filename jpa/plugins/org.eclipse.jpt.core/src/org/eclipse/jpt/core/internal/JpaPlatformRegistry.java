/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JpaPlatformRegistry 
{
	private static final JpaPlatformRegistry INSTANCE = new JpaPlatformRegistry();
	
	/**
	 * Return the singleton.
	 */
	public static JpaPlatformRegistry instance() {
		return INSTANCE;
	}

	private static final String EXTENSION_ID = 
		"jpaPlatform"; //$NON-NLS-1$
	
	private static final String EL_PLATFORM =
		"jpaPlatform"; //$NON-NLS-1$	
	
	private static final String AT_ID =
		"id"; //$NON-NLS-1$	
	
	private static final String AT_LABEL =
		"label"; //$NON-NLS-1$	
	
	private static final String AT_CLASS =
		"class"; //$NON-NLS-1$	
		
	// key: String jpaPlatform id  value: IConfigurationElement class descriptor
	private Map<String, IConfigurationElement> jpaPlatforms;
	
	
	/* (non Java doc)
	 * restrict access
	 */
	private JpaPlatformRegistry() {
		buildJpaPlatforms();
	}
	
	
	private void buildJpaPlatforms() {
		this.jpaPlatforms = new HashMap<String, IConfigurationElement>();
		
		for (Iterator<IConfigurationElement> stream = allConfigElements(); stream.hasNext(); ) {
			buildJpaPlatform(stream.next());
		}
	}
	
	private void buildJpaPlatform(IConfigurationElement configElement) {
		if (! configElement.getName().equals(EL_PLATFORM)) {
			return;
		}
		
		String platformId = configElement.getAttribute(AT_ID);
		String platformLabel = configElement.getAttribute(AT_LABEL);
		String platformClass = configElement.getAttribute(AT_CLASS);
		
		if ((platformId == null) || (platformLabel == null) || (platformClass == null)) {
			if (platformId == null) {
				reportMissingAttribute(configElement, AT_ID);
			}
			if (platformLabel == null) {
				reportMissingAttribute(configElement, AT_LABEL);
			}
			if (platformClass == null) {
				reportMissingAttribute(configElement, AT_CLASS);
			}
			return;
		}
		
		if (this.jpaPlatforms.containsKey(platformId)) {
			IConfigurationElement otherConfigElement = this.jpaPlatforms.get(platformId);
			reportDuplicatePlatform(configElement, otherConfigElement);
		}
		
		this.jpaPlatforms.put(platformId, configElement);
	}
	
	/**
	 * Return a new IJpaPlatform for the given id.
	 * NB: This should only be done when instantiating a platform for a given
	 *     IJpaProject, either when creating the project, or when changing the 
	 *     platform.
	 */
	public IJpaPlatform jpaPlatform(String id) {
		IConfigurationElement registeredConfigElement = this.jpaPlatforms.get(id);
		
		if (registeredConfigElement == null) {
			return null;
		}
		
		try {
			IJpaPlatform platform = 
				(IJpaPlatform) registeredConfigElement.createExecutableExtension(AT_CLASS);
			platform.setId(id);
			return platform;
		}
		catch (CoreException ce) {
			reportFailedInstantiation(registeredConfigElement);
			return null;
		}
	}
	
	/**
	 * Return an iterator of String ids for all registered JPA platforms.
	 * This does not load any of the platforms' plugin classes.
	 */
	public Iterator allJpaPlatformIds() {
		return Collections.unmodifiableMap(jpaPlatforms).keySet().iterator();
	}
	
	/**
	 * Return the label for the platform with the given id.
	 * This does not load the platform's plugin classes.
	 */
	public String jpaPlatformLabel(String id) {
		return jpaPlatforms.get(id).getAttribute(AT_LABEL);
	}
	
	private Iterator<IConfigurationElement> allConfigElements() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptCorePlugin.PLUGIN_ID, EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		return new CompositeIterator<IConfigurationElement>(
				new TransformationIterator<IExtension, Iterator<IConfigurationElement>>(CollectionTools.iterator(extensions)) {
					@Override
					protected Iterator<IConfigurationElement> transform(IExtension next) {
						return CollectionTools.iterator(next.getConfigurationElements());
					}
				}
			);
	}
	
	// TODO externalize strings
	private void reportMissingAttribute(IConfigurationElement configElement, String attributeName) {
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
	private void reportDuplicatePlatform(
			IConfigurationElement oneConfigElement, IConfigurationElement otherConfigElement) {
		String message =
			"The plugins \""
			+ oneConfigElement.getContributor().getName()
			+ "\" and \""
			+ otherConfigElement.getContributor().getName()
			+ "\" have registered a duplicate attribute \"id\" "
			+ "for the extension element \"jpaPlatform\".";
		JptCorePlugin.log(message);
	}
		
	// TODO externalize strings
	private void reportFailedInstantiation(IConfigurationElement configElement) {
		String message =
			"Could not instantiate the class \""
			+ configElement.getAttribute(AT_CLASS)
			+ "\" for the extension element \""
			+ configElement.getName()
			+ "\" in the plugin \""
			+ configElement.getContributor().getName()
			+ "\".";
		JptCorePlugin.log(message);
	}
}
