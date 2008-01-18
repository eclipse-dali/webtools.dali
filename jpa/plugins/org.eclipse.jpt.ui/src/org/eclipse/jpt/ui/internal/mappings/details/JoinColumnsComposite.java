/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import java.util.List;
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
import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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

public class JoinColumnsComposite extends BaseJpaComposite<JoinColumnsComposite.Owner>
{
	private final Adapter joinColumnsOwnerListener;
	private ListViewer joinColumnsListViewer;
	private final Adapter joinColumnListener;
	private Group joinColumnsGroup;
	private Button joinColumnsAddButton;
	private Button joinColumnsRemoveButton;
	private Button joinColumnsEditButton;

	public JoinColumnsComposite(PropertyValueModel<? extends JoinColumnsComposite.Owner> subjectHolder,
	                            Composite parent,
	                            TabbedPropertySheetWidgetFactory widgetFactory, String groupTitle) {

		super(subjectHolder, parent, widgetFactory);
		this.joinColumnsOwnerListener = buildJoinColumnsOwnerListener();
		this.joinColumnListener = buildJoinColumnListener();
		this.joinColumnsGroup.setText(groupTitle);
	}

	private Adapter buildJoinColumnsOwnerListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				joinColumnsOwnerChanged(notification);
			}
		};
	}

	private Adapter buildJoinColumnListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				joinColumnChanged(notification, joinColumnsListViewer);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);

		this.joinColumnsGroup =
			getWidgetFactory().createGroup(
				composite,
				"");
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

		this.joinColumnsAddButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinTableComposite_add, SWT.NONE);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		this.joinColumnsAddButton.setLayoutData(gridData);
		this.joinColumnsAddButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				subject().addJoinColumn();
			}
		});

		this.joinColumnsEditButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinTableComposite_edit, SWT.NONE);
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

		this.joinColumnsRemoveButton = getWidgetFactory().createButton(this.joinColumnsGroup, JptUiMappingsMessages.JoinTableComposite_remove, SWT.NONE);
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
				updateJoinColumnsEnablement();
			}
		});
	}

	private IContentProvider buildJoinColumnsListContentProvider() {
		return new IStructuredContentProvider(){
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			public void dispose() {

			}

			public Object[] getElements(Object inputElement) {
				return ((Owner) inputElement).getJoinColumns().toArray();
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IJoinColumn joinColumn = (IJoinColumn) element;
				return (JoinColumnsComposite.this.subject().containsSpecifiedJoinColumns()) ?
					buildJoinColumnLabel(joinColumn)
				:
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}

	private String buildDefaultJoinColumnLabel(IJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
	}

	private String buildJoinColumnLabel(IJoinColumn joinColumn) {
		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsBothDefault, joinColumn.getName(),joinColumn.getReferencedColumnName());
			}
			return NLS.bind(JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsFirstDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsSecDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
		else {
			return NLS.bind(JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParams, joinColumn.getName(), joinColumn.getReferencedColumnName());
		}
	}

	void editJoinColumn() {
		this.subject().editJoinColumn(getSelectedJoinColumn());
	}

	void removeJoinColumn() {
		ISelection selection = this.joinColumnsListViewer.getSelection();
		if (selection instanceof StructuredSelection) {
			for (Iterator stream = ((StructuredSelection) selection).iterator(); stream.hasNext(); ) {
				this.subject().getJoinColumns().remove(stream.next());
			}
		}
	}

	private IJoinColumn getSelectedJoinColumn() {
		return (IJoinColumn) ((StructuredSelection) this.joinColumnsListViewer.getSelection()).getFirstElement();
	}

	protected void joinColumnsOwnerChanged(Notification notification) {
		if (notification.getFeatureID(subject().owningFeatureClass()) == subject().specifiedJoinColumnsFeatureId()) {
			if (notification.getEventType() == Notification.ADD) {
				((IJoinColumn) notification.getNewValue()).eAdapters().add(this.joinColumnListener);
			}
			else if (notification.getEventType() == Notification.REMOVE) {
				((IJoinColumn) notification.getOldValue()).eAdapters().remove(this.joinColumnListener);
			}
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					joinColumnsListViewer.refresh();
					updateJoinColumnsEnablement();
				}
			});
		}
	}

	private void enableGroup(Group group, boolean enabled) {
		group.setEnabled(enabled);
		for (int i = 0; i < group.getChildren().length; i++) {
			group.getChildren()[i].setEnabled(enabled);
		}
	}
		protected void joinColumnChanged(Notification notification, final ListViewer listViewer) {
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
					listViewer.refresh();
				}
			});
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().getEObject().eAdapters().add(this.joinColumnsOwnerListener);
			for (IJoinColumn joinColumn : this.subject().getJoinColumns()) {
				joinColumn.eAdapters().add(this.joinColumnListener);
			}
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			for (IJoinColumn joinColumn : this.subject().getJoinColumns()) {
				joinColumn.eAdapters().remove(this.joinColumnListener);
			}
			this.subject().getEObject().eAdapters().remove(this.joinColumnsOwnerListener);
		}
	}

	public void doPopulate(EObject obj) {
		if (this.subject() == null) {
			this.joinColumnsListViewer.setInput(null);
			return;
		}

		this.joinColumnsListViewer.setInput(this.subject());

		updateEnablement();
	}

	private void updateEnablement() {
		updateJoinColumnsEnablement();
	}

	void updateJoinColumnsEnablement() {
		boolean groupEnabledState = this.subject().containsSpecifiedJoinColumns();
		enableGroup(this.joinColumnsGroup, groupEnabledState);

		this.joinColumnsRemoveButton.setEnabled(groupEnabledState && !((StructuredSelection) this.joinColumnsListViewer.getSelection()).isEmpty());
		this.joinColumnsEditButton.setEnabled(groupEnabledState && ((StructuredSelection) this.joinColumnsListViewer.getSelection()).size() == 1);
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		enableGroup(this.joinColumnsGroup, enabled);
		this.joinColumnsAddButton.setEnabled(enabled);
		this.joinColumnsEditButton.setEnabled(enabled);
		this.joinColumnsRemoveButton.setEnabled(enabled);
		this.joinColumnsListViewer.getList().setEnabled(enabled);
	}

	public static interface Owner<T> {
		int specifiedJoinColumnsFeatureId();

		Class<?> owningFeatureClass();

		T getEObject();

		boolean containsSpecifiedJoinColumns();

		List<IJoinColumn> getJoinColumns();

		List<IJoinColumn> getSpecifiedJoinColumns();

		IJoinColumn createJoinColumn(int index);

		void addJoinColumn();

		void editJoinColumn(IJoinColumn joinColumn);
	}
}