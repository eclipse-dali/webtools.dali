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
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This is the abstract implementation that keeps a combo in sync with the
 * database objects when a connection is active.
 *
 * @version 2.0
 * @since 2.0
 */
public abstract class AbstractDatabaseObjectCombo<T extends IJpaNode> extends AbstractFormPane<T>
{
	private CCombo combo;
	private ConnectionListener connectionListener;

	/**
	 * Creates a new <code>AbstractDatabaseObjectCombo</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	protected AbstractDatabaseObjectCombo(AbstractFormPane<? extends T> parentPane,
	                                      Composite parent) {

		super(parentPane, parent);
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
				CCombo combo = (CCombo) e.widget;
				AbstractDatabaseObjectCombo.this.valueChanged(combo.getText());
			}
		};
	}

	protected final ConnectionProfile connectionProfile() {
		return this.subject().jpaProject().connectionProfile();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners(T subject) {
		super.disengageListeners(subject);
		removeConnectionListener(subject);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);

		if (!this.combo.isDisposed()) {
			combo.setEnabled(enabled);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners(T subject) {
		super.engageListeners(subject);
		addConnectionListener(subject);
	}

	public final CCombo getCombo() {
		return this.combo;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.connectionListener = this.buildConnectionListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		this.combo = this.buildCombo(container);
		this.combo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		this.combo.addModifyListener(this.buildModifyListener());
	}

	private void removeConnectionListener(T value) {
		if (value != null) {
			value.jpaProject().connectionProfile().removeConnectionListener(this.connectionListener);
		}
	}

	protected abstract void schemaChanged(Schema schema);

	/**
	 * Sets the given value as the new value.
	 *
	 * @param value The new value to send to the model object
	 */
	protected abstract void setValue(String value);

	protected abstract void tableChanged(Table table);

	/**
	 * Requests the current value from the model object.
	 *
	 * @return The current value
	 */
	protected abstract String value();

	/**
	 * The selection has changed, update the model if required.
	 *
	 * @param value The new value
	 */
	protected void valueChanged(String value) {

		IJpaNode subject = subject();

		if (subject == null) {
			return;
		}

		String oldValue = value();

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// The default value
		if (value != null &&
		    getCombo().getItemCount() > 0 &&
		    value.equals(getCombo().getItem(0)))
		{
			value = null;
		}

		// Set the new value
		if ((value != null) && (oldValue == null)) {
			setValue(value);
		}
		else if ((oldValue != null) && !oldValue.equals(value)) {
			setValue(value);
		}
	}
}