/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Properties;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.IManagedConnection;
import org.eclipse.datatools.connectivity.db.generic.IDBDriverDefinitionConstants;
import org.eclipse.datatools.sqltools.core.DatabaseIdentifier;
import org.eclipse.datatools.sqltools.core.profile.ProfileUtil;

/**
 *  Wrap a DTP ConnectionProfile
 */
public final class DTPConnectionProfileWrapper extends ConnectionProfile {
	
	final private org.eclipse.datatools.connectivity.IConnectionProfile dtpConnectionProfile;
	
    public static final String CONNECTION_TYPE = "java.sql.Connection";  //$NON-NLS-1$
    public static final String CONNECTION_PROFILE_TYPE = "org.eclipse.datatools.connectivity.db.generic.connectionProfile";  //$NON-NLS-1$
    public static final String DATABASE_PRODUCT_PROPERTY = "org.eclipse.datatools.connectivity.server.version";  //$NON-NLS-1$
	/**
	 * This property is used in ConnectionProfile creation.
	 */
	public static final String DATABASE_SAVE_PWD_PROP_ID = IDBDriverDefinitionConstants.PROP_PREFIX + "savePWD"; //$NON-NLS-1$
	/**
	 * This property is used in ConnectionProfile creation.
	 */
	public static final String DRIVER_DEFINITION_PROP_ID = "org.eclipse.datatools.connectivity.driverDefinitionID"; //$NON-NLS-1$
	/**
	 * This property is used in DriverDefinition creation.
	 */
	public static final String DRIVER_DEFINITION_TYPE_PROP_ID = "org.eclipse.datatools.connectivity.drivers.defnType"; //$NON-NLS-1$
	/**
	 * This property is used in DriverDefinition creation.
	 */
	public static final String DRIVER_JAR_LIST_PROP_ID = "jarList"; //$NON-NLS-1$

	// ********** constructors **********

	DTPConnectionProfileWrapper( ConnectionProfileRepository profileRepository, org.eclipse.datatools.connectivity.IConnectionProfile dtpConnectionProfile) {
		super( profileRepository);
		this.dtpConnectionProfile = dtpConnectionProfile;
	}
	
	// ********** listeners **********

	public void addProfileListener( ProfileListener listener) {
		
		this.getProfileRepository().addProfileListener( listener);
	}

	public void removeProfileListener( ProfileListener listener) {
		
		this.getProfileRepository().removeProfileListener( listener);
	}
	
	public void addConnectionListener( ConnectionListener listener) {
		
		this.getConnection().addConnectionListener( listener);
	}

	public void removeConnectionListener( ConnectionListener listener) {

		this.getConnection().removeConnectionListener( listener);
	}
	
	// ********** behavior **********
	
	private IManagedConnection buildDtpManagedConnection( org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile) {
		return dtpProfile.getManagedConnection( CONNECTION_TYPE);
	}
	/**
	 * Connect using this profile.
	 */
	public void connect() {
		if( !this.dtpConnectionProfile.isConnected()) {
			
			IStatus status = this.dtpConnectionProfile.connect();
			if( !status.isOK()) {
				if( status.isMultiStatus()) {
					IStatus[] statusChildren = status.getChildren();
					throw new RuntimeException( statusChildren[ 0].getMessage(), statusChildren[ 0].getException());
				}
				else {
					throw new RuntimeException( status.getMessage(), status.getException());
				}
			}
		}
	}
	
	public void disconnect() {
		
		IStatus status = this.dtpConnectionProfile.disconnect();
		if( !status.isOK()) {
			if( status.isMultiStatus()) {
				IStatus[] statusChildren = status.getChildren();
				throw new RuntimeException( statusChildren[ 0].getMessage(), statusChildren[ 0].getException());
			}
			else {
				throw new RuntimeException( status.getMessage(), status.getException());
			}
		}
	}
	
	void databaseChanged( Database database, int eventType) {
		this.getConnection().databaseChanged( database, eventType);
		return;
	}
	
	 void catalogChanged( Catalog catalog, Database database, int eventType) {
		 //TODO
//		this.getConnection().catalogChanged( catalog, eventType);
		 return;
	}
	
	void schemaChanged( Schema schema, Database database, int eventType) {
		this.getConnection().schemaChanged( schema, database, eventType);
	}
		
	void tableChanged( Table table, Schema schema, Database database, int eventType) {
		this.getConnection().tableChanged( table, schema, database, eventType);
	}
		
	// ********** queries **********

	public boolean isConnected() {

		return this.getConnection().isConnected();
	}
	
	public boolean isNull() {
		return false;
	}
	
	public String getName() {

		return this.dtpConnectionProfile.getName();
	}
	
	public String getDatabaseName() {
		return this.getProperties().getProperty( IDBDriverDefinitionConstants.DATABASE_NAME_PROP_ID);
	}
	
	public String getDatabaseProduct() {
		return this.getProperties().getProperty( DATABASE_PRODUCT_PROPERTY);
	}
	
	public String getDatabaseVendor() {
		return this.getProperties().getProperty( IDBDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID);
	}
	
	public String getDatabaseVersion() {
		return this.getProperties().getProperty( IDBDriverDefinitionConstants.DATABASE_VERSION_PROP_ID);
	}

	public String getUserName() {
		return this.getProperties().getProperty( IDBDriverDefinitionConstants.USERNAME_PROP_ID);
	}
	
	public String getInstanceId() {
		return this.dtpConnectionProfile.getInstanceID();
	}

	public String getProviderId() {
		return this.dtpConnectionProfile.getProviderId();
	}
	
	private Properties getProperties() {
		return this.dtpConnectionProfile.getBaseProperties();
	}
	
	protected Connection buildConnection() {

		Connection connection = Connection.createConnection( this.buildDtpManagedConnection( this.dtpConnectionProfile));  //$NON-NLS-1$
		return connection;
	}

	protected Database buildDatabase() {
		
		org.eclipse.datatools.modelbase.sql.schema.Database dtpDatabase;
		if( this.isConnected()) {
			dtpDatabase = ProfileUtil.getDatabase( new DatabaseIdentifier( this.getName(), this.getDatabaseName()), false);
			return Database.createDatabase( this, dtpDatabase);
		}
		else {
			return NullDatabase.instance();
		}
	}
	
	boolean wraps( org.eclipse.datatools.connectivity.IConnectionProfile dtpProfile) {
		return this.dtpConnectionProfile == dtpProfile;
	}

}
