/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.List;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.Owner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JoinTableComposite extends BaseJpaComposite<IJoinTable>
{
	private final Adapter joinTableListener;
	private TableCombo tableCombo;
	private ConnectionProfile connectionProfile;
	private ConnectionListener connectionListener;
	private Button overrideDefaultJoinColumnsCheckBox;
	private JoinColumnsComposite joinColumnsComposite;
	private Button overrideDefaultInverseJoinColumnsCheckBox;
	private JoinColumnsComposite inverseJoinColumnsComposite;

	public JoinTableComposite(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, widgetFactory);
		this.joinTableListener = buildJoinTableListener();
		this.connectionListener = buildConnectionListener();
	}

	private Adapter buildJoinTableListener() {
		return new AdapterImpl() {
			@Override
			public void notifyChanged(Notification notification) {
				joinTableChanged(notification);
			}
		};
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				// not interested to this event.
			}

			public void closed(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						JoinTableComposite.this.tableCombo.populate();
					}
				});
			}

			public void modified(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						JoinTableComposite.this.tableCombo.populate();
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				// not interested to this event.
				return true;
			}

			public void opened(ConnectionProfile profile) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						JoinTableComposite.this.tableCombo.populate();
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile, final Database database) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(database == JoinTableComposite.this.tableCombo.getDatabase()) {
							if (!getControl().isDisposed()) {
								JoinTableComposite.this.tableCombo.populate();
							}
						}
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile, final Schema schema) {
				getControl().getDisplay().asyncExec( new Runnable() {
					public void run() {
						if(schema == JoinTableComposite.this.tableCombo.getTableSchema()) {
							if (!getControl().isDisposed()) {
								JoinTableComposite.this.tableCombo.populate();
							}
						}
					}
				});
			}

			public void tableChanged(ConnectionProfile profile, final Table table) {
				// not interested to this event.
			}
		};
    }

	private ConnectionProfile getConnectionProfile() {
		if(this.connectionProfile == null) {
			IJpaProject jpaProject = this.subject().getJpaProject();
			this.connectionProfile = jpaProject.connectionProfile();
		}
		return this.connectionProfile;
	}

	private void addConnectionListener() {
		this.getConnectionProfile().addConnectionListener(this.connectionListener);
	}

	private void removeConnectionListener() {
		this.getConnectionProfile().removeConnectionListener(this.connectionListener);
	}

	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);

		getWidgetFactory().createLabel(composite, JptUiMappingsMessages.JoinTableComposite_name);

		this.tableCombo = new TableCombo(composite, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.tableCombo.getCombo().setLayoutData(gridData);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.tableCombo.getCombo(), IJpaHelpContextIds.MAPPING_JOIN_TABLE_NAME);

		this.overrideDefaultJoinColumnsCheckBox =
			getWidgetFactory().createButton(
				composite,
				JptUiMappingsMessages.JoinTableComposite_overrideDefaultJoinColumns,
				SWT.CHECK);
		this.overrideDefaultJoinColumnsCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			public void widgetSelected(SelectionEvent e) {
				if (JoinTableComposite.this.overrideDefaultJoinColumnsCheckBox.getSelection()) {
					IJoinColumn defaultJoinColumn = JoinTableComposite.this.subject().getDefaultJoinColumns().get(0);
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
					IJoinColumn joinColumn = JoinTableComposite.this.subject().createJoinColumn(0);
					JoinTableComposite.this.subject().getSpecifiedJoinColumns().add(joinColumn);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				} else {
					JoinTableComposite.this.subject().getSpecifiedJoinColumns().clear();
				}
			}
		});

		this.joinColumnsComposite = new JoinColumnsComposite(composite, this.commandStack, getWidgetFactory(), JptUiMappingsMessages.JoinTableComposite_joinColumn);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.joinColumnsComposite.getControl().setLayoutData(gridData);

		this.overrideDefaultInverseJoinColumnsCheckBox = getWidgetFactory().createButton(composite, JptUiMappingsMessages.JoinTableComposite_overrideDefaultInverseJoinColumns, SWT.CHECK);
		this.overrideDefaultInverseJoinColumnsCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}

			public void widgetSelected(SelectionEvent e) {
				if (JoinTableComposite.this.overrideDefaultInverseJoinColumnsCheckBox.getSelection()) {
					IJoinColumn defaultJoinColumn = JoinTableComposite.this.subject().getDefaultInverseJoinColumns().get(0);
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();
					IJoinColumn joinColumn = JoinTableComposite.this.subject().createInverseJoinColumn(0);
					JoinTableComposite.this.subject().getSpecifiedInverseJoinColumns().add(joinColumn);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				} else {
					JoinTableComposite.this.subject().getSpecifiedInverseJoinColumns().clear();
				}
			}
		});
		this.inverseJoinColumnsComposite = new JoinColumnsComposite(composite, this.commandStack, getWidgetFactory(), JptUiMappingsMessages.JoinTableComposite_inverseJoinColumn);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		this.inverseJoinColumnsComposite.getControl().setLayoutData(gridData);
	}

	void addJoinColumn() {
		JoinColumnInJoinTableDialog dialog = new JoinColumnInJoinTableDialog(this.getControl().getShell(), this.subject());
		this.addJoinColumnFromDialog(dialog);
	}

	private void addJoinColumnFromDialog(JoinColumnInJoinTableDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		int index = this.subject().getJoinColumns().size();
		IJoinColumn joinColumn = this.subject().createJoinColumn(index);
		this.subject().getSpecifiedJoinColumns().add(joinColumn);
		joinColumn.setSpecifiedName(dialog.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(dialog.getReferencedColumnName());
	}

	void addInverseJoinColumn() {
		InverseJoinColumnDialog dialog = new InverseJoinColumnDialog(this.getControl().getShell(), this.subject());
		this.addInverseJoinColumnFromDialog(dialog);
	}

	private void addInverseJoinColumnFromDialog(InverseJoinColumnDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		int index = this.subject().getInverseJoinColumns().size();
		IJoinColumn joinColumn = this.subject().createInverseJoinColumn(index);
		this.subject().getSpecifiedInverseJoinColumns().add(joinColumn);
		joinColumn.setSpecifiedName(dialog.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(dialog.getReferencedColumnName());
	}

	void editJoinColumn(IJoinColumn joinColumn) {
		JoinColumnInJoinTableDialog dialog = new JoinColumnInJoinTableDialog(this.getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}

	private void editJoinColumnFromDialog(JoinColumnInJoinTableDialog dialog, IJoinColumn joinColumn) {
		if (dialog.open() == Window.OK) {
			editJoinColumnDialogOkd(dialog, joinColumn);
		}
	}

	private void editJoinColumnDialogOkd(JoinColumnInJoinTableDialog dialog, IJoinColumn joinColumn) {
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


	void editInverseJoinColumn(IJoinColumn joinColumn) {
		InverseJoinColumnDialog dialog = new InverseJoinColumnDialog(getControl().getShell(), joinColumn);
		editJoinColumnFromDialog(dialog, joinColumn);
	}

	protected void joinTableChanged(Notification notification) {
		if (notification.getFeatureID(IJoinTable.class) == JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS) {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					overrideDefaultJoinColumnsCheckBox.setSelection(joinTable.containsSpecifiedJoinColumns());
				}
			});
		}
		else if (notification.getFeatureID(IJoinTable.class) == JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS) {

			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					if (getControl().isDisposed()) {
						return;
					}
					overrideDefaultInverseJoinColumnsCheckBox.setSelection(joinTable.containsSpecifiedInverseJoinColumns());
				}
			});
		}
	}

	@Override
	protected void engageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().add(joinTableListener);
			this.addConnectionListener();
		}
	}

	@Override
	protected void disengageListeners() {
		if (this.subject() != null) {
			this.subject().eAdapters().remove(joinTableListener);
			this.removeConnectionListener();
		}
	}

	@Override
	protected void doPopulate() {
		if (this.subject() == null) {
			this.joinColumnsComposite.populate(null);
			this.inverseJoinColumnsComposite.populate(null);
			this.connectionProfile = null;
			return;
		}

		this.tableCombo.populate(this.subject());
		this.joinColumnsComposite.populate(new JoinColumnsOwner(this.subject()));
		this.inverseJoinColumnsComposite.populate(new InverseJoinColumnsOwner(this.subject()));

		this.overrideDefaultJoinColumnsCheckBox.setSelection(this.subject().containsSpecifiedJoinColumns());
		this.overrideDefaultInverseJoinColumnsCheckBox.setSelection(this.subject().containsSpecifiedInverseJoinColumns());
	}

	@Override
	public void dispose() {
		this.tableCombo.dispose();
		this.joinColumnsComposite.dispose();
		this.inverseJoinColumnsComposite.dispose();
		super.dispose();
	}

	private class JoinColumnsOwner implements Owner<IJoinTable> {

		private IJoinTable joinTable;

		public JoinColumnsOwner(IJoinTable joinTable) {
			super();
			this.joinTable = joinTable;
		}

		public void addJoinColumn() {
			JoinTableComposite.this.addJoinColumn();
		}

		public boolean containsSpecifiedJoinColumns() {
			return this.joinTable.containsSpecifiedJoinColumns();
		}

		public IJoinColumn createJoinColumn(int index) {
			return this.joinTable.createJoinColumn(index);
		}

		public List<IJoinColumn> getJoinColumns() {
			return this.joinTable.getJoinColumns();
		}

		public List<IJoinColumn> getSpecifiedJoinColumns() {
			return this.joinTable.getSpecifiedJoinColumns();
		}

		public int specifiedJoinColumnsFeatureId() {
			return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_JOIN_COLUMNS;
		}

		public Class<IJoinTable> owningFeatureClass() {
			return IJoinTable.class;
		}

		public void editJoinColumn(IJoinColumn joinColumn) {
			JoinTableComposite.this.editJoinColumn(joinColumn);
		}

		public IJoinTable getEObject() {
			return this.joinTable;
		}
	}

	private class InverseJoinColumnsOwner implements Owner<IJoinTable> {

		private IJoinTable joinTable;

		public InverseJoinColumnsOwner(IJoinTable joinTable) {
			super();
			this.joinTable = joinTable;
		}

		public void addJoinColumn() {
			JoinTableComposite.this.addInverseJoinColumn();
		}

		public boolean containsSpecifiedJoinColumns() {
			return this.joinTable.containsSpecifiedInverseJoinColumns();
		}

		public IJoinColumn createJoinColumn(int index) {
			return this.joinTable.createJoinColumn(index);
		}

		public List<IJoinColumn> getJoinColumns() {
			return this.joinTable.getInverseJoinColumns();
		}

		public List<IJoinColumn> getSpecifiedJoinColumns() {
			return this.joinTable.getSpecifiedInverseJoinColumns();
		}

		public int specifiedJoinColumnsFeatureId() {
			return JpaCoreMappingsPackage.IJOIN_TABLE__SPECIFIED_INVERSE_JOIN_COLUMNS;
		}

		public Class<IJoinTable> owningFeatureClass() {
			return IJoinTable.class;
		}

		public void editJoinColumn(IJoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public IJoinTable getEObject() {
			return this.joinTable;
		}
	}
}