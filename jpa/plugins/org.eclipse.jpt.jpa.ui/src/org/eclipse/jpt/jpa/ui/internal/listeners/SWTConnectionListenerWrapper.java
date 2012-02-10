/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.listeners;

import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ConnectionListener;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Sequence;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Wrap another connection listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary.
 */
public class SWTConnectionListenerWrapper
	implements ConnectionListener
{

	private final ConnectionListener listener;

	public SWTConnectionListenerWrapper(ConnectionListener listener) {
		super();
		if (listener == null) {
			throw new NullPointerException();
		}
		this.listener = listener;
	}

	public void opened(ConnectionProfile profile) {
		this.execute(new OpenedRunnable(profile));
	}

	/* CU private */ class OpenedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		OpenedRunnable(ConnectionProfile profile) {
			super();
			this.profile = profile;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.opened_(this.profile);
		}
	}

	void opened_(ConnectionProfile profile) {
		this.listener.opened(profile);
	}

	public void modified(ConnectionProfile profile) {
		this.execute(new ModifiedRunnable(profile));
	}

	/* CU private */ class ModifiedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		ModifiedRunnable(ConnectionProfile profile) {
			super();
			this.profile = profile;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.modified_(this.profile);
		}
	}

	void modified_(ConnectionProfile profile) {
		this.listener.modified(profile);
	}

	public boolean okToClose(ConnectionProfile profile) {
		OkToCloseRunnable r = new OkToCloseRunnable(profile);
		this.execute(r);
		return r.result;  // result is unpredictable...
	}

	/* CU private */ class OkToCloseRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		volatile boolean result = true;
		OkToCloseRunnable(ConnectionProfile profile) {
			super();
			this.profile = profile;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.okToClose_(this.profile);
		}
	}

	boolean okToClose_(ConnectionProfile profile) {
		return this.listener.okToClose(profile);
	}

	public void aboutToClose(ConnectionProfile profile) {
		this.execute(new AboutToCloseRunnable(profile));
	}

	/* CU private */ class AboutToCloseRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		AboutToCloseRunnable(ConnectionProfile profile) {
			super();
			this.profile = profile;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.aboutToClose_(this.profile);
		}
	}

	void aboutToClose_(ConnectionProfile profile) {
		this.listener.aboutToClose(profile);
	}

	public void closed(ConnectionProfile profile) {
		this.execute(new ClosedRunnable(profile));
	}

	/* CU private */ class ClosedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		ClosedRunnable(ConnectionProfile profile) {
			super();
			this.profile = profile;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.closed_(this.profile);
		}
	}

	void closed_(ConnectionProfile profile) {
		this.listener.closed(profile);
	}

	public void databaseChanged(ConnectionProfile profile, Database database) {
		this.execute(new DatabaseChangedRunnable(profile, database));
	}

	/* CU private */ class DatabaseChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Database database;
		DatabaseChangedRunnable(ConnectionProfile profile, Database database) {
			super();
			this.profile = profile;
			this.database = database;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.databaseChanged_(this.profile, this.database);
		}
	}

	void databaseChanged_(ConnectionProfile profile, Database database) {
		this.listener.databaseChanged(profile, database);
	}

	public void catalogChanged(ConnectionProfile profile, Catalog catalog) {
		this.execute(new CatalogChangedRunnable(profile, catalog));
	}

	/* CU private */ class CatalogChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Catalog catalog;
		CatalogChangedRunnable(ConnectionProfile profile, Catalog catalog) {
			super();
			this.profile = profile;
			this.catalog = catalog;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.catalogChanged_(this.profile, this.catalog);
		}
	}

	void catalogChanged_(ConnectionProfile profile, Catalog catalog) {
		this.listener.catalogChanged(profile, catalog);
	}

	public void schemaChanged(ConnectionProfile profile, Schema schema) {
		this.execute(new SchemaChangedRunnable(profile, schema));
	}

	/* CU private */ class SchemaChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Schema schema;
		SchemaChangedRunnable(ConnectionProfile profile, Schema schema) {
			super();
			this.profile = profile;
			this.schema = schema;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.schemaChanged_(this.profile, this.schema);
		}
	}

	void schemaChanged_(ConnectionProfile profile, Schema schema) {
		this.listener.schemaChanged(profile, schema);
	}

	public void sequenceChanged(ConnectionProfile profile, Sequence sequence) {
		this.execute(new SequenceChangedRunnable(profile, sequence));
	}

	/* CU private */ class SequenceChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Sequence sequence;
		SequenceChangedRunnable(ConnectionProfile profile, Sequence sequence) {
			super();
			this.profile = profile;
			this.sequence = sequence;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.sequenceChanged_(this.profile, this.sequence);
		}
	}

	void sequenceChanged_(ConnectionProfile profile, Sequence sequence) {
		this.listener.sequenceChanged(profile, sequence);
	}

	public void tableChanged(ConnectionProfile profile, Table table) {
		this.execute(new TableChangedRunnable(profile, table));
	}

	/* CU private */ class TableChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Table table;
		TableChangedRunnable(ConnectionProfile profile, Table table) {
			super();
			this.profile = profile;
			this.table = table;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.tableChanged_(this.profile, this.table);
		}
	}

	void tableChanged_(ConnectionProfile profile, Table table) {
		this.listener.tableChanged(profile, table);
	}

	public void columnChanged(ConnectionProfile profile, Column column) {
		this.execute(new ColumnChangedRunnable(profile, column));
	}

	/* CU private */ class ColumnChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final Column column;
		ColumnChangedRunnable(ConnectionProfile profile, Column column) {
			super();
			this.profile = profile;
			this.column = column;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.columnChanged_(this.profile, this.column);
		}
	}

	void columnChanged_(ConnectionProfile profile, Column column) {
		this.listener.columnChanged(profile, column);
	}

	public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey) {
		this.execute(new ForeignKeyChangedRunnable(profile, foreignKey));
	}

	/* CU private */ class ForeignKeyChangedRunnable
		extends RunnableAdapter
	{
		private final ConnectionProfile profile;
		private final ForeignKey foreignKey;
		ForeignKeyChangedRunnable(ConnectionProfile profile, ForeignKey foreignKey) {
			super();
			this.profile = profile;
			this.foreignKey = foreignKey;
		}
		@Override
		public void run() {
			SWTConnectionListenerWrapper.this.foreignKeyChanged_(this.profile, this.foreignKey);
		}
	}

	void foreignKeyChanged_(ConnectionProfile profile, ForeignKey foreignKey) {
		this.listener.foreignKeyChanged(profile, foreignKey);
	}

	/**
	 * {@link SWTUtil#execute(Runnable)} seems to work OK;
	 * but using {@link SWTUtil#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void execute(Runnable r) {
		SWTUtil.execute(r);
//		SWTUtil.syncExec(r);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener + ')'; //$NON-NLS-1$
	}
}
