/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.platform;

/**
 * Metadata that describes a JPA platform group as defined in an
 * extension to the <code>org.eclipse.jpt.jpa.core.jpaPlatforms</code>
 * extension point.
 * <p>
 * See <code>org.eclipse.jpt.jpa.core/plugin.xml:jpaPlatforms</code>.
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
public interface JpaPlatformGroupDescription {
	/**
	 * Return the description's manager.
	 */
	JpaPlatformManager getJpaPlatformManager();

	/**
	 * Return the JPA platform group description's extension-supplied ID.
	 * This is unique among all the JPA platform group descriptions.
	 */
	String getId();

	/**
	 * Return the JPA platform group description's extension-supplied label.
	 */
	String getLabel();

	/**
	 * Return the JPA platform descriptions that belong to the group.
	 */
	Iterable<JpaPlatformDescription> getJpaPlatformDescriptions();

	/**
	 * Return the ID of the plug-in that contributed the JPA platform group
	 * description.
	 */
	String getPluginId();
}
