/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

import org.eclipse.jpt.eclipselink.ui.JptEclipseLinkUiPlugin;

/**
 * Help context ids for the Dali EclipseLink UI.
 * <p>
 * This interface contains constants only; it is not intended to be
 * implemented.
 * </p>
 */
@SuppressWarnings("nls")
public interface EclipseLinkHelpContextIds {

	//ContextID prefix
	public static final String PREFIX = JptEclipseLinkUiPlugin.PLUGIN_ID + ".";

	//Persistent Type composites
	public static final String CACHING_CACHE_TYPE = PREFIX + "caching_cacheType";
	public static final String CACHING_SHARED = PREFIX + "caching_shared";
	public static final String CACHING_ALWAYS_REFRESH = PREFIX + "caching_alwaysRefresh";
	public static final String CACHING_REFRESH_ONLY_IF_NEWER = PREFIX + "caching_refreshOnlyIfNewer";
	public static final String CACHING_DISABLE_HITS = PREFIX + "caching_disableHits";
	
		
}
