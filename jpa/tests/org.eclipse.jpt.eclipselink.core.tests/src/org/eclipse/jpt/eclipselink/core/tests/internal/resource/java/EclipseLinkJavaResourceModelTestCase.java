/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.tests.internal.resource.java.JavaResourceModelTestCase;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;

public class EclipseLinkJavaResourceModelTestCase extends JavaResourceModelTestCase
{	

	public static final String ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	
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
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}

}
