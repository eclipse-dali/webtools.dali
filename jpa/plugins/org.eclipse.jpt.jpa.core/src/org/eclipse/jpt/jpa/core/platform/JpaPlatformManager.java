/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.platform;

import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * The <code>org.eclipse.jpt.jpa.core.jpaPlatforms</code> extension point
 * corresponding to a {@link JpaWorkspace JPA workspace}.
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
 * @see JpaPlatform
 * @see org.eclipse.jpt.jpa.core.JpaPlatform.Config
 * @version 3.0
 * @since 3.0
 */
public interface JpaPlatformManager {
	/**
	 * Return the manager's JPA workspace.
	 */
	JpaWorkspace getJpaWorkspace();


	// ********** JPA platforms **********

	/**
	 * Return the JPA platform for the specified ID.
	 */
	JpaPlatform getJpaPlatform(String jpaPlatformID);


	// ********** JPA platform group configs **********

	/**
	 * Return all the JPA platform group configs.
	 */
	Iterable<JpaPlatform.GroupConfig> getJpaPlatformGroupConfigs();

	/**
	 * Return the JPA platform group config for the specified ID.
	 */
	JpaPlatform.GroupConfig getJpaPlatformGroupConfig(String groupID);


	// ********** JPA platform configs **********

	/**
	 * Return all the JPA platform configs.
	 */
	Iterable<JpaPlatform.Config> getJpaPlatformConfigs();

	/**
	 * Return the JPA platform config for the specified ID.
	 */
	JpaPlatform.Config getJpaPlatformConfig(String jpaPlatformID);

	/**
	 * Return the JPA platform configs that support the specified
	 * JPA facet version.
	 */
	Iterable<JpaPlatform.Config> getJpaPlatformConfigs(IProjectFacetVersion jpaFacetVersion);


	// ********** default JPA platform config **********

	/**
	 * Return the JPA platform config that is the workspace's default for
	 * the specified JPA facet version.
	 */
	JpaPlatform.Config getDefaultJpaPlatformConfig(IProjectFacetVersion jpaFacetVersion);

	/**
	 * Set the workspace's default JPA platform config for the specified
	 * JPA facet version.
	 * @see #getDefaultJpaPlatformConfig(IProjectFacetVersion)
	 */
	void setDefaultJpaPlatformConfig(IProjectFacetVersion jpaFacetVersion, JpaPlatform.Config config);
}
