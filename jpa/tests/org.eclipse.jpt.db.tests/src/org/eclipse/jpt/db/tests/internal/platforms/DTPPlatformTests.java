/*******************************************************************************
 * Copyright (c) 2006 - 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.tests.internal.platforms;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Properties;
import junit.framework.Assert;
import junit.framework.TestCase;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.ConnectionProfileException;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.db.generic.IDBDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.drivers.IDriverMgmtConstants;
import org.eclipse.datatools.connectivity.drivers.IPropertySet;
import org.eclipse.datatools.connectivity.drivers.PropertySetImpl;
import org.eclipse.datatools.connectivity.drivers.XMLFileManager;
import org.eclipse.datatools.connectivity.internal.ConnectivityPlugin;
import org.eclipse.jpt.db.internal.Connection;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.ConnectionProfileRepository;
import org.eclipse.jpt.db.internal.DTPConnectionProfileWrapper;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.db.tests.internal.JptDbTestsPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  Base class for all supported DTP platform.
 */
public abstract class DTPPlatformTests extends TestCase {

    protected ConnectionProfileRepository connectionRepository;
    
    private Properties platformProperties;
	private String currentDbVendor;
	private String currentDbVersion;

	private static final String PLATFORM_CONFIG_DIRECTORY = "config";	//$NON-NLS-1$
	
	private static final String PROFILE_NAME_PROPERTY = "profileName";	//$NON-NLS-1$
	private static final String PROFILE_DESCRIPTION_PROPERTY = "profileDescription";	//$NON-NLS-1$
	private static final String USER_NAME_PROPERTY = "userName";	//$NON-NLS-1$
	private static final String USER_PASSWORD_PROPERTY = "userPassword";	//$NON-NLS-1$
	private static final String DB_DRIVER_JAR_PROPERTY = "databasedriverJarList";	//$NON-NLS-1$
	private static final String DB_NAME_PROPERTY = "databaseName";	//$NON-NLS-1$
	private static final String DB_URL_PROPERTY = "databaseUrl";	//$NON-NLS-1$
	
	private static final String PROFILE_NAME_DEFAULT = "jpatest";	//$NON-NLS-1$
	private static final String PROFILE_DESCRIPTION_DEFAULT = "JDBC Profile for JPA Testing";	//$NON-NLS-1$
	private static final String USER_NAME_DEFAULT = "userName";	//$NON-NLS-1$
	private static final String USER_PASSWORD_DEFAULT = "";	//$NON-NLS-1$
	private static final String DB_NAME_DEFAULT = "testdb";	//$NON-NLS-1$
	
	private static final String MISSING_PROPERTY_MESSAGE = "Enter missing property in platform config file (config/database.properties)";	

    public DTPPlatformTests( String name) {
        super( name);
        
        this.connectionRepository = ConnectionProfileRepository.instance();
    }

	protected void setUp() throws Exception {
        super.setUp();
        this.connectionRepository.initializeListeners();
        
        if( this.platformIsNew()) {
        	this.loadPlatformProperties();
        	
        	this.buildDriverDefinitionFile( IDriverMgmtConstants.DRIVER_FILE);
        	this.buildConnectionProfile( this.profileName());
        	this.currentDbVendor = this.databaseVendor();
        	this.currentDbVersion = this.databaseVersion();
        }
        this.verifyProfileNamed( this.profileName());
	}

	protected void tearDown() throws Exception {
        super.tearDown();
        
        this.connectionRepository.disposeListeners();
	}

	// ********** tests **********

	public void testConnection() throws Exception {

		this.connect();
		
        this.verifyDatabaseVersionNumber();
        this.verifyConnection();
        this.verifyDatabaseContent();

        this.disconnect();
    }
    
	public void testGetProfiles() {
		// Getting the profile directly from DTP ProfileManager
		IConnectionProfile[] profiles = ProfileManager.getInstance().getProfiles();
		Assert.assertNotNull( profiles);
		Assert.assertTrue( profiles.length > 0);
	}

	public void testGetProfilesByProvider() {
		// Get Profiles By ProviderID
		IConnectionProfile[] profiles = ProfileManager.getInstance().getProfileByProviderID( this.getProfile().getProviderId());
		Assert.assertNotNull( profiles);
		Assert.assertTrue( profiles.length > 0);
	}
	
	public void testGetProfileByName() {
		// Get Profile By Name
		IConnectionProfile dtpProfile = ProfileManager.getInstance().getProfileByName( this.profileName());
		Assert.assertNotNull( dtpProfile);
		Assert.assertTrue( dtpProfile.getName().equals( this.profileName()));
	}
	
	public void testGetProfileByInstanceId() {
		// Get Profile By InstanceID
		IConnectionProfile dtpProfile = ProfileManager.getInstance().getProfileByInstanceID( this.getProfile().getInstanceId());
		Assert.assertNotNull( dtpProfile);
		Assert.assertTrue( dtpProfile.getName().equals( this.profileName()));
	}

	// ********** internal tests **********

    private void verifyDatabaseVersionNumber() {
    	Database database = this.getProfile().getDatabase();
    	Assert.assertNotNull( database);
    	
        String actualVersionNumber = database.getVersion();
        String expectedVersionNumber = this.databaseVersion();
        String errorMessage = "Expected version number: " + expectedVersionNumber + " but the actual version number was: " + actualVersionNumber;
        assertTrue( errorMessage, actualVersionNumber.indexOf( expectedVersionNumber) != -1);

        String actualVendor = database.getVendor();
        String expectedVendor = this.databaseVendor();
        errorMessage = "Expected vendor: " + expectedVendor + " but the actual vendor was: " + actualVendor;
        assertEquals( errorMessage, actualVendor, expectedVendor);
    }
    
    private void verifyConnection() {
    	Connection connection = this.getProfile().getConnection();
    	Assert.assertNotNull( connection);

        String actualFactory = connection.getFactoryId();
        String expectedFactory = DTPConnectionProfileWrapper.CONNECTION_TYPE;
    	String errorMessage = "Expected factory: " + expectedFactory + " but the actual factory was: " + actualFactory;
    	assertEquals( errorMessage, actualFactory, expectedFactory);
    }
    
    private void verifyDatabaseContent() {
    	Database database = this.getProfile().getDatabase();
    	Assert.assertTrue( database.schemataSize() > 0);
		
        Schema schema = database.schemaNamed( this.getProfile().getUserName());
		if( schema != null) {
			Assert.assertTrue( schema.sequencesSize() >= 0);
			
			Object[] tableNames = CollectionTools.array( schema.tableNames());
			if( tableNames.length >= 1) {
				Table table = schema.tableNamed(( String)tableNames[ 0]);
				Assert.assertTrue( table.columnsSize() >= 0);
				Assert.assertTrue( table.foreignKeyColumnsSize() >= 0);
				Assert.assertTrue( table.foreignKeysSize() >= 0);
				Assert.assertTrue( table.primaryKeyColumnsSize() >= 0);
			}
		}
    }

    private void verifyProfileNamed( String profileName) {
    	
    	ConnectionProfile profile = this.getProfileNamed( profileName);
    	Assert.assertTrue( "ConnectionProfile not found", profileName.equals( profile.getName()));
    }

	// ***** Platform specific behavior *****

    protected abstract String databaseVendor();
    protected abstract String databaseVersion();

    protected abstract String driverName();
    protected abstract String driverDefinitionType();
    protected abstract String driverDefinitionId();
    protected abstract String driverClass();

    protected abstract String getConfigName();

	// ***** Behavior *****

    protected void connect() {

        this.getProfile().connect();
		Assert.assertTrue( "Connect failed.", this.getProfile().isConnected());
    }
    
    protected void disconnect() {
    	
    	this.getProfile().disconnect();
        Assert.assertFalse( "Disconnect failed.", this.getProfile().isConnected());
    }
    
	// ********** queries **********

    protected Schema getSchemaNamed( String schemaName) { 

	    return this.getProfile().getDatabase().schemaNamed( schemaName);
    }
	
    protected Collection getTables() {
		
        Schema schema = this.getSchemaNamed( this.getProfile().getUserName());
		if( schema == null) {
			return new ArrayList();
		}
		return CollectionTools.collection( schema.tables());
    }

    protected Table getTableNamed( String tableName) { 

	    Schema schema =  this.getSchemaNamed( this.getProfile().getUserName());
	    Assert.assertNotNull( schema);
	
		return schema.tableNamed( tableName);
    }

    protected String providerId() {
        return DTPConnectionProfileWrapper.CONNECTION_PROFILE_TYPE;
    }
     
    protected String passwordIsSaved() {
        return "true";
    }
	
    protected String profileName() {
    	return this.platformProperties.getProperty( PROFILE_NAME_PROPERTY, PROFILE_NAME_DEFAULT);
    }    
    
    protected String profileDescription() {
    	return this.platformProperties.getProperty( PROFILE_DESCRIPTION_PROPERTY, PROFILE_DESCRIPTION_DEFAULT);
    }    
    
    protected String userName() {
    	return this.platformProperties.getProperty( USER_NAME_PROPERTY, USER_NAME_DEFAULT);
    }
    
    protected String userPassword() {
    	return this.platformProperties.getProperty( USER_PASSWORD_PROPERTY, USER_PASSWORD_DEFAULT);
    }    
    
    protected String databaseName() {
    	return this.platformProperties.getProperty( DB_NAME_PROPERTY, DB_NAME_DEFAULT);
    }
    
    protected String databasedriverJarList() {
    	String dbDriverJarList = this.platformProperties.getProperty( DB_DRIVER_JAR_PROPERTY);
    	if ( StringTools.stringIsEmpty( dbDriverJarList)) {
    		throw new NoSuchElementException( MISSING_PROPERTY_MESSAGE);
    	}
    	return dbDriverJarList;
    }
    
    protected String databaseUrl() {
    	String dbUrl = this.platformProperties.getProperty( DB_URL_PROPERTY);
    	if ( StringTools.stringIsEmpty( dbUrl)) {
    		throw new NoSuchElementException( MISSING_PROPERTY_MESSAGE);
    	}
    	return dbUrl;
    }

    private ConnectionProfile getProfile() {

    	return this.getProfileNamed( this.profileName());
    }

    protected ConnectionProfile getProfileNamed( String profileName) {
    	
    	return ConnectionProfileRepository.instance().profileNamed( profileName);
    }
   
    private String getTestPluginBundleId() {
		return JptDbTestsPlugin.BUNDLE_ID;
	}

	private IPath getDriverDefinitionLocation() {
		return ConnectivityPlugin.getDefault().getStateLocation();
	}
	
	private String getConfigPath() {
    	return this.getConfigDir() + "/" + this.getConfigName();
    }
	
	private String getConfigDir() {
    	return PLATFORM_CONFIG_DIRECTORY;
    }

	private boolean platformIsNew() {
		return( !this.databaseVendor().equals( this.currentDbVendor) || this.databaseVersion().equals( this.currentDbVersion));
	}
	
    private void loadPlatformProperties() throws IOException {
		
		if( this.platformProperties == null) {
			URL configUrl = Platform.getBundle( this.getTestPluginBundleId()).getEntry( this.getConfigPath());

			this.platformProperties = new Properties();
			this.platformProperties.load( configUrl.openStream());
		}
	}
	
	private Properties buildDriverProperties() {
		Properties driverProperties = new Properties();
		driverProperties.setProperty( DTPConnectionProfileWrapper.DRIVER_DEFINITION_TYPE_PROP_ID, this.driverDefinitionType());
		driverProperties.setProperty( DTPConnectionProfileWrapper.DRIVER_JAR_LIST_PROP_ID, this.databasedriverJarList());
		driverProperties.setProperty( IDBDriverDefinitionConstants.USERNAME_PROP_ID, this.userName());
		driverProperties.setProperty( IDBDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.driverClass());
		driverProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.databaseName());
		driverProperties.setProperty( IDBDriverDefinitionConstants.PASSWORD_PROP_ID, this.userPassword());
		driverProperties.setProperty( IDBDriverDefinitionConstants.URL_PROP_ID, this.databaseUrl());
		driverProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.databaseVendor());
		driverProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.databaseVersion());
		return driverProperties;
	}

	private Properties buildBasicProperties() {
		Properties basicProperties = new Properties();
		basicProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.databaseName());
		basicProperties.setProperty( IDBDriverDefinitionConstants.USERNAME_PROP_ID, this.userName());
		basicProperties.setProperty( IDBDriverDefinitionConstants.PASSWORD_PROP_ID, this.userPassword());
		basicProperties.setProperty( DTPConnectionProfileWrapper.DRIVER_DEFINITION_PROP_ID, this.driverDefinitionId());
		
		basicProperties.setProperty( IDBDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.driverClass());
		basicProperties.setProperty( IDBDriverDefinitionConstants.URL_PROP_ID, this.databaseUrl());
		basicProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.databaseVendor());
		basicProperties.setProperty( IDBDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.databaseVersion());
		
		basicProperties.setProperty( DTPConnectionProfileWrapper.DATABASE_SAVE_PWD_PROP_ID, this.passwordIsSaved());
		return basicProperties;
	}

	private void buildConnectionProfile( String profileName) throws ConnectionProfileException {
		
		ProfileManager profileManager = ProfileManager.getInstance();
		Assert.assertNotNull( profileManager);
		IConnectionProfile profile = profileManager.getProfileByName( profileName);
		if( profile == null) {
			Properties basicProperties = buildBasicProperties();
			ProfileManager.getInstance().createProfile( profileName, this.profileDescription(), this.providerId(), basicProperties);
		}
	}

	private void buildDriverDefinitionFile( String driverFileName) throws CoreException {
		
		XMLFileManager.setStorageLocation( this.getDriverDefinitionLocation());
		XMLFileManager.setFileName( driverFileName);
		IPropertySet[] propsets = new IPropertySet[ 1];
		String driverName = this.driverName();
		String driverId = this.driverDefinitionId();
		PropertySetImpl propertySet = new PropertySetImpl( driverName, driverId);
		propertySet.setProperties( driverId, this.buildDriverProperties());
		propsets[ 0] = propertySet;

		XMLFileManager.saveNamedPropertySet( propsets);
		
		File driverDefinitioneFile =  this.getDriverDefinitionLocation().append( driverFileName).toFile();
		Assert.assertTrue( driverDefinitioneFile.exists());
	}
	
}
