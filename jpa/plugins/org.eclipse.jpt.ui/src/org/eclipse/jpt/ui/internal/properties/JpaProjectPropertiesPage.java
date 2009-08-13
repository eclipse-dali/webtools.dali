/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.IPreferenceChangeListener;
import org.eclipse.core.runtime.preferences.IEclipsePreferences.PreferenceChangeEvent;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JpaModelManager;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.facet.JpaLibraryProviderConstants;
import org.eclipse.jpt.core.internal.platform.JpaPlatformRegistry;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.ConnectionAdapter;
import org.eclipse.jpt.db.ConnectionListener;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.ConnectionProfileFactory;
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.utility.internal.BooleanTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.value.AbstractCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.AspectCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CommandChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryFacetPropertyPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.PlatformUI;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

/**
 * Way more complicated UI than you would think....
 */
public class JpaProjectPropertiesPage
	extends LibraryFacetPropertyPage 
{
	public static final String PROP_ID = "org.eclipse.jpt.ui.jpaProjectPropertiesPage"; //$NON-NLS-1$

	private WritablePropertyValueModel<IProject> projectModel;
	private PropertyValueModel<JpaProject> jpaProjectModel;
	private BufferedWritablePropertyValueModel.Trigger trigger;

	private BufferedWritablePropertyValueModel<String> platformIdModel;
	private PropertyChangeListener platformIdListener;

	private BufferedWritablePropertyValueModel<String> connectionModel;
	private PropertyValueModel<ConnectionProfile> connectionProfileModel;
	private PropertyValueModel<Boolean> disconnectedModel;
	private Link connectLink;

	private BufferedWritablePropertyValueModel<Boolean> userOverrideDefaultCatalogFlagModel;
	private BufferedWritablePropertyValueModel<String> userOverrideDefaultCatalogModel;
	private WritablePropertyValueModel<String> defaultCatalogModel;
	private ListValueModel<String> catalogChoicesModel;

	private BufferedWritablePropertyValueModel<Boolean> userOverrideDefaultSchemaFlagModel;
	private BufferedWritablePropertyValueModel<String> userOverrideDefaultSchemaModel;
	private WritablePropertyValueModel<String> defaultSchemaModel;
	private ListValueModel<String> schemaChoicesModel;

	private BufferedWritablePropertyValueModel<Boolean> discoverAnnotatedClassesModel;
	private WritablePropertyValueModel<Boolean> listAnnotatedClassesModel;

	private ChangeListener validationListener;


	// ************ construction ************

	public JpaProjectPropertiesPage() {
		super();

		this.projectModel = new SimplePropertyValueModel<IProject>();
		this.jpaProjectModel = new JpaProjectModel(this.projectModel);
		this.trigger = new BufferedWritablePropertyValueModel.Trigger();

		this.platformIdModel = this.buildPlatformIdModel();
		this.platformIdListener = this.buildPlatformIdListener();
		this.platformIdModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.platformIdListener);

		this.connectionModel = this.buildConnectionModel();
		this.connectionProfileModel = this.buildConnectionProfileModel();
		this.disconnectedModel = this.buildDisconnectedModel();

		this.userOverrideDefaultCatalogFlagModel = this.buildUserOverrideDefaultCatalogFlagModel();
		this.userOverrideDefaultCatalogModel = this.buildUserOverrideDefaultCatalogModel();
		this.defaultCatalogModel = this.buildDefaultCatalogModel();
		this.catalogChoicesModel = this.buildCatalogChoicesModel();

		this.userOverrideDefaultSchemaFlagModel = this.buildUserOverrideDefaultSchemaFlagModel();
		this.userOverrideDefaultSchemaModel = this.buildUserOverrideDefaultSchemaModel();
		this.defaultSchemaModel = this.buildDefaultSchemaModel();
		this.schemaChoicesModel = this.buildSchemaChoicesModel();

		this.discoverAnnotatedClassesModel = this.buildDiscoverAnnotatedClassesModel();
		this.listAnnotatedClassesModel = this.buildListAnnotatedClassesModel();

		this.validationListener = this.buildValidationListener();
		this.engageValidationListener();
	}

	// ***** platform ID model
	private BufferedWritablePropertyValueModel<String> buildPlatformIdModel() {
		return new BufferedWritablePropertyValueModel<String>(new PlatformIdModel(this.jpaProjectModel), this.trigger);
	}

	private PropertyChangeListener buildPlatformIdListener(){
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				JpaProjectPropertiesPage.this.platformIdChanged((String) event.getNewValue());
			}
		};
	}

	void platformIdChanged(String newPlatformId) {
		if ( ! this.getControl().isDisposed()) {
			this.getLibraryInstallDelegate().setEnablementContextVariable(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, newPlatformId);
		}
	}

	// ***** connection models
	private BufferedWritablePropertyValueModel<String> buildConnectionModel() {
		return new BufferedWritablePropertyValueModel<String>(new ConnectionModel(this.jpaProjectModel), this.trigger);
	}

	private PropertyValueModel<ConnectionProfile> buildConnectionProfileModel() {
		return new ConnectionProfileModel(this.connectionModel);
	}

	private PropertyValueModel<Boolean> buildDisconnectedModel() {
		return new DisconnectedModel(this.connectionProfileModel);
	}

	// ***** catalog models
	private BufferedWritablePropertyValueModel<Boolean> buildUserOverrideDefaultCatalogFlagModel() {
		return new BufferedWritablePropertyValueModel<Boolean>(new UserOverrideDefaultCatalogFlagModel(this.jpaProjectModel), this.trigger);
	}

	private BufferedWritablePropertyValueModel<String> buildUserOverrideDefaultCatalogModel() {
		return new BufferedWritablePropertyValueModel<String>(new UserOverrideDefaultCatalogModel(this.jpaProjectModel), this.trigger);
	}

	private WritablePropertyValueModel<String> buildDefaultCatalogModel() {
		return new DefaultModel(
					this.userOverrideDefaultCatalogFlagModel,
					this.userOverrideDefaultCatalogModel,
					this.buildDatabaseDefaultCatalogModel()
				);
	}

	private PropertyValueModel<String> buildDatabaseDefaultCatalogModel() {
		return new DatabaseDefaultCatalogModel(this.connectionProfileModel);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	@SuppressWarnings("unchecked")
	private ListValueModel<String> buildCatalogChoicesModel() {
		return new SortedListValueModelAdapter<String>(
				new SetCollectionValueModel<String>(
						new CompositeCollectionValueModel<CollectionValueModel<String>, String>(
								new PropertyCollectionValueModelAdapter<String>(this.defaultCatalogModel),
								this.buildDatabaseCatalogChoicesModel()
						)
				)
			);
	}

	private CollectionValueModel<String> buildDatabaseCatalogChoicesModel() {
		return new DatabaseCatalogChoicesModel(this.connectionProfileModel);
	}

	// ***** schema models
	private BufferedWritablePropertyValueModel<Boolean> buildUserOverrideDefaultSchemaFlagModel() {
		return new BufferedWritablePropertyValueModel<Boolean>(new UserOverrideDefaultSchemaFlagModel(this.jpaProjectModel), this.trigger);
	}

	private BufferedWritablePropertyValueModel<String> buildUserOverrideDefaultSchemaModel() {
		return new BufferedWritablePropertyValueModel<String>(new UserOverrideDefaultSchemaModel(this.jpaProjectModel), this.trigger);
	}

	private WritablePropertyValueModel<String> buildDefaultSchemaModel() {
		return new DefaultModel(
					this.userOverrideDefaultSchemaFlagModel,
					this.userOverrideDefaultSchemaModel,
					this.buildDatabaseDefaultSchemaModel()
				);
	}

	private PropertyValueModel<String> buildDatabaseDefaultSchemaModel() {
		return new DatabaseDefaultSchemaModel(this.connectionProfileModel, this.defaultCatalogModel);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	@SuppressWarnings("unchecked")
	private ListValueModel<String> buildSchemaChoicesModel() {
		return new SortedListValueModelAdapter<String>(
				new SetCollectionValueModel<String>(
						new CompositeCollectionValueModel<CollectionValueModel<String>, String>(
								new PropertyCollectionValueModelAdapter<String>(this.defaultSchemaModel),
								this.buildDatabaseSchemaChoicesModel()
						)
				)
			);
	}

	private CollectionValueModel<String> buildDatabaseSchemaChoicesModel() {
		return new DatabaseSchemaChoicesModel(this.connectionProfileModel, this.defaultCatalogModel);
	}

	// ***** discover/list annotated classes models
	private BufferedWritablePropertyValueModel<Boolean> buildDiscoverAnnotatedClassesModel() {
		return new BufferedWritablePropertyValueModel<Boolean>(new DiscoverAnnotatedClassesModel(this.jpaProjectModel), this.trigger);
	}

	private WritablePropertyValueModel<Boolean> buildListAnnotatedClassesModel() {
		return new ListAnnotatedClassesModel(this.discoverAnnotatedClassesModel);
	}


	// ********** convenience methods **********

	private String getConnectionName() {
		return this.connectionModel.getValue();
	}

	private ConnectionProfile getConnectionProfile() {
		return this.connectionProfileModel.getValue();
	}

	private boolean userOverrideDefaultCatalogFlagIsSet() {
		return flagIsSet(this.userOverrideDefaultCatalogFlagModel);
	}

	static boolean flagIsSet(PropertyValueModel<Boolean> flagModel) {
		Boolean flag = flagModel.getValue();
		return (flag != null) && flag.booleanValue();
	}

	private String getUserOverrideDefaultCatalog() {
		return this.userOverrideDefaultCatalogModel.getValue();
	}

	private boolean userOverrideDefaultSchemaFlagIsSet() {
		return flagIsSet(this.userOverrideDefaultSchemaFlagModel);
	}

	private String getUserOverrideDefaultSchema() {
		return this.userOverrideDefaultSchemaModel.getValue();
	}


	// ********** LibraryFacetPropertyPage implementation **********

	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		final IProjectFacet jsfFacet = ProjectFacetsManager.getProjectFacet(JptCorePlugin.FACET_ID);
		final IFacetedProject fproj = getFacetedProject();
		return fproj.getInstalledVersion( jsfFacet );
	}

	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(IFacetedProject project, IProjectFacetVersion fv) {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, ""); //$NON-NLS-1$
		return new LibraryInstallDelegate(project, fv, enablementVariables);
	}


	// ********** page **********

	@Override
	protected Control createPageContents(Composite parent) {
		this.projectModel.setValue(this.getProject());

		ScrolledComposite sc = new ScrolledComposite(parent, SWT.H_SCROLL | SWT.V_SCROLL);
		Composite composite = new Composite(sc, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		this.buildPlatformGroup(composite);

		Control libraryProviderComposite = super.createPageContents(composite);
		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.buildConnectionGroup(composite);
		this.buildPersistentClassManagementGroup(composite);

		Dialog.applyDialogFont(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);

		this.updateValidation();

		sc.setContent(composite);
		sc.setExpandHorizontal(true);
		sc.setExpandVertical(true);
		sc.setMinSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		return sc;
	}


	// ********** platform group **********

	private void buildPlatformGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo platformCombo = this.buildCombo(group);
		SWTTools.bind(
				this.buildPlatformChoicesModel(),
				this.platformIdModel,
				platformCombo,
				JPA_PLATFORM_LABEL_CONVERTER
		);
	}

	private ListValueModel<String> buildPlatformChoicesModel() {
		ArrayList<String> jpaPlatformIds = new ArrayList<String>();
		CollectionTools.addAll(jpaPlatformIds, JpaPlatformRegistry.instance().jpaPlatformIds());
		Collections.sort(jpaPlatformIds, JPA_PLATFORM_COMPARATOR);
		return new StaticListValueModel<String>(jpaPlatformIds);
	}

	private static final Comparator<String> JPA_PLATFORM_COMPARATOR =
			new Comparator<String>() {
				public int compare(String o1, String o2) {
					return getJpaPlatformLabel(o1).compareTo(getJpaPlatformLabel(o2));
				}
			};

	private static final StringConverter<String> JPA_PLATFORM_LABEL_CONVERTER =
			new StringConverter<String>() {
				public String convertToString(String id) {
					return getJpaPlatformLabel(id);
				}
			};

	static String getJpaPlatformLabel(String id) {
		return JpaPlatformRegistry.instance().getJpaPlatformLabel(id);
	}


	// ********** connection group **********

	private void buildConnectionGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_connectionLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);

		Combo connectionCombo = this.buildCombo(group, 3);
		SWTTools.bind(
				CONNECTION_CHOICES_MODEL,
				this.connectionModel,
				connectionCombo,
				this.buildConnectionStringConverter()
			);

		Link addConnectionLink = this.buildLink(group, JptUiMessages.JpaFacetWizardPage_connectionLink);
		addConnectionLink.addSelectionListener(this.buildAddConnectionLinkListener());  // the link will be GCed

		this.connectLink = this.buildLink(group, JptUiMessages.JpaFacetWizardPage_connectLink);
		SWTTools.controlEnabledState(this.disconnectedModel, this.connectLink);
		this.connectLink.addSelectionListener(this.buildConnectLinkListener());  // the link will be GCed

		// override default catalog
		Button overrideDefaultCatalogCheckBox = this.buildCheckBox(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultCatalogLabel);
		SWTTools.bind(this.userOverrideDefaultCatalogFlagModel, overrideDefaultCatalogCheckBox);

		Label defaultCatalogLabel = this.buildLabel(group, JptUiMessages.JpaFacetWizardPage_defaultCatalogLabel);
		Combo defaultCatalogCombo = this.buildCombo(group);
		SWTTools.bind(this.catalogChoicesModel, this.defaultCatalogModel, defaultCatalogCombo);

		SWTTools.controlEnabledState(this.userOverrideDefaultCatalogFlagModel, defaultCatalogLabel, defaultCatalogCombo);

		// override default schema
		Button overrideDefaultSchemaButton = this.buildCheckBox(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultSchemaLabel);
		SWTTools.bind(this.userOverrideDefaultSchemaFlagModel, overrideDefaultSchemaButton);

		Label defaultSchemaLabel = this.buildLabel(group, JptUiMessages.JpaFacetWizardPage_defaultSchemaLabel);
		Combo defaultSchemaCombo = this.buildCombo(group);
		SWTTools.bind(this.schemaChoicesModel, this.defaultSchemaModel, defaultSchemaCombo);

		SWTTools.controlEnabledState(this.userOverrideDefaultSchemaFlagModel, defaultSchemaLabel, defaultSchemaCombo);
	}

	private StringConverter<String> buildConnectionStringConverter() {
		return new StringConverter<String>() {
			public String convertToString(String o) {
				return (o != null) ? o : JptUiMessages.JpaFacetWizardPage_none;
			}
			@Override
			public String toString() {
				return "connection string converter"; //$NON-NLS-1$
			}
		};
	}

	private SelectionListener buildAddConnectionLinkListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JpaProjectPropertiesPage.this.openNewConnectionWizard();
			}
			@Override
			public String toString() {
				return "connection link listener"; //$NON-NLS-1$
			}
		};
	}

	void openNewConnectionWizard() {
		String connectionName = DTPUiTools.createNewConnectionProfile();
		if (connectionName != null) {
			this.connectionModel.setValue(connectionName);
		}
	}

	private SelectionListener buildConnectLinkListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JpaProjectPropertiesPage.this.openConnectionProfile();
			}
			@Override
			public String toString() {
				return "connect link listener"; //$NON-NLS-1$
			}
		};
	}

	void openConnectionProfile() {
		ConnectionProfile cp = this.getConnectionProfile();
		if (cp != null) {
			cp.connect();
		}
	}


	// ********** persistent class management group **********

	private void buildPersistentClassManagementGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);

		Button discoverClassesRadioButton = this.buildRadioButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton);
		SWTTools.bind(this.discoverAnnotatedClassesModel, discoverClassesRadioButton);

		Button listClassesRadioButton = this.buildRadioButton(group, 1, JptUiMessages.JpaFacetWizardPage_listClassesButton);
		SWTTools.bind(this.listAnnotatedClassesModel, listClassesRadioButton);
	}


	// ********** widgets **********

	private Button buildCheckBox(Composite parent, int horizontalSpan, String text) {
		return this.buildButton(parent, horizontalSpan, text, SWT.CHECK);
	}

	private Button buildRadioButton(Composite parent, int horizontalSpan, String text) {
		return this.buildButton(parent, horizontalSpan, text, SWT.RADIO);
	}

	private Button buildButton(Composite parent, int horizontalSpan, String text, int style) {
		Button button = new Button(parent, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = horizontalSpan;
		button.setLayoutData(gd);
		return button;
	}

	private Combo buildCombo(Composite parent) {
		return this.buildCombo(parent, 1);
	}

	private Combo buildCombo(Composite parent, int horizontalSpan) {
		Combo combo = new Combo(parent, SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = horizontalSpan;
		combo.setLayoutData(gd);
		return combo;
	}

	private Label buildLabel(Composite parent, String text) {
		Label label = new Label(parent, SWT.LEFT);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = 1;
		label.setLayoutData(gd);
		return label;
	}

	private Link buildLink(Composite parent, String text) {
		Link link = new Link(parent, SWT.NONE);
		GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
		data.horizontalSpan = 2;
		link.setLayoutData(data);
		link.setText(text);
		return link;
	}


	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
		super.performOk();

		try {
			// true=fork; false=uncancellable
			this.buildOkProgressMonitorDialog().run(true, false, this.buildOkRunnableWithProgress());
		}
		catch (InterruptedException ex) {
			return false;
		} 
		catch (InvocationTargetException ex) {
			throw new RuntimeException(ex.getTargetException());
		}

		return true;
	}

	private IRunnableContext buildOkProgressMonitorDialog() {
		return new ProgressMonitorDialog(this.getShell());
	}

	private IRunnableWithProgress buildOkRunnableWithProgress() {
		return new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					IWorkspace ws = ResourcesPlugin.getWorkspace();
					IWorkspaceRunnable wr = JpaProjectPropertiesPage.this.buildOkWorkspaceRunnable();
					ws.run(wr, ws.getRoot(), IWorkspace.AVOID_UPDATE, monitor);
				}
				catch (CoreException ex) {
					throw new InvocationTargetException(ex);
				}
			}
		};
	}

	IWorkspaceRunnable buildOkWorkspaceRunnable() {
		return new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				JpaProjectPropertiesPage.this.performOk_(monitor);
			}
		};
	}

	void performOk_(IProgressMonitor monitor) throws CoreException {
		if (this.isBuffering()) {
			boolean platformChanged = this.platformIdModel.isBuffering();
			this.trigger.accept();
			if (platformChanged) {
				// if the JPA platform is changed, we need to completely rebuild the JPA project
				JpaModelManager.instance().rebuildJpaProject(this.getProject());
			}
			this.getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}

	/**
	 * Return whether any of the models are buffering a change.
	 */
	private boolean isBuffering() {
		for (BufferedWritablePropertyValueModel<?> model : this.buildBufferedModels()) {
			if (model.isBuffering()) {
				return true;
			}
		}
		return false;
	}

	private BufferedWritablePropertyValueModel<?>[] buildBufferedModels() {
		return new BufferedWritablePropertyValueModel[] {
				this.platformIdModel,
				this.connectionModel,
				this.userOverrideDefaultCatalogFlagModel,
				this.userOverrideDefaultCatalogModel,
				this.userOverrideDefaultSchemaFlagModel,
				this.userOverrideDefaultSchemaModel,
				this.discoverAnnotatedClassesModel
		};
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		this.trigger.reset();
	}


	// ********** dispose **********

	@Override
	public void dispose() {
		this.disengageValidationListener();
		this.platformIdModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.platformIdListener);
		super.dispose();
	}


	// ********** validation **********

	private ChangeListener buildValidationListener() {
		return new CommandChangeListener() {
			@Override
			protected void modelChanged() {
				JpaProjectPropertiesPage.this.validate();
			}
			@Override
			public String toString() {
				return "validation listener"; //$NON-NLS-1$
			}
		};
	}

	void validate() {
		if ( ! this.getControl().isDisposed()) {
			this.updateValidation();
		}
	}

	private void engageValidationListener() {
		for (Model model : this.buildValidationModels()) {
			model.addChangeListener(this.validationListener);
		}
	}

	private Model[] buildValidationModels() {
		return new Model[] {
				this.platformIdModel,
				this.connectionModel,
				this.userOverrideDefaultCatalogFlagModel,
				this.defaultCatalogModel,
				this.userOverrideDefaultSchemaFlagModel,
				this.defaultSchemaModel,
				this.discoverAnnotatedClassesModel
		};
	}

	private void disengageValidationListener() {
		for (Model model : this.buildReverseValidationModels()) {
			model.removeChangeListener(this.validationListener);
		}
	}

	private Model[] buildReverseValidationModels() {
		return CollectionTools.reverse(this.buildValidationModels());
	}

	private static final Integer ERROR_STATUS = Integer.valueOf(IStatus.ERROR);
	private static final Integer WARNING_STATUS = Integer.valueOf(IStatus.WARNING);
	private static final Integer INFO_STATUS = Integer.valueOf(IStatus.INFO);
	private static final Integer OK_STATUS = Integer.valueOf(IStatus.OK);

	@Override
	protected IStatus performValidation() {
		HashMap<Integer, ArrayList<IStatus>> statuses = new HashMap<Integer, ArrayList<IStatus>>();
		statuses.put(ERROR_STATUS, new ArrayList<IStatus>());
		statuses.put(WARNING_STATUS, new ArrayList<IStatus>());
		statuses.put(INFO_STATUS, new ArrayList<IStatus>());
		statuses.put(OK_STATUS, CollectionTools.list(Status.OK_STATUS));

		/* platform */
		// user is unable to unset the platform, so no validation necessary

		/* library provider */
		IStatus lpStatus = super.performValidation();
		statuses.get(Integer.valueOf(lpStatus.getSeverity())).add(lpStatus);

		/* connection */
		ConnectionProfile connectionProfile = this.getConnectionProfile();
		String connectionName = this.getConnectionName();
		if ( ! StringTools.stringIsEmpty(connectionName)) {
			if (connectionProfile == null) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(NLS.bind(
						JptCoreMessages.VALIDATE_CONNECTION_INVALID,
						connectionName
				)));
			}
			else if ( ! connectionProfile.isActive()) {
				statuses.get(INFO_STATUS).add(this.buildInfoStatus(JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED));
			}
		}

		/* default catalog */
		if (this.userOverrideDefaultCatalogFlagIsSet()) {
			String defaultCatalog = this.getUserOverrideDefaultCatalog();
			if (StringTools.stringIsEmpty(defaultCatalog)) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED));
			}
			else if ((connectionProfile != null)
					&& connectionProfile.isConnected()
					&& ! CollectionTools.contains(this.catalogChoicesModel.iterator(), defaultCatalog)) {
				statuses.get(WARNING_STATUS).add(this.buildWarningStatus(NLS.bind(
						JptCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_CATALOG,
						defaultCatalog
				)));
			}
		}

		/* default schema */
		if (this.userOverrideDefaultSchemaFlagIsSet()) {
			String defaultSchema = this.getUserOverrideDefaultSchema();
			if (StringTools.stringIsEmpty(defaultSchema)) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(JptCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED));
			}
			else if ((connectionProfile != null)
					&& connectionProfile.isConnected()
					&& ! CollectionTools.contains(this.schemaChoicesModel.iterator(), defaultSchema)) {
				statuses.get(WARNING_STATUS).add(this.buildWarningStatus(NLS.bind(
						JptCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_SCHEMA,
						defaultSchema
				)));
			}
		}

		if ( ! statuses.get(ERROR_STATUS).isEmpty()) {
			return statuses.get(ERROR_STATUS).get(0);
		}
		else if ( ! statuses.get(WARNING_STATUS).isEmpty()) {
			return statuses.get(WARNING_STATUS).get(0);
		}
		else if ( ! statuses.get(INFO_STATUS).isEmpty()) {
			return statuses.get(INFO_STATUS).get(0);
		}
		else {
			return statuses.get(OK_STATUS).get(0);
		}
	}

	private IStatus buildInfoStatus(String message) {
		return this.buildStatus(IStatus.INFO, message);
	}

	private IStatus buildWarningStatus(String message) {
		return this.buildStatus(IStatus.WARNING, message);
	}

	private IStatus buildErrorStatus(String message) {
		return this.buildStatus(IStatus.ERROR, message);
	}

	private IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}


	// ********** UI model adapters **********

	/**
	 * Treat the JPA project as an "aspect" of the Eclipse project (IProject);
	 * but the JPA project is stored in the JPA model, not the Eclipse project
	 * itself....
	 * We also need to listen for the JPA project to be rebuilt if the user
	 * changes the Eclipse project's JPA platform (which is stored in the
	 * Eclipse project's preferences).
	 */
	static class JpaProjectModel
		extends AspectPropertyValueModelAdapter<IProject, JpaProject>
	{
		/**
		 * The JPA project's platform is stored as a preference.
		 * If it changes, a new JPA project is built.
		 */
		private IPreferenceChangeListener preferenceChangeListener;


		JpaProjectModel(PropertyValueModel<IProject> projectModel) {
			super(projectModel);
			this.preferenceChangeListener = this.buildPreferenceChangeListener();
		}

		private IPreferenceChangeListener buildPreferenceChangeListener() {
			return new IPreferenceChangeListener() {
				public void preferenceChange(PreferenceChangeEvent event) {
					if (event.getKey().equals(JptCorePlugin.JPA_PLATFORM)) {
						JpaProjectModel.this.platformChanged();
					}
				}
				@Override
				public String toString() {
					return "preference change listener"; //$NON-NLS-1$
				}
			};
		}

		void platformChanged() {
			this.propertyChanged();
		}

		@Override
		protected void engageSubject_() {
			this.getPreferences().addPreferenceChangeListener(this.preferenceChangeListener);
		}

		@Override
		protected void disengageSubject_() {
			this.getPreferences().removePreferenceChangeListener(this.preferenceChangeListener);
		}

		@Override
		protected JpaProject buildValue_() {
			return JptCorePlugin.getJpaProject(this.subject);
		}

		private IEclipsePreferences getPreferences() {
			return JptCorePlugin.getProjectPreferences(this.subject);
		}
	}


	/**
	 * The JPA project's data source is an auxiliary object that never changes;
	 * so if we have a JPA project, we have a JPA data source also.
	 */
	static class DataSourceModel
		extends TransformationPropertyValueModel<JpaProject, JpaDataSource>
	{
		DataSourceModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel);
		}

		@Override
		protected JpaDataSource transform_(JpaProject value) {
			return value.getDataSource();
		}
	}


	/**
	 * The DTP connection profile name is an aspect of the JPA project's
	 * data source
	 */
	static class ConnectionModel
		extends PropertyAspectAdapter<JpaDataSource, String>
	{
		ConnectionModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(new DataSourceModel(jpaProjectModel), JpaDataSource.CONNECTION_PROFILE_NAME_PROPERTY);
		}

		@Override
		protected String buildValue_() {
			return this.subject.getConnectionProfileName();
		}

		@Override
		public void setValue_(String connection) {
			this.subject.setConnectionProfileName(connection);
		}
	}


	/**
	 * Convert the selected connection profile name to a connection profile
	 */
	static class ConnectionProfileModel
		extends CachingTransformationPropertyValueModel<String, ConnectionProfile>
	{
		ConnectionProfileModel(PropertyValueModel<String> connectionModel) {
			super(connectionModel);
		}

		@Override
		protected ConnectionProfile transform_(String connectionName) {
			return JptDbPlugin.instance().getConnectionProfileFactory().buildConnectionProfile(connectionName);
		}
	}


	/**
	 * Treat the JPA platform ID as an "aspect" of the JPA project.
	 * The platform ID is stored in the project preferences.
	 * The platform ID does not change for a JPA project - if the user wants a
	 * different platform, we build an entirely new JPA project.
	 */
	static class PlatformIdModel
		extends AspectPropertyValueModelAdapter<JpaProject, String>
	{
		PlatformIdModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel);
		}

		@Override
		protected String buildValue_() {
			return JptCorePlugin.getJpaPlatformId(this.subject.getProject());
		}

		@Override
		public void setValue_(String newPlatformId) {
			JptCorePlugin.setJpaPlatformId(this.subject.getProject(), newPlatformId);
		}

		@Override
		protected void engageSubject_() {
			// the platform ID does not change
		}

		@Override
		protected void disengageSubject_() {
			// the platform ID does not change
		}
	}


	/**
	 * The connections are held by a singleton, so the model can be a singleton
	 * also.
	 */
	// by default, ExtendedListValueModelWrapper puts a null at the top of the list
	private static final ListValueModel<String> CONNECTION_CHOICES_MODEL =
			new ExtendedListValueModelWrapper<String>(
					new SortedListValueModelAdapter<String>(
							new ConnectionChoicesModel()
					)
			);

	/**
	 * Wrap the connection profile names held by the connection profile
	 * factory singleton.
	 */
	static class ConnectionChoicesModel
		extends AbstractCollectionValueModel
		implements CollectionValueModel<String>
	{
		private ConnectionProfileListener connectionProfileListener;

		ConnectionChoicesModel() {
			super();
			this.connectionProfileListener = this.buildConnectionProfileListener();
		}

		private ConnectionProfileListener buildConnectionProfileListener() {
			return new ConnectionProfileListener() {
				public void connectionProfileAdded(String name) {
					ConnectionChoicesModel.this.collectionChanged();
				}
				public void connectionProfileRemoved(String name) {
					ConnectionChoicesModel.this.collectionChanged();
				}
				public void connectionProfileRenamed(String oldName, String newName) {
					// Ignore this event for now. Connecting a profile actually
					// throws a connection renamed event, which messes up the 
					// list selection. There shouldn't be a connection renamed
					// within the scope of this dialog anyhow.
					// ConnectionChoicesModel.this.collectionChanged();
				}
			};
		}

		void collectionChanged() {
			this.fireCollectionChanged(CollectionValueModel.VALUES, CollectionTools.collection(this.iterator()));
		}

		public Iterator<String> iterator() {
			return this.getConnectionProfileFactory().connectionProfileNames();
		}

		public int size() {
			return CollectionTools.size(this.iterator());
		}

		@Override
		protected void engageModel() {
			this.getConnectionProfileFactory().addConnectionProfileListener(this.connectionProfileListener);
		}

		@Override
		protected void disengageModel() {
			this.getConnectionProfileFactory().removeConnectionProfileListener(this.connectionProfileListener);
		}

		private ConnectionProfileFactory getConnectionProfileFactory() {
			return JptDbPlugin.instance().getConnectionProfileFactory();
		}
	}


	/**
	 * Adapt whether the JPA project has a user override specified
	 * (either catalog or schema);
	 */
	abstract static class UserOverrideDefaultFlagModel
		extends PropertyAspectAdapter<JpaProject, Boolean>
	{
		UserOverrideDefaultFlagModel(PropertyValueModel<JpaProject> jpaProjectModel, String propertyName) {
			super(jpaProjectModel, propertyName);
		}

		@Override
		protected Boolean buildValue_() {
			return Boolean.valueOf(this.specifiesUserOverrideDefault());
		}

		boolean specifiesUserOverrideDefault() {
			return ! StringTools.stringIsEmpty(this.getUserOverrideDefault());
		}

		abstract String getUserOverrideDefault();

		@Override
		protected void setValue_(Boolean value) {
			// ignore
		}
	}


	/**
	 * Whether the JPA project has a user override default catalog specified.
	 */
	static class UserOverrideDefaultCatalogFlagModel
		extends UserOverrideDefaultFlagModel
	{
		UserOverrideDefaultCatalogFlagModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY);
		}
		@Override
		public String getUserOverrideDefault() {
			return this.subject.getUserOverrideDefaultCatalog();
		}
	}


	/**
	 * Whether the JPA project has a user override default schema specified.
	 */
	static class UserOverrideDefaultSchemaFlagModel
		extends UserOverrideDefaultFlagModel
	{
		UserOverrideDefaultSchemaFlagModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY);
		}
		@Override
		public String getUserOverrideDefault() {
			return this.subject.getUserOverrideDefaultSchema();
		}
	}


	/**
	 * The JPA project's user override default catalog
	 */
	static class UserOverrideDefaultCatalogModel
		extends PropertyAspectAdapter<JpaProject, String>
	{
		UserOverrideDefaultCatalogModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY);
		}

		@Override
		protected String buildValue_() {
			return this.subject.getUserOverrideDefaultCatalog();
		}

		@Override
		public void setValue_(String catalog) {
			this.subject.setUserOverrideDefaultCatalog(catalog);
		}
	}


	/**
	 * The JPA project's user override default catalog
	 */
	static class UserOverrideDefaultSchemaModel
		extends PropertyAspectAdapter<JpaProject, String>
	{
		UserOverrideDefaultSchemaModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY);
		}

		@Override
		protected String buildValue_() {
			return this.subject.getUserOverrideDefaultSchema();
		}

		@Override
		public void setValue_(String schema) {
			this.subject.setUserOverrideDefaultSchema(schema);
		}
	}


	/**
	 * Flag on the JPA project indicating whether it should discover annotated
	 * classes
	 */
	static class DiscoverAnnotatedClassesModel
		extends PropertyAspectAdapter<JpaProject, Boolean>
	{
		DiscoverAnnotatedClassesModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.DISCOVERS_ANNOTATED_CLASSES_PROPERTY);
		}

		@Override
		protected Boolean buildValue_() {
			return Boolean.valueOf(this.subject.discoversAnnotatedClasses());
		}

		@Override
		protected void setValue_(Boolean value) {
			this.subject.setDiscoversAnnotatedClasses(value.booleanValue());
		}
	}

	/**
	 * The negative of the "discover annotated classes" flag.
	 */
	static class ListAnnotatedClassesModel
		extends TransformationWritablePropertyValueModel<Boolean, Boolean>
	{
		ListAnnotatedClassesModel(WritablePropertyValueModel<Boolean> discoverAnnotatedClassesModel) {
			super(discoverAnnotatedClassesModel);
		}

		@Override
		protected Boolean transform_(Boolean value) {
			return BooleanTools.not(value);
		}

		@Override
		protected Boolean reverseTransform_(Boolean value) {
			return BooleanTools.not(value);
		}
	}


	/**
	 * Abstract property aspect adapter for DTP connection profile connection/database
	 */
	abstract static class ConnectionProfilePropertyAspectAdapter<V>
		extends AspectPropertyValueModelAdapter<ConnectionProfile, V>
	{
		private ConnectionListener connectionListener;

		ConnectionProfilePropertyAspectAdapter(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
			this.connectionListener = this.buildConnectionListener();
		}

		// the connection opening is probably the only thing that will happen...
		private ConnectionListener buildConnectionListener() {
			return new ConnectionAdapter() {
				@Override
				public void opened(ConnectionProfile profile) {
					ConnectionProfilePropertyAspectAdapter.this.connectionOpened(profile);
				}
			};
		}

		void connectionOpened(ConnectionProfile profile) {
			if (profile.equals(this.subject)) {
				this.propertyChanged();
			}
		}

		@Override
		protected void engageSubject_() {
			this.subject.addConnectionListener(this.connectionListener);
		}

		@Override
		protected void disengageSubject_() {
			this.subject.removeConnectionListener(this.connectionListener);
		}
	}


	/**
	 * Monitor the connection profile's connection to the database
	 * (used to enable the "Connect" link)
	 */
	static class DisconnectedModel
		extends ConnectionProfilePropertyAspectAdapter<Boolean>
	{
		DisconnectedModel(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
		}

		@Override
		protected Boolean buildValue_() {
			return Boolean.valueOf((this.subject != null) && this.subject.isDisconnected());
		}
	}


	/**
	 * Database-determined default catalog
	 */
	static class DatabaseDefaultCatalogModel
		extends ConnectionProfilePropertyAspectAdapter<String>
	{
		DatabaseDefaultCatalogModel(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
		}

		@Override
		protected String buildValue_() {
			Database db = this.subject.getDatabase();
			return (db == null) ? null : db.getDefaultCatalogIdentifier();
		}
	}


	/**
	 * The default schema is not derived purely from the database; it is also dependent
	 * on the current value of the default catalog (which may be overridden
	 * by the user).
	 */
	static class DatabaseDefaultSchemaModel
		extends ConnectionProfilePropertyAspectAdapter<String>
	{
		private final PropertyValueModel<String> defaultCatalogModel;
		private final PropertyChangeListener catalogListener;

		DatabaseDefaultSchemaModel(
				PropertyValueModel<ConnectionProfile> connectionProfileModel,
				PropertyValueModel<String> defaultCatalogModel
		) {
			super(connectionProfileModel);
			this.defaultCatalogModel = defaultCatalogModel;
			this.catalogListener = this.buildCatalogListener();
		}

		private PropertyChangeListener buildCatalogListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					DatabaseDefaultSchemaModel.this.catalogChanged();
				}
			};
		}

		void catalogChanged() {
			this.propertyChanged();
		}

		@Override
		protected void engageSubject_() {
			super.engageSubject_();
			this.defaultCatalogModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
		}

		@Override
		protected void disengageSubject_() {
			this.defaultCatalogModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
			super.disengageSubject_();
		}

		@Override
		protected String buildValue_() {
			SchemaContainer sc = this.getSchemaContainer();
			return (sc == null) ? null : sc.getDefaultSchemaIdentifier();
		}

		private SchemaContainer getSchemaContainer() {
			return this.databaseSupportsCatalogs() ? this.getCatalog() : this.getDatabase();
		}

		private boolean databaseSupportsCatalogs() {
			Database db = this.getDatabase();
			return (db != null) && db.supportsCatalogs();
		}

		private Catalog getCatalog() {
			String name = this.defaultCatalogModel.getValue();
			// if we get here we know the database is not null
			return (name == null) ? null : this.getDatabase().getCatalogForIdentifier(name);
		}

		private Database getDatabase() {
			return this.subject.getDatabase();
		}
	}


	/**
	 * Abstract collection aspect adapter for DTP connection profile connection/database
	 */
	abstract static class ConnectionProfileCollectionAspectAdapter<E>
		extends AspectCollectionValueModelAdapter<ConnectionProfile, E>
	{
		private ConnectionListener connectionListener;

		ConnectionProfileCollectionAspectAdapter(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
			this.connectionListener = this.buildConnectionListener();
		}

		// the connection opening is probably the only thing that will happen...
		private ConnectionListener buildConnectionListener() {
			return new ConnectionAdapter() {
				@Override
				public void opened(ConnectionProfile profile) {
					ConnectionProfileCollectionAspectAdapter.this.connectionOpened(profile);
				}
			};
		}

		void connectionOpened(ConnectionProfile profile) {
			if (profile.equals(this.subject)) {
				this.collectionChanged();
			}
		}

		@Override
		protected void engageSubject_() {
			this.subject.addConnectionListener(this.connectionListener);
		}

		@Override
		protected void disengageSubject_() {
			this.subject.removeConnectionListener(this.connectionListener);
		}
	}


	/**
	 * Catalogs on the database.
	 */
	static class DatabaseCatalogChoicesModel
		extends ConnectionProfileCollectionAspectAdapter<String>
	{
		DatabaseCatalogChoicesModel(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
		}

		@Override
		protected Iterator<String> iterator_() {
			Database db = this.subject.getDatabase();
			// use catalog *identifiers* since the string ends up being the "default" for various text entries
			return (db != null) ? db.sortedCatalogIdentifiers() : EmptyIterator.<String>instance();
		}
	}


	/**
	 * Schemas on the database or catalog.
	 * This list is not derived purely from the database; it is also dependent
	 * on the current value of the default catalog (which may be overridden
	 * by the user).
	 */
	static class DatabaseSchemaChoicesModel
		extends ConnectionProfileCollectionAspectAdapter<String>
	{
		private final PropertyValueModel<String> defaultCatalogModel;
		private final PropertyChangeListener catalogListener;

		DatabaseSchemaChoicesModel(
				PropertyValueModel<ConnectionProfile> connectionProfileModel,
				PropertyValueModel<String> defaultCatalogModel
		) {
			super(connectionProfileModel);
			this.defaultCatalogModel = defaultCatalogModel;
			this.catalogListener = this.buildCatalogListener();
		}

		private PropertyChangeListener buildCatalogListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					DatabaseSchemaChoicesModel.this.catalogChanged();
				}
			};
		}

		void catalogChanged() {
			this.collectionChanged();
		}

		@Override
		protected void engageSubject_() {
			super.engageSubject_();
			this.defaultCatalogModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
		}

		@Override
		protected void disengageSubject_() {
			this.defaultCatalogModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
			super.disengageSubject_();
		}

		@Override
		protected Iterator<String> iterator_() {
			SchemaContainer sc = this.getSchemaContainer();
			// use schema *identifiers* since the string ends up being the "default" for various text entries
			return (sc != null) ? sc.sortedSchemaIdentifiers() : EmptyIterator.<String>instance();
		}

		private SchemaContainer getSchemaContainer() {
			return this.databaseSupportsCatalogs() ? this.getCatalog() : this.getDatabase();
		}

		private boolean databaseSupportsCatalogs() {
			Database db = this.getDatabase();
			return (db != null) && db.supportsCatalogs();
		}

		private Catalog getCatalog() {
			String name = this.defaultCatalogModel.getValue();
			// if we get here we know the database is not null
			return (name == null) ? null : this.getDatabase().getCatalogForIdentifier(name);
		}

		private Database getDatabase() {
			return this.subject.getDatabase();
		}
	}

	/**
	 * Combine various models to determine the default catalog or schema.
	 * If the user has checked the "Override Default" check-box, the default
	 * is the JPA project's user override default, otherwise the default is
	 * determined by the database.
	 */
	static class DefaultModel
		extends CompositePropertyValueModel<String>
		implements WritablePropertyValueModel<String>
	{
		private PropertyValueModel<Boolean> userOverrideDefaultFlagModel;
		private WritablePropertyValueModel<String> userOverrideDefaultModel;
		private PropertyValueModel<String> databaseDefaultModel;

		DefaultModel(
				PropertyValueModel<Boolean> userOverrideDefaultFlagModel,
				WritablePropertyValueModel<String> userOverrideDefaultModel,
				PropertyValueModel<String> databaseDefaultModel
		) {
			super(userOverrideDefaultFlagModel, userOverrideDefaultModel, databaseDefaultModel);
			this.userOverrideDefaultFlagModel = userOverrideDefaultFlagModel;
			this.userOverrideDefaultModel = userOverrideDefaultModel;
			this.databaseDefaultModel = databaseDefaultModel;
		}

		/**
		 * If the checkbox has been unchecked, we need to clear out the JPA
		 * project's user override.
		 */
		@Override
		protected void propertyChanged(PropertyChangeEvent event) {
			super.propertyChanged(event);
			if (event.getSource() == this.userOverrideDefaultFlagModel) {
				if ( ! this.userOverrideDefaultFlagIsSet()) {
					this.userOverrideDefaultModel.setValue(null);
				}
			}
		}

		/**
		 * If the checkbox is checked, return the user override from the JPA project;
		 * otherwise return the default from the database
		 */
		@Override
		protected String buildValue() {
			return this.userOverrideDefaultFlagIsSet() ?
					this.userOverrideDefaultModel.getValue() :
					this.databaseDefaultModel.getValue();
		}

		/**
		 * This will be called when the user makes a selection from the
		 * drop-down; which is only possible when the checkbox is checked
		 * (and the drop-down is enabled).
		 */
		public void setValue(String value) {
			this.userOverrideDefaultModel.setValue(value);
			this.propertyChanged();
		}

		private boolean userOverrideDefaultFlagIsSet() {
			return flagIsSet(this.userOverrideDefaultFlagModel);
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.getValue());
		}
	}

}
