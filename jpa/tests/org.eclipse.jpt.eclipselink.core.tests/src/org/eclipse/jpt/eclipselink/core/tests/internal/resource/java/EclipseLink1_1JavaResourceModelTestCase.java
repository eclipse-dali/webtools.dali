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
import org.eclipse.jpt.eclipselink.core.internal.EclipseLink1_1JpaAnnotationProvider;

public class EclipseLink1_1JavaResourceModelTestCase extends EclipseLinkJavaResourceModelTestCase
{	
	
	public EclipseLink1_1JavaResourceModelTestCase(String name) {
		super(name);
	}

	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return EclipseLink1_1JpaAnnotationProvider.instance();
	}
}
