/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.tests.internal.resource.java;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.platform.GenericJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.platform.JpaAnnotationProviderImpl;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLink1_1JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaAnnotationDefinitionProvider;

public class EclipseLink1_1JavaResourceModelTestCase extends EclipseLinkJavaResourceModelTestCase
{	
	
	public EclipseLink1_1JavaResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new JpaAnnotationProviderImpl(
			GenericJpaAnnotationDefinitionProvider.instance(),
			EclipseLinkJpaAnnotationDefinitionProvider.instance(),
			EclipseLink1_1JpaAnnotationDefinitionProvider.instance());
	}
}
