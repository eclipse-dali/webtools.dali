/*******************************************************************************
* Copyright (c) 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.platform.EclipseLinkPlatform;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.PromptJPAProjectWizardPage;

/**
 *  PromptEclipseLinkProjectWizardPage
 */
public class PromptEclipseLinkProjectWizardPage extends PromptJPAProjectWizardPage
{

	public PromptEclipseLinkProjectWizardPage(String helpContextId) {
		super(helpContextId);
	}

	@Override
	protected boolean projectIsValidSelection(JpaProject jpaProject) {
		if(jpaProject == null) {
			return false;
		}
		return jpaProject.getJpaPlatform().getDescription().getGroup().getId().
							equals(EclipseLinkPlatform.GROUP.getId());
	}

}
