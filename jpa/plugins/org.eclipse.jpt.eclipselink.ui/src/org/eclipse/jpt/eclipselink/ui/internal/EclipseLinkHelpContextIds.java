/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal;

import org.eclipse.jpt.ui.JptUiPlugin;

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
	public static final String PREFIX = JptUiPlugin.PLUGIN_ID + ".";

	//Persistent Type composites
	public static final String CACHING_ALWAYS_REFRESH = PREFIX + "caching_alwaysRefresh";
	public static final String CACHING_CACHE_COORDINATION_TYPE = PREFIX + "caching_cacheCoordinationType";
	public static final String CACHING_CACHE_TYPE = PREFIX + "caching_cacheType";
	public static final String CACHING_DISABLE_HITS = PREFIX + "caching_disableHits";
	public static final String CACHING_REFRESH_ONLY_IF_NEWER = PREFIX + "caching_refreshOnlyIfNewer";
	public static final String CACHING_SHARED = PREFIX + "caching_shared";

	//Persistence Xml Editor
	public static final String PERSISTENCE_CACHING = PREFIX + "persistence_caching";
	public static final String PERSISTENCE_CUSTOMIZATION = PREFIX + "persistence_customization";
	public static final String PERSISTENCE_LOGGING = PREFIX + "persistence_logging";
	public static final String PERSISTENCE_OPTIONS = PREFIX + "persistence_options";
	public static final String PERSISTENCE_SCHEMA_GENERATION = PREFIX + "persistence_schemaGeneration";

	public static final String PERSISTENCE_CACHING_DEFAULT_SHARED = PREFIX + "caching_defaultShared";
	public static final String PERSISTENCE_CACHING_DEFAULT_SIZE = PREFIX + "caching_defaultSize";
	public static final String PERSISTENCE_CACHING_DEFAULT_TYPE = PREFIX + "caching_defaultType";
	
	public static final String PERSISTENCE_LOGGING_EXCEPTIONS = PREFIX + "logging_exceptions";
	public static final String PERSISTENCE_LOGGING_LEVEL = PREFIX + "logging_level";
	public static final String PERSISTENCE_LOGGING_SESSION = PREFIX + "logging_session";
	public static final String PERSISTENCE_LOGGING_THREAD = PREFIX + "logging_thread";
	public static final String PERSISTENCE_LOGGING_TIMESTAMP = PREFIX + "logging_timeStamp";
	
	public static final String PERSISTENCE_OPTIONS_SESSION_NAME = PREFIX + "options_sessionName";
	public static final String PERSISTENCE_OPTIONS_SESSIONS_XML = PREFIX + "options_sessionsXml";
	public static final String PERSISTENCE_OPTIONS_TARGET_DATABASE = PREFIX + "options_targetDatabase";
	public static final String PERSISTENCE_OPTIONS_TARGET_SERVER = PREFIX + "options_targetServer";

}
