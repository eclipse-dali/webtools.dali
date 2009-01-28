/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence.options;

import java.util.Map;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.value.ListValueModel;

/**
 *  EclipseLinkOptions
 */
public class EclipseLinkOptions extends EclipseLinkPersistenceUnitProperties
	implements Options
{
	// ********** EclipseLink properties **********
	private String sessionName;
	private String sessionsXml;
	private Boolean includeDescriptorQueries;
	private String targetDatabase; // storing EclipseLinkStringValue since value can be TargetDatabase or custom class
	private String targetServer; // storing EclipseLinkStringValue since value can be TargetServer or custom class
	private String eventListener;
	private Boolean temporalMutable;


	// ********** constructors **********
	public EclipseLinkOptions(PersistenceUnit parent, ListValueModel<PersistenceUnit.Property> propertyListAdapter) {
		super(parent, propertyListAdapter);
	}

	// ********** initialization **********
	/**
	 * Initializes properties with values from the persistence unit.
	 */
	@Override
	protected void initializeProperties() {
		// TOREVIEW - handle incorrect String in persistence.xml
		this.sessionName = 
			this.getStringValue(ECLIPSELINK_SESSION_NAME);
		this.sessionsXml = 
			this.getStringValue(ECLIPSELINK_SESSIONS_XML);
		this.includeDescriptorQueries = 
			this.getBooleanValue(ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES);
		this.targetDatabase = this.getTargetDatabaseFromPersistenceXml();
		this.targetServer = this.getTargetServerFromPersistenceXml();
		this.eventListener = 
			this.getStringValue(ECLIPSELINK_SESSION_EVENT_LISTENER);
		this.temporalMutable = 
			this.getBooleanValue(ECLIPSELINK_TEMPORAL_MUTABLE);
	}
	
	protected String getTargetDatabaseFromPersistenceXml() {

		String value = this.getStringValue(ECLIPSELINK_TARGET_DATABASE);
		if (value == null) {
			return null;
		}
		TargetDatabase standardTargetDatabase = this.getEnumValue(ECLIPSELINK_TARGET_DATABASE, TargetDatabase.values());
		return (standardTargetDatabase == null) ? value : getEclipseLinkStringValueOf(standardTargetDatabase);
	}
	
	protected String getTargetServerFromPersistenceXml() {

		String value = this.getStringValue(ECLIPSELINK_TARGET_SERVER);
		if (value == null) {
			return null;
		}
		TargetServer standardTargetServer = this.getEnumValue(ECLIPSELINK_TARGET_SERVER, TargetServer.values());
		return (standardTargetServer == null) ? value : getEclipseLinkStringValueOf(standardTargetServer);
	}

	// ********** behavior **********
	/**
	 * Adds property names key/value pairs, where: 
	 * 		key = EclipseLink property key
	 * 		value = property id
	 */
	@Override
	protected void addPropertyNames(Map<String, String> propertyNames) {
		propertyNames.put(
			ECLIPSELINK_SESSION_NAME,
			SESSION_NAME_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SESSIONS_XML,
			SESSIONS_XML_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES,
			SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_TARGET_DATABASE,
			TARGET_DATABASE_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_TARGET_SERVER,
			TARGET_SERVER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_SESSION_EVENT_LISTENER,
			SESSION_EVENT_LISTENER_PROPERTY);
		propertyNames.put(
			ECLIPSELINK_TEMPORAL_MUTABLE,
			TEMPORAL_MUTABLE_PROPERTY);
	}

	public void propertyChanged(PropertyChangeEvent event) {
		String aspectName = event.getAspectName();
		if (aspectName.equals(SESSION_NAME_PROPERTY)) {
			this.sessionNameChanged(event);
		}
		else if (aspectName.equals(SESSIONS_XML_PROPERTY)) {
			this.sessionsXmlChanged(event);
		}
		else if (aspectName.equals(TARGET_DATABASE_PROPERTY)) {
			this.targetDatabaseChanged(event);
		}
		else if (aspectName.equals(TARGET_SERVER_PROPERTY)) {
			this.targetServerChanged(event);
		}
		else if (aspectName.equals(SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY)) {
			this.includeDescriptorQueriesChanged(event);
		}
		else if (aspectName.equals(SESSION_EVENT_LISTENER_PROPERTY)) {
			this.eventListenerChanged(event);
		}
		else if (aspectName.equals(TEMPORAL_MUTABLE_PROPERTY)) {
			this.temporalMutableChanged(event);
		}
		else {
			throw new IllegalArgumentException("Illegal event received - property not applicable: " + aspectName); //$NON-NLS-1$
		}
		return;
	}

	// ********** SessionName **********
	public String getSessionName() {
		return this.sessionName;
	}

	public void setSessionName(String newSessionName) {
		String old = this.sessionName;
		this.sessionName = newSessionName;
		this.putProperty(SESSION_NAME_PROPERTY, newSessionName);
		this.firePropertyChanged(SESSION_NAME_PROPERTY, old, newSessionName);
	}

	private void sessionNameChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.sessionName;
		this.sessionName = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultSessionName() {
		return DEFAULT_SESSION_NAME;
	}

		// ********** SessionsXml **********
		public String getSessionsXml() {
			return this.sessionsXml;
		}

		public void setSessionsXml(String newSessionsXml) {
			String old = this.sessionsXml;
			this.sessionsXml = newSessionsXml;
			this.putProperty(SESSIONS_XML_PROPERTY, newSessionsXml);
			this.firePropertyChanged(SESSIONS_XML_PROPERTY, old, newSessionsXml);
		}

		private void sessionsXmlChanged(PropertyChangeEvent event) {
			String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
			String old = this.sessionsXml;
			this.sessionsXml = newValue;
			this.firePropertyChanged(event.getAspectName(), old, newValue);
		}

		public String getDefaultSessionsXml() {
			return DEFAULT_SESSIONS_XML;
		}

	// ********** IncludeDescriptorQueries **********
	public Boolean getIncludeDescriptorQueries() {
		return this.includeDescriptorQueries;
	}

	public void setIncludeDescriptorQueries(Boolean newIncludeDescriptorQueries) {
		Boolean old = this.includeDescriptorQueries;
		this.includeDescriptorQueries = newIncludeDescriptorQueries;
		this.putProperty(SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY, newIncludeDescriptorQueries);
		this.firePropertyChanged(SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY, old, newIncludeDescriptorQueries);
	}

	private void includeDescriptorQueriesChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.includeDescriptorQueries;
		this.includeDescriptorQueries = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public Boolean getDefaultIncludeDescriptorQueries() {
		return DEFAULT_SESSION_INCLUDE_DESCRIPTOR_QUERIES;
	}

	// ********** TargetDatabase **********
	/**
	 * Returns TargetDatabase or custom targetDatabase class.
	 * 
	 * @return EclipseLink string value for TargetDatabase enum or custom targetDatabase class
	 */
	public String getTargetDatabase() {
		return this.targetDatabase;
	}

	/**
	 * Sets EclipseLink targetDatabase.
	 * 
	 * @param newTargetDatabase - TargetDatabase
	 */
	public void setTargetDatabase(TargetDatabase newTargetDatabase) {
		if( newTargetDatabase == null) {
			this.setTargetDatabase_((String) null);
			return;
		}
		this.setTargetDatabase_(getEclipseLinkStringValueOf(newTargetDatabase));
	}

	/**
	 * Sets EclipseLink targetDatabase or custom targetDatabase.
	 * 
	 * @param newTargetDatabase -
	 *            Can be a EclipseLink TargetDatabase literal or
	 *            a fully qualified class name of a custom targetDatabase.
	 */
	public void setTargetDatabase(String newTargetDatabase) {
		if( newTargetDatabase == null) {
			this.setTargetDatabase_((String) null);
			return;
		}
		TargetDatabase customTargetDatabase = TargetDatabase.getTargetDatabaseFor(newTargetDatabase);
		if(customTargetDatabase == null) {	// custom TargetDatabase class
			this.setTargetDatabase_(newTargetDatabase);
		}
		else {
			this.setTargetDatabase(customTargetDatabase);
		}
	}
	
	private void setTargetDatabase_(String newTargetDatabase) {
		String old = this.targetDatabase;
		this.targetDatabase = newTargetDatabase;
		this.putProperty(TARGET_DATABASE_PROPERTY, newTargetDatabase);
		this.firePropertyChanged(TARGET_DATABASE_PROPERTY, old, newTargetDatabase);
	}

	private void targetDatabaseChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.targetDatabase;
		this.targetDatabase = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public String getDefaultTargetDatabase() {
		return DEFAULT_TARGET_DATABASE;
	}

	// ********** TargetServer **********
	/**
	 * Returns TargetServer or custom targetServer class.
	 * 
	 * @return EclipseLink string value for TargetServer enum or custom targetServer class
	 */
	public String getTargetServer() {
		return this.targetServer;
	}

	/**
	 * Sets EclipseLink targetServer.
	 * 
	 * @param newTargetServer - TargetServer
	 */
	public void setTargetServer(TargetServer newTargetServer) {
		if( newTargetServer == null) {
			this.setTargetServer_((String) null);
			return;
		}
		this.setTargetServer_(getEclipseLinkStringValueOf(newTargetServer));
	}

	/**
	 * Sets EclipseLink targetServer or custom targetServer.
	 * 
	 * @param newTargetServer -
	 *            Can be a EclipseLink TargetServer literal or
	 *            a fully qualified class name of a custom targetServer.
	 */
	public void setTargetServer(String newTargetServer) {
		if( newTargetServer == null) {
			this.setTargetServer_((String) null);
			return;
		}
		TargetServer customTargetServer = TargetServer.getTargetServerFor(newTargetServer);
		if(customTargetServer == null) {	// custom TargetServer class
			this.setTargetServer_(newTargetServer);
		}
		else {
			this.setTargetServer(customTargetServer);
		}
	}
	
	private void setTargetServer_(String newTargetServer) {
		String old = this.targetServer;
		this.targetServer = newTargetServer;
		this.putProperty(TARGET_SERVER_PROPERTY, newTargetServer);
		this.firePropertyChanged(TARGET_SERVER_PROPERTY, old, newTargetServer);
	}

	private void targetServerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.targetServer;
		this.targetServer = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public String getDefaultTargetServer() {
		return DEFAULT_TARGET_SERVER;
	}

	// ********** EventListener **********
	public String getEventListener() {
		return this.eventListener;
	}

	public void setEventListener(String newEventListener) {
		String old = this.eventListener;
		this.eventListener = newEventListener;
		this.putProperty(SESSION_EVENT_LISTENER_PROPERTY, newEventListener);
		this.firePropertyChanged(SESSION_EVENT_LISTENER_PROPERTY, old, newEventListener);
	}

	private void eventListenerChanged(PropertyChangeEvent event) {
		String newValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		String old = this.eventListener;
		this.eventListener = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}

	public String getDefaultEventListener() {
		return DEFAULT_SESSION_EVENT_LISTENER;
	}
	
	// ********** TemporalMutable **********

	public Boolean getTemporalMutable() {
		return this.temporalMutable;
	}

	public void setTemporalMutable(Boolean newTemporalMutable) {
		Boolean old = this.temporalMutable;
		this.temporalMutable = newTemporalMutable;
		this.putProperty(TEMPORAL_MUTABLE_PROPERTY, newTemporalMutable);
		this.firePropertyChanged(TEMPORAL_MUTABLE_PROPERTY, old, newTemporalMutable);
	}

	private void temporalMutableChanged(PropertyChangeEvent event) {
		String stringValue = (event.getNewValue() == null) ? null : ((PersistenceUnit.Property) event.getNewValue()).getValue();
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.temporalMutable;
		this.temporalMutable = newValue;
		this.firePropertyChanged(event.getAspectName(), old, newValue);
	}
	
	public Boolean getDefaultTemporalMutable() {
		return DEFAULT_TEMPORAL_MUTABLE;
	}

}