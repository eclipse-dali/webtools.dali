/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.proj;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jpt.jpa.core.internal.facet.BasicJpaEEConfigurationPresetFactory;
import org.eclipse.jpt.jpa.core.internal.facet.BasicJpaSEConfigurationPresetFactory;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.perspective.JpaPerspectiveFactory;
import org.eclipse.jpt.jpa.ui.internal.wizards.proj.model.JpaProjectCreationDataModelProvider;
import org.eclipse.jst.j2ee.internal.plugin.JavaEEPreferencesInitializer;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectWorkingCopy;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;
import org.eclipse.wst.web.ui.internal.wizards.NewProjectDataModelFacetWizard;

public class JpaProjectWizard
		extends NewProjectDataModelFacetWizard {
	
	public JpaProjectWizard() {
		super();
		setWindowTitle(JptJpaUiMessages.JPA_PROJECT_WIZARD_TITLE);
	}
	
	public JpaProjectWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptJpaUiMessages.JPA_PROJECT_WIZARD_TITLE);
	}
	
	
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return JptJpaUiImages.JPA_PROJECT_BANNER;
	}
	
	@Override
	protected IWizardPage createFirstPage() {
		return new JpaProjectWizardFirstPage(this.model, "first.page"); //$NON-NLS-1$ 
	}
	
	@Override
	protected IDataModel createDataModel() {
		return DataModelFactory.createDataModel(new JpaProjectCreationDataModelProvider());
	}
	
	@Override
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jpa.template"); //$NON-NLS-1$
	}
	
	@Override
	protected String getFinalPerspectiveID() {
		return JpaPerspectiveFactory.ID;
	}
	
	@Override
	public void createPageControls(Composite container) {
		super.createPageControls(container);
		
		IFacetedProjectWorkingCopy pfwc = getFacetedProjectWorkingCopy();
		IRuntime runtime = pfwc.getPrimaryRuntime();
		if (JavaEEPreferencesInitializer.getDefaultBoolean(JavaEEPreferencesInitializer.Keys.ADD_TO_EAR_BY_DEFAULT)) {
			try {
				if (runtime == null || IJ2EEFacetConstants.UTILITY_FACET.getLatestSupportedVersion(runtime) != null) {
					pfwc.setSelectedPreset(BasicJpaEEConfigurationPresetFactory.PRESET_ID);
					return;
				}
			}
			catch (CoreException ce) {
				// fall through
			}
		}
		
		pfwc.setSelectedPreset(BasicJpaSEConfigurationPresetFactory.PRESET_ID);
	}
}
