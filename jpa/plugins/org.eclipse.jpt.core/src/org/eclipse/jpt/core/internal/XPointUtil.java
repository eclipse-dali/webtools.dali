/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * Utilities for extension point management, validation, etc.
 */
public class XPointUtil {
	
	public static String findRequiredAttribute(
			IConfigurationElement configElement, String attributeName)
			throws XPointException {
		
		String val = configElement.getAttribute(attributeName);
		if (val == null) {
			logMissingAttribute(configElement, attributeName);
			throw new XPointException();
		}
		return val;
	}
	
	public static <T> T instantiate(
			String pluginId, String extensionPoint, String className, Class<T> interfaze) {
		
		Class<T> clazz = loadClass(pluginId, extensionPoint, className, interfaze);
		if (clazz == null) {
			return null;
		}
		else {
			return instantiate(pluginId, extensionPoint, clazz);
		}
    }
	
	public static <T> T instantiate(String pluginId, String extensionPoint, Class<T> clazz) {
		
		try {
			return clazz.newInstance();
		}
		catch (Exception e) {
			logFailedInstantiation(pluginId, extensionPoint, clazz.getName());
			return null;
		}
	}
	
	public static <T> Class<T> loadClass(
			String pluginId, String extensionPoint, String className, Class<T> interfaze) {
		
		Bundle bundle = Platform.getBundle(pluginId);
		Class<?> clazz;
		
		try {
			clazz = bundle.loadClass(className);
		}
		catch (Exception e) {
			logFailedClassLoad(pluginId, extensionPoint, className);
			return null;
		}
		
		if (interfaze != null && ! interfaze.isAssignableFrom(clazz)) {
			logFailedInterfaceAssignment(pluginId, extensionPoint, className, interfaze.getName());
			return null;
		}
		
		return (Class<T>) clazz;
    }
	
	public static void logDuplicateExtension(String extensionPoint, String id) {
		
		log(JptCoreMessages.REGISTRY_DUPLICATE, extensionPoint, id);
	}
	
	public static void logMissingAttribute(
			IConfigurationElement configElement, String attributeName) {
		
		log(JptCoreMessages.REGISTRY_MISSING_ATTRIBUTE,
				attributeName,
				configElement.getName(),
				configElement.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				configElement.getContributor().getName());
    }
	
	public static void logInvalidValue(
			IConfigurationElement configElement, String nodeName, String invalidValue) {
		
		log(JptCoreMessages.REGISTRY_INVALID_VALUE,
				invalidValue,
				configElement.getName(),
				configElement.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				configElement.getContributor().getName());
	}
	
	private static void logFailedClassLoad(String pluginId, String extensionPoint, String className) {
		
		log(JptCoreMessages.REGISTRY_FAILED_CLASS_LOAD,
				className,
				extensionPoint,
				pluginId);
	}
	
	private static void logFailedInterfaceAssignment(
			String pluginId, String extensionPoint, String className, String interfaceName) {
		
		log(JptCoreMessages.REGISTRY_FAILED_INTERFACE_ASSIGNMENT,
				className,
				extensionPoint,
				pluginId,
				interfaceName);
	}
	
	private static void logFailedInstantiation(String pluginId, String extensionPoint, String className) {
		
		log(JptCoreMessages.REGISTRY_FAILED_INSTANTIATION,
				className,
				extensionPoint,
				pluginId);
	}
	
	public static void log(String msg, String... bindings) {
		JptCorePlugin.log(NLS.bind(msg, bindings));
	}
	
	public static void log(Exception e) {
		JptCorePlugin.log(e);
	}
	
	
	public static final class XPointException
			extends Exception {
		
		private static final long serialVersionUID = 1L;
	}
}
