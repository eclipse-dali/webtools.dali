/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.dialogs;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddPersistentAttributeToXmlAndMapDialog extends StatusDialog
{
	private OrmPersistentAttribute unmappedPersistentAttribute;
	private Text attributeText;
	private ComboViewer mappingCombo;

	public AddPersistentAttributeToXmlAndMapDialog(Shell parentShell, OrmPersistentAttribute unmappedPersistentAttribute) {
		super(parentShell);
		this.unmappedPersistentAttribute = unmappedPersistentAttribute;
		setTitle(JptUiMessages.AddPersistentAttributeDialog_title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout());

		createLabel(composite, 1, JptUiMessages.AddPersistentAttributeDialog_attributeLabel);

		attributeText = createText(composite, 1);
//		attributeText.addModifyListener(
//				new ModifyListener() {
//					public void modifyText(ModifyEvent e) {
//						validate();
//					}
//				}
//			);
		attributeText.setText(unmappedPersistentAttribute.getName());
		attributeText.setEditable(false);

		createLabel(composite, 1, JptUiMessages.AddPersistentClassDialog_mappingLabel);

		mappingCombo = new ComboViewer(createCombo(composite, 1));
		mappingCombo.getCombo().setFocus();
		mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {}

				public Object[] getElements(Object inputElement) {
					return CollectionTools.array(((JpaPlatformUi) inputElement).attributeMappingUiProviders(unmappedPersistentAttribute));
				}
				
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
			});
		mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((AttributeMappingUiProvider) element).getLabel();
				}
			});
		mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		JpaPlatformUi jpaPlatformUi = JptUiPlugin.getPlugin().jpaPlatformUi(this.unmappedPersistentAttribute.getJpaProject().getJpaPlatform());
		mappingCombo.setInput(jpaPlatformUi);
		mappingCombo.getCombo().select(0);  // select Basic to begin

		// TODO - F1 Help
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);

		//getButton(IDialogConstants.OK_ID).setEnabled(false);  // disabled to start
		applyDialogFont(dialogArea);

		validate();

		return dialogArea;
	}

	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}

	private Text createText(Composite container, int span) {
		Text text = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		gd.widthHint = 250;
		text.setLayoutData(gd);
		return text;
	}

	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}

	public String getAttributeName() {
		return attributeText.getText();
	}

	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((AttributeMappingUiProvider) selection.getFirstElement()).getMappingKey();
	}

	private void validate() {
//		if (entityMappings.containsPersistentType(type)) {
//			updateStatus(
//				new Status(
//					IStatus.WARNING, JptUiPlugin.PLUGIN_ID,
//					JptUiMessages.AddPersistentClassDialog_duplicateClassWarning));
//			return;
//		}
//
		String mappingKey = getMappingKey();
		if (mappingKey == null) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptUiPlugin.PLUGIN_ID,
					JptUiMessages.AddPersistentAttributeDialog_noMappingKeyError));
			return;
		}

		updateStatus(Status.OK_STATUS);
	}

	@Override
	protected void okPressed() {
		unmappedPersistentAttribute.makeSpecified(getMappingKey());
		super.okPressed();
	}
}
