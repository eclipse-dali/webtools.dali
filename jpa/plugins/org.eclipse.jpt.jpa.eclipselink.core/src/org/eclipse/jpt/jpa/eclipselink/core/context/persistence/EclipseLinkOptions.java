/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 * EclipseLink options
 */
public interface EclipseLinkOptions
	extends PersistenceUnitProperties
{
	String getDefaultSessionName();
	String getSessionName();
	void setSessionName(String newSessionName); 
		String SESSION_NAME_PROPERTY = "sessionName"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSION_NAME = "eclipselink.session-name"; //$NON-NLS-1$
		String DEFAULT_SESSION_NAME = "";	// no default //$NON-NLS-1$

	String getDefaultSessionsXml();
	String getSessionsXml();
	void setSessionsXml(String newSessionsXml);
		String SESSIONS_XML_PROPERTY = "sessionsXml"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSIONS_XML = "eclipselink.sessions-xml"; //$NON-NLS-1$
		String DEFAULT_SESSIONS_XML = "";	// no default //$NON-NLS-1$
		
	Boolean getDefaultIncludeDescriptorQueries();
	Boolean getIncludeDescriptorQueries();
	void setIncludeDescriptorQueries(Boolean newIncludeDescriptorQueries);
		String INCLUDE_DESCRIPTOR_QUERIES_PROPERTY = "includeDescriptorQueries"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES = "eclipselink.session.include.descriptor.queries"; //$NON-NLS-1$
		Boolean DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES = Boolean.TRUE;
		Transformer<EclipseLinkOptions, Boolean> INCLUDE_DESCRIPTOR_QUERIES_TRANSFORMER = new IncludeDescriptorQueriesTransformer();
		class IncludeDescriptorQueriesTransformer
			extends TransformerAdapter<EclipseLinkOptions, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkOptions options) {
				return options.getIncludeDescriptorQueries();
			}
		}
	
		BiClosure<EclipseLinkOptions, Boolean> SET_INCLUDE_DESCRIPTOR_QUERIES_CLOSURE = new SetIncludeDescriptorQueriesClosure();
		class SetIncludeDescriptorQueriesClosure
			extends BiClosureAdapter<EclipseLinkOptions, Boolean>
		{
			@Override
			public void execute(EclipseLinkOptions logging, Boolean value) {
				logging.setIncludeDescriptorQueries(value);
			}
		}
		
	String getDefaultTargetDatabase();
	String getTargetDatabase();
	void setTargetDatabase(String newTargetDatabase);
	void setTargetDatabase(EclipseLinkTargetDatabase newTargetDatabase);
		String TARGET_DATABASE_PROPERTY = "targetDatabase"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_TARGET_DATABASE = "eclipselink.target-database"; //$NON-NLS-1$
		String DEFAULT_TARGET_DATABASE = EclipseLinkTargetDatabase.auto.getPropertyValue();

	String getDefaultTargetServer();
	String getTargetServer();
	void setTargetServer(String newTargetServer);
	void setTargetServer(EclipseLinkTargetServer newTargetServer);
		String TARGET_SERVER_PROPERTY = "targetServer"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_TARGET_SERVER = "eclipselink.target-server"; //$NON-NLS-1$
		String DEFAULT_TARGET_SERVER = EclipseLinkTargetServer.none.getPropertyValue();

	String getDefaultEventListener();
	String getEventListener();
	void setEventListener(String newEventListener);
		String SESSION_EVENT_LISTENER_PROPERTY = "eventListener"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_SESSION_EVENT_LISTENER = "eclipselink.session-event-listener"; //$NON-NLS-1$
		String DEFAULT_SESSION_EVENT_LISTENER = null;	// no default
		String ECLIPSELINK_EVENT_LISTENER_CLASS_NAME = "org.eclipse.persistence.sessions.SessionEventListener"; //$NON-NLS-1$

	Boolean getDefaultTemporalMutable();
	Boolean getTemporalMutable();
	void setTemporalMutable(Boolean temporalMutable);
		String TEMPORAL_MUTABLE_PROPERTY = "temporalMutable"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_TEMPORAL_MUTABLE = "eclipselink.temporal.mutable"; //$NON-NLS-1$
		Boolean DEFAULT_TEMPORAL_MUTABLE = Boolean.FALSE;
	
}
