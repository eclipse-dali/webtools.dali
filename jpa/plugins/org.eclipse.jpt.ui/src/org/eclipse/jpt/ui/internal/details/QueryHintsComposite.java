/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
	private WritablePropertyValueModel<QueryHint> queryHintHolder;

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

	private PropertyValueModel<Boolean> buildPaneEnableHolder() {
		return new TransformationPropertyValueModel<Query, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(Query query) {
				return (query != null);
			}
		};
	}

	private Adapter buildQueryHintAdapter() {
		return new AddRemoveTablePane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				QueryHint queryHint = getSubject().addHint(getSubject().getHintsSize());
				queryHintHolder.setValue(queryHint);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					getSubject().removeHint((QueryHint) item);
				}
			}
		};
	}

	private WritablePropertyValueModel<QueryHint> buildQueryHintHolder() {
		return new SimplePropertyValueModel<QueryHint>();
	};

	private ITableLabelProvider buildQueryHintLabelProvider() {
		return new TableLabelProvider();
	}

	private ListValueModel<QueryHint> buildQueryHintListHolder() {
		return new ListAspectAdapter<Query, QueryHint>(getSubjectHolder(), NamedQuery.HINTS_LIST) {
			@Override
			protected ListIterable<QueryHint> getListIterable() {
				return subject.getHints();
			}

			@Override
			protected int size_() {
				return subject.getHintsSize();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		queryHintHolder = buildQueryHintHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		TablePane tablePane = new TablePane(container);
		installPaneEnabler(tablePane);
	}

	private PaneEnabler installPaneEnabler(TablePane tablePane) {
		return new PaneEnabler(buildPaneEnableHolder(), tablePane);
	}

	private static class QueryHintColumnAdapter implements ColumnAdapter<QueryHint> {

		static final int COLUMN_COUNT = 2;
		static final int NAME_COLUMN_INDEX = 0;
		static final int VALUE_COLUMN_INDEX = 1;

		private WritablePropertyValueModel<String> buildNameHolder(QueryHint subject) {
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

		private WritablePropertyValueModel<?> buildValueHolder(QueryHint subject) {
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

		public WritablePropertyValueModel<?>[] cellModels(QueryHint subject) {
			WritablePropertyValueModel<?>[] models = new WritablePropertyValueModel<?>[COLUMN_COUNT];
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

	private class TablePane extends AddRemoveTablePane<Query> {

		private TablePane(Composite parent) {
			super(QueryHintsComposite.this,
			      parent,
			      buildQueryHintAdapter(),
			      buildQueryHintListHolder(),
			      queryHintHolder,
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
		protected ColumnAdapter<?> buildColumnAdapter() {
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
		                                       Adapter adapter,
		                                       ListValueModel<?> listHolder,
		                                       WritablePropertyValueModel<?> selectedItemHolder,
		                                       IBaseLabelProvider labelProvider,
		                                       String helpId) {

			super.initializeMainComposite(
				container,
				adapter,
				listHolder,
				selectedItemHolder,
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