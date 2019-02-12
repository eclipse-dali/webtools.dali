/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.PromptJPAProjectWizardPage;

public class EclipseLinkPromptJpaProjectWizardPage
	extends PromptJPAProjectWizardPage
{
	public EclipseLinkPromptJpaProjectWizardPage(String helpContextId) {
		super(helpContextId);
	}

	@Override
	protected boolean projectIsValidSelection(JpaProject jpaProject) {
		return super.projectIsValidSelection(jpaProject) &&
				jpaProject.getJpaPlatform().getConfig().getGroupConfig().getId().equals(EclipseLinkJpaPlatformFactory.GROUP_ID);
	}
}
