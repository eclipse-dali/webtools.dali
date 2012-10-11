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
import org.eclipse.jpt.common.core.internal.JptCommonCoreMessages;
import org.eclipse.osgi.util.NLS;

/**
 * {@link IConfigurationElement} utility methods.
 */
public class ConfigurationElementTools {
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

	private ConfigurationElementTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
