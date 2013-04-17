/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection;

import java.util.Comparator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.plugin.JptJpaEclipseLinkUiPlugin;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.jpa2.persistence.JptJpaUiPersistenceMessages2_0;
import org.eclipse.osgi.util.NLS;
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
public class EclipseLinkJdbcConnectionPropertiesComposite<T extends EclipseLinkConnection> 
	extends Pane<T>
{
	/**
	 * The constant ID used to retrieve the dialog settings.
	 */
	private static final String DIALOG_SETTINGS = "org.eclipse.jpt.jpa.eclipselink.ui.dialogs.ConnectionDialog";

	public EclipseLinkJdbcConnectionPropertiesComposite(
			Pane<T> parent, 
			Composite parentComposite
	) {
		super(parent, parentComposite);
	}

	private ModifiablePropertyValueModel<String> buildPasswordHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, String>(getSubjectHolder(), EclipseLinkConnection.PASSWORD_PROPERTY) {
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

	private ModifiablePropertyValueModel<String> buildUrlHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, String>(getSubjectHolder(), EclipseLinkConnection.URL_PROPERTY) {
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

	private ModifiablePropertyValueModel<String> buildUserHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, String>(getSubjectHolder(), EclipseLinkConnection.USER_PROPERTY) {
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
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// Populate from Connection hyperlink
		Hyperlink hyperLink = this.addHyperlink(
			container,
			JptJpaUiPersistenceMessages2_0.JDBC_CONNECTION_PROPERTIES_COMPOSITE_POPULATE_FROM_CONNECTION_HYPER_LINK,
			buildPopulateFromConnectionAction()
		);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		hyperLink.setLayoutData(gridData);

		// Driver
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_DRIVER_LABEL);
		this.initializeJdbcDriverClassChooser(container);

		// Url
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_URL_LABEL);
		this.addText(container, buildUrlHolder());

		// User
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_USER_LABEL);
		this.addText(container, buildUserHolder());

		// Password
		this.addLabel(container, JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_PASSWORD_LABEL);
		this.addPasswordText(container, buildPasswordHolder());

		// Bind Parameters

		TriStateCheckBox bindParametersCheckBox = this.addTriStateCheckBoxWithDefault(
			container,
			JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_BIND_PARAMETERS_LABEL,
			this.buildBindParametersHolder(),
			this.buildBindParametersStringHolder(),
			JpaHelpContextIds.PERSISTENCE_XML_CONNECTION
		);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		bindParametersCheckBox.getCheckBox().setLayoutData(gridData);
	}

	private ClassChooserPane<EclipseLinkConnection> initializeJdbcDriverClassChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkConnection>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkConnection, String>(
							this.getSubjectHolder(), EclipseLinkConnection.DRIVER_PROPERTY) {
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

	private ModifiablePropertyValueModel<Boolean> buildBindParametersHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, Boolean>(getSubjectHolder(), EclipseLinkConnection.BIND_PARAMETERS_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getBindParameters();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setBindParameters(value);
			}
		};
	}

	private PropertyValueModel<String> buildBindParametersStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultBindParametersHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_BIND_PARAMETERS_LABEL_DEFAULT, defaultStringValue);
				}
				return JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_CONNECTION_TAB_BIND_PARAMETERS_LABEL;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultBindParametersHolder() {
		return new PropertyAspectAdapter<EclipseLinkConnection, Boolean>(
			getSubjectHolder(),
			EclipseLinkConnection.BIND_PARAMETERS_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getBindParameters() != null) {
					return null;
				}
				return this.subject.getDefaultBindParameters();
			}
		};
	}

	void promptConnection() {

		ConnectionSelectionDialog dialog = new ConnectionSelectionDialog();

		if (dialog.open() != IDialogConstants.OK_ID) {
			return;
		}

		String name = (String) dialog.getResult()[0];
		ConnectionProfileFactory factory = this.getConnectionProfileFactory();
		ConnectionProfile cp = (factory == null) ? null : factory.buildConnectionProfile(name);

		EclipseLinkConnection connection = getSubject();
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

	/**
	 * This dialog shows the list of possible connection names and lets the user
	 * the option to filter them using a search field.
	 */
	protected class ConnectionSelectionDialog extends FilteredItemsSelectionDialog {

		/**
		 * Creates a new <code>MappingSelectionDialog</code>.
		 */
		protected ConnectionSelectionDialog() {
			super(EclipseLinkJdbcConnectionPropertiesComposite.this.getShell_(), false);
			setMessage(JptJpaEclipseLinkUiMessages.JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_MESSAGE);
			setTitle(JptJpaEclipseLinkUiMessages.JDBC_CONNECTION_PROPERTIES_COMPOSITE_CONNECTION_DIALOG_TITLE);
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

			Iterable<String> profileNames = this.getConnectionProfileNames();
			SubMonitor sm = SubMonitor.convert(monitor, IterableTools.size(profileNames));

			// Add the connection names to the dialog
			for (String name : profileNames) {
				provider.add(name, itemsFilter);
				sm.worked(1);
			}
		}

		private Iterable<String> getConnectionProfileNames() {
			ConnectionProfileFactory factory = EclipseLinkJdbcConnectionPropertiesComposite.this.getConnectionProfileFactory();
			return (factory == null) ? IterableTools.<String>emptyIterable() : factory.getConnectionProfileNames();
		}

		@Override
		protected IDialogSettings getDialogSettings() {
			return JptJpaEclipseLinkUiPlugin.instance().getDialogSettings(DIALOG_SETTINGS);
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
			return (item == null) ?
					JptJpaEclipseLinkUiPlugin.instance().buildErrorStatus() :
					Status.OK_STATUS;
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
