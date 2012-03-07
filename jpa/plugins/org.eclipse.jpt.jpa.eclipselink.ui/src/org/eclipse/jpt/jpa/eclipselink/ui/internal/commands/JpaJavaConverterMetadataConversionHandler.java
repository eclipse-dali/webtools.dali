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
import org.eclipse.jpt.jpa.ui.internal.commands.AbstractJpaJavaMetadataConversionHandler;

public class JpaJavaConverterMetadataConversionHandler extends AbstractJpaJavaMetadataConversionHandler {

	public JpaJavaConverterMetadataConversionHandler() {
		super();
	}

	@Override
	protected void converterJavaGlobalMetadata(JpaProject jpaProject) {
		this.getJpaPlatformUi(jpaProject).convertJavaConverterMetadataToGlobal(jpaProject);
	}

	@Override
	protected EclipseLinkJpaPlatformUi getJpaPlatformUi(JpaProject project) {
		return (EclipseLinkJpaPlatformUi) super.getJpaPlatformUi(project);
	}
}
