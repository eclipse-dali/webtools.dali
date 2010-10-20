/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.tests.internal.projects.TestJpaProject;

@SuppressWarnings("nls")
public class JpaJavaResourceModelTestCase
		extends JavaResourceModelTestCase {
	
	public static final String JAVAX_PERSISTENCE_PACKAGE_NAME = "javax.persistence"; //$NON-NLS-1$
	
	
	public JpaJavaResourceModelTestCase(String name) {
		super(name);
	}
	
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.javaProject.addJar(TestJpaProject.jpaJarName());
		if (TestJpaProject.eclipseLinkJarName() != null) {
			this.javaProject.addJar(TestJpaProject.eclipseLinkJarName());
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// nothing as of yet
	}
	
	protected ICompilationUnit createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return createAnnotationAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, annotationName, annotationBody);
	}
	
	protected ICompilationUnit createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return createEnumAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, enumName, enumBody);
	}
}
