/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.ui.internal.wizards.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.search.IJavaSearchConstants;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.EntityRow;
import org.eclipse.jpt.ui.internal.wizards.entity.data.model.IEntityDataModelProperties;
import org.eclipse.jst.j2ee.internal.common.operations.INewJavaClassDataModelProperties;
import org.eclipse.jst.j2ee.internal.dialogs.TypeSearchEngine;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wst.common.frameworks.datamodel.IDataModel;

/**
 * The class presents the table with entity fields. In the java file are included also content
 * and label provider, add and edit entity dialogs as well as help internal objects - listener 
 * and callback from the dialog to the main composite (table). 
 *
 */
public class EntityRowTableWizardSection extends Composite {
	
	/**
	 * The possible entity types, mentioned in the specification (Chapter 2.1.1 Persistent Fields and Properties p.20)
	 */
	protected final static String[] VALID_TYPES = {"int", "long", "short", "char", "boolean", "byte", "double", "float", "java.lang.String",
		"byte[]", "char[]",	"java.lang.Byte[]", "java.lang.Character[]", "java.math.BigDecimal", "java.math.BigInteger", "java.sql.Date", "java.sql.Time", "java.sql.Timestamp",
		"java.util.Date", "java.util.Calendar"};
	
	  private CheckboxTableViewer mTableViewer = null;
	  private Table mTableWidget = null;
	  private final int PK_COLUMN = 0;
	  private final int NAME_COLUMN = 1;
	  private final int TYPE_COLUMN = 2;
	
	  

	
	private Button addButton;
	private Button editButton;
	private Button removeButton;
	private String title = EntityWizardMsg.ENTITY_FIELDS;
	private String[] labelsForCombo = VALID_TYPES;
	private String[] labelsForText = new String[]{EntityWizardMsg.TYPE, EntityWizardMsg.NAME};
	private IDataModel model;
	private String propertyName;
	private Image labelProviderImage = null;
	private DialogCallback callback;	


	/**
	 * @param parent the main composite - Entity fields page
	 * @param model the data model representation
	 * @param propertyName data property name
	 */
	public EntityRowTableWizardSection(Composite parent, IDataModel model, String propertyName) {
		super(parent, SWT.NONE);	
		this.model = model;
		this.propertyName = propertyName;		

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 4;
		layout.marginWidth = 0;
		this.setLayout(layout);
		this.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label titleLabel = new Label(this, SWT.LEFT);
		titleLabel.setText(title);
		GridData data = new GridData();
		data.horizontalSpan = 2;
		titleLabel.setLayoutData(data);

		mTableWidget = new Table(this, SWT.CHECK | SWT.FULL_SELECTION | SWT.BORDER);
        mTableWidget.setHeaderVisible(true);
        mTableWidget.setLinesVisible(true);

        mTableViewer = new CheckboxTableViewer(mTableWidget);
        data = new GridData(GridData.FILL_BOTH);
        data.verticalSpan = 2;
		mTableWidget.setLayoutData(data);
		mTableViewer.setContentProvider(new EntityRowContentProvider());
		mTableViewer.setLabelProvider(new EntityRowLabelProvider());
		
		final Composite buttonComposition = new Composite(this, SWT.NULL);
		layout = new GridLayout();
		layout.marginHeight = 0;
		buttonComposition.setLayout(layout);
		buttonComposition.setLayoutData(new GridData(GridData.FILL_VERTICAL | GridData.VERTICAL_ALIGN_BEGINNING));

		addButton = new Button(buttonComposition, SWT.PUSH);
		addButton.setText(EntityWizardMsg.ADD_BUTTON_LABEL); 
		addButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleAddButtonSelected();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing
			}
		});

		editButton = new Button(buttonComposition, SWT.PUSH);
		editButton.setText(EntityWizardMsg.EDIT_BUTTON_LABEL);
		editButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		editButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleEditButtonSelected();
			}
			public void widgetDefaultSelected(SelectionEvent event) {
				//Do nothing
			}
		});
		editButton.setEnabled(false);
		mTableViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				handleEditButtonSelected();
			}
		});

		removeButton = new Button(buttonComposition, SWT.PUSH);
		removeButton.setText(EntityWizardMsg.REMOVE_BUTTON_LABEL);
		removeButton.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
		removeButton.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent event) {
				handleRemoveButtonSelected();
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
		
		
		final TableColumn pkColumn = new TableColumn(mTableWidget, SWT.CHECK);
        pkColumn.setText(EntityWizardMsg.KEY);
		pkColumn.pack();
		pkColumn.setResizable(false);

        TableColumn nameColumn = new TableColumn(mTableWidget, SWT.NONE);
        nameColumn.setText(EntityWizardMsg.NAME);

        TableColumn typeColumn = new TableColumn(mTableWidget, SWT.NONE);
        typeColumn.setText(EntityWizardMsg.TYPE);

        this.addControlListener(new ControlAdapter() {
        	@Override
			public void controlResized(ControlEvent e) {
        		Table table = mTableViewer.getTable();
                    TableColumn[] columns = table.getColumns();
				Point buttonArea = buttonComposition.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				Rectangle area = table.getParent().getClientArea();
				Point preferredSize = mTableViewer.getTable().computeSize(SWT.DEFAULT, SWT.DEFAULT);
				int width = area.width - 2 * table.getBorderWidth()- buttonArea.x - columns.length * 2 - pkColumn.getWidth();
				if (preferredSize.y > area.height + table.getHeaderHeight()) {
					// Subtract the scrollbar width from the total column width
					// if a vertical scrollbar will be required
					Point vBarSize = table.getVerticalBar().getSize();
					width -= vBarSize.x;
				}
				Point oldSize = table.getSize();
				int consumeWidth = 0;
				int col = columns.length - 1;
				for (int i = 1; i < columns.length; i++) {
					if (oldSize.x > area.width) {
						// table is getting smaller so make the columns
						// smaller first and then resize the table to
						// match the client area width
						consumeWidth = setColumntWidth(width, columns, consumeWidth, i);
						table.setSize(area.width - buttonArea.x	- (col * 2 + pkColumn.getWidth()), area.height);
					} else {
						// table is getting bigger so make the table
						// bigger first and then make the columns wider
						// to match the client area width
						consumeWidth = setColumntWidth(width, columns, 	consumeWidth, i);
						table.setSize(area.width - buttonArea.x - (col * 2 + pkColumn.getWidth()), area.height);
					}
				}
			}

			private int setColumntWidth(int width, TableColumn[] columns,
					int consumeWidth, int i) {
				if (i < columns.length - 1) {
					columns[i].setWidth(width / (columns.length - 1));
					consumeWidth += columns[i].getWidth();
				} else {
					columns[i].setWidth(width - consumeWidth);
				}
				return consumeWidth;
			}
		});
        
		mTableViewer.addCheckStateListener(new PKFieldCheckStateListener());
		callback = new FieldDialogCallback();
	}

	/**
	 * This method process the Add... button pressed event. It opens dialog to
	 * add new entity field
	 */
	private void handleAddButtonSelected() {
		AddFieldDialog dialog = new AddFieldDialog(getShell(), title, labelsForCombo, labelsForText);
		dialog.open();
		EntityRow entityRow = dialog.getEntityRow();
		addEntityRow(entityRow);
		if (!entityRow.couldBeKey()) {
			mTableViewer.setGrayed(entityRow, true);
		}		
	}
	
	/**
	 * Add new entity to the table input
	 * 
	 * @param entity
	 *            the entity which have to be added to the table
	 */
	private void addEntityRow(EntityRow entity) {
		if (entity == null)
			return;
		List<EntityRow> valueList = (ArrayList<EntityRow>) mTableViewer.getInput();		
		if (valueList == null)
			valueList = new ArrayList<EntityRow>();		
		valueList.add(entity);
		setInput(valueList);
	}
	
	/**
	 * This method process the Edit... button pressed event. It opens dialog to edit chosen entity field
	 */
	private void handleEditButtonSelected() {
		ISelection s = mTableViewer.getSelection();
		if (!(s instanceof IStructuredSelection))
			return;
		IStructuredSelection selection = (IStructuredSelection) s;
		if (selection.size() != 1)
			return;
		
		Object selectedObj = selection.getFirstElement();
		EntityRow entityForEdit = (EntityRow) selectedObj;
		int index = mTableWidget.getSelectionIndex();
		boolean isChecked = mTableViewer.getChecked(entityForEdit);
		
		EditFieldDialog dialog = new EditFieldDialog(getShell(), title, labelsForCombo, labelsForText, entityForEdit);
		dialog.open();
		EntityRow entityRow = dialog.getEntityRow();
		if (entityRow != null) {			
			editEntityRow(index, entityRow);
			mTableViewer.setChecked(entityRow, isChecked);
			if (!entityRow.couldBeKey()) {
				mTableViewer.setChecked(entityRow, false);
				mTableViewer.setGrayed(entityRow, true);
			} else {				
				mTableViewer.setGrayed(entityRow, false);
			}
		}
	}
	
	/**
	 * Edit chosen entity from the table
	 * @param index the index of the entity in the table
	 * @param newEntity the edited entity field
	 */
	private void editEntityRow(int index, EntityRow newEntity) {
		if (newEntity == null)
			return;
		
		List<EntityRow> valueList = (ArrayList<EntityRow>) mTableViewer.getInput();		
		if (valueList == null) {
			valueList = new ArrayList();
		}
				
		if (index == -1) {
			valueList.add(newEntity);
		} else {
			valueList.set(index, newEntity);
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
		List selectedObject = ((IStructuredSelection) selection).toList();
		removeEntityRow(selectedObject);		
	}
	
	/**
	 * Removes the selected entities from the table 
	 * @param entities list with entities, which should be removed
	 */
	private void removeEntityRow(Collection entities) {
		List<EntityRow> valueList = (ArrayList<EntityRow>) mTableViewer.getInput();
		valueList.removeAll(entities);
		setInput(valueList);
	}

	/**
	 * Set the input of the table
	 * @param input the list with entities which have to be presented in the table
	 */
	private void setInput(List input) {
		mTableViewer.setInput(input);
		// Create a new list to trigger property change
		ArrayList<EntityRow> newInput = new ArrayList<EntityRow>();
		newInput.addAll(input);
		model.setProperty(propertyName, newInput);		
	}

	/**
	 * @return the TableViewer of the table
	 */
	public TableViewer getTableViewer() {
		return mTableViewer;
	}
	
	// PROVIDERS FOR THE FIELD TABLE
	
	/**
	 * The content provider for the table items
	 */
	protected class EntityRowContentProvider implements IStructuredContentProvider {
		public boolean isDeleted(Object element) {
			return false;
		}
		public Object[] getElements(Object element) {
			if (element instanceof List) {
				return ((List) element).toArray();
			}
			return new Object[0];
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
		    if (columnIndex == 0) {
		        return labelProviderImage;       
		    }
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			EntityRow entity = (EntityRow) element;
			if (columnIndex == NAME_COLUMN) {
				return entity.getName();
			}
			if (columnIndex == TYPE_COLUMN) {
				return entity.getType();
			}		
			if (entity.couldBeKey()) {
				mTableViewer.setChecked(entity, entity.isKey());
			} 
			return "";
		}

        @Override
        public Image getImage(Object element) {
            return labelProviderImage;
        }

        @Override
        public String getText(Object element) {
			String[] array = (String[]) element;
			if (array.length > 0) {
				return array[0];
			} else {
				return super.getText(element);
			}
		}
	}	
	// END - PROVIDERS FOR THE FIELD TABLE	

	/**
	 * Listener for the Primary Key check box in the table item
	 */
	private class PKFieldCheckStateListener implements ICheckStateListener {

		public void checkStateChanged(CheckStateChangedEvent event) {
			Object[] checkedElements = mTableViewer.getCheckedElements();
			List<String> pkFields = new ArrayList<String>();
			for (Object object : checkedElements) {
				EntityRow entity = (EntityRow)object;
				if (entity.couldBeKey()) {					
					pkFields.add(entity.getName());
				} else {
					mTableViewer.setChecked(entity, false);
				}
			}			
			model.setProperty(IEntityDataModelProperties.PK_FIELDS, pkFields);			
		}
		
	}
	
	// CALLBACK MECHANISM
	/**
	 * Callback interface used by the Add/Edit-FieldDialog classes. 
	 */
	public interface DialogCallback {
		
		/**	
		 * Validates the text fields. 
		 * <p>Used to decide whether to enable the OK button of the dialog. 
		 * If the method returns <code>true</code> the OK button is enabled, 
		 * otherwise the OK button is disabled.</p> 
		 * @param combo contains the predefined types
		 * @param texts	the name of the entity field	
		 * @return <code>true</code> if the values in the text fields are 
		 *         valid, <code>false</code> otherwise.	 
		 */
		public boolean validate(Combo combo, Text[] texts);
		
		/**
		 * Retrieves the entity presentation object from the fields of the dialog. 
		 * <p>Implementers of the callback can use these method to do some 
		 * preprocessing (like trimming) of the data in the text fields before 
		 * using it. The returned values will be the actual data that will be 
		 * put in the table viewer.</p> 
		 * @param combo contains the predefined types
		 * @param texts	the name of the entity field	
		 * @return the entity presentation object retrieved from the dialog
		 */
		public EntityRow retrieveResultStrings(Combo combo, Text[] texts);
		
	}
	
	/**
	 * Implementation of the <code>FieldDialogCallback</code> interface for 
	 * both "Name" and "Types" table columns. 
	 */
	public class FieldDialogCallback implements DialogCallback {

		/**
		 * The first text field should not be empty. 
		 */
		public boolean validate(Combo combo, Text[] texts) {
			if (texts.length > 0) {
				return texts[0].getText().trim().length() > 0;
			}
			return true;
		}
		
		/**
		 * Just retrieves the unmodified values of the text fields as a 
		 * entity field presentation
		 * @see org.eclipse.jpt.ui.internal.wizards.entity.data.model.EntityRow
		 */
		public EntityRow retrieveResultStrings(Combo combo, Text[] texts) {
			EntityRow entity = new EntityRow();			
			entity.setFqnTypeName(combo.getText());
			entity.setName(texts[0].getText());
			return entity;
		}
	}
	
	// THE DIALOGS USED FOR ADD/EDIT OF ENTITY FIELDS - BEGIN
	
	/**
	 * The dialog which collect the information (name and type) for the new entity field
	 */
	private class AddFieldDialog extends Dialog implements ModifyListener, SelectionListener {
		protected String windowTitle;
		protected String[] labelsForCombo;
		protected String[] labelsForText;
		protected Text[] texts;		
		protected EntityRow entityRow;
		protected Combo combo;
		
		/**
		 * Constructs AddFieldDialog
		 * @param shell
		 * @param windowTitle dialog label
		 * @param labelsForCombo the elements for the combo
		 * @param labelsForText name text
		 */
		public AddFieldDialog(Shell shell, String windowTitle, String[] labelsForCombo, String[] labelsForText) {
			super(shell);
			this.windowTitle = windowTitle;
			this.labelsForCombo = labelsForCombo;
			this.labelsForText  = labelsForText;
		}
		
		/* Create the area of dialog
		 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public Control createDialogArea(Composite parent) {

			Composite composite = (Composite) super.createDialogArea(parent);
			getShell().setText(windowTitle);

			GridLayout layout = new GridLayout();
			layout.numColumns = 4;
			composite.setLayout(layout);
			GridData data = new GridData();
			data.verticalAlignment = GridData.FILL;
			data.horizontalAlignment = GridData.FILL;
			data.widthHint = 300;
			composite.setLayoutData(data);
			
			Label label = new Label(composite, SWT.LEFT);
			label.setText(labelsForText[0]);
			label.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
			
			
			combo = new Combo(composite, SWT.SINGLE | SWT.BORDER);// | SWT.READ_ONLY);			
			combo.setItems(labelsForCombo);
			data = new GridData(GridData.FILL_HORIZONTAL);
			data.horizontalSpan = 2;			
			combo.setLayoutData(data);
			
			Button browseButton = new Button(composite, SWT.PUSH);
			browseButton.setText(EntityWizardMsg.BROWSE_BUTTON_LABEL);
			GridData browseButtonData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			browseButtonData.horizontalSpan = 1;
			browseButton.setLayoutData(browseButtonData);
			browseButton.addSelectionListener(new SelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					handleChooseEntityTypeButtonPressed();
				}

				public void widgetDefaultSelected(SelectionEvent e) {
					// Do nothing
				}
			});

			int n = labelsForText.length;
			texts = new Text[n-1];
			for (int i = 1; i < n; i++) {
				Label labelI = new Label(composite, SWT.LEFT);
				labelI.setText(labelsForText[i]);
				labelI.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
				texts[i-1] = new Text(composite, SWT.SINGLE | SWT.BORDER);
				data = new GridData(GridData.FILL_HORIZONTAL);
				data.widthHint = 100;
				texts[i-1].setLayoutData(data);
			}

			combo.setFocus();
			Dialog.applyDialogFont(parent);
			return composite;
		}

		/**
		 * Process browsing when the Browse... button have been pressed. Allow adding of entity field
		 * with arbitrary type.
		 * @see org.eclipse.jdt.internal.ui.dialogs.FilteredTypesSelectionDialog
		 */
		private void handleChooseEntityTypeButtonPressed() {
			//getControl().setCursor(new Cursor(getShell().getDisplay(), SWT.CURSOR_WAIT));
			IPackageFragmentRoot packRoot = (IPackageFragmentRoot) model.getProperty(INewJavaClassDataModelProperties.JAVA_PACKAGE_FRAGMENT_ROOT);
			if (packRoot == null) {
				return;
			}

			// this eliminates the non-exported classpath entries
			final IJavaSearchScope scope = TypeSearchEngine.createJavaSearchScopeForAProject(packRoot.getJavaProject(), true, true);

			// This includes all entries on the classpath.
			FilteredTypesSelectionDialog dialog = new FilteredTypesSelectionDialog(getShell(), false, null/*getWizard().getContainer()*/, scope, IJavaSearchConstants.TYPE);
			dialog.setTitle(EntityWizardMsg.TYPE_DIALOG_TITLE);
			dialog.setMessage(EntityWizardMsg.TYPE_DIALOG_DESCRIPTION);

			if (dialog.open() == Window.OK) {
				IType type = (IType) dialog.getFirstResult();
				String superclassFullPath = IEntityDataModelProperties.EMPTY_STRING;
				if (type != null) {
					superclassFullPath = type.getFullyQualifiedName();
				}
				combo.setText(superclassFullPath);
				//getControl().setCursor(null);
				return;
			}
			//getControl().setCursor(null);
		}
		
		
		/* Create the content of the dialog
		 * @see org.eclipse.jface.dialogs.Dialog#createContents(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		protected Control createContents(Composite parent) {
			Composite composite = (Composite) super.createContents(parent);
			
			combo.addSelectionListener(this);
			for (int i = 0; i < texts.length; i++) {
				texts[i].addModifyListener(this);
			}
			
			updateOKButton();
			
			return composite;
		}

		/* Processes OK button pressed event.
		 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
		 */
		@Override
		protected void okPressed() {
			entityRow = callback.retrieveResultStrings(combo, texts);
			super.okPressed();
		}

		/**
		 * @return the entity representation with the information collected from the dialog
		 */
		public EntityRow getEntityRow() {
			return entityRow;
		}
		
		/* Processes text modifying event
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 */
		public void modifyText(ModifyEvent e) {
			updateOKButton();
		}
		
		/**
		 * Sets state of the OK button in accordance with validate method of the callback object
		 * @see DialogCallback
		 */
		private void updateOKButton() {
			getButton(IDialogConstants.OK_ID).setEnabled(callback.validate(combo, texts));
		}
		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
		/* Update OK button when the appropriate event occurs
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		public void widgetSelected(SelectionEvent e) {
			updateOKButton();			
		}
	}
	
	/**
	 * Constructs EditFieldDialog
	 */
	private class EditFieldDialog extends AddFieldDialog {
		protected EntityRow entityRow;
		/**
		 * EditFieldDialog constructor comment.
		 */
		public EditFieldDialog(Shell shell, String windowTitle, String[] labelsForCombo, String[] labelsForText, EntityRow entity) {
			super(shell, windowTitle, labelsForCombo, labelsForText);
			this.entityRow = entity;		
		}

		/* Create the area of the dialog
		 * @see org.eclipse.jpt.ui.internal.wizards.entity.EntityRowTableWizardSection.AddFieldDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
		 */
		@Override
		public Control createDialogArea(Composite parent) {

			Composite composite = (Composite) super.createDialogArea(parent);

			combo.setText(entityRow.getFqnTypeName());
			texts[0].setText(entityRow.getName());
			
			return composite;
		}
	}	

}
