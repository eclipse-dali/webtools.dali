/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.jpt.jpa.core.JpaProject;

public class GenericJpaJavaGeneratorMetadataConversionWizard extends
	GenericJpaJavaGlobalMetadataConversionWizard {

	public GenericJpaJavaGeneratorMetadataConversionWizard(JpaProject jpaProject) {
		super(jpaProject);
	}

	@Override
	protected GenericJpaJavaGeneratorMetadataConversionWizardPage buildJpaMetadataConversionWizardPage() {
		return new GenericJpaJavaGeneratorMetadataConversionWizardPage(
				this.getPersistenceUnit(), this.mappingXmlModel, HELP_CONTEXT_ID);
	}

	@Override
	public boolean performFinish() {
		try {
			((GenericJpaJavaGeneratorMetadataConversionWizardPage)
					super.jpaMetadataConversionWizardPage).performFinish();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return true;
	}
}
