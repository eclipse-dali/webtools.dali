/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.commands;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.platform.EclipseLinkJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.handlers.AbstractJavaMetadataConversionHandler;

public class EclipseLinkJavaConverterConversionHandler
	extends AbstractJavaMetadataConversionHandler
{
	public EclipseLinkJavaConverterConversionHandler() {
		super();
	}

	@Override
	protected void convertJavaMetadata(JpaPlatformUi ui, JpaProject jpaProject) {
		((EclipseLinkJpaPlatformUi) ui).convertJavaConverterMetadataToGlobal(jpaProject);
	}
}
