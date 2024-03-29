/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.tests.internal.resource.java;

import org.eclipse.jpt.common.core.AnnotationProvider;
import org.eclipse.jpt.jpa.core.internal.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.JpaAnnotationProvider;
import org.eclipse.jpt.jpa.core.tests.internal.resource.java.JpaJavaResourceModelTestCase;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;

public class EclipseLinkJavaResourceModelTestCase extends JpaJavaResourceModelTestCase
{	

	public static final String ECLIPSELINK_ANNOTATIONS_PACKAGE_NAME = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	
	public EclipseLinkJavaResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected AnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProvider(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance());
	}

}
