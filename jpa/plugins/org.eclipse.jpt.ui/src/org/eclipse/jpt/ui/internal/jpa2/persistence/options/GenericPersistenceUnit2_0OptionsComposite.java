/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2.persistence.options;

import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.ui.internal.jpa2.Jpt2_0UiMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 *  GenericPersistenceUnit2_0OptionsComposite
 */
public class GenericPersistenceUnit2_0OptionsComposite extends FormPane<JpaOptions2_0>
{
	public GenericPersistenceUnit2_0OptionsComposite(
		FormPane<JpaOptions2_0> subjectHolder,
		Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		this.initializeMiscellaneousPane(parent);
	}

	private void initializeMiscellaneousPane(Composite container) {	
		
		this.updateGridData(container);
		this.updateGridData(container.getParent());
		
		Composite composite = this.addSection(container, 
			Jpt2_0UiMessages.GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionTitle,
			Jpt2_0UiMessages.GenericPersistenceUnit2_0OptionsComposite_miscellaneousSectionDescription);
		
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());

		new LockingConfigurationComposite(this, composite);
		new QueryConfigurationComposite(this, composite);
		new ValidationConfigurationComposite(this, composite);
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}

}
