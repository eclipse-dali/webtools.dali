/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.proj;

import static org.eclipse.jpt.jpa.ui.internal.wizards.proj.model.JpaProjectCreationDataModelProperties.*;
import static org.eclipse.wst.common.componentcore.datamodel.properties.IFacetProjectCreationDataModelProperties.FACET_RUNTIME;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jst.j2ee.ui.project.facet.EarProjectWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;
import org.eclipse.wst.common.frameworks.internal.datamodel.ui.DataModelSynchHelper;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class AddToEarComposite {
	
	private final Button addToEar;
	private final Combo combo;
	private final Button newButton;
	private final Label label;
	
	private final IDataModel model;
	private DataModelSynchHelper synchhelper;
	
	public AddToEarComposite(IDataModel model, Composite parent) {
		this.model = model;
		this.synchhelper = new DataModelSynchHelper(model);
		
		Group group = new Group(parent, SWT.NONE);
		group.setLayoutData(gdhfill());
		group.setLayout(new GridLayout(3, false));
		group.setText(JptUiMessages.AddToEarComposite_earMemberShip);
		
		this.addToEar = new Button(group, SWT.CHECK);
		this.addToEar.setText(JptUiMessages.AddToEarComposite_addToEarLabel);
		GridDataFactory.defaultsFor(this.addToEar).span(3, 1).applyTo(this.addToEar);
		this.synchhelper.synchCheckbox(addToEar, ADD_TO_EAR, null);
		
		this.label = new Label(group, SWT.NULL);
		this.label.setText(JptUiMessages.AddToEarComposite_earProjectLabel);
		this.combo = new Combo(group, SWT.NONE);
		this.combo.setLayoutData(gdhfill());
		
		this.newButton = new Button(group, SWT.PUSH);
		this.newButton.setText(JptUiMessages.AddToEarComposite_newButtonLabel);
		GridDataFactory.defaultsFor(this.newButton).applyTo(this.newButton);
		
		this.newButton.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent event) {
					handleAddButton();
				}
			});
		
		this.synchhelper.synchCombo(combo, EAR_PROJECT_NAME, new Control[]{label, newButton});
		Dialog.applyDialogFont(parent);
	}
	
	private void handleAddButton() {
		EarProjectWizard wizard = new EarProjectWizard();
		
		WizardDialog dialog = new WizardDialog(newButton.getShell(), wizard);
		
		IRuntime runtime = (IRuntime) this.model.getProperty(FACET_RUNTIME);
		wizard.setRuntimeInDataModel(runtime);
		
		if (dialog.open() != Window.CANCEL) {
			this.model.notifyPropertyChange(EAR_PROJECT_NAME, IDataModel.VALID_VALUES_CHG);
			String earproj = wizard.getProjectName();
			this.model.setProperty(EAR_PROJECT_NAME, earproj);
		}
	}
	
	private static GridData gdhfill() {
		return new GridData(GridData.FILL_HORIZONTAL);
	}
	
	public static final GridData gdhspan(GridData gd, int span) {
		gd.horizontalSpan = span;
		return gd;
	}
	
	public void dispose() {
		if (this.synchhelper != null){
			this.synchhelper.dispose();
			this.synchhelper = null;
		}
	}
	
	public String getComboText(){
		return this.combo.getText();
	}
}
