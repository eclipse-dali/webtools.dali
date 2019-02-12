/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.platform.generic;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.GenericJpaPlatformUiProvider2_0;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUiFactory;

public class GenericJpaPlatformUiFactory2_0
	implements JpaPlatformUiFactory
{
	/**
	 * Zero arg constructor for extension point
	 */
	public GenericJpaPlatformUiFactory2_0() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new GenericJpaPlatformUi(
					GenericJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					GenericJpaPlatformUiProvider2_0.instance()
				);
	}
}
