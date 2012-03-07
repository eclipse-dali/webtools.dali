/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.conversion.java;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jpt.jpa.core.JpaProject;

public class EclipseLinkJpaJavaQueryMetadataConversionWizard extends 
	EclipseLinkJpaJavaGlobalMetadataConversionWizard {

	public EclipseLinkJpaJavaQueryMetadataConversionWizard(JpaProject jpaProject) {
		super(jpaProject);
	}

	@Override
	public boolean performFinish() {
		try {
			((EclipseLinkJpaJavaQueryMetadataConversionWizardPage)
					super.jpaMetadataConversionWizardPage).performFinish();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	protected EclipseLinkJpaJavaQueryMetadataConversionWizardPage buildJpaMetadataConversionWizardPage() {
		return	new EclipseLinkJpaJavaQueryMetadataConversionWizardPage(
				this.getPersistenceUnit(), this.mappingXmlModel, HELP_CONTEXT_ID);
	}
}
