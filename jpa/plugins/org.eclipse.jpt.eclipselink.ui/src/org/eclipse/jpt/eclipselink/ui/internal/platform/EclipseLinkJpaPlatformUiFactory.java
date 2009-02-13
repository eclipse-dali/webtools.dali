/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.platform;

import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkJpaUiFactory;
import org.eclipse.jpt.eclipselink.ui.internal.structure.EclipseLinkPersistenceResourceModelStructureProvider;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.ui.internal.GenericJpaPlatformUiProvider;
import org.eclipse.jpt.ui.internal.structure.JavaResourceModelStructureProvider;

public class EclipseLinkJpaPlatformUiFactory implements JpaPlatformUiFactory
{

	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLinkJpaPlatformUiFactory() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLinkJpaPlatformUi(
			new EclipseLinkJpaUiFactory(),
			new EclipseLinkNavigatorProvider(),
			JavaResourceModelStructureProvider.instance(), 
			EclipseLinkPersistenceResourceModelStructureProvider.instance(),
			GenericJpaPlatformUiProvider.instance(),
			EclipseLinkJpaPlatformUiProvider.instance()
		);
	}
}
