/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandContext;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.annotate.JavaClassAnnotater;
import org.eclipse.jpt.jpa.annotate.mapping.EntityMappingsDoc;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.TableAnnotationAttributes;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaContextRoot;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.SelectMappingFileDialog;
import org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent.JpaMakePersistentWizard.TypeComparator;
import org.eclipse.jpt.jpa.ui.internal.wizards.orm.EmbeddedMappingFileWizard;
import org.eclipse.jpt.jpa.ui.wizards.entity.JptJpaUiWizardsEntityMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

@SuppressWarnings("restriction")
public class JpaMakePersistentWizardPage
	extends WizardPage
{
	private TableViewer classTableViewer;
	private final String helpContextId;

	/* CU private */ final JpaProject jpaProject;
	private JavaClassMapping[] javaClassMappings;
	private JptResourceType jptResourceType;
	
	private final ModifiablePropertyValueModel<Boolean> annotateInJavaModel;

	private boolean isFirstCheck = true;
	private final ModifiablePropertyValueModel<String> mappingFileModel;
	
	private final ModifiablePropertyValueModel<Boolean> listInPersistenceXmlModel;

	/* CU private */ final ResourceManager resourceManager;
	
	private final Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());

	// sizing constants
	private final static int TABLE_HEIGHT = 250;
	private final static int TABLE_WIDTH = 300;

	private static String MAKE_PERSISTENT_PAGE_NAME = "MakePersistent"; //$NON-NLS-1$


	protected JpaMakePersistentWizardPage(
			JpaProject jpaProject,
			JavaClassMapping[] javaClassMappings,
			ResourceManager resourceManager,
			String helpContextId
	) {
		super(MAKE_PERSISTENT_PAGE_NAME);
		this.jpaProject = jpaProject;

		this.javaClassMappings = javaClassMappings;
		this.resourceManager = resourceManager;
		this.jptResourceType = JavaSourceFileDefinition.instance().getResourceType();
		this.helpContextId = helpContextId;
		this.annotateInJavaModel = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.mappingFileModel = new SimplePropertyValueModel<String>();
		this.listInPersistenceXmlModel = new SimplePropertyValueModel<Boolean>(Boolean.valueOf(!this.jpaProject.discoversAnnotatedClasses()));
		this.setTitle(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_TITLE);
		this.setMessage(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MESSAGE);
		
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		return (JpaPlatformUi) this.jpaProject.getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 1;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		Button javaAnnotationButton = new Button(composite, SWT.RADIO);
		javaAnnotationButton.setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_ANNOTATE_IN_JAVA_RADIO_BUTTON);
		javaAnnotationButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				JpaMakePersistentWizardPage.this.annotateInJavaModel.setValue(Boolean.TRUE);
				JpaMakePersistentWizardPage.this.jptResourceType = JavaSourceFileDefinition.instance().getResourceType();
				JpaMakePersistentWizardPage.this.validate();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				this.widgetSelected(e);
			}
		});
		
		Button mappingFileButton = new Button(composite, SWT.RADIO);
		mappingFileButton.setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_FILE_RADIO_BUTTON);
		Composite mappingFileComposite = this.createMappingFileControl(composite);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		mappingFileComposite.setLayoutData(data);

		mappingFileButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				JpaMakePersistentWizardPage.this.annotateInJavaModel.setValue(Boolean.FALSE);
				if (JpaMakePersistentWizardPage.this.isFirstCheck) {
					JpaMakePersistentWizardPage.this.mappingFileModel.setValue(XmlEntityMappings.DEFAULT_RUNTIME_PATH_NAME);
					JpaMakePersistentWizardPage.this.isFirstCheck = false;
				}
				JpaMakePersistentWizardPage.this.validate();
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				this.widgetSelected(e);
			}
		});


		this.classTableViewer = new TableViewer(composite, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		this.classTableViewer.getTable().setLinesVisible(true);
		this.classTableViewer.getTable().setHeaderVisible(true);
		this.classTableViewer.setContentProvider(new TypeContentProvider());
		this.classTableViewer.setComparator(new TypeComparator());
		this.createTypeTableColumn();
		this.createMappingTableColumn();
		this.classTableViewer.setInput(this.javaClassMappings);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = TABLE_HEIGHT;
		data.widthHint = TABLE_WIDTH;
		this.classTableViewer.getTable().setLayoutData(data);
	
		final Button persistenceXmlCheckBox = new Button(composite, SWT.CHECK);
		persistenceXmlCheckBox.setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_LIST_IN_PERSISTENCE_XML_CHECK_BOX);
		SWTBindingTools.bind(this.listInPersistenceXmlModel, persistenceXmlCheckBox);
		SWTBindingTools.bindVisibleState(this.annotateInJavaModel, persistenceXmlCheckBox);
		
		setControl(composite);
	}
	
	@Override
	public boolean canFlipToNextPage() {
		return this.annotateInJavaModel.getValue().booleanValue() && containsEntities() && super.canFlipToNextPage();
	}
	
	private boolean containsEntities() {
		boolean ret = false;
		for (JavaClassMapping javaClassMapping : this.javaClassMappings) {
			if (javaClassMapping.getMappingKey().equals(MappingKeys.ENTITY_TYPE_MAPPING_KEY)) {
				ret = true;
				break;
			}
		}
		return ret;
	}
	
	public void addListener(Listener listener) 
	{
		if( ! this.listeners.add(listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
	}

	public void removeListener(Listener listener) 
	{
		if( ! this.listeners.remove(listener)) {
			throw new IllegalArgumentException("missing listener: " + listener); //$NON-NLS-1$
		}
	}

	private Iterator<Listener> listeners() {
		return IteratorTools.clone(this.listeners);
	}

	private void fireMappingTypeChanged(JavaClassMapping javaClassMapping) 
	{
		for(Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) 
		{
			stream.next().mappingTypeChanged(javaClassMapping);
		}
	}
	
	private Composite createMappingFileControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginLeft = 10;
		composite.setLayout(layout);

		Link mappingFileLink = new Link(composite, SWT.LEFT);
		mappingFileLink.setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_FILE_LINK);
		mappingFileLink.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
		mappingFileLink.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					openNewMappingFileWizard();
				}
			}
		);
		
		Text mappingFileText = this.createText(composite, 1);
		SWTBindingTools.bind(this.mappingFileModel, mappingFileText);
		this.mappingFileModel.addPropertyChangeListener(PropertyValueModel.VALUE, new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent event) {
				JptXmlResource ormXmlResource = getOrmXmlResource();
				if (ormXmlResource == null) {
					jptResourceType = GenericOrmXmlDefinition.instance().getResourceType(); //just default to 1.0 orm resource type
				}
				else {
					jptResourceType = ormXmlResource.getResourceType();
				}
				validate();
			}
		});
		
		Button browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_FILE_BROWSE_BUTTON);
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				mappingFileBrowseButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});

		SWTBindingTools.bindEnabledState(new ListInOrmMappingFileModel(this.annotateInJavaModel), mappingFileLink, mappingFileText, browseButton);
		
		return composite;
	}

	private Text createText(Composite container, int span) {
		Text text = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = span;
		gd.grabExcessHorizontalSpace = true;
		text.setLayoutData(gd);
		return text;
	}

	private void mappingFileBrowseButtonPressed() {		
		ViewerFilter filter = buildMappingFileDialogViewerFilter();
		ITreeContentProvider contentProvider = new WorkbenchContentProvider();
		ILabelProvider labelProvider = new WorkbenchLabelProvider();
		SelectMappingFileDialog dialog = new SelectMappingFileDialog(getShell(), this.jpaProject.getProject(), labelProvider, contentProvider);
		dialog.setTitle(JptJpaUiWizardsEntityMessages.MAPPING_XML_TITLE);
		dialog.setMessage(JptJpaUiWizardsEntityMessages.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
			
		JptXmlResource resource = this.jpaProject.getMappingFileXmlResource(new Path(getMappingFileLocation()));
		IFile initialSelection = (resource != null) ? resource.getFile() : null;
		dialog.setInput(this.jpaProject.getProject());

		if (initialSelection != null) {
			dialog.setInitialSelection(initialSelection);
		}
		if (dialog.open() == Window.OK) {
			this.mappingFileModel.setValue(dialog.getChosenName());
			//have to validate in case the file name did not actually change, but the xml file was created
			validate();
		}		
	}

	/**
	 * This method create filter for the browse/add alternative mapping XML 
	 * @return new instance of viewer filter for the SelectMappingXMLDialog
	 */
	protected ViewerFilter buildMappingFileDialogViewerFilter() {
		return new XmlMappingFileViewerFilter(this.jpaProject, ResourceMappingFile.Root.CONTENT_TYPE);
	}

	private void openNewMappingFileWizard() {
		IPath path = EmbeddedMappingFileWizard.createNewMappingFile(
					new StructuredSelection(this.jpaProject.getProject()), 
					getMappingFileName());
		if (path != null) {
			this.mappingFileModel.setValue(path.toString());
			//have to validate in case the file name did not actually change, but the xml file was created
			this.validate();
		}
	}

	protected void createTypeTableColumn() {
		TableViewerColumn column = new TableViewerColumn(this.classTableViewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_TYPE_TABLE_COLUMN);
		column.setLabelProvider(this.buildTypeColumnLabelProvider());
	}

	protected ColumnLabelProvider buildTypeColumnLabelProvider() {
		return new TypeColumnLabelProvider();
	}

	protected void createMappingTableColumn() {
		TableViewerColumn column;
		column = new TableViewerColumn(this.classTableViewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_TABLE_COLUMN);

		column.setEditingSupport(new EditingSupport(this.classTableViewer) {
			@Override
			protected Object getValue(Object element) {
				return getMappingUiDefinition(((JavaClassMapping) element).getMappingKey());
			}

			@Override
			protected void setValue(Object element, Object value) {
				((JavaClassMapping) element).setMappingKey(((MappingUiDefinition) value).getKey());
				validate();
				fireMappingTypeChanged((JavaClassMapping) element);				
				getViewer().update(element, null);				
			}
		
			@Override
			protected CellEditor getCellEditor(Object element) {
				final ComboBoxViewerCellEditor comboCellEditor =
					new ComboBoxViewerCellEditor((Composite) JpaMakePersistentWizardPage.this.classTableViewer.getControl());

				comboCellEditor.setLabelProvider(buildMappingComboCellEditorLabelProvider());
				comboCellEditor.setContentProvider(buildMappingComboCellEditorContentProvider());
				comboCellEditor.setInput(element);
				return comboCellEditor;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		column.setLabelProvider(buildMappingColumnLabelProvider());
	}

	//The ComboBoxViewerCellEditor does not support the image, so no reason to implement getImage(Object)
	protected ColumnLabelProvider buildMappingComboCellEditorLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return ((MappingUiDefinition) element).getLabel();
			}
		};
	}

	protected IStructuredContentProvider buildMappingComboCellEditorContentProvider() {
		return new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ArrayTools.array(getTypeMappingUiDefinitions());
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				//do nothing
			}

			public void dispose() {
				//do nothing
			}
		};
	}

	protected ColumnLabelProvider buildMappingColumnLabelProvider() {
		return new MappingColumnLabelProvider();
	}

	/**
	 * Allows clients to listen for changes to the java class mapping keys
	 */
	public interface Listener extends EventListener
	{
		void mappingTypeChanged(JavaClassMapping javaClassMapping);
	}
	
	/* CU private */ class MappingColumnLabelProvider
		extends ColumnLabelProvider
	{
		@Override
		public String getText(Object element) {
			MappingUiDefinition def = this.getMappingUiDefinition(element);
			return (def == null) ? null : def.getLabel();
		}

		@Override
		public Image getImage(Object element) {
			ImageDescriptor descriptor = this.getImageDescriptor(element);
			return (descriptor == null) ? null : this.getResourceManager().createImage(descriptor);
		}

		private ImageDescriptor getImageDescriptor(Object element) {
			MappingUiDefinition def = this.getMappingUiDefinition(element);
			return (def == null) ? null : def.getImageDescriptor();
		}

		private MappingUiDefinition getMappingUiDefinition(Object element) {
			return JpaMakePersistentWizardPage.this.getMappingUiDefinition(((JavaClassMapping) element).getMappingKey());
		}
		private ResourceManager getResourceManager() {
			return JpaMakePersistentWizardPage.this.resourceManager;
		}
	}

	protected MappingUiDefinition getMappingUiDefinition(String mappingKey) {
		JpaPlatformUi ui = this.getJpaPlatformUi();
		return (ui == null) ? null : ui.getTypeMappingUiDefinition(this.jptResourceType, mappingKey);
	}

	protected Iterable<String> typeMappingKeys(Iterable<? extends MappingUiDefinition> mappingUiDefinitions) {
		return IterableTools.transform(mappingUiDefinitions, MappingUiDefinition.KEY_TRANSFORMER);
	}

	protected void validate() {
		String errorMessage = null;
		if (this.javaClassMappings.length == 0) {
			errorMessage = JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_SELECTED_TYPES_PERSISTENT_ERROR;
		}
		else if (this.isAddToOrmMappingFile()) {
			JptXmlResource ormXmlResource = getOrmXmlResource();
			if (ormXmlResource == null) {
				errorMessage = JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_FILE_DOES_NOT_EXIST_ERROR;
			}
			else if (this.jpaProject.getJpaFile(ormXmlResource.getFile()).getRootStructureNodesSize() == 0) {
				errorMessage = JptJpaUiMessages.JPA_MAKE_PERSISTENT_WIZARD_PAGE_MAPPING_FILE_NOT_LISTED_IN_PERSISTENCE_XML_ERROR;
			}
		}
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}

	@Override
	public final void performHelp() {
	    PlatformUI.getWorkbench().getHelpSystem().displayHelp( this.helpContextId );
	}
	
	protected void performFinish() throws InvocationTargetException {
		if (this.isAddToOrmMappingFile()) {
			this.performAddToOrmXml();
		}
		else {
			this.performAnnotateInJava();
		}
	}

	private void performAddToOrmXml() throws InvocationTargetException {
		this.perform(new AddToOrmXmlRunnable(this.jpaProject, this.getOrmXmlResource(), this.javaClassMappings));	
		try {
			this.openEditor(this.getOrmXmlResource().getFile());
		}
		catch (Exception cantOpen) {
			throw new InvocationTargetException(cantOpen);
		} 
	}

	private void performAnnotateInJava() {
		JpaMakePersistentWizard wizard = (JpaMakePersistentWizard)this.getWizard();
		this.perform(new AnnotateInJavaRunnable(wizard, this.jpaProject, this.javaClassMappings, this.isListInPersistenceXml()));
	}

	private void perform(IRunnableWithProgress runnable) {
		try {
			this.buildProgressMonitorDialog().run(true, true, runnable);  // true => fork; true => cancellable
		} catch (InvocationTargetException ex) {
			JptJpaUiPlugin.instance().logError(ex);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
			JptJpaUiPlugin.instance().logError(ex);
		}
	}

	private ProgressMonitorDialog buildProgressMonitorDialog() {
		return new ProgressMonitorDialog(null);
	}
	
	private void openEditor(final IFile file) {
		if (file != null) {
			getShell().getDisplay().asyncExec(new Runnable() {
				public void run() {
					try {
						IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, file, true);
					}
					catch (PartInitException e) {
						JptJpaUiPlugin.instance().logError(e);
					}
				}
			});
		}
	}

	protected JptXmlResource getOrmXmlResource() {
		return this.jpaProject.getMappingFileXmlResource(new Path(this.getMappingFileLocation()));
	}
	
	protected boolean isListInPersistenceXml() {
		return this.listInPersistenceXmlModel.getValue().booleanValue();
	}

	protected boolean isAnnotateInJavaModel() {
		return this.annotateInJavaModel.getValue().booleanValue();
	}

	protected boolean isAddToOrmMappingFile() {
		return !isAnnotateInJavaModel();
	}
	
	protected String getMappingFileLocation() {
		return this.mappingFileModel.getValue();
	}

	protected String getMappingFileName() {
		return new File(getMappingFileLocation()).getName();
	}

	protected Iterable<MappingUiDefinition> getTypeMappingUiDefinitions() {
		JpaPlatformUi ui = JpaMakePersistentWizardPage.this.getJpaPlatformUi();
		return (ui != null) ? ui.getTypeMappingUiDefinitions(JpaMakePersistentWizardPage.this.jptResourceType) : IterableTools.<MappingUiDefinition>emptyIterable();
	}

	private final class TypeContentProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement){
			return (JavaClassMapping[]) inputElement;
		}

		public void dispose(){}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput){}
		
	}

	private final class TypeColumnLabelProvider extends ColumnLabelProvider
	{
		private final JavaElementLabelProvider javaElementLabelProvider = 
			new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_POST_QUALIFIED
										| JavaElementLabelProvider.SHOW_SMALL_ICONS
										| JavaElementLabelProvider.SHOW_OVERLAY_ICONS);

		@Override
		public String getText(Object element) {
			return this.javaElementLabelProvider.getText(((JavaClassMapping) element).getJDTType());
		}

		@Override
		public Image getImage(Object element) {
			return this.javaElementLabelProvider.getImage(((JavaClassMapping) element).getJDTType());
		}
	}
	
	static class ListInOrmMappingFileModel
		extends AspectPropertyValueModelAdapter<Boolean, Boolean>
	{
		ListInOrmMappingFileModel(PropertyValueModel<Boolean> annotateInJavaModel) {
			super(annotateInJavaModel);
		}

		@Override
		protected Boolean buildValue_() {
			return Boolean.valueOf(!this.subject.booleanValue());
		}

		@Override
		protected void engageSubject_() {/*nothing*/}
		@Override
		protected void disengageSubject_() {/*nothing*/}
	}


	// ********** add to orm.xml runnable **********

	/**
	 * This is dispatched to the progress monitor dialog.
	 */
	/* CU private */ static class AddToOrmXmlRunnable
		implements IRunnableWithProgress
	{
		private final JpaProject jpaProject;
		private final JptXmlResource ormXmlResource;
		private final PersistentType.Config[] selectedTypes;

		AddToOrmXmlRunnable(JpaProject jpaProject, JptXmlResource ormXmlResource, PersistentType.Config[] selectedTypes) {
			super();
			this.jpaProject = jpaProject;
			this.ormXmlResource = ormXmlResource;
			this.selectedTypes = selectedTypes;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				this.run_(monitor);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}

		private void run_(IProgressMonitor monitor) throws CoreException {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			// lock the orm.xml resource, it is the only thing we will be modifying.
			ISchedulingRule rule = workspace.getRuleFactory().modifyRule(this.ormXmlResource.getFile());
			workspace.run(
				new AddToOrmXmlWorkspaceRunnable(this.jpaProject, this.ormXmlResource, this.selectedTypes),
				rule,
				IWorkspace.AVOID_UPDATE,
				monitor
			);
		}
	}


	// ********** add to orm.xml workspace runnable **********

	/**
	 * This is dispatched to the Eclipse workspace.
	 */
	/* CU private */ static class AddToOrmXmlWorkspaceRunnable
		implements IWorkspaceRunnable
	{
		private final JpaProject jpaProject;
		private final JptXmlResource ormXmlResource;
		private final PersistentType.Config[] selectedTypes;

		AddToOrmXmlWorkspaceRunnable(JpaProject jpaProject, JptXmlResource ormXmlResource, PersistentType.Config[] selectedTypes) {
			super();
			this.jpaProject = jpaProject;
			this.ormXmlResource = ormXmlResource;
			this.selectedTypes = selectedTypes;
		}

		public void run(IProgressMonitor monitor) {
			if (monitor.isCanceled()) {
				return;
			}
			JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
			if (jpaProjectManager == null) {
				return;
			}
			Command addToOrmXmlCommand = new AddToOrmXmlCommand(this.getEntityMappings(),this.selectedTypes, monitor);
			try {
				jpaProjectManager.execute(addToOrmXmlCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();  // skip save?
				throw new RuntimeException(ex);
			}
			this.ormXmlResource.save();
		}

		protected EntityMappings getEntityMappings() {
			return (EntityMappings) this.jpaProject.getJpaFile(this.ormXmlResource.getFile()).getRootStructureNodes().iterator().next();
		}

		private JpaProjectManager getJpaProjectManager() {
			return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
		}
	
		private IProject getProject() {
			return this.jpaProject.getProject();
		}

		private IWorkspace getWorkspace() {
			return this.getProject().getWorkspace();
		}
	}


	// ********** add to orm.xml command **********

	/**
	 * This is dispatched to the JPA project manager.
	 */
	/* CU private */ static class AddToOrmXmlCommand
		implements Command
	{
		private final EntityMappings entityMappings;
		private final PersistentType.Config[] selectedTypes;
		private final IProgressMonitor monitor;

		AddToOrmXmlCommand(EntityMappings entityMappings, PersistentType.Config[] selectedTypes, IProgressMonitor monitor) {
			super();
			this.entityMappings = entityMappings;
			this.selectedTypes = selectedTypes;
			this.monitor = monitor;
		}

		public void execute() {
			//TODO add API to EntityMappings - added this post-M6
			this.entityMappings.addPersistentTypes(this.selectedTypes, this.monitor);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.selectedTypes);
		}
	}


	// ********** annotate in Java runnable **********

	/**
	 * This is dispatched to the progress monitor dialog.
	 */
	/* CU private */ static class AnnotateInJavaRunnable
		implements IRunnableWithProgress
	{
		private final JpaMakePersistentWizard wizard;
		private final JpaProject jpaProject;
		private final PersistentType.Config[] selectedTypes;
		private final boolean listInPersistenceXml;

		AnnotateInJavaRunnable(JpaMakePersistentWizard wizard, JpaProject jpaProject, PersistentType.Config[] selectedTypes, boolean listInPersistenceXml) {
			super();
			this.wizard = wizard;
			this.jpaProject = jpaProject;
			this.selectedTypes = selectedTypes;
			this.listInPersistenceXml = listInPersistenceXml;
		}

		public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
			try {
				this.run_(monitor);
			} catch (CoreException ex) {
				throw new InvocationTargetException(ex);
			}
		}

		private void run_(IProgressMonitor monitor) throws CoreException {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			// lock the entire project, since we could be modifying multiple java files and the persistence.xml file
			ISchedulingRule rule = workspace.getRuleFactory().modifyRule(this.jpaProject.getProject());
			workspace.run(
				new AnnotateInJavaWorkspaceRunnable(this.wizard, this.jpaProject, this.selectedTypes, this.listInPersistenceXml),
				rule,
				IWorkspace.AVOID_UPDATE,
				monitor
			);
		}
	}


	// ********** annotate in Java workspace runnable **********

	/**
	 * This is dispatched to the Eclipse workspace.
	 */
	/* CU private */ static class AnnotateInJavaWorkspaceRunnable
		implements IWorkspaceRunnable
	{
		private final JpaProject jpaProject;
		private final PersistentType.Config[] selectedTypes;
		private final boolean listInPersistenceXml;
		private JpaMakePersistentWizard wizard;

		AnnotateInJavaWorkspaceRunnable(JpaMakePersistentWizard wizard, JpaProject jpaProject, PersistentType.Config[] selectedTypes, boolean listInPersistenceXml) {
			super();
			this.wizard = wizard;
			this.jpaProject = jpaProject;
			this.selectedTypes = selectedTypes;
			this.listInPersistenceXml = listInPersistenceXml;
		}

		public void run(IProgressMonitor monitor) {
			if (monitor.isCanceled()) {
				return;
			}
			PersistenceUnit persistenceUnit = this.getPersistenceUnit();
			if (persistenceUnit == null) {
				return; //unlikely...
			}
			JpaProjectManager jpaProjectManager = this.getJpaProjectManager();
			if (jpaProjectManager == null) {
				return;
			}
			Command annotateInJavaCommand = new AnnotateInJavaCommand(this.wizard, persistenceUnit, this.selectedTypes, this.listInPersistenceXml, monitor);
			try {
				jpaProjectManager.execute(annotateInJavaCommand, SynchronousUiCommandContext.instance());
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();  // skip save?
				throw new RuntimeException(ex);
			}
			if (this.listInPersistenceXml) {
				JptXmlResource persistenceXmlResource = this.jpaProject.getPersistenceXmlResource();
				if (persistenceXmlResource != null) {
					persistenceXmlResource.save();
				}
			}
		}
		
		protected PersistenceUnit getPersistenceUnit() {
			Persistence p = this.getPersistence();
			if (p == null) {
				return null;
			}
			ListIterator<PersistenceUnit> units = p.getPersistenceUnits().iterator();
			return units.hasNext() ? units.next() : null;
		}

		protected Persistence getPersistence() {
			PersistenceXml pxml = this.getPersistenceXml();
			return (pxml == null) ? null : pxml.getRoot();
		}

		protected PersistenceXml getPersistenceXml() {
			JpaContextRoot root = this.jpaProject.getContextRoot();
			return (root == null) ? null : root.getPersistenceXml();
		}


		private JpaProjectManager getJpaProjectManager() {
			return (JpaProjectManager) this.getWorkspace().getAdapter(JpaProjectManager.class);
		}
	
		private IProject getProject() {
			return this.jpaProject.getProject();
		}

		private IWorkspace getWorkspace() {
			return this.getProject().getWorkspace();
		}
	}


	// ********** annotate in Java command **********

	/**
	 * This is dispatched to the JPA project manager.
	 */
	/* CU private */ static class AnnotateInJavaCommand
		implements Command
	{
		private final PersistenceUnit persistenceUnit;
		private final PersistentType.Config[] selectedTypes;
		private final boolean listInPersistenceXml;
		private final IProgressMonitor monitor;
		private final JpaMakePersistentWizard wizard;

		AnnotateInJavaCommand(JpaMakePersistentWizard wiz, PersistenceUnit persistenceUnit, PersistentType.Config[] selectedTypes, boolean listInPersistenceXml, IProgressMonitor monitor) {
			super();
			this.wizard = wiz;
			this.persistenceUnit = persistenceUnit;
			this.selectedTypes = selectedTypes;
			this.listInPersistenceXml = listInPersistenceXml;
			this.monitor = monitor;
		}

		public void execute() {
			if (this.wizard.getSchema() == null) {
				this.persistenceUnit.addPersistentTypes(this.selectedTypes, this.listInPersistenceXml, this.monitor);
			}
			else {				
				JavaClassMapping[] classMappings = wizard.getJavaClassMappings();
				SubMonitor sm = SubMonitor.convert(this.monitor, classMappings.length * 2);
				for (JavaClassMapping classMapping : classMappings) {
					if (sm.isCanceled()) {
						return;
					}					
					PersistentType.Config[] mappedTypes = new PersistentType.Config[1];
					mappedTypes[0] = classMapping;
					((AbstractPersistenceUnit) this.persistenceUnit).addPersistentTypes(mappedTypes, this.listInPersistenceXml, sm);
					sm.worked(1);
					if (classMapping.getMappingKey().equals(MappingKeys.ENTITY_TYPE_MAPPING_KEY)) {
						PersistentType persistentType =  this.persistenceUnit.getPersistentType(classMapping.getFullyQualifiedName());
						annotateJavaClass(this.wizard.getSchema(), classMapping, persistentType, sm);
					}
					sm.worked(1);
				}
			}

		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.selectedTypes);
		}
		
		private void annotateJavaClass(Schema schema, JavaClassMapping classMapping, PersistentType persistentType, IProgressMonitor pm)
		{
			TableAnnotationAttributes tableAttrs = new TableAnnotationAttributes();
			tableAttrs.setTableName(classMapping.getDBTable());
			tableAttrs.setSchema(schema.getName());
			EntityPropertyElem[] propElems = classMapping.getPropertyMappings().toArray(new EntityPropertyElem[0]);
			EntityMappingsDoc mappingDoc = new EntityMappingsDoc(classMapping.getFullyQualifiedName(), 
					this.persistenceUnit.getJpaProject().getProject(), tableAttrs, 
					propElems, false);
			JavaClassAnnotater annotater = new JavaClassAnnotater(persistentType, mappingDoc, wizard.getSchema());
			annotater.setDatabaseAnnotationNameBuilder(wizard.getDatabaseAnnotationNameBuilder());
			try 
			{
				annotater.annotate(pm);
			}
			catch (Exception e)
			{
				JptJpaUiPlugin.instance().logError(e);
			}
			
		}
	}
	
}
