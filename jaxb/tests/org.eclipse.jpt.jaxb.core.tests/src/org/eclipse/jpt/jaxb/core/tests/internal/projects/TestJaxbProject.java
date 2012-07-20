/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jpt.common.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.synchronizers.CallbackSynchronousSynchronizer;
import org.eclipse.jpt.common.utility.internal.synchronizers.SynchronousSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.CallbackSynchronizer;
import org.eclipse.jpt.common.utility.synchronizers.Synchronizer;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.wst.common.componentcore.datamodel.properties.IFacetDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * This builds and holds a "JAXB" project.
 * Support for adding packages and types.
 * 
 * The JPA project's settings (platform, database connection, etc.) can be
 * controlled by building a data model and passing it into the constructor.
 */
public class TestJaxbProject
		extends TestJavaProject {
	
	private final JaxbProject jaxbProject;
	
	
	// ***** static methods *****
	
	public static final String JAXB_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jaxb.jar";
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";
	
	public static String jaxbJarName() {
		return getSystemProperty(JAXB_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	public static String eclipseLinkJarName() {
		return getSystemProperty(ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY);
	}
	
	private static String getSystemProperty(String propertyName) {
		return System.getProperty(propertyName);
	}
	
	
	// ********** builders **********
	
	public static TestJaxbProject buildJaxbProject(
			String baseProjectName, boolean autoBuild, IDataModel config)
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
	
	public TestJaxbProject(String projectName, boolean autoBuild, IDataModel config) throws CoreException {
		super(projectName, autoBuild);
		String jaxbFacetVersion = 
				((IProjectFacetVersion) config.getProperty(IFacetDataModelProperties.FACET_VERSION)).getVersionString();
		this.installFacet(JaxbFacet.ID, jaxbFacetVersion, config);
		String jaxbJarName = jaxbJarName();
		this.addJar(jaxbJarName);
		String eclipseLinkJarName = eclipseLinkJarName();
		if ((eclipseLinkJarName != null) && ! eclipseLinkJarName.equals(jaxbJarName)) {
			this.addJar(eclipseLinkJarName);
		}
		this.jaxbProject = JptJaxbCorePlugin.instance().getProjectManager().getJaxbProject(this.getProject());
		this.jaxbProject.setContextModelSynchronizer(this.buildSynchronousContextModelSynchronizer());
		this.jaxbProject.setUpdateSynchronizer(this.buildSynchronousUpdateSynchronizer());
	}
	
	protected Synchronizer buildSynchronousContextModelSynchronizer() {
		return new SynchronousSynchronizer(this.buildSynchronousContextModelSynchronizerCommand());
	}

	protected Command buildSynchronousContextModelSynchronizerCommand() {
		return new SynchronousContextModelSynchronizerCommand();
	}

	protected class SynchronousContextModelSynchronizerCommand implements Command {
		public void execute() {
			TestJaxbProject.this.jaxbProject.synchronizeContextModel(new NullProgressMonitor());
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
			TestJaxbProject.this.jaxbProject.update(new NullProgressMonitor());
		}
	}
	
	
	// ********** public methods **********
	
	public JaxbProject getJaxbProject() {
		return this.jaxbProject;
	}
}
