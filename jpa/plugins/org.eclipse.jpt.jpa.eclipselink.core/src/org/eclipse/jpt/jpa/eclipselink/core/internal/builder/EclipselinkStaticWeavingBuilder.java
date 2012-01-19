/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.builder;

import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.weave.StaticWeave;
import org.eclipse.jpt.jpa.eclipselink.core.internal.weave.StaticWeavePreferences;

public class EclipselinkStaticWeavingBuilder extends IncrementalProjectBuilder
{
    public static final String BUILDER_ID = JptJpaEclipseLinkCorePlugin.PLUGIN_ID + ".builder"; //$NON-NLS-1$

	// ********** static methods **********
    
	static public void addBuilder(IProject project) {
		if(project == null) return;
		try {
			IProjectDescription description = project.getDescription();
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
			project.setDescription(description, null);
		} 
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}

	static public boolean projectHasStaticWeavingBuilder(IProject project) {

		try {
			IProjectDescription description = project.getDescription();
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

	static public void removeBuilder(IProject project) {
		if(project == null) return;
		try {
			IProjectDescription description = project.getDescription();
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
			project.setDescription(description, IResource.NONE, null);
		}
		catch(CoreException ce) {
			// if we can't read the information, the project isn't open
			return;
		}
	}
	
	static public String getSourcePreference(IProject project) {
		
		String source = StaticWeavePreferences.getPreference(project, StaticWeavePreferences.SOURCE);
		if(StringTools.stringIsEmpty(source)) {
			source = getJavaProjectOutputLocation(project);	// default
		}
		return source;
	}
	
	static public String getTargetPreference(IProject project) {
		
		String target = StaticWeavePreferences.getPreference(project, StaticWeavePreferences.TARGET);
		if(StringTools.stringIsEmpty(target)) {
			target = getJavaProjectOutputLocation(project);	// default
		}
		return target;
	}
	
	static public String getPersistenceInfoPreference(IProject project) {
		
		String persistenceInfo = StaticWeavePreferences.getPreference(project, StaticWeavePreferences.PERSISTENCE_INFO);
		if(StringTools.stringIsEmpty(persistenceInfo)) {
			persistenceInfo = null;		// default
		}
		return persistenceInfo;
	}
	
	static public String getLogLevelPreference(IProject project) {
		
		String logLevel = StaticWeavePreferences.getPreference(project, StaticWeavePreferences.LOG_LEVEL);
		if(StringTools.stringIsEmpty(logLevel)) {
			logLevel = null;		// default
		}
		return logLevel;
	}

	static private String getJpaProjectPersistenceXmlLocation(IProject project) {
		//TODELETE
		JpaProject jpaProject = JptJpaCorePlugin.getJpaProject(project);
		IPath persistenceXml = jpaProject.getPersistenceXmlResource().getFile().getLocation();
		IPath relativePath = persistenceXml.makeRelativeTo(project.getLocation());
		int segmentCount = relativePath.segmentCount();
		return relativePath.toOSString();
	}
	
	static private String getJavaProjectOutputLocation(IProject project) {
		try {
			return getJavaProjectOutputLocationPath(project).toOSString();
		}
		catch (CoreException e) {
			return "."; 				//$NON-NLS-1$
		}
	}
	
	/**
	 * @return output location path relative to the project
	 */
	static private IPath getJavaProjectOutputLocationPath(IProject project)  throws CoreException {
		IPath outputLocation = getJavaProject(project).getOutputLocation();
    	String projectName = outputLocation.segment(0);

    	if(project.getName().equals(projectName)) {
    		outputLocation = outputLocation.removeFirstSegments(1);
    	}
    	return outputLocation;
	}

	static private IJavaProject getJavaProject(IProject project) {
	  return  JavaCore.create(project);
	}

	// ********** overrides **********

	/**
     * Performs static weaving on project's model classes
     */
	@Override
	protected IProject[] build(int kind, Map<String, String> parameters, IProgressMonitor monitor) throws CoreException
	{
		String buildingType;
		switch(kind) {
			case IncrementalProjectBuilder.FULL_BUILD:
				buildingType = "Full Build";
				break;
			case IncrementalProjectBuilder.INCREMENTAL_BUILD:
				buildingType = "Incremental Build";
				break;
			case IncrementalProjectBuilder.AUTO_BUILD:
				buildingType = "Auto Build";
				break;
			default:
				buildingType = "Clean Build";
				break;
		}	//TODELETE
//		System.out.println("\n ##### JptBuilder building - type = " + buildingType + " - project = " + this.getProject().getName());
		
		String loglevel = "FINEST";

		StaticWeave.weave(
			getJavaProject(this.getProject()), 
			getSourcePreference(this.getProject()),
			getTargetPreference(this.getProject()),
			loglevel, 
			getPersistenceInfoPreference(this.getProject()),
			monitor);
		
		return new IProject[0];
	}
	
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
		
	}
}
