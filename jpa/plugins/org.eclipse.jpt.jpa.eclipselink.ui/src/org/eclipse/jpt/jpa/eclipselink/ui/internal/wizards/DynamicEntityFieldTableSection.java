/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.Path;
import org.eclipse.jem.util.emf.workbench.ProjectUtilities;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.common.utility.internal.Tools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.dialogs.AddVirtualAttributeDialog;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model.DynamicEntityField;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wst.common.componentcore.internal.operation.IArtifactEditOperationDataModelProperties;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

public class DynamicEntityFieldTableSection extends Composite {

	private IDataModel model;
	private Table mTableWidget = null;
	private TableViewer mTableViewer = null;
	private final int NAME_COLUMN = 0;
	private final int MAPPING_TYPE_COLUMN = 1;
	private final int ATTRIBUTE_TYPE_COLUMN = 2;
	private final int TARGET_TYPE_COLUMN = 3;
	private Button addButton;
	private Button editButton;
	private Button removeButton;
	private String propertyName;

	/**
	 * @param parent the main composite - DynamicEntityFieldsWizardPage
	 * @param model the data model representation
	 * @param propertyName data property name
	 */
	public DynamicEntityFieldTableSection(Composite parent, IDataModel model, String propertyName) {
		super(parent, SWT.NONE);	
		this.model = model;
		this.propertyName = propertyName;		

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 4;
		layout.marginWidth = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));

		// table widget
		mTableWidget = new Table(this, SWT.FULL_SELECTION | SWT.BORDER);
		mTableWidget.setHeaderVisible(true);
		mTableWidget.setLinesVisible(true);
		GridData data = new GridData(GridData.FILL_BOTH);
		data.verticalSpan = 2;
		mTableWidget.setLayoutData(data);

		TableColumn nameColumn = new TableColumn(mTableWidget, SWT.NONE);
		nameColumn.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_nameColumnLabel);

		TableColumn mappingTypeColumn = new TableColumn(mTableWidget, SWT.NONE);
		mappingTypeColumn.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_mappingTypeColumnLabel);

		TableColumn attrTypeColumn = new TableColumn(mTableWidget, SWT.NONE);
		attrTypeColumn.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_attributeTypeColumnLabel);

		TableColumn targetTypeColumn = new TableColumn(mTableWidget, SWT.NONE);
		targetTypeColumn.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_targetTypeColumnLabel);

		mTableViewer = new TableViewer(mTableWidget);
		mTableViewer.setContentProvider(new EntityRowContentProvider());
		mTableViewer.setLabelProvider(new EntityRowLabelProvider());
		mTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleEditButtonSelected();
				updatePrimaryKeyFieldProperty();
			}
		});

		// add, edit and remove buttons
		final Composite buttonComposition = new Composite(this, SWT.NULL);
		layout = new GridLayout();
		layout.marginHeight = 0;
		buttonComposition.setLayout(layout);
		buttonComposition.setLayoutData(new GridData(GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING));

		addButton = new Button(buttonComposition, SWT.PUSH);
		addButton.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_addButtonLabel); 
		addButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleAddButtonSelected();
				updatePrimaryKeyFieldProperty();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing
			}
		});

		editButton = new Button(buttonComposition, SWT.PUSH);
		editButton.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_editButtonLabel);
		editButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		editButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleEditButtonSelected();
				updatePrimaryKeyFieldProperty();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing
			}
		});
		editButton.setEnabled(false);

		removeButton = new Button(buttonComposition, SWT.PUSH);
		removeButton.setText(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_removeButtonLabel);
		removeButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		removeButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleRemoveButtonSelected();
				updatePrimaryKeyFieldProperty();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing
			}
		});
		removeButton.setEnabled(false);

		mTableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				ISelection selection = event.getSelection();
				if (editButton != null) {
					boolean enabled = ((IStructuredSelection) selection).size() == 1;
					editButton.setEnabled(enabled);
				}
				removeButton.setEnabled(!selection.isEmpty());
			}
		});

		this.addControlListener(new ColumnControlListener(buttonComposition));
	}


	/**
	 * This method process the Add... button pressed event. 
	 * It opens dialog to add a new dynamic entity field
	 */
	private void handleAddButtonSelected() {
		AddFieldDialog dialog = new AddFieldDialog(getShell(), this.getJpaProject());
		int result = dialog.open();
		if (result == Window.CANCEL) {
			return;
		}
		DynamicEntityField entityField = dialog.getEntityField();
		addEntityField(entityField);
	}

	/**
	 * Add the given dynamic entity field to the table
	 * 
	 * @param DynamicEntityField
	 * @return the DynamicEntityField which is to be added to the table
	 */
	private void addEntityField(DynamicEntityField field) {
		if (field == null)
			return;
		List<DynamicEntityField> valueList = (ArrayList<DynamicEntityField>) mTableViewer.getInput();		
		if (valueList == null)
			valueList = new ArrayList<DynamicEntityField>();		
		valueList.add(field);
		setInput(valueList);
	}

	/**
	 * This method process the Edit... button pressed event. 
	 * It opens dialog to edit a chosen dynamic entity field
	 */
	private void handleEditButtonSelected() {
		ISelection s = mTableViewer.getSelection();
		if (!(s instanceof IStructuredSelection))
			return;
		IStructuredSelection selection = (IStructuredSelection) s;
		if (selection.size() != 1)
			return;

		Object selectedObj = selection.getFirstElement();
		DynamicEntityField fieldForEdit = (DynamicEntityField) selectedObj;
		int index = mTableWidget.getSelectionIndex();

		EditFieldDialog dialog = new EditFieldDialog(getShell(), this.getJpaProject(), fieldForEdit);
		dialog.open();
		DynamicEntityField entityField = dialog.getEntityField();
		if (entityField != null) {			
			editEntityField(index, entityField);
		}
	}

	/**
	 * Edit a chosen dynamic entity field from the table
	 * @param index the index of the dynamic entity field in the table
	 * @param field the edited dynamic entity field
	 */
	private void editEntityField(int index, DynamicEntityField field) {
		if (field == null)
			return;
		List<DynamicEntityField> valueList = (ArrayList<DynamicEntityField>) mTableViewer.getInput();		
		if (valueList == null) {
			valueList = new ArrayList<DynamicEntityField>();
		}
		if (index == -1) {
			valueList.add(field);
		} else {
			valueList.set(index, field);
		}
		setInput(valueList);		
	}

	/**
	 * This method process the Remove button pressed event.
	 */
	private void handleRemoveButtonSelected() {
		ISelection selection = mTableViewer.getSelection();
		if (selection.isEmpty() || !(selection instanceof IStructuredSelection))
			return;
		List<DynamicEntityField> selectedObject = ((IStructuredSelection) selection).toList();
		removeEntityField(selectedObject);
	}

	/**
	 * Removes the selected dynamic entity field(s) from the table
	 * @param fields list of dynamic entity fields which should be removed
	 */
	private void removeEntityField(Collection<DynamicEntityField> fields) {
		List<DynamicEntityField> valueList = (ArrayList<DynamicEntityField>) mTableViewer.getInput();
		valueList.removeAll(fields);
		setInput(valueList);
	}

	/**
	 * Set the input of the table
	 * @param input the list of dynamic entity fields which have to be presented in the table
	 */
	private void setInput(List<DynamicEntityField> input) {
		mTableViewer.setInput(input);
		// Create a new list to trigger property change
		ArrayList<DynamicEntityField> newInput = new ArrayList<DynamicEntityField>();
		newInput.addAll(input);
		model.setProperty(propertyName, newInput);		
	}

	/**
	 * @return the TableViewer of the table
	 */
	public TableViewer getTableViewer() {
		return mTableViewer;
	}

	/**
	 * Update PK fields property when any field(s) added to, edited in, or removed from the field table viewer
	 */
	private void updatePrimaryKeyFieldProperty() {
		List<DynamicEntityField> pkFields = new ArrayList<DynamicEntityField>();
		TableItem[] children = mTableViewer.getTable().getItems();
		for (int i = 0; i < children.length; i++) {
			TableItem item = children[i];
			DynamicEntityField field = (DynamicEntityField)item.getData();
			if (field.getMappingType().getKey().equals(MappingKeys.ID_ATTRIBUTE_MAPPING_KEY)) {
				field.setKey(true);
				pkFields.add(field);
			}
		}
		DynamicEntityFieldTableSection.this.model.setProperty(IEntityDataModelProperties.PK_FIELDS, pkFields);
	}

	// ********** Control listener for field columns **************

	private final class ColumnControlListener extends ControlAdapter {
		private final Composite buttonComposition;

		private ColumnControlListener(Composite buttonComposition) {
			this.buttonComposition = buttonComposition;
		}

		@Override
		public void controlResized(ControlEvent e) {
			Table table = mTableViewer.getTable();
			Rectangle area = table.getParent().getClientArea();
			Point preferredSize = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			TableColumn[] columns = table.getColumns();
			Point buttonArea = buttonComposition.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			int width = area.width - 3 * table.getBorderWidth();
			if (preferredSize.y > area.height + table.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = table.getVerticalBar().getSize();
				width -= vBarSize.x;
			}
			Point oldSize = table.getSize();
			int consumeWidth = 0;
			for (int i = 0; i < columns.length; i++) {
				if (oldSize.x > area.width) {
					// table is getting smaller so make the columns
					// smaller first and then resize the table to
					// match the client area width
					consumeWidth = setColumntWidth(width, columns, consumeWidth, i);
					table.setSize(area.width - buttonArea.x, area.height);
				} else {
					// table is getting bigger so make the table
					// bigger first and then make the columns wider
					// to match the client area width
					consumeWidth = setColumntWidth(width, columns, 	consumeWidth, i);
					table.setSize(area.width - buttonArea.x, area.height);
				}
			}
		}

		private int setColumntWidth(int width, TableColumn[] columns, int consumeWidth, int i) {
			columns[i].setWidth(width / (columns.length + 1));
			consumeWidth += columns[i].getWidth();
			return consumeWidth;
		}
	}

	// ********** Providers for the dynamic entity field table viewer **************

	/**
	 * The content provider for the table items
	 */
	protected class EntityRowContentProvider implements IStructuredContentProvider {
		public boolean isDeleted(Object element) {
			return false;
		}
		public Object[] getElements(Object element) {
			return (element instanceof List) ?
					((List<?>) element).toArray() :
					Tools.EMPTY_OBJECT_ARRAY;
		}
		public void inputChanged(Viewer aViewer, Object oldInput, Object newInput) {
			//Default nothing
		}
		public void dispose() {
			//Default nothing
		}
	}

	/**
	 * The label provider for the table items
	 */
	protected class EntityRowLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			DynamicEntityField entity = (DynamicEntityField) element;
			if (columnIndex == NAME_COLUMN) {
				return entity.getName();
			}
			if (columnIndex == MAPPING_TYPE_COLUMN) {
				return entity.getMappingType().getLabel();
			}
			if (columnIndex == ATTRIBUTE_TYPE_COLUMN) {
				return entity.getFqnAttributeType();
			}
			if (columnIndex == TARGET_TYPE_COLUMN) {
				return entity.getFqnTargetType();
			}		
			return "";
		}

		@Override
		public Image getImage(Object element) {
			return null;
		}

		@Override
		public String getText(Object element) {
			String[] array = (String[]) element;
			if (array.length > 0) {
				return array[0];
			}
			return super.getText(element);
		}
	}

	// ********* Add dynamic entity field dialog ******************

	/**
	 * The dialog used to collect information for a new dynamic entity field
	 * Information includes name, mapping type, attribute type and target type
	 */
	private class AddFieldDialog extends AddVirtualAttributeDialog {
		
		protected DynamicEntityField entityField;
		protected final JpaProject jpaProject;

		/**
		 * Constructs AddFieldDialog
		 * @param shell
		 * @param jpaProject the selected jpaProject
		 */
		public AddFieldDialog(Shell shell, JpaProject jpaProject) {
			super(shell, null);
			this.jpaProject = jpaProject;
			setTitle(EclipseLinkUiMessages.DynamicEntityFieldsWizardPage_addDynamicEntityFieldDialog_title);
		}

		/* Create the area of dialog
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);
			return composite;
		}

		/* Processes OK button pressed event
		 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
		 */
		@Override
		protected void okPressed() {
			/**
			 * @see org.eclipse.jpt.jpa.eclipselink.ui.internal.entity.data.model.DynamicEntityField
			 */
			DynamicEntityField field = new DynamicEntityField();
			field.setName(this.nameText.getText());
			field.setMappingType(this.getMappingUiDefinition(this.mappingCombo));
			String mappingKey = field.getMappingType().getKey();
			
			if (this.getAttributeMappingDefinition(mappingKey).isSingleRelationshipMapping()) {
				// reset the unnecessary value to empty string to reduce confusion
				field.setFqnAttributeType("");
			} else {
				field.setFqnAttributeType(this.attributeTypeText.getText());
			}
			
			if (!this.getAttributeMappingDefinition(mappingKey).isSingleRelationshipMapping() &&
					!this.getAttributeMappingDefinition(mappingKey).isCollectionMapping()) {
				// reset the unnecessary value to empty string to reduce confusion
				field.setFqnTargetType("");
			} else {
				field.setFqnTargetType(this.targetTypeText.getText());
			}
			entityField = field;

			setReturnCode(OK);
			close();
		}

		private MappingUiDefinition<?,?> getMappingUiDefinition(ComboViewer mappingType) {
			StructuredSelection selection = (StructuredSelection) mappingType.getSelection();
			return (selection.isEmpty()) ? null : (MappingUiDefinition<?,?>) selection.getFirstElement();
		}

		/**
		 * @return the dynamic entity field representation with the information collected from the dialog
		 */
		public DynamicEntityField getEntityField() {
			return this.entityField;
		}

		// ********** override super class methods ************
		
		@Override
		protected JpaProject getJpaProject() {
			return this.jpaProject;
		}

		@Override
		protected JptResourceType getJptResourceType() {
			String xmlName = model.getStringProperty(IEntityDataModelProperties.XML_NAME);
			return this.getOrmXmlResource(xmlName).getResourceType();
		}

		protected JptXmlResource getOrmXmlResource(String xmlName) {
			return this.jpaProject == null ? null : this.jpaProject.getMappingFileXmlResource(new Path(xmlName));
		}
	}

	// ********* Edit dynamic entity field dialog ******************

	/**
	 * The dialog used to edit an existing dynamic entity field
	 */
	private class EditFieldDialog extends AddFieldDialog {

		public EditFieldDialog(Shell shell, JpaProject jpaProject, DynamicEntityField field) {
			super(shell, jpaProject);
			this.entityField = field;		
		}

		/* Create the area of the dialog
		 */
		@Override
		public Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);

			this.nameText.setText(this.entityField.getName());
			this.mappingCombo.setSelection(new StructuredSelection(this.entityField.getMappingType()));
			this.attributeTypeText.setText(this.entityField.getFqnAttributeType());
			this.targetTypeText.setText(this.entityField.getFqnTargetType());

			return composite;
		}
	}

	//************* misc **************

	protected JpaProject getJpaProject() {
		return (JpaProject) getIProject().getAdapter(JpaProject.class);
	}

	protected IProject getIProject() {
		String projectName = model.getStringProperty(IArtifactEditOperationDataModelProperties.PROJECT_NAME);
		return ProjectUtilities.getProject(projectName);
	}
}