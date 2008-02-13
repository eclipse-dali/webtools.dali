/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.IJpaProject;
import org.eclipse.jpt.db.internal.ConnectionListener;
import org.eclipse.jpt.db.internal.ConnectionProfile;
import org.eclipse.jpt.db.internal.Database;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.IWidgetFactory;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract implementation keeps a combo in sync with the database objects
 * when a connection is active.
 *
 * @see CatalogCombo
 * @see ColumnCombo
 * @see SchemaCombo
 * @see TableCombo
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class AbstractDatabaseObjectCombo<T extends IJpaNode> extends AbstractFormPane<T>
{
	/**
	 * The main widget of this pane.
	 */
	private CCombo combo;

	/**
	 * The listener added to the <code>ConnectionProfile</code> responsible to
	 * keep the combo in sync with the database metadata.
	 */
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
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	protected AbstractDatabaseObjectCombo(AbstractFormPane<?> parentPane,
	                                      PropertyValueModel<? extends T> subjectHolder,
	                                      Composite parent) {

		super(parentPane, subjectHolder, parent);
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
	                                      IWidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	private ConnectionListener buildConnectionListener() {

		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				log(Tracing.UI_DB, "aboutToClose");
			}

			public void closed(ConnectionProfile profile) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "closed");

						if (!getCombo().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile,
			                            Database database) {

				log(Tracing.UI_DB, "databaseChanged");
			}

			public void modified(ConnectionProfile profile) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "modified");

						if (!getCombo().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public boolean okToClose(ConnectionProfile profile) {
				log(Tracing.UI_DB, "okToClose");
				return true;
			}

			public void opened(ConnectionProfile profile) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "opened");

						if (!getCombo().isDisposed()) {
							AbstractDatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public void schemaChanged(ConnectionProfile profile,
			                          final Schema schema) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "schemaChanged: " + schema.getName());

						if (!getCombo().isDisposed()) {
							AbstractDatabaseObjectCombo.this.schemaChanged(schema);
						}
					}
				});
			}

			public void tableChanged(ConnectionProfile profile,
			                         final Table table) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "tableChanged: " + table.getName());

						if (!getCombo().isDisposed()) {
							AbstractDatabaseObjectCombo.this.tableChanged(table);
						}
					}
				});
			}
		};
	}

	private FocusListener buildFocusListener() {

		return new FocusListener() {

			public void focusGained(FocusEvent e) {

				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;

					if (combo.getSelectionIndex() == 0) {
						setPopulating(true);

						try {
							combo.setText("");
						}
						finally {
							setPopulating(false);
						}
					}
				}
			}

			public void focusLost(FocusEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;

					if (combo.getText().length() == 0) {
						setPopulating(true);

						try {
							combo.select(0);
						}
						finally {
							setPopulating(false);
						}
					}
				}
			}
		};
	}

	private ModifyListener buildModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					valueChanged(combo.getText());
				}
			}
		};
	}

	/**
	 * If the value changes but the subject is null, then is invoked in order to
	 * create the subject.
	 */
	protected void buildSubject() {
	}

	/**
	 * Returns the JPA project's connection profile, which is never
	 * <code>null</code>.
	 *
	 * @return The connection set in the project's properties or <code>null</code>
	 * if it could not being retrieved
	 */
	protected final ConnectionProfile connectionProfile() {
		IJpaProject jpaProject = jpaProject();

		if (jpaProject != null) {
			return jpaProject.connectionProfile();
		}

		return null;
	}

	/**
	 * Returns the database associated with the active connection profile.
	 *
	 * @return The online database or a <code>null</code> instance if no
	 * connection profile was set or the
	 */
	protected final Database database() {
		return connectionProfile().getDatabase();
	}

	/**
	 * Returns the default value, or <code>null</code> if no default is
	 * specified.
	 *
	 * @return The value that represents the default when no value was specified
	 */
	protected abstract String defaultValue();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners(T subject) {
		super.disengageListeners(subject);

		IJpaProject jpaProject = jpaProject();

		if (jpaProject != null) {
			jpaProject.connectionProfile().removeConnectionListener(this.connectionListener);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {

		super.doPopulate();
		populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {

		super.enableWidgets(enabled);

		if (!this.combo.isDisposed()) {
			this.combo.setEnabled(enabled);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners(T subject) {
		super.engageListeners(subject);

		IJpaProject jpaProject = jpaProject();

		if (jpaProject != null) {
			jpaProject.connectionProfile().addConnectionListener(this.connectionListener);
		}
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
		this.connectionListener = buildConnectionListener();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		this.combo = buildEditableCCombo(container);
		this.combo.addModifyListener(buildModifyListener());
		this.combo.addFocusListener(buildFocusListener());
	}

	/**
	 * Determines if the subject should be created when the value changes.
	 *
	 * @return <code>false</code> is the default behavior
	 */
	protected boolean isBuildSubjectAllowed() {
		return false;
	}

	/**
	 * Retrives the <code>IJpaProject</code> that is required to register a
	 * <code>ConnectionListener</code> in order to keep the combo in sync with
	 * the associated online database.
	 *
	 * @return The JPA project
	 */
	protected IJpaProject jpaProject() {
		return subject() == null ? null : subject().jpaProject();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void log(String flag, String message) {

		super.log(flag, message);

		if (Tracing.UI_DB.equals(flag) &&
		    Tracing.booleanDebugOption(Tracing.UI_DB))
		{
			Class<?> thisClass = getClass();
			String className = ClassTools.shortNameFor(thisClass);

			if (thisClass.isAnonymousClass()) {
				className = className.substring(0, className.indexOf('$'));
				className += "->" + ClassTools.shortNameFor(thisClass.getSuperclass());
			}

			Tracing.log(className + ": " + message);
		}
	}

	/**
	 * Populates the combo's list by adding first the default value is available
	 * and then the possible choices.
	 */
	private void populateCombo() {

		combo.removeAll();
		populateDefaultValue();

		ConnectionProfile connectionProfile = connectionProfile();

		if ((connectionProfile != null) && connectionProfile.isConnected()) {

			for (Iterator<String> iter = CollectionTools.sort(values()); iter.hasNext(); ) {
				combo.add(iter.next());
			}
		}

		updateSelectedItem();
	}

	/**
	 * Adds the default value to the combo if one exists.
	 */
	private void populateDefaultValue() {

		String defaultValue = (subject() != null) ? defaultValue() : null;

		if (defaultValue != null) {
			combo.add(NLS.bind(
				JptUiMappingsMessages.ColumnComposite_defaultWithOneParam,
				defaultValue
			));
		}
		else {
			combo.add(JptUiMappingsMessages.ColumnComposite_defaultEmpty);
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);
		updateSelectedItem();
	}

	/**
	 * The
	 *
	 * @param schema
	 */
	protected void schemaChanged(Schema schema) {
	}

	/**
	 * Sets the given value as the new value.
	 *
	 * @param value The new value to send to the model object
	 */
	protected abstract void setValue(String value);

	/**
	 * The
	 *
	 * @param catalog
	 */
	protected void tableChanged(Table table) {
	}

	/**
	 * Updates the selected item by selected the current value, if not
	 * <code>null</code>, or select the default value if one is available,
	 * otherwise remove the selection.
	 * <p>
	 * <b>Note:</b> It seems the text can be shown as truncated, changing the
	 * selection to (0, 0) makes the entire text visible.
	 */
	private void updateSelectedItem() {

		T subject = subject();

		String value         = (subject != null) ? value()        : null;
		String defaultValue  = (subject != null) ? defaultValue() : null;
		String displayString = JptUiMappingsMessages.ColumnComposite_defaultEmpty;

		if (defaultValue != null) {
			displayString = NLS.bind(
				JptUiMappingsMessages.ColumnComposite_defaultWithOneParam,
				defaultValue
			);
		}

		// Make sure the default value is up to date
		if (!combo.getItem(0).equals(displayString)) {
			combo.remove(0);
			combo.add(displayString, 0);
		}

		// Select the new value
		if (value != null) {
			combo.setText(value);
		}
		// Select the default value
		else {
			combo.select(0);
		}

		combo.setSelection(new Point(0, 0));
	}

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

		if ((subject == null) && !isBuildSubjectAllowed()) {
			return;
		}

		String oldValue = (subject != null) ? value() : null;

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// Convert the default value to null
		if (value != null &&
		    getCombo().getItemCount() > 0 &&
		    value.equals(getCombo().getItem(0)))
		{
			value = null;
		}

		// Nothing to change
		if ((oldValue == value) && value == null) {
			return;
		}

		// Build the subject before setting the value
		if (subject == null) {
			buildSubject();
		}

		// Set the new value
		if ((value != null) && (oldValue == null) ||
		   ((oldValue != null) && !oldValue.equals(value))) {

			setPopulating(true);

			try {
				setValue(value);
			}
			finally {
				setPopulating(false);
			}
		}
	}

	/**
	 * Retrieves the possible values, which will be added to the combo during
	 * population.
	 *
	 * @return A non-<code>null</code> <code>Iterator</code> of the possible
	 * choices to be added to the combo
	 */
	protected abstract Iterator<String> values();
}