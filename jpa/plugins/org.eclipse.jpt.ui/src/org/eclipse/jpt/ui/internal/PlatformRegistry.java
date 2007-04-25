/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal;

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

public class PlatformRegistry 
{
	public static final PlatformRegistry INSTANCE = new PlatformRegistry();
	
	private static final String EXTENSION_ID = 
		"jpaPlatform"; //$NON-NLS-1$
	
	private static final String EL_PLATFORM =
		"jpaPlatform"; //$NON-NLS-1$	

	private static final String AT_ID =
		"id"; //$NON-NLS-1$	

	private static final String AT_CLASS =
		"class"; //$NON-NLS-1$	
		
	// key: String jpaPlatform id  value: IConfigurationElement class descriptor
	private Map<String, IConfigurationElement> jpaPlatforms;
	
	
	/* (non Java doc)
	 * restrict access
	 */
	private PlatformRegistry() {
		buildJpaPlatforms();
	}
	
	
	private void buildJpaPlatforms() {
		this.jpaPlatforms = new HashMap<String, IConfigurationElement>();
		
		for (Iterator stream = allConfigElements(); stream.hasNext(); ) {
			buildJpaPlatform((IConfigurationElement) stream.next());
		}
	}
	
	private void buildJpaPlatform(IConfigurationElement configElement) {
		if (! configElement.getName().equals(EL_PLATFORM)) {
			return;
		}
		
		String platformId = configElement.getAttribute(AT_ID);
		String platformClass = configElement.getAttribute(AT_CLASS);
		
		if ((platformId == null) || (platformClass == null)) {
			if (platformId == null) {
				reportMissingAttribute(configElement, AT_ID);
			}
			if (platformClass == null) {
				reportMissingAttribute(configElement, AT_CLASS);
			}
			return;
		}
		
		if (jpaPlatforms.containsKey(platformId)) {
			IConfigurationElement otherConfigElement = jpaPlatforms.get(platformId);
			reportDuplicatePlatform(configElement, otherConfigElement);
		}
		
		jpaPlatforms.put(platformId, configElement);
	}
	
	public IJpaPlatformUi getJpaPlatform(String vendorId) {
		IConfigurationElement registeredConfigElement = this.jpaPlatforms.get(vendorId);
		
		if (registeredConfigElement == null) {
			return null;
		}
		
		try {
			return (IJpaPlatformUi) registeredConfigElement.createExecutableExtension(AT_CLASS);
		}
		catch (CoreException ce) {
			reportFailedInstantiation(registeredConfigElement);
			return null;
		}
	}
	
	private Iterator allConfigElements() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IExtensionPoint extensionPoint = 
			registry.getExtensionPoint(JptUiPlugin.PLUGIN_ID, EXTENSION_ID);
		IExtension[] extensions = extensionPoint.getExtensions();
		
		return new CompositeIterator(
				new TransformationIterator(CollectionTools.iterator(extensions)) {
					@Override
					protected Object transform(Object next) {
						return CollectionTools.iterator(((IExtension) next).getConfigurationElements());
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
	private void reportDuplicatePlatform(
			IConfigurationElement oneConfigElement, IConfigurationElement otherConfigElement) {
		String message =
			"The plugins \""
			+ oneConfigElement.getContributor().getName()
			+ "\" and \""
			+ otherConfigElement.getContributor().getName()
			+ "\" have registered a duplicate attribute \"id\" "
			+ "for the extension element \"jpaVendor\".";
		JptUiPlugin.log(message);
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
		JptUiPlugin.log(message);
	}
}
