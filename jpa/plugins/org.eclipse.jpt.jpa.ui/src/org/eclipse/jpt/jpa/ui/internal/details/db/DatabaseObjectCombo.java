/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.db;

import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.internal.widgets.ComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ConnectionListener;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Sequence;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.internal.listeners.SWTConnectionListenerWrapper;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.widgets.Composite;

/**
 * This abstract pane keeps a combo in sync with the database objects
 * when a connection is active.
 *
 * @see CatalogCombo
 * @see ColumnCombo
 * @see SchemaCombo
 * @see SequenceCombo
 * @see TableCombo
 */
@SuppressWarnings("nls")
public abstract class DatabaseObjectCombo<T extends JpaModel>
	extends ComboPane<T>
{
	/**
	 * The listener added to the <code>ConnectionProfile</code>.
	 * It keeps the combo in sync with the database metadata.
	 */
	private ConnectionListener connectionListener;
	
	private PropertyChangeListener connectionProfileListener;
	
	
	// ********** constructors **********
	
	protected DatabaseObjectCombo(
			Pane<? extends T> parentPane, 
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	protected DatabaseObjectCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	protected DatabaseObjectCombo(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
	}

	
	// ********** initialization **********
	
	@Override
	protected void initialize() {
		super.initialize();
		this.connectionListener = this.buildConnectionListener();
		this.connectionProfileListener = this.buildConnectionProfileListener();
	}
	
	protected ConnectionListener buildConnectionListener() {
		return new SWTConnectionListenerWrapper(this.buildConnectionListener_());
	}
	
	protected ConnectionListener buildConnectionListener_() {
		return new LocalConnectionListener();
	}
	
	protected PropertyChangeListener buildConnectionProfileListener() {
		return SWTListenerTools.wrap(this.buildConnectionProfileListener_());
	}
	
	protected PropertyChangeListener buildConnectionProfileListener_() {
		return new ConnectionProfileListener();
	}

	public class ConnectionProfileListener
		implements PropertyChangeListener
	{
		public void propertyChanged(PropertyChangeEvent event) {
			DatabaseObjectCombo.this.connectionProfileChanged(event);
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this, DatabaseObjectCombo.this);
		}
	}
	
	protected void connectionProfileChanged(PropertyChangeEvent event) {
		if (event.getOldValue() != null) {
			((ConnectionProfile) event.getOldValue()).removeConnectionListener(this.connectionListener);
		}
		if (event.getNewValue() != null) {
			((ConnectionProfile) event.getNewValue()).addConnectionListener(this.connectionListener);			
		}
		this.repopulateComboBox();
	}
	
	
	// ********** overrides **********
	
	@Override
	protected void engageListeners_(T subject) {
		super.engageListeners_(subject);

		subject.getJpaProject().getDataSource().addPropertyChangeListener(JpaDataSource.CONNECTION_PROFILE_PROPERTY, this.connectionProfileListener);
		ConnectionProfile cp = subject.getJpaProject().getConnectionProfile();
		if (cp != null) {
			cp.addConnectionListener(this.connectionListener);
		}
	}
	
	@Override
	protected void disengageListeners_(T subject) {
		ConnectionProfile cp = subject.getJpaProject().getConnectionProfile();
		if (cp != null) {
			cp.removeConnectionListener(this.connectionListener);
		}
		subject.getJpaProject().getDataSource().removePropertyChangeListener(JpaDataSource.CONNECTION_PROFILE_PROPERTY, this.connectionProfileListener);

		super.disengageListeners_(subject);
	}
	
	@Override
	protected final Iterable<String> getValues() {
		return this.connectionProfileIsActive() ? this.getValues_() : EmptyIterable.<String>instance();
	}
	
	/**
	 * Called only when connection profile is active
	 */
	protected abstract Iterable<String> getValues_();
	
	
	// ********** convenience methods **********
	
	/**
	 * Return the subject's JPA project.
	 * Allow subclasses to override this method, so we can still get the JPA
	 * project even when the subject is null.
	 */
	protected JpaProject getJpaProject() {
		T subject = this.getSubject();
		return (subject == null) ? null : subject.getJpaProject();
	}
	
	/**
	 * Return the subject's connection profile.
	 */
	protected final ConnectionProfile getConnectionProfile() {
		JpaProject jpaProject = this.getJpaProject();
		return (jpaProject == null) ? null : jpaProject.getConnectionProfile();
	}
	
	/**
	 * Return whether the subject's connection profile is active.
	 */
	protected final boolean connectionProfileIsActive() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp != null) && cp.isActive();
	}
	
	/**
	 * Returns the subject's database.
	 */
	protected final Database getDatabase() {
		ConnectionProfile cp = this.getConnectionProfile();
		return (cp == null) ? null : cp.getDatabase();
	}
	
	
	// ********** connection listener callbacks **********
	
	protected final void databaseChanged(Database database) {
		if ( ! this.comboBox.isDisposed()) {
			this.databaseChanged_(database);
		}
	}
	
	protected void databaseChanged_(@SuppressWarnings("unused") Database database) {
		// do nothing by default
	}
	
	protected final void catalogChanged(Catalog catalog) {
		if ( ! this.comboBox.isDisposed()) {
			this.catalogChanged_(catalog);
		}
	}
	
	protected void catalogChanged_(@SuppressWarnings("unused") Catalog catalog) {
		// do nothing by default
	}
	
	protected final void schemaChanged(Schema schema) {
		if ( ! this.comboBox.isDisposed()) {
			this.schemaChanged_(schema);
		}
	}
	
	protected void schemaChanged_(@SuppressWarnings("unused") Schema schema) {
		// do nothing by default
	}
	
	protected final void sequenceChanged(Sequence sequence) {
		if ( ! this.comboBox.isDisposed()) {
			this.sequenceChanged_(sequence);
		}
	}
	
	protected void sequenceChanged_(@SuppressWarnings("unused") Sequence sequence) {
		// do nothing by default
	}
	
	protected final void tableChanged(Table table) {
		if ( ! this.comboBox.isDisposed()) {
			this.tableChanged_(table);
		}
	}
	
	protected void tableChanged_(@SuppressWarnings("unused") Table table) {
		// do nothing by default
	}
	
	protected final void columnChanged(Column column) {
		if ( ! this.comboBox.isDisposed()) {
			this.columnChanged_(column);
		}
	}
	
	protected void columnChanged_(@SuppressWarnings("unused") Column column) {
		// do nothing by default
	}
	
	protected final void foreignKeyChanged(ForeignKey foreignKey) {
		if ( ! this.comboBox.isDisposed()) {
			this.foreignKeyChanged_(foreignKey);
		}
	}
	
	protected void foreignKeyChanged_(@SuppressWarnings("unused") ForeignKey foreignKey) {
		// do nothing by default
	}
	
	// broaden accessibility a bit
	@Override
	protected void repopulateComboBox() {
		super.repopulateComboBox();
	}
	
	
	// ********** connection listener **********
	
	public class LocalConnectionListener 
		implements ConnectionListener 
	{
		public LocalConnectionListener() {
			super();
		}
		
		public void opened(ConnectionProfile profile) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "opened: {0}", profile);
			DatabaseObjectCombo.this.repopulateComboBox();
		}
		
		public void modified(ConnectionProfile profile) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "modified: {0}", profile);
			DatabaseObjectCombo.this.repopulateComboBox();
		}
		
		public boolean okToClose(ConnectionProfile profile) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "OK to close: {0}", profile);
			return true;
		}
		
		public void aboutToClose(ConnectionProfile profile) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "about to close: {0}", profile);
		}
		
		public void closed(ConnectionProfile profile) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "closed: {0}", profile);
			DatabaseObjectCombo.this.repopulateComboBox();
		}
		
		public void databaseChanged(ConnectionProfile profile, Database database) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "database changed: {0}", database);
			DatabaseObjectCombo.this.databaseChanged(database);
		}
		
		public void catalogChanged(ConnectionProfile profile, Catalog catalog) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "catalog changed: {0}", catalog);
			DatabaseObjectCombo.this.catalogChanged(catalog);
		}
		
		public void schemaChanged(ConnectionProfile profile, Schema schema) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "schema changed: {0}", schema);
			DatabaseObjectCombo.this.schemaChanged(schema);
		}
		
		public void sequenceChanged(ConnectionProfile profile, Sequence sequence) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "sequence changed: {0}", sequence);
			DatabaseObjectCombo.this.sequenceChanged(sequence);
		}
		
		public void tableChanged(ConnectionProfile profile, Table table) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "table changed: {0}", table);
			DatabaseObjectCombo.this.tableChanged(table);
		}
		
		public void columnChanged(ConnectionProfile profile, Column column) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "column changed: {0}", column);
			DatabaseObjectCombo.this.columnChanged(column);
		}
		
		public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey) {
			JptJpaUiPlugin.instance().trace(TRACE_OPTION, "foreign key changed: {0}", foreignKey);
			DatabaseObjectCombo.this.foreignKeyChanged(foreignKey);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, DatabaseObjectCombo.this);
		}
	}	
	/* CU private */ static final String TRACE_OPTION = DatabaseObjectCombo.class.getSimpleName();
}
