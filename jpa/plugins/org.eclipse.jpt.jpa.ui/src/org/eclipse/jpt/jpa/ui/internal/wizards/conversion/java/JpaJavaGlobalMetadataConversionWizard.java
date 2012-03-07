/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;
import java.io.File;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.common.core.internal.resource.ResourceLocatorManager;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiIcons;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectJPAProjectWizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public abstract class JpaJavaGlobalMetadataConversionWizard extends Wizard {
	public static final String HELP_CONTEXT_ID = JptJpaUiPlugin.PLUGIN_ID + ".GLobalMetadataConversion"; //$NON-NLS-1$

	protected JpaProject jpaProject;
	protected final ResourceManager resourceManager;
	protected SimplePropertyValueModel<String>  mappingXmlModel;
	protected IStructuredSelection selection;

	private SelectJPAProjectWizardPage selectJPAProjectWizardPage;
	protected JpaJavaGlobalMetadataConversionWizardPage<?> jpaMetadataConversionWizardPage;

	public JpaJavaGlobalMetadataConversionWizard(JpaProject jpaProject) {
		this.jpaProject = jpaProject;
		this.resourceManager = new LocalResourceManager(JFaceResources.getResources());
		this.mappingXmlModel = new SimplePropertyValueModel<String>();
		this.setWindowTitle(JptUiMessages.JpaGlobalMetadataConversionWizard_title);
		this.setDefaultPageImageDescriptor(JptJpaUiPlugin.getImageDescriptor(JptUiIcons.JPA_FILE_WIZ_BANNER));
		return;
	}

	@Override
	public void addPages() {
		if( this.jpaProject == null ){
			this.selectJPAProjectWizardPage= new SelectJPAProjectWizardPage(HELP_CONTEXT_ID);
			this.addPage(this.selectJPAProjectWizardPage);
			return;
		}
		setMappingXml();
		addMainPages();
	}

	protected void addMainPages(){
		this.jpaMetadataConversionWizardPage = buildJpaMetadataConversionWizardPage();
		this.addPage(jpaMetadataConversionWizardPage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
	protected PersistenceUnit getPersistenceUnit() {
		PersistenceXml persistenceXml = this.jpaProject.getRootContextNode().getPersistenceXml();
		if (persistenceXml != null) {
			Persistence persistence = persistenceXml.getPersistence();
			if (persistence != null && persistence.getPersistenceUnitsSize() > 0) {
				for (PersistenceUnit pu : persistence.getPersistenceUnits())
					return pu; // return the first persistence unit
			}
		}
		return null;
	}

	public void setJpaProject(JpaProject jpaProject) {
		if (this.jpaProject == null) {
			this.jpaProject = jpaProject;
			IWizardPage currentPage = getContainer().getCurrentPage();
			if (this.selectJPAProjectWizardPage != null && currentPage.equals(this.selectJPAProjectWizardPage)) {
				setMappingXml();
				buildJpaMetadataConversionWizardPage();
			}
		}
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		super.dispose();
	}

	protected void setMappingXml() {
		if (getFirstAvaliableMappingFile() == null) {
			this.mappingXmlModel.setValue(null);
		} else
		if (getDefaultOrmXmlResource() != null) {
			this.mappingXmlModel.setValue(getDefaultMappingFileRuntimPath());
		} else {
			this.mappingXmlModel.setValue(getMappingFileRuntimPath(getFirstAvaliableMappingFile()));
		}
	}

	protected String getMappingFileRuntimPath(MappingFile mappingFile) {
		if (mappingFile != null) {
			IPath resourcePath = mappingFile.getXmlResource().getFile().getFullPath();
			IProject project = jpaProject.getProject();
			@SuppressWarnings("restriction")
			String runtimePath = ResourceLocatorManager.instance().getResourceLocator(project).
								 getRuntimePath(project, resourcePath).toOSString();
			return runtimePath.replace(File.separatorChar, '/');
		}
		return null;
	}

	protected JpaXmlResource getDefaultOrmXmlResource() {
		return jpaProject.getMappingFileXmlResource(new Path(getDefaultMappingFileRuntimPath()));
	}

	protected static Shell getCurrentShell() {
		return Display.getCurrent().getActiveShell();
	}
	
	// ************ abstract methods ***************

	abstract protected JpaJavaGlobalMetadataConversionWizardPage<?> buildJpaMetadataConversionWizardPage();
	
	abstract protected String getDefaultMappingFileRuntimPath();
	
	abstract protected MappingFile getFirstAvaliableMappingFile();
}
