/*******************************************************************************
* Copyright (c) 2007, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.ddlgen;

import static java.util.logging.Level.SEVERE;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.dynamic.DynamicClassLoader;

/** 
 * This class creates a EclipseLink <code>EntityManagerFactory</code>, 
 * and executes the DDL generator with the command set in the properties: 
 *     <code>eclipselink.ddl-generation.output-mode</code> 
 *     <code>eclipselink.application-location</code>
 * 
 * Current command-line arguments:
 *     [-pu puName] - persistence unit name
 *     [-p propertiesFilePath] - properties for EclipseLink EntityManager
 *     [-debug] - debug mode
 *     [-dynamic] - use the org.eclipse.persistence.dynamic.DynamicClassLoader
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
	protected EntityManagerFactory entityManagerFactory;
	private Map<String, Object> eclipseLinkProperties;
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
		
		this.entityManagerFactory = this.buildEntityManagerFactory(this.getPUName(args), this.eclipseLinkProperties);
		this.generate();
		this.closeEntityManagerFactory();
		
		this.dispose();
		return;
	}
	
	private void generate() {
		// create an EM to generate schema.
		this.entityManagerFactory.createEntityManager().close();
	}
	
	private EntityManagerFactory buildEntityManagerFactory(String puName,  Map<String, Object> properties) {
		return Persistence.createEntityManagerFactory(puName, properties);
	}
	
	private void closeEntityManagerFactory() {
		this.entityManagerFactory.close();
	}
	
	private void initializeWith(String[] args) {
		this.eclipseLinkPropertiesPath = this.getEclipseLinkPropertiesPath(args);
		this.eclipseLinkProperties = this.getProperties(this.eclipseLinkPropertiesPath);
		this.setDynamicClassLoaderProperty(args);

		this.isDebugMode = this.getDebugMode(args);
	}

	private void setDynamicClassLoaderProperty(String[] args) {
		if(this.getEclipseLinkDynamic(args)) {
			this.eclipseLinkProperties.put(PersistenceUnitProperties.CLASSLOADER, this.buildDynamicClassLoader());
		}
	}

	private ClassLoader buildDynamicClassLoader() {
		return new DynamicClassLoader(Thread.currentThread().getContextClassLoader());
	}

	private void dispose() {
		if( ! this.isDebugMode) {
			new File(this.eclipseLinkPropertiesPath).delete();
		}
	}
	
	private Map<String, Object> getProperties(String eclipseLinkPropertiesPath) {
		Set<Entry<Object, Object>> propertiesSet = null;
		try {
			propertiesSet = this.loadEclipseLinkProperties(eclipseLinkPropertiesPath);
		}
		catch (IOException e) {
			this.logMessage(SEVERE, "Missing: " + eclipseLinkPropertiesPath); //$NON-NLS-1$
			e.printStackTrace();
			this.generationFailed();
		}
		
		Map<String, Object> properties = new HashMap<String, Object>();
		for(Entry<Object, Object> property : propertiesSet) {
			properties.put((String) property.getKey(), property.getValue());
		}
		return properties;
	}

    private Set<Entry<Object, Object>> loadEclipseLinkProperties(String eclipseLinkPropertiesPath) throws IOException {
        FileInputStream stream = new FileInputStream(eclipseLinkPropertiesPath);
        
        Properties properties = new Properties();
        properties.load(stream);
        
        return properties.entrySet();
	}
	
	private void generationFailed() {
		System.exit(1);
	}

	// ********** argument queries **********
    
	private String getPUName(String[] args) {
		return this.getArgumentValue("-pu", args); //$NON-NLS-1$
	}
	
	private String getEclipseLinkPropertiesPath(String[] args) {
		return this.getArgumentValue("-p", args); //$NON-NLS-1$
	}

	private boolean getDebugMode(String[] args) {
		return this.argumentExists("-debug", args); //$NON-NLS-1$
	}
	
	private boolean getEclipseLinkDynamic(String[] args) {
		return this.argumentExists("-dynamic", args); //$NON-NLS-1$
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

	// ********** argument queries **********
	
	public void logMessage(Level level, String message) {
		if(level == SEVERE) {
			System.err.println('\n' + message);
		}
		else {
			System.out.println('\n' + message);
		}
	}
}
