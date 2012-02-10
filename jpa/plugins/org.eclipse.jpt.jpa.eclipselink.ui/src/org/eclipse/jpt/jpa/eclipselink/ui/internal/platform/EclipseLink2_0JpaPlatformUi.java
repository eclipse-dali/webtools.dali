/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards.EclipseLink2_0DDLGeneratorUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.base.BaseJpaPlatformUi;

public class EclipseLink2_0JpaPlatformUi
	extends BaseJpaPlatformUi
{
	public EclipseLink2_0JpaPlatformUi(
					ItemTreeStateProviderFactoryProvider navigatorFactoryProvider,
					JpaPlatformUiProvider platformUiProvider
	) {
		super(navigatorFactoryProvider, platformUiProvider);
	}

	// ********** DDL generation **********

	public void generateDDL(JpaProject project, IStructuredSelection selection) {
		EclipseLink2_0DDLGeneratorUi.generate(project);
	}
}
