/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.gen;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.core.gen.JptGenerator;
import org.eclipse.jpt.common.ui.gen.AbstractJptGenerateJob;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGenerator;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorExtensionOptions;
import org.eclipse.jpt.jaxb.core.internal.gen.ClassesGeneratorOptions;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.plugin.JptJaxbUiPlugin;

/**
 *  GenerateJaxbClassesJob
 */
public class GenerateJaxbClassesJob extends AbstractJptGenerateJob {
	private final String absoluteLocalXsdUri;
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
		String absoluteLocalXsdUri, 
		String outputDir,
		String targetPackage, 
		String catalog, 
		boolean usesMoxyGenerator,
		String[] bindingsFileNames,
		ClassesGeneratorOptions generatorOptions,
		ClassesGeneratorExtensionOptions generatorExtensionOptions) {
		
		super(JptJaxbUiMessages.GenerateJaxbClassesJob_generatingClasses, javaProject);
		if (javaProject == null) {
			throw new RuntimeException("Project is null");		//$NON-NLS-1$
		}
		else if (StringTools.isBlank(absoluteLocalXsdUri)) {
			throw new RuntimeException("Schema cannot be empty");	//$NON-NLS-1$
		}
		else if(StringTools.isBlank(outputDir)) {
				throw new RuntimeException("Output directory cannot be empty");	//$NON-NLS-1$
		}
		this.absoluteLocalXsdUri = absoluteLocalXsdUri;
		this.outputDir = outputDir;
		this.targetPackage = targetPackage;
		this.catalog = catalog;
		this.usesMoxyGenerator = usesMoxyGenerator;
		this.bindingsFileNames = bindingsFileNames;
		this.generatorOptions = generatorOptions;
		this.generatorExtensionOptions = generatorExtensionOptions;
	}

	// ********** overwrite AbstractJptGenerateJob **********

	@Override
	protected JptGenerator buildGenerator() {
		return new ClassesGenerator(this.getJavaProject(), 
			this.absoluteLocalXsdUri, 
			this.outputDir, 
			this.targetPackage, 
			this.catalog, 
			this.usesMoxyGenerator,
			this.bindingsFileNames,
			this.generatorOptions,
			this.generatorExtensionOptions);	}

	@Override
	protected void postGenerate() {
		this.refreshProject();
	}

	@Override
	protected String getJobName() {
		return JptJaxbUiMessages.GenerateJaxbClassesJob_generatingClassesTask;
	}

	@Override
	protected void jptPluginLogException(Exception exception) {
		JptJaxbUiPlugin.instance().logError(exception);
	}
}