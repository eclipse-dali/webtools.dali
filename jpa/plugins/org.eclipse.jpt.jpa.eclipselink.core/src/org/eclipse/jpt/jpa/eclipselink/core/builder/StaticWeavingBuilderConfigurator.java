/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.builder;

import java.util.logging.Level;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;
import org.osgi.service.prefs.BackingStoreException;

/**
 *  Configures and coordinates StaticWeaving builder behavior for the project.
 *  Also handles the builder preferences.
 */
public class StaticWeavingBuilderConfigurator
{
    public static final String BUILDER_ID = EclipselinkStaticWeavingBuilder.BUILDER_ID;

	private static final String STATIC_WEAVE_PREFIX = "staticweave."; //$NON-NLS-1$

	public static final String SOURCE = "SOURCE"; //$NON-NLS-1$
	public static final String TARGET = "TARGET"; //$NON-NLS-1$
	public static final String LOG_LEVEL = "LOG_LEVEL"; //$NON-NLS-1$
	public static final String PERSISTENCE_INFO = "PERSISTENCE_INFO"; //$NON-NLS-1$
	
	private final IProject project;

	// ********** constructors **********

	public StaticWeavingBuilderConfigurator(IProject project) {
		
		this.project = project;
		if(this.project == null) {
			throw new RuntimeException("Project is null"); 	//$NON-NLS-1$
		}
	}

	// ********** builder **********

	public void addBuilder() {
		try {
			IProjectDescription description = this.project.getDescription();
			ICommand[] commands = description.getBuildSpec();

			ICommand newCommand = description.newCommand();
			newCommand.setBuilderName(BUILDER_ID);

			ICommand[] newCommands = null;
			if(commands != null) {
				newCommands = new ICommand[commands.length + 1];
				System.arraycopy(commands, 0, newCommands, 0, commands.length);
				newCommands[commands.length] = newCommand;
			} 
			else {
				newCommands = new ICommand[1];
				newCommands[0] = newCommand;
			}
			description.setBuildSpec(newCommands);
			this.project.setDescription(description, null);
		} 
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}

	public boolean projectHasStaticWeavingBuilder() {

		try {
			IProjectDescription description = this.project.getDescription();
			ICommand[] commands = description.getBuildSpec();
			if(commands.length == 0)
				return false;
			for(int i = 0; i < commands.length; i++) {
				if(commands[i].getBuilderName().equals(BUILDER_ID)) {
					return true;
				}
			}
			return false;
		}
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return false;
		}
	}

	public void removeBuilder() {
		try {
			IProjectDescription description = this.project.getDescription();
			ICommand[] commands = description.getBuildSpec();
			if(commands.length == 0)
				return;
			int newLength = 0;
			// null out all commands that match BUILDER_ID
			for(int i = 0; i < commands.length; i++) {
				if(commands[i].getBuilderName().equals(BUILDER_ID))
					commands[i] = null;
				else
					newLength++;
			}
			// check if any were actually removed
			if(newLength == commands.length)
				return;

			ICommand[] newCommands = new ICommand[newLength];
			for(int i = 0, j = 0; i < commands.length; i++) {
				if(commands[i] != null)
					newCommands[j++] = commands[i];
			}
			description.setBuildSpec(newCommands);
			this.project.setDescription(description, IResource.NONE, null);
		}
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}

	// ********** preferences **********
	
	//  retrieves preference, if none return default
	
	public String getSourceLocationPreference() {
		
		return this.getPreference(SOURCE, this.getDefaultSourceLocation());
	}
	
	public String getTargetLocationPreference() {
		
		return this.getPreference(TARGET, this.getDefaultTargetLocation());
	}
	
	public String getPersistenceInfoPreference() {
		
		return this.getPreference(PERSISTENCE_INFO, this.getDefaultPersistenceInfo());
	}
	
	public String getLogLevelPreference() {
		
		return this.getPreference(LOG_LEVEL, this.getDefaultLogLevel());
	}

	// default preferences value
	
	public String getDefaultSourceLocation() {
		return this.getJavaProjectOutputLocation();
	}
	
	public String getDefaultTargetLocation() {
		return this.getJavaProjectOutputLocation();
	}
	
	public String getDefaultPersistenceInfo() {
		return null;
	}
	
	public String getDefaultLogLevel() {
		return Level.SEVERE.getName();
	}
	
	public LoggingLevel[] getLogLevelValues() {
		
		return LoggingLevel.values();
	}

	//  setting and removing preferences
	
	public void setSourceLocationPreference(String location) {
		this.setPreference(SOURCE, location);
	}
	
	public void removeSourceLocationPreference() {
		this.setPreference(SOURCE, null);
	}
	
	public void setTargetLocationPreference(String location) {
		this.setPreference(TARGET, location);
	}
	
	public void removeTargetLocationPreference() {
		this.setPreference(TARGET, null);
	}
	
	public void setLogLevelPreference(String logLevel) {
		this.setPreference(LOG_LEVEL, logLevel);
	}
	
	public void removeLogLevelPreference() {
		this.setPreference(LOG_LEVEL, null);
	}
	
	public void setPersistenceInfoPreference(String persistenceInfo) {
		this.setPreference(PERSISTENCE_INFO, persistenceInfo);
	}
	
	public void removePersistenceInfoPreference() {
		this.setPreference(PERSISTENCE_INFO, null);
	}
	
	// fush preferences
	
	public void flush(IEclipsePreferences prefs) {
		try {
			prefs.flush();
		} 
		catch(BackingStoreException ex) {
			JptJpaCorePlugin.log(ex);
		}
	}

	// ********** preferences private methods **********
	
	private String getPreference(String id, String defaultValue) {
		return this.getPreference_(JptJpaCorePlugin.getProjectPreferences(this.project), id, defaultValue);
	}

	private String getPreference_(IEclipsePreferences prefs, String id, String defaultValue) {
		return prefs.get(this.appendStaticWeavePrefix(id), defaultValue);
	}
	
	private String appendStaticWeavePrefix(String id) {
		return STATIC_WEAVE_PREFIX + id;
	}
	
	private void setPreference(String id, String staticWeavePreference) {
		IEclipsePreferences projectPrefs = JptJpaCorePlugin.getProjectPreferences(this.project);
		
		this.setPreference_(projectPrefs, id, staticWeavePreference);
		this.flush(projectPrefs);
	}

	private void setPreference_(IEclipsePreferences preferences, String id, String staticWeavePreference) {
		if(StringTools.stringIsEmpty(staticWeavePreference)) {
			preferences.remove(this.appendStaticWeavePrefix(id));
		}
		else {
			preferences.put(this.appendStaticWeavePrefix(id), staticWeavePreference);
		}
	}

	// ********** private methods **********
	
	private String getJavaProjectOutputLocation() {
		try {
			return this.getJavaProjectOutputLocationPath().toOSString();
		}
		catch (CoreException e) {
			return "."; 				//$NON-NLS-1$
		}
	}
	
	/**
	 * @return output location path relative to the project
	 */
	private IPath getJavaProjectOutputLocationPath()  throws CoreException {
		IPath outputLocation = this.getJavaProject().getOutputLocation();
    	String projectName = outputLocation.segment(0);

    	if(this.project.getName().equals(projectName)) {
    		outputLocation = outputLocation.removeFirstSegments(1);
    	}
    	return outputLocation;
	}

	private IJavaProject getJavaProject() {
	  return  JavaCore.create(this.project);
	}

}
