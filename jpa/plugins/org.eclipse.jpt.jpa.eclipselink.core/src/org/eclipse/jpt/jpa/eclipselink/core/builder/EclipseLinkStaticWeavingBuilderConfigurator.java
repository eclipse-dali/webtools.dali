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
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.core.internal.prefs.JpaPreferencesManager;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.LoggingLevel;

/**
 *  Configures and coordinates StaticWeaving builder behavior for the project.
 *  Also handles the builder preferences.
 */
public class EclipseLinkStaticWeavingBuilderConfigurator extends JpaPreferencesManager
{
    public static final String BUILDER_ID = EclipselinkStaticWeavingBuilder.BUILDER_ID;

	private static final String STATIC_WEAVE_PREFIX = "staticweave."; //$NON-NLS-1$

	public static final String SOURCE = "SOURCE"; //$NON-NLS-1$
	public static final String TARGET = "TARGET"; //$NON-NLS-1$
	public static final String LOG_LEVEL = "LOG_LEVEL"; //$NON-NLS-1$
	public static final String PERSISTENCE_INFO = "PERSISTENCE_INFO"; //$NON-NLS-1$

	// ********** constructors **********

	public EclipseLinkStaticWeavingBuilderConfigurator(IProject project) {
		
		super(project);
	}

	// ********** builder **********

	public void addBuilder() {
		try {
			IProjectDescription description = this.getProject().getDescription();
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
			this.getProject().setDescription(description, null);
		} 
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}

	public boolean projectHasStaticWeavingBuilder() {

		try {
			IProjectDescription description = this.getProject().getDescription();
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
			IProjectDescription description = this.getProject().getDescription();
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
			this.getProject().setDescription(description, IResource.NONE, null);
		}
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}

	// ********** preferences **********
	
	//  retrieves preference, if none return default
	
	public String getSourceLocationPreference() {
		
		return this.getLegacyProjectPreference(this.appendPrefix(SOURCE), this.getDefaultSourceLocation());
	}
	
	public String getTargetLocationPreference() {
		
		return this.getLegacyProjectPreference(this.appendPrefix(TARGET), this.getDefaultTargetLocation());
	}
	
	public String getPersistenceInfoPreference() {
		
		return this.getLegacyProjectPreference(this.appendPrefix(PERSISTENCE_INFO), this.getDefaultPersistenceInfo());
	}
	
	public String getLogLevelPreference() {
		
		return this.getLegacyProjectPreference(this.appendPrefix(LOG_LEVEL), this.getDefaultLogLevel());
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
		this.setLegacyProjectPreference(this.appendPrefix(SOURCE), location);
	}
	
	public void removeSourceLocationPreference() {
		this.setLegacyProjectPreference(this.appendPrefix(SOURCE), null);
	}
	
	public void setTargetLocationPreference(String location) {
		this.setLegacyProjectPreference(this.appendPrefix(TARGET), location);
	}
	
	public void removeTargetLocationPreference() {
		this.setLegacyProjectPreference(this.appendPrefix(TARGET), null);
	}
	
	public void setLogLevelPreference(String logLevel) {
		this.setLegacyProjectPreference(this.appendPrefix(LOG_LEVEL), logLevel);
	}
	
	public void removeLogLevelPreference() {
		this.setLegacyProjectPreference(this.appendPrefix(LOG_LEVEL), null);
	}
	
	public void setPersistenceInfoPreference(String persistenceInfo) {
		this.setLegacyProjectPreference(this.appendPrefix(PERSISTENCE_INFO), persistenceInfo);
	}
	
	public void removePersistenceInfoPreference() {
		this.setLegacyProjectPreference(this.appendPrefix(PERSISTENCE_INFO), null);
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

    	if(this.getProject().getName().equals(projectName)) {
    		outputLocation = outputLocation.removeFirstSegments(1);
    	}
    	return outputLocation;
	}

	private IJavaProject getJavaProject() {
	  return  JavaCore.create(this.getProject());
	}
	
	private String appendPrefix(String id) {
		return STATIC_WEAVE_PREFIX + id;
	}

}
