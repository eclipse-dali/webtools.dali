/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_2.platform;

import org.eclipse.jpt.jpa.eclipselink.ui.internal.platform.EclipseLinkNavigatorProvider;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.platform.EclipseLink2_0JpaPlatformUi;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.platform.EclipseLink2_0JpaPlatformUiFactory;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;

public class EclipseLink2_2JpaPlatformUiFactory
	extends EclipseLink2_0JpaPlatformUiFactory
{
	/**
	 * Zero arg constructor for extension point
	 */
	public EclipseLink2_2JpaPlatformUiFactory() {
		super();
	}
	@Override
	public JpaPlatformUi buildJpaPlatformUi() {
		return new EclipseLink2_0JpaPlatformUi(
			new EclipseLinkNavigatorProvider(),
			EclipseLink2_2JpaPlatformUiProvider.instance()
		);
	}
}
