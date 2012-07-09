/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jpt.common.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryHint;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | AddRemoveTablePane                                                        |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Query
 * @see QueryHint
 * @see AddRemoveTablePane
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public class QueryHintsComposite extends Pane<Query>
{
	private ModifiableCollectionValueModel<QueryHint> selectedQueryHintsModel;

	/**
	 * Creates a new <code>QueryHintsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public QueryHintsComposite(Pane<? extends Query> parentPane,
	                           Composite container) {

		super(parentPane, container);
	}

	private Adapter<QueryHint> buildQueryHintAdapter() {
		return new AddRemoveTablePane.AbstractAdapter<QueryHint>() {

			public QueryHint addNewItem() {
				return getSubject().addHint(getSubject().getHintsSize());
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<QueryHint> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<QueryHint> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				QueryHint hint = selectedItemsModel.iterator().next();
				getSubject().removeHint(hint);
			}
		};
	}

	private ModifiableCollectionValueModel<QueryHint> buildSelectedQueryHintsModel() {
		return new SimpleCollectionValueModel<QueryHint>();
	}

	private ITableLabelProvider buildQueryHintLabelProvider() {
		return new TableLabelProvider();
	}

	private ListValueModel<QueryHint> buildQueryHintListHolder() {
		return new ListAspectAdapter<Query, QueryHint>(getSubjectHolder(), NamedQuery.HINTS_LIST) {
			@Override
			protected ListIterable<QueryHint> getListIterable() {
				return new SuperListIterableWrapper<QueryHint>(this.subject.getHints());
			}

			@Override
			protected int size_() {
				return subject.getHintsSize();
			}
		};
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedQueryHintsModel = buildSelectedQueryHintsModel();
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addTitledGroup(
			parent,
			JptUiDetailsMessages.NamedQueryPropertyComposite_queryHintsGroupBox
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new TablePane(container);
	}

	private static class QueryHintColumnAdapter implements ColumnAdapter<QueryHint> {

		static final int COLUMN_COUNT = 2;
		static final int NAME_COLUMN_INDEX = 0;
		static final int VALUE_COLUMN_INDEX = 1;

		private ModifiablePropertyValueModel<String> buildNameHolder(QueryHint subject) {
			return new PropertyAspectAdapter<QueryHint, String>(QueryHint.NAME_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					subject.setName(value);
				}
			};
		}

		private ModifiablePropertyValueModel<?> buildValueHolder(QueryHint subject) {
			return new PropertyAspectAdapter<QueryHint, String>(QueryHint.VALUE_PROPERTY, subject) {
				@Override
				protected String buildValue_() {
					return subject.getValue();
				}

				@Override
				protected void setValue_(String value) {
					subject.setValue(value);
				}
			};
		}

		public ModifiablePropertyValueModel<?>[] cellModels(QueryHint subject) {
			ModifiablePropertyValueModel<?>[] models = new ModifiablePropertyValueModel<?>[COLUMN_COUNT];
			models[NAME_COLUMN_INDEX]  = buildNameHolder(subject);
			models[VALUE_COLUMN_INDEX] = buildValueHolder(subject);
			return models;
		}

		public int columnCount() {
			return COLUMN_COUNT;
		}

		public String columnName(int columnIndex) {

			switch (columnIndex) {
				case QueryHintColumnAdapter.NAME_COLUMN_INDEX: {
					return JptUiDetailsMessages.QueryHintsComposite_nameColumn;
				}

				case QueryHintColumnAdapter.VALUE_COLUMN_INDEX: {
					return JptUiDetailsMessages.QueryHintsComposite_valueColumn;
				}

				default: {
					return null;
				}
			}
		}
	}

	private class TableLabelProvider extends LabelProvider
	                                 implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {

			QueryHint queryHint = (QueryHint) element;
			String value = "";

			switch (columnIndex) {
				case QueryHintColumnAdapter.NAME_COLUMN_INDEX: {
					value = queryHint.getName();
					break;
				}

				case QueryHintColumnAdapter.VALUE_COLUMN_INDEX: {
					value = queryHint.getValue();
					break;
				}
			}

			if (value == null) {
				value = "";
			}

			return value;
		}
	}

	private class TablePane extends AddRemoveTablePane<Query, QueryHint> {

		private TablePane(Composite parent) {
			super(QueryHintsComposite.this,
			      parent,
			      buildQueryHintAdapter(),
			      buildQueryHintListHolder(),
			      QueryHintsComposite.this.selectedQueryHintsModel,
			      buildQueryHintLabelProvider());
		}

		private CellEditor[] buildCellEditors(Table table) {
			return new CellEditor[] {
				new TextCellEditor(table),
				new TextCellEditor(table)
			};
		}

		private ICellModifier buildCellModifier() {
			return new ICellModifier() {

				public boolean canModify(Object element, String property) {
					return true;
				}

				public Object getValue(Object element, String property) {
					QueryHint queryHint = (QueryHint) element;
					String value = "";

					if (property == QueryHint.NAME_PROPERTY) {
						value = queryHint.getName();
					}
					else if (property == QueryHint.VALUE_PROPERTY) {
						value = queryHint.getValue();
					}

					if (value == null) {
						value = "";
					}

					return value;
				}

				public void modify(Object element, String property, Object value) {
					QueryHint queryHint;

					if (element instanceof TableItem) {
						TableItem tableItem = (TableItem) element;
						queryHint = (QueryHint) tableItem.getData();
					}
					else {
						queryHint = (QueryHint) element;
					}

					if (property == QueryHint.NAME_PROPERTY) {
						 queryHint.setName(value.toString());
					}
					else if (property == QueryHint.VALUE_PROPERTY) {
						 queryHint.setValue(value.toString());
					}
				}
			};
		}

		@Override
		protected ColumnAdapter<QueryHint> buildColumnAdapter() {
			return new QueryHintColumnAdapter();
		}

		private String[] buildColumnProperties() {
			return new String[] {
				QueryHint.NAME_PROPERTY,
				QueryHint.VALUE_PROPERTY
			};
		}

		@Override
		protected void initializeMainComposite(Composite container,
		                                       Adapter<QueryHint> adapter,
		                                       ListValueModel<?> listHolder,
		                                       ModifiableCollectionValueModel<QueryHint> selectedItemsModel,
		                                       IBaseLabelProvider labelProvider,
		                                       String helpId) {

			super.initializeMainComposite(
				container,
				adapter,
				listHolder,
				selectedItemsModel,
				labelProvider,
				helpId
			);

			Table table = getMainControl();

			TableViewer tableViewer = new TableViewer(table);
			tableViewer.setCellEditors(buildCellEditors(table));
			tableViewer.setCellModifier(buildCellModifier());
			tableViewer.setColumnProperties(buildColumnProperties());
		}
	}
}