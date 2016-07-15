/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import java.util.Comparator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.connection.Connection2_0;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 *  JdbcConnectionPropertiesComposite
 */
@SuppressWarnings("nls")
public class JdbcConnectionPropertiesComposite2_0 extends Pane<Connection2_0>
{
	/**
	 * The constant ID used to retrieve the dialog settings.
	 */
	private static final String DIALOG_SETTINGS = "org.eclipse.jpt.jpa.ui.internal.jpa2.dialogs.ConnectionDialog";

	public JdbcConnectionPropertiesComposite2_0(Pane<Connection2_0> parentComposite, Composite parent, PropertyValueModel<Boolean> enabledModel) {

		super(parentComposite, parent, enabledModel);
	}

	private ModifiablePropertyValueModel<String> buildPasswordModel() {
		return new PropertyAspectAdapterXXXX<Connection2_0, String>(this.getSubjectHolder(), Connection2_0.PASSWORD_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getPassword();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setPassword(value);
			}
		};
	}

	private Runnable buildPopulateFromConnectionAction() {
		return new Runnable() {
			public void run() {
				promptConnection();
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildUrlModel() {
		return new PropertyAspectAdapterXXXX<Connection2_0, String>(this.getSubjectHolder(), Connection2_0.URL_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getUrl();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setUrl(value);
			}
		};
	}

	private ModifiablePropertyValueModel<String> buildUserModel() {
		return new PropertyAspectAdapterXXXX<Connection2_0, String>(this.getSubjectHolder(), Connection2_0.USER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getUser();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setUser(value);
			}
		};
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0); //2 columns
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Populate from Connection hyperlink
		Hyperlink hyperLink = this.addHyperlink(
			container,
			JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_POPULATE_FROM_CONNECTION_HYPER_LINK,
			this.buildPopulateFromConnectionAction()
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		hyperLink.setLayoutData(gridData);
		

		// Driver
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_DRIVER_LABEL);
		this.initializeJdbcDriverClassChooser(container);

		// Url
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_URL_LABEL);
		this.addText(container, this.buildUrlModel());

		// User
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_USER_LABEL);
		this.addText(container, this.buildUserModel());

		// Password
		this.addLabel(container, JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_PASSWORD_LABEL);
		this.addPasswordText(container, this.buildPasswordModel());
	}

	void promptConnection() {

		ConnectionSelectionDialog dialog = new ConnectionSelectionDialog();

		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		String name = (String) dialog.getResult()[0];
		ConnectionProfileFactory factory = this.getConnectionProfileFactory();
		ConnectionProfile cp = (factory == null) ? null : factory.buildConnectionProfile(name);

		Connection2_0 connection = getSubject();
		connection.setUrl((cp == null) ? "" : cp.getURL());
		connection.setUser((cp == null) ? "" : cp.getUserName());
		connection.setPassword((cp == null) ? "" : cp.getUserPassword());
		connection.setDriver((cp == null) ? "" : cp.getDriverClassName());
	}

	ConnectionProfileFactory getConnectionProfileFactory() {
		// we allow the user to select any connection profile and simply
		// take the settings from it (user, password, etc.) and give them
		// to the persistence connection, so we go
		// to the db plug-in directly to get the factory
		JpaWorkspace jpaWorkspace = this.getJpaWorkspace();
		return (jpaWorkspace == null) ? null : jpaWorkspace.getConnectionProfileFactory();
	}

	private JpaWorkspace getJpaWorkspace() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench == null) ? null : jpaWorkbench.getJpaWorkspace();
	}

	private JpaWorkbench getJpaWorkbench() {
		return WorkbenchTools.getAdapter(JpaWorkbench.class);
	}

	// broaden access a bit
	Shell getShell_() {
		return this.getShell();
	}

	private ClassChooserPane<Connection2_0> initializeJdbcDriverClassChooser(Composite container) {

		return new ClassChooserPane<Connection2_0>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextModel() {
				return new PropertyAspectAdapterXXXX<Connection2_0, String>(
							this.getSubjectHolder(), Connection2_0.DRIVER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDriver();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setDriver(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return this.getSubject().getDriver();
			}
			
			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				this.getSubject().setDriver(className);				
			}
		};
	}
	/**
	 * This dialog shows the list of possible connection names and lets the user
	 * the option to filter them using a search field.
	 */
	protected class ConnectionSelectionDialog extends FilteredItemsSelectionDialog {

		/**
		 * Creates a new <code>MappingSelectionDialog</code>.
		 */
		protected ConnectionSelectionDialog() {
			super(JdbcConnectionPropertiesComposite2_0.this.getShell_(), false);
			this.setMessage(JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_MESSAGE);
			this.setTitle(JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_TITLE);
			this.setListLabelProvider(this.buildLabelProvider());
			this.setDetailsLabelProvider(this.buildLabelProvider());
		}

		protected ILabelProvider buildLabelProvider() {
			return new LabelProvider() {
				@Override
				public Image getImage(Object element) {
					return null;
				}

				@Override
				public String getText(Object element) {
					return (element == null) ? "" : element.toString();
				}
			};
		}

		@Override
		protected Control createExtendedContentArea(Composite parent) {
			return null;
		}

		@Override
		protected ItemsFilter createFilter() {
			return new ConnectionItemsFilter();
		}

		@Override
		protected void fillContentProvider(AbstractContentProvider provider,
		                                   ItemsFilter itemsFilter,
		                                   IProgressMonitor monitor) throws CoreException {

			monitor.beginTask(null, -1);

			try {
				// Add the connection names to the dialog
				for (String name : this.getConnectionProfileNames()) {
					provider.add(name, itemsFilter);
				}
			}
			finally {
				monitor.done();
			}
		}

		private Iterable<String> getConnectionProfileNames() {
			ConnectionProfileFactory factory = JdbcConnectionPropertiesComposite2_0.this.getConnectionProfileFactory();
			return (factory == null) ? IterableTools.<String>emptyIterable() : factory.getConnectionProfileNames();
		}

		@Override
		protected IDialogSettings getDialogSettings() {
			return JptJpaUiPlugin.instance().getDialogSettings(DIALOG_SETTINGS);
		}

		@Override
		public String getElementName(Object object) {
			return object.toString();
		}

		@Override
		protected Comparator<String> getItemsComparator() {
			return new Comparator<String>() {
				public int compare(String item1, String item2) {
					return item1.compareTo(item2);
				}
			};
		}

		@Override
		protected IStatus validateItem(Object item) {
			int severity = (item == null) ? IStatus.ERROR : IStatus.OK;
			return JptJpaUiPlugin.instance().buildStatus(severity);
		}

		/**
		 * Create the filter responsible to remove any connection name based on
		 * the pattern entered in the text field.
		 */
		private class ConnectionItemsFilter extends ItemsFilter {

			/**
			 * Creates a new <code>ConnectionItemsFilter</code>.
			 */
			ConnectionItemsFilter() {

				super();

				// Make sure that if the pattern is empty, we specify * in order
				// to show all the mapping types
				if (StringTools.isBlank(getPattern())) {
					this.patternMatcher.setPattern("*");
				}
			}

			/*
			 * (non-Javadoc)
			 */
			@Override
			public boolean isConsistentItem(Object item) {
				return true;
			}

			/*
			 * (non-Javadoc)
			 */
			@Override
			public boolean matchItem(Object item) {
				return matches(item.toString());
			}
		}
	}
}
