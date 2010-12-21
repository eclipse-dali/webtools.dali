/*******************************************************************************
 *  Copyright (c) 2010  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.properties;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.operation.IRunnableContext;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.JptJaxbCorePlugin;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.jaxb.ui.internal.wizards.classesgen.SelectFileOrXMLCatalogIdPanel;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.swt.TableModelAdapter;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterables.SingleElementIterable;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.AspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.BufferedWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritableCollectionValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.eclipse.wst.common.uriresolver.internal.URI;

/**
 * Shows the schemas associated with a JAXB project
 */
public class JaxbSchemasPropertiesPage
		extends PropertyPage {
	
	private static final int SIZING_SELECTION_PANE_WIDTH = 450;
	
	
	protected final WritablePropertyValueModel<IProject> projectModel;
	
	private final PropertyValueModel<JaxbProject> jaxbProjectModel;
	
	protected final BufferedWritablePropertyValueModel.Trigger trigger;
	
	private final SchemasModel schemasModel;
	
	private final WritableCollectionValueModel<Schema> schemasSelectionModel;
	
	
	public JaxbSchemasPropertiesPage() {
		super();
		this.projectModel = new SimplePropertyValueModel<IProject>();
		this.jaxbProjectModel = new JaxbProjectModel(this.projectModel);
		this.trigger = new BufferedWritablePropertyValueModel.Trigger();
		this.schemasModel = new SchemasModel(this.jaxbProjectModel, this.trigger);
		this.schemasSelectionModel = new SimpleCollectionValueModel<Schema>();
		setDescription(JptJaxbUiMessages.SchemasPage_description);
	}
	
	
	protected IProject getProject() {
		return this.projectModel.getValue();
	}
	
	@Override
	public final void createControl(Composite parent ) {
		super.createControl( parent );
		
		Button revertButton = getDefaultsButton();
		
		revertButton.setText(JptJaxbUiMessages.SchemasPage_revert);
		
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
		Point minButtonSize = revertButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
		gd.widthHint = Math.max(convertHorizontalDLUsToPixels(IDialogConstants.BUTTON_WIDTH), minButtonSize.x);
		revertButton.setLayoutData(gd);
		
		updateButtons();
		
		getShell().layout(true, true);
    }
	
	@Override
	protected final Control createContents(Composite parent) {
		IAdaptable element = getElement();
		
		IProject project = null;
		if (element instanceof IProject) {
			project = (IProject) element;
		}
		else {
			project = (IProject) Platform.getAdapterManager().loadAdapter(element, IProject.class.getName());
		}
		
		this.projectModel.setValue(project);
		
		Composite pageComponent = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		pageComponent.setLayout(layout);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.widthHint = SIZING_SELECTION_PANE_WIDTH;
		pageComponent.setLayoutData(data);
		
		Label schemasLabel = new Label(pageComponent, SWT.LEFT);
		schemasLabel.setText(JptJaxbUiMessages.SchemasPage_schemas);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		schemasLabel.setLayoutData(data);
		
		// create the table composite
		Composite tableComposite = new Composite(pageComponent, SWT.NONE);
		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		data.widthHint = SIZING_SELECTION_PANE_WIDTH;
		tableComposite.setLayoutData(data);
		
		// create the table
		TableViewer schemasTable = new TableViewer(tableComposite, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);
		TableModelAdapter.adapt(
				new SortedListValueModelAdapter<Schema>(this.schemasModel),
				this.schemasSelectionModel,
				schemasTable.getTable(),
				new SchemaColumnAdapter(),
				new SchemaTableLabelProvider());
		ColumnViewerToolTipSupport.enableFor(schemasTable, ToolTip.NO_RECREATE);
		schemasTable.getTable().setHeaderVisible(true);
		schemasTable.getTable().setToolTipText(null);
        
		// set the table composite layout (after the table, so that column information is available)
		TableColumnLayout tableLayout = new TableColumnLayout();
		tableLayout.setColumnData(schemasTable.getTable().getColumn(0), new ColumnWeightData(15));
		tableLayout.setColumnData(schemasTable.getTable().getColumn(1), new ColumnWeightData(25));
		tableComposite.setLayout(tableLayout);
		
		// set the table layout data
		data = new GridData(GridData.FILL_BOTH);
		data.heightHint = schemasTable.getTable().getItemHeight() * 7;
		data.widthHint = 200;
		schemasTable.getTable().setLayoutData(data);
		
		createButtonGroup(pageComponent);
        
        Dialog.applyDialogFont(parent);
		
        return pageComponent;
    }
	
	protected void createButtonGroup(Composite parent) {
		Composite groupComponent = new Composite(parent, SWT.NULL);
		GridLayout groupLayout = new GridLayout();
		groupLayout.marginWidth = 0;
		groupLayout.marginHeight = 0;
		groupComponent.setLayout(groupLayout);
		GridData data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		groupComponent.setLayoutData(data);
		
		Button addButton = new Button(groupComponent, SWT.PUSH);
		addButton.setText(JptJaxbUiMessages.SchemasPage_addButtonLabel);
		addButton.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						addSchema();
					}
				});
		setButtonLayoutData(addButton);
		
		Button editButton = new Button(groupComponent, SWT.PUSH);
		editButton.setText(JptJaxbUiMessages.SchemasPage_editButtonLabel);
		editButton.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						editSelectedSchema();
					}
				});
		SWTTools.controlEnabledState(buildEditEnabledModel(), editButton);
		setButtonLayoutData(editButton);
		
		Button removeButton = new Button(groupComponent, SWT.PUSH);
		removeButton.setText(JptJaxbUiMessages.SchemasPage_removeButtonLabel);
		removeButton.addSelectionListener(
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						removeSelectedSchemas();
					}
				});
		SWTTools.controlEnabledState(buildRemoveEnabledModel(), removeButton);
        setButtonLayoutData(removeButton);
	}
	
	
	private void addSchema() {
		// constructs a dialog for editing the new schema namespace and location
		AddEditSchemaDialog dialog = 
				new AddEditSchemaDialog(
						getShell(),
						null,
						this.schemasModel.getValue());
		
		// opens the dialog - just returns if the user cancels it
		if (dialog.open() == Window.CANCEL) {
			return;
		}
		
		// otherwise, adds the new schema
		Schema schema = this.schemasModel.addSchema(dialog.getNamespace(), dialog.getLocation());
		
        // select the new schema
        this.schemasSelectionModel.setValues(new SingleElementIterable<Schema>(schema));
	}
	
	private PropertyValueModel<Boolean> buildEditEnabledModel() {
		return new CollectionPropertyValueModelAdapter<Boolean>(this.schemasSelectionModel) {
			@Override
			protected Boolean buildValue() {
				return this.collectionModel.size() == 1;
			}
		};
	}
	
	private void editSelectedSchema() {
		// constructs a dialog for editing the new schema namespace and location
		final Schema schema = this.schemasSelectionModel.iterator().next();
		AddEditSchemaDialog dialog = 
				new AddEditSchemaDialog(
						getShell(),
						schema,
						this.schemasModel.getValue());
		
		// opens the dialog - just returns if the user cancels it
		if (dialog.open() == Window.CANCEL) {
			return;
		}
		
		// otherwise, changes the new schema
		schema.setNamespace(dialog.getNamespace());
		schema.setLocation(dialog.getLocation());
	}
	
	private PropertyValueModel<Boolean> buildRemoveEnabledModel() {
		return new CollectionPropertyValueModelAdapter<Boolean>(this.schemasSelectionModel) {
			@Override
			protected Boolean buildValue() {
				return this.collectionModel.size() >= 1;
			}
		};
	}
	
	private void removeSelectedSchemas() {
		this.schemasModel.removeSchemas(CollectionTools.iterable(this.schemasSelectionModel.iterator()));
	}
	
	private void updateButtons() {
		boolean enableApply = isValid();
		boolean enableRevert = true;
		
		Button applyButton = getApplyButton();
		
		if (applyButton != null) {
			applyButton.setEnabled(enableApply);
		}
		
		Button revertButton = getDefaultsButton();
		
		if (revertButton != null) {
			revertButton.setEnabled(enableRevert);
		}
	}
	
	
	// ********** OK/Revert/Apply behavior **********

	@Override
	public boolean performOk() {
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
							buildOkWorkspaceRunnable(),
							ws.getRoot(),
							IWorkspace.AVOID_UPDATE,
							monitor);
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
				performOk_(monitor);
			}
		};
	}
	
	void performOk_(IProgressMonitor monitor) throws CoreException {
		if (this.schemasModel.hasChanges()) {
			this.trigger.accept();
			JptJaxbCorePlugin.getProjectManager().rebuildJaxbProject(getProject());
			getProject().build(IncrementalProjectBuilder.FULL_BUILD, monitor);
		}
	}
	
	@Override
	protected void performDefaults() {
		this.trigger.reset();
	}
	
	@Override
	public void dispose() {
		
		super.dispose();
	}
	
	
	static class SchemasModel
			extends AspectAdapter<JaxbProject>
			implements CollectionValueModel<Schema> {
		
		/**
		 * The collection of schemas
		 */
		protected final Collection<Schema> schemas;
		
		/**
		 * This is the trigger that indicates whether the buffered value
		 * should be accepted or reset.
		 */
		protected final PropertyValueModel<Boolean> triggerHolder;
	
		/** This listens to the trigger holder. */
		protected final PropertyChangeListener triggerChangeListener;

		
		SchemasModel(PropertyValueModel<JaxbProject> subjectHolder, PropertyValueModel<Boolean> triggerHolder) {
			super(subjectHolder);
			this.schemas = new ArrayList<Schema>();
			this.triggerHolder = triggerHolder;
			this.triggerChangeListener = buildTriggerChangeListener();
		}
		
		
		protected PropertyChangeListener buildTriggerChangeListener() {
			return new PropertyChangeListener() {
				public void propertyChanged(PropertyChangeEvent event) {
					triggerChanged(event);
				}
			};
		}
		
		protected Collection<Schema> buildSchemas_() {
			ArrayList<Schema> schemas = new ArrayList<Schema>();
			for (Map.Entry<String, String> entry : this.subject.getSchemaLibrary().getSchemaLocations().entrySet()) {
				schemas.add(new Schema(entry.getKey(), entry.getValue()));
			}
			return schemas;
		}
		
		public boolean hasChanges() {
			return (this.subject == null) ? false : ! this.schemas.equals(buildSchemas_());
		}
		
		protected void triggerChanged(PropertyChangeEvent event) {
			if (hasChanges()) {
				if (this.subject != null && ((Boolean) event.getNewValue()).booleanValue()) {
					this.accept();
				} else {
					this.reset();
				}
			}
		}
		
		public void accept() {
			Map<String, String> schemaLocations = new HashMap<String, String>();
			for (Schema schema : this.schemas) {
				schemaLocations.put(schema.getNamespace(), schema.getLocation());
			}
			this.subject.getSchemaLibrary().setSchemaLocations(schemaLocations);
		}
		
		public void reset() {
			this.schemas.clear();
			if (this.subject != null) {
				this.schemas.addAll(buildSchemas_());
			}
			fireCollectionChanged(VALUES, getValue());
		}
		
		public Schema addSchema(String namespace, String location) {
			Schema schema = new Schema(namespace, location);
			addItemToCollection(schema, this.schemas, VALUES);
			return schema;
		}
		
		public void removeSchemas(Iterable<Schema> schemas) {
			removeItemsFromCollection(schemas, this.schemas, VALUES);
		}
		
		
		// ************ AspectAdapter impl ************************************
		
		@Override
		protected String getListenerAspectName() {
			return VALUES;
		}
		
		@Override
		protected Class<? extends EventListener> getListenerClass() {
			return CollectionChangeListener.class;
		}
		
		@Override
		protected boolean hasListeners() {
			return hasAnyCollectionChangeListeners(VALUES);
		}
		
		@Override
		protected void fireAspectChanged(Object oldValue, Object newValue) {
			fireCollectionChanged(VALUES, getValue());
		}
		
		@Override
		protected void engageSubject_() {
			this.schemas.addAll(buildSchemas_());
		}
		
		@Override
		protected void disengageSubject_() {
			this.schemas.clear();
		}
		
		@Override
		protected void engageModels() {
			super.engageModels();
			this.triggerHolder.addPropertyChangeListener(PropertyValueModel.VALUE, this.triggerChangeListener);
		}
		
		@Override
		protected void disengageModels() {
			this.triggerHolder.removePropertyChangeListener(PropertyValueModel.VALUE, this.triggerChangeListener);
			super.disengageModels();
		}
		
		@Override
		protected Collection<Schema> getValue() {
			return CollectionTools.collection(iterator());
		}
		
		
		// ************ CollectionValueModel impl *****************************
		
		public Iterator<Schema> iterator() {
			return this.schemas.iterator();
		}
		
		public int size() {
			return this.schemas.size();
		}
	}
	
	
	static class Schema 
			extends AbstractModel
			implements Comparable<Schema> {
		
		Schema(String namespace, String location) {
			super();
			this.namespace = namespace;
			this.location = location;
		}
		
		
		private String namespace;
		final static String NAMESPACE_PROPERTY = "namespace";
		
		private String location;
		final static String LOCATION_PROPERTY = "location";
		
		
		String getNamespace() {
			return this.namespace;
		}
		
		void setNamespace(String namespace) {
			String old = this.namespace;
			this.namespace = namespace;
			firePropertyChanged(NAMESPACE_PROPERTY, old, namespace);
		}
		
		String getLocation() {
			return this.location;
		}
		
		void setLocation(String location) {
			String old = this.location;
			this.location = location;
			firePropertyChanged(LOCATION_PROPERTY, old, location);
		}
		
		public int compareTo(Schema o) {
			return this.namespace.compareTo(o.namespace);
		}
	}
	
	
	static class SchemaColumnAdapter
			implements ColumnAdapter<Schema> {
		
		static final int COLUMN_COUNT = 2;
		static final int NAMESPACE_COLUMN = 0;
		static final int LOCATION_COLUMN = 1;
		
		
		public int columnCount() {
			return COLUMN_COUNT;
		}
		
		public String columnName(int columnIndex) {
			switch (columnIndex) {
				case NAMESPACE_COLUMN :
					return JptJaxbUiMessages.SchemasPage_namespaceColumnLabel;
				case LOCATION_COLUMN :
					return JptJaxbUiMessages.SchemasPage_locationColumnLabel;
				default :
					return null;
			}
		}
		
		public WritablePropertyValueModel<?>[] cellModels(Schema subject) {
			WritablePropertyValueModel<?>[] cellModels = new WritablePropertyValueModel<?>[COLUMN_COUNT];
			cellModels[NAMESPACE_COLUMN] = buildNamespaceCellModel(subject);
			cellModels[LOCATION_COLUMN] = buildLocationCellModel(subject);
			return cellModels;
		}
		
		private WritablePropertyValueModel<String> buildNamespaceCellModel(Schema subject) {
			return new PropertyAspectAdapter<Schema, String>(Schema.NAMESPACE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getNamespace();
				}
			};
		}
		
		private WritablePropertyValueModel<String> buildLocationCellModel(Schema subject) {
			return new PropertyAspectAdapter<Schema, String>(Schema.LOCATION_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return this.subject.getLocation();
				}
			};
		}
	}
	
	
	static class SchemaTableLabelProvider
			extends LabelProvider
			implements ITableLabelProvider {
		
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			switch (columnIndex) {
				case SchemaColumnAdapter.NAMESPACE_COLUMN :
					return ((Schema) element).getNamespace();
				case SchemaColumnAdapter.LOCATION_COLUMN :
					return ((Schema) element).getLocation();
				default :
					return null;
			}
		}
	}
	
	
	static class AddEditSchemaDialog
			extends TitleAreaDialog {
		
		private Schema currentSchema;
		
		private String defaultMessage;
		
		private String namespace;
		
		private final WritablePropertyValueModel<String> location;
		
		private final Mode mode;
		
		private Iterable<Schema> allSchemas;
		
		
		public AddEditSchemaDialog(Shell shell, Schema currentSchema, Iterable<Schema> allSchemas) {
			super(shell);
			this.currentSchema = currentSchema;
			this.allSchemas = allSchemas;
			this.location = new SimplePropertyValueModel<String>();
			
			this.mode = (this.currentSchema == null) ? Mode.ADD : Mode.EDIT;
			if (this.mode == Mode.ADD) {
				this.defaultMessage = JptJaxbUiMessages.SchemasPage_addSchemaMessage;
				this.namespace = "";
				this.location.setValue("");
			}
			else {
				this.defaultMessage = JptJaxbUiMessages.SchemasPage_editSchemaMessage;
				this.namespace = currentSchema.getNamespace();
				this.location.setValue(currentSchema.getLocation());
			}
		}
		
		
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			if (this.mode == Mode.ADD) {
				newShell.setText(JptJaxbUiMessages.SchemasPage_addSchemaDialogTitle);
			}
			else {
				newShell.setText(JptJaxbUiMessages.SchemasPage_editSchemaDialogTitle);
			}
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite dialogArea = (Composite) super.createDialogArea(parent);
			
			setMessage(this.defaultMessage);
			if (this.mode == Mode.ADD) {
				setTitle(JptJaxbUiMessages.SchemasPage_addSchemaTitle);
			}
			else {
				setTitle(JptJaxbUiMessages.SchemasPage_editSchemaTitle);
			}
			
			Composite composite = new Composite(dialogArea, SWT.NONE);
			composite.setLayout(new GridLayout(3, false));
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			
			Label namespaceLabel = new Label(composite, SWT.NULL);
			namespaceLabel.setText(JptJaxbUiMessages.SchemasPage_namespaceLabel);
			namespaceLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			
			final Text namespaceText = new Text(composite, SWT.SINGLE | SWT.BORDER);
			namespaceText.setText(this.namespace);
			namespaceText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
			namespaceText.addModifyListener(
					new ModifyListener() {
						public void modifyText(ModifyEvent event) {
							namespaceChanged(namespaceText.getText());
						}
					});
			
			Label locationLabel = new Label(composite, SWT.NULL);
			locationLabel.setText(JptJaxbUiMessages.SchemasPage_locationLabel);
			locationLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			
			final Text locationText = new Text(composite, SWT.SINGLE | SWT.BORDER);
			locationText.setText(this.location.getValue());
			locationText.setEditable(false);
			locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
			this.location.addPropertyChangeListener(
					PropertyValueModel.VALUE,
					new PropertyChangeListener() {
						public void propertyChanged(PropertyChangeEvent event) {
							locationText.setText((String) event.getNewValue());
						}
					});
			
			Button browseButton = new Button(composite, SWT.PUSH);
			browseButton.setText(JptJaxbUiMessages.SchemasPage_browseButtonLabel);
			browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, true, 1, 1));
			browseButton.addSelectionListener(
					new SelectionListener() {
						public void widgetSelected(SelectionEvent e) {
							browseForSchemaLocation();
						}
						
						public void widgetDefaultSelected(SelectionEvent e) {
							widgetSelected(e);
						}
					});
			
			Dialog.applyDialogFont(dialogArea);
			
			return dialogArea;
		}
		
		@Override
		protected boolean isResizable() {
			return true;
		}
		
		private void namespaceChanged(String newNamespace) {
			this.namespace = newNamespace;
			validate();
		}
		
		private void browseForSchemaLocation() {
			SchemaLocationDialog dialog = new SchemaLocationDialog(getShell());
			
			// opens the dialog - just returns if the user cancels it
			if (dialog.open() == Window.CANCEL) {
				return;
			}
			
			this.location.setValue(dialog.getLocation());
			validate();
		}
		
		public String getNamespace() {
			return this.namespace;
		}
		
		public String getLocation() {
			return this.location.getValue();
		}
		
		private void validate() {
			if (StringTools.stringIsEmpty(this.namespace)) {
				setMessage(JptJaxbUiMessages.SchemasPage_noNamespaceMessage, IMessageProvider.INFORMATION);
			}
			else if (isDuplicateNamespace()) {
				setErrorMessage(JptJaxbUiMessages.SchemasPage_duplicateNamespaceMessage);
			}
			else if (StringTools.stringIsEmpty(this.location.getValue())) {
				setErrorMessage(JptJaxbUiMessages.SchemasPage_noLocationMessage);
			}
			else {
				setErrorMessage(null);
				setMessage(this.defaultMessage);
			}
			getButton(IDialogConstants.OK_ID).setEnabled(getErrorMessage() == null);
		}
		
		private boolean isDuplicateNamespace() {
			for (Schema schema : this.allSchemas) {
				if ((this.currentSchema != schema) && this.namespace.equals(schema.getNamespace())) {
					return true;
				}
			}
			return false;
		}
		
		
		private enum Mode {
			ADD,
			EDIT
		}
	}
	
	
	static class SchemaLocationDialog
			extends TrayDialog {
		
		private SelectFileOrXMLCatalogIdPanel locationPanel;
		
		private String location;
		
		
		public SchemaLocationDialog(Shell shell) {
			super(shell);
		}
		
		
		@Override
		protected void configureShell(Shell newShell) {
			super.configureShell(newShell);
			newShell.setText(JptJaxbUiMessages.SchemasPage_chooseLocationTitle);
		}
		
		@Override
		protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			
			this.locationPanel = new SelectFileOrXMLCatalogIdPanel(composite, StructuredSelection.EMPTY);
			this.locationPanel.setFilterExtensions(new String[] {".xsd"});
			this.locationPanel.update();
			this.locationPanel.setVisibleHelper(true);
			
			return composite;
		}
		
		@Override
		protected boolean isResizable() {
			return true;
		}
		
		@Override
		protected void okPressed() {
			IFile file = this.locationPanel.getFile();
			if (file != null) {
				this.location = URI.createPlatformResourceURI(file.getFullPath().toString()).toString();
			}
			else {
				this.location = this.locationPanel.getXMLCatalogId();
			}
			
			super.okPressed();
		}
		
		public String getLocation() {
			return this.location;
		}
	}
}
