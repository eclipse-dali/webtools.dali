/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.JptCorePlugin;

public class TestJpaProject extends TestJavaProject {
	private IJpaProject jpaProject;

	public static final String JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jpa.jar";
	
	
	// ********** builders *****************************
	
	public static TestJpaProject buildJpaProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return new TestJpaProject(uniqueProjectName(baseProjectName), autoBuild);
	}

	
	// ********** constructors/initialization **********

	public TestJpaProject(String projectName) throws CoreException {
		this(projectName, false);
	}
	
	public TestJpaProject(String projectName, boolean autoBuild) throws CoreException {
		super(projectName, autoBuild);
		this.installFacet("jst.utility", "1.0");
		this.installFacet("jpt.jpa", "1.0");
		this.addJar(this.jarName());
		this.jpaProject = JptCorePlugin.getJpaProject(this.getProject());
	}

	protected String jarName() {
		String jarName = System.getProperty(JAR_NAME_SYSTEM_PROPERTY);
		if (jarName == null) {
			throw new RuntimeException("missing Java system property: \"" + JAR_NAME_SYSTEM_PROPERTY + "\"");
		}
		return jarName;
	}


	// ********** public methods **********

	public IJpaProject getJpaProject() {
		return this.jpaProject;
	}

	@Override
	public void dispose() throws CoreException {
		this.jpaProject = null;
		super.dispose();
	}

}
