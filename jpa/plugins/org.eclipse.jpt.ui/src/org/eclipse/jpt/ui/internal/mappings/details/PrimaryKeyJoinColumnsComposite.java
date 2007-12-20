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
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
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

public class PrimaryKeyJoinColumnsComposite extends BaseJpaComposite<IEntity>
{
	private final Adapter entityListener;
	Button overrideDefaultJoinColumnsCheckBox;
	private final Adapter pkJoinColumnListener;
	private Button pkJoinColumnsEditButton;
	private Group pkJoinColumnsGroup;
	ListViewer pkJoinColumnsListViewer;
	private Button pkJoinColumnsRemoveButton;

	public PrimaryKeyJoinColumnsComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
		this.subject()Listener = buildEntityListener();
		this.pkJoinColumnListener = buildPkJoinColumnListener();
	}

	private void addJoinColumnFromDialog(PrimaryKeyJoinColumnDialog dialog) {
		if (dialog.open() == Window.OK) {
			int index = this.subject().getSpecifiedPrimaryKeyJoinColumns().size();
			String name = dialog.getSelectedName();
			String referencedColumnName = dialog.getReferencedColumnName();
			IPrimaryKeyJoinColumn joinColumn = this.subject().createPrimaryKeyJoinColumn(index);
			this.subject().getSpecifiedPrimaryKeyJoinColumns().add(joinColumn);
			joinColumn.setSpecifiedName(name);
			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}
	}

	void addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getControl().getShell(), this.subject());
		addJoinColumnFromDialog(dialog);
	}

	String buildDefaultJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
	}

	private Adapter buildEntityListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				entityChanged(notification);
			}
		};
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

	private IContentProvider buildJoinColumnsListContentProvider() {
		return new IStructuredContentProvider(){
			public void dispose() {
				// do nothing
			}

			public Object[] getElements(Object inputElement) {
				return ((IEntity) inputElement).getPrimaryKeyJoinColumns().toArray();
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// do nothing
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) element;
				return (PrimaryKeyJoinColumnsComposite.this.subject().containsSpecifiedPrimaryKeyJoinColumns()) ?
					buildJoinColumnLabel(joinColumn)
				:
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}


	private Adapter buildPkJoinColumnListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				pkJoinColumnChanged(notification);
			}
		};
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			//this.removeConnectionListener();
			for (IPrimaryKeyJoinColumn pkJoinColumn : this.subject().getPrimaryKeyJoinColumns()) {
				pkJoinColumn.eAdapters().remove(this.pkJoinColumnListener);
			}
			this.subject().eAdapters().remove(this.subject()Listener);
		}
	}

	@Override
	protected void doPopulate() {
		if (this.subject() == null) {
			this.pkJoinColumnsListViewer.setInput(null);
			return;
		}

		this.pkJoinColumnsListViewer.setInput(this.subject());


		updatePrimaryKeyJoinColumnsEnablement();
		this.overrideDefaultJoinColumnsCheckBox.setSelection(this.subject().containsSpecifiedPrimaryKeyJoinColumns());
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

	private void editJoinColumnFromDialog(PrimaryKeyJoinColumnDialog dialog, IPrimaryKeyJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
	}

	void editPrimaryKeyJoinColumn() {
		IPrimaryKeyJoinColumn joinColumn = getSelectedJoinColumn();
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}

	private void enableGroup(Group group, boolean enabled) {
		group.setEnabled(enabled);
		for (int i = 0; i < group.getChildren().length; i++) {
			group.getChildren()[i].setEnabled(enabled);
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(this.subject()Listener);
			for (IPrimaryKeyJoinColumn pkJoinColumn : this.subject().getPrimaryKeyJoinColumns()) {
				pkJoinColumn.eAdapters().add(this.pkJoinColumnListener);
			}
			//this.addConnectionListener();
		}
	}


	protected void entityChanged(Notification notification) {
		if (notification.getFeatureID(IEntity.class) == JpaCoreMappingsPackage.IENTITY__SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS) {
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
					overrideDefaultJoinColumnsCheckBox.setSelection(entity.containsSpecifiedPrimaryKeyJoinColumns());
					updatePrimaryKeyJoinColumnsEnablement();
				}
			});
		}
	}

	private IPrimaryKeyJoinColumn getSelectedJoinColumn() {
		return (IPrimaryKeyJoinColumn) ((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).getFirstElement();
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
				if (PrimaryKeyJoinColumnsComposite.this.overrideDefaultJoinColumnsCheckBox.getSelection()) {
					IPrimaryKeyJoinColumn defaultJoinColumn = PrimaryKeyJoinColumnsComposite.this.subject().getDefaultPrimaryKeyJoinColumns().get(0);
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IPrimaryKeyJoinColumn pkJoinColumn = PrimaryKeyJoinColumnsComposite.this.subject().createPrimaryKeyJoinColumn(0);
					PrimaryKeyJoinColumnsComposite.this.subject().getSpecifiedPrimaryKeyJoinColumns().add(pkJoinColumn);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				} else {
					PrimaryKeyJoinColumnsComposite.this.subject().getSpecifiedPrimaryKeyJoinColumns().clear();
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

		Button addJoinColumnButton = getWidgetFactory().createButton(
			this.pkJoinColumnsGroup,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_add,
			SWT.NONE);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		addJoinColumnButton.setLayoutData(gridData);
		addJoinColumnButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			public void widgetSelected(SelectionEvent e) {
				addPrimaryKeyJoinColumn();
			}
		});

		this.pkJoinColumnsEditButton = getWidgetFactory().createButton(
			this.pkJoinColumnsGroup,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_edit,
			SWT.NONE);
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

		this.pkJoinColumnsRemoveButton = getWidgetFactory().createButton(
			this.pkJoinColumnsGroup,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_remove,
			SWT.NONE);
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

	void removePrimaryKeyJoinColumn() {
		ISelection selection = this.pkJoinColumnsListViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			for (Iterator i = ((StructuredSelection) selection).iterator(); i.hasNext(); ) {
				this.subject().getPrimaryKeyJoinColumns().remove(i.next());
			}
		}
	}

	void updatePrimaryKeyJoinColumnsEnablement() {
		boolean groupEnabledState = this.subject().containsSpecifiedPrimaryKeyJoinColumns();
		enableGroup(this.pkJoinColumnsGroup, groupEnabledState);

		this.pkJoinColumnsRemoveButton.setEnabled(groupEnabledState && !((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).isEmpty());
		this.pkJoinColumnsEditButton.setEnabled(groupEnabledState && ((StructuredSelection) this.pkJoinColumnsListViewer.getSelection()).size() == 1);
	}
}