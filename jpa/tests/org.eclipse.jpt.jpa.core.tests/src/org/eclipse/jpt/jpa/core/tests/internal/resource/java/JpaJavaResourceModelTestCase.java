/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.tests.internal.projects.JpaProjectTestHarness;

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
		this.javaProjectTestHarness.addJar(JpaProjectTestHarness.jpaJarName());
		if (JpaProjectTestHarness.eclipseLinkJarName() != null) {
			this.javaProjectTestHarness.addJar(JpaProjectTestHarness.eclipseLinkJarName());
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		// nothing as of yet
	}

	@Override
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(this.annotationDefinitionProvider());
	}

	protected JpaAnnotationDefinitionProvider annotationDefinitionProvider() {
		return GenericJpaAnnotationDefinitionProvider.instance();
	}
	
	@Override
	protected AnnotationDefinition[] annotationDefinitions() {
		throw new UnsupportedOperationException("We build the AnnotationProvider for JPA differently for now");
	}

	@Override
	protected NestableAnnotationDefinition[] nestableAnnotationDefinitions() {
		throw new UnsupportedOperationException("We build the AnnotationProvider for JPA differently for now");
	}

	protected ICompilationUnit createAnnotationAndMembers(String annotationName, String annotationBody) throws Exception {
		return createAnnotationAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, annotationName, annotationBody);
	}
	
	protected ICompilationUnit createEnumAndMembers(String enumName, String enumBody) throws Exception {
		return createEnumAndMembers(JAVAX_PERSISTENCE_PACKAGE_NAME, enumName, enumBody);
	}
}
