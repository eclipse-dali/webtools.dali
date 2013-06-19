/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.dialogs;

import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
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

public class AddPersistentClassDialog extends StatusDialog
{
	private EntityMappings entityMappings;
	
	private Text classText;
	
	private Button classBrowseButton;
	
	private ComboViewer mappingCombo;
	
	private OrmPersistentType addedType;
		
	
	public AddPersistentClassDialog(Shell parentShell, EntityMappings entityMappings) {
		super(parentShell);
		this.entityMappings = entityMappings;
		setTitle(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_TITLE);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		
		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(3, false));
		
		createLabel(composite, JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_CLASS_LABEL);
			
		this.classText = createText(composite);
		this.classText.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validate();
					}
				}
			);
		
		this.classBrowseButton = createButton(composite, JptJpaUiMessages.GENERAL_BROWSE);
		this.classBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IType type = chooseType();
				if (type != null) {
					classText.setText(type.getFullyQualifiedName('$'));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		createLabel(composite, JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_MAPPING_LABEL);
		
		this.mappingCombo = new ComboViewer(createCombo(composite, 2));
		this.mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {
					//nothing to dispose
				}
				
				public Object[] getElements(Object inputElement) {
					return ArrayTools.array(getTypeMappingUiDefinitions((EntityMappings) inputElement));
				}

				protected Iterable<MappingUiDefinition> getTypeMappingUiDefinitions(EntityMappings entityMappings) {
					JpaPlatformUi ui = getJpaPlatformUi();
					return (ui != null) ? ui.getTypeMappingUiDefinitions(entityMappings.getResourceType()) : IterableTools.<MappingUiDefinition>emptyIterable();
				}

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					//NOP
				}
			});
		this.mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((MappingUiDefinition) element).getLabel();
				}
			});
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		this.mappingCombo.setInput(this.entityMappings);
		//TODO maybe use JpaPlatformUi.getDefaultTypeMappingDefintion() to do this? currently that returns null for orm.xml
		this.mappingCombo.getCombo().select(0);  // select Entity to begin 
		
		// TODO - F1 Help
		// PlatformUI.getWorkbench().getHelpSystem().setHelp(group, IDaliHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_DATABASE);
		
		//getButton(IDialogConstants.OK_ID).setEnabled(false);  // disabled to start
		applyDialogFont(dialogArea);		
		
		validate();
		
		return dialogArea;
	}
	
	private Label createLabel(Composite container, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		return label;
	}
	
	private Text createText(Composite container) {
		// TODO bug 156185 - when this is fixed there should be api for this
		JavaTypeCompletionProcessor javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(false, false);
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
			JptJpaUiPlugin.instance().logError(ex);
			return null;
		}
	}

	
	private Button createButton(Composite container, String text) {
		Button button = new Button(container, SWT.NONE);
		button.setText(text);
		return button;
	}
	
	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = getFieldGridData();
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		return (JpaPlatformUi) this.entityMappings.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}
	
	private JpaProject getJpaProject() {
		return this.entityMappings.getJpaProject();
	}
	
	public String getClassName() {
		return this.classText.getText();
	}
	
	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) this.mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((MappingUiDefinition) selection.getFirstElement()).getKey();
	}
	
	protected IType chooseType() {
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(
					getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createJavaSearchScope(new IJavaElement[] { getJpaProject().getJavaProject() }),
					IJavaElementSearchConstants.CONSIDER_CLASSES,
					false,
					getClassName()
				);
		} catch (JavaModelException ex) {
			JptJpaUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_CLASS_DIALOG_TITLE); 
		dialog.setMessage(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_CLASS_DIALOG_MESSAGE); 

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
	}
	
	private void validate() {
		String className = getClassName();
		
		if (StringTools.isBlank(className)) {
			updateStatus(JptJpaUiPlugin.instance().buildErrorStatus(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_NO_CLASS_ERROR));
			return;
		}
		className = className.replace('$', '.');
		
		IType type;
		try {
			type = getJpaProject().getJavaProject().findType(className);
		}
		catch (JavaModelException jme) {
			type = null;
		}
		
		if (type == null) {
			updateStatus(JptJpaUiPlugin.instance().buildWarningStatus(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_CLASS_NOT_FOUND_WARNING));
			return;
		}
		
		if (this.entityMappings.containsManagedType(className)) {
			updateStatus(JptJpaUiPlugin.instance().buildWarningStatus(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_DUPLICATE_CLASS_WARNING));
			return;
		}
		
		String mappingKey = getMappingKey();
		if (mappingKey == null) {
			updateStatus(JptJpaUiPlugin.instance().buildErrorStatus(JptJpaUiMessages.ADD_PERSISTENT_CLASS_DIALOG_NO_MAPPING_KEY_ERROR));
			return;
		}
		
		updateStatus(Status.OK_STATUS);
	}
	
	@Override
	protected void okPressed() {
		this.addedType = this.entityMappings.addPersistentType(getMappingKey(), getClassName());
		super.okPressed();
	}
	
	public OrmPersistentType openAndReturnType() {
		super.open();
		return this.addedType;
	}
}
