/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.properties;

import static org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi.createInstallLibraryPanel;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jpt.core.JpaDataSource;
import org.eclipse.jpt.core.JpaFacet;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaProjectManager;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.platform.JpaPlatformDescription;
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
import org.eclipse.jpt.ui.internal.jpa2.Jpa2_0ProjectFlagModel;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.NotBooleanTransformer;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;
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
import org.eclipse.jpt.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.CollectionAddEvent;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeAdapter;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.listener.SimpleChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.IPropertyChangeListener;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryFacetPropertyPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import com.ibm.icu.text.Collator;

/**
 * Way more complicated UI than you would think....
 */
public class JpaProjectPropertiesPage
	extends LibraryFacetPropertyPage 
{
	public static final String PROP_ID = "org.eclipse.jpt.ui.jpaProjectPropertiesPage"; //$NON-NLS-1$

	private final WritablePropertyValueModel<IProject> projectModel;
	private final PropertyValueModel<JpaProject> jpaProjectModel;
	private final BufferedWritablePropertyValueModel.Trigger trigger;

	private final BufferedWritablePropertyValueModel<String> platformIdModel;
	private final PropertyChangeListener platformIdListener;

	private final BufferedWritablePropertyValueModel<String> connectionModel;
	private final PropertyValueModel<ConnectionProfile> connectionProfileModel;
	private final PropertyValueModel<Boolean> disconnectedModel;
	private final PropertyChangeListener disconnectedModelListener;
	private Link connectLink;

	private final BufferedWritablePropertyValueModel<Boolean> userOverrideDefaultCatalogFlagModel;
	private final BufferedWritablePropertyValueModel<String> userOverrideDefaultCatalogModel;
	private final WritablePropertyValueModel<String> defaultCatalogModel;
	private final ListValueModel<String> catalogChoicesModel;

	private final BufferedWritablePropertyValueModel<Boolean> userOverrideDefaultSchemaFlagModel;
	private final BufferedWritablePropertyValueModel<String> userOverrideDefaultSchemaModel;
	private final WritablePropertyValueModel<String> defaultSchemaModel;
	private final ListValueModel<String> schemaChoicesModel;

	private final BufferedWritablePropertyValueModel<Boolean> discoverAnnotatedClassesModel;
	private final WritablePropertyValueModel<Boolean> listAnnotatedClassesModel;

	private final PropertyValueModel<Boolean> jpa2_0ProjectFlagModel;

	private final BufferedWritablePropertyValueModel<String> metamodelSourceFolderModel;
	private final ListValueModel<String> javaSourceFolderChoicesModel;
	private static final String BUILD_PATHS_PROPERTY_PAGE_ID = "org.eclipse.jdt.ui.propertyPages.BuildPathsPropertyPage"; //$NON-NLS-1$

	private final ChangeListener validationListener;


	@SuppressWarnings("unchecked")
	/* private */ static final Comparator<String> STRING_COMPARATOR = Collator.getInstance();


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
		this.disconnectedModelListener = buildDisconnectedModelListener();

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

		this.jpa2_0ProjectFlagModel = this.buildJpa2_0ProjectFlagModel();

		this.metamodelSourceFolderModel = this.buildMetamodelSourceFolderModel();
		this.javaSourceFolderChoicesModel = this.buildJavaSourceFolderChoicesModel();

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
			// handle null, in the case the jpa facet is changed via the facets page,
			// the library install delegate is temporarily null
			adjustLibraryProviders();
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
	private ListValueModel<String> buildCatalogChoicesModel() {
		return new SortedListValueModelAdapter<String>(this.buildUnsortedCatalogChoicesModel(), STRING_COMPARATOR);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	@SuppressWarnings("unchecked")
	private CollectionValueModel<String> buildUnsortedCatalogChoicesModel() {
		return new SetCollectionValueModel<String>(
					new CompositeCollectionValueModel<CollectionValueModel<String>, String>(
							new PropertyCollectionValueModelAdapter<String>(this.defaultCatalogModel),
							this.buildDatabaseCatalogChoicesModel()
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
	private ListValueModel<String> buildSchemaChoicesModel() {
		return new SortedListValueModelAdapter<String>(this.buildUnsortedSchemaChoicesModel(), STRING_COMPARATOR);
	}

	@SuppressWarnings("unchecked")
	private CollectionValueModel<String> buildUnsortedSchemaChoicesModel() {
		return new SetCollectionValueModel<String>(
				new CompositeCollectionValueModel<CollectionValueModel<String>, String>(
						new PropertyCollectionValueModelAdapter<String>(this.defaultSchemaModel),
						this.buildDatabaseSchemaChoicesModel()
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

	/**
	 * The opposite of the "discover annotated classes" flag.
	 */
	private WritablePropertyValueModel<Boolean> buildListAnnotatedClassesModel() {
		return new TransformationWritablePropertyValueModel<Boolean, Boolean>(this.discoverAnnotatedClassesModel, NotBooleanTransformer.instance());
	}
	
	// ***** JPA 2.0 project flag
	private PropertyValueModel<Boolean> buildJpa2_0ProjectFlagModel() {
		return new Jpa2_0ProjectFlagModel<JpaProject>(this.jpaProjectModel);
	}

	// ***** metamodel models
	private BufferedWritablePropertyValueModel<String> buildMetamodelSourceFolderModel() {
		return new BufferedWritablePropertyValueModel<String>(new MetamodelSourceFolderModel(this.jpaProjectModel), this.trigger);
	}

	private ListValueModel<String> buildJavaSourceFolderChoicesModel() {
		// by default, ExtendedListValueModelWrapper puts a null at the top of the list
		return new ExtendedListValueModelWrapper<String>(
					new SortedListValueModelAdapter<String>(
						new JavaSourceFolderChoicesModel(this.jpaProjectModel),
						STRING_COMPARATOR
					)
				);
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

	/* private */ static boolean flagIsSet(PropertyValueModel<Boolean> flagModel) {
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

	private IWorkbenchPreferenceContainer getWorkbenchPreferenceContainer() {
		IWorkbenchPreferenceContainer container= (IWorkbenchPreferenceContainer) getContainer();
		return container;
	}

	// ********** LibraryFacetPropertyPage implementation **********

	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		return this.getFacetedProject().getInstalledVersion(JpaFacet.FACET);
	}

	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(IFacetedProject project, IProjectFacetVersion fv) {
		LibraryInstallDelegate lid = new LibraryInstallDelegate(project, fv, null);
		lid.addListener(buildLibraryProviderListener());
		return lid;
	}
	
	protected IPropertyChangeListener buildLibraryProviderListener() {
		return new IPropertyChangeListener() {
				public void propertyChanged(String property, Object oldValue, Object newValue ) {
					if (LibraryInstallDelegate.PROP_AVAILABLE_PROVIDERS.equals(property)) {
						adjustLibraryProviders();
					}
				}
			};
	}
	
	protected void adjustLibraryProviders() {
		LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
		if (lid != null) {
			List<JpaLibraryProviderInstallOperationConfig> jpaConfigs 
					= new ArrayList<JpaLibraryProviderInstallOperationConfig>();
			// add the currently selected one first
			JpaLibraryProviderInstallOperationConfig currentJpaConfig = null;
			LibraryProviderOperationConfig config = lid.getLibraryProviderOperationConfig();
			if (config instanceof JpaLibraryProviderInstallOperationConfig) {
				currentJpaConfig = (JpaLibraryProviderInstallOperationConfig) config;
				jpaConfigs.add(currentJpaConfig);
			}
			for (ILibraryProvider lp : lid.getLibraryProviders()) {
				config = lid.getLibraryProviderOperationConfig(lp);
				if (config instanceof JpaLibraryProviderInstallOperationConfig
						&& ! config.equals(currentJpaConfig)) {
					jpaConfigs.add((JpaLibraryProviderInstallOperationConfig) config);
				}
			}
			for (JpaLibraryProviderInstallOperationConfig jpaConfig : jpaConfigs) {
				jpaConfig.setJpaPlatform(JptCorePlugin.getJpaPlatformManager().getJpaPlatform(this.platformIdModel.getValue()));
			}
		}
	}
	
	
	// ********** page **********

	@Override
	protected Control createPageContents(Composite parent) {
		this.projectModel.setValue(this.getProject());
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);

		this.buildPlatformGroup(composite);

		Control libraryProviderComposite = createInstallLibraryPanel(
				composite, 
				this.getLibraryInstallDelegate(), 
				JptUiMessages.JpaFacetWizardPage_jpaImplementationLabel);

 		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		this.buildConnectionGroup(composite);
		this.buildPersistentClassManagementGroup(composite);
		this.buildMetamodelGroup(composite);

		Dialog.applyDialogFont(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
		
		adjustLibraryProviders();
		this.updateValidation();

		return composite;
	}

	/**
	 * Don't allow {@link org.eclipse.jface.preference.PreferencePage#computeSize()}
	 * to cache the page's size, since the size of the "Library" panel can
	 * change depending on the user's selection from the drop-down list.
	 */
	@Override
	public Point computeSize() {
		return this.doComputeSize();
	}


	// ********** platform group **********

	private void buildPlatformGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo platformDropDown = this.buildDropDown(group);
		SWTTools.bind(
				this.buildPlatformChoicesModel(),
				this.platformIdModel,
				platformDropDown,
				JPA_PLATFORM_LABEL_CONVERTER
		);
	}

	/**
	 * Add the project's JPA platform if it is not on the list of valid
	 * platforms.
	 * <p>
	 * This is probably only useful if the project is corrupted
	 * and has a platform that exists in the registry but is not on the
	 * list of valid platforms for the project's JPA facet version.
	 * Because, if the project's JPA platform is completely invalid, there
	 * would be no JPA project!
	 */
	@SuppressWarnings("unchecked")
	private ListValueModel<String> buildPlatformChoicesModel() {
		return new SortedListValueModelAdapter<String>(
				new SetCollectionValueModel<String>(
						new CompositeCollectionValueModel<CollectionValueModel<String>, String>(
								new PropertyCollectionValueModelAdapter<String>(this.platformIdModel),
								this.buildRegistryPlatformsModel()
						)
				),
				JPA_PLATFORM_COMPARATOR
			);
	}

	private CollectionValueModel<String> buildRegistryPlatformsModel() {
		Iterable<String> enabledPlatformIds = 
			new TransformationIterable<JpaPlatformDescription, String>(
					new FilteringIterable<JpaPlatformDescription>(JptCorePlugin.getJpaPlatformManager().getJpaPlatforms()) {
						@Override
						protected boolean accept(JpaPlatformDescription o) {
							return o.supportsJpaFacetVersion(getProjectFacetVersion());
						}
					}) {
				@Override
				protected String transform(JpaPlatformDescription o) {
					return o.getId();
				}
			};
		return new StaticCollectionValueModel<String>(enabledPlatformIds);
	}

	private static final Comparator<String> JPA_PLATFORM_COMPARATOR =
			new Comparator<String>() {
				public int compare(String id1, String id2) {
					String label1 = getJpaPlatformLabel(id1);
					String label2 = getJpaPlatformLabel(id2);
					return STRING_COMPARATOR.compare(label1, label2);
				}
			};

	private static final StringConverter<String> JPA_PLATFORM_LABEL_CONVERTER =
			new StringConverter<String>() {
				public String convertToString(String id) {
					return getJpaPlatformLabel(id);
				}
			};

	/* private */ static String getJpaPlatformLabel(String id) {
		return JptCorePlugin.getJpaPlatformManager().getJpaPlatform(id).getLabel();
	}


	// ********** connection group **********

	private void buildConnectionGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_connectionLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);

		Combo connectionDropDown = this.buildDropDown(group, 3);
		SWTTools.bind(
				CONNECTION_CHOICES_MODEL,
				this.connectionModel,
				connectionDropDown,
				SIMPLE_STRING_CONVERTER
			);

		Link addConnectionLink = this.buildLink(group, JptUiMessages.JpaFacetWizardPage_connectionLink);
		addConnectionLink.addSelectionListener(this.buildAddConnectionLinkListener());  // the link will be GCed

		this.connectLink = this.buildLink(group, buildConnectLinkText());
		SWTTools.controlEnabledState(this.disconnectedModel, this.connectLink);
		this.connectLink.addSelectionListener(this.buildConnectLinkListener());  // the link will be GCed
		this.disconnectedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);

		// override default catalog
		Button overrideDefaultCatalogCheckBox = this.buildCheckBox(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultCatalogLabel);
		SWTTools.bind(this.userOverrideDefaultCatalogFlagModel, overrideDefaultCatalogCheckBox);

		Label defaultCatalogLabel = this.buildLabel(group, JptUiMessages.JpaFacetWizardPage_defaultCatalogLabel);
		Combo defaultCatalogDropDown = this.buildDropDown(group);
		SWTTools.bind(this.catalogChoicesModel, this.defaultCatalogModel, defaultCatalogDropDown);

		SWTTools.controlEnabledState(this.userOverrideDefaultCatalogFlagModel, defaultCatalogLabel, defaultCatalogDropDown);

		// override default schema
		Button overrideDefaultSchemaButton = this.buildCheckBox(group, 3, JptUiMessages.JpaFacetWizardPage_overrideDefaultSchemaLabel);
		SWTTools.bind(this.userOverrideDefaultSchemaFlagModel, overrideDefaultSchemaButton);

		Label defaultSchemaLabel = this.buildLabel(group, JptUiMessages.JpaFacetWizardPage_defaultSchemaLabel);
		Combo defaultSchemaDropDown = this.buildDropDown(group);
		SWTTools.bind(this.schemaChoicesModel, this.defaultSchemaModel, defaultSchemaDropDown);

		SWTTools.controlEnabledState(this.userOverrideDefaultSchemaFlagModel, defaultSchemaLabel, defaultSchemaDropDown);
	}

	private static final StringConverter<String> SIMPLE_STRING_CONVERTER =
			new StringConverter<String>() {
				public String convertToString(String string) {
					return (string != null) ? string : JptUiMessages.JpaFacetWizardPage_none;
				}
			};

	private PropertyChangeListener buildDisconnectedModelListener() {
		return new SWTPropertyChangeListenerWrapper(buildDisconnectedModelListener_());
	}
	
	private PropertyChangeListener buildDisconnectedModelListener_() {
		return new PropertyChangeListener() {
			
			public void propertyChanged(PropertyChangeEvent event) {
				JpaProjectPropertiesPage.this.updateConnectLinkText(buildConnectLinkText());	
			}
		};
	}
	
	private String buildConnectLinkText(){
		ConnectionProfile connectionProfile = getConnectionProfile();
		if (connectionProfile != null && connectionProfile.isConnected()) {
			return JptUiMessages.JpaFacetWizardPage_connectedText;
		}
		else {
			return JptUiMessages.JpaFacetWizardPage_connectLink;
		}
	}
	
	private void updateConnectLinkText(String text) {
		connectLink.setText(text);
		SWTUtil.reflow(connectLink.getParent());
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

		Button discoverClassesRadioButton = this.buildRadioButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton);
		SWTTools.bind(this.discoverAnnotatedClassesModel, discoverClassesRadioButton);

		Button listClassesRadioButton = this.buildRadioButton(group, 1, JptUiMessages.JpaFacetWizardPage_listClassesButton);
		SWTTools.bind(this.listAnnotatedClassesModel, listClassesRadioButton);
	}


	// ********** metamodel group **********

	private void buildMetamodelGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptUiMessages.JpaFacetWizardPage_metamodelLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Link metamodelSourceFolderLink = this.buildLink(group, JptUiMessages.JpaFacetWizardPage_metamodelSourceFolderLink);
		metamodelSourceFolderLink.addSelectionListener(buildMetamodelSourceFolderLinkListener());
		Combo metamodelSourceFolderDropDown = this.buildDropDown(group);
		SWTTools.bind(
				this.javaSourceFolderChoicesModel,
				this.metamodelSourceFolderModel,
				metamodelSourceFolderDropDown,
				SIMPLE_STRING_CONVERTER
		);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_METAMODEL);

		SWTTools.controlVisibleState(this.jpa2_0ProjectFlagModel, group, metamodelSourceFolderLink, metamodelSourceFolderDropDown);
	}

	private SelectionListener buildMetamodelSourceFolderLinkListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JpaProjectPropertiesPage.this.openJavaBuildPathPage();
			}
			@Override
			public String toString() {
				return "metamodel source folder link listener"; //$NON-NLS-1$
			}
		};
	}

	void openJavaBuildPathPage() {
		IWorkbenchPreferenceContainer container = getWorkbenchPreferenceContainer();
		container.openPage(BUILD_PATHS_PROPERTY_PAGE_ID, null);
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

	private Combo buildDropDown(Composite parent) {
		return this.buildDropDown(parent, 1);
	}

	private Combo buildDropDown(Composite parent, int horizontalSpan) {
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
				IWorkspace ws = ResourcesPlugin.getWorkspace();
				try {
					// the build we execute in #performOk_() locks the workspace root,
					// so we need to use the workspace root as our scheduling rule here
					ws.run(
							JpaProjectPropertiesPage.this.buildOkWorkspaceRunnable(),
							ws.getRoot(),
							IWorkspace.AVOID_UPDATE,
							monitor
					);
				}
				catch (CoreException ex) {
					throw new InvocationTargetException(ex);
				}
			}
		};
	}

	/* private */ IWorkspaceRunnable buildOkWorkspaceRunnable() {
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
				JptCorePlugin.rebuildJpaProject(this.getProject());
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
				this.discoverAnnotatedClassesModel,
				this.metamodelSourceFolderModel
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
		this.disconnectedModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);
		super.dispose();
	}


	// ********** validation **********

	private ChangeListener buildValidationListener() {
		return new SimpleChangeListener() {
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
		return ArrayTools.reverse(this.buildValidationModels());
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
		private final IPreferenceChangeListener preferenceChangeListener;
		
		/**
		 * The JPA project may also change via another page (notably, the project facets page).
		 * In that case, the preference change occurs before we actually have another project,
		 * so we must listen to the projects manager
		 */
		private final CollectionChangeListener projectManagerListener;
		
		
		JpaProjectModel(PropertyValueModel<IProject> projectModel) {
			super(projectModel);
			this.preferenceChangeListener = this.buildPreferenceChangeListener();
			this.projectManagerListener = buildProjectManagerListener();
		}
		
		private IPreferenceChangeListener buildPreferenceChangeListener() {
			return new IPreferenceChangeListener() {
				public void preferenceChange(PreferenceChangeEvent event) {
					if (event.getKey().equals(JptCorePlugin.getJpaPlatformIdPrefKey())) {
						JpaProjectModel.this.platformChanged();
					}
				}
				@Override
				public String toString() {
					return "preference change listener"; //$NON-NLS-1$
				}
			};
		}
		
		private CollectionChangeListener buildProjectManagerListener() {
			return new CollectionChangeAdapter() {
				// we are only looking for the project rebuild *add* event here so we can
				// determine if the platform has changed.
				// the other events are unimportant in this case
				@Override
				public void itemsAdded(CollectionAddEvent event) {
					JpaProjectModel.this.platformChanged();
				}
			};
		}
		
		void platformChanged() {
			this.propertyChanged();
		}
		
		@Override
		protected void engageSubject_() {
			this.getPreferences().addPreferenceChangeListener(this.preferenceChangeListener);
			JptCorePlugin.getJpaProjectManager().addCollectionChangeListener(
						JpaProjectManager.JPA_PROJECTS_COLLECTION, this.projectManagerListener);
		}
		
		@Override
		protected void disengageSubject_() {
			this.getPreferences().removePreferenceChangeListener(this.preferenceChangeListener);
			JptCorePlugin.getJpaProjectManager().removeCollectionChangeListener(
						JpaProjectManager.JPA_PROJECTS_COLLECTION, this.projectManagerListener);
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
			return JptDbPlugin.getConnectionProfileFactory().buildConnectionProfile(connectionName);
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
							new ConnectionChoicesModel(),
							STRING_COMPARATOR
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
		private final ConnectionProfileListener connectionProfileListener;

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
			return this.getConnectionProfileFactory().getConnectionProfileNames().iterator();
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
			return JptDbPlugin.getConnectionProfileFactory();
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
	 * The folder where the source for the generated Canonical Metamodel
	 * is written.
	 */
	static class MetamodelSourceFolderModel
		extends PropertyAspectAdapter<JpaProject, String>
	{
		MetamodelSourceFolderModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel, JpaProject2_0.METAMODEL_SOURCE_FOLDER_NAME_PROPERTY);
		}

		@Override
		protected String buildValue_() {
			return jpaProjectIsJpa2_0() ? ((JpaProject2_0) this.subject).getMetamodelSourceFolderName() : null;
		}

		@Override
		protected void setValue_(String value) {
			if (this.jpaProjectIsJpa2_0()) {
				((JpaProject2_0) this.subject).setMetamodelSourceFolderName(value);
			}
		}

		private boolean jpaProjectIsJpa2_0() {
			return JptCorePlugin.nodeIsJpa2_0Compatible(this.subject);
		}
	}


	/**
	 * Java project source folders.
	 * We keep the metamodel source folder in synch with the Java source folders
	 * (i.e. if a Java source folder is deleted or removed from the build path,
	 * we remove the metamodel source folder); therefore the list of folder
	 * choices does not need to be augmented with the current folder (as we do
	 * when the current folder is not in the list of choices).
	 */
	static class JavaSourceFolderChoicesModel
		extends AspectCollectionValueModelAdapter<JpaProject, String>
	{
		private final IElementChangedListener javaElementChangedListener;

		JavaSourceFolderChoicesModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel);
			this.javaElementChangedListener = this.buildJavaElementChangedListener();
		}

		private IElementChangedListener buildJavaElementChangedListener() {
			return new IElementChangedListener() {
				public void elementChanged(ElementChangedEvent event) {
					JavaSourceFolderChoicesModel.this.processJavaDelta(event.getDelta());
				}
			};
		}

		void processJavaDelta(IJavaElementDelta delta) {
			switch (delta.getElement().getElementType()) {
				case IJavaElement.JAVA_MODEL :
					this.processJavaDeltaChildren(delta);
					break;
				case IJavaElement.JAVA_PROJECT :
					this.processJavaProjectDelta(delta);
					break;
				default :
					break; // ignore everything else
			}
		}

		private void processJavaDeltaChildren(IJavaElementDelta delta) {
			for (IJavaElementDelta child : delta.getAffectedChildren()) {
				this.processJavaDelta(child); // recurse
			}
		}

		private void processJavaProjectDelta(IJavaElementDelta delta) {
			IJavaProject javaProject = (IJavaProject) delta.getElement();
			if (javaProject.equals(this.subject.getJavaProject()) && this.classpathHasChanged(delta)) {
				this.fireCollectionChanged(CollectionValueModel.VALUES, CollectionTools.collection(this.iterator()));
			}
		}

		private boolean classpathHasChanged(IJavaElementDelta delta) {
			return this.deltaFlagIsSet(delta, IJavaElementDelta.F_RESOLVED_CLASSPATH_CHANGED);
		}

		private boolean deltaFlagIsSet(IJavaElementDelta delta, int flag) {
			return (delta.getKind() == IJavaElementDelta.CHANGED) &&
					BitTools.flagIsSet(delta.getFlags(), flag);
		}

		@Override
		protected Iterable<String> getIterable() {
			return this.jpaProjectIsJpa2_0() ?
					((JpaProject2_0) this.subject).getJavaSourceFolderNames() :
					EmptyIterable.<String>instance();
		}

		private boolean jpaProjectIsJpa2_0() {
			return JptCorePlugin.nodeIsJpa2_0Compatible(this.subject);
		}

		@Override
		protected void engageSubject_() {
			JavaCore.addElementChangedListener(this.javaElementChangedListener);
		}

		@Override
		protected void disengageSubject_() {
			JavaCore.removeElementChangedListener(this.javaElementChangedListener);
		}
	}


	/**
	 * Abstract property aspect adapter for DTP connection profile connection/database
	 */
	abstract static class ConnectionProfilePropertyAspectAdapter<V>
		extends AspectPropertyValueModelAdapter<ConnectionProfile, V>
	{
		private final ConnectionListener connectionListener;

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
		private final ConnectionListener connectionListener;

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
		protected Iterable<String> getIterable() {
			Database db = this.subject.getDatabase();
			// use catalog *identifiers* since the string ends up being the "default" for various text entries
			return (db != null) ? db.getSortedCatalogIdentifiers() : EmptyIterable.<String>instance();
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
		protected Iterable<String> getIterable() {
			SchemaContainer sc = this.getSchemaContainer();
			// use schema *identifiers* since the string ends up being the "default" for various text entries
			return (sc != null) ? sc.getSortedSchemaIdentifiers() : EmptyIterable.<String>instance();
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
		private final PropertyValueModel<Boolean> userOverrideDefaultFlagModel;
		private final WritablePropertyValueModel<String> userOverrideDefaultModel;
		private final PropertyValueModel<String> databaseDefaultModel;

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
