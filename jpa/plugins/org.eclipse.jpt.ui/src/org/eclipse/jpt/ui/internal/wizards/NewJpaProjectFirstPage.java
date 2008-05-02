/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jst.j2ee.ui.project.facet.UtilityProjectFirstPage;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class NewJpaProjectFirstPage extends UtilityProjectFirstPage 
{
	public NewJpaProjectFirstPage(IDataModel dataModel, String pageName) {
		super(dataModel, pageName);
		setTitle(JptUiMessages.NewJpaProjectWizard_firstPage_title);
		setDescription(JptUiMessages.NewJpaProjectWizard_firstPage_description);
		setInfopopID(JpaHelpContextIds.NEW_JPA_PROJECT);
	}
}
