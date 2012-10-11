/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

import org.eclipse.jpt.jaxb.core.JaxbWorkspace;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * The <code>org.eclipse.jpt.jaxb.core.jaxbPlatforms</code> extension point
 * corresponding to a {@link JaxbWorkspace JAXB workspace}.
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
 * @see JaxbPlatform
 * @see JaxbPlatformConfig
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPlatformManager {
	/**
	 * Return the manager's JAXB workspace.
	 */
	JaxbWorkspace getJaxbWorkspace();


	// ********** JAXB platforms **********

	/**
	 * Return the JAXB platform definition for the specified ID.
	 */
	JaxbPlatformDefinition getJaxbPlatformDefinition(String jaxbPlatformID);


	// ********** JAXB platform group configs **********

	/**
	 * Return all the JAXB platform group configs.
	 */
	Iterable<JaxbPlatformGroupConfig> getJaxbPlatformGroupConfigs();

	/**
	 * Return the JAXB platform group config for the specified ID.
	 */
	JaxbPlatformGroupConfig getJaxbPlatformGroupConfig(String groupID);


	// ********** JAXB platform configs **********

	/**
	 * Return all the JAXB platform configs.
	 */
	Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs();

	/**
	 * Return the JAXB platform config for the specified ID.
	 */
	JaxbPlatformConfig getJaxbPlatformConfig(String jaxbPlatformID);

	/**
	 * Return the JAXB platform configs that support the specified
	 * JAXB facet version.
	 */
	Iterable<JaxbPlatformConfig> getJaxbPlatformConfigs(IProjectFacetVersion jaxbFacetVersion);


	// ********** default JAXB platform config **********

	/**
	 * Return the JAXB platform config that is the workspace's default for
	 * the specified JAXB facet version.
	 */
	JaxbPlatformConfig getDefaultJaxbPlatformConfig(IProjectFacetVersion jaxbFacetVersion);

	/**
	 * Set the workspace's default JAXB platform config for the specified
	 * JAXB facet version.
	 * @see #getDefaultJaxbPlatformConfig(IProjectFacetVersion)
	 */
	void setDefaultJaxbPlatformConfig(IProjectFacetVersion jaxbFacetVersion, JaxbPlatformConfig config);
}
