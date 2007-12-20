/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
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

public class JoinColumnComposite extends BaseJpaComposite<ISingleRelationshipMapping>
{
	private final Adapter joinColumnListener;
	private Button joinColumnsEditButton;
	private Group joinColumnsGroup;
	private ListViewer joinColumnsListViewer;
	private Button joinColumnsRemoveButton;
	private Button overrideDefaultJoinColumnsCheckBox;
	private final Adapter singleRelationshipMappingListener;

	public JoinColumnComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
		this.singleRelationshipMappingListener = buildSingleRelationshipMappingListener();
		this.joinColumnListener = buildJoinColumnListener();
	}

	void addJoinColumn() {
		JoinColumnDialog dialog = new JoinColumnInRelationshipMappingDialog(this.getControl().getShell(), this.subject());
		this.addJoinColumnFromDialog(dialog);
	}

	private void addJoinColumnFromDialog(JoinColumnDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		int index = this.subject().getJoinColumns().size();
		IJoinColumn joinColumn = this.subject().createJoinColumn(index);
		this.subject().getSpecifiedJoinColumns().add(joinColumn);
		joinColumn.setSpecifiedName(dialog.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(dialog.getReferencedColumnName());
		if (!dialog.isDefaultTableSelected()) {
			//not checking this for name and referenced column name because there is no
			//default option when you are adding a second join column.  There is always
			//at least 1 join column (the default)
			joinColumn.setSpecifiedTable(dialog.getSelectedTable());
		}
	}

	String buildDefaultJoinColumnLabel(IJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
	}

	String buildJoinColumnLabel(IJoinColumn joinColumn) {
		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsBothDefault, joinColumn.getName(),joinColumn.getReferencedColumnName());
			}
			return NLS.bind(JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsFirstDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsSecDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
		else {
			return NLS.bind(JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParams, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
	}

	private Adapter buildJoinColumnListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				joinColumnChanged(notification);
			}
		};
	}

	private IContentProvider buildJoinColumnsListContentProvider() {
		return new IStructuredContentProvider(){
			public void dispose() {
				// do nothing
			}
			public Object[] getElements(Object inputElement) {
				return ((ISingleRelationshipMapping) inputElement).getJoinColumns().toArray();
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
				IJoinColumn joinColumn = (IJoinColumn) element;
				return (JoinColumnComposite.this.subject().getSpecifiedJoinColumns().size() == 0) ?
					buildDefaultJoinColumnLabel(joinColumn)
				:
					buildJoinColumnLabel(joinColumn);
			}
		};
	}

	private Adapter buildSingleRelationshipMappingListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				singleRelationshipMappingChanged(notification);
			}
		};
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			for (Iterator i = this.subject().getJoinColumns().iterator(); i.hasNext(); ) {
				((IJoinColumn) i.next()).eAdapters().remove(this.joinColumnListener);
			}
			this.subject().eAdapters().remove(this.singleRelationshipMappingListener);
		}
	}

	@Override
	protected void doPopulate() {
		if (this.subject() == null) {
			this.joinColumnsListViewer.setInput(null);
			return;
		}

		this.joinColumnsListViewer.setInput(this.subject());


		updateEnablement();
		this.overrideDefaultJoinColumnsCheckBox.setSelection(this.subject().containsSpecifiedJoinColumns());
	}

	void editJoinColumn() {
		IJoinColumn joinColumn = getSelectedJoinColumn();
		JoinColumnDialog dialog = new JoinColumnInRelationshipMappingDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}

	private void editJoinColumnDialogOkd(JoinColumnDialog dialog, IJoinColumn joinColumn) {
		String name = dialog.getSelectedName();
		String referencedColumnName = dialog.getReferencedColumnName();
		String table = dialog.getSelectedTable();

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

		if (dialog.isDefaultTableSelected()) {
			if (joinColumn.getSpecifiedTable() != null) {
				joinColumn.setSpecifiedTable(null);
			}
		}
		else if (joinColumn.getSpecifiedTable() == null || !joinColumn.getSpecifiedTable().equals(table)){
			joinColumn.setSpecifiedTable(table);
		}

		DefaultTrueBoolean insertable = dialog.getInsertable();
		if (joinColumn.getInsertable() != insertable) {
			joinColumn.setInsertable(insertable);
		}

		DefaultTrueBoolean updatable = dialog.getUpdatable();
		if (joinColumn.getUpdatable() != updatable) {
			joinColumn.setUpdatable(updatable);
		}
	}

	private void editJoinColumnFromDialog(JoinColumnDialog dialog, IJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
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
			this.subject().eAdapters().add(this.singleRelationshipMappingListener);
			for (Iterator i = this.subject().getJoinColumns().iterator(); i.hasNext(); ) {
				((IJoinColumn) i.next()).eAdapters().add(this.joinColumnListener);
			}
		}
	}



	private IJoinColumn getSelectedJoinColumn() {
		return (IJoinColumn) ((StructuredSelection) this.joinColumnsListViewer.getSelection()).getFirstElement();
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		this.overrideDefaultJoinColumnsCheckBox =
			getWidgetFactory().createButton(
				composite,
				JptUiMappingsMessages.JoinColumnComposite_overrideDefaultJoinColumns,
				SWT.CHECK);
		this.overrideDefaultJoinColumnsCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			public void widgetSelected(SelectionEvent e) {
				if (JoinColumnComposite.this.overrideDefaultJoinColumnsCheckBox.getSelection()) {
					IJoinColumn defaultJoinColumn = JoinColumnComposite.this.subject().getDefaultJoinColumns().get(0);
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = JoinColumnComposite.this.subject().createJoinColumn(0);
					JoinColumnComposite.this.subject().getSpecifiedJoinColumns().add(joinColumn);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				} else {
					JoinColumnComposite.this.subject().getSpecifiedJoinColumns().clear();
				}
			}
		});

		this.joinColumnsGroup =
			getWidgetFactory().createGroup(
				composite,
				JptUiMappingsMessages.JoinColumnComposite_joinColumn);
		this.joinColumnsGroup.setLayout(new GridLayout(2, false));
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.joinColumnsGroup.setLayoutData(gridData);

		this.joinColumnsListViewer = new ListViewer(this.joinColumnsGroup, SWT.BORDER | SWT.MULTI);
		this.joinColumnsListViewer.setContentProvider(buildJoinColumnsListContentProvider());
		this.joinColumnsListViewer.setLabelProvider(buildJoinColumnsListLabelProvider());
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.verticalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.joinColumnsListViewer.getList().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.joinColumnsListViewer.getList(), IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS);

		Button addJoinColumnButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinColumnComposite_add, SWT.NONE);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		addJoinColumnButton.setLayoutData(gridData);
		addJoinColumnButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				addJoinColumn();
			}
		});

		this.joinColumnsEditButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinColumnComposite_edit, SWT.NONE);
		this.joinColumnsEditButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				editJoinColumn();
			}
		});
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.joinColumnsEditButton.setLayoutData(gridData);

		this.joinColumnsRemoveButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinColumnComposite_remove, SWT.NONE);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		this.joinColumnsRemoveButton.setLayoutData(gridData);
		this.joinColumnsRemoveButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				removeJoinColumn();
			}
		});

		this.joinColumnsListViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				updateEnablement();
			}
		});

	}

	protected void joinColumnChanged(Notification notification) {
		if (notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__SPECIFIED_NAME
			|| notification.getFeatureID(INamedColumn.class) == JpaCoreMappingsPackage.INAMED_COLUMN__DEFAULT_NAME
			|| notification.getFeatureID(IAbstractColumn.class) == JpaCoreMappingsPackage.IABSTRACT_COLUMN__SPECIFIED_TABLE
			|| notification.getFeatureID(IAbstractColumn.class) == JpaCoreMappingsPackage.IABSTRACT_COLUMN__DEFAULT_TABLE
			|| notification.getFeatureID(IAbstractJoinColumn.class) == JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__SPECIFIED_REFERENCED_COLUMN_NAME
			|| notification.getFeatureID(IAbstractJoinColumn.class) == JpaCoreMappingsPackage.IABSTRACT_JOIN_COLUMN__DEFAULT_REFERENCED_COLUMN_NAME) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					joinColumnsListViewer.refresh();
				}
			});
		}
	}

	void removeJoinColumn() {
		ISelection selection = this.joinColumnsListViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			for (Iterator stream = ((StructuredSelection) selection).iterator(); stream.hasNext(); ) {
				this.subject().getJoinColumns().remove(stream.next());
			}
		}
	}

	protected void singleRelationshipMappingChanged(Notification notification) {
		if (notification.getFeatureID(ISingleRelationshipMapping.class) == JpaCoreMappingsPackage.ISINGLE_RELATIONSHIP_MAPPING__SPECIFIED_JOIN_COLUMNS) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					JoinColumnComposite.this.joinColumnsListViewer.refresh();
					JoinColumnComposite.this.overrideDefaultJoinColumnsCheckBox.setSelection(subject().containsSpecifiedJoinColumns());
					JoinColumnComposite.this.updateEnablement();
				}
			});
			if (notification.getEventType() == Notification.ADD) {
				((IJoinColumn) notification.getNewValue()).eAdapters().add(this.joinColumnListener);
			}
			else if (notification.getEventType() == Notification.REMOVE) {
				((IJoinColumn) notification.getOldValue()).eAdapters().remove(this.joinColumnListener);
			}
		}
	}

	void updateEnablement() {
		boolean groupEnabledState = this.subject().containsSpecifiedJoinColumns();
		enableGroup(this.joinColumnsGroup, groupEnabledState);

		this.joinColumnsRemoveButton.setEnabled(groupEnabledState && !((StructuredSelection) this.joinColumnsListViewer.getSelection()).isEmpty());
		this.joinColumnsEditButton.setEnabled(groupEnabledState && ((StructuredSelection) this.joinColumnsListViewer.getSelection()).size() == 1);
	}
}