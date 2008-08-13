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

public class EclipseLinkJpaPlatform extends GenericJpaPlatform
{
	public static String ID = "org.eclipse.eclipselink.platform";

	// ********* constructor *********
	public EclipseLinkJpaPlatform() {
		super();
	}

	@Override
	public String getId() {
		return EclipseLinkJpaPlatform.ID;
	}

	// ********* Model construction / updating *********
	@Override
	protected JpaFactory buildJpaFactory() {
		return new EclipseLinkJpaFactoryImpl();
	}

	// ********* java annotation support *********	
	@Override
	protected JpaAnnotationProvider buildAnnotationProvider() {
		return new EclipseLinkJpaAnnotationProvider();
	}
	
	@Override
	protected boolean supportsContentType(String contentTypeId) {
		return contentTypeId.equals(JptEclipseLinkCorePlugin.ECLIPSELINK_ORM_XML_CONTENT_TYPE) ||
			super.supportsContentType(contentTypeId);
	}
	
}
