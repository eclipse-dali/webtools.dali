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

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.GenericJpaAnnotationProvider;
import org.eclipse.jpt.core.tests.internal.resource.java.JpaJavaResourceModelTestCase;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;

public class EclipseLinkJavaResourceModelTestCase extends JpaJavaResourceModelTestCase
{	

	public static final String ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	
	public EclipseLinkJavaResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new GenericJpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}

}
