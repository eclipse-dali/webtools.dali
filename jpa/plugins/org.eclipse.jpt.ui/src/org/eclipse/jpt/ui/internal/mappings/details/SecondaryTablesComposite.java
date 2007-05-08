/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO need to support a list of tables
public class SecondaryTablesComposite extends BaseJpaComposite 
{
	private IEntity entity;
	private final Adapter entityListener;
	private final Adapter secondaryTableListener;
	
	ListViewer secondaryTablesListViewer;

	private Button addButton;
	private Button editButton;
	private Button removeButton;
	
	
	public SecondaryTablesComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
		this.entityListener = buildEntityListener();
		this.secondaryTableListener = buildSecondaryTableListener();
	}
	
	private Adapter buildEntityListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				entityChanged(notification);
			}
		};
	}
	
	private Adapter buildSecondaryTableListener() {
		return new AdapterImpl() {
			public void notifyChanged(Notification notification) {
				seoncaryTableChanged(notification);
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		GridData gridData =  new GridData();
			
		this.secondaryTablesListViewer = new ListViewer(composite, SWT.BORDER | SWT.MULTI);
		this.secondaryTablesListViewer.setContentProvider(buildSecondaryTablesListContentProvider());
		this.secondaryTablesListViewer.setLabelProvider(buildSecondaryTablesListLabelProvider());
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.verticalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.secondaryTablesListViewer.getList().setLayoutData(gridData);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(this.secondaryTablesListViewer.getList(), IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS);
		
		this.addButton = new Button(composite, SWT.NONE);
		this.addButton.setText(JptUiMappingsMessages.SecondaryTablesComposite_add);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.addButton.setLayoutData(gridData);
		this.addButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				addSecondaryTable();
			}
		});
		
		this.editButton = new Button(composite, SWT.NONE);
		this.editButton.setText(JptUiMappingsMessages.SecondaryTablesComposite_edit);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.editButton.setLayoutData(gridData);
		this.editButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				editSecondaryTable();
			}
		});

		this.removeButton = new Button(composite, SWT.NONE);
		this.removeButton.setText(JptUiMappingsMessages.SecondaryTablesComposite_remove);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		this.removeButton.setLayoutData(gridData);
		this.removeButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				removeSecondaryTable();
			}
		});
		
		this.secondaryTablesListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateEnablement();
			}
		});
	}
	
	private IContentProvider buildSecondaryTablesListContentProvider() {
		return new IStructuredContentProvider(){
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		
			public void dispose() {
				// do nothing
			}
		
			public Object[] getElements(Object inputElement) {
				return ((IEntity) inputElement).getSecondaryTables().toArray();
			}
		};
	}
	
	private ILabelProvider buildSecondaryTablesListLabelProvider() {
		return new LabelProvider() {
			public String getText(Object element) {
				//TODO display a qualified name instead
				ISecondaryTable secondaryTable = (ISecondaryTable) element;
				return secondaryTable.getName();
			}
		};
	}
	
	void addSecondaryTable() {
		SecondaryTableDialog dialog = new SecondaryTableDialog(this.getControl().getShell(), this.entity);
		addSecondaryTableFromDialog(dialog);
	}
	
	private void addSecondaryTableFromDialog(SecondaryTableDialog dialog) {
		if (dialog.open() == Window.OK) {
			int index = this.entity.getSpecifiedSecondaryTables().size();
			String name = dialog.getSelectedName();
			String catalog = dialog.getSelectedCatalog();
			String schema = dialog.getSelectedSchema();
			ISecondaryTable secondaryTable = this.entity.createSecondaryTable(index);
			this.entity.getSpecifiedSecondaryTables().add(secondaryTable);
			secondaryTable.setSpecifiedName(name);
			secondaryTable.setSpecifiedCatalog(catalog);
			secondaryTable.setSpecifiedSchema(schema);
		}
	}
	
	void editSecondaryTable() {
		ISecondaryTable secondaryTable = getSelectedSecondaryTable();
		SecondaryTableDialog dialog = new SecondaryTableDialog(this.getControl().getShell(), secondaryTable, this.entity);
		editSecondaryTableFromDialog(dialog, secondaryTable);
	}
	
	private void editSecondaryTableFromDialog(SecondaryTableDialog dialog, ISecondaryTable secondaryTable) {
		if (dialog.open() == Window.OK) {
			editSecondaryTableDialogOkd(dialog, secondaryTable);
		}
	}
	
	private void editSecondaryTableDialogOkd(SecondaryTableDialog dialog, ISecondaryTable secondaryTable) {
		String name = dialog.getSelectedName();
		String catalog = dialog.getSelectedCatalog();
		String schema = dialog.getSelectedSchema();

		if (secondaryTable.getSpecifiedName() == null || !secondaryTable.getSpecifiedName().equals(name)){
			secondaryTable.setSpecifiedName(name);
		}
		
		if (dialog.isDefaultCatalogSelected()) {
			if (secondaryTable.getSpecifiedCatalog() != null) {
				secondaryTable.setSpecifiedCatalog(null);
			}
		}
		else if (secondaryTable.getSpecifiedCatalog() == null || !secondaryTable.getSpecifiedCatalog().equals(catalog)){
			secondaryTable.setSpecifiedCatalog(catalog);
		}
		
		if (dialog.isDefaultSchemaSelected()) {
			if (secondaryTable.getSpecifiedSchema() != null) {
				secondaryTable.setSpecifiedSchema(null);
			}
		}
		else if (secondaryTable.getSpecifiedSchema() == null || !secondaryTable.getSpecifiedSchema().equals(schema)){
			secondaryTable.setSpecifiedSchema(schema);
		}
	}

	private ISecondaryTable getSelectedSecondaryTable() {
		return (ISecondaryTable) ((StructuredSelection) this.secondaryTablesListViewer.getSelection()).getFirstElement();
	}

	
	void removeSecondaryTable() {
		ISelection selection = this.secondaryTablesListViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			for (Iterator<ISecondaryTable> i = ((StructuredSelection) selection).iterator(); i.hasNext(); ) {
				this.entity.getSecondaryTables().remove(i.next());
			}
		}
	}
	
	void updateEnablement() {
		this.editButton.setEnabled(!((StructuredSelection) this.secondaryTablesListViewer.getSelection()).isEmpty());
		this.removeButton.setEnabled(!((StructuredSelection) this.secondaryTablesListViewer.getSelection()).isEmpty());
	}	
	
	public void doPopulate(EObject obj) {
		this.entity = (IEntity) obj;
		if (this.entity == null) {
			this.secondaryTablesListViewer.setInput(null);
			return;
		}
		
		this.secondaryTablesListViewer.setInput(this.entity);

		updateEnablement();
	}

	@Override
	protected void doPopulate() {
	}

	protected void engageListeners() {
		if (this.entity != null) {
			this.entity.eAdapters().add(this.entityListener);
			for (ISecondaryTable secondaryTable : this.entity.getSecondaryTables()) {
				secondaryTable.eAdapters().add(this.secondaryTableListener);
			}
			//this.addConnectionListener();		
		}
	}
	
	protected void disengageListeners() {
		if (this.entity != null) {
			//this.removeConnectionListener();
			for (ISecondaryTable secondaryTable : this.entity.getSecondaryTables()) {
				secondaryTable.eAdapters().remove(this.secondaryTableListener);
			}
			this.entity.eAdapters().remove(this.entityListener);
		}
	}
	
	protected void entityChanged(Notification notification) {
		if (notification.getFeatureID(IEntity.class) == JpaCoreMappingsPackage.IENTITY__SPECIFIED_SECONDARY_TABLES) {
			if (notification.getEventType() == Notification.ADD) {
				((ISecondaryTable) notification.getNewValue()).eAdapters().add(this.secondaryTableListener);
			}
			else if (notification.getEventType() == Notification.REMOVE) {
				((ISecondaryTable) notification.getOldValue()).eAdapters().remove(this.secondaryTableListener);				
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					secondaryTablesListViewer.refresh();
					updateEnablement();
				}
			});
		}
	}

	protected void seoncaryTableChanged(Notification notification) {
		if (notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME
			|| notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG
			|| notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA
			|| notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG
			|| notification.getFeatureID(ITable.class) == JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					secondaryTablesListViewer.refresh();
				}
			});
		}
	}
}
