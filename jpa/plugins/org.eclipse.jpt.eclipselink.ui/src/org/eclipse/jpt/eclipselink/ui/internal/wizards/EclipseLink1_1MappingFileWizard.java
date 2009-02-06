/*******************************************************************************
 *  Copyright (c) 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.wizards;

import org.eclipse.jpt.eclipselink.core.internal.operations.EclipseLink1_1OrmFileCreationDataModelProvider;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileWizard;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileWizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.datamodel.IDataModelProvider;

/**
 * This is referenced in the plugin.xml as an org.eclipse.ui.newWizards extension
 */
public class EclipseLink1_1MappingFileWizard extends MappingFileWizard 
	implements INewWizard 
{
	public EclipseLink1_1MappingFileWizard() {
		this(null);
	}
	
	public EclipseLink1_1MappingFileWizard(IDataModel dataModel) {
		super(dataModel);
		setWindowTitle(EclipseLinkUiMessages.EclipseLink1_1MappingFileWizard_title);
	}
	
	
	@Override
	protected MappingFileWizardPage buildMappingFileWizardPage() {
		return new EclipseLinkMappingFileWizardPage(getDataModel(), "Page_1", EclipseLinkUiMessages.EclipseLink1_1MappingFileWizardPage_title, EclipseLinkUiMessages.EclipseLink1_1MappingFileWizardPage_desc);
	}
	
	@Override
	protected IDataModelProvider getDefaultProvider() {
		return new EclipseLink1_1OrmFileCreationDataModelProvider();
	}
}
