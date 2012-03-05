/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.dialogs;

import java.util.Comparator;
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
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import com.ibm.icu.text.Collator;

//TODO widget for attribute type
public class AddVirtualAttributeDialog extends StatusDialog
{
	private EclipseLinkOrmPersistentType persistentType;
	private Text nameText;
	private ComboViewer mappingCombo;

	private OrmPersistentAttribute addedAttribute;

	public AddVirtualAttributeDialog(Shell parentShell, EclipseLinkOrmPersistentType persistentType) {
		super(parentShell);
		this.persistentType = persistentType;
		setTitle(EclipseLinkUiMessages.AddVirtualAttributeDialog_title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(2, false));

		createLabel(composite, 1, EclipseLinkUiMessages.AddVirtualAttributeDialog_nameLabel);

		this.nameText = createText(composite, 1);
		this.nameText.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validate();
					}
				}
			);

		createLabel(composite, 1, EclipseLinkUiMessages.AddVirtualAttributeDialog_mappingLabel);

		this.mappingCombo = new ComboViewer(createCombo(composite, 1));
		this.mappingCombo.getCombo().setFocus();
		this.mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {
					//nothing to dispose
				}

				public Object[] getElements(Object inputElement) {
					return ArrayTools.array(
						CollectionTools.sort(
							((JpaPlatformUi) inputElement).attributeMappingUiDefinitions(AddVirtualAttributeDialog.this.persistentType.getResourceType()),
							getProvidersComparator()));
				}

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					//input will not change
				}
			});
		this.mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((MappingUiDefinition<?,?>) element).getLabel();
				}
			});
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		JpaPlatformUi jpaPlatformUi = (JpaPlatformUi) this.persistentType.getJpaPlatform().getAdapter(JpaPlatformUi.class);
		this.mappingCombo.setInput(jpaPlatformUi);
		this.mappingCombo.getCombo().select(0);  // select Basic to begin
		this.nameText.setFocus();

		applyDialogFont(dialogArea);

		validate();

		return dialogArea;
	}

	protected Comparator<MappingUiDefinition<?,?>> getProvidersComparator() {
		return new Comparator<MappingUiDefinition<?,?>>() {
			public int compare(MappingUiDefinition<?,?> item1, MappingUiDefinition<?,?> item2) {
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
		return this.nameText.getText();
	}

	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) this.mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((MappingUiDefinition<?,?>) selection.getFirstElement()).getKey();
	}

	private void validate() {
		if (StringTools.stringIsEmpty(this.getAttributeName())) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
					EclipseLinkUiMessages.AddVirtualAttributeDialog_noNameError));
			return;
		}
		if (this.getMappingKey() == null) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
					EclipseLinkUiMessages.AddVirtualAttributeDialog_noMappingKeyError));
			return;
		}

		updateStatus(Status.OK_STATUS);
	}

	
	@Override
	protected void okPressed() {
		this.addedAttribute = this.persistentType.addVirtualAttribute(getAttributeName(), getMappingKey(), "String");
		super.okPressed();
	}

	public OrmPersistentAttribute openAndReturnAttribute() {
		super.open();
		return this.addedAttribute;
	}
}
