/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.tests.extension.resource;

import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.jpa.ui.internal.GenericJpaPlatformUiProvider;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUi;
import org.eclipse.jpt.jpa.ui.internal.platform.generic.GenericJpaPlatformUiFactory;

public class TestJpaPlatformUiFactory
	implements JpaPlatformUiFactory
{
	/**
	 * Zero arg constructor for extension point
	 */
	public TestJpaPlatformUiFactory() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new GenericJpaPlatformUi(
					GenericJpaPlatformUiFactory.NAVIGATOR_FACTORY_PROVIDER,
					GenericJpaPlatformUiProvider.instance()
				);
	}
}
