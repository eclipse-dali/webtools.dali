/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;

public class EclipseLinkJpaPlatformUiFactory2_4
	extends EclipseLinkJpaPlatformUiFactory2_0
{
	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLinkJpaPlatformUiFactory2_4() {
		super();
	}

	@Override
	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLinkJpaPlatformUi2_0(
					EclipseLinkJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					EclipseLinkJpaPlatformUiProvider2_4.instance()
				);
	}
}
