/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.datatools.connectivity.ConnectionProfileException;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.datatools.connectivity.drivers.IDriverMgmtConstants;
import org.eclipse.datatools.connectivity.drivers.IPropertySet;
import org.eclipse.datatools.connectivity.drivers.PropertySetImpl;
import org.eclipse.datatools.connectivity.drivers.XMLFileManager;
import org.eclipse.datatools.connectivity.drivers.jdbc.IJDBCDriverDefinitionConstants;
import org.eclipse.datatools.connectivity.internal.ConnectivityPlugin;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileRepository;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.db.tests.internal.JptDbTestsPlugin;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;

import junit.framework.Assert;
import junit.framework.TestCase;

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
        
        this.connectionRepository = JptDbPlugin.instance().connectionProfileRepository();
    }

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        
        if( this.platformIsNew()) {
        	this.loadPlatformProperties();
        	
        	this.buildDriverDefinitionFile( IDriverMgmtConstants.DRIVER_FILE);
        	this.buildConnectionProfile( this.profileName());
        	this.currentDbVendor = this.databaseVendor();
        	this.currentDbVersion = this.databaseVersion();
        }
        this.verifyProfileNamed( this.profileName());
	}

    @Override
	protected void tearDown() throws Exception {
        super.tearDown();
	}


	// ********** tests **********

	public void testConnection() throws Exception {

		this.connect();
		
        this.verifyDatabaseVersionNumber();
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
		IConnectionProfile[] profiles = ProfileManager.getInstance().getProfileByProviderID( this.getProfile().providerID());
		Assert.assertNotNull( profiles);
		Assert.assertTrue( profiles.length > 0);
	}
	
	public void testGetProfileByName() {
		// Get Profile By Name
		IConnectionProfile dtpProfile = this.getDTPProfile();
		Assert.assertNotNull( dtpProfile);
		Assert.assertTrue( dtpProfile.getName().equals( this.profileName()));
	}
	
	public void testGetProfileByInstanceId() {
		// Get Profile By InstanceID
		IConnectionProfile dtpProfile = ProfileManager.getInstance().getProfileByInstanceID( this.getProfile().instanceID());
		Assert.assertNotNull( dtpProfile);
		Assert.assertTrue( dtpProfile.getName().equals( this.profileName()));
	}

	public void testWorkingOffline() {
		
		if( this.getProfile().supportsWorkOfflineMode()) {

			if( ! this.getProfile().canWorkOffline()) {
				this.connect();
				this.saveWorkOfflineData();
				this.disconnect();
				
				Assert.assertTrue( this.getProfile().canWorkOffline());
			}
			this.workOffline();
			
			Assert.assertTrue( this.getProfile().isActive());
			Assert.assertTrue( this.getProfile().isWorkingOffline());

			this.disconnect();
		}
	}
		
	// ********** internal tests **********

    private void verifyDatabaseVersionNumber() {
    	Database database = this.getProfile().database();
    	Assert.assertNotNull( database);
    	
        String actualVersionNumber = database.version();
        String expectedVersionNumber = this.databaseVersion();
        String errorMessage = "Expected version number: " + expectedVersionNumber + " but the actual version number was: " + actualVersionNumber;
        assertTrue( errorMessage, actualVersionNumber.indexOf( expectedVersionNumber) != -1);

        String actualVendor = database.vendor();
        String expectedVendor = this.databaseVendor();
        errorMessage = "Expected vendor: " + expectedVendor + " but the actual vendor was: " + actualVendor;
        assertEquals( errorMessage, actualVendor, expectedVendor);
    }
    
    private void verifyDatabaseContent() {
    	Database database = this.getProfile().database();
    	Assert.assertTrue( database.schemataSize() > 0);
		
        Schema schema = database.schemaNamed( this.getProfile().userName());
		if( schema != null) {
			Assert.assertTrue( schema.sequencesSize() >= 0);
			
			Object[] tableNames = CollectionTools.array( schema.tableNames());
			if( tableNames.length >= 1) {
				Table table = schema.tableNamed(( String)tableNames[ 0]);
				Assert.assertTrue( table.columnsSize() >= 0);
				Assert.assertTrue( table.foreignKeysSize() >= 0);
				Assert.assertTrue( table.primaryKeyColumnsSize() >= 0);
			}
		}
    }

    private void verifyProfileNamed( String profileName) {
    	
    	ConnectionProfile profile = this.getProfileNamed( profileName);
    	Assert.assertTrue( "ConnectionProfile not found", profileName.equals( profile.name()));
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
		Assert.assertTrue( "Could not connect.", this.getProfile().isActive());
		Assert.assertFalse( "Connection corrupted.", this.getProfile().isWorkingOffline());
    }
    
    protected void disconnect() {
    	
    	this.getProfile().disconnect();
        Assert.assertFalse( "Disconnect failed.", this.getProfile().isActive());
    }

	private void workOffline() {
		IStatus status = this.getProfile().workOffline();

		Assert.assertTrue( "Could not work offline.", status.isOK());
	}

	private void saveWorkOfflineData() {
		IStatus status = this.getProfile().saveWorkOfflineData();

		Assert.assertTrue( "Could not save offline data.", status.isOK());
	}
    
	// ********** queries **********

    protected Schema getSchemaNamed( String schemaName) { 

	    return this.getProfile().database().schemaNamed( schemaName);
    }
	
    protected Collection<Table> getTables() {
		
        Schema schema = this.getSchemaNamed( this.getProfile().userName());
		if( schema == null) {
			return new ArrayList<Table>();
		}
		return CollectionTools.collection( schema.tables());
    }

    protected Table getTableNamed( String tableName) { 

	    Schema schema =  this.getSchemaNamed( this.getProfile().userName());
	    Assert.assertNotNull( schema);
	
		return schema.tableNamed( tableName);
    }

    protected String providerId() {
        return ConnectionProfile.CONNECTION_PROFILE_TYPE;
    }
     
    protected String passwordIsSaved() {
        return "true";
    }


    protected IConnectionProfile getDTPProfile() {
		return ProfileManager.getInstance().getProfileByName( this.profileName());
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

    protected ConnectionProfile getProfile() {

    	return this.getProfileNamed( this.profileName());
    }

    protected ConnectionProfile getProfileNamed( String profileName) {
    	
    	return this.connectionRepository.connectionProfileNamed( profileName);
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
		driverProperties.setProperty( ConnectionProfile.DRIVER_DEFINITION_TYPE_PROP_ID, this.driverDefinitionType());
		driverProperties.setProperty( ConnectionProfile.DRIVER_JAR_LIST_PROP_ID, this.databasedriverJarList());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.USERNAME_PROP_ID, this.userName());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.driverClass());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.databaseName());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID, this.userPassword());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.URL_PROP_ID, this.databaseUrl());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.databaseVendor());
		driverProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.databaseVersion());
		return driverProperties;
	}

	private Properties buildBasicProperties() {
		Properties basicProperties = new Properties();
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_NAME_PROP_ID, this.databaseName());
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.USERNAME_PROP_ID, this.userName());
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.PASSWORD_PROP_ID, this.userPassword());
		basicProperties.setProperty( ConnectionProfile.DRIVER_DEFINITION_PROP_ID, this.driverDefinitionId());
		
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.DRIVER_CLASS_PROP_ID, this.driverClass());
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.URL_PROP_ID, this.databaseUrl());
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_VENDOR_PROP_ID, this.databaseVendor());
		basicProperties.setProperty( IJDBCDriverDefinitionConstants.DATABASE_VERSION_PROP_ID, this.databaseVersion());
		
		basicProperties.setProperty( ConnectionProfile.DATABASE_SAVE_PWD_PROP_ID, this.passwordIsSaved());
		return basicProperties;
	}

	private void buildConnectionProfile( String profileName) throws ConnectionProfileException {
		
		ProfileManager profileManager = ProfileManager.getInstance();
		Assert.assertNotNull( profileManager);
		
		IConnectionProfile dtpProfile = this.getDTPProfile();
		if( dtpProfile == null) {
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
