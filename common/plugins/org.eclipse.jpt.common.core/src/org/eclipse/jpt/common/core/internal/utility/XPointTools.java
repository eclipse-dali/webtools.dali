/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.osgi.util.NLS;
import org.osgi.framework.Bundle;

/**
 * Utilities for Eclipse extension point management, validation, etc.
 */
public class XPointTools {
	/**
	 * Return the value of the specified attribute of the specified element.
	 * @exception XPointException if the value is <code>null</code>.
	 */
	public static String findRequiredAttribute(IConfigurationElement element, String attributeName) throws XPointException {
		String value = element.getAttribute(attributeName);
		if (value == null) {
			logMissingAttribute(element, attributeName);
			throw new XPointException();
		}
		return value;
	}

	private static void logMissingAttribute(IConfigurationElement element, String attributeName) {
		logError(buildMissingAttributeMessage(element, attributeName));
    }

	/**
	 * Return a helpful message indicating the specified attribute is missing
	 * from the specified element.
	 */
	public static String buildMissingAttributeMessage(IConfigurationElement element, String attributeName) {
		return bind(JptCommonCoreMessages.REGISTRY_MISSING_ATTRIBUTE,
						attributeName,
						element.getName(),
						element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
						element.getContributor().getName()
					);
    }
	
	/**
	 * Return a helpful message indicating the specified attribute
	 * from the specified element has an invalid value.
	 */
	public static String buildInvalidValueMessage(IConfigurationElement element, String attributeName, String invalidValue) {
		return bind(JptCommonCoreMessages.REGISTRY_INVALID_VALUE,
						invalidValue,
						attributeName,
						element.getDeclaringExtension().getExtensionPointUniqueIdentifier(),
						element.getContributor().getName()
					);
	}

	private static String bind(String msg, Object... args) {
		return NLS.bind(msg, args);
	}
	
	/**
	 * Load the specified class, using the specified bundle, and, if it is a
	 * sub-type the specified interface, instantiate it and return the resulting
	 * object, cast appropriately.
	 * Log an error and return <code>null</code> for any of the following
	 * conditions:<ul>
	 * <li>the bundle cannot be resolved
	 * <li>the class fails to load
	 * <li>the loaded class is not a sub-type of the specified interface
	 * <li>the loaded class cannot be instantiated.
	 * </ul>
	 */
	public static <T> T instantiate(String pluginID, String extensionPoint, String className, Class<T> interfaze) {
		Class<T> clazz = loadClass(pluginID, extensionPoint, className, interfaze);
		return (clazz == null) ? null : instantiate(pluginID, extensionPoint, clazz);
    }
	
	/**	
	 * Load the specified class, using the specified bundle, and cast it to the
	 * specified interface before returning it.
	 * Log an error and return <code>null</code> for any of the following
	 * conditions:<ul>
	 * <li>the bundle cannot be resolved
	 * <li>the class fails to load
	 * <li>the loaded class is not a sub-type of the specified interface
	 * </ul>
	 */
	private static <T> Class<T> loadClass(String pluginID, String extensionPoint, String className, Class<T> interfaze) {
		Bundle bundle = Platform.getBundle(pluginID);
		if (bundle == null) {
			logMissingBundle(pluginID);
			return null;
		}

		Class<?> clazz;
		try {
			clazz = bundle.loadClass(className);
		} catch (Exception ex) {
			logFailedClassLoad(ex, pluginID, extensionPoint, className);
			return null;
		}

		if ( ! interfaze.isAssignableFrom(clazz)) {
			logFailedInterfaceAssignment(pluginID, extensionPoint, clazz, interfaze);
			return null;
		}

		@SuppressWarnings("unchecked")
		Class<T> clazzT = (Class<T>) clazz;
		return clazzT;
    }
	
	private static void logMissingBundle(String pluginID) {
		logError(JptCommonCoreMessages.REGISTRY_MISSING_BUNDLE, pluginID);
	}
	
	private static void logFailedClassLoad(Exception ex, String pluginID, String extensionPoint, String className) {
		logError(ex, JptCommonCoreMessages.REGISTRY_FAILED_CLASS_LOAD,
				className,
				extensionPoint,
				pluginID
			);
	}
	
	private static void logFailedInterfaceAssignment(String pluginID, String extensionPoint, Class<?> clazz, Class<?> interfaze) {
		logError(JptCommonCoreMessages.REGISTRY_FAILED_INTERFACE_ASSIGNMENT,
				clazz.getName(),
				extensionPoint,
				pluginID,
				interfaze.getName()
			);
	}
	
	/**
	 * Instantiate the specified class.
	 * Log an error and return <code>null</code> if the instantiation fails.
	 */
	private static <T> T instantiate(String pluginID, String extensionPoint, Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception ex) {
			logFailedInstantiation(ex, pluginID, extensionPoint, clazz);
			return null;
		}
	}

	private static void logFailedInstantiation(Exception ex, String pluginID, String extensionPoint, Class<?> clazz) {
		logError(ex, JptCommonCoreMessages.REGISTRY_FAILED_INSTANTIATION,
				clazz.getName(),
				extensionPoint,
				pluginID
			);
	}
	
	private static void logError(String msg, Object... args) {
		JptCommonCorePlugin.instance().logError(msg, args);
	}
	
	private static void logError(Throwable ex, String msg, Object... args) {
		JptCommonCorePlugin.instance().logError(ex, msg, args);
	}

	private static void logError(String msg) {
		JptCommonCorePlugin.instance().logError(msg);
	}

	private XPointTools() {
		throw new UnsupportedOperationException();
	}

	/**
	 * exception
	 */
	public static final class XPointException extends Exception {
		private static final long serialVersionUID = 1L;
	}
}
