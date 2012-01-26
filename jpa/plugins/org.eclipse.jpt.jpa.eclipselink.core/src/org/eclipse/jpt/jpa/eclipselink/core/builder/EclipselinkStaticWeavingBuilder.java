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

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;
import org.eclipse.jpt.jpa.eclipselink.core.internal.weave.StaticWeave;

public class EclipselinkStaticWeavingBuilder extends IncrementalProjectBuilder
{
    public static final String BUILDER_ID = JptJpaEclipseLinkCorePlugin.PLUGIN_ID + ".builder"; //$NON-NLS-1$

	private StaticWeavingBuilderConfigurator configurator;
	
	// ********** overrides **********
	
	/**
     * Performs static weaving on project's model classes
     */
	@Override
	protected IProject[] build(int kind, Map<String, String> parameters, IProgressMonitor monitor) throws CoreException
	{
		StaticWeave.weave(
			JavaCore.create(this.getProject()), 
			this.configurator.getSourceLocationPreference(),
			this.configurator.getTargetLocationPreference(),
			this.configurator.getLogLevelPreference(),
			this.configurator.getPersistenceInfoPreference(),
			monitor);

		return new IProject[0];
	}

	@Override
	protected void startupOnInitialize() {
		super.startupOnInitialize();
		this.configurator = new StaticWeavingBuilderConfigurator(this.getProject());
	}
	
	@Override
	protected void clean(IProgressMonitor monitor) throws CoreException {
		super.clean(monitor);
		
	}
}
