/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * Utilities for extension point management, validation, etc.
 */
public class XPointTools {
	
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
	
	public static <T> T instantiate(String pluginId, String extensionPoint, String className, Class<T> interfaze) {
		Class<T> clazz = loadClass(pluginId, extensionPoint, className, interfaze);
		return (clazz == null) ? null : instantiate(pluginId, extensionPoint, clazz);
    }
	
	/**
	 * Instantiate the specified class.
	 */
	public static <T> T instantiate(String pluginId, String extensionPoint, Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			logFailedInstantiation(ex, pluginId, extensionPoint, clazz.getName());
			return null;
		}
	}

	/**	
	 * Load the specified class and cast it to the specified interface.
	 */
	private static <T> Class<T> loadClass(String pluginId, String extensionPoint, String className, Class<T> interfaze) {
		Bundle bundle = Platform.getBundle(pluginId);

		Class<?> clazz;
		try {
			clazz = bundle.loadClass(className);
		} catch (Exception ex) {
			logFailedClassLoad(ex, pluginId, extensionPoint, className);
			return null;
		}
		
		if (interfaze.isAssignableFrom(clazz)) {
			@SuppressWarnings("unchecked")
			Class<T> clazzT = (Class<T>) clazz;
			return clazzT;
		}
		
		logFailedInterfaceAssignment(pluginId, extensionPoint, className, interfaze.getName());
		return null;
    }
	
	public static void logDuplicateExtension(String extensionPoint, String nodeName, String value) {
		log(JptCommonCoreMessages.REGISTRY_DUPLICATE, extensionPoint, nodeName, value);
	}
	
	public static void logMissingAttribute(IConfigurationElement configElement, String attributeName) {
		log(JptCommonCoreMessages.REGISTRY_MISSING_ATTRIBUTE,
				attributeName,
				configElement.getName(),
				configElement.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				configElement.getContributor().getName());
    }
	
	public static void logInvalidValue(
			IConfigurationElement configElement, String nodeName, String invalidValue) {
		
		log(JptCommonCoreMessages.REGISTRY_INVALID_VALUE,
				invalidValue,
				nodeName,
				configElement.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
				configElement.getContributor().getName());
	}
	
	private static void logFailedClassLoad(Exception ex, String pluginId, String extensionPoint, String className) {
		log(ex, JptCommonCoreMessages.REGISTRY_FAILED_CLASS_LOAD,
				className,
				extensionPoint,
				pluginId);
	}
	
	private static void logFailedInterfaceAssignment(
			String pluginId, String extensionPoint, String className, String interfaceName) {
		
		log(JptCommonCoreMessages.REGISTRY_FAILED_INTERFACE_ASSIGNMENT,
				className,
				extensionPoint,
				pluginId,
				interfaceName);
	}
	
	private static void logFailedInstantiation(Exception ex, String pluginId, String extensionPoint, String className) {
		log(ex, JptCommonCoreMessages.REGISTRY_FAILED_INSTANTIATION,
				className,
				extensionPoint,
				pluginId);
	}
	
	public static void log(String msg, String... bindings) {
		JptCommonCorePlugin.log(NLS.bind(msg, bindings));
	}
	
	public static void log(Throwable ex, String msg, String... bindings) {
		JptCommonCorePlugin.log(NLS.bind(msg, bindings), ex);
	}
	
	public static void log(Throwable ex) {
		JptCommonCorePlugin.log(ex);
	}
	
	
	public static final class XPointException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
