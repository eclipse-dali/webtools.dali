/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;

public class EclipseLinkJpaPlatformUiFactory1_2
	implements JpaPlatformUiFactory
{
	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLinkJpaPlatformUiFactory1_2() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLink1_0JpaPlatformUi(
					EclipseLinkJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					EclipseLinkJpaPlatformUiProvider1_2.instance()
				);
	}
}
