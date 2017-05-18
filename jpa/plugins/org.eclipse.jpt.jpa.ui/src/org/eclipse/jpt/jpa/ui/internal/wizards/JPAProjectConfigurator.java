/*******************************************************************************
 * Copyright (c) 2016 Red Hat Inc.
 * All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *   Mickael Istria (Red Hat Inc.) - initial implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jpt.jpa.core.internal.facet.JpaFacetInstallDataModelProvider;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jst.j2ee.project.facet.IJ2EEModuleFacetInstallDataModelProperties;
import org.eclipse.ui.wizards.datatransfer.ProjectConfigurator;
import org.eclipse.ui.wizards.datatransfer.RecursiveFileFinder;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.w3c.dom.Document;

public class JPAProjectConfigurator implements ProjectConfigurator {

	@Override
	public boolean canConfigure(IProject project, Set<IPath> ignoredDirectories, IProgressMonitor monitor) {
		try {
			RecursiveFileFinder finder = new RecursiveFileFinder("persistence.xml", ignoredDirectories);
			project.accept(finder);
			return finder.getFile() != null;
		} catch (CoreException ex) {
			return false;
		}
	}

	@Override
	public void configure(IProject project, Set<IPath> ignoredDirectories, IProgressMonitor monitor) {
		try {
			IFacetedProject facetedProject = ProjectFacetsManager.create(project, true, monitor);

			IProjectFacet JPA_FACET = ProjectFacetsManager.getProjectFacet("jpt.jpa");
			if (!facetedProject.hasProjectFacet(JPA_FACET)) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				RecursiveFileFinder finder = new RecursiveFileFinder("persistence.xml", ignoredDirectories);
				project.accept(finder);
				InputStream webXmlStream = finder.getFile().getContents();
				Document doc = dBuilder.parse(webXmlStream);
				webXmlStream.close();
	
				IDataModel aFacetInstallDataModel = DataModelFactory.createDataModel(new JpaFacetInstallDataModelProvider());
				aFacetInstallDataModel.setBooleanProperty(IJ2EEModuleFacetInstallDataModelProperties.ADD_TO_EAR, false);
				String version = doc.getElementById("persistence").getAttribute("version");
				facetedProject.installProjectFacet(JPA_FACET.getVersion(version), aFacetInstallDataModel, monitor);
			}
		} catch (Exception ex) {
			JptJpaUiPlugin.instance().logError(ex);
		}
	}

	@Override
	public boolean shouldBeAnEclipseProject(IContainer container, IProgressMonitor monitor) {
		return false; // TODO can we make sure a given dir is actually a JPA project
	}

	@Override
	public Set<IFolder> getFoldersToIgnore(IProject project, IProgressMonitor monitor) {
		return null;
	}

	// TODO uncomment @Override when adopting newer Easymport
	// @Override
	public Set<File> findConfigurableLocations(File root, IProgressMonitor monitor) {
		// No easy way to deduce project roots from jee files...
		return Collections.emptySet();
	}

}
