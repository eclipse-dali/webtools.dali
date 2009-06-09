/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.platform.generic;

import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JpaPlatformUiFactory;
import org.eclipse.jpt.ui.internal.Generic2_0JpaPlatformUiProvider;
import org.eclipse.jpt.ui.internal.GenericJpaPlatformUiProvider;
import org.eclipse.jpt.ui.internal.Generic2_0JpaUiFactory;
import org.eclipse.jpt.ui.internal.structure.JavaResourceModelStructureProvider;
import org.eclipse.jpt.ui.internal.structure.PersistenceResourceModelStructureProvider;

public class Generic2_0JpaPlatformUiFactory implements JpaPlatformUiFactory
{

	/**
	 * Zero arg constructor for extension point
	 */
	public Generic2_0JpaPlatformUiFactory() {
		super();
	}

	public JpaPlatformUi buildJpaPlatformUi() {
		return new GenericJpaPlatformUi(
			new Generic2_0JpaUiFactory(),
			new GenericNavigatorProvider(),
			JavaResourceModelStructureProvider.instance(), 
			PersistenceResourceModelStructureProvider.instance(),
			GenericJpaPlatformUiProvider.instance(),
			Generic2_0JpaPlatformUiProvider.instance()
		);
	}
}
