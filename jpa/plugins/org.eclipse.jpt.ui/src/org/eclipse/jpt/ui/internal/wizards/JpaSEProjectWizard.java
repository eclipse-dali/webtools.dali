/*******************************************************************************
 *  Copyright (c) 2006, 2010 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.jpt.core.internal.facet.JpaSEProjectCreationDataModelProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.wst.common.frameworks.datamodel.DataModelFactory;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectTemplate;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaSEProjectWizard 
		extends JpaProjectWizard {
	
	public JpaSEProjectWizard() {
		super();
		setWindowTitle(JptUiMessages.JpaSEProjectWizard_title);
	}

	public JpaSEProjectWizard(IDataModel model) {
		super(model);
		setWindowTitle(JptUiMessages.JpaSEProjectWizard_title);
	}
	
	
	@Override
	protected IDataModel createDataModel() {
		return DataModelFactory.createDataModel(new JpaSEProjectCreationDataModelProvider());
	}
	
	
	@Override
	protected IFacetedProjectTemplate getTemplate() {
		return ProjectFacetsManager.getTemplate("jpt.jpa.se.template");
	}
}
