/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.navigator;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.navigator.JpaNavigatorProvider;

/**
 * This class is only provided to minimize object construction.  Only one
 * {@link JpaNavigatorProvider} is required to be constructed for each platform UI.
 */
public class JpaNavigatorProviderLookup
{
	private static Map<String, JpaNavigatorProvider> providers;
	
	
	public static JpaNavigatorProvider provider(String platformId) {
		if (providers == null) {
			providers = new HashMap<String, JpaNavigatorProvider>();
		}
		if (providers.containsKey(platformId)) {
			return providers.get(platformId);
		}
		JpaPlatformUi platform = JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
		if (platform == null) {
			return null;
		}
		JpaNavigatorProvider provider = platform.buildNavigatorProvider();
		providers.put(platformId, provider);
		return provider;
	}
	
	// prevent construction
	private JpaNavigatorProviderLookup() {	
	}
}
