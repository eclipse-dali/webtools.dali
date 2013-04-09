/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.properties;

import static org.eclipse.jst.common.project.facet.ui.libprov.LibraryProviderFrameworkUi.createInstallLibraryPanel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ElementChangedEvent;
import org.eclipse.jdt.core.IElementChangedListener;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaElementDelta;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.utility.ICUStringCollator;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.properties.JptProjectPropertiesPage;
import org.eclipse.jpt.common.ui.internal.swt.bind.SWTBindTools;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.AbstractCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.AspectCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.BufferedModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.DoublePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.JpaPreferences;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaWorkspace;
import org.eclipse.jpt.jpa.core.JptJpaCoreMessages;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.libprov.JpaLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.jpa.core.platform.JpaPlatformManager;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.ConnectionAdapter;
import org.eclipse.jpt.jpa.db.ConnectionListener;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.ConnectionProfileAdapter;
import org.eclipse.jpt.jpa.db.ConnectionProfileFactory;
import org.eclipse.jpt.jpa.db.ConnectionProfileListener;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.jpa.ui.JpaProjectModel;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.JpaVersionIsCompatibleWith;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jst.common.project.facet.core.libprov.ILibraryProvider;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryProviderOperationConfig;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

/**
 * Way more complicated UI than you would think....
 */
public class JpaProjectPropertiesPage
	extends JptProjectPropertiesPage
{
	public static final String PROP_ID = "org.eclipse.jpt.jpa.ui.jpaProjectProperties"; //$NON-NLS-1$

	private PropertyValueModel<JpaProject> jpaProjectModel;
	private PropertyValueModel<Boolean> jpaProjectNotNullFlagModel;

	private BufferedModifiablePropertyValueModel<JpaPlatform.Config> jpaPlatformConfigModel;
	private PropertyChangeListener jpaPlatformConfigListener;

	private BufferedModifiablePropertyValueModel<String> connectionModel;
	private PropertyValueModel<ConnectionProfile> connectionProfileModel;
	private PropertyValueModel<Boolean> disconnectedModel;
	private PropertyChangeListener disconnectedModelListener;
	private Link connectLink;

	private BufferedModifiablePropertyValueModel<Boolean> userOverrideDefaultCatalogFlagModel;
	private BufferedModifiablePropertyValueModel<String> userOverrideDefaultCatalogModel;
	private ModifiablePropertyValueModel<String> defaultCatalogModel;
	private ListValueModel<String> catalogChoicesModel;

	private BufferedModifiablePropertyValueModel<Boolean> userOverrideDefaultSchemaFlagModel;
	private BufferedModifiablePropertyValueModel<String> userOverrideDefaultSchemaModel;
	private ModifiablePropertyValueModel<String> defaultSchemaModel;
	private ListValueModel<String> schemaChoicesModel;

	private BufferedModifiablePropertyValueModel<Boolean> discoverAnnotatedClassesModel;
	private ModifiablePropertyValueModel<Boolean> listAnnotatedClassesModel;

	private PropertyValueModel<Boolean> jpa2_0ProjectFlagModel;

	private BufferedModifiablePropertyValueModel<String> metamodelSourceFolderModel;
	private ListValueModel<String> javaSourceFolderChoicesModel;

	private static final String BUILD_PATHS_PROPERTY_PAGE_ID = "org.eclipse.jdt.ui.propertyPages.BuildPathsPropertyPage"; //$NON-NLS-1$

	/* private */ static final Comparator<String> STRING_COMPARATOR = new ICUStringCollator();

	// ************ construction ************

	public JpaProjectPropertiesPage() {
		super();
	}

	@Override
	protected void buildModels() {
		this.jpaProjectModel = this.buildJpaProjectModel();
		this.jpaProjectNotNullFlagModel = this.buildJpaProjectNotNullFlagModel();

		this.jpaPlatformConfigModel = this.buildJpaPlatformConfigModel();
		this.jpaPlatformConfigListener = this.buildJpaPlatformConfigListener();

		this.connectionModel = this.buildConnectionModel();
		this.connectionProfileModel = this.buildConnectionProfileModel();
		this.disconnectedModel = this.buildDisconnectedModel();
		this.disconnectedModelListener = this.buildDisconnectedModelListener();

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
	}

	// ***** JPA project model
	private PropertyValueModel<JpaProject> buildJpaProjectModel() {
		return new DoublePropertyValueModel<JpaProject>(this.buildJpaProjectModelModel());
	}

	private PropertyValueModel<PropertyValueModel<JpaProject>> buildJpaProjectModelModel() {
		return new TransformationPropertyValueModel<IProject, PropertyValueModel<JpaProject>>(this.projectModel, JPA_PROJECT_MODEL_TRANSFORMER);
	}

	private static final Transformer<IProject, PropertyValueModel<JpaProject>> JPA_PROJECT_MODEL_TRANSFORMER = new JpaProjectModelTransformer();

	/* CU private */ static class JpaProjectModelTransformer
		extends AbstractTransformer<IProject, PropertyValueModel<JpaProject>>
	{
		@Override
		protected PropertyValueModel<JpaProject> transform_(IProject project) {
			return (JpaProjectModel) project.getAdapter(JpaProjectModel.class);
		}
	}

	// ***** JPA project not null model
	private PropertyValueModel<Boolean> buildJpaProjectNotNullFlagModel() {
		return new TransformationPropertyValueModel<JpaProject, Boolean>(this.jpaProjectModel, JPA_PROJECT_NOT_NULL_TRANSFORMER);
	}

	private static final Transformer<JpaProject, Boolean> JPA_PROJECT_NOT_NULL_TRANSFORMER = new JpaProjectNotNullTransformer();

	/* CU private */ static class JpaProjectNotNullTransformer
		extends AbstractTransformer<JpaProject, Boolean>
	{
		@Override
		protected Boolean transform_(JpaProject jpaProject) {
			return Boolean.TRUE;  // if we get here, the JPA project is not null
		}
	}

	// ***** JPA platform config model
	private BufferedModifiablePropertyValueModel<JpaPlatform.Config> buildJpaPlatformConfigModel() {
		return new BufferedModifiablePropertyValueModel<JpaPlatform.Config>(new JpaPlatformConfigModel(this.jpaProjectModel), this.trigger);
	}

	private PropertyChangeListener buildJpaPlatformConfigListener(){
		return new JpaPlatformConfigListener();
	}

	/* CU private */ class JpaPlatformConfigListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaProjectPropertiesPage.this.jpaPlatformConfigChanged();
		}
	}

	void jpaPlatformConfigChanged() {
		if ( ! this.getControl().isDisposed()) {
			// handle null, in the case the jpa facet is changed via the facets page,
			// the library install delegate is temporarily null
			this.adjustLibraryProviders();
		}
	}

	// ***** connection models
	private BufferedModifiablePropertyValueModel<String> buildConnectionModel() {
		return new BufferedModifiablePropertyValueModel<String>(new ConnectionModel(this.jpaProjectModel), this.trigger);
	}

	private PropertyValueModel<ConnectionProfile> buildConnectionProfileModel() {
		return new ConnectionProfileModel(this.connectionModel);
	}

	private PropertyValueModel<Boolean> buildDisconnectedModel() {
		return new DisconnectedModel(this.connectionProfileModel);
	}

	// ***** catalog models
	private BufferedModifiablePropertyValueModel<Boolean> buildUserOverrideDefaultCatalogFlagModel() {
		return new BufferedModifiablePropertyValueModel<Boolean>(new UserOverrideDefaultCatalogFlagModel(this.jpaProjectModel), this.trigger);
	}

	private BufferedModifiablePropertyValueModel<String> buildUserOverrideDefaultCatalogModel() {
		return new BufferedModifiablePropertyValueModel<String>(new UserOverrideDefaultCatalogModel(this.jpaProjectModel), this.trigger);
	}

	private ModifiablePropertyValueModel<String> buildDefaultCatalogModel() {
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
					CompositeCollectionValueModel.forModels(
							new PropertyCollectionValueModelAdapter<String>(this.defaultCatalogModel),
							this.buildDatabaseCatalogChoicesModel()
					)
			);
	}

	private CollectionValueModel<String> buildDatabaseCatalogChoicesModel() {
		return new DatabaseCatalogChoicesModel(this.connectionProfileModel);
	}

	// ***** schema models
	private BufferedModifiablePropertyValueModel<Boolean> buildUserOverrideDefaultSchemaFlagModel() {
		return new BufferedModifiablePropertyValueModel<Boolean>(new UserOverrideDefaultSchemaFlagModel(this.jpaProjectModel), this.trigger);
	}

	private BufferedModifiablePropertyValueModel<String> buildUserOverrideDefaultSchemaModel() {
		return new BufferedModifiablePropertyValueModel<String>(new UserOverrideDefaultSchemaModel(this.jpaProjectModel), this.trigger);
	}

	private ModifiablePropertyValueModel<String> buildDefaultSchemaModel() {
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
				CompositeCollectionValueModel.forModels(
						new PropertyCollectionValueModelAdapter<String>(this.defaultSchemaModel),
						this.buildDatabaseSchemaChoicesModel()
				)
			);
	}

	private CollectionValueModel<String> buildDatabaseSchemaChoicesModel() {
		return new DatabaseSchemaChoicesModel(this.connectionProfileModel, this.defaultCatalogModel);
	}

	// ***** discover/list annotated classes models
	private BufferedModifiablePropertyValueModel<Boolean> buildDiscoverAnnotatedClassesModel() {
		return new BufferedModifiablePropertyValueModel<Boolean>(new DiscoverAnnotatedClassesModel(this.jpaProjectModel), this.trigger);
	}

	/**
	 * The opposite of the "discover annotated classes" flag.
	 */
	private ModifiablePropertyValueModel<Boolean> buildListAnnotatedClassesModel() {
		return new TransformationModifiablePropertyValueModel<Boolean, Boolean>(this.discoverAnnotatedClassesModel, TransformerTools.notBooleanTransformer(), TransformerTools.notBooleanTransformer());
	}

	// ***** JPA 2.0 project flag
	private PropertyValueModel<Boolean> buildJpa2_0ProjectFlagModel() {
		return new TransformationPropertyValueModel<JpaProject, Boolean>(this.jpaProjectModel, this.buildJpaProjectIsCompatibleWithJpa2_0Transformer());
	}

	private Transformer<JpaProject, Boolean> buildJpaProjectIsCompatibleWithJpa2_0Transformer() {
		return new JpaVersionIsCompatibleWith<JpaProject>(JpaProject2_0.FACET_VERSION_STRING);
	}

	// ***** metamodel models
	private BufferedModifiablePropertyValueModel<String> buildMetamodelSourceFolderModel() {
		return new BufferedModifiablePropertyValueModel<String>(new MetamodelSourceFolderModel(this.jpaProjectModel), this.trigger);
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
		IWorkbenchPreferenceContainer container= (IWorkbenchPreferenceContainer) this.getContainer();
		return container;
	}

	// ********** LibraryFacetPropertyPage implementation **********

	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		return this.getFacetedProject().getInstalledVersion(JpaProject.FACET);
	}

	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(IFacetedProject project, IProjectFacetVersion fv) {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();

		//TODO Ask Paul about these empty enablement variables - trying to reproduce Helios functionality
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_ENABLEMENT_EXP, ""); //$NON-NLS-1$
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP, new EmptyJpaPlatformConfig());

		LibraryInstallDelegate lid = new LibraryInstallDelegate(project, fv, enablementVariables);
		lid.addListener(this.buildLibraryProviderListener());
		return lid;
	}

	/* CU private */ class EmptyJpaPlatformConfig
		implements JpaPlatform.Config
	{
		public JpaPlatformManager getJpaPlatformManager() {
			return null;
		}

		public JpaPlatform getJpaPlatform() {
			return null;
		}

		public IProjectFacetVersion getJpaFacetVersion() {
			return null;
		}

		public boolean supportsJpaFacetVersion(IProjectFacetVersion jpaFacetVersion) {
			return false;
		}

		public boolean isDefault() {
			return false;
		}

		public String getPluginId() {
			return null;
		}

		public String getLabel() {
			return null;
		}

		public String getId() {
			return null;
		}

		public JpaPlatform.GroupConfig getGroupConfig() {
			return null;
		}

		public String getFactoryClassName() {
			return null;
		}
	}

	@Override
	protected void adjustLibraryProviders() {
		LibraryInstallDelegate lid = this.getLibraryInstallDelegate();
		if (lid == null) {
			return;
		}

		JpaPlatform.Config jpaPlatformConfig = this.jpaPlatformConfigModel.getValue();
		String jpaPlatformID = (jpaPlatformConfig == null) ? "" : jpaPlatformConfig.getId(); //$NON-NLS-1$

		lid.setEnablementContextVariable(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_ENABLEMENT_EXP, jpaPlatformID);
		lid.setEnablementContextVariable(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP, jpaPlatformConfig);

		ArrayList<JpaLibraryProviderInstallOperationConfig> jpaConfigs = new ArrayList<JpaLibraryProviderInstallOperationConfig>();
		// add the currently selected one first
		JpaLibraryProviderInstallOperationConfig currentJpaConfig = null;
		LibraryProviderOperationConfig config = lid.getLibraryProviderOperationConfig();
		if (config instanceof JpaLibraryProviderInstallOperationConfig) {
			currentJpaConfig = (JpaLibraryProviderInstallOperationConfig) config;
			jpaConfigs.add(currentJpaConfig);
		}
		for (ILibraryProvider lp : lid.getLibraryProviders()) {
			config = lid.getLibraryProviderOperationConfig(lp);
			if ((config instanceof JpaLibraryProviderInstallOperationConfig)
					&& ! config.equals(currentJpaConfig)) {
				jpaConfigs.add((JpaLibraryProviderInstallOperationConfig) config);
			}
		}
		for (JpaLibraryProviderInstallOperationConfig jpaConfig : jpaConfigs) {
			jpaConfig.setJpaPlatformConfig(jpaPlatformConfig);
		}
	}


	// ********** page **********

	@Override
	protected void createWidgets(Composite parent) {
		this.buildPlatformGroup(parent);

		Control libraryProviderComposite = createInstallLibraryPanel(
				parent,
				this.getLibraryInstallDelegate(),
				JptJpaUiMessages.JpaFacetWizardPage_jpaImplementationLabel);

 		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		SWTBindTools.controlEnabledState(this.jpaProjectNotNullFlagModel, libraryProviderComposite);

		this.buildConnectionGroup(parent);
		this.buildPersistentClassManagementGroup(parent);
		this.buildMetamodelGroup(parent);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
	}

	@Override
	protected void engageListeners() {
		super.engageListeners();
		this.jpaPlatformConfigModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaPlatformConfigListener);
		this.disconnectedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);
	}

	@Override
	protected void disengageListeners() {
		this.jpaPlatformConfigModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaPlatformConfigListener);
		this.disconnectedModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);
		super.disengageListeners();
	}


	// ********** platform group **********

	private void buildPlatformGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JpaFacetWizardPage_platformLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo jpaPlatformDropDown = this.buildDropDown(group);
		SWTBindTools.bind(
				this.buildJpaPlatformConfigChoicesModel(),
				this.jpaPlatformConfigModel,
				jpaPlatformDropDown,
				JPA_PLATFORM_CONFIG_LABEL_CONVERTER);

		Link facetsPageLink = this.buildFacetsPageLink(group, JptJpaUiMessages.JpaFacetWizardPage_facetsPageLink);

		SWTBindTools.controlEnabledState(this.jpaProjectNotNullFlagModel, group, jpaPlatformDropDown, facetsPageLink);
	}

	/**
	 * Add the project's JPA platform if it is not on the list of valid
	 * JPA platforms.
	 * <p>
	 * This is probably only useful if the project is corrupted
	 * and has a JPA platform that exists in the registry but is not on the
	 * list of valid JPA platforms for the project's JPA facet version.
	 * Because, if the project's JPA platform is completely invalid, there
	 * would be no JPA project!
	 */
	private ListValueModel<JpaPlatform.Config> buildJpaPlatformConfigChoicesModel() {
		return new SortedListValueModelAdapter<JpaPlatform.Config>(
				new SetCollectionValueModel<JpaPlatform.Config>(
					CompositeCollectionValueModel.forModels(
						this.buildJpaPlatformConfigChoicesModels()
					)
				),
				JPA_PLATFORM_CONFIG_COMPARATOR
			);
	}

	@SuppressWarnings("unchecked")
	private CollectionValueModel<JpaPlatform.Config>[] buildJpaPlatformConfigChoicesModels() {
		return new CollectionValueModel[] {
				new PropertyCollectionValueModelAdapter<JpaPlatform.Config>(this.jpaPlatformConfigModel),
				this.buildEnabledJpaPlatformConfigsModel()
			};
	}

	/**
	 * Return only the JPA platform configs that support the project's
	 * JPA facet version.
	 */
	private CollectionValueModel<JpaPlatform.Config> buildEnabledJpaPlatformConfigsModel() {
		return new StaticCollectionValueModel<JpaPlatform.Config>(
				IterableTools.filter(
					getJpaPlatformConfigs(),
					new JpaPlatformConfigIsEnabled()
				)
			);
	}

	/* CU private */ class JpaPlatformConfigIsEnabled
		extends PredicateAdapter<JpaPlatform.Config>
	{
		@Override
		public boolean evaluate(JpaPlatform.Config config) {
			return config.supportsJpaFacetVersion(JpaProjectPropertiesPage.this.getProjectFacetVersion());
		}
	}

	private static final Comparator<JpaPlatform.Config> JPA_PLATFORM_CONFIG_COMPARATOR = new JpaPlatformConfigComparator();

	/* CU private */ static class JpaPlatformConfigComparator
		implements Comparator<JpaPlatform.Config>
	{
		public int compare(JpaPlatform.Config config1, JpaPlatform.Config config2) {
			return STRING_COMPARATOR.compare(config1.getLabel(), config2.getLabel());
		}
		@Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	}

	private static final Transformer<JpaPlatform.Config, String> JPA_PLATFORM_CONFIG_LABEL_CONVERTER = new JpaPlatformConfigLabelConverter();

	/* CU private */ static class JpaPlatformConfigLabelConverter
		extends TransformerAdapter<JpaPlatform.Config, String>
	{
		@Override
		public String transform(JpaPlatform.Config config) {
			return config.getLabel();
		}
	}

	/* CU private */ static ConnectionProfileFactory getConnectionProfileFactory() {
		JpaWorkspace jpaWorkspace = getJpaWorkspace();
		return (jpaWorkspace == null) ? null : jpaWorkspace.getConnectionProfileFactory();
	}

	/* CU private */ static Iterable<JpaPlatform.Config> getJpaPlatformConfigs() {
		JpaPlatformManager jpaPlatformManager = getJpaPlatformManager();
		return (jpaPlatformManager != null) ? jpaPlatformManager.getJpaPlatformConfigs() : IterableTools.<JpaPlatform.Config>emptyIterable();
	}

	/* CU private */ static JpaPlatformManager getJpaPlatformManager() {
		JpaWorkspace jpaWorkspace = getJpaWorkspace();
		return (jpaWorkspace == null) ? null : jpaWorkspace.getJpaPlatformManager();
	}

	/* CU private */ static JpaWorkspace getJpaWorkspace() {
		JpaWorkbench jpaWorkbench = getJpaWorkbench();
		return (jpaWorkbench == null) ? null : jpaWorkbench.getJpaWorkspace();
	}

	/* CU private */ static JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(PlatformUI.getWorkbench(), JpaWorkbench.class);
	}


	// ********** connection group **********

	private void buildConnectionGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JpaFacetWizardPage_connectionLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);

		Combo connectionDropDown = this.buildDropDown(group, 3);
		SWTBindTools.bind(
				CONNECTION_CHOICES_MODEL,
				this.connectionModel,
				connectionDropDown,
				SIMPLE_STRING_TRANSFORMER
			);

		Link addConnectionLink = this.buildLink(group, JptJpaUiMessages.JpaFacetWizardPage_connectionLink);
		addConnectionLink.addSelectionListener(new AddConnectionLinkListener());  // the link will be GCed

		this.connectLink = this.buildLink(group, this.buildConnectLinkText());
		SWTBindTools.controlEnabledState(this.disconnectedModel, this.connectLink);
		this.connectLink.addSelectionListener(new ConnectLinkListener());  // the link will be GCed

		// override default catalog
		Button overrideDefaultCatalogCheckBox = this.buildCheckBox(group, 3, JptJpaUiMessages.JpaFacetWizardPage_overrideDefaultCatalogLabel);
		SWTBindTools.bind(this.userOverrideDefaultCatalogFlagModel, overrideDefaultCatalogCheckBox);

		Label defaultCatalogLabel = this.buildLabel(group, JptJpaUiMessages.JpaFacetWizardPage_defaultCatalogLabel);
		Combo defaultCatalogDropDown = this.buildDropDown(group);
		SWTBindTools.bind(this.catalogChoicesModel, this.defaultCatalogModel, defaultCatalogDropDown);

		SWTBindTools.controlEnabledState(this.userOverrideDefaultCatalogFlagModel, defaultCatalogLabel, defaultCatalogDropDown);

		// override default schema
		Button overrideDefaultSchemaButton = this.buildCheckBox(group, 3, JptJpaUiMessages.JpaFacetWizardPage_overrideDefaultSchemaLabel);
		SWTBindTools.bind(this.userOverrideDefaultSchemaFlagModel, overrideDefaultSchemaButton);

		Label defaultSchemaLabel = this.buildLabel(group, JptJpaUiMessages.JpaFacetWizardPage_defaultSchemaLabel);
		Combo defaultSchemaDropDown = this.buildDropDown(group);
		SWTBindTools.bind(this.schemaChoicesModel, this.defaultSchemaModel, defaultSchemaDropDown);

		SWTBindTools.controlEnabledState(this.userOverrideDefaultSchemaFlagModel, defaultSchemaLabel, defaultSchemaDropDown);

		SWTBindTools.controlEnabledState(this.jpaProjectNotNullFlagModel, group, connectionDropDown, addConnectionLink, overrideDefaultCatalogCheckBox, overrideDefaultSchemaButton);
	}

	private static final Transformer<String, String> SIMPLE_STRING_TRANSFORMER = TransformerTools.passThruTransformer(JptJpaUiMessages.JpaFacetWizardPage_none);

	private PropertyChangeListener buildDisconnectedModelListener() {
		return new SWTPropertyChangeListenerWrapper(new DisconnectedModelListener());
	}

	/* CU private */ class DisconnectedModelListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaProjectPropertiesPage.this.updateConnectLinkText();
		}
	}

	/* CU private */ void updateConnectLinkText() {
		this.updateConnectLinkText(this.buildConnectLinkText());
	}

	private String buildConnectLinkText() {
		ConnectionProfile connectionProfile = this.getConnectionProfile();
		return ((connectionProfile != null) && connectionProfile.isConnected()) ?
				JptJpaUiMessages.JpaFacetWizardPage_connectedText :
				JptJpaUiMessages.JpaFacetWizardPage_connectLink;
	}

	private void updateConnectLinkText(String text) {
		if (this.connectLink.isDisposed()) {
			return;
		}
		this.connectLink.setText(text);
		SWTUtil.reflow(this.connectLink.getParent());
	}

	/* CU private */ class AddConnectionLinkListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e) {
			JpaProjectPropertiesPage.this.openNewConnectionWizard();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	void openNewConnectionWizard() {
		String connectionName = DTPUiTools.createNewConnectionProfile();
		if (connectionName != null) {
			this.connectionModel.setValue(connectionName);
		}
	}

	/* CU private */ class ConnectLinkListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e) {
			JpaProjectPropertiesPage.this.openConnectionProfile();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
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
		group.setText(JptJpaUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button discoverClassesRadioButton = this.buildRadioButton(group, 1, JptJpaUiMessages.JpaFacetWizardPage_discoverClassesButton);
		SWTBindTools.bind(this.discoverAnnotatedClassesModel, discoverClassesRadioButton);

		Button listClassesRadioButton = this.buildRadioButton(group, 1, JptJpaUiMessages.JpaFacetWizardPage_listClassesButton);
		SWTBindTools.bind(this.listAnnotatedClassesModel, listClassesRadioButton);

		SWTBindTools.controlEnabledState(this.jpaProjectNotNullFlagModel, group, discoverClassesRadioButton, listClassesRadioButton);
	}


	// ********** metamodel group **********

	private void buildMetamodelGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JpaFacetWizardPage_metamodelLabel);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Link metamodelSourceFolderLink = this.buildLink(group, JptJpaUiMessages.JpaFacetWizardPage_metamodelSourceFolderLink);
		metamodelSourceFolderLink.addSelectionListener(new MetamodelSourceFolderLinkListener());  // the link will be GCed
		Combo metamodelSourceFolderDropDown = this.buildDropDown(group);
		SWTBindTools.bind(
				this.javaSourceFolderChoicesModel,
				this.metamodelSourceFolderModel,
				metamodelSourceFolderDropDown,
				SIMPLE_STRING_TRANSFORMER
		);

		PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_METAMODEL);

		SWTBindTools.controlVisibleState(this.jpa2_0ProjectFlagModel, group, metamodelSourceFolderLink, metamodelSourceFolderDropDown);
	}

	/* CU private */ class MetamodelSourceFolderLinkListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent e) {
			JpaProjectPropertiesPage.this.openJavaBuildPathPage();
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	void openJavaBuildPathPage() {
		IWorkbenchPreferenceContainer container = this.getWorkbenchPreferenceContainer();
		container.openPage(BUILD_PATHS_PROPERTY_PAGE_ID, null);
	}

	// ********** OK/Revert/Apply behavior **********

	@Override
	protected boolean projectRebuildRequired() {
		return this.jpaPlatformConfigModel.isBuffering();
	}

	@Override
	protected void rebuildProject() throws CoreException {
		// if the JPA platform is changed, we need to completely rebuild the JPA project
		try {
			this.rebuildProject_();
		} catch (InterruptedException ex) {
			throw new CoreException(JptJpaUiPlugin.instance().buildStatus(IStatus.CANCEL, ex));
		}
	}

	private void rebuildProject_() throws InterruptedException {
		JpaProject.Reference ref = this.getJpaProjectReference();
		if (ref != null) {
			ref.rebuild();
		}
	}

	private JpaProject.Reference getJpaProjectReference() {
		return ((JpaProject.Reference) this.getProject().getAdapter(JpaProject.Reference.class));
	}

	@Override
	protected BufferedModifiablePropertyValueModel<?>[] buildBufferedModels() {
		return new BufferedModifiablePropertyValueModel[] {
				this.jpaPlatformConfigModel,
				this.connectionModel,
				this.userOverrideDefaultCatalogFlagModel,
				this.userOverrideDefaultCatalogModel,
				this.userOverrideDefaultSchemaFlagModel,
				this.userOverrideDefaultSchemaModel,
				this.discoverAnnotatedClassesModel,
				this.metamodelSourceFolderModel
		};
	}


	// ********** validation **********

	@Override
	protected Model[] buildValidationModels() {
		return new Model[] {
				this.jpaPlatformConfigModel,
				this.connectionModel,
				this.userOverrideDefaultCatalogFlagModel,
				this.defaultCatalogModel,
				this.userOverrideDefaultSchemaFlagModel,
				this.defaultSchemaModel,
				this.discoverAnnotatedClassesModel
		};
	}

	@Override
	protected void performValidation(Map<Integer, ArrayList<IStatus>> statuses) {
		JpaProject jpaProject = this.jpaProjectModel.getValue();
		if (jpaProject == null) {
			return;
		}
		/* platform */
		// user is unable to unset the platform, so no validation necessary

		/* library provider */
		this.validateLibraryProvider(statuses);

		/* connection */
		ConnectionProfile connectionProfile = this.getConnectionProfile();
		String connectionName = this.getConnectionName();
		if ( ! StringTools.isBlank(connectionName)) {
			if (connectionProfile == null) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(NLS.bind(
						JptJpaCoreMessages.VALIDATE_CONNECTION_INVALID,
						connectionName)));
			}
			else if ( ! connectionProfile.isActive()) {
				statuses.get(INFO_STATUS).add(this.buildInfoStatus(JptJpaCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED));
			}
		}

		/* default catalog */
		if (this.userOverrideDefaultCatalogFlagIsSet()) {
			String defaultCatalog = this.getUserOverrideDefaultCatalog();
			if (StringTools.isBlank(defaultCatalog)) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(JptJpaCoreMessages.VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED));
			}
			else if ((connectionProfile != null)
					&& connectionProfile.isConnected()
					&& ! IterableTools.contains(this.catalogChoicesModel, defaultCatalog)) {
				statuses.get(WARNING_STATUS).add(this.buildWarningStatus(NLS.bind(
						JptJpaCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_CATALOG,
						defaultCatalog
				)));
			}
		}

		/* default schema */
		if (this.userOverrideDefaultSchemaFlagIsSet()) {
			String defaultSchema = this.getUserOverrideDefaultSchema();
			if (StringTools.isBlank(defaultSchema)) {
				statuses.get(ERROR_STATUS).add(this.buildErrorStatus(JptJpaCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED));
			}
			else if ((connectionProfile != null)
					&& connectionProfile.isConnected()
					&& ! IterableTools.contains(this.schemaChoicesModel, defaultSchema)) {
				statuses.get(WARNING_STATUS).add(this.buildWarningStatus(NLS.bind(
						JptJpaCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_SCHEMA,
						defaultSchema
				)));
			}
		}
	}

	private void validateLibraryProvider(Map<Integer, ArrayList<IStatus>> statuses) {
        LibraryInstallDelegate libInstallDelegate = this.getLibraryInstallDelegate();
        if (libInstallDelegate == null) {
        	return;
        }

        Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_ENABLEMENT_EXP, this.jpaPlatformConfigModel.getValue());
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP, this.jpaProjectModel.getValue().getJpaPlatform().getConfig());

		if ( ! libInstallDelegate.getLibraryProvider().isEnabledFor(
				this.getFacetedProject(), this.getProjectFacetVersion(), enablementVariables)) {
			this.addStatus(this.buildErrorStatus(JptJpaCoreMessages.VALIDATE_LIBRARY_PROVIDER_INVALID), statuses);
		}
	}


	// ********** UI model adapters **********

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
		protected JpaDataSource transform_(JpaProject v) {
			return v.getDataSource();
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
		extends TransformationPropertyValueModel<String, ConnectionProfile>
	{
		ConnectionProfileModel(PropertyValueModel<String> connectionModel) {
			super(connectionModel);
		}

		@Override
		protected ConnectionProfile transform_(String connectionName) {
			ConnectionProfileFactory factory = getConnectionProfileFactory();
			return (factory == null) ? null : factory.buildConnectionProfile(connectionName);
		}
	}


	/**
	 * Treat the JPA platform config as an "aspect" of the JPA project.
	 * The JPA platform ID is stored in the project preferences.
	 * The JPA platform does not change for a JPA project - if the user wants a
	 * different JPA platform, we build an entirely new JPA project.
	 */
	static class JpaPlatformConfigModel
		extends AspectPropertyValueModelAdapter<JpaProject, JpaPlatform.Config>
	{
		JpaPlatformConfigModel(PropertyValueModel<JpaProject> jpaProjectModel) {
			super(jpaProjectModel);
		}

		@Override
		protected JpaPlatform.Config buildValue_() {
			String jpaPlatformID = JpaPreferences.getJpaPlatformID(this.subject.getProject());
			JpaPlatformManager jpaPlatformManager = getJpaPlatformManager();
			return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(jpaPlatformID);
		}

		@Override
		public void setValue_(JpaPlatform.Config jpaPlatformConfig) {
			String jpaPlatformID = jpaPlatformConfig.getId();
			JpaPreferences.setJpaPlatformID(this.subject.getProject(), jpaPlatformID);
		}

		@Override
		protected void engageSubject_() {
			// the JPA platform does not change
		}

		@Override
		protected void disengageSubject_() {
			// the JPA platform does not change
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
		private final ConnectionProfileFactory connectionProfileFactory;
		private final ConnectionProfileListener connectionProfileListener;

		ConnectionChoicesModel() {
			super();
			this.connectionProfileFactory = getConnectionProfileFactory();
			this.connectionProfileListener = new LocalConnectionProfileListener();
		}

		/* class private */ class LocalConnectionProfileListener
			extends ConnectionProfileAdapter
		{
			@Override
			public void connectionProfileAdded(String name) {
				ConnectionChoicesModel.this.collectionChanged();
			}

			@Override
			public void connectionProfileRemoved(String name) {
				ConnectionChoicesModel.this.collectionChanged();
			}

			@Override
			public void connectionProfileRenamed(String oldName, String newName) {
				// Ignore this event for now. Connecting a profile actually
				// throws a connection renamed event, which messes up the
				// list selection. There shouldn't be a connection renamed
				// within the scope of this dialog anyhow.
				// ConnectionChoicesModel.this.collectionChanged();
			}
		}

		void collectionChanged() {
			this.fireCollectionChanged(CollectionValueModel.VALUES, CollectionTools.collection(this.iterator()));
		}

		public Iterator<String> iterator() {
			return this.getConnectionProfileNames().iterator();
		}

		private Iterable<String> getConnectionProfileNames() {
			return (this.connectionProfileFactory != null) ? this.connectionProfileFactory.getConnectionProfileNames() : IterableTools.<String>emptyIterable();
		}

		public int size() {
			return IterableTools.size(this.getConnectionProfileNames());
		}

		@Override
		protected void engageModel() {
			if (this.connectionProfileFactory != null) {
				this.connectionProfileFactory.addConnectionProfileListener(this.connectionProfileListener);
			}
		}

		@Override
		protected void disengageModel() {
			if (this.connectionProfileFactory != null) {
				this.connectionProfileFactory.removeConnectionProfileListener(this.connectionProfileListener);
			}
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
			return ! StringTools.isBlank(this.getUserOverrideDefault());
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
			return this.subjectIsInJpa2_0Project() ? ((JpaProject2_0) this.subject).getMetamodelSourceFolderName() : null;
		}

		@Override
		protected void setValue_(String value) {
			if (this.subjectIsInJpa2_0Project()) {
				((JpaProject2_0) this.subject).setMetamodelSourceFolderName(value);
			}
		}

		private boolean subjectIsInJpa2_0Project() {
			return this.subject.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(JpaProject2_0.FACET_VERSION_STRING);
		}
	}


	/**
	 * Java project source folders.
	 * We keep the metamodel source folder in sync with the Java source folders
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
			this.javaElementChangedListener = new JavaElementChangedListener();
		}

		/* class private */ class JavaElementChangedListener
			implements IElementChangedListener
		{
			public void elementChanged(ElementChangedEvent event) {
				JavaSourceFolderChoicesModel.this.processJavaDelta(event.getDelta());
			}
			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
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
			return this.subjectIsInJpa2_0Project() ?
					((JpaProject2_0) this.subject).getJavaSourceFolderNames() :
					EmptyIterable.<String>instance();
		}

		private boolean subjectIsInJpa2_0Project() {
			return this.subject.getJpaProject().getJpaPlatform().getJpaVersion().isCompatibleWithJpaVersion(JpaProject2_0.FACET_VERSION_STRING);
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
			return new LocalConnectionListener();
		}

		class LocalConnectionListener
			extends ConnectionAdapter
		{
			@Override
			public void opened(ConnectionProfile profile) {
				ConnectionProfilePropertyAspectAdapter.this.connectionOpened(profile);
			}
		}

		void connectionOpened(ConnectionProfile profile) {
			if (profile.equals(this.subject)) {
				this.aspectChanged();
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
			this.catalogListener = new CatalogListener();
		}

		/* class private */ class CatalogListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DatabaseDefaultSchemaModel.this.catalogChanged();
			}
		}

		void catalogChanged() {
			this.aspectChanged();
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
			return new LocalConnectionListener();
		}

		class LocalConnectionListener
			extends ConnectionAdapter
		{
			@Override
			public void opened(ConnectionProfile profile) {
				ConnectionProfileCollectionAspectAdapter.this.connectionOpened(profile);
			}
		}

		void connectionOpened(ConnectionProfile profile) {
			if (profile.equals(this.subject)) {
				this.aspectChanged();
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
			this.catalogListener = new CatalogListener();
		}

		/* class private */ class CatalogListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DatabaseSchemaChoicesModel.this.catalogChanged();
			}
		}

		void catalogChanged() {
			this.aspectChanged();
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
		extends CompositePropertyValueModel<String, Object>
		implements ModifiablePropertyValueModel<String>
	{
		private final PropertyValueModel<Boolean> userOverrideDefaultFlagModel;
		private final ModifiablePropertyValueModel<String> userOverrideDefaultModel;
		private final PropertyValueModel<String> databaseDefaultModel;

		@SuppressWarnings("unchecked")
		DefaultModel(
				PropertyValueModel<Boolean> userOverrideDefaultFlagModel,
				ModifiablePropertyValueModel<String> userOverrideDefaultModel,
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
		protected void componentChanged(PropertyChangeEvent event) {
			super.componentChanged(event);
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
	}
}
