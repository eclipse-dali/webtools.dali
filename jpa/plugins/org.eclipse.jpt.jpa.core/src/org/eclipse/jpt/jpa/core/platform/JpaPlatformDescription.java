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

import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Metadata that describes a JPA platform as defined in an
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
public interface JpaPlatformDescription {
	/**
	 * Return the description's manager.
	 */
	JpaPlatformManager getJpaPlatformManager();

	/**
	 * Return the description's extension-supplied ID.
	 * This is unique among all the JPA platform descriptions.
	 */
	String getId();

	/**
	 * Return the description's extension-supplied label.
	 */
	String getLabel();

	/**
	 * Return the description's extension-supplied factory class name.
	 * The class must be instantiable and implement the
	 * {@link org.eclipse.jpt.jpa.core.JpaPlatformFactory} interface.
	 */
	String getFactoryClassName();

	/**
	 * Return whether the description's JPA platform supports the specified
	 * JPA facet version. If the extension specifies a JPA facet version, it
	 * must be the same as the specified JPA facet version. If the extension
	 * does <em>not</em> specify a JPA facet verion, the description's JPA
	 * platform supports all JPA facet versions.
	 * @exception IllegalArgumentException if the specified facet version is
	 * not for a JPA facet
	 */
	boolean supportsJpaFacetVersion(IProjectFacetVersion jpaFacetVersion);

	/**
	 * Return whether the description's JPA platform can be used as the default
	 * JPA platform for its {@link #supportsJpaFacetVersion(IProjectFacetVersion)
	 * supported JPA facet versions}.
	 */
	boolean isDefault();

	/**
	 * Return description's group description.
	 */
	JpaPlatformGroupDescription getGroupDescription();

	/**
	 * Return the ID of the plug-in that contributed the JPA platform
	 * description.
	 */
	String getPluginId();
}
