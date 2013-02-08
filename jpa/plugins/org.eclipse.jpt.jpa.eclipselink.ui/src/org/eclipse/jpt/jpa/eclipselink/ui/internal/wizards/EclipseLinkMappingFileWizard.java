/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.jpa.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileNewFileWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileOptionsWizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 * This is referenced in the plugin.xml as an org.eclipse.ui.newWizards extension
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
	protected MappingFileNewFileWizardPage buildMappingFileNewFileWizardPage() {
		return new MappingFileNewFileWizardPage(
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
