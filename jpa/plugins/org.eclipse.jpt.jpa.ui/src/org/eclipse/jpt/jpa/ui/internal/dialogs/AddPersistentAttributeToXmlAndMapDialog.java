/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.dialogs;

import java.util.Comparator;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.ibm.icu.text.Collator;

public class AddPersistentAttributeToXmlAndMapDialog extends StatusDialog
{
	private OrmPersistentAttribute unmappedPersistentAttribute;
	private Text attributeText;
	private ComboViewer mappingCombo;

	public AddPersistentAttributeToXmlAndMapDialog(Shell parentShell, OrmPersistentAttribute unmappedPersistentAttribute) {
		super(parentShell);
		this.unmappedPersistentAttribute = unmappedPersistentAttribute;
		setTitle(JptJpaUiMessages.ADD_PERSISTENT_ATTRIBUTE_DIALOG_TITLE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(2, false));

		createLabel(composite, 1, JptJpaUiMessages.ADD_PERSISTENT_ATTRIBUTE_DIALOG_ATTRIBUTE_LABEL);

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

		createLabel(composite, 1, JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_MAPPING_LABEL);

		mappingCombo = new ComboViewer(createCombo(composite, 1));
		mappingCombo.getCombo().setFocus();
		mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {}

				public Object[] getElements(Object inputElement) {
					return ArrayTools.array(
						IterableTools.sort(
							((JpaPlatformUi) inputElement).getAttributeMappingUiDefinitions(unmappedPersistentAttribute), 
							getProvidersComparator()));
				}
				
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
			});
		mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((MappingUiDefinition) element).getLabel();
				}
			});
		mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		JpaPlatformUi jpaPlatformUi = (JpaPlatformUi) this.unmappedPersistentAttribute.getJpaPlatform().getAdapter(JpaPlatformUi.class);
		mappingCombo.setInput(jpaPlatformUi);
		mappingCombo.getCombo().select(0);  // select Basic to begin

		// TODO - F1 Help
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);

		//getButton(IDialogConstants.OK_ID).setEnabled(false);  // disabled to start
		applyDialogFont(dialogArea);

		validate();

		return dialogArea;
	}
	
	protected Comparator<MappingUiDefinition> getProvidersComparator() {
		return new Comparator<MappingUiDefinition>() {
			public int compare(MappingUiDefinition item1, MappingUiDefinition item2) {
				String displayString1 = item1.getLabel();
				String displayString2 = item2.getLabel();
				return Collator.getInstance().compare(displayString1, displayString2);
			}
		};
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
		return (selection.isEmpty()) ? null : ((MappingUiDefinition) selection.getFirstElement()).getKey();
	}

	private void validate() {
//		if (entityMappings.containsPersistentType(type)) {
//			updateStatus(
//				new Status(
//					IStatus.WARNING, JptJpaUiPlugin.PLUGIN_ID,
//					JptUiMessages.ADD_PERSISTENT_CLASS_DIALOG_duplicateClassWarning));
//			return;
//		}
//
		String mappingKey = getMappingKey();
		if (mappingKey == null) {
			updateStatus(JptJpaUiPlugin.instance().buildErrorStatus(JptJpaUiMessages.ADD_PERSISTENT_ATTRIBUTE_DIALOG_NO_MAPPING_KEY_ERROR));
			return;
		}

		updateStatus(Status.OK_STATUS);
	}

	@Override
	protected void okPressed() {
		this.unmappedPersistentAttribute.addToXml(getMappingKey());
		super.okPressed();
	}
}
