/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;

public class EclipseLinkJpaPlatformUiFactory1_1
	implements JpaPlatformUiFactory
{
	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLinkJpaPlatformUiFactory1_1() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLinkJpaPlatformUi1_0(
					EclipseLinkJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					EclipseLinkJpaPlatformUiProvider1_1.instance()
				);
	}
}
