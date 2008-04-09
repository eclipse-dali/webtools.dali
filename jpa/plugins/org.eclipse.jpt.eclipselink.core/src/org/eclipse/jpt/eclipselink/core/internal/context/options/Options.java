/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.options;

import org.eclipse.jpt.eclipselink.core.internal.context.PersistenceUnitProperties;

/**
 *  Session Options
 */
public interface Options extends PersistenceUnitProperties
{
	String getDefaultSessionName();
	String getSessionName();
	void setSessionName(String newSessionName); 
		static final String SESSION_NAME_PROPERTY = "sessionNameProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_NAME = "eclipselink.session-name";
		static final String DEFAULT_SESSION_NAME = "";	// no default

	String getDefaultSessionsXml();
	String getSessionsXml();
	void setSessionsXml(String newSessionsXml);
		static final String SESSIONS_XML_PROPERTY = "sessionsXmlProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_SESSIONS_XML = "eclipselink.sessions-xml";
		static final String DEFAULT_SESSIONS_XML = "";	// no default
		
	Boolean getDefaultIncludeDescriptorQueries();
	Boolean getIncludeDescriptorQueries();
	void setIncludeDescriptorQueries(Boolean newIncludeDescriptorQueries);
		static final String SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY = "includeDescriptorQueriesProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES = "eclipselink.session.include.descriptor.queries";
		static final Boolean DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES = Boolean.TRUE;
		
	TargetDatabase getDefaultTargetDatabase();
	TargetDatabase getTargetDatabase();
	void setTargetDatabase(TargetDatabase newTargetDatabase); // put
		static final String TARGET_DATABASE_PROPERTY = "targetDatabaseProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_TARGET_DATABASE = "eclipselink.target-database";
		static final TargetDatabase DEFAULT_TARGET_DATABASE = TargetDatabase.auto;

	String getDefaultEventListener();
	String getEventListener();
	void setEventListener(String newEventListener);
		static final String SESSION_EVENT_LISTENER_PROPERTY = "eventListenerProperty";
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_EVENT_LISTENER = "eclipselink.session-event-listener";
		static final String DEFAULT_SESSION_EVENT_LISTENER = null;	// no default
		
	static final String ECLIPSELINK_TARGET_SERVER = "eclipselink.target-server";

}
