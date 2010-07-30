/*******************************************************************************
 *  Copyright (c) 2008, 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.wizards;

import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLinkOrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileNewFileWizardPage;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileOptionsWizardPage;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 * This is referenced in the plugin.xml as an org.eclipse.ui.newWizards extension
 */
public class EclipseLinkMappingFileWizard
		extends MappingFileWizard {
	
	public EclipseLinkMappingFileWizard() {
		this(null);
	}
	
	public EclipseLinkMappingFileWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(EclipseLinkUiMessages.MappingFileWizard_title);
	}
	
	
	@Override
	protected MappingFileNewFileWizardPage buildMappingFileNewFileWizardPage() {
		return new MappingFileNewFileWizardPage(
				"Page_1", this.mungedSelection, getDataModel(),
				EclipseLinkUiMessages.MappingFileWizardPage_newFile_title, 
				EclipseLinkUiMessages.MappingFileWizardPage_newFile_desc);
	}
	
	@Override
	protected MappingFileOptionsWizardPage buildMappingFileOptionsWizardPage() {
		return new MappingFileOptionsWizardPage(
				"Page_2", getDataModel(),
				EclipseLinkUiMessages.MappingFileWizardPage_options_title, 
				EclipseLinkUiMessages.MappingFileWizardPage_options_desc);
	}
	
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new EclipseLinkOrmFileCreationDataModelProvider();
	}
}
