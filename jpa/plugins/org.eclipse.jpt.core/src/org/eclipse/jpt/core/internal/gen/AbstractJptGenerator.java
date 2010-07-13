/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.internal.gen;

import java.io.File;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.core.internal.JptCoreMessages;

public abstract class AbstractJptGenerator
{
	private IVMInstall jre;
	protected ILaunchConfigurationWorkingCopy launchConfig;
	private ILaunch launch;
	
	protected final IJavaProject javaProject;
	protected final String projectLocation;

	private final boolean isDebug = true;

	// ********** constructors **********

	protected AbstractJptGenerator(IJavaProject javaProject) {
		super();
		this.javaProject = javaProject;
		this.projectLocation = javaProject.getProject().getLocation().toString();
		this.initialize();
	}

	// ********** behavior **********
	
	protected void initialize() {
		try {
			this.jre = this.getProjectJRE();
			if (this.jre == null) {
				String message = "Could not identify the VM."; //$NON-NLS-1$
				throw new RuntimeException(message);
			}
			this.launchConfig = this.buildLaunchConfiguration();
		} 
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	protected void generate(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 10);
		this.preGenerate(sm.newChild(2));
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		sm.subTask(JptCoreMessages.GENERATION_CREATING_LAUNCH_CONFIG_TASK);
		this.initializeLaunchConfiguration();
		sm.worked(1);
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}

		this.addLaunchListener();
		sm.worked(1);

		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		this.launch = this.saveAndLaunchConfig(sm.newChild(6));
	}
	
	private void initializeLaunchConfiguration() {
		this.specifyJRE();
		
		this.specifyProject(); 
		this.specifyMainType();	
		
		this.specifyProgramArguments();
		this.specifyWorkingDir(); 

		this.specifyClasspathProperties();
	}
	
	private void addLaunchListener() {

		this.getLaunchManager().addLaunchListener(this.buildLaunchListener());
	}
	
	protected abstract void preGenerate(IProgressMonitor monitor);
	
	protected void postGenerate() {
		try {
			if ( ! this.isDebug) {
				this.removeLaunchConfiguration();
			}
		}
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	private ILaunchesListener2 buildLaunchListener() {
		return new ILaunchesListener2() {
			
			public void launchesTerminated(ILaunch[] launches) {
				for (int i = 0; i < launches.length; i++) {
					ILaunch launch = launches[i];
					if (launch.equals(AbstractJptGenerator.this.getLaunch())) {

						AbstractJptGenerator.this.postGenerate();
						return;
					}
				}
			}

			public void launchesAdded(ILaunch[] launches) {
				// not interested to this event
			}

			public void launchesChanged(ILaunch[] launches) {
				// not interested to this event
			}

			public void launchesRemoved(ILaunch[] launches) {
				// not interested to this event
			}
		};
	}

	// ********** Setting Launch Configuration **********
	
	private void specifyJRE() {
		String jreName = this.jre.getName(); 
		String vmId = this.jre.getVMInstallType().getId();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, jreName);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_JRE_CONTAINER_PATH, vmId);
	}
	
	private void specifyProject() {
		String projectName = this.getProject().getName();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_PROJECT_NAME, projectName);
	}

	protected IProject getProject() {
		return this.javaProject.getProject();
	}
	
	private void specifyMainType() {
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, this.getMainType());
	}

	protected abstract String getMainType();
	
	protected abstract void specifyProgramArguments();

	protected void specifyClasspathProperties()  {
		List<String> classpath;
		try {
			classpath = this.buildClasspath();
		}
		catch (CoreException e) {
			throw new RuntimeException("An error occured generating a memento", e);
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
	}

	protected abstract List<String> buildClasspath() throws CoreException;
	
	private void specifyWorkingDir() {
		File workingDir = new Path(this.projectLocation).toFile();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
	}

	// ********** ClasspathEntry **********
	
	protected IRuntimeClasspathEntry getSystemLibraryClasspathEntry() throws CoreException {

		IPath systemLibsPath = new Path(JavaRuntime.JRE_CONTAINER);
		return JavaRuntime.newRuntimeContainerClasspathEntry(systemLibsPath, IRuntimeClasspathEntry.STANDARD_CLASSES);
	}
	
	protected IRuntimeClasspathEntry getDefaultProjectClasspathEntry() {

		IRuntimeClasspathEntry projectEntry = JavaRuntime.newDefaultProjectClasspathEntry(this.javaProject);
		projectEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return projectEntry;
	}

	protected static IRuntimeClasspathEntry getArchiveClasspathEntry(IPath archivePath) {
		IRuntimeClasspathEntry archiveEntry = JavaRuntime.newArchiveRuntimeClasspathEntry(archivePath);
		archiveEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		
		return archiveEntry;
	}
	

	// ********** LaunchConfig **********
	
	private ILaunch saveAndLaunchConfig(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 10);
		ILaunchConfiguration configuration = null;
		ILaunch result = null;
		try {
			sm.subTask(JptCoreMessages.GENERATION_SAVING_LAUNCH_CONFIG_TASK);
			configuration = this.launchConfig.doSave();
		}
		catch (CoreException saveException) {
			throw new RuntimeException("Could not save LaunchConfig", saveException);
		}
		sm.worked(1);
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		try {
			sm.subTask(JptCoreMessages.GENERATION_LAUNCHING_CONFIG_TASK);
			result = configuration.launch(ILaunchManager.RUN_MODE, sm.newChild(9));
		}
		catch (CoreException launchException) {
			throw new RuntimeException("An error occured during launch", launchException);
		}
		return result;
	}

	private ILaunchConfigurationWorkingCopy buildLaunchConfiguration() throws CoreException {
		this.removeLaunchConfiguration();

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);

		return type.newInstance(null, this.getLaunchConfigName());
	}

	protected abstract String getLaunchConfigName();
	
	private void removeLaunchConfiguration() throws CoreException {

		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);
	
		ILaunchConfiguration[] configurations = manager.getLaunchConfigurations(type);
				for (int i = 0; i < configurations.length; i++) {
			ILaunchConfiguration configuration = configurations[i];
			if (configuration.getName().equals(this.getLaunchConfigName())) {
				configuration.delete();
				break;
			}
		}
	}

	// ********** Main arguments **********
	
	protected void appendDebugArgument(StringBuffer sb) {
		if (this.isDebug) {
			sb.append(" -debug"); //$NON-NLS-1$
		}
	}

	// ********** Queries **********
	
	protected ILaunch getLaunch() {
		return this.launch;
	}
	
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	private IVMInstall getProjectJRE() throws CoreException {
		return JavaRuntime.getVMInstall(this.javaProject);
	}
}
