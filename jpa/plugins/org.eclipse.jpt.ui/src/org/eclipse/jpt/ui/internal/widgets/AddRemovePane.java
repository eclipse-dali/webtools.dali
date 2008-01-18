/*******************************************************************************
 *  Copyright (c) 2008 Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0 which accompanies this
 *  distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.utility.internal.model.event.ListChangeEvent;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeAdapter;
import org.eclipse.jpt.utility.internal.model.listener.ListChangeListener;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ListModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.internal.node.Node;
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
public abstract class AddRemovePane<T extends Node> extends BaseJpaComposite<T>
{
	private Adapter adapter;
	private Button addButton;
	private Composite container;
	private ListValueModel listHolder;
	private Button optionalButton;
	private Button removeButton;
	private ObjectListSelectionModel rowSelectionModel;
	private PropertyValueModel<Object> selectedItemHolder;
	private Object list;

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param adapter
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 */
	protected AddRemovePane(BaseJpaController<? extends T> parentController,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        PropertyValueModel<?> selectedItemHolder) {

		super(parentController, parent);
		initialize(adapter, listHolder, selectedItemHolder);
		postInitialize();
	}

	/**
	 * Creates a new <code>AddRemovePane</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param adapter
	 * @param parent The parent container
	 * @param listHolder The <code>ListValueModel</code> containing the items
	 * @param selectedItemHolder The holder of the selected item, if more than
	 * one item or no items are selected, then <code>null</code> will be passed
	 */
	protected AddRemovePane(BaseJpaController<?> parentController,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        Adapter adapter,
	                        ListValueModel/*<?>*/ listHolder,
	                        PropertyValueModel<?> selectedItemHolder) {

		super(parentController, subjectHolder, parent);
		initialize(adapter, listHolder, selectedItemHolder);
		postInitialize();
	}

	/**
	 * @category Add
	 */
	protected String addButtonKey() {
		return null;
	}

	/**
	 * @category Add
	 */
	protected void addItem() {
		adapter.addNewItem(rowSelectionModel);
	}

	/**
	 * @category Initialize
	 */
	protected Adapter buildAdapter() {
		return null;
	}

	/**
	 * @category Add
	 */
	protected Button buildAddButton(Composite parent) {
		return buildButton(parent, addButtonKey(), buildAddItemAction());
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

	private ListChangeListener/*<Object>*/ buildListChangeListener() {
		return new ListChangeAdapter/*<Object>*/() {
			@Override
			public void listChanged(ListChangeEvent/*<Object>*/ e) {
				AddRemovePane.this.updateButtons();
			}
		};
	}

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
		return buildButton(parent, removeButtonKey(), buildRemoveItemsAction());
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

	private ListSelectionListener buildRowSelectionListener() {
		return new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					AddRemovePane.this.rowSelectionChanged(e);
				}
			}
		};
	}

	protected ObjectListSelectionModel buildRowSelectionModel(ListValueModel/*<?>*/ listModel) {
		return new ObjectListSelectionModel(new ListModelAdapter(listModel));
	}

	public ObjectListSelectionModel getSelectionModel() {
		return rowSelectionModel;
	}

	@SuppressWarnings("unchecked")
	protected void initialize(Adapter adapter,
	                          ListValueModel/*<?>*/ listHolder,
	                          PropertyValueModel<?> selectedItemHolder)
	{
		this.adapter            = (adapter == null) ? buildAdapter() : adapter;
		this.selectedItemHolder = (PropertyValueModel<Object>) selectedItemHolder;

		initialize(listHolder);
	}

	protected void initialize(ListValueModel/*<?>*/ listHolder) {

		this.listHolder = listHolder;
		listHolder.addListChangeListener(buildListChangeListener());

		rowSelectionModel = buildRowSelectionModel(listHolder);
		rowSelectionModel.setSelectedValue(selectedItemHolder.value());
		rowSelectionModel.addListSelectionListener(buildRowSelectionListener());
	}

	private void initializeButtonPane() {

		Composite buttonPane = buildSubPane(
			container,
			adapter.hasOptionalButton() ? 3 : 2,
			0, 5, 0, 0
		);

		addButton      = buildAddButton(buttonPane);
		removeButton   = buildRemoveButton(buttonPane);

		if (adapter.hasOptionalButton()) {
			optionalButton = buildOptionalButton(buttonPane);
		}

//		upButton       = buildUpButton      ((adapter instanceof UpDownAdapter) ? (UpDownAdapter) adapter : null);
//		downButton     = buildDownButton    ((adapter instanceof UpDownAdapter) ? (UpDownAdapter) adapter : null);
//		gotoButton     = buildGotoButton();
//		component      = buildComponent();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;

		container = buildPane(container, layout);
	}

	protected void postInitialize() {
		initializeList();
		initializeButtonPane();
		enableWidgets(subject() != null);
	}

	private void initializeList() {
//		list = buildList(container, selectedItemHolder);
	}

	/**
	 * @category Remove
	 */
	protected String removeButtonKey() {
		return null;
	}

	/**
	 * @category Remove
	 */
	protected void removeItems() {
		adapter.removeSelectedItems(rowSelectionModel);
	}

	protected void rowSelectionChanged(ListSelectionEvent e) {
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateAddButton(Button addButton) {
		addButton.setEnabled(getControl().isEnabled() && (subject() != null));
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateButtons() {
		if (container.isDisposed()) {
			return;
		}

		updateAddButton(addButton);
		updateRemoveButton(addButton);
		updateOptionalButton(optionalButton);
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateOptionalButton(Button optionalButton) {
		if (optionalButton != null) {
			optionalButton.setEnabled(
				getControl().isEnabled() &&
				adapter.enableOptionOnSelectionChange(rowSelectionModel)
			);
		}
	}

	/**
	 * @category UpdateButtons
	 */
	protected void updateRemoveButton(Button removeButton) {
		removeButton.setEnabled(
			getControl().isEnabled() &&
			rowSelectionModel.getSelectedValue() != null
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

		AbstractAdapter(boolean hasOptionalButton) {
			super();
			this.hasOptionalButton = hasOptionalButton;
		}

		AbstractAdapter(String optionalButtonText) {
			this(true);
			this.optionalButtonText = optionalButtonText;
		}

		AbstractAdapter(String addButtonText,
		                String removeButtonText) {

			super();
			this.addButtonText    = addButtonText;
			this.removeButtonText = removeButtonText;
		}

		AbstractAdapter(String addButtonText,
		                String removeButtonText,
		                String optionalButtonText) {

			this(optionalButtonText);
			this.addButtonText    = addButtonText;
			this.removeButtonText = removeButtonText;
		}

		public String addButtonText() {
			return addButtonText;
		}

		public boolean hasOptionalButton() {
			return hasOptionalButton;
		}

		public String optionalButtonText() {
			return optionalButtonText;
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
