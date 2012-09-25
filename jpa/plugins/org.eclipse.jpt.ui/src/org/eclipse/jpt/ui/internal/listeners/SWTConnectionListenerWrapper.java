/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.listeners;

import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Sequence;
import org.eclipse.jpt.db.Table;
import org.eclipse.swt.widgets.Display;

/**
 * Wrap another connection listener and forward events to it on the SWT
 * UI thread, asynchronously if necessary. If the event arrived on the UI
 * thread that is probably because it was initiated by a UI widget; as a
 * result, we want to loop back synchronously so the events can be
 * short-circuited.
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
		if (this.isExecutingOnUIThread()) {
			this.opened_(profile);
		} else {
			this.executeOnUIThread(this.buildOpenedRunnable(profile));
		}
	}

	public void modified(ConnectionProfile profile) {
		if (this.isExecutingOnUIThread()) {
			this.modified_(profile);
		} else {
			this.executeOnUIThread(this.buildModifiedRunnable(profile));
		}
	}

	public boolean okToClose(ConnectionProfile profile) {
		if (this.isExecutingOnUIThread()) {
			return this.okToClose_(profile);
		}
		this.executeOnUIThread(this.buildOkToCloseRunnable(profile));
		return true;
	}

	public void aboutToClose(ConnectionProfile profile) {
		if (this.isExecutingOnUIThread()) {
			this.aboutToClose_(profile);
		} else {
			this.executeOnUIThread(this.buildAboutToCloseRunnable(profile));
		}
	}

	public void closed(ConnectionProfile profile) {
		if (this.isExecutingOnUIThread()) {
			this.closed_(profile);
		} else {
			this.executeOnUIThread(this.buildClosedRunnable(profile));
		}
	}

	public void databaseChanged(ConnectionProfile profile, Database database) {
		if (this.isExecutingOnUIThread()) {
			this.databaseChanged_(profile, database);
		} else {
			this.executeOnUIThread(this.buildDatabaseChangedRunnable(profile, database));
		}
	}

	public void catalogChanged(ConnectionProfile profile, Catalog catalog) {
		if (this.isExecutingOnUIThread()) {
			this.catalogChanged_(profile, catalog);
		} else {
			this.executeOnUIThread(this.buildCatalogChangedRunnable(profile, catalog));
		}
	}

	public void schemaChanged(ConnectionProfile profile, Schema schema) {
		if (this.isExecutingOnUIThread()) {
			this.schemaChanged_(profile, schema);
		} else {
			this.executeOnUIThread(this.buildSchemaChangedRunnable(profile, schema));
		}
	}

	public void sequenceChanged(ConnectionProfile profile, Sequence sequence) {
		if (this.isExecutingOnUIThread()) {
			this.sequenceChanged_(profile, sequence);
		} else {
			this.executeOnUIThread(this.buildSequenceChangedRunnable(profile, sequence));
		}
	}

	public void tableChanged(ConnectionProfile profile, Table table) {
		if (this.isExecutingOnUIThread()) {
			this.tableChanged_(profile, table);
		} else {
			this.executeOnUIThread(this.buildTableChangedRunnable(profile, table));
		}
	}

	public void columnChanged(ConnectionProfile profile, Column column) {
		if (this.isExecutingOnUIThread()) {
			this.columnChanged_(profile, column);
		} else {
			this.executeOnUIThread(this.buildColumnChangedRunnable(profile, column));
		}
	}

	public void foreignKeyChanged(ConnectionProfile profile, ForeignKey foreignKey) {
		if (this.isExecutingOnUIThread()) {
			this.foreignKeyChanged_(profile, foreignKey);
		} else {
			this.executeOnUIThread(this.buildForeignKeyChangedRunnable(profile, foreignKey));
		}
	}

	private Runnable buildOpenedRunnable(final ConnectionProfile profile) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.opened_(profile);
			}
			@Override
			public String toString() {
				return "opened"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildModifiedRunnable(final ConnectionProfile profile) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.modified_(profile);
			}
			@Override
			public String toString() {
				return "modified"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildOkToCloseRunnable(final ConnectionProfile profile) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.okToClose_(profile);
			}
			@Override
			public String toString() {
				return "OK to close"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildAboutToCloseRunnable(final ConnectionProfile profile) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.aboutToClose_(profile);
			}
			@Override
			public String toString() {
				return "about to close"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildClosedRunnable(final ConnectionProfile profile) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.closed_(profile);
			}
			@Override
			public String toString() {
				return "closed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildDatabaseChangedRunnable(final ConnectionProfile profile, final Database database) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.databaseChanged_(profile, database);
			}
			@Override
			public String toString() {
				return "database changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildCatalogChangedRunnable(final ConnectionProfile profile, final Catalog catalog) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.catalogChanged_(profile, catalog);
			}
			@Override
			public String toString() {
				return "catalog changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildSchemaChangedRunnable(final ConnectionProfile profile, final Schema schema) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.schemaChanged_(profile, schema);
			}
			@Override
			public String toString() {
				return "schema changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildSequenceChangedRunnable(final ConnectionProfile profile, final Sequence sequence) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.sequenceChanged_(profile, sequence);
			}
			@Override
			public String toString() {
				return "sequence changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildTableChangedRunnable(final ConnectionProfile profile, final Table table) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.tableChanged_(profile, table);
			}
			@Override
			public String toString() {
				return "table changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildColumnChangedRunnable(final ConnectionProfile profile, final Column column) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.columnChanged_(profile, column);
			}
			@Override
			public String toString() {
				return "column changed"; //$NON-NLS-1$
			}
		};
	}

	private Runnable buildForeignKeyChangedRunnable(final ConnectionProfile profile, final ForeignKey foreignKey) {
		return new Runnable() {
			public void run() {
				SWTConnectionListenerWrapper.this.foreignKeyChanged_(profile, foreignKey);
			}
			@Override
			public String toString() {
				return "foreign key changed"; //$NON-NLS-1$
			}
		};
	}

	private boolean isExecutingOnUIThread() {
		return Display.getCurrent() != null;
	}

	/**
	 * {@link Display#asyncExec(Runnable)} seems to work OK;
	 * but using {@link Display#syncExec(Runnable)} can somtimes make things
	 * more predictable when debugging, at the risk of deadlocks.
	 */
	private void executeOnUIThread(Runnable r) {
		Display.getDefault().asyncExec(r);
//		Display.getDefault().syncExec(r);
	}

	void opened_(ConnectionProfile profile) {
		this.listener.opened(profile);
	}

	void modified_(ConnectionProfile profile) {
		this.listener.modified(profile);
	}

	boolean okToClose_(ConnectionProfile profile) {
		return this.listener.okToClose(profile);
	}

	void aboutToClose_(ConnectionProfile profile) {
		this.listener.aboutToClose(profile);
	}

	void closed_(ConnectionProfile profile) {
		this.listener.closed(profile);
	}

	void databaseChanged_(ConnectionProfile profile, Database database) {
		this.listener.databaseChanged(profile, database);
	}

	void catalogChanged_(ConnectionProfile profile, Catalog catalog) {
		this.listener.catalogChanged(profile, catalog);
	}

	void schemaChanged_(ConnectionProfile profile, Schema schema) {
		this.listener.schemaChanged(profile, schema);
	}

	void sequenceChanged_(ConnectionProfile profile, Sequence sequence) {
		this.listener.sequenceChanged(profile, sequence);
	}

	void tableChanged_(ConnectionProfile profile, Table table) {
		this.listener.tableChanged(profile, table);
	}

	void columnChanged_(ConnectionProfile profile, Column column) {
		this.listener.columnChanged(profile, column);
	}

	void foreignKeyChanged_(ConnectionProfile profile, ForeignKey foreignKey) {
		this.listener.foreignKeyChanged(profile, foreignKey);
	}

	@Override
	public String toString() {
		return "SWT(" + this.listener.toString() + ')'; //$NON-NLS-1$
	}

}
