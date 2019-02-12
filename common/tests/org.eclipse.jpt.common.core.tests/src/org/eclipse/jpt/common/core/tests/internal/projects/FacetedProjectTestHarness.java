/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * This builds and holds a "faceted" project.
 * Support for installing facets.
 */
public class FacetedProjectTestHarness
	extends ProjectTestHarness
{
	private final IFacetedProject facetedProject;


	public FacetedProjectTestHarness(String projectName) throws CoreException {
		this(projectName, true);
	}
	
	public FacetedProjectTestHarness(String projectName, boolean autoBuild) throws CoreException {
		super(projectName, autoBuild);
		this.facetedProject = this.createFacetedProject();
	}

	private IFacetedProject createFacetedProject() throws CoreException {
		return ProjectFacetsManager.create(this.getProject(), true, null);		// true = "convert if necessary"
	}

	public IFacetedProject getFacetedProject() {
		return this.facetedProject;
	}

	public void installFacet(String facetName, String versionName) throws CoreException {
		this.installFacet(facetName, versionName, null);
	}

	public void uninstallFacet(String facetName, String versionName) throws CoreException {
		this.uninstallFacet(facetName, versionName, null);
	}

	/**
	 * If <code>config</code> is <code>null</code>
	 * (and <code>facetName</code> is <code>"jpt.jpa"</code>),
	 * the JPA project will be built with the defaults defined in
	 * {@code org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider.getDefaultProperty(String)}.
	 * <p>
	 * <code>config</code> will be an org.eclipse.wst.common.frameworks.datamodel.IDataModel
	 */
	public void installFacet(String facetName, String versionName, Object config) throws CoreException {
		this.facetedProject.installProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	public void uninstallFacet(String facetName, String versionName, Object config) throws CoreException {
		this.facetedProject.uninstallProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	private IProjectFacetVersion facetVersion(String facetName, String versionName) {
		return ProjectFacetsManager.getProjectFacet(facetName).getVersion(versionName);
	}
}
