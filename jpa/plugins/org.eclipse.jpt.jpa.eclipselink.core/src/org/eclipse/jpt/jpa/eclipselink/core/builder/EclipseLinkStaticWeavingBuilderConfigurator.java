/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
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
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaPreferences;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLoggingLevel;

/**
 *  Configures and coordinates StaticWeaving builder behavior for the project.
 *  Also handles the builder preferences.
 */
public class EclipseLinkStaticWeavingBuilderConfigurator
{
	private final IProject project;

	private static final String BUILDER_ID = EclipseLinkStaticWeavingBuilder.BUILDER_ID;


	// ********** constructors **********

	public EclipseLinkStaticWeavingBuilderConfigurator(IProject project) {
		super();
		this.project = project;
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
		
		return EclipseLinkJpaPreferences.getStaticWeavingSourceLocation(this.project, this.getDefaultSourceLocation());
	}
	
	public String getTargetLocationPreference() {
		
		return EclipseLinkJpaPreferences.getStaticWeavingTargetLocation(this.project, this.getDefaultTargetLocation());
	}
	
	public String getPersistenceInfoPreference() {
		
		return EclipseLinkJpaPreferences.getStaticWeavingPersistenceInfo(this.project, this.getDefaultPersistenceInfo());
	}
	
	public String getLogLevelPreference() {
		
		return EclipseLinkJpaPreferences.getStaticWeavingLogLevel(this.project, this.getDefaultLogLevel());
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
	
	public EclipseLinkLoggingLevel[] getLogLevelValues() {
		
		return EclipseLinkLoggingLevel.values();
	}

	//  setting and removing preferences
	
	public void setSourceLocationPreference(String location) {
		EclipseLinkJpaPreferences.setStaticWeavingSourceLocation(this.project, location);
	}
	
	public void removeSourceLocationPreference() {
		EclipseLinkJpaPreferences.setStaticWeavingSourceLocation(this.project, null);
	}
	
	public void setTargetLocationPreference(String location) {
		EclipseLinkJpaPreferences.setStaticWeavingTargetLocation(this.project, location);
	}
	
	public void removeTargetLocationPreference() {
		EclipseLinkJpaPreferences.setStaticWeavingTargetLocation(this.project, null);
	}
	
	public void setLogLevelPreference(String logLevel) {
		EclipseLinkJpaPreferences.setStaticWeavingLogLevel(this.project, logLevel);
	}
	
	public void removeLogLevelPreference() {
		EclipseLinkJpaPreferences.setStaticWeavingLogLevel(this.project, null);
	}
	
	public void setPersistenceInfoPreference(String info) {
		EclipseLinkJpaPreferences.setStaticWeavingPersistenceInfo(this.project, info);
	}
	
	public void removePersistenceInfoPreference() {
		EclipseLinkJpaPreferences.setStaticWeavingPersistenceInfo(this.project, null);
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
