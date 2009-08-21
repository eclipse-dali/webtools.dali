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
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.eclipse.jpt.db.ConnectionProfileListener;
import org.eclipse.jpt.db.Database;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.db.ui.internal.DTPUiTools;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.swt.BooleanButtonModelAdapter;
import org.eclipse.jpt.ui.internal.swt.ComboModelAdapter;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.value.AspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ExtendedListValueModelWrapper;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleListValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel.Trigger;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.ChangeListener;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jst.common.project.facet.core.libprov.LibraryInstallDelegate;
import org.eclipse.jst.common.project.facet.ui.libprov.LibraryFacetPropertyPage;
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
import org.eclipse.wst.common.project.facet.core.IFacetedProject;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.ProjectFacetsManager;

public class JpaProjectPropertiesPage
	extends LibraryFacetPropertyPage 
{
	public static final String PROP_ID = "org.eclipse.jpt.ui.jpaProjectPropertiesPage"; //$NON-NLS-1$

	private WritablePropertyValueModel<IProject> projectHolder;
	
	private PropertyValueModel<JpaProject> jpaProjectHolder;
	
	private Trigger trigger;
	
	private PropertyChangeListener validationListener;
	
	private PropertyChangeListener overrideDefaultCatalogListener;
	
	private PropertyChangeListener overrideDefaultSchemaListener;

	private PropertyChangeListener platformChangelistener;
	
	private BufferedWritablePropertyValueModel<String> platformIdModel;
	
	private BufferedWritablePropertyValueModel<String> connectionModel;
	
	private ListValueModel<String> connectionChoicesModel;
	
	private PropertyValueModel<ConnectionProfile> connectionProfileModel;
	
	private BufferedWritablePropertyValueModel<Boolean> overrideDefaultCatalogModel;
	
	private BufferedWritablePropertyValueModel<String> defaultCatalogModel;
	
	private WritablePropertyValueModel<String> combinedDefaultCatalogModel;
	
	private ListValueModel<String> catalogChoicesModel;

	private BufferedWritablePropertyValueModel<Boolean> overrideDefaultSchemaModel;
	
	private BufferedWritablePropertyValueModel<String> defaultSchemaModel;
	
	private WritablePropertyValueModel<String> combinedDefaultSchemaModel;
	
	private ListValueModel<String> schemaChoicesModel;
	
	private BufferedWritablePropertyValueModel<Boolean> discoverAnnotatedClassesModel;
	
	private WritablePropertyValueModel<Boolean> listAnnotatedClassesModel;


	// ************ construction/initialization ************

	public JpaProjectPropertiesPage() {
		super();
		initialize();
	}

	protected void initialize() {
		this.projectHolder = new SimplePropertyValueModel<IProject>();
		this.jpaProjectHolder = initializeJpaProjectHolder();
		this.trigger = new Trigger();
		this.validationListener = this.initializeValidationListener();
		this.overrideDefaultCatalogListener = this.initializeOverrideDefaultCatalogChangeListener();
		this.overrideDefaultSchemaListener = this.initializeOverrideDefaultSchemaChangeListener();
		
		this.platformChangelistener = this.initializePlatformChangeListener();
		this.platformIdModel = this.initializePlatformIdModel();
		
		this.connectionModel = this.initializeConnectionModel();
		this.connectionChoicesModel = this.initializeConnectionChoicesModel();
		this.connectionProfileModel = this.initializeConnectionProfileModel();
		
		this.overrideDefaultCatalogModel = this.initializeOverrideDefaultCatalogModel();
		this.defaultCatalogModel = this.initializeDefaultCatalogModel();
		this.combinedDefaultCatalogModel = this.initializeCombinedDefaultCatalogModel();
		this.catalogChoicesModel = this.initializeCatalogChoicesModel();
		
		this.overrideDefaultSchemaModel = this.initializeOverrideDefaultSchemaModel();
		this.defaultSchemaModel = this.initializeDefaultSchemaModel();
		this.combinedDefaultSchemaModel = this.initializeCombinedDefaultSchemaModel();
		this.schemaChoicesModel = this.initializeSchemaChoicesModel();
		
		this.discoverAnnotatedClassesModel = this.initializeDiscoverAnnotatedClassesModel();
		this.listAnnotatedClassesModel = this.initializeListAnnotatedClassesModel();
	}
	
	protected PropertyValueModel<JpaProject> initializeJpaProjectHolder() {
		return new JpaProjectHolder(this.projectHolder);
	}
	
	protected PropertyChangeListener initializeValidationListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				if (! JpaProjectPropertiesPage.this.getControl().isDisposed()) {
					updateValidation();
				}
			}
		};
	}
	
	protected PropertyChangeListener initializeOverrideDefaultCatalogChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				Boolean enabled = (Boolean) event.getNewValue();
				if( ! enabled) {
					defaultCatalogModel.setValue(null);
				}
			}
		};
	}
	
	protected PropertyChangeListener initializeOverrideDefaultSchemaChangeListener() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				Boolean enabled = (Boolean) event.getNewValue();
				if( ! enabled) {
					defaultSchemaModel.setValue(null);
				}
			}
		};
	}
	
	protected PropertyChangeListener initializePlatformChangeListener(){
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				if (! JpaProjectPropertiesPage.this.getControl().isDisposed()) {
					JpaProjectPropertiesPage.this.getLibraryInstallDelegate().
						setEnablementContextVariable(
							JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM,
							event.getNewValue());
				}
			}
		};
	}
	
	protected BufferedWritablePropertyValueModel<String> initializePlatformIdModel() {
		BufferedWritablePropertyValueModel<String> model =
			new BufferedWritablePropertyValueModel(
				new PlatformIdModel(this.jpaProjectHolder), this.trigger);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.platformChangelistener);
		return model;
	}
	
	protected BufferedWritablePropertyValueModel<String> initializeConnectionModel() {
		BufferedWritablePropertyValueModel<String> model = 
			new BufferedWritablePropertyValueModel(
				new ConnectionModel(this.jpaProjectHolder), this.trigger);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		return model;
	}
	
	protected ListValueModel<String> initializeConnectionChoicesModel() {
		return new ExtendedListValueModelWrapper(
				(String) null,
				new SortedListValueModelAdapter(	
					new CollectionListValueModelAdapter(
						new ConnectionChoicesModel(this.projectHolder))));
	}
	
	protected PropertyValueModel<ConnectionProfile> initializeConnectionProfileModel() {
		return new CachingTransformationPropertyValueModel<String, ConnectionProfile>(this.connectionModel) {
			@Override
			protected ConnectionProfile transform_(String value) {
				return JptDbPlugin.instance().getConnectionProfileFactory().buildConnectionProfile(value);
			}
		};
	}
	
	protected BufferedWritablePropertyValueModel<Boolean> initializeOverrideDefaultCatalogModel() {
		OverrideDefaultCatalogModel model = new OverrideDefaultCatalogModel(this.jpaProjectHolder);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.overrideDefaultCatalogListener);

		BufferedWritablePropertyValueModel<Boolean> bufferedModel = new BufferedWritablePropertyValueModel<Boolean>(model, this.trigger);
		bufferedModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);

		return bufferedModel;
	}
	
	protected BufferedWritablePropertyValueModel<String> initializeDefaultCatalogModel() {
		return new BufferedWritablePropertyValueModel(
			new DefaultCatalogModel(this.jpaProjectHolder),
			this.trigger);
	}
	
	protected WritablePropertyValueModel<String> initializeCombinedDefaultCatalogModel() {
		WritablePropertyValueModel<String> model = 
			new CombinedDefaultCatalogModel(
				this.defaultCatalogModel,
				this.overrideDefaultCatalogModel,
				new DefaultDefaultCatalogModel(this.connectionProfileModel));
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		return model;
	}
	
	protected ListValueModel<String> initializeCatalogChoicesModel() {
		Collection<CollectionValueModel> cvms = new ArrayList<CollectionValueModel>();
		cvms.add(new PropertyCollectionValueModelAdapter(this.defaultCatalogModel));
		cvms.add(new CatalogChoicesModel(this.connectionProfileModel));
		return new SortedListValueModelAdapter(
			new CompositeCollectionValueModel<CollectionValueModel, String>(cvms) {
				@Override
				public Iterator<String> iterator() {
					Set<String> uniqueValues = new HashSet<String>();
					for (String each : CollectionTools.iterable(super.iterator())) {
						if (each != null) {
							uniqueValues.add(each);
						}
					}
					return uniqueValues.iterator();
				}
			});
	}

	protected BufferedWritablePropertyValueModel<Boolean> initializeOverrideDefaultSchemaModel() {
		OverrideDefaultSchemaModel model = new OverrideDefaultSchemaModel(this.jpaProjectHolder);
		BufferedWritablePropertyValueModel<Boolean> modelBuffer = 
			new BufferedWritablePropertyValueModel(model, this.trigger);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.overrideDefaultSchemaListener);
		modelBuffer.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		return modelBuffer;
	
	}
	
	protected BufferedWritablePropertyValueModel<String> initializeDefaultSchemaModel() {
		return new BufferedWritablePropertyValueModel(
			new DefaultSchemaModel(this.jpaProjectHolder),
			this.trigger);
	}
	
	protected WritablePropertyValueModel<String> initializeCombinedDefaultSchemaModel() {
		WritablePropertyValueModel<String> model = 
			new CombinedDefaultSchemaModel(
				this.defaultSchemaModel,
				this.overrideDefaultSchemaModel,
				new DefaultDefaultSchemaModel(this.defaultCatalogModel, this.connectionProfileModel));
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		return model;
	}
	
	protected ListValueModel<String> initializeSchemaChoicesModel() {
		Collection<CollectionValueModel> cvms = new ArrayList<CollectionValueModel>();
		cvms.add(new PropertyCollectionValueModelAdapter(this.defaultSchemaModel));
		cvms.add(new SchemaChoicesModel(this.defaultCatalogModel, this.connectionProfileModel));
		return new SortedListValueModelAdapter(
			new CompositeCollectionValueModel<CollectionValueModel, String>(cvms) {
				@Override
				public Iterator<String> iterator() {
					Set<String> uniqueValues = new HashSet<String>();
					for (String each : CollectionTools.iterable(super.iterator())) {
						if (each != null) {
							uniqueValues.add(each);
						}
					}
					return uniqueValues.iterator();
				}
			});
	}
	
	protected BufferedWritablePropertyValueModel<Boolean> initializeDiscoverAnnotatedClassesModel() {
		BufferedWritablePropertyValueModel<Boolean> model = 
			new BufferedWritablePropertyValueModel(
				new DiscoverAnnotatedClassesModel(this.jpaProjectHolder), this.trigger);
		model.addPropertyChangeListener(PropertyValueModel.VALUE, this.validationListener);
		return model;
	}
	
	protected WritablePropertyValueModel<Boolean> initializeListAnnotatedClassesModel() {
		return new TransformationWritablePropertyValueModel<Boolean, Boolean>(
				this.discoverAnnotatedClassesModel) {
			@Override
			protected Boolean transform_(Boolean value) {
				return ! value;
			}
			
			@Override
			protected Boolean reverseTransform_(Boolean value) {
				return ! value;
			}
		};
	}

	// ************ queries ************
	
	protected JpaProject getJpaProject() {
		return this.jpaProjectHolder.getValue();
	}
	
	protected String getJpaPlatformId() {
		return this.platformIdModel.getValue();
	}
	
	protected String getConnectionName() {
		return this.connectionModel.getValue();
	}
	
	protected ConnectionProfile getConnectionProfile() {
		// we just use the connection profile to log in, so go to the db plug-in
		return JptDbPlugin.instance().getConnectionProfileFactory().
			buildConnectionProfile(getConnectionName());
	}
	
	protected Boolean getOverrideDefaultCatalog() {
		return this.overrideDefaultCatalogModel.getValue();
	}
	
	protected List<String> getDefaultCatalogChoices() {
		return CollectionTools.list(this.catalogChoicesModel.iterator());
	}
	
	protected String getDefaultCatalog() {
		return this.defaultCatalogModel.getValue();
	}
	
	
	protected Boolean getOverrideDefaultSchema() {
		return this.overrideDefaultSchemaModel.getValue();
	}
	
	protected List<String> getDefaultSchemaChoices() {
		return CollectionTools.list(this.schemaChoicesModel.iterator());
	}
	
	protected String getDefaultSchema() {
		return this.defaultSchemaModel.getValue();
	}
	
	@Override
	public IProjectFacetVersion getProjectFacetVersion() {
		final IProjectFacet jsfFacet = ProjectFacetsManager.getProjectFacet( "jpt.jpa" );
		final IFacetedProject fproj = getFacetedProject();
		return fproj.getInstalledVersion( jsfFacet );
	}
	
	@Override
	protected LibraryInstallDelegate createLibraryInstallDelegate(
			IFacetedProject project, IProjectFacetVersion fv) {
		Map<String, Object> enablementVariables = new HashMap<String, Object>();
		enablementVariables.put(
			JpaLibraryProviderConstants.EXPR_VAR_JPA_PLATFORM, "");	
		return new LibraryInstallDelegate(project, fv, enablementVariables);
	}
	
	@Override
	protected Control createPageContents(Composite parent) {
		this.projectHolder.setValue(getProject());
		
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		composite.setLayout(layout);
		
		new PlatformGroup(composite);
		
		Control libraryProviderComposite = super.createPageContents(composite);
		libraryProviderComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		new ConnectionGroup(composite);
		new PersistentClassManagementGroup(composite);

		Dialog.applyDialogFont(composite);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE);
		
		updateValidation();
		
		return composite;
	}
	
	Button createButton(Composite container, int span, String text, int style) {
		Button button = new Button(container, SWT.NONE | style);
		button.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		button.setLayoutData(gd);
		return button;
	}
	
	Combo createCombo(Composite container, int span, boolean fillHorizontal) {
		Combo combo = new Combo(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
		GridData gd;
		if (fillHorizontal) {
			gd = new GridData(GridData.FILL_HORIZONTAL);
		}
		else {
			gd = new GridData();
		}
		gd.horizontalSpan = span;
		combo.setLayoutData(gd);
		return combo;
	}
	
	@Override
	public boolean performOk() {
		super.performOk();
		
		final IWorkspaceRunnable wr = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor)
					throws CoreException {
				performOkInternal(monitor);
			}
		};
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) 
					throws InvocationTargetException, InterruptedException {
				try {
					IWorkspace ws = ResourcesPlugin.getWorkspace();
					ws.run(wr, ws.getRoot(), IWorkspace.AVOID_UPDATE, monitor);
				}
				catch(CoreException e) {
					throw new InvocationTargetException(e);
				}
			}
		};
		
		try {
			new ProgressMonitorDialog(getShell()).run(true, false, op);
		}
		catch (InterruptedException e) {
			return false;
		} 
		catch (InvocationTargetException e) {
			final Throwable te = e.getTargetException();
			throw new RuntimeException(te);
		}
		
		return true;
	}
	
	private void performOkInternal(IProgressMonitor monitor) 
			throws CoreException {
		if (this.platformIdModel.isBuffering()) {
			this.trigger.accept();
			JpaModelManager.instance().rebuildJpaProject(getProject());
			getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
		else if (this.connectionModel.isBuffering()
				|| this.overrideDefaultCatalogModel.isBuffering()
				|| this.defaultCatalogModel.isBuffering()
				|| this.overrideDefaultSchemaModel.isBuffering()
				|| this.defaultSchemaModel.isBuffering()
				|| this.discoverAnnotatedClassesModel.isBuffering()) {
			this.trigger.accept();
			getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}
	
	@Override
	protected void performDefaults() {
		super.performDefaults();
		this.trigger.reset();
	}
	
	@Override
	protected IStatus performValidation() {
		Map<Integer, List<IStatus>> statuses = new HashMap<Integer, List<IStatus>>();
		statuses.put(IStatus.ERROR, new ArrayList<IStatus>());
		statuses.put(IStatus.WARNING, new ArrayList<IStatus>());
		statuses.put(IStatus.INFO, new ArrayList<IStatus>());
		statuses.put(IStatus.OK, CollectionTools.list(Status.OK_STATUS));
		
		/* validate platform */
		// user is unable to unset the platform, so no validation necessary
		
		/* library provider */
		IStatus lpStatus = super.performValidation();
		statuses.get(lpStatus.getSeverity()).add(lpStatus);
		
		/* validate connection */
		String connectionName = getConnectionName();
		ConnectionProfile connectionProfile = getConnectionProfile();
		if (! StringTools.stringIsEmpty(connectionName)) {
			if (connectionProfile == null) {
				statuses.get(IStatus.ERROR).add( 
					buildErrorStatus(
						NLS.bind(
							JptCoreMessages.VALIDATE_CONNECTION_INVALID, 
							connectionName)));
			}
			else if (! connectionProfile.isActive()) {
				statuses.get(IStatus.INFO).add( 
					buildInfoStatus(
						JptCoreMessages.VALIDATE_CONNECTION_NOT_CONNECTED));
			}
		}
		
		/* default catalog */
		if (getOverrideDefaultCatalog()) {
			String defaultCatalog = getDefaultCatalog();
			if (StringTools.stringIsEmpty(defaultCatalog)) {
				statuses.get(IStatus.ERROR).add( 
					buildErrorStatus(
						JptCoreMessages.VALIDATE_DEFAULT_CATALOG_NOT_SPECIFIED));
			}
			else if (connectionProfile != null
				&& connectionProfile.isConnected()
				&& ! getDefaultCatalogChoices().contains(defaultCatalog)) {
				statuses.get(IStatus.WARNING).add(
					buildWarningStatus(
						NLS.bind(
							JptCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_CATALOG,
							defaultCatalog)));
			}
		}
		
		/* default schema */
		if (getOverrideDefaultSchema()) {
			String defaultSchema = getDefaultSchema();
			if (StringTools.stringIsEmpty(defaultSchema)) {
				statuses.get(IStatus.ERROR).add( 
					buildErrorStatus(
						JptCoreMessages.VALIDATE_DEFAULT_SCHEMA_NOT_SPECIFIED));
			}
			else if (connectionProfile != null
					&& connectionProfile.isConnected()
					&& ! getDefaultSchemaChoices().contains(defaultSchema)) {
				statuses.get(IStatus.WARNING).add(
					buildWarningStatus(
						NLS.bind(
							JptCoreMessages.VALIDATE_CONNECTION_DOESNT_CONTAIN_SCHEMA,
							defaultSchema)));
			}
		}
		
		if (! statuses.get(IStatus.ERROR).isEmpty()) {
			return statuses.get(IStatus.ERROR).get(0);
		}
		else if (! statuses.get(IStatus.WARNING).isEmpty()) {
			return statuses.get(IStatus.WARNING).get(0);
		}
		else if (! statuses.get(IStatus.INFO).isEmpty()) {
			return statuses.get(IStatus.INFO).get(0);
		}
		else {
			return statuses.get(IStatus.OK).get(0);
		}
	}
	
	private IStatus buildInfoStatus(String message) {
		return buildStatus(IStatus.INFO, message);
	}
	
	private IStatus buildWarningStatus(String message) {
		return buildStatus(IStatus.WARNING, message);
	}
	
	private IStatus buildErrorStatus(String message) {
		return buildStatus(IStatus.ERROR, message);
	}
	
	private IStatus buildStatus(int severity, String message) {
		return new Status(severity, JptCorePlugin.PLUGIN_ID, message);
	}
	
	@Override
	public void dispose() {
		super.dispose();
		this.platformIdModel.removePropertyChangeListener(PropertyValueModel.VALUE, platformChangelistener);
	}
	
	
	private static abstract class BasePropertyAspectAdapter<S, V>
		extends AspectAdapter<S>
		implements WritablePropertyValueModel<V>
	{
		/**
		 * Cache the current value so we can pass an old value when we fire a 
		 * property change event
		 */
		protected V value;
		
		
		protected BasePropertyAspectAdapter(PropertyValueModel<S> subjectHolder) {
			super(subjectHolder);
			this.value = null;
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public V getValue() {
			return this.value;
		}
		
		public final void setValue(V value) {
			if (this.subject != null) {
				setValue_(value);
			}
		}
		
		protected void setValue_(V value) {
			throw new UnsupportedOperationException("setValue");
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected Class<? extends ChangeListener> getListenerClass() {
			return PropertyChangeListener.class;
		}
		
		@Override
		protected String getListenerAspectName() {
			return VALUE;
		}
		
		@Override
		protected boolean hasListeners() {
			return hasAnyPropertyChangeListeners(VALUE);
		}
		
		@Override
		protected void fireAspectChange(Object oldValue, Object newValue) {
			firePropertyChanged(VALUE, oldValue, newValue);
		}
		
		@Override
		protected void engageSubject_() {
			this.value = buildValue();
		}
		
		@Override
		protected void disengageSubject_() {
			this.value = null;
		}
		
		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.getValue());
		}
		
		
		// ************ internal ***********************************************
		
		protected V buildValue() {
			if (this.subject != null) {
				return buildValue_();
			}
			return null;
		}
		
		protected abstract V buildValue_();
		
		protected void valueChanged() {
			V oldValue = this.getValue();
			this.value = buildValue();
			this.fireAspectChange(oldValue, this.getValue());
		}
	}
	
	
	private abstract static class BaseCollectionAspectAdapter<S, E>
		extends AspectAdapter<S>
		implements CollectionValueModel<E>
	{
		protected BaseCollectionAspectAdapter(PropertyValueModel<? extends S> subjectHolder) {
			super(subjectHolder);
		}
		
			
		// ************ ListValueModel impl ************************************
		
		public Iterator<E> iterator() {
			return this.subject == null ? EmptyIterator.<E>instance() : this.iterator_();
		}
		
		public int size() {
			return this.subject == null ? 0 : this.size_();
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected Object getValue() {
			return this.iterator();
		}
	
		@Override
		protected Class<? extends ChangeListener> getListenerClass() {
			return CollectionChangeListener.class;
		}
	
		@Override
		protected String getListenerAspectName() {
			return VALUES;
		}
	
		@Override
		protected boolean hasListeners() {
			return this.hasAnyCollectionChangeListeners(VALUES);
		}
		
		@Override
		protected void fireAspectChange(Object oldValue, Object newValue) {
			this.fireCollectionChanged(VALUES);
		}
		
		
		// ************ internal ***********************************************
		
		protected Iterator<E> iterator_() {
			throw new UnsupportedOperationException();
		}
		
		protected int size_() {
			return CollectionTools.size(this.iterator());
		}
		
		protected void collectionChanged() {
			this.fireCollectionChanged(VALUES);
		}
	}
	
	
	private static class JpaProjectHolder
		extends BasePropertyAspectAdapter<IProject, JpaProject>
	{
		/**
		 * Listens to the preferences to determine if the project's platform
		 * has changed, and is therefore a new JPA project
		 */
		private IPreferenceChangeListener platformChangeListener;
		
		
		private JpaProjectHolder(PropertyValueModel<IProject> projectHolder) {
			super(projectHolder);
			this.platformChangeListener = buildPlatformChangeListener();
		}
		
		
		// ************ initialization *****************************************
		
		private IPreferenceChangeListener buildPlatformChangeListener() {
			return new IPreferenceChangeListener() {
				public void preferenceChange(PreferenceChangeEvent event) {
					if (JptCorePlugin.JPA_PLATFORM.equals(event.getKey())) {
						JpaProjectHolder.this.valueChanged();
					}
				}
			};
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected void engageSubject_() {
			IEclipsePreferences preferences = JptCorePlugin.getProjectPreferences(this.subject);
			preferences.addPreferenceChangeListener(this.platformChangeListener);
			super.engageSubject_();
		}
		
		@Override
		protected void disengageSubject_() {
			IEclipsePreferences preferences = JptCorePlugin.getProjectPreferences(this.subject);
			preferences.removePreferenceChangeListener(this.platformChangeListener);
			super.disengageSubject_();
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected JpaProject buildValue_() {
			return JptCorePlugin.getJpaProject(this.subject);
		}
	}
	
	
	private static class PlatformIdModel
		extends BasePropertyAspectAdapter<JpaProject, String>
	{
		private PlatformIdModel(PropertyValueModel<JpaProject> jpaProjectHolder) {
			super(jpaProjectHolder);
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public void setValue_(String newPlatformId) {
			JptCorePlugin.setJpaPlatformId(this.subject.getProject(), newPlatformId);
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected String buildValue_() {
			return JptCorePlugin.getJpaPlatformId(this.subject.getProject());
		}
	}
	
	
	private final class PlatformGroup
	{
		private PlatformGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_platformLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Combo platformCombo = createCombo(group, 1, true);
			
			ComboModelAdapter.adapt(
				buildPlatformChoicesListValueModel(),
				JpaProjectPropertiesPage.this.platformIdModel,
				platformCombo,
				buildPlatformLabelConverter());
		}
		
		
		private ListValueModel<String> buildPlatformChoicesListValueModel() {
			return new SortedListValueModelAdapter<String>(
				new SimpleListValueModel<String>(
					CollectionTools.list(JpaPlatformRegistry.instance().jpaPlatformIds())),
				new Comparator<String>() {
					public int compare(String o1, String o2) {
						return JpaPlatformRegistry.instance().getJpaPlatformLabel(o1).
							compareTo(JpaPlatformRegistry.instance().getJpaPlatformLabel(o2));
					}
				});
		}
		
		private StringConverter<String> buildPlatformLabelConverter() {
			return new StringConverter<String>() {
				public String convertToString(String platformId) {
					return JpaPlatformRegistry.instance().getJpaPlatformLabel(platformId);
				}
			};
		}
	}
	
	
	private static class ConnectionModel
		extends BasePropertyAspectAdapter<JpaProject, String>
	{
		private PropertyChangeListener connectionChangeListener;
		
		
		private ConnectionModel(PropertyValueModel<JpaProject> jpaProjectHolder) {
			super(jpaProjectHolder);
			this.connectionChangeListener = buildConnectionChangeListener();
		}
		
		
		// ************ initialization *****************************************
		
		private PropertyChangeListener buildConnectionChangeListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					ConnectionModel.this.valueChanged();
				}
			};
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public void setValue_(String newConnection) {
			this.subject.getDataSource().setConnectionProfileName(newConnection);
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected void engageSubject_() {
			JpaDataSource dataSource = this.subject.getDataSource();
			dataSource.addPropertyChangeListener(JpaDataSource.CONNECTION_PROFILE_NAME_PROPERTY, this.connectionChangeListener);
			super.engageSubject_();
		}
		
		@Override
		protected void disengageSubject_() {
			JpaDataSource dataSource = this.subject.getDataSource();
			dataSource.removePropertyChangeListener(JpaDataSource.CONNECTION_PROFILE_NAME_PROPERTY, this.connectionChangeListener);
			super.disengageSubject_();
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected String buildValue_() {
			return this.subject.getDataSource().getConnectionProfileName();
		}
	}
	
	
	private static class ConnectionChoicesModel
		extends BaseCollectionAspectAdapter<IProject, String>
	{
		private ConnectionProfileListener connectionProfileListener;
		
		
		private ConnectionChoicesModel(PropertyValueModel<IProject> subjectHolder) {
			super(subjectHolder);
			this.connectionProfileListener = buildConnectionProfileListener();
		}
		
		
		// ************ initialization *****************************************
		
		private ConnectionProfileListener buildConnectionProfileListener() {
			return new ConnectionProfileListener() {
				public void connectionProfileAdded(String name) {
					collectionChanged();
				}
				
				public void connectionProfileRemoved(String name) {
					collectionChanged();
				}
				
				public void connectionProfileRenamed(String oldName, String newName) {
					// ignore this event for now.  connecting a profile actually
					// throws a connection renamed event, which messes up the 
					// list selection.  there shouldn't be a connection renamed
					// within the scope of this dialog anyhow.
					// collectionChanged();
				}
			};
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected void engageSubject_() {
			JptDbPlugin.instance().getConnectionProfileFactory().
				addConnectionProfileListener(this.connectionProfileListener);
		}
		
		@Override
		protected void disengageSubject_() {
			JptDbPlugin.instance().getConnectionProfileFactory().
				removeConnectionProfileListener(this.connectionProfileListener);
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected Iterator<String> iterator_() {
			return JptDbPlugin.instance().getConnectionProfileFactory().connectionProfileNames();
		}
	}
	

	private abstract static class OverrideDefaultModel
		extends BasePropertyAspectAdapter<JpaProject, Boolean>
	{
		// the superclass "value" is the *cached* value
		private Boolean actualValue;


		OverrideDefaultModel(PropertyValueModel<JpaProject> jpaProjectHolder) {
			super(jpaProjectHolder);
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public Boolean getValue() {
			Boolean v = super.getValue();
			return (v == null) ? Boolean.FALSE : v;
		}
		
		@Override
		public void setValue_(Boolean newValue) {
			this.actualValue = newValue;
			this.valueChanged();
		}
		
		
		// ************ AspectAdapter impl *************************************
		
		@Override
		protected void engageSubject_() {
			// we need to build 'actualValue' *before* calling 'super'
			// because 'super' calls back here to #buildValue_()
			this.actualValue = this.buildActualValue_();
			super.engageSubject_();
		}
		
		@Override
		protected void disengageSubject_() {
			super.disengageSubject_();
			this.actualValue = null;
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected Boolean buildValue_() {
			return this.actualValue;
		}
		
		protected Boolean buildActualValue_() {
			return Boolean.valueOf(this.subjectHasUserOverrideDefault());
		}

		protected boolean subjectHasUserOverrideDefault() {
			return ! StringTools.stringIsEmpty(this.getSubjectUserOverrideDefault());
		}
		
		protected abstract String getSubjectUserOverrideDefault();
	}

	
	// ************ Catalog ************
	private static class OverrideDefaultCatalogModel
		extends OverrideDefaultModel
	{
		OverrideDefaultCatalogModel(PropertyValueModel<JpaProject> jpaProjectHolder) {
			super(jpaProjectHolder);
		}

		@Override
		protected String getSubjectUserOverrideDefault() {
			return this.subject.getUserOverrideDefaultCatalog();
		}
	}

	
	private static class DefaultCatalogModel
		extends PropertyAspectAdapter<JpaProject, String>
	{
		DefaultCatalogModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_CATALOG_PROPERTY);
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public void setValue_(String newCatalog) {
			this.subject.setUserOverrideDefaultCatalog(newCatalog);
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected String buildValue_() {
			return this.subject.getUserOverrideDefaultCatalog();
		}
	}

	private abstract static class ConnectionProfilePropertyAspectAdapter<V>
		extends BasePropertyAspectAdapter<ConnectionProfile, V>
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
					ConnectionProfilePropertyAspectAdapter.this.profileOpened(profile);
				}
			};
		}

		void profileOpened(ConnectionProfile profile) {
			if (profile.equals(this.subject)) {
				this.valueChanged();
			}
		}

		@Override
		protected void engageSubject_() {
			super.engageSubject_();
			this.subject.addConnectionListener(this.connectionListener);
		}

		@Override
		protected void disengageSubject_() {
			this.subject.removeConnectionListener(this.connectionListener);
			super.disengageSubject_();
		}

	}

	private static class DefaultDefaultCatalogModel
		extends ConnectionProfilePropertyAspectAdapter<String>
	{
		DefaultDefaultCatalogModel(PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
		}

		@Override
		protected String buildValue_() {
			Database db = this.subject.getDatabase();
			return (db == null) ? null : db.getDefaultCatalogIdentifier();
		}
	}

	private static class CombinedDefaultCatalogModel
		extends ListPropertyValueModelAdapter<String>
		implements WritablePropertyValueModel<String>
	{
		private WritablePropertyValueModel<String> defaultCatalogModel;
		
		private PropertyValueModel<Boolean> overrideDefaultCatalogModel;
		
		private PropertyValueModel<String> defaultDefaultCatalogModel;
		
		
		CombinedDefaultCatalogModel(
				WritablePropertyValueModel<String> defaultCatalogModel,
				PropertyValueModel<Boolean> overrideDefaultCatalogModel,
				PropertyValueModel<String> defaultDefaultCatalogModel) {
			super(
				new CompositeListValueModel<ListValueModel<?>,Object>(
					CollectionTools.<ListValueModel<?>>list(
						new PropertyListValueModelAdapter<String>(defaultCatalogModel),
						new PropertyListValueModelAdapter<Boolean>(overrideDefaultCatalogModel),
						new PropertyListValueModelAdapter<String>(defaultDefaultCatalogModel)
					)));
			this.defaultCatalogModel = defaultCatalogModel;
			this.overrideDefaultCatalogModel = overrideDefaultCatalogModel;
			this.defaultDefaultCatalogModel = defaultDefaultCatalogModel;
		}
		
		
		// ************ ListPropertyValueModelAdapter impl *********************
		
		@Override
		protected String buildValue() {
			if (this.overrideDefaultCatalogModel.getValue()) {
				return this.defaultCatalogModel.getValue();
			}
			return this.defaultDefaultCatalogModel.getValue();
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		public void setValue(String value) {
			if (this.overrideDefaultCatalogModel.getValue()) {
				this.defaultCatalogModel.setValue(value);
			}
			propertyChanged();
		}
	}

	
	private static class ConnectionProfileCollectionAspectAdapter<E>
		extends BaseCollectionAspectAdapter<ConnectionProfile, E>
	{
		private ConnectionListener connectionListener;

		ConnectionProfileCollectionAspectAdapter(PropertyValueModel<ConnectionProfile> subjectHolder) {
			super(subjectHolder);
			this.connectionListener = this.buildConnectionListener();
		}

		// the connection opening is probably the only thing that will happen...
		private ConnectionListener buildConnectionListener() {
			return new ConnectionAdapter() {
				@Override
				public void opened(ConnectionProfile profile) {
					ConnectionProfileCollectionAspectAdapter.this.profileOpened(profile);
				}
			};
		}

		void profileOpened(ConnectionProfile profile) {
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

	private static class CatalogChoicesModel
		extends ConnectionProfileCollectionAspectAdapter<String>
	{
		CatalogChoicesModel(PropertyValueModel<ConnectionProfile> subjectHolder) {
			super(subjectHolder);
		}

		@Override
		protected Iterator<String> iterator_() {
			Database db = this.subject.getDatabase();
			return ((db == null) || ( ! db.supportsCatalogs())) ? 
				EmptyIterator.<String>instance() : 
				db.sortedCatalogIdentifiers();
		}
	}

	
	// ************ Schema ************
	private static class OverrideDefaultSchemaModel
		extends OverrideDefaultModel
	{
		OverrideDefaultSchemaModel(PropertyValueModel<JpaProject> jpaProjectHolder) {
			super(jpaProjectHolder);
		}

		@Override
		public String getSubjectUserOverrideDefault() {
			return this.subject.getUserOverrideDefaultSchema();
		}
	}
	
	
	private static class DefaultSchemaModel
		extends PropertyAspectAdapter<JpaProject, String>
	{
		DefaultSchemaModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.USER_OVERRIDE_DEFAULT_SCHEMA_PROPERTY);
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		public void setValue_(String newSchema) {
			this.subject.setUserOverrideDefaultSchema(newSchema);
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected String buildValue_() {
			return this.subject.getUserOverrideDefaultSchema();
		}
	}
	
	
	private static class DefaultDefaultSchemaModel
		extends ConnectionProfilePropertyAspectAdapter<String>
	{
		private final PropertyValueModel<String> defaultCatalogModel;
		private final PropertyChangeListener catalogListener;

		DefaultDefaultSchemaModel(PropertyValueModel<String> defaultCatalogModel,
				PropertyValueModel<ConnectionProfile> connectionProfileModel) {
			super(connectionProfileModel);
			this.defaultCatalogModel = defaultCatalogModel;
			this.catalogListener = this.buildCatalogListener();
		}

		private PropertyChangeListener buildCatalogListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					DefaultDefaultSchemaModel.this.valueChanged();
				}
			};
		}

		@Override
		protected void engageSubject_() {
			// call 'super' last?
			this.defaultCatalogModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
			super.engageSubject_();
		}
		
		@Override
		protected void disengageSubject_() {
			super.disengageSubject_();
			this.defaultCatalogModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
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
			return (name == null) ? null : this.getCatalog(name);
		}

		/**
		 * pre-condition: 'name' is not null
		 */
		private Catalog getCatalog(String name) {
			Database db = this.getDatabase();
			return (db == null) ? null : db.getCatalogForIdentifier(name);
		}

		private Database getDatabase() {
			return this.subject.getDatabase();
		}

	}
	
	
	private static class CombinedDefaultSchemaModel
		extends ListPropertyValueModelAdapter<String>
		implements WritablePropertyValueModel<String>
	{
		private WritablePropertyValueModel<String> defaultSchemaModel;
		
		private PropertyValueModel<Boolean> overrideDefaultSchemaModel;
		
		private PropertyValueModel<String> defaultDefaultSchemaModel;
		
		
		CombinedDefaultSchemaModel(
				WritablePropertyValueModel<String> defaultSchemaModel,
				PropertyValueModel<Boolean> overrideDefaultSchemaModel,
				PropertyValueModel<String> defaultDefaultSchemaModel) {
			super(
				new CompositeListValueModel(
					CollectionTools.list(
						new PropertyListValueModelAdapter<String>(defaultSchemaModel),
						new PropertyListValueModelAdapter<Boolean>(overrideDefaultSchemaModel),
						new PropertyListValueModelAdapter<String>(defaultDefaultSchemaModel)
					)));
			this.defaultSchemaModel = defaultSchemaModel;
			this.overrideDefaultSchemaModel = overrideDefaultSchemaModel;
			this.defaultDefaultSchemaModel = defaultDefaultSchemaModel;
		}
		
		
		// ************ ListPropertyValueModelAdapter impl *********************
		
		@Override
		protected String buildValue() {
			if (this.overrideDefaultSchemaModel.getValue()) {
				return this.defaultSchemaModel.getValue();
			}
			return this.defaultDefaultSchemaModel.getValue();
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		public void setValue(String value) {
			if (this.overrideDefaultSchemaModel.getValue()) {
				this.defaultSchemaModel.setValue(value);
			}
			propertyChanged();
		}
	}


	private static class SchemaChoicesModel
		extends ConnectionProfileCollectionAspectAdapter<String>
	{
		private final PropertyValueModel<String> defaultCatalogModel;
		private final PropertyChangeListener catalogListener;

		SchemaChoicesModel(PropertyValueModel<String> defaultCatalogModel,
				PropertyValueModel<ConnectionProfile> subjectHolder) {
			super(subjectHolder);
			this.defaultCatalogModel = defaultCatalogModel;
			this.catalogListener = this.buildCatalogListener();
		}

		private PropertyChangeListener buildCatalogListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					collectionChanged();
				}
			};
		}

		@Override
		protected void engageSubject_() {
			// call 'super' last?
			this.defaultCatalogModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
			super.engageSubject_();
		}

		@Override
		protected void disengageSubject_() {
			super.disengageSubject_();
			this.defaultCatalogModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.catalogListener);
		}

		@Override
		protected Iterator<String> iterator_() {
			SchemaContainer sc = this.getSchemaContainer();
			return (sc == null) ? EmptyIterator.<String>instance() : sc.sortedSchemaIdentifiers();
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
			return (name == null) ? null : this.getCatalog(name);
		}

		/**
		 * pre-condition: 'name' is not null
		 */
		private Catalog getCatalog(String name) {
			Database db = this.getDatabase();
			return (db == null) ? null : db.getCatalogForIdentifier(name);
		}

		private Database getDatabase() {
			return this.subject.getDatabase();
		}

	}
	
	
	private final class ConnectionGroup
	{
		private Link connectLink;
		
		
		public ConnectionGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_connectionLabel);
			group.setLayout(new GridLayout(3, false));
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.PROPERTIES_JAVA_PERSISTENCE_CONNECTION);
			
			Combo connectionCombo = createCombo(group, 3, true);
			
			ComboModelAdapter.adapt(
				JpaProjectPropertiesPage.this.connectionChoicesModel,
				JpaProjectPropertiesPage.this.connectionModel,
				connectionCombo,
				new StringConverter<String>() {
					public String convertToString(String o) {
						return (o == null) ? 
							JptUiMessages.JpaFacetWizardPage_none :
							o;
					}
				});
			
			Link connectionLink = new Link(group, SWT.NONE);
			GridData data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			connectionLink.setLayoutData(data);
			connectionLink.setText(JptUiMessages.JpaFacetWizardPage_connectionLink);
			connectionLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						openNewConnectionWizard();
					}
				}
			);
			
			connectLink = new Link(group, SWT.NONE);
			data = new GridData(GridData.END, GridData.CENTER, false, false);
			data.horizontalSpan = 2;
			connectLink.setLayoutData(data);
			connectLink.setText(JptUiMessages.JpaFacetWizardPage_connectLink);
			updateConnectLink();
			connectLink.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						openConnectionProfile();
					}
				});
			
			PropertyChangeListener linkUpdateListener = new PropertyChangeListener() {
					public void propertyChanged(PropertyChangeEvent event) {
						updateConnectLink();
					}
				};
			JpaProjectPropertiesPage.this.connectionModel.addPropertyChangeListener(
					PropertyValueModel.VALUE,
					linkUpdateListener);

			// overrideDefaultCatalog
			Button overrideDefaultCatalogButton = createButton(
					group, 3, 
					JptUiMessages.JpaFacetWizardPage_overrideDefaultCatalogLabel, 
					SWT.CHECK);
			
			BooleanButtonModelAdapter.adapt(
				JpaProjectPropertiesPage.this.overrideDefaultCatalogModel,
				overrideDefaultCatalogButton);
			
			Label defaultCatalogLabel = new Label(group, SWT.LEFT);
			defaultCatalogLabel.setText(JptUiMessages.JpaFacetWizardPage_defaultCatalogLabel);
			GridData gd = new GridData();
			gd.horizontalSpan = 1;
			defaultCatalogLabel.setLayoutData(gd);
			
			Combo defaultCatalogCombo = createCombo(group, 1, true);
			
			ComboModelAdapter.adapt(
				JpaProjectPropertiesPage.this.catalogChoicesModel,
				JpaProjectPropertiesPage.this.combinedDefaultCatalogModel,
				defaultCatalogCombo);
			
			new ControlEnabler(
				JpaProjectPropertiesPage.this.overrideDefaultCatalogModel,
				defaultCatalogLabel, defaultCatalogCombo);
			
			// overrideDefaultSchema
			Button overrideDefaultSchemaButton = createButton(
					group, 3, 
					JptUiMessages.JpaFacetWizardPage_overrideDefaultSchemaLabel, 
					SWT.CHECK);
			
			BooleanButtonModelAdapter.adapt(
				JpaProjectPropertiesPage.this.overrideDefaultSchemaModel,
				overrideDefaultSchemaButton);
			
			Label defaultSchemaLabel = new Label(group, SWT.LEFT);
			defaultSchemaLabel.setText(JptUiMessages.JpaFacetWizardPage_defaultSchemaLabel);
			gd = new GridData();
			gd.horizontalSpan = 1;
			defaultSchemaLabel.setLayoutData(gd);
			
			Combo defaultSchemaCombo = createCombo(group, 1, true);
			
			ComboModelAdapter.adapt(
				JpaProjectPropertiesPage.this.schemaChoicesModel,
				JpaProjectPropertiesPage.this.combinedDefaultSchemaModel,
				defaultSchemaCombo);
			
			new ControlEnabler(
				JpaProjectPropertiesPage.this.overrideDefaultSchemaModel,
				defaultSchemaLabel, defaultSchemaCombo);
		}
		
		private void openNewConnectionWizard() {
			String connectionName = DTPUiTools.createNewConnectionProfile();
			if (connectionName != null) {
				JpaProjectPropertiesPage.this.connectionModel.setValue(connectionName);
			}
		}
		
		private void openConnectionProfile() {
			ConnectionProfile cp = JpaProjectPropertiesPage.this.getConnectionProfile();
			if (cp != null) {
				cp.connect();
			}
			updateConnectLink();
		}
		
		private void updateConnectLink() {
			if (! getControl().isDisposed()) {
				ConnectionProfile cp = getConnectionProfile();
				this.connectLink.setEnabled((cp != null) && cp.isDisconnected());
			}
		}
	}
	
	
	private class DiscoverAnnotatedClassesModel
		extends PropertyAspectAdapter<JpaProject, Boolean>
	{
		private DiscoverAnnotatedClassesModel(PropertyValueModel<JpaProject> jpaProjectModel) { 
			super(jpaProjectModel, JpaProject.DISCOVERS_ANNOTATED_CLASSES_PROPERTY);
		}
		
		
		// ************ WritablePropertyValueModel impl ************************
		
		@Override
		protected void setValue_(Boolean newValue) {
			this.subject.setDiscoversAnnotatedClasses(newValue);
		}
		
		
		// ************ internal ***********************************************
		
		@Override
		protected Boolean buildValue_() {
			return this.subject.discoversAnnotatedClasses();
		}
	}
	
	
	private final class PersistentClassManagementGroup
	{
		public PersistentClassManagementGroup(Composite composite) {
			Group group = new Group(composite, SWT.NONE);
			group.setText(JptUiMessages.JpaFacetWizardPage_persistentClassManagementLabel);
			group.setLayout(new GridLayout());
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			PlatformUI.getWorkbench().getHelpSystem().setHelp(group, JpaHelpContextIds.NEW_JPA_PROJECT_CONTENT_PAGE_CLASSPATH);

			Button discoverClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_discoverClassesButton, SWT.RADIO);
			
			BooleanButtonModelAdapter.adapt(
				JpaProjectPropertiesPage.this.discoverAnnotatedClassesModel, 
				discoverClassesButton);
			
			Button listClassesButton = createButton(group, 1, JptUiMessages.JpaFacetWizardPage_listClassesButton, SWT.RADIO);
			
			BooleanButtonModelAdapter.adapt(
				JpaProjectPropertiesPage.this.listAnnotatedClassesModel,
				listClassesButton);
		}
	}
}
