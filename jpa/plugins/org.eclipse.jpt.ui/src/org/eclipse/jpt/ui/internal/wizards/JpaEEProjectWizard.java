/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.jpt.core.internal.facet.JpaEEProjectCreationDataModelProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaEEProjectWizard extends JpaProjectWizard {
	
	public JpaEEProjectWizard() {
		super();
		setWindowTitle(JptUiMessages.JpaEEProjectWizard_title);
	}

	public JpaEEProjectWizard(IDataModel model) {
		super(model);
		setWindowTitle(JptUiMessages.JpaEEProjectWizard_title);
	}
	
	
	@Override
	protected IDataModel createDataModel() {
		return DataModelFactory.createDataModel(new JpaEEProjectCreationDataModelProvider());
	}
	
	
	@Override
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jpa.ee.template");
	}
}
