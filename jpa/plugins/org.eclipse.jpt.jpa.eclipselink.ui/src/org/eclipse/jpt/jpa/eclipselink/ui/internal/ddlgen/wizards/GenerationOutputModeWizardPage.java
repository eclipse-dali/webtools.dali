/*******************************************************************************
* Copyright (c) 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.ddlgen.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOutputMode;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 *  GenerationOutputModeWizardPage
 */
public class GenerationOutputModeWizardPage extends WizardPage {

	GenerationOutputModeGroup outputModeGroup;
	
	public GenerationOutputModeWizardPage() {
		super("Database Schema"); //$NON-NLS-1$
		this.setTitle(JptJpaEclipseLinkUiMessages.GENERATION_OUTPUT_MODE_WIZARD_PAGE_TITLE);
		this.setMessage(JptJpaEclipseLinkUiMessages.GENERATION_OUTPUT_MODE_WIZARD_PAGE_DESC);
	}

	public void createControl(Composite parent) {
		this.setPageComplete(true);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		this.outputModeGroup = new GenerationOutputModeGroup(composite);
		Dialog.applyDialogFont(parent);
		return composite;
	}

	// ********** intra-wizard methods **********

	public EclipseLinkOutputMode getOutputMode() {
		return this.outputModeGroup.getOutputMode();
	}


	// ********** Generation OutputMode Group **********

	class GenerationOutputModeGroup {
		private EclipseLinkOutputMode outputMode;
		
		private final Button databaseButton;
		private final Button sqlScriptButton;
		private final Button bothButton;
		
		// ********** constructor **********

		private GenerationOutputModeGroup(Composite parent) {
			super();
			Group outputModeGroup = new Group(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			outputModeGroup.setLayout(layout);
			outputModeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			outputModeGroup.setText(JptJpaEclipseLinkUiMessages.GENERATION_OUTPUT_MODE_WIZARD_PAGE_GENERATION_OUTPUT_MODE);
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			SelectionListener outputModeButtonListener = this.buildOutputModeRadioButtonListener();

			this.databaseButton = this.buildRadioButton(outputModeGroup, 
				JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_DATABASE, outputModeButtonListener, 3);
			
			this.sqlScriptButton = this.buildRadioButton(outputModeGroup, 
				JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_SQL_SCRIPT, outputModeButtonListener, 3);
			
			this.bothButton = this.buildRadioButton(outputModeGroup, 
				JptJpaEclipseLinkUiMessages.OUTPUT_MODE_COMPOSITE_BOTH, outputModeButtonListener, 3);

			this.databaseButton.setSelection(true);
			this.outputModeButtonChanged();
		}

		// ********** listeners **********
		
		private SelectionListener buildOutputModeRadioButtonListener() {
			return new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent event) {
					this.widgetSelected(event);
				}
				public void widgetSelected(SelectionEvent event) {
					outputModeButtonChanged();
				}
			};
		}
		
		// ********** internal methods **********

		private void outputModeButtonChanged() {

			if(this.databaseButton.getSelection()) {
				this.outputMode = EclipseLinkOutputMode.database;
			}
			else if(this.sqlScriptButton.getSelection()) {
				this.outputMode = EclipseLinkOutputMode.sql_script;
			}
			else if(this.bothButton.getSelection()) {
				this.outputMode = EclipseLinkOutputMode.both;
			}
		}

		private Button buildRadioButton(Composite parent, String text, SelectionListener listener, int horizontalSpan) {
			Button radioButton = new Button(parent, SWT.RADIO);
			GridData gridData = new GridData();
			gridData.horizontalSpan = horizontalSpan;
			radioButton.setLayoutData(gridData);
			radioButton.setText(text);
			radioButton.addSelectionListener(listener);
			return radioButton;
		}

		private EclipseLinkOutputMode getOutputMode() {
			return this.outputMode;
		}
	}
}
