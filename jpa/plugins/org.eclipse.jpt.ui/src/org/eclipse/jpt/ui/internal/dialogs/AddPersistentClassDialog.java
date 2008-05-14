/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.details.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.details.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmEntityUiProvider;
import org.eclipse.jpt.utility.internal.StringTools;
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
		setTitle(JptUiMessages.AddPersistentClassDialog_title);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogArea = (Composite) super.createDialogArea(parent);
		
		Composite composite = new Composite(dialogArea, SWT.NULL);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setLayout(new GridLayout(2, false));
		
		createLabel(composite, 2, JptUiMessages.AddPersistentClassDialog_classLabel);
			
		this.classText = createText(composite, 1);
		this.classText.addModifyListener(
				new ModifyListener() {
					public void modifyText(ModifyEvent e) {
						validate();
					}
				}
			);
		
		this.classBrowseButton = createButton(composite, 1, JptUiMessages.General_browse);
		this.classBrowseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				IType type = chooseType();
				if (type != null) {
					classText.setText(type.getFullyQualifiedName('.'));
				}
			}
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		
		createLabel(composite, 2, JptUiMessages.AddPersistentClassDialog_mappingLabel);
		
		this.mappingCombo = new ComboViewer(createCombo(composite, 2));
		this.mappingCombo.setContentProvider(
			new IStructuredContentProvider() {
				public void dispose() {}
				
				public Object[] getElements(Object inputElement) {
					return new Object[] {
						MappedSuperclassUiProvider.instance(), 
						OrmEntityUiProvider.instance(), 
						EmbeddableUiProvider.instance()
					};
				}
				
				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
			});
		this.mappingCombo.setLabelProvider(
			new LabelProvider() {
				@Override
				public String getText(Object element) {
					return ((TypeMappingUiProvider<?>) element).getLabel();
				}
			});
		this.mappingCombo.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				validate();
			}
		});
		this.mappingCombo.setInput("FOO");
		this.mappingCombo.getCombo().select(1);  // select Entity to begin
		
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
	
	private Button createButton(Composite container, int span, String text) {
		Button button = new Button(container, SWT.NONE);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		return button;
	}
	
	private Combo createCombo(Composite container, int span) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}
	
	private JpaProject getJpaProject() {
		return this.entityMappings.getJpaProject();
	}
	
	public String getClassName() {
		return this.classText.getText();
	}
	
	public String getMappingKey() {
		StructuredSelection selection = (StructuredSelection) this.mappingCombo.getSelection();
		return (selection.isEmpty()) ? null : ((TypeMappingUiProvider<?>) selection.getFirstElement()).getMappingKey();
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
						IJavaElementSearchConstants.CONSIDER_CLASSES, 
						false, getClassName());
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
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
		String className = getClassName();
		
		if (StringTools.stringIsEmpty(className)) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptUiPlugin.PLUGIN_ID, 
					JptUiMessages.AddPersistentClassDialog_noClassError));
			return;
		}
		
		IType type;
		try {
			type = getJpaProject().getJavaProject().findType(className);
		}
		catch (JavaModelException jme) {
			type = null;
		}
		
		if (type == null) {
			updateStatus(
				new Status(
					IStatus.WARNING, JptUiPlugin.PLUGIN_ID,
					JptUiMessages.AddPersistentClassDialog_classNotFoundWarning));
			return;
		}
		
		if (this.entityMappings.containsPersistentType(className)) {
			updateStatus(
				new Status(
					IStatus.WARNING, JptUiPlugin.PLUGIN_ID, 
					JptUiMessages.AddPersistentClassDialog_duplicateClassWarning));
			return;
		}
		
		String mappingKey = getMappingKey();
		if (mappingKey == null) {
			updateStatus(
				new Status(
					IStatus.ERROR, JptUiPlugin.PLUGIN_ID,
					JptUiMessages.AddPersistentClassDialog_noMappingKeyError));
			return;
		}
		
		updateStatus(Status.OK_STATUS);
	}
	
	@Override
	protected void okPressed() {
		this.addedType = this.entityMappings.addOrmPersistentType(getMappingKey(), getClassName());
		super.okPressed();
	}
	
	public OrmPersistentType openAndReturnType() {
		super.open();
		return addedType;
	}
}
