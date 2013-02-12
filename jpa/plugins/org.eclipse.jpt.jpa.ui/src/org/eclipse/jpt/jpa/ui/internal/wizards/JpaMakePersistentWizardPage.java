/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
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
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaElementComparator;
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
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.ui.internal.utility.SynchronousUiCommandExecutor;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.command.Command;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProjectManager;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.Persistence;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.internal.context.java.JavaSourceFileDefinition;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXmlDefinition;
import org.eclipse.jpt.jpa.core.resource.ResourceMappingFile;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
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

public class JpaMakePersistentWizardPage
	extends WizardPage
{
	private TableViewer classTableViewer;
	private final String helpContextId;

	private final Type[] selectedTypes;

	/* CU private */ final JpaProject jpaProject;
	private JptResourceType jptResourceType;
	
	private final ModifiablePropertyValueModel<Boolean> annotateInJavaModel;

	private boolean isFirstCheck = true;
	private final ModifiablePropertyValueModel<String> mappingFileModel;
	
	private final ModifiablePropertyValueModel<Boolean> listInPersistenceXmlModel;

	/* CU private */ final ResourceManager resourceManager;

	// sizing constants
	private final static int TABLE_HEIGHT = 250;
	private final static int TABLE_WIDTH = 300;

	private static String MAKE_PERSISTENT_PAGE_NAME = "MakePersistent"; //$NON-NLS-1$


	protected JpaMakePersistentWizardPage(
			JpaProject jpaProject,
			Set<IType> selectedJdtTypes,
			ResourceManager resourceManager,
			String helpContextId
	) {
		super(MAKE_PERSISTENT_PAGE_NAME);
		this.jpaProject = jpaProject;

		this.selectedTypes = this.buildSelectedTypes(selectedJdtTypes);
		this.resourceManager = resourceManager;
		this.jptResourceType = JavaSourceFileDefinition.instance().getResourceType();
		this.helpContextId = helpContextId;
		this.annotateInJavaModel = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.mappingFileModel = new SimplePropertyValueModel<String>();
		this.listInPersistenceXmlModel = new SimplePropertyValueModel<Boolean>(Boolean.valueOf(!this.jpaProject.discoversAnnotatedClasses()));
		this.setTitle(JptJpaUiMessages.JpaMakePersistentWizardPage_title);
		this.setMessage(JptJpaUiMessages.JpaMakePersistentWizardPage_message);
	}

	protected Type[] buildSelectedTypes(Set<IType> selectedJdtTypes) {
		return ArrayTools.array(this.buildSelectedTypesIterable(selectedJdtTypes), Type.class);
	}

	protected Iterable<Type> buildSelectedTypesIterable(Set<IType> selectedJdtTypes) {
		return new TransformationIterable<IType, Type>(this.selectNonPersistentJdtTypes(selectedJdtTypes), new JdtTypeTransformer());
	}

	/* CU private */ class JdtTypeTransformer
		extends TransformerAdapter<IType, Type>
	{
		@Override
		public Type transform(IType jdtType) {
			return new Type(jdtType);
		}
	}

	/**
	 * Return all {@link IType JDT type}s that are not already persistent.
	 * Any root structure nodes means the type is already annotated, 
	 * listed in <code>persistence.xml</code>, or listed in a mapping file.
	 */
	protected Iterable<IType> selectNonPersistentJdtTypes(Set<IType> selectedJdtTypes) {
		return IterableTools.filter(selectedJdtTypes, new NonPersistentJdtTypeFilter());
	}

	/* CU private */ class NonPersistentJdtTypeFilter
		extends Predicate.Adapter<IType>
	{
		@Override
		public boolean evaluate(IType jdtType) {
			return this.getJpaFile(jdtType).getRootStructureNodesSize() == 0;
		}
		private JpaFile getJpaFile(IType jdtType) {
			return this.getJpaProject().getJpaFile((IFile) jdtType.getResource());
		}
		private JpaProject getJpaProject() {
			return JpaMakePersistentWizardPage.this.jpaProject;
		}
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
		javaAnnotationButton.setText(JptJpaUiMessages.JpaMakePersistentWizardPage_annotateInJavaRadioButton);
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
		mappingFileButton.setText(JptJpaUiMessages.JpaMakePersistentWizardPage_mappingFileRadioButton);
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
		this.classTableViewer.setInput(this.selectedTypes);

		data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.heightHint = TABLE_HEIGHT;
		data.widthHint = TABLE_WIDTH;
		this.classTableViewer.getTable().setLayoutData(data);
	
		final Button persistenceXmlCheckBox = new Button(composite, SWT.CHECK);
		persistenceXmlCheckBox.setText(JptJpaUiMessages.JpaMakePersistentWizardPage_listInPersistenceXmlCheckBox);
		SWTTools.bind(this.listInPersistenceXmlModel, persistenceXmlCheckBox);
		SWTTools.controlVisibleState(this.annotateInJavaModel, persistenceXmlCheckBox);
		
		setControl(composite);
	}
	
	private Composite createMappingFileControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginLeft = 10;
		composite.setLayout(layout);

		Link mappingFileLink = new Link(composite, SWT.LEFT);
		mappingFileLink.setText(JptJpaUiMessages.JpaMakePersistentWizardPage_mappingFileLink);
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
		SWTTools.bind(this.mappingFileModel, mappingFileText);
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
		browseButton.setText(JptJpaUiMessages.JpaMakePersistentWizardPage_mappingFileBrowseButton);
		browseButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				mappingFileBrowseButtonPressed();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				// Do nothing
			}
		});

		SWTTools.controlEnabledState(new ListInOrmMappingFileModel(this.annotateInJavaModel), mappingFileLink, mappingFileText, browseButton);
		
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
		column.getColumn().setText(JptJpaUiMessages.JpaMakePersistentWizardPage_typeTableColumn);
		column.setLabelProvider(this.buildTypeColumnLabelProvider());
	}

	protected ColumnLabelProvider buildTypeColumnLabelProvider() {
		return new TypeColumnLabelProvider();
	}

	protected void createMappingTableColumn() {
		TableViewerColumn column;
		column = new TableViewerColumn(this.classTableViewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(JptJpaUiMessages.JpaMakePersistentWizardPage_mappingTableColumn);

		column.setEditingSupport(new EditingSupport(this.classTableViewer) {
			@Override
			protected Object getValue(Object element) {
				return getMappingUiDefinition(((Type) element).mappingKey);
			}

			@Override
			protected void setValue(Object element, Object value) {
				((Type) element).setMappingKey(((MappingUiDefinition) value).getKey());
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
				return ArrayTools.array(((Type) inputElement).getTypeMappingUiDefinitions());
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
			return JpaMakePersistentWizardPage.this.getMappingUiDefinition(((Type) element).mappingKey);
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
		if (this.selectedTypes.length == 0) {
			errorMessage = JptJpaUiMessages.JpaMakePersistentWizardPage_selectedTypesPersistentError;
		}
		else if (this.isAddToOrmMappingFile()) {
			JptXmlResource ormXmlResource = getOrmXmlResource();
			if (ormXmlResource == null) {
				errorMessage = JptJpaUiMessages.JpaMakePersistentWizardPage_mappingFileDoesNotExistError;
			}
			else if (this.jpaProject.getJpaFile(ormXmlResource.getFile()).getRootStructureNodesSize() == 0) {
				errorMessage = JptJpaUiMessages.JpaMakePersistentWizardPage_mappingFileNotListedInPersistenceXmlError;
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
		this.perform(new AddToOrmXmlRunnable(this.jpaProject, this.getOrmXmlResource(), this.selectedTypes));	
		try {
			this.openEditor(this.getOrmXmlResource().getFile());
		}
		catch (Exception cantOpen) {
			throw new InvocationTargetException(cantOpen);
		} 
	}

	private void performAnnotateInJava() {
		this.perform(new AnnotateInJavaRunnable(this.jpaProject, this.selectedTypes, this.isListInPersistenceXml()));
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


	private class Type implements AbstractPersistenceUnit.MappedType {

		private final IType jdtType;

		/* CU private */ String mappingKey;

		protected Type(IType jdtType) {
			super();
			this.jdtType = jdtType;
			this.mappingKey = MappingKeys.ENTITY_TYPE_MAPPING_KEY;
		}

		public String getFullyQualifiedName() {
			return this.jdtType.getFullyQualifiedName();
		}

		public String getMappingKey() {
			return this.mappingKey;
		}

		protected void setMappingKey(String mappingKey) {
			this.mappingKey = mappingKey;
		}

		protected Iterable<MappingUiDefinition> getTypeMappingUiDefinitions() {
			JpaPlatformUi ui = JpaMakePersistentWizardPage.this.getJpaPlatformUi();
			return (ui != null) ? ui.getTypeMappingUiDefinitions(JpaMakePersistentWizardPage.this.jptResourceType) : IterableTools.<MappingUiDefinition>emptyIterable();
		}
	}
	
	private final class TypeContentProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement){
			return (Type[]) inputElement;
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
			return this.javaElementLabelProvider.getText(((Type) element).jdtType);
		}

		@Override
		public Image getImage(Object element) {
			return this.javaElementLabelProvider.getImage(((Type) element).jdtType);
		}
	}

	private final class TypeComparator extends ViewerComparator {
		private final JavaElementComparator javaElementComparator = new JavaElementComparator();

		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			return this.javaElementComparator.compare(viewer, ((Type) e1).jdtType,  ((Type) e2).jdtType);
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
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;

		AddToOrmXmlRunnable(JpaProject jpaProject, JptXmlResource ormXmlResource, AbstractPersistenceUnit.MappedType[] selectedTypes) {
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
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;

		AddToOrmXmlWorkspaceRunnable(JpaProject jpaProject, JptXmlResource ormXmlResource, AbstractPersistenceUnit.MappedType[] selectedTypes) {
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
				jpaProjectManager.execute(addToOrmXmlCommand, SynchronousUiCommandExecutor.instance());
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
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;
		private final IProgressMonitor monitor;

		AddToOrmXmlCommand(EntityMappings entityMappings, AbstractPersistenceUnit.MappedType[] selectedTypes, IProgressMonitor monitor) {
			super();
			this.entityMappings = entityMappings;
			this.selectedTypes = selectedTypes;
			this.monitor = monitor;
		}

		public void execute() {
			//TODO add API to EntityMappings - added this post-M6
			((AbstractEntityMappings) this.entityMappings).addPersistentTypes(this.selectedTypes, this.monitor);
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
		private final JpaProject jpaProject;
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;
		private final boolean listInPersistenceXml;

		AnnotateInJavaRunnable(JpaProject jpaProject, AbstractPersistenceUnit.MappedType[] selectedTypes, boolean listInPersistenceXml) {
			super();
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
				new AnnotateInJavaWorkspaceRunnable(this.jpaProject, this.selectedTypes, this.listInPersistenceXml),
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
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;
		private final boolean listInPersistenceXml;

		AnnotateInJavaWorkspaceRunnable(JpaProject jpaProject, AbstractPersistenceUnit.MappedType[] selectedTypes, boolean listInPersistenceXml) {
			super();
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
			Command annotateInJavaCommand = new AnnotateInJavaCommand(persistenceUnit, this.selectedTypes, this.listInPersistenceXml, monitor);
			try {
				jpaProjectManager.execute(annotateInJavaCommand, SynchronousUiCommandExecutor.instance());
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
			JpaRootContextNode rcn = this.jpaProject.getRootContextNode();
			return (rcn == null) ? null : rcn.getPersistenceXml();
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
		private final AbstractPersistenceUnit.MappedType[] selectedTypes;
		private final boolean listInPersistenceXml;
		private final IProgressMonitor monitor;

		AnnotateInJavaCommand(PersistenceUnit persistenceUnit, AbstractPersistenceUnit.MappedType[] selectedTypes, boolean listInPersistenceXml, IProgressMonitor monitor) {
			super();
			this.persistenceUnit = persistenceUnit;
			this.selectedTypes = selectedTypes;
			this.listInPersistenceXml = listInPersistenceXml;
			this.monitor = monitor;
		}

		public void execute() {
			//TODO add API to PersistenceUnit - added this post-M6
			((AbstractPersistenceUnit) this.persistenceUnit).addPersistentTypes(this.selectedTypes, this.listInPersistenceXml, this.monitor);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.selectedTypes);
		}
	}
}
