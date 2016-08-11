/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.properties.JptProjectPropertiesPage;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ControlTools;
import org.eclipse.jpt.common.utility.Association;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.closure.ClosureAdapter;
import org.eclipse.jpt.common.utility.internal.closure.ClosureTools;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.AbstractCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.AspectCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.PluggableModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PluggablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SetCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
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
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaModel;
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
	private PropertyValueModel<JpaProject2_0> jpaProject2_0Model;
	private PropertyValueModel<Boolean> jpaProjectIsNotNullFlagModel;

	private ModifiablePropertyValueModel<JpaPlatform.Config> jpaPlatformConfigModel;
	private PropertyValueModel<Boolean> jpaPlatformConfigModelBufferingFlag;
	private PropertyChangeListener jpaPlatformConfigListener;

	private ModifiablePropertyValueModel<String> connectionModel;
	private PropertyValueModel<Boolean> connectionModelBufferingFlag;
	private PropertyValueModel<ConnectionProfile> connectionProfileModel;
	private PropertyValueModel<Boolean> disconnectedModel;
	private PropertyChangeListener disconnectedModelListener;
	private Link connectLink;

	private ModifiablePropertyValueModel<Boolean> userOverrideDefaultCatalogFlagModel;
	private PropertyValueModel<Boolean> userOverrideDefaultCatalogFlagModelBufferingFlag;
	private ModifiablePropertyValueModel<String> userOverrideDefaultCatalogModel;
	private PropertyValueModel<Boolean> userOverrideDefaultCatalogModelBufferingFlag;
	private ModifiablePropertyValueModel<String> defaultCatalogModel;
	private ListValueModel<String> catalogChoicesModel;

	private ModifiablePropertyValueModel<Boolean> userOverrideDefaultSchemaFlagModel;
	private PropertyValueModel<Boolean> userOverrideDefaultSchemaFlagModelBufferingFlag;
	private ModifiablePropertyValueModel<String> userOverrideDefaultSchemaModel;
	private PropertyValueModel<Boolean> userOverrideDefaultSchemaModelBufferingFlag;
	private ModifiablePropertyValueModel<String> defaultSchemaModel;
	private ListValueModel<String> schemaChoicesModel;

	private ModifiablePropertyValueModel<Boolean> discoverAnnotatedClassesModel;
	private PropertyValueModel<Boolean> discoverAnnotatedClassesModelBufferingFlag;
	private ModifiablePropertyValueModel<Boolean> listAnnotatedClassesModel;

	private PropertyValueModel<Boolean> jpa2_0ProjectFlagModel;

	private ModifiablePropertyValueModel<String> metamodelSourceFolderModel;
	private PropertyValueModel<Boolean> metamodelSourceFolderModelBufferingFlag;
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
		this.jpaProject2_0Model = this.buildJpaProject2_0Model();
		this.jpaProjectIsNotNullFlagModel = this.buildJpaProjectIsNotNullFlagModel();

		Association<ModifiablePropertyValueModel<JpaPlatform.Config>, PropertyValueModel<Boolean>> platformConfigAssoc = this.buildJpaPlatformConfigModel();
		this.jpaPlatformConfigModel = platformConfigAssoc.getKey();
		this.jpaPlatformConfigModelBufferingFlag = platformConfigAssoc.getValue();
		this.jpaPlatformConfigListener = this.buildJpaPlatformConfigListener();

		Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> connectionAssoc = this.buildConnectionModel();
		this.connectionModel = connectionAssoc.getKey();
		this.connectionModelBufferingFlag = connectionAssoc.getValue();
		this.connectionProfileModel = this.buildConnectionProfileModel();
		this.disconnectedModel = this.buildDisconnectedModel();
		this.disconnectedModelListener = this.buildDisconnectedModelListener();

		Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> userOverrideDefaultCatalogFlagAssoc = this.buildUserOverrideDefaultCatalogFlagModel();
		this.userOverrideDefaultCatalogFlagModel = userOverrideDefaultCatalogFlagAssoc.getKey();
		this.userOverrideDefaultCatalogFlagModelBufferingFlag = userOverrideDefaultCatalogFlagAssoc.getValue();
		Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> userOverrideDefaultCatalogAssoc = this.buildUserOverrideDefaultCatalogModel();
		this.userOverrideDefaultCatalogModel = userOverrideDefaultCatalogAssoc.getKey();
		this.userOverrideDefaultCatalogModelBufferingFlag = userOverrideDefaultCatalogAssoc.getValue();
		this.defaultCatalogModel = this.buildDefaultCatalogModel();
		this.catalogChoicesModel = this.buildCatalogChoicesModel();

		Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> userOverrideDefaultSchemaFlagAssoc = this.buildUserOverrideDefaultSchemaFlagModel();
		this.userOverrideDefaultSchemaFlagModel = userOverrideDefaultSchemaFlagAssoc.getKey();
		this.userOverrideDefaultSchemaFlagModelBufferingFlag = userOverrideDefaultSchemaFlagAssoc.getValue();
		Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> userOverrideDefaultSchemaAssoc = this.buildUserOverrideDefaultSchemaModel();
		this.userOverrideDefaultSchemaModel = userOverrideDefaultSchemaAssoc.getKey();
		this.userOverrideDefaultSchemaModelBufferingFlag = userOverrideDefaultSchemaAssoc.getValue();
		this.defaultSchemaModel = this.buildDefaultSchemaModel();
		this.schemaChoicesModel = this.buildSchemaChoicesModel();

		Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> discoverAnnotatedClassesAssoc = this.buildDiscoverAnnotatedClassesModel();
		this.discoverAnnotatedClassesModel = discoverAnnotatedClassesAssoc.getKey();
		this.discoverAnnotatedClassesModelBufferingFlag = discoverAnnotatedClassesAssoc.getValue();
		this.listAnnotatedClassesModel = this.buildListAnnotatedClassesModel();

		this.jpa2_0ProjectFlagModel = this.buildJpa2_0ProjectFlagModel();

		Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> metamodelSourceFolderAssoc = this.buildMetamodelSourceFolderModel();
		this.metamodelSourceFolderModel = metamodelSourceFolderAssoc.getKey();
		this.metamodelSourceFolderModelBufferingFlag = metamodelSourceFolderAssoc.getValue();
		this.javaSourceFolderChoicesModel = this.buildJavaSourceFolderChoicesModel();
	}

	// ***** JPA project model
	private PropertyValueModel<JpaProject> buildJpaProjectModel() {
		return PropertyValueModelTools.compound(this.buildJpaProjectModelModel());
	}

	private PropertyValueModel<PropertyValueModel<JpaProject>> buildJpaProjectModelModel() {
		return PropertyValueModelTools.transform(this.projectModel, JPA_PROJECT_MODEL_TRANSFORMER);
	}

	private static final Transformer<IProject, PropertyValueModel<JpaProject>> JPA_PROJECT_MODEL_TRANSFORMER = new JpaProjectModelTransformer();

	/* CU private */ static class JpaProjectModelTransformer
		extends TransformerAdapter<IProject, PropertyValueModel<JpaProject>>
	{
		@Override
		public PropertyValueModel<JpaProject> transform(IProject project) {
			return project.getAdapter(JpaProjectModel.class);
		}
	}

	// ***** JPA 2.0 project model
	private PropertyValueModel<JpaProject2_0> buildJpaProject2_0Model() {
		return PropertyValueModelTools.filter(this.jpaProjectModel, JpaProject2_0.class);
	}

	// ***** JPA project is not null model
	private PropertyValueModel<Boolean> buildJpaProjectIsNotNullFlagModel() {
		return PropertyValueModelTools.valueIsNotNull(this.jpaProjectModel);
	}

	// ***** JPA platform config model
	/**
	 * The JPA platform ID is stored in the project preferences.
	 * The JPA platform does not change for a JPA project - if the user wants a
	 * different JPA platform, we build an entirely new JPA project.
	 */
	private Association<ModifiablePropertyValueModel<JpaPlatform.Config>, PropertyValueModel<Boolean>> buildJpaPlatformConfigModel() {
		return PropertyValueModelTools.buffer(this.buildJpaPlatformConfigModel_(), this.trigger);
	}
	
	private ModifiablePropertyValueModel<JpaPlatform.Config> buildJpaPlatformConfigModel_() {
		return PropertyValueModelTools.transform(
				this.jpaProjectModel,
				JPA_PROJECT_PLATFORM_CONFIG_TRANSFORMER,
				new JpaProjectSetPlatformConfigClosure()
			);
	}
	
	private static final TransformerAdapter<JpaProject, JpaPlatform.Config> JPA_PROJECT_PLATFORM_CONFIG_TRANSFORMER = new JpaProjectPlatformConfigTransformer();
	static class JpaProjectPlatformConfigTransformer
		extends TransformerAdapter<JpaProject, JpaPlatform.Config>
	{
		@Override
		public JpaPlatform.Config transform(JpaProject jpaProject) {
			String jpaPlatformID = JpaPreferences.getJpaPlatformID(jpaProject.getProject());
			JpaPlatformManager jpaPlatformManager = getJpaPlatformManager();
			return (jpaPlatformManager == null) ? null : jpaPlatformManager.getJpaPlatformConfig(jpaPlatformID);
		}
	}

	class JpaProjectSetPlatformConfigClosure
		extends ClosureAdapter<JpaPlatform.Config>
	{
		@Override
		public void execute(JpaPlatform.Config platformConfig) {
			JpaProjectPropertiesPage.this.setJpaProjectPlatformConfig(platformConfig);
		}
	}

	void setJpaProjectPlatformConfig(JpaPlatform.Config jpaPlatformConfig) {
		String jpaPlatformID = jpaPlatformConfig.getId();
		JpaPreferences.setJpaPlatformID(this.jpaProjectModel.getValue().getProject(), jpaPlatformID);
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
	private Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> buildConnectionModel() {
		return PropertyValueModelTools.buffer(this.buildConnectionModel_(), this.trigger);
	}

	private ModifiablePropertyValueModel<String> buildConnectionModel_() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.buildDataSourceModel(),
				JpaDataSource.CONNECTION_PROFILE_NAME_PROPERTY,
				JpaDataSource.CONNECTION_PROFILE_NAME_TRANSFORMER,
				JpaDataSource.SET_CONNECTION_PROFILE_NAME_CLOSURE
			);
	}

	private PropertyValueModel<JpaDataSource> buildDataSourceModel() {
		return PropertyValueModelTools.transform(this.jpaProjectModel, JpaProject.DATA_SOURCE_TRANSFORMER);
	}

	private PropertyValueModel<ConnectionProfile> buildConnectionProfileModel() {
		return PropertyValueModelTools.transform(this.connectionModel, CONNECTION_PROFILE_TRANSFORMER);
	}

	private static final Transformer<String, ConnectionProfile> CONNECTION_PROFILE_TRANSFORMER = new ConnectionProfileTransformer();
	static class ConnectionProfileTransformer
		extends TransformerAdapter<String, ConnectionProfile>
	{
		@Override
		public ConnectionProfile transform(String connectionName) {
			ConnectionProfileFactory factory = getConnectionProfileFactory();
			return (factory == null) ? null : factory.buildConnectionProfile(connectionName);
		}
	}

	/**
	 * Monitor the connection profile's connection to the database
	 * (used to enable the "Connect" link)
	 */
	private PropertyValueModel<Boolean> buildDisconnectedModel() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.connectionProfileModel,
				new ConnectionProfilePropertyAspectAdapter.Factory<>(TransformerTools.adapt(ConnectionProfile.DISCONNECTED_PREDICATE))
			);
	}

	// ***** catalog models
	private Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> buildUserOverrideDefaultCatalogFlagModel() {
		return PropertyValueModelTools.buffer(this.buildUserOverrideDefaultCatalogFlagModel_(), this.trigger);
	}

	/**
	 * Whether the JPA project has a user override default catalog specified.
	 */
	private ModifiablePropertyValueModel<Boolean> buildUserOverrideDefaultCatalogFlagModel_() {
		return PropertyValueModelTools.transform(
				this.buildUserOverrideDefaultCatalogModel_(),
				TransformerTools.adapt(StringTools.IS_NOT_BLANK),
				ClosureTools.nullClosure()
			);
	}

	private Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> buildUserOverrideDefaultCatalogModel() {
		return PropertyValueModelTools.buffer(this.buildUserOverrideDefaultCatalogModel_(), this.trigger);
	}

	private ModifiablePropertyValueModel<String> buildUserOverrideDefaultCatalogModel_() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.jpaProjectModel,
				JpaProject.USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY,
				JpaProject.USER_OVERRIDE_DEFAULT_CATALOG_TRANSFORMER,
				JpaProject.SET_USER_OVERRIDE_DEFAULT_CATALOG_CLOSURE
			);
	}

	private ModifiablePropertyValueModel<String> buildDefaultCatalogModel() {
		return PropertyValueModelTools.modifiableModel(this.buildDefaultCatalogModelAdapterFactory());
	}

	private PluggableModifiablePropertyValueModel.Adapter.Factory<String> buildDefaultCatalogModelAdapterFactory() {
		return new DefaultDatabaseComponentModelAdapter.Factory(
					this.userOverrideDefaultCatalogFlagModel,
					this.userOverrideDefaultCatalogModel,
					this.buildDatabaseDefaultCatalogModel()
				);
	}

	/**
	 * Database-determined default catalog
	 */
	private PropertyValueModel<String> buildDatabaseDefaultCatalogModel() {
		return PropertyValueModelTools.subjectModelAspectAdapter(
				this.connectionProfileModel,
				new ConnectionProfilePropertyAspectAdapter.Factory<>(CONNECTION_PROFILE_DATABASE_DEFAULT_CATALOG_TRANSFORMER)
			);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	private ListValueModel<String> buildCatalogChoicesModel() {
		return new SortedListValueModelAdapter<>(this.buildUnsortedCatalogChoicesModel(), STRING_COMPARATOR);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	@SuppressWarnings("unchecked")
	private CollectionValueModel<String> buildUnsortedCatalogChoicesModel() {
		return new SetCollectionValueModel<>(
					CompositeCollectionValueModel.forModels(
							new PropertyCollectionValueModelAdapter<>(this.defaultCatalogModel),
							this.buildDatabaseCatalogChoicesModel()
					)
			);
	}

	private CollectionValueModel<String> buildDatabaseCatalogChoicesModel() {
		return new DatabaseCatalogChoicesModel(this.connectionProfileModel);
	}

	// ***** schema models
	private Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> buildUserOverrideDefaultSchemaFlagModel() {
		return PropertyValueModelTools.buffer(this.buildUserOverrideDefaultSchemaFlagModel_(), this.trigger);
	}

	/**
	 * Whether the JPA project has a user override default schema specified.
	 */
	private ModifiablePropertyValueModel<Boolean> buildUserOverrideDefaultSchemaFlagModel_() {
		return PropertyValueModelTools.transform(
				this.buildUserOverrideDefaultSchemaModel_(),
				TransformerTools.adapt(StringTools.IS_NOT_BLANK),
				ClosureTools.nullClosure()
			);
	}

	private Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> buildUserOverrideDefaultSchemaModel() {
		return PropertyValueModelTools.buffer(this.buildUserOverrideDefaultSchemaModel_(), this.trigger);
	}

	private ModifiablePropertyValueModel<String> buildUserOverrideDefaultSchemaModel_() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.jpaProjectModel,
				JpaProject.USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY,
				JpaProject.USER_OVERRIDE_DEFAULT_SCHEMA_TRANSFORMER,
				JpaProject.SET_USER_OVERRIDE_DEFAULT_SCHEMA_CLOSURE
			);
	}

	private ModifiablePropertyValueModel<String> buildDefaultSchemaModel() {
		return PropertyValueModelTools.modifiableModel(this.buildDefaultSchemaModelAdapterFactory());
	}

	private PluggableModifiablePropertyValueModel.Adapter.Factory<String> buildDefaultSchemaModelAdapterFactory() {
		return new DefaultDatabaseComponentModelAdapter.Factory(
					this.userOverrideDefaultSchemaFlagModel,
					this.userOverrideDefaultSchemaModel,
					this.buildDatabaseDefaultSchemaModel()
				);
	}

	private PropertyValueModel<String> buildDatabaseDefaultSchemaModel() {
		return PropertyValueModelTools.propertyValueModel(this.buildDatabaseDefaultSchemaModelAdapterFactory());
	}

	private PluggablePropertyValueModel.Adapter.Factory<String> buildDatabaseDefaultSchemaModelAdapterFactory() {
		return new DatabaseDefaultSchemaModelAdapter.Factory(this.connectionProfileModel, this.disconnectedModel, this.defaultCatalogModel);
	}

	/**
	 * Add the default catalog if it is not on the list from the database
	 */
	private ListValueModel<String> buildSchemaChoicesModel() {
		return new SortedListValueModelAdapter<>(this.buildUnsortedSchemaChoicesModel(), STRING_COMPARATOR);
	}

	@SuppressWarnings("unchecked")
	private CollectionValueModel<String> buildUnsortedSchemaChoicesModel() {
		return new SetCollectionValueModel<>(
				CompositeCollectionValueModel.forModels(
						new PropertyCollectionValueModelAdapter<>(this.defaultSchemaModel),
						this.buildDatabaseSchemaChoicesModel()
				)
			);
	}

	private CollectionValueModel<String> buildDatabaseSchemaChoicesModel() {
		return new DatabaseSchemaChoicesModel(this.connectionProfileModel, this.defaultCatalogModel);
	}

	// ***** discover/list annotated classes models
	private Association<ModifiablePropertyValueModel<Boolean>, PropertyValueModel<Boolean>> buildDiscoverAnnotatedClassesModel() {
		return PropertyValueModelTools.buffer(this.buildDiscoverAnnotatedClassesModel_(), this.trigger);
	}

	private ModifiablePropertyValueModel<Boolean> buildDiscoverAnnotatedClassesModel_() {
		return PropertyValueModelTools.modifiableBooleanSubjectModelAspectAdapter(
				this.jpaProjectModel,
				JpaProject.DISCOVERS_ANNOTATED_CLASSES_PROPERTY,
				JpaProject.DISCOVERS_ANNOTATED_CLASSES_PREDICATE,
				JpaProject.SET_DISCOVERS_ANNOTATED_CLASSES_CLOSURE
			);
	}

	/**
	 * The opposite of the "discover annotated classes" flag.
	 */
	private ModifiablePropertyValueModel<Boolean> buildListAnnotatedClassesModel() {
		return PropertyValueModelTools.transform_(
				this.discoverAnnotatedClassesModel,
				TransformerTools.notBooleanTransformer(),
				TransformerTools.notBooleanTransformer()
			);
	}

	// ***** JPA 2.0 project flag
	private PropertyValueModel<Boolean> buildJpa2_0ProjectFlagModel() {
		return PropertyValueModelTools.valueAffirms(this.jpaProjectModel, IS_COMPATIBLE_WITH_JPA_2_0, false);
	}

	private static final Predicate<JpaModel> IS_COMPATIBLE_WITH_JPA_2_0 = new JpaModel.JpaVersionIsCompatibleWith<>(JpaProject2_0.FACET_VERSION_STRING);

	// ***** metamodel models
	private Association<ModifiablePropertyValueModel<String>, PropertyValueModel<Boolean>> buildMetamodelSourceFolderModel() {
		return PropertyValueModelTools.buffer(this.buildMetamodelSourceFolderModel_(), this.trigger);
	}

	/**
	 * The folder where the source for the generated Canonical Metamodel is written.
	 */
	private ModifiablePropertyValueModel<String> buildMetamodelSourceFolderModel_() {
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.jpaProject2_0Model,
				JpaProject2_0.METAMODEL_SOURCE_FOLDER_NAME_PROPERTY,
				JpaProject2_0.METAMODEL_SOURCE_FOLDER_NAME_TRANSFORMER,
				JpaProject2_0.SET_METAMODEL_SOURCE_FOLDER_NAME_CLOSURE
			);
	}

	private ListValueModel<String> buildJavaSourceFolderChoicesModel() {
		// by default, ExtendedListValueModelWrapper puts a null at the top of the list
		return new ExtendedListValueModelWrapper<>(
					new SortedListValueModelAdapter<>(
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
		Map<String, Object> enablementVariables = new HashMap<>();

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

		ArrayList<JpaLibraryProviderInstallOperationConfig> jpaConfigs = new ArrayList<>();
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
				JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_JPA_IMPLEMENTATION_LABEL);

 		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		SWTBindingTools.bindEnabledState(this.jpaProjectIsNotNullFlagModel, libraryProviderComposite);

		this.buildConnectionGroup(parent);
		this.buildPersistentClassManagementGroup(parent);
		this.buildMetamodelGroup(parent);

		WorkbenchTools.setHelp(parent, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
	}

	@Override
	protected void engageListeners_() {
		super.engageListeners_();
		this.jpaPlatformConfigModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaPlatformConfigListener);
		this.disconnectedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);
	}

	@Override
	protected void disengageListeners_() {
		this.jpaPlatformConfigModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaPlatformConfigListener);
		this.disconnectedModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.disconnectedModelListener);
		super.disengageListeners_();
	}


	// ********** platform group **********

	private void buildPlatformGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_PLATFORM_LABEL);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Combo jpaPlatformDropDown = this.buildDropDown(group);
		SWTBindingTools.bindDropDownListBox(
				this.buildJpaPlatformConfigChoicesModel(),
				this.jpaPlatformConfigModel,
				jpaPlatformDropDown,
				JPA_PLATFORM_CONFIG_LABEL_CONVERTER);

		Link facetsPageLink = this.buildFacetsPageLink(group, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_FACETS_PAGE_LINK);

		SWTBindingTools.bindEnabledState(this.jpaProjectIsNotNullFlagModel, group, jpaPlatformDropDown, facetsPageLink);
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
		return new SortedListValueModelAdapter<>(
				new SetCollectionValueModel<>(
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
				new PropertyCollectionValueModelAdapter<>(this.jpaPlatformConfigModel),
				this.buildEnabledJpaPlatformConfigsModel()
			};
	}

	/**
	 * Return only the JPA platform configs that support the project's
	 * JPA facet version.
	 */
	private CollectionValueModel<JpaPlatform.Config> buildEnabledJpaPlatformConfigsModel() {
		return new StaticCollectionValueModel<>(
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
		return WorkbenchTools.getAdapter(JpaWorkbench.class);
	}


	// ********** connection group **********

	private void buildConnectionGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_CONNECTION_LABEL);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		WorkbenchTools.setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);

		Combo connectionDropDown = this.buildDropDown(group, 3);
		SWTBindingTools.bindDropDownListBox(
				CONNECTION_CHOICES_MODEL,
				this.connectionModel,
				connectionDropDown,
				SIMPLE_STRING_TRANSFORMER
			);

		Link addConnectionLink = this.buildLink(group, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_CONNECTION_LINK);
		addConnectionLink.addSelectionListener(new AddConnectionLinkListener());  // the link will be GCed

		this.connectLink = this.buildLink(group, this.buildConnectLinkText());
		SWTBindingTools.bindEnabledState(this.disconnectedModel, this.connectLink);
		this.connectLink.addSelectionListener(new ConnectLinkListener());  // the link will be GCed

		// override default catalog
		Button overrideDefaultCatalogCheckBox = this.buildCheckBox(group, 3, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_OVERRIDE_DEFAULT_CATALOG_LABEL);
		SWTBindingTools.bind(this.userOverrideDefaultCatalogFlagModel, overrideDefaultCatalogCheckBox);

		Label defaultCatalogLabel = this.buildLabel(group, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_DEFAULT_CATALOG_LABEL);
		Combo defaultCatalogDropDown = this.buildDropDown(group);
		SWTBindingTools.bindDropDownListBox(this.catalogChoicesModel, this.defaultCatalogModel, defaultCatalogDropDown);

		SWTBindingTools.bindEnabledState(this.userOverrideDefaultCatalogFlagModel, defaultCatalogLabel, defaultCatalogDropDown);

		// override default schema
		Button overrideDefaultSchemaButton = this.buildCheckBox(group, 3, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_OVERRIDE_DEFAULT_SCHEMA_LABEL);
		SWTBindingTools.bind(this.userOverrideDefaultSchemaFlagModel, overrideDefaultSchemaButton);

		Label defaultSchemaLabel = this.buildLabel(group, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_DEFAULT_SCHEMA_LABEL);
		Combo defaultSchemaDropDown = this.buildDropDown(group);
		SWTBindingTools.bindDropDownListBox(this.schemaChoicesModel, this.defaultSchemaModel, defaultSchemaDropDown);

		SWTBindingTools.bindEnabledState(this.userOverrideDefaultSchemaFlagModel, defaultSchemaLabel, defaultSchemaDropDown);

		SWTBindingTools.bindEnabledState(this.jpaProjectIsNotNullFlagModel, group, connectionDropDown, addConnectionLink, overrideDefaultCatalogCheckBox, overrideDefaultSchemaButton);
	}

	private static final Transformer<String, String> SIMPLE_STRING_TRANSFORMER = TransformerTools.passThruTransformer(JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_NONE);

	private PropertyChangeListener buildDisconnectedModelListener() {
		return SWTListenerTools.wrap(new DisconnectedModelListener());
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
				JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_CONNECTED_TEXT :
				JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_CONNECT_LINK;
	}

	private void updateConnectLinkText(String text) {
		if (this.connectLink.isDisposed()) {
			return;
		}
		this.connectLink.setText(text);
		ControlTools.reflow(this.connectLink);
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
		group.setText(JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_PERSISTENT_CLASS_MANAGEMENT_LABEL);
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button discoverClassesRadioButton = this.buildRadioButton(group, 1, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_DISCOVER_CLASSES_BUTTON);
		SWTBindingTools.bind(this.discoverAnnotatedClassesModel, discoverClassesRadioButton);

		Button listClassesRadioButton = this.buildRadioButton(group, 1, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_LIST_CLASSES_BUTTON);
		SWTBindingTools.bind(this.listAnnotatedClassesModel, listClassesRadioButton);

		SWTBindingTools.bindEnabledState(this.jpaProjectIsNotNullFlagModel, group, discoverClassesRadioButton, listClassesRadioButton);
	}


	// ********** metamodel group **********

	private void buildMetamodelGroup(Composite composite) {
		Group group = new Group(composite, SWT.NONE);
		group.setText(JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_METAMODEL_LABEL);
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Link metamodelSourceFolderLink = this.buildLink(group, JptJpaUiMessages.JPA_FACET_WIZARD_PAGE_METAMODEL_SOURCE_FOLDER_LINK);
		metamodelSourceFolderLink.addSelectionListener(new MetamodelSourceFolderLinkListener());  // the link will be GCed
		Combo metamodelSourceFolderDropDown = this.buildDropDown(group);
		SWTBindingTools.bindDropDownListBox(
				this.javaSourceFolderChoicesModel,
				this.metamodelSourceFolderModel,
				metamodelSourceFolderDropDown,
				SIMPLE_STRING_TRANSFORMER
		);

		WorkbenchTools.setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_METAMODEL);

		SWTBindingTools.bindVisibleState(this.jpa2_0ProjectFlagModel, group, metamodelSourceFolderLink, metamodelSourceFolderDropDown);
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
		return this.jpaPlatformConfigModelBufferingFlag.getValue().booleanValue();
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
		return this.getProject().getAdapter(JpaProject.Reference.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected PropertyValueModel<Boolean>[] buildBufferingFlags() {
		return new PropertyValueModel[] {
				this.jpaPlatformConfigModelBufferingFlag,
				this.connectionModelBufferingFlag,
				this.userOverrideDefaultCatalogFlagModelBufferingFlag,
				this.userOverrideDefaultCatalogModelBufferingFlag,
				this.userOverrideDefaultSchemaFlagModelBufferingFlag,
				this.userOverrideDefaultSchemaModelBufferingFlag,
				this.discoverAnnotatedClassesModelBufferingFlag,
				this.metamodelSourceFolderModelBufferingFlag
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

        JpaPlatform.Config jpaPlatformConfig = this.jpaPlatformConfigModel.getValue();
        String jpaPlatformID = (jpaPlatformConfig == null) ? "" : jpaPlatformConfig.getId(); //$NON-NLS-1$

        Map<String, Object> enablementVariables = new HashMap<>();
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_ENABLEMENT_EXP, jpaPlatformID);
		enablementVariables.put(JpaLibraryProviderInstallOperationConfig.JPA_PLATFORM_DESCRIPTION_ENABLEMENT_EXP, this.jpaProjectModel.getValue().getJpaPlatform().getConfig());

		if ( ! libInstallDelegate.getLibraryProvider().isEnabledFor(
				this.getFacetedProject(), this.getProjectFacetVersion(), enablementVariables)) {
			this.addStatus(this.buildErrorStatus(JptJpaCoreMessages.VALIDATE_LIBRARY_PROVIDER_INVALID), statuses);
		}
	}


	// ********** UI model adapters **********

	/**
	 * The connections are held by a singleton, so the model can be a singleton
	 * also.
	 */
	// by default, ExtendedListValueModelWrapper puts a null at the top of the list
	private static final ListValueModel<String> CONNECTION_CHOICES_MODEL =
			new ExtendedListValueModelWrapper<>(
					new SortedListValueModelAdapter<>(
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
			this.fireCollectionChanged(CollectionValueModel.VALUES, CollectionTools.hashBag(this.iterator()));
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
				this.fireCollectionChanged(CollectionValueModel.VALUES, CollectionTools.hashBag(this.iterator()));
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
	 * property aspect adapter for DTP connection profile connection/database
	 */
	static class ConnectionProfilePropertyAspectAdapter<V>
		implements PluggablePropertyAspectAdapter.SubjectAdapter<V, ConnectionProfile>
	{
		private final Transformer<? super ConnectionProfile, ? extends V> transformer;
		private final PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener;
		private final ConnectionListener connectionListener;

		ConnectionProfilePropertyAspectAdapter(Transformer<? super ConnectionProfile, ? extends V> transformer, PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener) {
			super();
			if (transformer == null) {
				throw new NullPointerException();
			}
			this.transformer = transformer;

			if (listener == null) {
				throw new NullPointerException();
			}
			this.listener = listener;

			this.connectionListener = new LocalConnectionListener();
		}

		// the connection opening is probably the only thing that will happen...
		class LocalConnectionListener
			extends ConnectionAdapter
		{
			@Override
			public void opened(ConnectionProfile profile) {
				ConnectionProfilePropertyAspectAdapter.this.connectionOpened(profile);
			}
		}

		void connectionOpened(ConnectionProfile profile) {
			this.listener.valueChanged(this.transformer.transform(profile));
		}

		public V engageSubject(ConnectionProfile subject) {
			if (subject != null) {
				subject.addConnectionListener(this.connectionListener);
			}
			return this.transformer.transform(subject);
		}

		public V disengageSubject(ConnectionProfile subject) {
			if (subject != null) {
				subject.removeConnectionListener(this.connectionListener);
			}
			return this.transformer.transform(subject);
		}

		// ********** Factory **********

		static class Factory<V>
			implements PluggablePropertyAspectAdapter.SubjectAdapter.Factory<V, ConnectionProfile>
		{
			/* CU private */ final Transformer<? super ConnectionProfile, ? extends V> transformer;

			public Factory(Transformer<? super ConnectionProfile, ? extends V> transformer) {
				super();
				if (transformer == null) {
					throw new NullPointerException();
				}
				this.transformer = TransformerTools.nullCheck(transformer);
			}

			public PluggablePropertyAspectAdapter.SubjectAdapter<V, ConnectionProfile> buildAdapter(PluggablePropertyAspectAdapter.SubjectAdapter.Listener<V> listener) {
				return new ConnectionProfilePropertyAspectAdapter<>(this.transformer, listener);
			}

			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
		}
	}


	private static final Transformer<ConnectionProfile, String> CONNECTION_PROFILE_DATABASE_DEFAULT_CATALOG_TRANSFORMER = new ConnectionProfileDatabaseDefaultCatalogTransformer();

	static class ConnectionProfileDatabaseDefaultCatalogTransformer
		extends TransformerAdapter<ConnectionProfile, String>
	{
		@Override
		public String transform(ConnectionProfile cp) {
			Database db = cp.getDatabase();
			return (db == null) ? null : db.getDefaultCatalogIdentifier();
		}
	}


	/**
	 * The default schema is not derived purely from the database; it is also dependent
	 * on the current value of the default catalog (which may be overridden
	 * by the user).
	 */
	static class DatabaseDefaultSchemaModelAdapter
		implements PluggablePropertyValueModel.Adapter<String>
	{
		private final PropertyValueModel<ConnectionProfile> connectionProfileModel;
		private final PropertyChangeListener connectionProfileListener;
		/* class private */ volatile ConnectionProfile connectionProfile;

		private final PropertyValueModel<Boolean> connectionProfileConnectedModel;
		private final PropertyChangeListener connectionProfileConnectedListener;
		/* class private */ volatile Boolean connectionProfileConnected;

		private final PropertyValueModel<String> defaultCatalogModel;
		private final PropertyChangeListener defaultCatalogListener;
		/* class private */ volatile String defaultCatalog;

		private final PluggablePropertyValueModel.Adapter.Listener<String> listener;


		DatabaseDefaultSchemaModelAdapter(
				PropertyValueModel<ConnectionProfile> connectionProfileModel,
				PropertyValueModel<Boolean> connectionProfileConnectedModel,
				PropertyValueModel<String> defaultCatalogModel,
				PluggablePropertyValueModel.Adapter.Listener<String> listener
		) {
			super();
			if (connectionProfileModel == null) {
				throw new NullPointerException();
			}
			this.connectionProfileModel = connectionProfileModel;
			this.connectionProfileListener = new ConnectionProfileListener();

			if (connectionProfileConnectedModel == null) {
				throw new NullPointerException();
			}
			this.connectionProfileConnectedModel = connectionProfileConnectedModel;
			this.connectionProfileConnectedListener = new ConnectionProfileConnectedListener();

			if (defaultCatalogModel == null) {
				throw new NullPointerException();
			}
			this.defaultCatalogModel = defaultCatalogModel;
			this.defaultCatalogListener = new DefaultCatalogListener();

			if (listener == null) {
				throw new NullPointerException();
			}
			this.listener = listener;
		}

		/* class private */ class ConnectionProfileListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DatabaseDefaultSchemaModelAdapter.this.connectionProfile = (ConnectionProfile) event.getNewValue();
				DatabaseDefaultSchemaModelAdapter.this.update();
			}
		}

		/* class private */ class ConnectionProfileConnectedListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DatabaseDefaultSchemaModelAdapter.this.connectionProfileConnected = (Boolean) event.getNewValue();
				DatabaseDefaultSchemaModelAdapter.this.update();
			}
		}

		/* class private */ class DefaultCatalogListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DatabaseDefaultSchemaModelAdapter.this.defaultCatalog = (String) event.getNewValue();
				DatabaseDefaultSchemaModelAdapter.this.update();
			}
		}

		/* class private */ void update() {
			this.listener.valueChanged(this.buildValue());
		}

		private String buildValue() {
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
			// if we get here we know the database is not null
			return (this.defaultCatalog == null) ? null : this.getDatabase().getCatalogForIdentifier(this.defaultCatalog);
		}

		private Database getDatabase() {
			return (this.connectionProfile == null) ? null : this.connectionProfile.getDatabase();
		}

		public String engageModel() {
			this.connectionProfileModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.connectionProfileListener);
			this.connectionProfile = this.connectionProfileModel.getValue();
			this.connectionProfileConnectedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.connectionProfileConnectedListener);
			this.connectionProfileConnected = this.connectionProfileConnectedModel.getValue();
			this.defaultCatalogModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.defaultCatalogListener);
			this.defaultCatalog = this.defaultCatalogModel.getValue();
			return this.buildValue();
		}

		public String disengageModel() {
			this.connectionProfileModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.connectionProfileListener);
			this.connectionProfile = null;
			this.connectionProfileConnectedModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.connectionProfileConnectedListener);
			this.connectionProfileConnected = null;
			this.defaultCatalogModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.defaultCatalogListener);
			this.defaultCatalog = null;
			return null;
		}

		// ********** Factory **********

		static class Factory
			implements PluggablePropertyValueModel.Adapter.Factory<String>
		{
			private final PropertyValueModel<ConnectionProfile> connectionProfileModel;

			private final PropertyValueModel<Boolean> connectionProfileConnectedModel;

			private final PropertyValueModel<String> defaultCatalogModel;

			public Factory(
					PropertyValueModel<ConnectionProfile> connectionProfileModel,
					PropertyValueModel<Boolean> connectionProfileConnectedModel,
					PropertyValueModel<String> defaultCatalogModel
			) {
				super();
				if (connectionProfileModel == null) {
					throw new NullPointerException();
				}
				this.connectionProfileModel = connectionProfileModel;

				if (connectionProfileConnectedModel == null) {
					throw new NullPointerException();
				}
				this.connectionProfileConnectedModel = connectionProfileConnectedModel;

				if (defaultCatalogModel == null) {
					throw new NullPointerException();
				}
				this.defaultCatalogModel = defaultCatalogModel;
			}

			public DatabaseDefaultSchemaModelAdapter buildAdapter(PluggablePropertyValueModel.Adapter.Listener<String> listener) {
				return new DatabaseDefaultSchemaModelAdapter(this.connectionProfileModel, this.connectionProfileConnectedModel, this.defaultCatalogModel, listener);
			}

			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
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
	static class DefaultDatabaseComponentModelAdapter
		implements PluggableModifiablePropertyValueModel.Adapter<String>
	{
		private final PropertyValueModel<Boolean> userOverrideDefaultFlagModel;
		private final PropertyChangeListener userOverrideDefaultFlagListener = new UserOverrideDefaultFlagListener();
		/* CU private */ volatile boolean userOverrideDefaultFlag = false;

		private final ModifiablePropertyValueModel<String> userOverrideDefaultModel;
		private final PropertyChangeListener userOverrideDefaultListener = new UserOverrideDefaultListener();
		/* CU private */ volatile String userOverrideDefault = null;

		private final PropertyValueModel<String> databaseDefaultModel;
		private final PropertyChangeListener databaseDefaultListener = new DatabaseDefaultListener();
		/* CU private */ volatile String databaseDefault = null;

		private final PluggablePropertyValueModel.Adapter.Listener<String> listener;


		public DefaultDatabaseComponentModelAdapter(Factory factory, PluggablePropertyValueModel.Adapter.Listener<String> listener) {
			super();
			if (factory == null) {
				throw new NullPointerException();
			}
			this.userOverrideDefaultFlagModel = factory.userOverrideDefaultFlagModel;
			this.userOverrideDefaultModel = factory.userOverrideDefaultModel;
			this.databaseDefaultModel = factory.databaseDefaultModel;
			if (listener == null) {
				throw new NullPointerException();
			}
			this.listener = listener;
		}

		/**
		 * This will be called when the user makes a selection from the
		 * drop-down; which is possible only when the checkbox is checked
		 * (and the drop-down is enabled).
		 */
		public void setValue(String value) {
			this.userOverrideDefaultModel.setValue(value);
		}

		public String engageModel() {
			this.userOverrideDefaultFlagModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.userOverrideDefaultFlagListener);
			Boolean flag = this.userOverrideDefaultFlagModel.getValue();
			this.userOverrideDefaultFlag = (flag != null) && flag.booleanValue();
			this.userOverrideDefaultModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.userOverrideDefaultListener);
			this.userOverrideDefault = this.userOverrideDefaultModel.getValue();
			this.databaseDefaultModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.databaseDefaultListener);
			this.databaseDefault = this.databaseDefaultModel.getValue();
			return this.buildValue();
		}

		public String disengageModel() {
			this.databaseDefaultModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.databaseDefaultListener);
			this.databaseDefault = null;
			this.userOverrideDefaultModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.userOverrideDefaultListener);
			this.userOverrideDefault = null;
			this.userOverrideDefaultFlagModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.userOverrideDefaultFlagListener);
			this.userOverrideDefaultFlag = false;
			return null;
		}

		/* CU private */ void update() {
			this.listener.valueChanged(this.buildValue());
		}

		/**
		 * If the checkbox is checked, return the user override from the JPA project;
		 * otherwise return the default from the database
		 */
		private String buildValue() {
			return this.userOverrideDefaultFlag ? this.userOverrideDefault : this.databaseDefault;
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.buildValue());
		}


		// ********** user override default flag listener **********
		
		/* CU private */ class UserOverrideDefaultFlagListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DefaultDatabaseComponentModelAdapter.this.userOverrideDefaultFlagChanged(event);
			}
		}

		/* CU private */ void userOverrideDefaultFlagChanged(PropertyChangeEvent event) {
			Boolean newValue = (Boolean) event.getNewValue();
			this.userOverrideDefaultFlag = (newValue != null) && newValue.booleanValue();
			this.update();

			// If the checkbox has been unchecked, we need to clear out the JPA project's user override.
			if ( ! this.userOverrideDefaultFlag) {
				this.userOverrideDefaultModel.setValue(null);
			}
		}


		// ********** user override default listener **********

		/* CU private */ class UserOverrideDefaultListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DefaultDatabaseComponentModelAdapter.this.userOverrideDefault = (String) event.getNewValue();
				DefaultDatabaseComponentModelAdapter.this.update();
			}
		}


		// ********** database default listener **********

		/* CU private */ class DatabaseDefaultListener
			extends PropertyChangeAdapter
		{
			@Override
			public void propertyChanged(PropertyChangeEvent event) {
				DefaultDatabaseComponentModelAdapter.this.databaseDefault = (String) event.getNewValue();
				DefaultDatabaseComponentModelAdapter.this.update();
			}
		}


		// ********** Factory **********

		public static class Factory
			implements PluggableModifiablePropertyValueModel.Adapter.Factory<String>
		{
			/* CU private */ final PropertyValueModel<Boolean> userOverrideDefaultFlagModel;
			/* CU private */ final ModifiablePropertyValueModel<String> userOverrideDefaultModel;
			/* CU private */ final PropertyValueModel<String> databaseDefaultModel;

			public Factory(
					PropertyValueModel<Boolean> userOverrideDefaultFlagModel,
					ModifiablePropertyValueModel<String> userOverrideDefaultModel,
					PropertyValueModel<String> databaseDefaultModel
			) {
				super();
				this.userOverrideDefaultFlagModel = userOverrideDefaultFlagModel;
				this.userOverrideDefaultModel = userOverrideDefaultModel;
				this.databaseDefaultModel = databaseDefaultModel;
			}

			public DefaultDatabaseComponentModelAdapter buildAdapter(PluggablePropertyValueModel.Adapter.Listener<String> listener) {
				return new DefaultDatabaseComponentModelAdapter(this, listener);
			}

			@Override
			public String toString() {
				return ObjectTools.toString(this);
			}
		}
	}
}
