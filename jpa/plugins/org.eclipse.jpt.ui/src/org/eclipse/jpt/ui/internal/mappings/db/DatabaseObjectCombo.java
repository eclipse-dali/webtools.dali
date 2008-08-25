/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.db;

import java.util.Iterator;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Sequence;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
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
public abstract class DatabaseObjectCombo<T extends JpaNode> extends Pane<T>
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
	protected DatabaseObjectCombo(Pane<? extends T> parentPane,
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
	protected DatabaseObjectCombo(Pane<?> parentPane,
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
	protected DatabaseObjectCombo(PropertyValueModel<? extends T> subjectHolder,
	                                      Composite parent,
	                                      WidgetFactory widgetFactory)
	{
		super(subjectHolder, parent, widgetFactory);
	}

	private ConnectionListener buildConnectionListener() {

		return new ConnectionListener() {

			public void aboutToClose(ConnectionProfile profile) {
				log(Tracing.UI_DB, "aboutToClose");
			}

			public void catalogChanged(ConnectionProfile profile,
			                          final Catalog catalog) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "catalogChanged: " + catalog.getName());

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.catalogChanged(catalog);
						}
					}
				});
			}

			public void closed(ConnectionProfile profile) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "closed");

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.repopulate();
						}
					}
				});
			}

			public void columnChanged(ConnectionProfile profile,
			                         final Column column) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "columnChanged: " + column.getName());

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.columnChanged(column);
						}
					}
				});
			}

			public void databaseChanged(ConnectionProfile profile,
			                            Database database) {

				log(Tracing.UI_DB, "databaseChanged");
			}

			public void foreignKeyChanged(ConnectionProfile profile,
			                         final ForeignKey foreignKey) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "foreignKeyChanged: " + foreignKey.getName());

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.foreignKeyChanged(foreignKey);
						}
					}
				});
			}

			public void modified(ConnectionProfile profile) {
				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "modified");

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.repopulate();
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
							DatabaseObjectCombo.this.repopulate();
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
							DatabaseObjectCombo.this.schemaChanged(schema);
						}
					}
				});
			}

			public void sequenceChanged(ConnectionProfile profile,
			                          final Sequence sequence) {

				SWTUtil.asyncExec(new Runnable() {
					public void run() {
						log(Tracing.UI_DB, "sequenceChanged: " + sequence.getName());

						if (!getCombo().isDisposed()) {
							DatabaseObjectCombo.this.sequenceChanged(sequence);
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
							DatabaseObjectCombo.this.tableChanged(table);
						}
					}
				});
			}
		};
	}

	private ModifyListener buildModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					if (combo.getData("populating") != Boolean.TRUE) {//check !TRUE because null is a possibility as well
						valueChanged(combo.getText());
					}
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
	 * The
	 *
	 * @param catalog
	 */
	protected void catalogChanged(Catalog catalog) {
	}

	/**
	 * Makes sure the combo shows nothing instead of the default value because
	 * the focus is still on the combo. The user can start typing something and
	 * we don't want to start the typing after the default value.
	 */
	private void clearDefaultValue() {

		if (this.combo.isFocusControl()) {

			setPopulating(true);

			try {
				combo.setText("");
			}
			finally {
				setPopulating(false);
			}
		}
	}

	/**
	 * The
	 *
	 * @param column
	 */
	protected void columnChanged(Column column) {
	}

	/**
	 * Returns whether the JPA project's connection profile is active.
	 *
	 * @return Whether the JPA project's connection profile is active.
	 */
	protected final boolean connectionProfileIsActive() {
		JpaProject jpaProject = this.getJpaProject();
		return (jpaProject == null) ? false : jpaProject.getDataSource().connectionProfileIsActive();
	}

	/**
	 * Returns the JPA project's connection profile.
	 *
	 * @return The connection set in the project's properties or <code>null</code>
	 * if it could not being retrieved
	 */
	protected final ConnectionProfile getConnectionProfile() {
		JpaProject jpaProject = this.getJpaProject();
		return (jpaProject == null) ? null : jpaProject.getConnectionProfile();
	}

	/**
	 * Returns the database associated with the active connection profile.
	 *
	 * @return The online database or a <code>null</code> instance if no
	 * connection profile was set or the
	 */
	protected final Database getDatabase() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDatabase();
	}

	/**
	 * Returns the default value, or <code>null</code> if no default is
	 * specified.
	 *
	 * @return The value that represents the default when no value was specified
	 */
	protected abstract String getDefaultValue();

	@Override
	protected void disengageListeners(T subject) {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp != null) {
			cp.removeConnectionListener(this.connectionListener);
		}

		super.disengageListeners(subject);
	}

	@Override
	protected void doPopulate() {

		super.doPopulate();
		populateCombo();
	}

	@Override
	public void enableWidgets(boolean enabled) {

		super.enableWidgets(enabled);

		if (!this.combo.isDisposed()) {
			this.combo.setEnabled(enabled);
		}
	}

	@Override
	protected void engageListeners(T subject) {
		super.engageListeners(subject);

		ConnectionProfile cp = this.getConnectionProfile();
		if (cp != null) {
			cp.addConnectionListener(this.connectionListener);
		}
	}

	/**
	 * The
	 *
	 * @param foreignKey
	 */
	protected void foreignKeyChanged(ForeignKey foreignKey) {
	}

	public final CCombo getCombo() {
		return this.combo;
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.connectionListener = buildConnectionListener();
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.combo = addEditableCCombo(container);
		this.combo.addModifyListener(buildModifyListener());
		SWTUtil.attachDefaultValueHandler(this.combo);
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
	protected JpaProject getJpaProject() {
		return getSubject() == null ? null : getSubject().getJpaProject();
	}

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

		if (this.connectionProfileIsActive()) {
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

		String defaultValue = (getSubject() != null) ? getDefaultValue() : null;

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
	 * The
	 *
	 * @param sequence
	 */
	protected void sequenceChanged(Sequence sequence) {
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
	 * @param table
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

		T subject = getSubject();

		String value         = (subject != null) ? getValue()        : null;
		String defaultValue  = (subject != null) ? getDefaultValue() : null;
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
			if (!value.equals(combo.getText())) {
				//this prevents the cursor from being set back to the beginning of the line (bug 234418).
				//The reason we are hitting this updateSelectedItem() code at all
				//is because the context model is  updating from the resource model
				//in a way that causes change notifications to be fired (the annotation is added 
				//to the resource model, change notification occurs on the update thread, 
				//and then the name is set, these 2 threads can get in the wrong order).
				//The valueChanged() method sets the populating flag to true, but in this case
				//it is already set back to false when we receive notification back from the model
				//because it has moved to the update thread and then jumps back on the UI thread.
				combo.setText(value);
			}
		}
		// Select the default value
		else {
			combo.select(0);
			
			//i think we can remove this, I don't believe the problem explained
			//in the comments of this method is happening anymore. Not removing it now because we are working on 2.0RC3
			combo.setSelection(new Point(0, 0));
		}
	}

	/**
	 * Requests the current value from the model object.
	 *
	 * @return The current value
	 */
	protected abstract String getValue();

	/**
	 * The selection has changed, update the model if required.
	 *
	 * @param value The new value
	 */
	protected void valueChanged(String value) {

		JpaNode subject = getSubject();

		if ((subject == null) && !isBuildSubjectAllowed()) {
			return;
		}

		String oldValue = (subject != null) ? getValue() : null;

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
			clearDefaultValue();
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
			combo.setData("populating", Boolean.TRUE);

			try {
				setValue(value);
			}
			finally {
				setPopulating(false);
				combo.setData("populating", Boolean.FALSE);
			}

			if (value == null) {
				clearDefaultValue();
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
