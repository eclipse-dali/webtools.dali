/*******************************************************************************
* Copyright (c) 2007 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.ddlgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** 
 * This class creates a EclipseLink <code>EntityManagerFactory</code>, 
 * and executes the DDL generator with the command set in the properties: 
 *     <code>eclipselink.ddl-generation.output-mode</code> 
 *     <code>eclipselink.application-location</code>
 * 
 * Current command-line arguments:
 *     [-pu puName] - persistence unit name
 *     [-p propertiesFilePath] - properties for EclipseLink EntityManager
 *  
 *  Example of a properties file:
 *  	eclipselink.jdbc.bind-parameters=false
 *  	eclipselink.jdbc.driver=org.apache.derby.jdbc.EmbeddedDriver
 *  	eclipselink.jdbc.url=jdbc\:derby\:c\:/derbydb/testdb;create\=true
 *  	eclipselink.jdbc.user=tran
 *  	eclipselink.jdbc.password=
 *  	eclipselink.logging.level=FINEST
 *  	eclipselink.logging.timestamp=false
 *  	eclipselink.logging.thread=false
 *  	eclipselink.logging.session=false
 *  	eclipselink.logging.exceptions=true
 *  	eclipselink.orm.throw.exceptions=true
 *  	eclipselink.ddl-generation.output-mode=database
 *  	eclipselink.ddl-generation=drop-and-create-tables
 *  	eclipselink.application-location=c\:/_Projects_/ExampleDDL
 */
public class Main
{
	protected EntityManagerFactory emf;
	private Map<String, String> eclipseLinkProperties;
	private String createDDLFileName;
	private String dropDDLFileName;
	private String appLocation;
	private String eclipseLinkPropertiesPath;
	private boolean isDebugMode;
	
	// ********** constructors **********
	
	public static void main(String[] args) {
		new Main().execute(args);
	}

	private Main() {
		super();
	}

	// ********** behavior **********
	
	protected void execute(String[] args) {
		this.initializeWith(args);
		
		this.emf = Persistence.createEntityManagerFactory(this.getPUName(args), this.eclipseLinkProperties);
		this.perform();
		this.closeEntityManagerFactory();
		
		this.dispose();
		return;
	}
	
	protected void perform() {
		// create an EM to generate schema.
		this.emf.createEntityManager().close();
	}
	
	protected void closeEntityManagerFactory() {
		this.emf.close();
	}
	
	private void initializeWith(String[] args) {

		this.eclipseLinkPropertiesPath = this.getEclipseLinkPropertiesPath(args);
		this.eclipseLinkProperties = this.getProperties(this.eclipseLinkPropertiesPath);
		
		this.createDDLFileName = this.getConfigPropertyAsString( 
						"eclipselink.create-ddl-jdbc-file-name",
						this.eclipseLinkProperties,  
						"createDDL.jdbc");
//TODO	
//					SchemaGeneration.ECLIPSELINK_CREATE_FILE_NAME, 
//					this.eclipseLinkProperties,  
//					SchemaGeneration.DEFAULT_SCHEMA_GENERATION_CREATE_FILE_NAME);

		this.dropDDLFileName = this.getConfigPropertyAsString( 
						"eclipselink.drop-ddl-jdbc-file-name",
						this.eclipseLinkProperties,  
						"dropDDL.jdbc");
//TODO	
//					SchemaGeneration.ECLIPSELINK_DROP_FILE_NAME, 
//					this.eclipseLinkProperties,  
//					SchemaGeneration.DEFAULT_SCHEMA_GENERATION_DROP_FILE_NAME);
		
		this.appLocation = this.eclipseLinkProperties.get(
						"eclipselink.application-location");
//TODO	
//					SchemaGeneration.ECLIPSELINK_APPLICATION_LOCATION);
		this.isDebugMode = this.getDebugMode(args);
	}
	
	private void dispose() {

		if( ! this.isDebugMode) {
			new File(this.appLocation + "/" + this.createDDLFileName).delete();
			new File(this.appLocation + "/" + this.dropDDLFileName).delete();
			new File(this.eclipseLinkPropertiesPath).delete();
		}
	}
	
	private Map<String, String> getProperties(String eclipseLinkPropertiesPath) {
		
		Set<Entry<Object, Object>> propertiesSet = null;
		try {
			propertiesSet = this.loadEclipseLinkProperties(eclipseLinkPropertiesPath);
		}
		catch (IOException e) {
			throw new RuntimeException("Missing: " + eclipseLinkPropertiesPath, e);
		}
		
		Map<String, String> properties = new HashMap<String, String>();
		for(Entry<Object, Object> property : propertiesSet) {
			properties.put((String)property.getKey(), (String)property.getValue());
		}
		return properties;
	}

    private Set<Entry<Object, Object>> loadEclipseLinkProperties(String eclipseLinkPropertiesPath) throws IOException {
		
        FileInputStream stream = new FileInputStream(eclipseLinkPropertiesPath);
        
        Properties properties = new Properties();
        properties.load(stream);
        
        return properties.entrySet();
	}

	// ********** argument queries **********
    
	private String getPUName(String[] args) {

		return this.getArgumentValue("-pu", args);
	}
	
	private String getEclipseLinkPropertiesPath(String[] args) {

		return this.getArgumentValue("-p", args);
	}

	private boolean getDebugMode(String[] args) {

		return this.argumentExists("-debug", args);
	}
	
	private String getArgumentValue(String argument, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argument)) {
				int j = i + 1;
				if (j < args.length) {
					return args[j];
				}
			}
		}
		return null;
	}
	
	private boolean argumentExists(String argument, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argument)) {
				return true;
			}
		}
		return false;
	}

	// ****** utility methods *******
	
    /**
     * Check the provided map for an object with the given key.  If that object is not available, check the
     * System properties.  If it is not available from either location, return the default value.
     * @param propertyKey 
     * @param map 
     * @param defaultValue 
     * @return 
     */
    protected String getConfigPropertyAsString(String propertyKey, Map<String, String> overrides, String defaultValue){
    	String value = this.getConfigPropertyAsString(propertyKey, overrides);
        if (value == null){
            value = defaultValue;
        }
        return value;
    }
    
    protected String getConfigPropertyAsString(String propertyKey, Map<String, String> overrides){
        String value = null;
        if (overrides != null){
            value = (String)overrides.get(propertyKey);
        }
        if (value == null){
            value = System.getProperty(propertyKey);
        }
        return value;
    }
}
