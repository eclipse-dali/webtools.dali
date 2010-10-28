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
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.tests.internal.projects.TestJaxbProject;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

@SuppressWarnings("nls")
public abstract class JaxbContextModelTestCase extends AnnotationTestCase
{
	protected static final String BASE_PROJECT_NAME = "JaxbContextModelTestProject";
	
	
	protected JaxbContextModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected TestJavaProject buildJavaProject(boolean autoBuild) throws Exception {
		return buildJaxbProject(BASE_PROJECT_NAME, autoBuild, null);
	}
	
	protected TestJaxbProject buildJaxbProject(String projectName, boolean autoBuild, IDataModel jaxbConfig) 
			throws Exception {
		return TestJaxbProject.buildJaxbProject(projectName, autoBuild, jaxbConfig);
	}
	
//	protected IDataModel buildJaxbConfigDataModel() {
//		IDataModel dataModel = DataModelFactory.createDataModel(new JaxbFacetInstallDataModelProvider());		
//		// default facet version is 2.0 - most tests use 1.0
//		dataModel.setProperty(IFacetDataModelProperties.FACET_VERSION_STR, JpaFacet.VERSION_1_0.getVersionString());
//		// most tests use the basic generic platform
//		dataModel.setProperty(JpaFacetDataModelProperties.PLATFORM, GenericPlatform.VERSION_1_0);
//		// most tests do use an orm.xml
//		dataModel.setProperty(JpaFacetInstallDataModelProperties.CREATE_ORM_XML, Boolean.TRUE);
//		return dataModel;
//	}
//
//	protected JaxbRootContextNode getRootContextNode() {
//		return this.getJaxbProject().getRootContextNode();
//	}
	
	@Override
	protected TestJaxbProject getJavaProject() {
		return (TestJaxbProject) super.getJavaProject();
	}
	
	protected JaxbProject getJaxbProject() {
		return this.getJavaProject().getJaxbProject();
	}
//	
//	protected void deleteResource(Resource resource) throws CoreException {
//		WorkbenchResourceHelper.deleteResource(resource);
//	}
}
