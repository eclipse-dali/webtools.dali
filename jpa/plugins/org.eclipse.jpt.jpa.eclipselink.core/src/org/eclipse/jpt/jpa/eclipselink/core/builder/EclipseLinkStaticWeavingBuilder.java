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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.core.gen.LaunchConfigListener;
import org.eclipse.jpt.common.utility.internal.reference.SynchronizedBoolean;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCoreMessages;
import org.eclipse.jpt.jpa.eclipselink.core.internal.plugin.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.weave.EclipseLinkStaticWeave;

public class EclipseLinkStaticWeavingBuilder extends IncrementalProjectBuilder
{
    public static final String BUILDER_ID = JptJpaEclipseLinkCorePlugin.instance().getPluginID() + ".builder"; //$NON-NLS-1$

	private EclipseLinkStaticWeavingBuilderConfigurator configurator;

	private final SynchronizedBoolean generationCompleted;
	private boolean generationSuccessful;

	// ********** constructor **********
	
	public EclipseLinkStaticWeavingBuilder() {
		this.generationCompleted = new SynchronizedBoolean(false);
	}
	
	// ********** IncrementalProjectBuilder implementation **********
	/**
     * Performs static weaving on project's model classes
     */
	@Override
	protected IProject[] build(int kind, Map<String, String> parameters, IProgressMonitor monitor) throws CoreException
	{
		this.staticWeaveGeneratorGenerate(monitor);

		return new IProject[0];
	}
	
	// ********** internal methods **********

	private void staticWeaveGeneratorGenerate(IProgressMonitor monitor) throws CoreException {
		this.generationCompleted.setFalse();
		this.generationSuccessful = false;
		
		JptGenerator staticWeaveGenerator = new EclipseLinkStaticWeave(
								this.getJavaProject(),
								this.configurator.getSourceLocationPreference(),
								this.configurator.getTargetLocationPreference(),
								this.configurator.getLogLevelPreference(),
								this.configurator.getPersistenceInfoPreference());

		LaunchConfigListener launchListener = this.buildLaunchListener();
		staticWeaveGenerator.addLaunchConfigListener(launchListener);
		staticWeaveGenerator.generate(monitor);
		try {
			this.generationCompleted.waitUntilTrue();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		finally {
			staticWeaveGenerator.removeLaunchConfigListener(launchListener);
			this.generationCompleted.setFalse();
		}
		
		this.postGenerate(this.generationSuccessful);
	}
	
	protected void postGenerate(boolean generationSuccessful) throws CoreException {
		if( ! generationSuccessful) {
			throw new RuntimeException(JptJpaEclipseLinkCoreMessages.ECLIPSELINK_STATIC_WEAVING_BUILDER__STATIC_WEAVING_FAILED);
		}
		else {
			this.refreshProject();
		}
	}

	private void refreshProject() throws CoreException {
		this.getProject().refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
	}
	
	private LaunchConfigListener buildLaunchListener() {
		return new LaunchConfigListener() {
			
			public void launchCompleted(boolean generationSuccessful) {
				EclipseLinkStaticWeavingBuilder.this.generationSuccessful = generationSuccessful;
				EclipseLinkStaticWeavingBuilder.this.generationCompleted.setTrue();
			}
		};
	}

	private IJavaProject getJavaProject() {
		return JavaCore.create(this.getProject());
	}
	
	// ********** overrides **********

	@Override
	protected void startupOnInitialize() {
		super.startupOnInitialize();
		this.configurator = new EclipseLinkStaticWeavingBuilderConfigurator(this.getProject());
	}
	
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
		this.generationCompleted.setFalse();
		this.generationSuccessful = false;
	}
}
