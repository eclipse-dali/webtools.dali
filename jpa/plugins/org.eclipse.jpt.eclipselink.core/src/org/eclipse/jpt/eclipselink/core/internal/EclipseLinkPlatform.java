/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.internal.platform.GenericJpaPlatform;

public class EclipseLinkPlatform extends GenericJpaPlatform
{
	public static String ID = "org.eclipse.eclipselink.platform";

	// ********* constructor *********
	public EclipseLinkPlatform() {
		super();
	}

	@Override
	public String getId() {
		return ID;
	}

	// ********* Model construction / updating *********
	@Override
	protected JpaFactory buildJpaFactory() {
		return new EclipseLinkFactory();
	}

	// ********* java annotation support *********
	@Override
	public JpaAnnotationProvider annotationProvider() {
		return super.annotationProvider();
	}
}
