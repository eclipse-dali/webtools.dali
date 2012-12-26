/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.commands;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.platform.EclipseLinkJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.handlers.AbstractJavaMetadataConversionHandler;

public class JavaConverterConversionHandler
	extends AbstractJavaMetadataConversionHandler
{
	public JavaConverterConversionHandler() {
		super();
	}

	@Override
	protected void convertJavaMetadata(JpaProject jpaProject) {
		this.getJpaPlatformUi(jpaProject).convertJavaConverterMetadataToGlobal(jpaProject);
	}

	@Override
	protected EclipseLinkJpaPlatformUi getJpaPlatformUi(JpaProject project) {
		return (EclipseLinkJpaPlatformUi) super.getJpaPlatformUi(project);
	}
}
