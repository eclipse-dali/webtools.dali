/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this
 *  distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * The abstract definition of a pane that has add/remove buttons, up/down
 * buttons and an option button that is kept in sync with the selected items.
 *
 * @version 1.0
 * @since 2.0
 */
public abstract class AddRemovePane<T extends Model> extends AbstractPane<T>
{
	private Adapter adapter;
	private Button addButton;
	private Composite container;
	private ILabelProvider labelProvider;
	private ListValueModel listHolder;
	private Button optionalButton;
	private Button removeButton;
	private WritablePropertyValueModel<Object> selectedItemHolder;
	private ObjectListSelectionModel selectionModel;

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	protected AddRemovePane(AbstractPane<? extends T> parentPane,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        WritablePropertyValueModel<?> selectedItemHolder,
	                        ILabelProvider labelProvider) {

		this(parentPane,
		     parent,
		     adapter,
		     listHolder,
		     selectedItemHolder,
		     labelProvider,
		     null);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	protected AddRemovePane(AbstractPane<? extends T> parentPane,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        WritablePropertyValueModel<?> selectedItemHolder,
	                        ILabelProvider labelProvider,
	                        String helpId) {

		super(parentPane, parent);

		initialize(
			adapter,
			listHolder,
			selectedItemHolder,
			labelProvider
		);

		initializeLayout(
			adapter,
			listHolder,
			selectedItemHolder,
			labelProvider,
			helpId
		);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 */
	protected AddRemovePane(AbstractPane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        WritablePropertyValueModel<?> selectedItemHolder,
	                        ILabelProvider labelProvider) {

		this(parentPane,
		     subjectHolder,
		     parent,
		     adapter,
		     listHolder,
		     selectedItemHolder,
		     labelProvider,
		     null);
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane
	 */
	protected AddRemovePane(AbstractPane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        WritablePropertyValueModel<?> selectedItemHolder,
	                        ILabelProvider labelProvider,
	                        String helpId) {

		super(parentPane, subjectHolder, parent);

		initialize(
			adapter,
			listHolder,
			selectedItemHolder,
			labelProvider
		);

		initializeLayout(
			adapter,
			listHolder,
			selectedItemHolder,
			labelProvider,
			helpId
		);
	}

	/**
	 * @category Add
	 */
	protected void addItem() {
		adapter.addNewItem(selectionModel);
	}

	/**
	 * @category Initialize
	 */
	protected Adapter buildAdapter() {
		return adapter;
	}

	/**
	 * @category Add
	 */
	protected Button buildAddButton(Composite parent) {
		return buildButton(
			parent,
			adapter.addButtonText(),
			buildAddItemAction()
		);
	}

	/**
	 * @category Add
	 */
	private Runnable buildAddItemAction() {
		return new Runnable() {
			public void run() {
				AddRemovePane.this.addItem();
			}
		};
	}

//	private ListChangeListener/*<Object>*/ buildListChangeListener() {
//		return new ListChangeAdapter/*<Object>*/() {
//			@Override
//			public void listChanged(ListChangeEvent/*<Object>*/ e) {
//				AddRemovePane.this.updateButtons();
//			}
//		};
//	}

	/**
	 * @category Option
	 */
	private Runnable buildOptionalAction() {
		return new Runnable() {
			public void run() {
			}
		};
	}

	/**
	 * @category Option
	 */
	protected Button buildOptionalButton(Composite container) {
		return buildButton(
			container,
			adapter.optionalButtonText(),
			buildOptionalAction()
		);
	}

	/**
	 * @category Add
	 */
	protected Button buildRemoveButton(Composite parent) {
		return buildButton(
			parent,
			adapter.removeButtonText(),
			buildRemoveItemsAction()
		);
	}

	/**
	 * @category Remove
	 */
	private Runnable buildRemoveItemsAction() {
		return new Runnable() {
			public void run() {
				AddRemovePane.this.removeItems();
			}
		};
	}

	protected ObjectListSelectionModel buildRowSelectionModel(ListValueModel/*<?>*/ listModel) {
		return new ObjectListSelectionModel(new ListModelAdapter(listModel));
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		updateButtons();
	}

	protected final Composite getContainer() {
		return container;
	}

	protected final ILabelProvider getLabelProvider() {
		return labelProvider;
	}

	protected final ListValueModel/*<?>*/ getListHolder() {
		return listHolder;
	}

	protected final WritablePropertyValueModel<Object> getSelectedItemHolder() {
		return selectedItemHolder;
	}

	public final ObjectListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	@SuppressWarnings("unchecked")
	protected void initialize(Adapter adapter,
	                          ListValueModel/*<?>*/ listHolder,
	                          WritablePropertyValueModel<?> selectedItemHolder,
	                          ILabelProvider labelProvider)
	{
		this.listHolder         = listHolder;
		this.labelProvider      = labelProvider;
		this.adapter            = (adapter == null) ? buildAdapter() : adapter;
		this.selectedItemHolder = (WritablePropertyValueModel<Object>) selectedItemHolder;
		this.selectionModel     = new ObjectListSelectionModel(new ListModelAdapter(listHolder));
	}

	protected void initializeButtonPane(Composite container, String helpId) {

		container = buildPane(container);

		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		container.setLayout(layout);
		container.setLayoutData(new GridData());

		// Add button
		addButton = buildAddButton(container);
		addAlignRight(addButton);

		// Optional button
		if (adapter.hasOptionalButton()) {
			optionalButton = buildOptionalButton(container);
			addAlignRight(optionalButton);
		}

		// Remove button
		removeButton = buildRemoveButton(container);
		addAlignRight(removeButton);

		// Update the help topic ID
		if (helpId != null) {
			helpSystem().setHelp(addButton, helpId);
			helpSystem().setHelp(removeButton, helpId);

			if (optionalButton != null) {
				helpSystem().setHelp(optionalButton, helpId);
			}
		}

//		upButton       = buildUpButton((adapter instanceof UpDownAdapter) ? (UpDownAdapter) adapter : null);
//		downButton     = buildDownButton((adapter instanceof UpDownAdapter) ? (UpDownAdapter) adapter : null);
//		gotoButton     = buildGotoButton();
//		component      = buildComponent();
	}

	protected void initializeLayout(Adapter adapter,
    	                             ListValueModel/*<?>*/ listHolder,
   	                             WritablePropertyValueModel<?> selectedItemHolder,
   	                             ILabelProvider labelProvider,
   	                             String helpId) {

		initializeMainComposite(
			container,
			adapter,
			listHolder,
			selectedItemHolder,
			labelProvider,
			helpId);

		initializeButtonPane(container, helpId);
		enableWidgets(subject() != null);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		this.container = buildSubPane(container, 2, 0, 0, 0, 0);
	}

	/**
	 * Initializes the main widget of this add/remove pane.
	 *
	 * @param container The parent container
	 * @param adapter This <code>Adapter</code> is used to dictacte the behavior
	 * of this <code>AddRemovePane</code> and by delegating to it some of the
	 * behavior
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 * @param labelProvider The renderer used to format the list holder's items
	 * @param helpId The topic help ID to be registered with this pane or
	 * <code>null</code> if it was not specified
	 */
	protected abstract void initializeMainComposite(Composite container,
	                                                Adapter adapter,
	                   	                           ListValueModel/*<?>*/ listHolder,
	                  	                           WritablePropertyValueModel<?> selectedItemHolder,
	                  	                           ILabelProvider labelProvider,
	                  	                           String helpId);

	/**
	 * @category Remove
	 */
	protected void removeItems() {
		adapter.removeSelectedItems(selectionModel);
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateAddButton(Button addButton) {
		addButton.setEnabled(
			getControl().isEnabled() &&
			(subject() != null)
		);
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateButtons() {
		if (container.isDisposed()) {
			return;
		}

		updateAddButton(addButton);
		updateRemoveButton(removeButton);
		updateOptionalButton(optionalButton);
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateOptionalButton(Button optionalButton) {
		if (optionalButton != null) {
			optionalButton.setEnabled(
				getControl().isEnabled() &&
				adapter.enableOptionOnSelectionChange(selectionModel)
			);
		}
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateRemoveButton(Button removeButton) {
		removeButton.setEnabled(
			getControl().isEnabled() &&
			selectionModel.selectedValue() != null
		);
	}

	/**
	 * An abstract implementation of <code>Adapter</code>.
	 */
	public static abstract class AbstractAdapter implements Adapter {

		private String addButtonText;
		private boolean hasOptionalButton;
		private String optionalButtonText;
		private String removeButtonText;

		public AbstractAdapter() {
			this(JptUiMessages.AddRemovePane_AddButtonText,
			     JptUiMessages.AddRemovePane_RemoveButtonText);
		}

		public AbstractAdapter(boolean hasOptionalButton) {
			super();
			this.hasOptionalButton = hasOptionalButton;
		}

		public AbstractAdapter(String optionalButtonText) {
			this(true);
			this.optionalButtonText = optionalButtonText;
		}

		public AbstractAdapter(String addButtonText,
		                       String removeButtonText) {

			super();
			this.addButtonText    = addButtonText;
			this.removeButtonText = removeButtonText;
		}

		public AbstractAdapter(String addButtonText,
		                       String removeButtonText,
		                       String optionalButtonText) {

			this(optionalButtonText);
			this.addButtonText    = addButtonText;
			this.removeButtonText = removeButtonText;
		}

		public String addButtonText() {
			return addButtonText;
		}

		public boolean enableOptionOnSelectionChange(ObjectListSelectionModel listSelectionModel) {
			return listSelectionModel.selectedValuesSize() == 1;
		}

		public boolean hasOptionalButton() {
			return hasOptionalButton;
		}

		public String optionalButtonText() {
			return optionalButtonText;
		}

		public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
		}

		public String removeButtonText() {
			return removeButtonText;
		}

		public void setAddButtonText(String addButtonText) {
			this.addButtonText = addButtonText;
		}

		public void setOptionalButtonText(String optionalButtonText) {
			this.optionalButtonText = optionalButtonText;
		}

		public void setRemoveButtonText(String removeButtonText) {
			this.removeButtonText = removeButtonText;
		}
	}

	/**
	 * This adapter is used to perform the actual action when adding a new item
	 * or removing the selected items. It is possible to add an optional button.
	 */
	public static interface Adapter {

		/**
		 *
		 */
		String addButtonText();

		/**
		 * Invoked when the user selects the Add button.
		 */
		void addNewItem(ObjectListSelectionModel listSelectionModel);

		/**
		 * Invoked when selection changes. Implementation dictates whether button
		 * should be enabled.
		 */
		boolean enableOptionOnSelectionChange(ObjectListSelectionModel listSelectionModel);

		/**
		 *
		 */
		boolean hasOptionalButton();

		/**
		 * Resource string key for the optional button.
		 */
		String optionalButtonText();

		/**
		 * Invoked when the user selects the optional button
		 */
		void optionOnSelection(ObjectListSelectionModel listSelectionModel);

		/**
		 *
		 */
		String removeButtonText();

		/**
		 * Invoked when the user selects the Remove button.
		 */
		void removeSelectedItems(ObjectListSelectionModel listSelectionModel);
	}
}
