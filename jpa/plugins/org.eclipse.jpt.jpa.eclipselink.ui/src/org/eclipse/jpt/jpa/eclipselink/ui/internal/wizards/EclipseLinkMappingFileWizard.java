/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.common.ui.internal.wizards.NewJptFileWizardPage;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileOptionsWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 * See <code>org.eclipse.jpt.jpa.eclipselink.ui/plugin.xml:org.eclipse.ui.newWizards</code>.
 */
@SuppressWarnings("restriction")
public class EclipseLinkMappingFileWizard
		extends MappingFileWizard {
	
	public EclipseLinkMappingFileWizard() {
		this(null);
	}
	
	public EclipseLinkMappingFileWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(JptJpaEclipseLinkUiMessages.MAPPING_FILE_WIZARD_TITLE);
	}
	
	
	@Override
	protected NewJptFileWizardPage buildMappingFileNewFileWizardPage() {
		return new NewJptFileWizardPage(
				"Page_1", this.mungedSelection, getDataModel(),
				JptJpaEclipseLinkUiMessages.MAPPING_FILE_WIZARD_PAGE_NEW_FILE_TITLE, 
				JptJpaEclipseLinkUiMessages.MAPPING_FILE_WIZARD_PAGE_NEW_FILE_DESC);
	}
	
	@Override
	protected MappingFileOptionsWizardPage buildMappingFileOptionsWizardPage() {
		return new MappingFileOptionsWizardPage(
				"Page_2", getDataModel(),
				JptJpaEclipseLinkUiMessages.MAPPING_FILE_WIZARD_PAGE_OPTIONS_TITLE, 
				JptJpaEclipseLinkUiMessages.MAPPING_FILE_WIZARD_PAGE_OPTIONS_DESC);
	}
	
	public static IPath createNewMappingFile(IStructuredSelection selection, String xmlFileName) {
		EclipseLinkMappingFileWizard wizard = new EclipseLinkMappingFileWizard(
				DataModelFactory.createDataModel(new EclipseLinkOrmFileCreationDataModelProvider()));
		return MappingFileWizard.createMappingFile(selection, xmlFileName, wizard);		
	}

	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new EclipseLinkOrmFileCreationDataModelProvider();
	}
}
