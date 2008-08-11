/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.orm;

import junit.framework.TestCase;
import org.eclipse.jpt.core.tests.internal.ProjectUtility;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;
import org.eclipse.jpt.eclipselink.core.internal.JptEclipseLinkCorePlugin;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmArtifactEdit;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmResource;
import org.eclipse.jpt.eclipselink.core.resource.elorm.XmlEntityMappings;

public class EclipseLinkOrmResourceModelTests extends TestCase
{
	static final String BASE_PROJECT_NAME = EclipseLinkOrmResourceModelTests.class.getSimpleName();
	
	TestJpaProject jpaProject;

	
	public EclipseLinkOrmResourceModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ProjectUtility.deleteAllProjects();
		jpaProject = TestJpaProject.buildJpaProject(BASE_PROJECT_NAME, false); // false = no auto-build
		
		EclipseLinkOrmArtifactEdit ae = 
			EclipseLinkOrmArtifactEdit.getArtifactEditForWrite(jpaProject.getProject());
		EclipseLinkOrmResource resource = 
			ae.getResource(JptEclipseLinkCorePlugin.getDefaultEclipseLinkOrmXmlDeploymentURI(jpaProject.getProject()));

		// 202811 - do not add content if it is already present
		if (resource.getEntityMappings() == null) {
			XmlEntityMappings entityMappings = EclipseLinkOrmFactory.eINSTANCE.createXmlEntityMappings();
			entityMappings.setVersion("1.0");
			resource.getContents().add(entityMappings);
			ae.save(null);
		}
		
		ae.dispose();
	}
	
	@Override
	protected void tearDown() throws Exception {
		jpaProject = null;
		super.tearDown();
	}
	
	public void testModelLoad() {
		EclipseLinkOrmArtifactEdit artifactEdit = 
			EclipseLinkOrmArtifactEdit.getArtifactEditForRead(jpaProject.getProject());
		assertNotNull(artifactEdit);
		EclipseLinkOrmResource resource = artifactEdit.getResource("META-INF/eclipselink-orm.xml");
		assertNotNull(resource);
		artifactEdit.dispose();
	}
	
	public void testModelLoad2() {
		EclipseLinkOrmArtifactEdit artifactEdit = 
			EclipseLinkOrmArtifactEdit.getArtifactEditForRead(jpaProject.getProject());
		assertNotNull(artifactEdit);
		EclipseLinkOrmResource resource = artifactEdit.getResource("META-INF/eclipselink-orm.xml");
		assertNotNull(resource);
		artifactEdit.dispose();
	}
}
