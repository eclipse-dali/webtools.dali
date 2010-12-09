/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.gen;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.jaxb.core.internal.JptJaxbCoreMessages;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 *  GenerateJaxbClassesJob
 */
public class GenerateJaxbClassesJob extends WorkspaceJob {
	private final IJavaProject javaProject;
	private final String schemaPathOrUri;
	private final String outputDir;
	private final String targetPackage;
	private final String catalog;
	private final boolean usesMoxyGenerator;
	private final String[] bindingsFileNames;
	private final ClassesGeneratorOptions generatorOptions;
	private final ClassesGeneratorExtensionOptions generatorExtensionOptions;

	// ********** constructors **********
	
	public GenerateJaxbClassesJob(
		IJavaProject javaProject, 
		String schemaPathOrUri, 
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean usesMoxyGenerator,
		String[] bindingsFileNames,
		ClassesGeneratorOptions generatorOptions,
		ClassesGeneratorExtensionOptions generatorExtensionOptions) {
		
		super(JptJaxbCoreMessages.ClassesGenerator_generatingClasses);
		if(javaProject == null) {
			throw new RuntimeException("Project is null");		//$NON-NLS-1$
		}
		else if(StringTools.stringIsEmpty(schemaPathOrUri)) {
			throw new RuntimeException("Schema cannot be empty");	//$NON-NLS-1$
		}
		else if(StringTools.stringIsEmpty(outputDir)) {
				throw new RuntimeException("Output directory cannot be empty");	//$NON-NLS-1$
		}
		this.javaProject = javaProject;
		this.schemaPathOrUri = schemaPathOrUri;
		this.outputDir = outputDir;
		this.targetPackage = targetPackage;
		this.catalog = catalog;
		this.usesMoxyGenerator = usesMoxyGenerator;
		this.bindingsFileNames = bindingsFileNames;
		this.generatorOptions = generatorOptions;
		this.generatorExtensionOptions = generatorExtensionOptions;
		this.setRule(ResourcesPlugin.getWorkspace().getRuleFactory().modifyRule(this.javaProject.getProject()));
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
		SubMonitor sm = SubMonitor.convert(monitor, JptJaxbCoreMessages.ClassesGenerator_generatingClassesTask, 1);
		try {
			this.classesGeneratorGenerate(this.javaProject, 
				this.schemaPathOrUri, 
				this.outputDir, 
				this.targetPackage, 
				this.catalog, 
				this.usesMoxyGenerator,
				this.bindingsFileNames,
				this.generatorOptions,
				this.generatorExtensionOptions, 
				sm.newChild(1));
		} 
		catch (OperationCanceledException e) {
			return Status.CANCEL_STATUS;
			// fall through and tell monitor we are done
		}
		catch (RuntimeException re) {
			throw new RuntimeException(re);
		}
		return Status.OK_STATUS;
}

	private void classesGeneratorGenerate(IJavaProject javaProject, 
		String schemaPathOrUri, 
		String outputDir, 
		String targetPackage, 
		String catalog, 
		boolean usesMoxyGenerator,
		String[] bindingsFileNames, 
		ClassesGeneratorOptions generatorOptions,
		ClassesGeneratorExtensionOptions generatorExtensionOptions,
		IProgressMonitor monitor) {

		ClassesGenerator.generate(javaProject, 
			schemaPathOrUri, 
			outputDir, 
			targetPackage, 
			catalog, 
			usesMoxyGenerator, 
			bindingsFileNames, 
			generatorOptions, 
			generatorExtensionOptions,
			monitor);
		return;
	}
}