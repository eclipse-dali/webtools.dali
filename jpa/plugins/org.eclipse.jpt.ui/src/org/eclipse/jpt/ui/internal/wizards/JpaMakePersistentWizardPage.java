/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.wizards;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
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
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;
import org.eclipse.jpt.common.utility.internal.model.value.AspectPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaTypeMappingDefinition;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.jface.XmlMappingFileViewerFilter;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.wizards.entity.EntityWizardMsg;
import org.eclipse.jpt.ui.internal.wizards.orm.MappingFileWizard;
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
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class JpaMakePersistentWizardPage extends WizardPage {

	// sizing constants
	private final static int TABLE_HEIGHT = 250;
	private final static int TABLE_WIDTH = 300;

	private static String MAKE_PERSISTENT_PAGE_NAME = "MakePersistent"; //$NON-NLS-1$
	private TableViewer classTableViewer;
	private final String helpContextId;

	private final Type[] selectedTypes;

	private final JpaProject jpaProject;
	private JptResourceType jptResourceType;
	
	private final WritablePropertyValueModel<Boolean> annotateInJavaModel;

	private boolean isFirstCheck = true;
	private final WritablePropertyValueModel<String> mappingFileModel;
	
	private final WritablePropertyValueModel<Boolean> listInPersistenceXmlModel;

	protected JpaMakePersistentWizardPage(final JpaProject jpaProject, final List<IType> selectedTypes, final String helpContextId) {
		super(MAKE_PERSISTENT_PAGE_NAME);
		this.jpaProject = jpaProject;

		this.selectedTypes = this.buildTypes(selectedTypes);
		this.jptResourceType = JptCommonCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
		this.helpContextId = helpContextId;
		this.annotateInJavaModel = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
		this.mappingFileModel = new SimplePropertyValueModel<String>();
		this.listInPersistenceXmlModel = new SimplePropertyValueModel<Boolean>(Boolean.valueOf(!this.jpaProject.discoversAnnotatedClasses()));
		this.setTitle(JptUiMessages.JpaMakePersistentWizardPage_title);
		this.setMessage(JptUiMessages.JpaMakePersistentWizardPage_message);
	}

	protected Type[] buildTypes(final List<IType> selectedTypes) {
		return CollectionTools.list(
			new TransformationIterable<IType, Type>(nonPersistentTypes(selectedTypes)) {
				@Override
				protected Type transform(IType jdtType) {
					return new Type(jdtType);
				}
			}).toArray(new Type[] {});
	}

	/**
	 * Return all ITypes that are not already persistent.
	 * Any root structure nodes means the type is already annotated, 
	 * listed in persistence.xml, or listed in a mapping file
	 */
	protected Iterable<IType> nonPersistentTypes(final List<IType> selectedTypes) {
		return new FilteringIterable<IType>(selectedTypes) {
			@Override
			protected boolean accept(IType jdtType) {
				return getJpaProject().getJpaFile((IFile) jdtType.getResource()).rootStructureNodesSize() == 0;
			}
		};
	}

	protected JpaProject getJpaProject() {
		return this.jpaProject;
	}

	protected JpaPlatformUi getJpaPlatformUi() {
		String platformId = this.getJpaProject().getJpaPlatform().getId();
		return JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 1;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		Button javaAnnotationButton = new Button(composite, SWT.RADIO);
		javaAnnotationButton.setText(JptUiMessages.JpaMakePersistentWizardPage_annotateInJavaRadioButton);
		javaAnnotationButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				JpaMakePersistentWizardPage.this.annotateInJavaModel.setValue(Boolean.TRUE);
				JpaMakePersistentWizardPage.this.jptResourceType = JptCommonCorePlugin.JAVA_SOURCE_RESOURCE_TYPE;
			}
			
			public void widgetDefaultSelected(SelectionEvent e) {
				this.widgetSelected(e);
			}
		});
		
		Button mappingFileButton = new Button(composite, SWT.RADIO);
		mappingFileButton.setText(JptUiMessages.JpaMakePersistentWizardPage_mappingFileRadioButton);
		Composite mappingFileComposite = this.createMappingFileControl(composite);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		mappingFileComposite.setLayoutData(data);

		mappingFileButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				JpaMakePersistentWizardPage.this.annotateInJavaModel.setValue(Boolean.FALSE);
				if (JpaMakePersistentWizardPage.this.isFirstCheck) {
					JpaMakePersistentWizardPage.this.mappingFileModel.setValue(JptCorePlugin.DEFAULT_ORM_XML_RUNTIME_PATH.toString());
					JpaMakePersistentWizardPage.this.isFirstCheck = false;
				}
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
		persistenceXmlCheckBox.setText(JptUiMessages.JpaMakePersistentWizardPage_listInPersistenceXmlCheckBox);
		SWTTools.bind(this.listInPersistenceXmlModel, persistenceXmlCheckBox);
		SWTTools.controlVisibleState(this.annotateInJavaModel, persistenceXmlCheckBox);
		
		setControl(composite);
		
		this.validate();
	}
	
	private Composite createMappingFileControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginLeft = 10;
		composite.setLayout(layout);

		Link mappingFileLink = new Link(composite, SWT.LEFT);
		mappingFileLink.setText(JptUiMessages.JpaMakePersistentWizardPage_mappingFileLink);
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
				JpaXmlResource ormXmlResource = getOrmXmlResource();
				if (ormXmlResource == null) {
					jptResourceType = JptCorePlugin.ORM_XML_1_0_RESOURCE_TYPE; //just default to 1.0 orm resource type
				}
				else {
					jptResourceType = ormXmlResource.getResourceType();
				}
				validate();
			}
		});
		
		Button browseButton = new Button(composite, SWT.PUSH);
		browseButton.setText(JptUiMessages.JpaMakePersistentWizardPage_mappingFileBrowseButton);
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
		SelectJpaOrmMappingFileDialog dialog = new SelectJpaOrmMappingFileDialog(getShell(), this.jpaProject.getProject(), labelProvider, contentProvider);
		dialog.setTitle(EntityWizardMsg.MAPPING_XML_TITLE);
		dialog.setMessage(EntityWizardMsg.CHOOSE_MAPPING_XML_MESSAGE);
		dialog.addFilter(filter);
			
		JpaXmlResource resource = this.jpaProject.getMappingFileXmlResource(new Path(getMappingFileLocation()));
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
		return new XmlMappingFileViewerFilter(this.jpaProject);
	}

	private void openNewMappingFileWizard() {
		IPath path = MappingFileWizard.createNewMappingFile(
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
		column.getColumn().setText(JptUiMessages.JpaMakePersistentWizardPage_typeTableColumn);
		column.setLabelProvider(this.buildTypeColumnLabelProvider());
	}

	protected ColumnLabelProvider buildTypeColumnLabelProvider() {
		return new TypeColumnLabelProvider();
	}

	protected void createMappingTableColumn() {
		TableViewerColumn column;
		column = new TableViewerColumn(this.classTableViewer, SWT.NONE);
		column.getColumn().setWidth(200);
		column.getColumn().setText(JptUiMessages.JpaMakePersistentWizardPage_mappingTableColumn);

		column.setEditingSupport(new EditingSupport(this.classTableViewer) {
			@Override
			protected Object getValue(Object element) {
				return getMappingUiDefinition(((Type) element).mappingKey);
			}

			@Override
			protected void setValue(Object element, Object value) {
				((Type) element).setMappingKey(((MappingUiDefinition<?, ?>) value).getKey());
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
				return ((MappingUiDefinition<?, ?>) element).getLabel();
			}
		};
	}

	protected IStructuredContentProvider buildMappingComboCellEditorContentProvider() {
		return new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return CollectionTools.collection(((Type) inputElement).typeMappingUiDefinitions()).toArray();
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
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				MappingUiDefinition<? extends PersistentType, ?> mappingUiDefinition = getMappingUiDefinition(((Type) element).mappingKey);
				return mappingUiDefinition.getLabel();
			}
			
			@Override
			public Image getImage(Object element) {
				MappingUiDefinition<? extends PersistentType, ?> mappingUiDefinition = getMappingUiDefinition(((Type) element).mappingKey);
				return mappingUiDefinition.getImage();
			}
		};
	}

	protected MappingUiDefinition<? extends PersistentType, ?> getMappingUiDefinition(String mappingKey) {
		for (MappingUiDefinition<? extends PersistentType, ?> provider : typeMappingUiDefinitions(this.jptResourceType)) {
			if (provider.getKey() == mappingKey) {
				return provider;
			}
		}
		throw new IllegalArgumentException();	
	}

	protected Iterable<String> typeMappingKeys(Iterable<? extends MappingUiDefinition<? extends PersistentType, ?>> mappingUiDefinitions) {
		return new TransformationIterable<MappingUiDefinition<? extends PersistentType, ?>, String>(mappingUiDefinitions) {
			@Override
			protected String transform(MappingUiDefinition<? extends PersistentType, ?> next) {
				return next.getKey();
			}
		};
	}

	protected Iterable<? extends MappingUiDefinition<? extends PersistentType, ?>> typeMappingUiDefinitions(JptResourceType jpaResourceType) {
		return CollectionTools.iterable(getJpaPlatformUi().typeMappingUiDefinitions(jpaResourceType));
	}

	protected void validate() {
		String errorMessage = null;
		if (this.selectedTypes.length == 0) {
			errorMessage = JptUiMessages.JpaMakePersistentWizardPage_selectedTypesPersistentError;
		}
		else if (this.isAddToOrmMappingFile()) {
			JpaXmlResource ormXmlResource = getOrmXmlResource();
			if (ormXmlResource == null) {
				errorMessage = JptUiMessages.JpaMakePersistentWizardPage_mappingFileDoesNotExistError;
			}
			else if (getJpaProject().getJpaFile(ormXmlResource.getFile()).rootStructureNodesSize() == 0) {
				errorMessage = JptUiMessages.JpaMakePersistentWizardPage_mappingFileNotListedInPersistenceXmlError;
			}
		}
		setErrorMessage(errorMessage);
		setPageComplete(errorMessage == null);
	}

	@Override
	public final void performHelp() {
	    PlatformUI.getWorkbench().getHelpSystem().displayHelp( this.helpContextId );
	}
	
	protected void performFinish() {
		boolean modifiedPersistenceXml = false;
		for (Type type : this.selectedTypes) {
			modifiedPersistenceXml |= type.makePersistent();			
		}
		if (modifiedPersistenceXml) {
			try {
				this.getJpaProject().getPersistenceXmlResource().save(null);
			}
			catch (IOException e) {
				//ignore, file just won't get saved
			}
		}
	}

	protected JpaXmlResource getOrmXmlResource() {
		return getJpaProject().getMappingFileXmlResource(new Path(getMappingFileLocation()));
	}

	protected EntityMappings getEntityMappings() {
		JpaXmlResource xmlResource = getOrmXmlResource();
		return (EntityMappings) getJpaProject().getJpaFile(xmlResource.getFile()).rootStructureNodes().next();
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

	private class Type {

		private final IType jdtType;

		private String mappingKey;

		protected Type(IType jdtType) {
			super();
			this.jdtType = jdtType;
			this.mappingKey = MappingKeys.ENTITY_TYPE_MAPPING_KEY;
		}

		protected void setMappingKey(String mappingKey) {
			this.mappingKey = mappingKey;
		}
		
		protected boolean makePersistent() {
			if (JpaMakePersistentWizardPage.this.isAnnotateInJavaModel()) {
				PersistenceUnit persistenceUnit = this.getPersistenceUnit();
				JavaResourcePersistentType persistentType = getJavaResourcePersistentType();
				persistentType.addAnnotation(getJavaTypeMappingDefinition(this.mappingKey).getAnnotationName());
				if (JpaMakePersistentWizardPage.this.isListInPersistenceXml()) {
					if (persistenceUnit != null) {
						persistenceUnit.addSpecifiedClassRef(persistentType.getQualifiedName());
						return true;
					}
				}
			}
			else {
				JpaXmlResource ormXmlResource = getOrmXmlResource();
				final EntityMappings entityMappings = getEntityMappings();
				ormXmlResource.modify(new Runnable() {
					public void run() {
						entityMappings.addPersistentType(Type.this.mappingKey, Type.this.jdtType.getFullyQualifiedName());
					}
				});
			}
			return false;
		}

		protected Iterable<? extends MappingUiDefinition<? extends PersistentType, ?>> typeMappingUiDefinitions() {
			return CollectionTools.iterable(getJpaPlatformUi().typeMappingUiDefinitions(jptResourceType));
		}

		protected JavaTypeMappingDefinition getJavaTypeMappingDefinition(String key) {
			for (JavaTypeMappingDefinition definition : getJpaProject().getJpaPlatform().getJavaTypeMappingDefinitions()) {
				if (Tools.valuesAreEqual(definition.getKey(), key)) {
					return definition;
				}
			}
			throw new IllegalArgumentException("Illegal type mapping key: " + key); //$NON-NLS-1$
		}

		protected JavaResourcePersistentType getJavaResourcePersistentType() {
			return getJpaProject().getJavaResourcePersistentType(this.jdtType.getFullyQualifiedName());
		}


		protected PersistenceUnit getPersistenceUnit() {
			Persistence p = this.getPersistence();
			if (p == null) {
				return null;
			}
			Iterator<PersistenceUnit> units = p.persistenceUnits();
			return units.hasNext() ? units.next() : null;
		}

		protected Persistence getPersistence() {
			PersistenceXml pxml = this.getPersistenceXml();
			return (pxml == null) ? null : pxml.getPersistence();
		}

		protected PersistenceXml getPersistenceXml() {
			JpaRootContextNode rcn = getJpaProject().getRootContextNode();
			return (rcn == null) ? null : rcn.getPersistenceXml();
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

}
