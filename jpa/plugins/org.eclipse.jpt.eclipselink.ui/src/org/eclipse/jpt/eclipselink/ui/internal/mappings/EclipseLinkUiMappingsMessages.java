/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings;

import org.eclipse.osgi.util.NLS;

/**
 * The localized strings used by the mapping panes.
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class EclipseLinkUiMappingsMessages extends NLS {

	public static String CacheTypeComposite_label;
	public static String CacheTypeComposite_full;
	public static String CacheTypeComposite_weak;
	public static String CacheTypeComposite_soft;
	public static String CacheTypeComposite_soft_weak;
	public static String CacheTypeComposite_hard_weak;
	public static String CacheTypeComposite_cache;
	public static String CacheTypeComposite_none;
	public static String EclipseLinkJavaEntityComposite_caching;

	public static String CachingComposite_sharedLabelDefault;
	public static String CachingComposite_sharedLabel;
	public static String AlwaysRefreshComposite_alwaysRefreshDefault;
	public static String AlwaysRefreshComposite_alwaysRefreshLabel;
	public static String RefreshOnlyIfNewerComposite_refreshOnlyIfNewerDefault;
	public static String RefreshOnlyIfNewerComposite_refreshOnlyIfNewerLabel;
	public static String DisableHitsComposite_disableHitsDefault;
	public static String DisableHitsComposite_disableHitsLabel;
	
	static {
		NLS.initializeMessages("eclipselink_ui_mappings", EclipseLinkUiMappingsMessages.class);
	}

	private EclipseLinkUiMappingsMessages() {
		throw new UnsupportedOperationException();
	}
}