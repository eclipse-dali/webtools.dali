/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.gen;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.ILaunchesListener2;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.jdt.launching.IRuntimeClasspathEntry;
import org.eclipse.jdt.launching.IVMInstall;
import org.eclipse.jdt.launching.JavaRuntime;
import org.eclipse.jpt.common.core.JptCommonCoreMessages;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.core.gen.LaunchConfigListener;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.model.ModelTools;
import org.eclipse.osgi.service.datalocation.Location;
import org.osgi.framework.Bundle;

public abstract class AbstractJptGenerator implements JptGenerator
{
	public static final String PLUGINS_DIR = "plugins/";	  //$NON-NLS-1$

	private IVMInstall jre;
	private ILaunchConfigurationWorkingCopy launchConfig;
	/* CU private */ ILaunch launch;
	
	private final IJavaProject javaProject;
	private final String projectLocation;
	private final ListenerList<LaunchConfigListener> launchConfigListenerList = ModelTools.listenerList();

	private boolean isDebug = false;

	// ********** constructors **********

	protected AbstractJptGenerator(IJavaProject javaProject) {
		super();
		this.javaProject = javaProject;
		this.projectLocation = javaProject.getProject().getLocation().toString();
		this.initialize();
	}

	// ********** abstract methods **********
	
	protected abstract String getMainType();

	protected abstract String getLaunchConfigName();

	protected String getBootstrapJarPrefix() {
		throw new RuntimeException("Bootstrap JAR not specified.");   //$NON-NLS-1$;
	}
	
	protected abstract void specifyProgramArguments();

	protected abstract List<String> buildClasspath() throws CoreException;

	// ********** behavior **********
	
	protected void initialize() {
		try {
			this.launchConfig = this.buildLaunchConfiguration();
			this.jre = this.getProjectJRE();
			if (this.jre == null) {
				String message = "Could not identify the VM."; //$NON-NLS-1$
				throw new RuntimeException(message);
			}
		} 
		catch (CoreException e) {
			throw new RuntimeException(e);
		}
	}

	public JptGenerator generate(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 10);
		this.preGenerate(sm.newChild(2));
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		sm.subTask(JptCommonCoreMessages.GENERATION_CREATING_LAUNCH_CONFIG_TASK);
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

		return this;
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
	
	/* CU private */ void removeLaunchListener(ILaunchesListener2 listener) {

		this.getLaunchManager().removeLaunchListener(listener);
	}
	
	protected abstract void preGenerate(IProgressMonitor monitor);

	protected void postGenerate(boolean generationSuccessful) {
		try {
			if( ! this.isDebug) {
				this.removeLaunchConfiguration();
			}
			this.notifyLaunchConfigListeners(generationSuccessful);
		}
		catch(CoreException e) {
			throw new RuntimeException(e);
		}
	}

	private void notifyLaunchConfigListeners(boolean generationSuccessful) {
		for(LaunchConfigListener launchConfigListener : this.launchConfigListenerList) {
			launchConfigListener.launchCompleted(generationSuccessful);
		}	
	}
	
	/* CU private */ void generationSuccessful() {
		this.postGenerate(true);
	}
	
	/* CU private */ void generationFailed() {
		this.postGenerate(false);
	}

	private ILaunchesListener2 buildLaunchListener() {
		return new ILaunchesListener2() {
			
			public void launchesTerminated(ILaunch[] launches) {
				for (ILaunch terminatedLaunch : launches) {
					if (terminatedLaunch.equals(AbstractJptGenerator.this.getLaunch())) {
						try {
							if(terminatedLaunch.isTerminated()) {
								if(this.generationIsSuccessful(terminatedLaunch.getProcesses())) {
									AbstractJptGenerator.this.generationSuccessful();
								}
								else {
									AbstractJptGenerator.this.generationFailed();
								}
								AbstractJptGenerator.this.launch = null;
							}
						}
						finally {
							AbstractJptGenerator.this.removeLaunchListener(this);
						}
						return;
					}
				}
			}

			private boolean generationIsSuccessful(IProcess[] processes) {
				int exitValue = -1;
				for (IProcess process : processes) {
					try {
						exitValue = process.getExitValue();
						break;
					}
					catch (DebugException e) {
						throw new RuntimeException(e);
					}
				}
				return (exitValue == 0);
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
	
	protected void specifyJRE() {
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

	protected void specifyClasspathProperties()  {
		List<String> classpath;
		try {
			classpath = this.buildClasspath();
		}
		catch (CoreException e) {
			throw new RuntimeException("An error occured generating a memento", e); //$NON-NLS-1$
		}
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_CLASSPATH, classpath);
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_DEFAULT_CLASSPATH, false);
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

	protected static IRuntimeClasspathEntry getVariableRuntimeClasspathEntry(IPath path) {
		IRuntimeClasspathEntry varEntry = JavaRuntime.newVariableRuntimeClasspathEntry(path);
		varEntry.setClasspathProperty(IRuntimeClasspathEntry.USER_CLASSES);
		return varEntry;
	}

	// ********** LaunchConfig **********
	
	protected ILaunch saveAndLaunchConfig(IProgressMonitor monitor) {
		SubMonitor sm = SubMonitor.convert(monitor, 10);
		ILaunchConfiguration configuration = null;
		ILaunch result = null;
		try {
			sm.subTask(JptCommonCoreMessages.GENERATION_SAVING_LAUNCH_CONFIG_TASK);

			this.launchConfig.setAttribute(DebugPlugin.ATTR_CAPTURE_OUTPUT, true);
			configuration = this.launchConfig.doSave();
		}
		catch (CoreException saveException) {
			throw new RuntimeException("Could not save LaunchConfig", saveException); //$NON-NLS-1$
		}
		sm.worked(1);
		if (sm.isCanceled()) {
			throw new OperationCanceledException();
		}
		try {
			sm.subTask(JptCommonCoreMessages.GENERATION_LAUNCHING_CONFIG_TASK);
			result = configuration.launch(ILaunchManager.RUN_MODE, sm.newChild(9));
		}
		catch (CoreException launchException) {
			throw new RuntimeException("An error occured during launch", launchException); //$NON-NLS-1$
		}
		return result;
	}

	// ********** Main arguments **********
	
	protected void appendDebugArgument(StringBuffer sb) {
		if (this.isDebug) {
			sb.append(" -debug"); //$NON-NLS-1$
		}
	}

	// ********** Queries **********

	protected IJavaProject getJavaProject() {
		return this.javaProject;
	}
	
	protected ILaunch getLaunch() {
		return this.launch;
	}
	
	protected ILaunchConfigurationWorkingCopy getLaunchConfig() {
		return this.launchConfig;
	}

	protected String getProjectLocation() {
		return this.projectLocation;
	}
	
	protected ILaunchManager getLaunchManager() {
		return DebugPlugin.getDefault().getLaunchManager();
	}
	
	private IVMInstall getProjectJRE() throws CoreException {
		return JavaRuntime.getVMInstall(this.javaProject);
	}

	// ********** Utilities **********

	protected IRuntimeClasspathEntry getBootstrapJarClasspathEntry() {
		return getArchiveClasspathEntry(this.buildBootstrapJarPath());
	}

	protected IPath buildBootstrapJarPath() {
		return this.findGenJarStartingWith(this.getBootstrapJarPrefix());
	}
	
	protected IPath findGenJarStartingWith(String genJarName) {
		try {
			File jarInstallDir = this.getBundleParentDir(JptCommonCorePlugin.instance().getBundle());

			List<File> result = new ArrayList<File>();
			this.findFileStartingWith(genJarName, jarInstallDir, result);
			if (result.isEmpty()) {
				throw new RuntimeException("Could not find: " + genJarName + "#.#.#v###.jar in: " + jarInstallDir);   //$NON-NLS-1$ //$NON-NLS-2$
			}
			File genJarFile = result.get(0);
			String genJarPath = genJarFile.getCanonicalPath();
			return new Path(genJarPath);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	// ********** private methods **********

	private File getBundleParentDir(Bundle bundle) throws IOException {
	
		if (Platform.inDevelopmentMode()) {
			Location eclipseHomeLoc = Platform.getInstallLocation();
			String eclipseHome = eclipseHomeLoc.getURL().getPath();
			if ( ! eclipseHome.endsWith(PLUGINS_DIR)) {
				eclipseHome += PLUGINS_DIR;
			}
			return new File(eclipseHome);
		}
		return FileLocator.getBundleFile(bundle).getParentFile();
	}

	private ILaunchConfigurationWorkingCopy buildLaunchConfiguration() throws CoreException {
		this.removeLaunchConfiguration();

		ILaunchManager manager = this.getLaunchManager();
		ILaunchConfigurationType type = manager.getLaunchConfigurationType(IJavaLaunchConfigurationConstants.ID_JAVA_APPLICATION);

		return type.newInstance(null, this.getLaunchConfigName());
	}
	
	private void removeLaunchConfiguration() throws CoreException {

		ILaunchManager manager = this.getLaunchManager();
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

	private void findFileStartingWith(String fileName, File directory, List<? super File> list) {
		if(directory.listFiles() == null) {
			throw new RuntimeException("Could not find directory: " + directory);   //$NON-NLS-1$
		}
		for (File file : directory.listFiles()) {
			if (file.getName().startsWith(fileName)) {
				list.add(file);
			}
			if (file.isDirectory()) {
				this.findFileStartingWith(fileName, file, list);
			}
		}
	}
	
	private void specifyWorkingDir() {
		File workingDir = new Path(this.projectLocation).toFile();
		this.launchConfig.setAttribute(IJavaLaunchConfigurationConstants.ATTR_WORKING_DIRECTORY, workingDir.getAbsolutePath());
	}

	protected void setDebug(boolean isDebug) {
		this.isDebug = isDebug;
	}
	

	// ********** listener **********
	
	public void addLaunchConfigListener(LaunchConfigListener listener) {
		this.launchConfigListenerList.add(listener);
	}

	public void removeLaunchConfigListener(LaunchConfigListener listener) {
		this.launchConfigListenerList.remove(listener);
	}
}
