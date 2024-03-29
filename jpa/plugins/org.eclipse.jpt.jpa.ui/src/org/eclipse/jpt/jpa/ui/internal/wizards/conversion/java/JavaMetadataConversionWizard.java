/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.conversion.java;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;

public class JavaMetadataConversionWizard
	extends Wizard
{
	private final JavaMetadataConversionWizardPage page;


	public JavaMetadataConversionWizard(JavaMetadataConversionWizardPage page) {
		super();
		if (page == null) {
			throw new NullPointerException();
		}
		this.page = page;
		this.setWindowTitle(JptJpaUiMessages.JAVA_METADATA_CONVERSION_WIZARD_TITLE);
		this.setDefaultPageImageDescriptor(JptJpaUiImages.JPA_FILE_BANNER);
	}

	@Override
	public void addPages() {
		this.addPage(this.page);
	}

	@Override
	public boolean performFinish() {
		return this.page.performFinish();
	}
}
