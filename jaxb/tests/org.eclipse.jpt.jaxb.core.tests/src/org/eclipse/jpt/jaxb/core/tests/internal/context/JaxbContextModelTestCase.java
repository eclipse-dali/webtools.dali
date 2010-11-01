/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.tests.internal.context;

import org.eclipse.jpt.core.tests.internal.projects.TestJavaProject;
import org.eclipse.jpt.core.tests.internal.utility.jdt.AnnotationTestCase;
import org.eclipse.jpt.core.utility.jdt.AnnotatedElement;
import org.eclipse.jpt.jaxb.core.JaxbFacet;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.internal.facet.JaxbFacetInstallConfig;
import org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDescription;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.TestJaxbProject;
import org.eclipse.jpt.utility.internal.ReflectionTools;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

@SuppressWarnings("nls")
public abstract class JaxbContextModelTestCase extends AnnotationTestCase
{
	protected static final String BASE_PROJECT_NAME = "JaxbContextModelTestProject";
	
	
	protected JaxbContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJaxbProject(BASE_PROJECT_NAME, autoBuild, buildJaxbFacetInstallConfig());
	}
	
	protected TestJaxbProject buildJaxbProject(String projectName, boolean autoBuild, JaxbFacetInstallConfig jaxbConfig) 
			throws Exception {
		return TestJaxbProject.buildJaxbProject(projectName, autoBuild, jaxbConfig);
	}
	
	protected JaxbFacetInstallConfig buildJaxbFacetInstallConfig() {
		JaxbFacetInstallConfig config = new JaxbFacetInstallConfig();
		config.setProjectFacetVersion(getProjectFacetVersion());
		config.setPlatform(getPlatform());
		return config;
	}
	
	protected JaxbPlatformDescription getPlatform() {
		return JptJaxbCorePlugin.getDefaultPlatform(getProjectFacetVersion());
	}

	protected IProjectFacetVersion getProjectFacetVersion() {
		return JaxbFacet.VERSION_2_1;
	}

	protected JaxbRootContextNode getRootContextNode() {
		return this.getJaxbProject().getRootContextNode();
	}
	
	protected AnnotatedElement annotatedElement(JavaResourceAnnotatedElement resource) {
		return (AnnotatedElement) ReflectionTools.getFieldValue(resource, "annotatedElement");
	}
	
	@Override
	protected TestJaxbProject getJavaProject() {
		return (TestJaxbProject) super.getJavaProject();
	}
	
	protected JaxbProject getJaxbProject() {
		return this.getJavaProject().getJaxbProject();
	}
}
