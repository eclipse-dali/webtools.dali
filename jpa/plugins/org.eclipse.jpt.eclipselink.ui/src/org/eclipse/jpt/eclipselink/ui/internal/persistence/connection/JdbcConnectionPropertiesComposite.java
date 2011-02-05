/*******************************************************************************
* Copyright (c) 2008, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
*
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.connection;

import java.util.Comparator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.eclipselink.core.context.persistence.connection.Connection;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.JptJpaDbPlugin;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.jpa2.persistence.JptUiPersistence2_0Messages;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.FilteredItemsSelectionDialog;

/**
 *  JdbcConnectionPropertiesComposite
 */
@SuppressWarnings("nls")
public class JdbcConnectionPropertiesComposite<T extends Connection> 
	extends Pane<T>
{
	/**
	 * The constant ID used to retrieve the dialog settings.
	 */
	private static final String DIALOG_SETTINGS = "org.eclipse.jpt.eclipselink.ui.dialogs.ConnectionDialog";

	public JdbcConnectionPropertiesComposite(
					Pane<T> parentComposite, 
					Composite parent) {

		super(parentComposite, parent);
	}

	private WritablePropertyValueModel<String> buildPasswordHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.PASSWORD_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getPassword();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setPassword(value);
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

	private WritablePropertyValueModel<String> buildUrlHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.URL_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getUrl();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setUrl(value);
			}
		};
	}

	private WritablePropertyValueModel<String> buildUserHolder() {
		return new PropertyAspectAdapter<Connection, String>(getSubjectHolder(), Connection.USER_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getUser();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setUser(value);
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Populate from Connection hyperlink
		this.addHyperlink(
			container,
			JptUiPersistence2_0Messages.JdbcConnectionPropertiesComposite_populateFromConnectionHyperLink,
			buildPopulateFromConnectionAction()
		);

		// Driver
		new JdbcDriverComposite(this, container);

		// Url
		this.addLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_urlLabel,
			buildUrlHolder()
		);

		// User
		this.addLabeledText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_userLabel,
			buildUserHolder()
		);

		// Password
		this.addLabeledPasswordText(
			container,
			EclipseLinkUiMessages.PersistenceXmlConnectionTab_passwordLabel,
			buildPasswordHolder()
		);

		// Bind Parameters
		new JdbcBindParametersComposite(this, container);
	}

	void promptConnection() {

		ConnectionSelectionDialog dialog = new ConnectionSelectionDialog();

		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		String name = (String) dialog.getResult()[0];
		ConnectionProfile cp = this.getConnectionProfileFactory().buildConnectionProfile(name);

		Connection connection = getSubject();
		connection.setUrl((cp == null) ? "" : cp.getURL());
		connection.setUser((cp == null) ? "" : cp.getUserName());
		connection.setPassword((cp == null) ? "" : cp.getUserPassword());
		connection.setDriver((cp == null) ? "" : cp.getDriverClassName());
	}

	ConnectionProfileFactory getConnectionProfileFactory() {
		// we allow the user to select any connection profile and simply
		// take the settings from it (user, password, etc.) and give them
		// to the EclipseLink connection, so we go
		// to the db plug-in directly to get the factory
		return JptJpaDbPlugin.getConnectionProfileFactory();
	}

	// broaden access a bit
	Shell getShell_() {
		return this.getShell();
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
			super(JdbcConnectionPropertiesComposite.this.getShell_(), false);
			setMessage(EclipseLinkUiMessages.JdbcConnectionPropertiesComposite_ConnectionDialog_Message);
			setTitle(EclipseLinkUiMessages.JdbcConnectionPropertiesComposite_ConnectionDialog_Title);
			setListLabelProvider(buildLabelProvider());
			setDetailsLabelProvider(buildLabelProvider());
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

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected Control createExtendedContentArea(Composite parent) {
			return null;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected ItemsFilter createFilter() {
			return new ConnectionItemsFilter();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected void fillContentProvider(AbstractContentProvider provider,
		                                   ItemsFilter itemsFilter,
		                                   IProgressMonitor monitor) throws CoreException {

			Iterable<String> profileNames = this.getConnectionProfileNames();
			SubMonitor sm = SubMonitor.convert(monitor, CollectionTools.size(profileNames));

			// Add the connection names to the dialog
			for (String name : profileNames) {
				provider.add(name, itemsFilter);
				sm.worked(1);
			}
		}

		private Iterable<String> getConnectionProfileNames() {
			return JdbcConnectionPropertiesComposite.this.getConnectionProfileFactory().getConnectionProfileNames();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected IDialogSettings getDialogSettings() {

			IDialogSettings dialogSettings = JptUiPlugin.instance().getDialogSettings();
			IDialogSettings settings = dialogSettings.getSection(DIALOG_SETTINGS);

			if (settings == null) {
				settings = dialogSettings.addNewSection(DIALOG_SETTINGS);
			}

			return settings;
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		public String getElementName(Object object) {
			return object.toString();
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected Comparator<String> getItemsComparator() {
			return new Comparator<String>() {
				public int compare(String item1, String item2) {
					return item1.compareTo(item2);
				}
			};
		}

		/*
		 * (non-Javadoc)
		 */
		@Override
		protected IStatus validateItem(Object item) {

			if (item == null) {
				return new Status(IStatus.ERROR, JptUiPlugin.PLUGIN_ID, IStatus.ERROR, "", null);
			}

			return Status.OK_STATUS;
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
				if (StringTools.stringIsEmpty(getPattern())) {
					patternMatcher.setPattern("*");
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