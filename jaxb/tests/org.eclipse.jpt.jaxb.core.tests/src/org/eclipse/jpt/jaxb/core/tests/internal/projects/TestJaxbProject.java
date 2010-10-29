/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetInstallConfig;

/**
 * This builds and holds a "JAXB" project.
 * Support for adding packages and types.
 * 
 * The JPA project's settings (platform, database connection, etc.) can be
 * controlled by building a data model and passing it into the constructor.
 */
@SuppressWarnings("nls")
public class TestJaxbProject extends TestJavaProject {
	private final JaxbProject jaxbProject;


	// ********** builders **********

	public static TestJaxbProject buildJaxbProject(String baseProjectName, boolean autoBuild, JaxbFacetInstallConfig config)
			throws CoreException {
		return new TestJaxbProject(baseProjectName, autoBuild, config);
	}

	// ********** constructors/initialization **********

	public TestJaxbProject(String projectName) throws CoreException {
		this(projectName, false);
	}

	public TestJaxbProject(String projectName, boolean autoBuild) throws CoreException {
		this(projectName, autoBuild, null);
	}

	public TestJaxbProject(String projectName, boolean autoBuild, JaxbFacetInstallConfig config) throws CoreException {
		super(projectName, autoBuild);
		String jaxbFacetVersion = config.getProjectFacetVersion().getVersionString();
		this.installFacet(JaxbFacet.ID, jaxbFacetVersion, config);
		this.jaxbProject = JptJaxbCorePlugin.getJaxbProject(this.getProject());
//		this.jaxbProject.setUpdater(new SynchronousJpaProjectUpdater(this.jaxbProject));
	}



	// ********** public methods **********

	public JaxbProject getJaxbProject() {
		return this.jaxbProject;
	}

}
