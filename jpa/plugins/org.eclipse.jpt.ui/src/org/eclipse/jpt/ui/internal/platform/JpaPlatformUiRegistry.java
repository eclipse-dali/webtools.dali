/*******************************************************************************
 *  Copyright (c) 2006, 2009 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.platform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

public class JpaPlatformUiRegistry 
{
	// singleton
	private static final JpaPlatformUiRegistry INSTANCE = new JpaPlatformUiRegistry();

	/**
	 * Return the singleton.
	 */
	public static JpaPlatformUiRegistry instance() {
		return INSTANCE;
	}

	private static final String EXTENSION_ID = 
		"jpaPlatformUis"; //$NON-NLS-1$
	
	private static final String EL_PLATFORM_UI =
		"jpaPlatformUi"; //$NON-NLS-1$	

	private static final String AT_ID =
		"id"; //$NON-NLS-1$	
	
	private static final String AT_JPA_PLATFORM =
		"jpaPlatform"; //$NON-NLS-1$	

	private static final String AT_FACTORY_CLASS =
		"factoryClass"; //$NON-NLS-1$	
		
	// key: String id  value: IConfigurationElement class descriptor
	private Map<String, IConfigurationElement> jpaPlatformUiConfigElements;
	
	//cache the jpaPlatformUis when they are built
	//key: jpa platform id  value: JpaPlaformUi
	private Map<String, JpaPlatformUi> jpaPlatformUis;
	
	/* (non Java doc)
	 * restrict access
	 */
	private JpaPlatformUiRegistry() {
		buildJpaPlatformUiConfigElements();
		this.jpaPlatformUis = new HashMap<String, JpaPlatformUi>();
	}
	
	
	private void buildJpaPlatformUiConfigElements() {
		this.jpaPlatformUiConfigElements = new HashMap<String, IConfigurationElement>();
		
		for (Iterator<IConfigurationElement> stream = allConfigElements(); stream.hasNext(); ) {
			buildJpaPlatformUi(stream.next());
		}
	}
	
	private void buildJpaPlatformUi(IConfigurationElement configElement) {
		if (! configElement.getName().equals(EL_PLATFORM_UI)) {
			return;
		}
		
		String platformUiId = configElement.getAttribute(AT_ID);
		String platform = configElement.getAttribute(AT_JPA_PLATFORM);
		String platformUiFactoryClass = configElement.getAttribute(AT_FACTORY_CLASS);
		
		if ((platformUiId == null) || (platformUiFactoryClass == null)) {
			if (platformUiId == null) {
				reportMissingAttribute(configElement, AT_ID);
			}
			if (platform == null) {
				reportMissingAttribute(configElement, AT_JPA_PLATFORM);
			}
			if (platformUiFactoryClass == null) {
				reportMissingAttribute(configElement, AT_FACTORY_CLASS);
			}
			return;
		}
		
		if (this.jpaPlatformUiConfigElements.containsKey(platformUiId)) {
			IConfigurationElement otherConfigElement = this.jpaPlatformUiConfigElements.get(platform);
			reportDuplicatePlatformUi(configElement, otherConfigElement);
		}
		
		this.jpaPlatformUiConfigElements.put(platformUiId, configElement);
	}
	
	public JpaPlatformUi getJpaPlatformUi(String platformId) {
		if (this.jpaPlatformUis.containsKey(platformId)) {
			return this.jpaPlatformUis.get(platformId);
		}
		IConfigurationElement registeredConfigElement = null;
		for (IConfigurationElement configurationElement : this.jpaPlatformUiConfigElements.values()) {
			if (configurationElement.getAttribute(AT_JPA_PLATFORM).equals(platformId)) {
				registeredConfigElement = configurationElement;
				break;
			}
		}
		
		if (registeredConfigElement == null) {
			return null;
		}
		JpaPlatformUiFactory jpaPlatformUiFactory;
		try {
			jpaPlatformUiFactory = (JpaPlatformUiFactory) registeredConfigElement.createExecutableExtension(AT_FACTORY_CLASS);
		}
		catch (CoreException ce) {
			reportFailedInstantiation(registeredConfigElement);
			throw new IllegalArgumentException(platformId);
		}
		JpaPlatformUi platformUi = jpaPlatformUiFactory.buildJpaPlatformUi();
		this.jpaPlatformUis.put(platformId, platformUi);
		return platformUi;
	}
	
	private Iterator<IConfigurationElement> allConfigElements() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptUiPlugin.PLUGIN_ID, EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		return new CompositeIterator<IConfigurationElement>(
				new TransformationIterator<IExtension, Iterator<IConfigurationElement>>(CollectionTools.iterator(extensions)) {
					@Override
					protected Iterator<IConfigurationElement> transform(IExtension extension) {
						return CollectionTools.iterator(extension.getConfigurationElements());
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
		JptUiPlugin.log(message);
	}
	
	// TODO externalize strings
	private void reportDuplicatePlatformUi(
			IConfigurationElement oneConfigElement, IConfigurationElement otherConfigElement) {
		String message =
			"The plugins \""
			+ oneConfigElement.getContributor().getName()
			+ "\" and \""
			+ otherConfigElement.getContributor().getName()
			+ "\" have registered a duplicate attribute \"id\" "
			+ "for the extension element \"jpaPlatformUi\".";
		JptUiPlugin.log(message);
	}
		
	// TODO externalize strings
	private void reportFailedInstantiation(IConfigurationElement configElement) {
		String message =
			"Could not instantiate the class \""
			+ configElement.getAttribute(AT_FACTORY_CLASS)
			+ "\" for the extension element \""
			+ configElement.getName()
			+ "\" in the plugin \""
			+ configElement.getContributor().getName()
			+ "\".";
		JptUiPlugin.log(message);
	}
}
