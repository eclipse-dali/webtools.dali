/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.core.gen;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;
import static org.eclipse.persistence.tools.dbws.DBWSPackager.ArchiveUse.ignore;

import java.io.File;
import java.util.logging.Level;

import javax.wsdl.WSDLException;

import org.eclipse.jpt.dbws.eclipselink.core.gen.internal.JptDbwsCoreMessages;
import org.eclipse.jpt.dbws.eclipselink.core.gen.internal.Tools;
import org.eclipse.persistence.oxm.XMLContext;
import org.eclipse.persistence.oxm.XMLUnmarshaller;
import org.eclipse.persistence.tools.dbws.DBWSBuilder;
import org.eclipse.persistence.tools.dbws.DBWSBuilderModel;
import org.eclipse.persistence.tools.dbws.DBWSBuilderModelProject;
import org.eclipse.persistence.tools.dbws.DBWSPackager;
import org.eclipse.persistence.tools.dbws.EclipsePackager;

/**
 *  Generate DBWS content
 *  
 * Current command-line arguments:
 *     [-builderFile dbws-builder.xml] - specifies source builder file
 *     [-stageDir .\Output_Dir] - specifies destination directory
 *     [-packageAs eclipse] - specifies the source package
 */
public class Main
{
	private String builderFile;
	private String stageDir;
	private String packageAs;
	@SuppressWarnings("unused")
	private boolean isDebugMode;

	// ********** static methods **********
	
	public static void main(String[] args) {
		new Main().execute(args);
	}
	
	// ********** constructors **********

	private Main() {
		super();
	}

	// ********** behavior **********
	
	protected void execute(String[] args) {
		
		this.initializeWith(args);
		
		this.generate();
	}

	private void generate() {

		DBWSBuilder builder = new DBWSBuilder();

    	DBWSBuilderModel model = this.buildBuilderModel(this.builderFile);
		if(model == null) {
			this.logMessage(INFO, JptDbwsCoreMessages.NO_GENERATION_PERFORMED);
	        return;
		}

        builder.setProperties(model.properties);
        builder.setOperations(model.operations);

        if( ! this.jdbcDriverIsOnClasspath(builder.getDriver())) {
        	return;
        }
		
        DBWSPackager packager = this.buildEclipsePackager();
        
    	this.initializePackager(packager, builder, this.buildStageDir(this.stageDir));
    	builder.setPackager(packager);
    	
        try {
			builder.start();
		}
		catch(WSDLException e) {
			this.logMessage(SEVERE, JptDbwsCoreMessages.WSDL_EXCEPTION, e.getMessage());
            return;
		}
		catch(Exception e) {
			//TODO Clean-up Stage dir. ?
			this.logMessage(SEVERE, JptDbwsCoreMessages.GENERATION_FAILED, e.getMessage());
			e.printStackTrace();
            return;
		}
		this.logMessage(INFO, JptDbwsCoreMessages.GENERATION_COMPLETED);
        return;

	}

	// ********** internal methods **********
    
	private void initializeWith(String[] args) {
		
    	this.builderFile = this.getArgumentBuilderFile(args);
    	this.stageDir = this.getArgumentStageDir(args);
    	this.packageAs = this.getArgumentPackageAs(args);

		this.isDebugMode = this.getArgumentDebugMode(args);
	}
	    
	private void initializePackager(DBWSPackager packager, DBWSBuilder builder, File stageDir) {

        packager.setDBWSBuilder(builder);
        packager.setArchiveUse(ignore);
        packager.setAdditionalArgs(null);
        packager.setStageDir(stageDir);
        packager.setSessionsFileName(builder.getSessionsFileName());
	}
    
	private DBWSBuilderModel buildBuilderModel(String builderFileName) {
		
    	File builderFile = this.getBuilderFile(builderFileName);
		return this.unmarshal(builderFile);
	}

	private DBWSBuilderModel unmarshal(File builderFile) {
        if((builderFile == null) || ( ! builderFile.exists())) {
        	return null;
        }
		XMLContext context = new XMLContext(new DBWSBuilderModelProject());
        XMLUnmarshaller unmarshaller = context.createUnmarshaller();
        
        DBWSBuilderModel model = (DBWSBuilderModel)unmarshaller.unmarshal(builderFile);
        if(model.properties.size() == 0) {
        	this.logMessage(SEVERE, JptDbwsCoreMessages.NO_OPERATIONS_SPECIFIED);
            return null;
        }
        return model;
	}

	private File getBuilderFile(String builderFileName) {

        File builderFile = new File(builderFileName);
        if( ! (builderFile.exists() && builderFile.isFile())) {
        	this.logMessage(SEVERE, 
        		JptDbwsCoreMessages.UNABLE_TO_LOCATE_BUILDER_XML, builderFileName); //$NON-NLS-1$
        	return null;
        }
        return builderFile;
	}
	
	private File buildStageDir(String stageDirName) {
    	File dir = new File(stageDirName);
        if(( ! dir.exists()) || ( ! dir.isDirectory())) {
        	dir = Tools.buildDirectory(stageDirName);
        }
		return dir;
	}
	
	private EclipsePackager buildEclipsePackager() {
		return new EclipsePackager() {
			@Override
			public void start() {
				  // no need to check for the existence of stageDir, everything is in-memory
			}
		};
	}

	private boolean jdbcDriverIsOnClasspath(String jdbcDriverName) {
	    try {
			Class.forName(jdbcDriverName);
		}
		catch (ClassNotFoundException cnfe) {
			this.logMessage(SEVERE, JptDbwsCoreMessages.DRIVER_NOT_ON_CLASSPATH, jdbcDriverName);
			return false;
		}
		return true;
	}
	
	private void logMessage(Level level, String key, Object argument) {
		Tools.logMessage(level, Tools.bind(key, argument));
	}
	
	private void logMessage(Level level, String key) {
		Tools.logMessage(level, Tools.getString(key));
	}

	// ********** argument queries **********
	
	private String getArgumentBuilderFile(String[] args) {

		return this.getArgumentValue("-builderFile", args);   //$NON-NLS-1$
	}
	
	private String getArgumentStageDir(String[] args) {

		return this.getArgumentValue("-stageDir", args);   //$NON-NLS-1$
	}
	
	private String getArgumentPackageAs(String[] args) {

		return this.getArgumentValue("-packageAs", args);   //$NON-NLS-1$
	}
	
	private boolean getArgumentDebugMode(String[] args) {

		return this.argumentExists("-debug", args);   //$NON-NLS-1$
	}
	
	private String getArgumentValue(String argName, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.equals(argName)) {
				int j = i + 1;
				if (j < args.length) {
					return args[j];
				}
			}
		}
		return null;
	}
	
	private boolean argumentExists(String argName, String[] args) {
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if (arg.toLowerCase().equals(argName)) {
				return true;
			}
		}
		return false;
	}

}
