/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This is the abstract implementation that keeps a combo in sync with the
 * database objects when a connection is active.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractDatabaseObjectCombo<T extends IJpaNode> extends BaseJpaController<T>
{
	private CCombo combo;
	private ConnectionListener connectionListener;

	/**
	 * Creates a new <code>AbstractDatabaseObjectCombo</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	protected AbstractDatabaseObjectCombo(BaseJpaController<? extends T> parentController,
	                                      Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>AbstractDatabaseObjectCombo</code>.
	 *
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected AbstractDatabaseObjectCombo(PropertyValueModel<? extends T> subjectHolder,
	                                      Composite parent,
	                                      TabbedPropertySheetWidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	private void addConnectionListener(T column) {
		if (column != null) {
			column.jpaProject().connectionProfile().addConnectionListener(this.connectionListener);
		}
	}

	private ConnectionListener buildConnectionListener() {
		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
			}

			public void closed(ConnectionProfile profile) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile,
			                            Database database) {
				System.out.println("");
			}

			public void modified(ConnectionProfile profile) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				return true;
			}

			public void opened(ConnectionProfile profile) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile,
			                          final Schema schema) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							AbstractDatabaseObjectCombo.this.schemaChanged(schema);
						}
					}
				});
			}

			public void tableChanged(ConnectionProfile profile,
			                         final Table table) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						if (!getControl().isDisposed()) {
							AbstractDatabaseObjectCombo.this.tableChanged(table);
						}
					}
				});
			}
		};
	}

	private ModifyListener buildModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				AbstractDatabaseObjectCombo.this.valueChanged((String) e.data);
			}
		};
	}

	@SuppressWarnings("unchecked")
	private PropertyChangeListener buildSubjectPropertyListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				AbstractDatabaseObjectCombo.this.removeConnectionListener((T) e.oldValue());
				AbstractDatabaseObjectCombo.this.repopulate();
				AbstractDatabaseObjectCombo.this.addConnectionListener((T) e.newValue());
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void buildWidgets(Composite parent) {

		this.combo = this.buildCombo(parent);
		this.combo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		this.combo.addModifyListener(this.buildModifyListener());

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		combo.setLayoutData(gridData);
	}

	protected final ConnectionProfile connectionProfile() {
		return this.subject().jpaProject().connectionProfile();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void disengageListeners() {
		super.disengageListeners();
		this.removeConnectionListener(this.subject());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void engageListeners() {
		super.engageListeners();
		this.addConnectionListener(this.subject());
	}

	public final CCombo getCombo() {
		return this.combo;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Control getControl() {
		return this.combo;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		this.getSubjectHolder().addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildSubjectPropertyListener()
		);

		this.connectionListener = this.buildConnectionListener();
	}

	private void removeConnectionListener(T value) {
		if (value != null) {
			value.jpaProject().connectionProfile().removeConnectionListener(this.connectionListener);
		}
	}

	protected abstract void schemaChanged(Schema schema);

	protected abstract void tableChanged(Table table);

	protected abstract void valueChanged(String value);
}
