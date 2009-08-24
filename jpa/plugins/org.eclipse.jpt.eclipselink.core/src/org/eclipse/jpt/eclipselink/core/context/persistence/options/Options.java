/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence.options;

import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceUnitProperties;

/**
 *  Session Options
 */
public interface Options extends PersistenceUnitProperties
{
	String getDefaultSessionName();
	String getSessionName();
	void setSessionName(String newSessionName); 
		static final String SESSION_NAME_PROPERTY = "sessionName"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_NAME = "eclipselink.session-name"; //$NON-NLS-1$
		static final String DEFAULT_SESSION_NAME = "";	// no default //$NON-NLS-1$

	String getDefaultSessionsXml();
	String getSessionsXml();
	void setSessionsXml(String newSessionsXml);
		static final String SESSIONS_XML_PROPERTY = "sessionsXml"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSIONS_XML = "eclipselink.sessions-xml"; //$NON-NLS-1$
		static final String DEFAULT_SESSIONS_XML = "";	// no default //$NON-NLS-1$
		
	Boolean getDefaultIncludeDescriptorQueries();
	Boolean getIncludeDescriptorQueries();
	void setIncludeDescriptorQueries(Boolean newIncludeDescriptorQueries);
		static final String SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY = "includeDescriptorQueriesy"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES = "eclipselink.session.include.descriptor.queries"; //$NON-NLS-1$
		static final Boolean DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES = Boolean.TRUE;
		
	String getDefaultTargetDatabase();
	String getTargetDatabase();
	void setTargetDatabase(String newTargetDatabase);
	void setTargetDatabase(TargetDatabase newTargetDatabase);
		static final String TARGET_DATABASE_PROPERTY = "targetDatabase"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_TARGET_DATABASE = "eclipselink.target-database"; //$NON-NLS-1$
		static final String DEFAULT_TARGET_DATABASE = 
			AbstractPersistenceUnitProperties.getPropertyStringValueOf(TargetDatabase.auto);

	String getDefaultTargetServer();
	String getTargetServer();
	void setTargetServer(String newTargetServer);
	void setTargetServer(TargetServer newTargetServer);
		static final String TARGET_SERVER_PROPERTY = "targetServer"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_TARGET_SERVER = "eclipselink.target-server"; //$NON-NLS-1$
		static final String DEFAULT_TARGET_SERVER = 
			AbstractPersistenceUnitProperties.getPropertyStringValueOf(TargetServer.none);

	String getDefaultEventListener();
	String getEventListener();
	void setEventListener(String newEventListener);
		static final String SESSION_EVENT_LISTENER_PROPERTY = "eventListener"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_SESSION_EVENT_LISTENER = "eclipselink.session-event-listener"; //$NON-NLS-1$
		static final String DEFAULT_SESSION_EVENT_LISTENER = null;	// no default
		String ECLIPSELINK_EVENT_LISTENER_CLASS_NAME = "org.eclipse.persistence.sessions.SessionEventListener"; //$NON-NLS-1$

	Boolean getDefaultTemporalMutable();
	Boolean getTemporalMutable();
	void setTemporalMutable(Boolean temporalMutable);
		static final String TEMPORAL_MUTABLE_PROPERTY = "temporalMutable"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_TEMPORAL_MUTABLE = "eclipselink.temporal.mutable"; //$NON-NLS-1$
		static final Boolean DEFAULT_TEMPORAL_MUTABLE = Boolean.FALSE;
	
}
