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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;
import com.ibm.icu.text.Collator;

public class AddVirtualAttributeDialog extends StatusDialog
{
	private EclipseLinkOrmPersistentType persistentType;
	private Text nameText;
	private ComboViewer mappingCombo;
	
	private Text attributeTypeText;
	
	private Button attributeTypeBrowseButton;

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
		composite.setLayout(new GridLayout(3, false));

		createLabel(composite, 1, EclipseLinkUiMessages.AddVirtualAttributeDialog_nameLabel);

		this.nameText = createText(composite, 2);
		this.nameText.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validate();
					}
				}
			);

		createLabel(composite, 1, EclipseLinkUiMessages.AddVirtualAttributeDialog_mappingLabel);

		this.mappingCombo = new ComboViewer(createCombo(composite, 2));
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
		
		createLabel(composite, 1, EclipseLinkUiMessages.AddVirtualAttributeDialog_attributeTypeLabel);
			
		this.attributeTypeText = createAttributeTypeText(composite);
		this.attributeTypeText.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validate();
					}
				}
			);
		
		this.attributeTypeBrowseButton = createButton(composite, JptUiMessages.General_browse);
		this.attributeTypeBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IType type = chooseType();
				if (type != null) {
					attributeTypeText.setText(type.getFullyQualifiedName('$'));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

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

	private Text createAttributeTypeText(Composite container) {
		// TODO bug 156185 - when this is fixed there should be api for this
		JavaTypeCompletionProcessor javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(true/*enableBaseTypes*/, false);
		IPackageFragmentRoot pfr = getPackageFragmentRoot();
		if (pfr != null) {
			javaTypeCompletionProcessor.setPackageFragment(pfr.getPackageFragment(""));
		}
		
		Text text = new Text(container, SWT.BORDER | SWT.SINGLE);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaTypeCompletionProcessor
		);
		
		text.setLayoutData(getFieldGridData());
		return text;
	}
	
	protected GridData getFieldGridData() {
		int margin = FieldDecorationRegistry.getDefault()
				.getMaximumDecorationWidth();
		GridData data = new GridData();
		data.horizontalAlignment = SWT.FILL;
		data.widthHint = IDialogConstants.ENTRY_FIELD_WIDTH + margin;
		data.horizontalIndent = margin;
		data.grabExcessHorizontalSpace = true;
		return data;
	}

	protected IPackageFragmentRoot getPackageFragmentRoot() {
		try {
			return this.getJpaProject().getJavaProject().getPackageFragmentRoots()[0];
		} catch (JavaModelException ex) {
			JptJpaEclipseLinkUiPlugin.log(ex);
			return null;
		}
	}

	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}
	
	private Button createButton(Composite container, String text) {
		Button button = new Button(container, SWT.NONE);
		button.setText(text);
		return button;
	}
	
	private JpaProject getJpaProject() {
		return this.persistentType.getJpaProject();
	}
	
	public String getAttributeType() {
		return this.attributeTypeText.getText();
	}

	public String getAttributeName() {
		return this.nameText.getText();
	}

	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) this.mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((MappingUiDefinition<?,?>) selection.getFirstElement()).getKey();
	}
	
	protected IType chooseType() {
		IJavaElement[] elements= new IJavaElement[] { getJpaProject().getJavaProject() };
		IJavaSearchScope scope= SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		
		SelectionDialog typeSelectionDialog;
		try {
			typeSelectionDialog = 
				JavaUI.createTypeDialog(
						getShell(), service, scope, 
						IJavaElementSearchConstants.CONSIDER_ALL_TYPES, 
						false, getAttributeType());
		}
		catch (JavaModelException e) {
			JptJpaEclipseLinkUiPlugin.log(e);
			throw new RuntimeException(e);
		}
		typeSelectionDialog.setTitle(JptUiMessages.AddPersistentClassDialog_classDialog_title); 
		typeSelectionDialog.setMessage(JptUiMessages.AddPersistentClassDialog_classDialog_message); 

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
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
		if (StringTools.stringIsEmpty(this.getAttributeType())) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptJpaEclipseLinkUiPlugin.PLUGIN_ID,
					EclipseLinkUiMessages.AddVirtualAttributeDialog_noAttributeTypeError));
			return;
		}

		updateStatus(Status.OK_STATUS);
	}

	
	@Override
	protected void okPressed() {
		this.addedAttribute = this.persistentType.addVirtualAttribute(getAttributeName(), getMappingKey(), getAttributeType());
		super.okPressed();
	}

	public OrmPersistentAttribute openAndReturnAttribute() {
		super.open();
		return this.addedAttribute;
	}
}
