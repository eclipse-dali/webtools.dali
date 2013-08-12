/*******************************************************************************
 * Copyright (c) 2005, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.projects;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.tests.internal.projects.JavaProjectTestHarness;
import org.eclipse.jpt.jpa.core.JpaProject;
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
public class JpaProjectTestHarness
	extends JavaProjectTestHarness
{
	public static final String JPA_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.jpa.jar";
	public static final String ECLIPSELINK_JAR_NAME_SYSTEM_PROPERTY = "org.eclipse.jpt.eclipselink.jar";


	public JpaProjectTestHarness(String projectName) throws CoreException {
		this(projectName, false);
	}

	public JpaProjectTestHarness(String projectName, boolean autoBuild) throws CoreException {
		this(projectName, autoBuild, null);
	}

	public JpaProjectTestHarness(String projectName, boolean autoBuild, IDataModel jpaConfig) throws CoreException {
		super(projectName, autoBuild);
		String jpaFacetVersion = JpaProject.FACET_VERSION_STRING;
		if (jpaConfig != null) {
			jpaFacetVersion = jpaConfig.getStringProperty(IFacetDataModelProperties.FACET_VERSION_STR);
		}
		this.installFacet("jst.utility", "1.0");
		this.installFacet(JpaProject.FACET_ID, jpaFacetVersion, jpaConfig);
		this.addJar(jpaJarName());
		if (eclipseLinkJarName() != null) {
			this.addJar(eclipseLinkJarName());
		}
		this.getJpaProject().setDiscoversAnnotatedClasses(true);
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
		try {
			return this.getJpaProject_();
		} catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}

	private JpaProject getJpaProject_() throws InterruptedException {
		return this.getJpaProjectReference().getValue();
	}

	private JpaProject.Reference getJpaProjectReference() {
		return (JpaProject.Reference) this.getProject().getAdapter(JpaProject.Reference.class);
	}
}
