/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * This builds and holds a "faceted" project.
 * Support for installing facets.
 */
public class TestFacetedProject extends TestPlatformProject {
	private final IFacetedProject facetedProject;


	// ********** builders *****************************
	
	public static TestFacetedProject buildFacetedProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new TestFacetedProject(baseProjectName, autoBuild);
	}
	
	
	// ********** constructors/initialization **********

	public TestFacetedProject(String projectName) throws CoreException {
		this(projectName, true);
	}
	
	public TestFacetedProject(String projectName, boolean autoBuild) throws CoreException {
		super(projectName, autoBuild);
		this.facetedProject = this.createFacetedProject();
	}

	private IFacetedProject createFacetedProject() throws CoreException {
		return ProjectFacetsManager.create(this.getProject(), true, null);		// true = "convert if necessary"
	}


	// ********** public methods **********

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
	 * if 'config' is null (and 'facetName' is "jpt.jpa"), the JPA project will be built with the defaults
	 * defined in JpaFacetDataModelProvider#getDefaultProperty(String)
	 */
	public void installFacet(String facetName, String versionName, IDataModel config) throws CoreException {
		this.facetedProject.installProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	public void uninstallFacet(String facetName, String versionName, IDataModel config) throws CoreException {
		this.facetedProject.uninstallProjectFacet(this.facetVersion(facetName, versionName), config, null);
	}

	private IProjectFacetVersion facetVersion(String facetName, String versionName) {
		return ProjectFacetsManager.getProjectFacet(facetName).getVersion(versionName);
	}

}
