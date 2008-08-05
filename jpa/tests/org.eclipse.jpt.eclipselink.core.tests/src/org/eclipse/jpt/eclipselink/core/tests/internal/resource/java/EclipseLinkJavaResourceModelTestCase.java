/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.SimpleJpaProjectConfig;
import org.eclipse.jpt.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkPlatform;

public class EclipseLinkJavaResourceModelTestCase extends JavaResourceModelTestCase
{	

	public static final String ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME = "org.eclipse.persistence.annotations";
	
	public EclipseLinkJavaResourceModelTestCase(String name) {
		super(name);
	}
	
	@Override
	protected ICompilationUnit createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return createAnnotationAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, annotationName, annotationBody);
	}
		
	@Override
	protected ICompilationUnit createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return createEnumAndMembers(ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME, enumName, enumBody);
	}

	@Override
	protected JpaProject.Config buildJpaProjectConfig(IProject project) {
		JptCorePlugin.setJpaPlatformId(project, EclipseLinkPlatform.ID);
		
		SimpleJpaProjectConfig config = new SimpleJpaProjectConfig();
		config.setProject(project);
		config.setJpaPlatform(JptCorePlugin.getJpaPlatform(project));
		config.setConnectionProfileName(JptCorePlugin.getConnectionProfileName(project));
		config.setDiscoverAnnotatedClasses(JptCorePlugin.discoverAnnotatedClasses(project));
		return config;
	}

}
