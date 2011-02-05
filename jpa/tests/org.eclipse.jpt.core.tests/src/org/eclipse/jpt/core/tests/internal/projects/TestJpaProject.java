/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.utility.Command;
import org.eclipse.jpt.common.utility.internal.synchronizers.CallbackSynchronousSynchronizer;
import org.eclipse.jpt.common.utility.internal.synchronizers.SynchronousSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * This builds and holds a "JPA" project.
 * Support for adding packages and types.
 * 
 * The JPA project's settings (platform, database connection, etc.) can be
 * controlled by building a data model and passing it into the constructor.
 */
@SuppressWarnings("nls")
public class TestJpaProject extends TestJavaProject {
	final JpaProject jpaProject;

	public static final String JPA_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jpa.jar";
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";


	// ********** builders **********

	public static TestJpaProject buildJpaProject(String baseProjectName, boolean autoBuild, IDataModel jpaConfig)
			throws CoreException {
		return new TestJpaProject(baseProjectName, autoBuild, jpaConfig);
	}

	public static TestJpaProject buildJpaProject(String baseProjectName, boolean autoBuild)
			throws CoreException {
		return buildJpaProject(baseProjectName, autoBuild, null);
	}

	// ********** constructors/initialization **********

	public TestJpaProject(String projectName) throws CoreException {
		this(projectName, false);
	}

	public TestJpaProject(String projectName, boolean autoBuild) throws CoreException {
		this(projectName, autoBuild, null);
	}

	public TestJpaProject(String projectName, boolean autoBuild, IDataModel jpaConfig) throws CoreException {
		super(projectName, autoBuild);
		String jpaFacetVersion = JpaFacet.VERSION_1_0.getVersionString();
		if (jpaConfig != null) {
			jpaFacetVersion = jpaConfig.getStringProperty(IFacetDataModelProperties.FACET_VERSION_STR);
		}
		this.installFacet("jst.utility", "1.0");
		this.installFacet(JpaFacet.ID, jpaFacetVersion, jpaConfig);
		this.addJar(jpaJarName());
		if (eclipseLinkJarName() != null) {
			this.addJar(eclipseLinkJarName());
		}
		this.jpaProject = JptCorePlugin.getJpaProject(this.getProject());
		this.jpaProject.setDiscoversAnnotatedClasses(true);
		this.jpaProject.setContextModelSynchronizer(this.buildSynchronousContextModelSynchronizer());
		this.jpaProject.setUpdateSynchronizer(this.buildSynchronousUpdateSynchronizer());
	}

	protected Synchronizer buildSynchronousContextModelSynchronizer() {
		return new SynchronousSynchronizer(this.buildSynchronousContextModelSynchronizerCommand());
	}

	protected Command buildSynchronousContextModelSynchronizerCommand() {
		return new SynchronousContextModelSynchronizerCommand();
	}

	protected class SynchronousContextModelSynchronizerCommand implements Command {
		public void execute() {
			TestJpaProject.this.jpaProject.synchronizeContextModel(new NullProgressMonitor());
		}
	}

	protected CallbackSynchronizer buildSynchronousUpdateSynchronizer() {
		return new CallbackSynchronousSynchronizer(this.buildSynchronousUpdateSynchronizerCommand());
	}

	protected Command buildSynchronousUpdateSynchronizerCommand() {
		return new SynchronousUpdateSynchronizerCommand();
	}

	protected class SynchronousUpdateSynchronizerCommand implements Command {
		public void execute() {
			TestJpaProject.this.jpaProject.update(new NullProgressMonitor());
		}
	}

	public static String jpaJarName() {
		return getSystemProperty(JPA_JAR_NAME_SYSTEM_PROPERTY);
	}

	public static String eclipseLinkJarName() {
		return getSystemProperty(ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}

	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}


	// ********** public methods **********

	public JpaProject getJpaProject() {
		return this.jpaProject;
	}

}
