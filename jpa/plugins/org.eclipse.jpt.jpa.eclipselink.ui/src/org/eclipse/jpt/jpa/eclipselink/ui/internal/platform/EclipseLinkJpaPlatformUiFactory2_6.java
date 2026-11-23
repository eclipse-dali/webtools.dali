/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.platform;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;

/**
 * EclipseLink 2.6 platform UI.
 * <p>
 * EclipseLink 2.6 presents the same mapping file and persistence unit
 * resources as 2.5, so its UI provider currently matches 2.5's.
 */
public class EclipseLinkJpaPlatformUiFactory2_6
	extends EclipseLinkJpaPlatformUiFactory2_0
{
	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLinkJpaPlatformUiFactory2_6() {
		super();
	}

	@Override
	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLinkJpaPlatformUi2_0(
					EclipseLinkJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					EclipseLinkJpaPlatformUiProvider2_6.instance()
				);
	}
}
