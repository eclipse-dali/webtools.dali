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
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMappingDefinition;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.context.orm.EclipseLinkOrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
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

public class AddVirtualAttributeDialog extends StatusDialog {

	private EclipseLinkOrmPersistentType persistentType;
	private OrmPersistentAttribute addedAttribute;
	
	private Button attributeTypeBrowseButton;
	private Button targetTypeBrowserButton;
	
	protected Text nameText;
	protected ComboViewer mappingCombo;
	
	protected Text attributeTypeText;
	protected Text targetTypeText;

	public AddVirtualAttributeDialog(Shell parentShell, EclipseLinkOrmPersistentType persistentType) {
		super(parentShell);
		this.persistentType = persistentType;
		setTitle(JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_TITLE);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);

		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(3, false));

		createLabel(composite, 1, JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_NAME_LABEL);

		this.nameText = createText(composite, 2);
		this.nameText.addModifyListener(getTextModifyListener());

		createLabel(composite, 1, JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_MAPPING_LABEL);

		this.mappingCombo = new ComboViewer(createCombo(composite, 2));
		this.mappingCombo.getCombo().setFocus();
		this.mappingCombo.setContentProvider(this.buildComboContentProvider());
		this.mappingCombo.setLabelProvider(
				new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((MappingUiDefinition) element).getLabel();
					}
				});
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				String mappingKey = AddVirtualAttributeDialog.this.getMappingKey();
				OrmAttributeMappingDefinition mapping = 
						AddVirtualAttributeDialog.this.getAttributeMappingDefinition(mappingKey);
				if (mapping.isSingleRelationshipMapping()) {
					AddVirtualAttributeDialog.this.enableAttributeType(false);
					AddVirtualAttributeDialog.this.enableTargetType(true);
				} else if  (mapping.isCollectionMapping()) {
					AddVirtualAttributeDialog.this.enableAttributeType(true);
					AddVirtualAttributeDialog.this.enableTargetType(true);
				} else {
					AddVirtualAttributeDialog.this.enableAttributeType(true);
					AddVirtualAttributeDialog.this.enableTargetType(false);
				}
				validate();
			}
		});
		this.mappingCombo.setInput(this.getJpaPlatformUi());
		
		createLabel(composite, 1, JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_ATTRIBUTE_TYPE_LABEL);
			
		this.attributeTypeText = createTypeText(composite);
		this.attributeTypeText.addModifyListener(getTextModifyListener());

		this.attributeTypeBrowseButton = createButton(composite, JptJpaUiMessages.General_browse);
		this.attributeTypeBrowseButton.addSelectionListener(addButtonSelectionListener(attributeTypeText));

		createLabel(composite, 1, JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_TARGET_TYPE_LABEL);

		this.targetTypeText = createTypeText(composite);
		this.targetTypeText.addModifyListener(getTextModifyListener());
		this.targetTypeText.setEnabled(false);

		this.targetTypeBrowserButton = createButton(composite, JptJpaUiMessages.General_browse);
		this.targetTypeBrowserButton.addSelectionListener(addButtonSelectionListener(targetTypeText));
		this.targetTypeBrowserButton.setEnabled(false);

		this.nameText.setFocus();

		applyDialogFont(dialogArea);

		validate();

		return dialogArea;
	}

	private ModifyListener getTextModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		};
	}

	private SelectionListener addButtonSelectionListener(final Text text) {
		return new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IType type = chooseType(text.getText());
				if (type != null) {
					text.setText(type.getFullyQualifiedName('$'));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		};
	}

	private void enableAttributeType(boolean enabled) {
		this.attributeTypeText.setEnabled(enabled);
		this.attributeTypeBrowseButton.setEnabled(enabled);
	}

	private void enableTargetType(boolean enabled) {
		this.targetTypeText.setEnabled(enabled);
		this.targetTypeBrowserButton.setEnabled(enabled);
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		return (JpaPlatformUi) this.getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	protected IStructuredContentProvider buildComboContentProvider() {
		return 	new IStructuredContentProvider() {
			public void dispose() {
				//nothing to dispose
			}

			public Object[] getElements(Object inputElement) {
				return ArrayTools.array(
					IterableTools.sort(
						((JpaPlatformUi) inputElement).getAttributeMappingUiDefinitions(AddVirtualAttributeDialog.this.getJptResourceType()),
						getProvidersComparator()));
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				//input will not change
			}
		};
	}

	protected JptResourceType getJptResourceType() {
		return this.persistentType.getResourceType();
	}

	protected OrmAttributeMappingDefinition getAttributeMappingDefinition(String mappingKey) {
		return this.getOrmXmlDefinition().getAttributeMappingDefinition(mappingKey);
	}
	
	// TODO bjv this can perhaps be removed once possible public API 
	// (JpaContextNode.getMappingFileDefinition()?) introduced
	private OrmXmlDefinition getOrmXmlDefinition() {
		return (OrmXmlDefinition) this.getJpaProject().getJpaPlatform().getResourceDefinition(this.getJptResourceType());
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
		text.setLayoutData(getFieldGridData(span));
		return text;
	}

	private Text createTypeText(Composite container) {
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
		
		text.setLayoutData(getFieldGridData(1));
		return text;
	}
	
	protected GridData getFieldGridData(int span) {
		int margin = FieldDecorationRegistry.getDefault()
				.getMaximumDecorationWidth();
		GridData data = new GridData();
		data.horizontalSpan = span;
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
			JptJpaEclipseLinkUiPlugin.instance().logError(ex);
			return null;
		}
	}

	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		combo.setLayoutData(getFieldGridData(span));
		return combo;
	}
	
	private Button createButton(Composite container, String text) {
		Button button = new Button(container, SWT.NONE);
		button.setText(text);
		return button;
	}
	
	protected JpaProject getJpaProject() {
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
		return (selection.isEmpty()) ? null : ((MappingUiDefinition) selection.getFirstElement()).getKey();
	}
	
	public String getTargetType() {
		return this.targetTypeText.getText();
	}

	protected IType chooseType(String type) {
		IJavaElement[] elements= new IJavaElement[] { getJpaProject().getJavaProject() };
		IJavaSearchScope scope= SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		
		SelectionDialog typeSelectionDialog;
		try {
			typeSelectionDialog = 
				JavaUI.createTypeDialog(
						getShell(), service, scope, 
						IJavaElementSearchConstants.CONSIDER_ALL_TYPES, 
						false, type);
		}
		catch (JavaModelException e) {
			JptJpaEclipseLinkUiPlugin.instance().logError(e);
			throw new RuntimeException(e);
		}
		typeSelectionDialog.setTitle(JptJpaUiMessages.AddPersistentClassDialog_classDialog_title); 
		typeSelectionDialog.setMessage(JptJpaUiMessages.AddPersistentClassDialog_classDialog_message); 

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}
		return null;
	}

	private void validate() {
		if (StringTools.isBlank(this.getAttributeName())) {
			updateStatus(JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus(JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_NO_NAME_ERROR));
			return;
		}
		if (this.getMappingKey() == null) {
			updateStatus(JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus(JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_NO_MAPPING_KEY_ERROR));
			return;
		}
		if (!this.getAttributeMappingDefinition(this.getMappingKey()).isSingleRelationshipMapping() && 
				StringTools.isBlank(this.getAttributeType())) {
			updateStatus(JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus(JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_NO_ATTRIBUTE_TYPE_ERROR));
			return;
		}
		if ((this.getAttributeMappingDefinition(this.getMappingKey()).isSingleRelationshipMapping() || 
				this.getAttributeMappingDefinition(this.getMappingKey()).isCollectionMapping()) &&
				StringTools.isBlank(this.getTargetType())) {
			updateStatus(JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus(JptJpaEclipseLinkUiMessages.ADD_VIRTUAL_ATTRIBUTE_DIALOG_NO_TARGET_TYPE_ERROR));
			return;
		}

		updateStatus(Status.OK_STATUS);
	}

	@Override
	protected void okPressed() {
		this.addedAttribute = this.persistentType.addVirtualAttribute(getAttributeName(), getMappingKey(), getAttributeType(), getTargetType());
		super.okPressed();
	}

	public OrmPersistentAttribute openAndReturnAttribute() {
		super.open();
		return this.addedAttribute;
	}
}
