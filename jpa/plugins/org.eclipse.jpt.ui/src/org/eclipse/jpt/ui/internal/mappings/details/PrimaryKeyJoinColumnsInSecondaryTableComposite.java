/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.mappings.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.mappings.INamedColumn;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PrimaryKeyJoinColumnsInSecondaryTableComposite extends BaseJpaComposite 
{
	private ISecondaryTable secondaryTable;
	private final Adapter secondaryTableListener;
	private final Adapter pkJoinColumnListener;
	
	ListViewer pkJoinColumnsListViewer;

	private Group pkJoinColumnsGroup;
	Button overrideDefaultJoinColumnsCheckBox;
	private Button pkJoinColumnsAddButton;
	private Button pkJoinColumnsRemoveButton;
	private Button pkJoinColumnsEditButton;
	
	
	public PrimaryKeyJoinColumnsInSecondaryTableComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.secondaryTableListener = buildSecondaryTableListener();
		this.pkJoinColumnListener = buildPkJoinColumnListener();
	}
	
	private Adapter buildSecondaryTableListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				secondaryTableChanged(notification);
			}
		};
	}
	
	private Adapter buildPkJoinColumnListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				pkJoinColumnChanged(notification);
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		GridData gridData =  new GridData();
		this.overrideDefaultJoinColumnsCheckBox = getWidgetFactory().createButton(
			composite, 
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns, 
			SWT.CHECK);
		this.overrideDefaultJoinColumnsCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				if (PrimaryKeyJoinColumnsInSecondaryTableComposite.this.overrideDefaultJoinColumnsCheckBox.getSelection()) {
					IPrimaryKeyJoinColumn defaultJoinColumn = PrimaryKeyJoinColumnsInSecondaryTableComposite.this.secondaryTable.getDefaultPrimaryKeyJoinColumns().get(0);
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
					
					IPrimaryKeyJoinColumn pkJoinColumn = PrimaryKeyJoinColumnsInSecondaryTableComposite.this.secondaryTable.createPrimaryKeyJoinColumn(0);
					PrimaryKeyJoinColumnsInSecondaryTableComposite.this.secondaryTable.getSpecifiedPrimaryKeyJoinColumns().add(pkJoinColumn);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				} else {
					PrimaryKeyJoinColumnsInSecondaryTableComposite.this.secondaryTable.getSpecifiedPrimaryKeyJoinColumns().clear();
				}
			}
		});

		this.pkJoinColumnsGroup = 
			getWidgetFactory().createGroup(
				composite, 
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn);
		this.pkJoinColumnsGroup.setLayout(new GridLayout(2, false));
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.pkJoinColumnsGroup.setLayoutData(gridData);
			
		this.pkJoinColumnsListViewer = new ListViewer(this.pkJoinColumnsGroup, SWT.BORDER | SWT.MULTI);
		this.pkJoinColumnsListViewer.setContentProvider(buildJoinColumnsListContentProvider());
		this.pkJoinColumnsListViewer.setLabelProvider(buildJoinColumnsListLabelProvider());
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.verticalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.pkJoinColumnsListViewer.getList().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.pkJoinColumnsListViewer.getList(), IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS);
		
		this.pkJoinColumnsAddButton = new Button(this.pkJoinColumnsGroup, SWT.NONE);
		this.pkJoinColumnsAddButton.setText(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_add);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.pkJoinColumnsAddButton.setLayoutData(gridData);
		this.pkJoinColumnsAddButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				addPrimaryKeyJoinColumn();
			}
		});
		
		this.pkJoinColumnsEditButton = new Button(this.pkJoinColumnsGroup, SWT.NONE);
		this.pkJoinColumnsEditButton.setText(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_edit);
		this.pkJoinColumnsEditButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				editPrimaryKeyJoinColumn();
			}
		});
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.pkJoinColumnsEditButton.setLayoutData(gridData);

		this.pkJoinColumnsRemoveButton = new Button(this.pkJoinColumnsGroup, SWT.NONE);
		this.pkJoinColumnsRemoveButton.setText(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_remove);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		this.pkJoinColumnsRemoveButton.setLayoutData(gridData);
		this.pkJoinColumnsRemoveButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				removePrimaryKeyJoinColumn();
			}
		});
		
		this.pkJoinColumnsListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updatePrimaryKeyJoinColumnsEnablement();
			}
		});
	}
	
	private IContentProvider buildJoinColumnsListContentProvider() {
		return new IStructuredContentProvider(){
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		
			public void dispose() {
				// do nothing
			}
		
			public Object[] getElements(Object inputElement) {
				return ((ISecondaryTable) inputElement).getPrimaryKeyJoinColumns().toArray();
			}
		};
	}
	
	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) element;
				return (PrimaryKeyJoinColumnsInSecondaryTableComposite.this.secondaryTable.containsSpecifiedPrimaryKeyJoinColumns()) ?
					buildJoinColumnLabel(joinColumn)
				:
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}
	
	String buildDefaultJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());				
	}
	
	String buildJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault, joinColumn.getName(),joinColumn.getReferencedColumnName());				
			}
			return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());				
		}
		else {
			return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams, joinColumn.getName(), joinColumn.getReferencedColumnName());					
		}
	}

	
	void addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnInSecondaryTableDialog dialog = new PrimaryKeyJoinColumnInSecondaryTableDialog(this.getControl().getShell(), this.secondaryTable);
		addJoinColumnFromDialog(dialog);
	}
	
	private void addJoinColumnFromDialog(PrimaryKeyJoinColumnInSecondaryTableDialog dialog) {
		if (dialog.open() == Window.OK) {
			int index = this.secondaryTable.getSpecifiedPrimaryKeyJoinColumns().size();
			String name = dialog.getSelectedName();
			String referencedColumnName = dialog.getReferencedColumnName();
			IPrimaryKeyJoinColumn joinColumn = this.secondaryTable.createPrimaryKeyJoinColumn(index);
			this.secondaryTable.getSpecifiedPrimaryKeyJoinColumns().add(joinColumn);
			joinColumn.setSpecifiedName(name);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}
	
	private IPrimaryKeyJoinColumn getSelectedJoinColumn() {
		return (IPrimaryKeyJoinColumn) ((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).getFirstElement();
	}

	void editPrimaryKeyJoinColumn() {
		IPrimaryKeyJoinColumn joinColumn = getSelectedJoinColumn();
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}
	
	private void editJoinColumnFromDialog(PrimaryKeyJoinColumnDialog dialog, IPrimaryKeyJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
	}
	
	private void editJoinColumnDialogOkd(PrimaryKeyJoinColumnDialog dialog, IPrimaryKeyJoinColumn joinColumn) {
		String name = dialog.getSelectedName();
		String referencedColumnName = dialog.getReferencedColumnName();

		if (dialog.isDefaultNameSelected()) {
			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null || !joinColumn.getSpecifiedName().equals(name)){
			joinColumn.setSpecifiedName(name);
		}
		
		if (dialog.isDefaultReferencedColumnNameSelected()) {
			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null || !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)){
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}
	
	void removePrimaryKeyJoinColumn() {
		ISelection selection = this.pkJoinColumnsListViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			for (Iterator i = ((StructuredSelection) selection).iterator(); i.hasNext(); ) {
				this.secondaryTable.getPrimaryKeyJoinColumns().remove(i.next());
			}
		}
	}
	
	void updatePrimaryKeyJoinColumnsEnablement() {
		boolean groupEnabledState = this.secondaryTable.containsSpecifiedPrimaryKeyJoinColumns();
		enableGroup(this.pkJoinColumnsGroup, groupEnabledState);

		this.pkJoinColumnsRemoveButton.setEnabled(groupEnabledState && !((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).isEmpty());
		this.pkJoinColumnsEditButton.setEnabled(groupEnabledState && ((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).size() == 1);
	}
	
	private void enableGroup(Group group, boolean enabled) {
		group.setEnabled(enabled);
		for (int i = 0; i < group.getChildren().length; i++) {
			group.getChildren()[i].setEnabled(enabled);
		}	
	}
	
	
	public void doPopulate(EObject obj) {
		this.secondaryTable = (ISecondaryTable) obj;
		if (this.secondaryTable == null) {
			this.pkJoinColumnsListViewer.setInput(null);
			return;
		}
		
		this.pkJoinColumnsListViewer.setInput(this.secondaryTable);
		

		updatePrimaryKeyJoinColumnsEnablement();
		this.overrideDefaultJoinColumnsCheckBox.setSelection(this.secondaryTable.containsSpecifiedPrimaryKeyJoinColumns());
	}

	@Override
	protected void doPopulate() {
	}

	protected void engageListeners() {
		if (this.secondaryTable != null) {
			this.secondaryTable.eAdapters().add(this.secondaryTableListener);
			for (IPrimaryKeyJoinColumn pkJoinColumn : this.secondaryTable.getPrimaryKeyJoinColumns()) {
				pkJoinColumn.eAdapters().add(this.pkJoinColumnListener);
			}
		}
	}
	
	protected void disengageListeners() {
		if (this.secondaryTable != null) {
			for (IPrimaryKeyJoinColumn pkJoinColumn : this.secondaryTable.getPrimaryKeyJoinColumns()) {
				pkJoinColumn.eAdapters().remove(this.pkJoinColumnListener);
			}
			this.secondaryTable.eAdapters().remove(this.secondaryTableListener);
		}
	}
	
	protected void secondaryTableChanged(Notification notification) {
		if (notification.getFeatureID(ISecondaryTable.class) == JpaCoreMappingsPackage.ISECONDARY_TABLE__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS) {
			if (notification.getEventType() == Notification.ADD) {
				((IPrimaryKeyJoinColumn) notification.getNewValue()).eAdapters().add(this.pkJoinColumnListener);
			}
			else if (notification.getEventType() == Notification.REMOVE) {
				((IPrimaryKeyJoinColumn) notification.getOldValue()).eAdapters().remove(this.pkJoinColumnListener);				
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					pkJoinColumnsListViewer.refresh();
					overrideDefaultJoinColumnsCheckBox.setSelection(secondaryTable.containsSpecifiedPrimaryKeyJoinColumns());
					updatePrimaryKeyJoinColumnsEnablement();
				}
			});
		}
	}

	protected void pkJoinColumnChanged(Notification notification) {
		if (notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME
			|| notification.getFeatureID(IAbstractJoinColumn.class) == JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME
			|| notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME
			|| notification.getFeatureID(IAbstractJoinColumn.class) == JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					pkJoinColumnsListViewer.refresh();
				}
			});
		}
	}
	
	protected void enableWidgets(boolean enabled) {
		this.pkJoinColumnsListViewer.getControl().setEnabled(enabled);
		this.overrideDefaultJoinColumnsCheckBox.setEnabled(enabled);
		this.pkJoinColumnsEditButton.setEnabled(enabled);
		this.pkJoinColumnsRemoveButton.setEnabled(enabled);
		this.pkJoinColumnsAddButton.setEnabled(enabled);
	}

}
