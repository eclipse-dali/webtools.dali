/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.options;

import java.util.Map;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.Options;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.TargetDatabase;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.options.TargetServer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnitProperties;
import org.eclipse.text.edits.ReplaceEdit;

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
	public EclipseLinkOptions(PersistenceUnit parent) {
		super(parent);
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
		return (standardTargetDatabase == null) ? value : getPropertyStringValueOf(standardTargetDatabase);
	}
	
	protected String getTargetServerFromPersistenceXml() {

		String value = this.getStringValue(ECLIPSELINK_TARGET_SERVER);
		if (value == null) {
			return null;
		}
		TargetServer standardTargetServer = this.getEnumValue(ECLIPSELINK_TARGET_SERVER, TargetServer.values());
		return (standardTargetServer == null) ? value : getPropertyStringValueOf(standardTargetServer);
	}

	// ********** behavior **********
	
	public void propertyValueChanged(String propertyName, String newValue) {
		if (propertyName.equals(ECLIPSELINK_SESSION_NAME)) {
			this.sessionNameChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSIONS_XML)) {
			this.sessionsXmlChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_TARGET_DATABASE)) {
			this.targetDatabaseChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_TARGET_SERVER)) {
			this.targetServerChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES)) {
			this.includeDescriptorQueriesChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_EVENT_LISTENER)) {
			this.eventListenerChanged(newValue);
		}
		else if (propertyName.equals(ECLIPSELINK_TEMPORAL_MUTABLE)) {
			this.temporalMutableChanged(newValue);
		}	
	}
	
	public void propertyRemoved(String propertyName) {
		if (propertyName.equals(ECLIPSELINK_SESSION_NAME)) {
			this.sessionNameChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSIONS_XML)) {
			this.sessionsXmlChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_TARGET_DATABASE)) {
			this.targetDatabaseChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_TARGET_SERVER)) {
			this.targetServerChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_INCLUDE_DESCRIPTOR_QUERIES)) {
			this.includeDescriptorQueriesChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_SESSION_EVENT_LISTENER)) {
			this.eventListenerChanged(null);
		}
		else if (propertyName.equals(ECLIPSELINK_TEMPORAL_MUTABLE)) {
			this.temporalMutableChanged(null);
		}	
	}

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

	private void sessionNameChanged(String newValue) {
		String old = this.sessionName;
		this.sessionName = newValue;
		this.firePropertyChanged(SESSION_NAME_PROPERTY, old, newValue);
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

	private void sessionsXmlChanged(String newValue) {
		String old = this.sessionsXml;
		this.sessionsXml = newValue;
		this.firePropertyChanged(SESSIONS_XML_PROPERTY, old, newValue);
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

	private void includeDescriptorQueriesChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.includeDescriptorQueries;
		this.includeDescriptorQueries = newValue;
		this.firePropertyChanged(SESSION_INCLUDE_DESCRIPTOR_QUERIES_PROPERTY, old, newValue);
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
		this.setTargetDatabase_(getPropertyStringValueOf(newTargetDatabase));
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

	private void targetDatabaseChanged(String newValue) {
		String old = this.targetDatabase;
		this.targetDatabase = newValue;
		this.firePropertyChanged(TARGET_DATABASE_PROPERTY, old, newValue);
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
		this.setTargetServer_(getPropertyStringValueOf(newTargetServer));
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

	private void targetServerChanged(String newValue) {
		String old = this.targetServer;
		this.targetServer = newValue;
		this.firePropertyChanged(TARGET_SERVER_PROPERTY, old, newValue);
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

	private void eventListenerChanged(String newValue) {
		String old = this.eventListener;
		this.eventListener = newValue;
		this.firePropertyChanged(SESSION_EVENT_LISTENER_PROPERTY, old, newValue);
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

	private void temporalMutableChanged(String stringValue) {
		Boolean newValue = getBooleanValueOf(stringValue);
		
		Boolean old = this.temporalMutable;
		this.temporalMutable = newValue;
		this.firePropertyChanged(TEMPORAL_MUTABLE_PROPERTY, old, newValue);
	}
	
	public Boolean getDefaultTemporalMutable() {
		return DEFAULT_TEMPORAL_MUTABLE;
	}


	// ********** refactoring ************

	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return this.createEventListenerRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createEventListenerRenameTypeEdits(IType originalType, String newName) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_SESSION_EVENT_LISTENER);
		if (property != null) {
			return property.createRenameTypeEdits(originalType, newName);
		}
		return EmptyIterable.instance();
	}

	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.createEventListenerMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createEventListenerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_SESSION_EVENT_LISTENER);
		if (property != null) {
			return property.createMoveTypeEdits(originalType, newPackage);
		}
		return EmptyIterable.instance();
	}

	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.createEventListenerRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createEventListenerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		PersistenceUnit.Property property = getPersistenceUnit().getProperty(ECLIPSELINK_SESSION_EVENT_LISTENER);
		if (property != null) {
			return property.createRenamePackageEdits(originalPackage, newName);
		}
		return EmptyIterable.instance();
	}
}