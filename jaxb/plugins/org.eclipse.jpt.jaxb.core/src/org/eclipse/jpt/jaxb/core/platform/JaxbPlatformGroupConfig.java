/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

/**
 * Metadata that describes a JAXB platform group as defined in an
 * extension to the <code>org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>
 * extension point.
 * <p>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:jaxbPlatforms</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPlatformGroupConfig {
	/**
	 * Return the config's manager.
	 */
	JaxbPlatformManager getJaxbPlatformManager();

	/**
	 * Return the JAXB platform group config's extension-supplied ID.
	 * This is unique among all the JAXB platform group configs.
	 */
	String getId();

	/**
	 * Return the JAXB platform group config's extension-supplied label.
	 */
	String getLabel();

	/**
	 * Return the JAXB platform configs that belong to the group.
	 */
	Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs();

	/**
	 * Return the ID of the plug-in that contributed the JAXB platform group
	 * config.
	 */
	String getPluginId();
}
