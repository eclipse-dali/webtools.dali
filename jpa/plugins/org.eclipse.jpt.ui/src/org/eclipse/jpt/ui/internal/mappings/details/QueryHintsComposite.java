/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHint;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.swt.ColumnAdapter;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveTablePane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

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
public class QueryHintsComposite extends AbstractPane<Query>
{
	private WritablePropertyValueModel<QueryHint> queryHintHolder;

	/**
	 * Creates a new <code>QueryHintsComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public QueryHintsComposite(AbstractPane<? extends Query> parentPane,
	                           Composite container) {

		super(parentPane, container);
	}

	private Adapter buildQueryHintAdapter() {
		return new AddRemoveTablePane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				QueryHint queryHint = subject().addHint(subject().hintsSize());
				queryHintHolder.setValue(queryHint);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					subject().removeHint((QueryHint) item);
				}
			}
		};
	}

	private WritablePropertyValueModel<QueryHint> buildQueryHintHolder() {
		return new SimplePropertyValueModel<QueryHint>();
	}

	private ITableLabelProvider buildQueryHintLabelProvider() {
		return new TableLabelProvider();
	};

	private ListValueModel<QueryHint> buildQueryHintListHolder() {
		return new ListAspectAdapter<Query, QueryHint>(getSubjectHolder(), NamedQuery.HINTS_LIST) {
			@Override
			protected ListIterator<QueryHint> listIterator_() {
				return subject.hints();
			}

			@Override
			protected int size_() {
				return subject.hintsSize();
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

		new AddRemoveTablePane<Query>(
			this,
			container,
			buildQueryHintAdapter(),
			buildQueryHintListHolder(),
			queryHintHolder,
			buildQueryHintLabelProvider()
		) {
			@Override
			protected ColumnAdapter<?> buildColumnAdapter() {
				return new QueryHintColumnAdapter();
			}
		};
	}

	private static class QueryHintColumnAdapter implements ColumnAdapter<QueryHint> {

		static final int COLUMN_COUNT = 2;
		static final int NAME_COLUMN_INDEX = 0;
		static final int VALUE_COLUMN_INDEX = 1;

		private WritablePropertyValueModel<?> buildNameHolder(QueryHint subject) {
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

		private WritablePropertyValueModel<String> buildValueHolder(QueryHint subject) {
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
					return JptUiMappingsMessages.QueryHintsComposite_nameColumn;
				}

				case QueryHintColumnAdapter.VALUE_COLUMN_INDEX: {
					return JptUiMappingsMessages.QueryHintsComposite_valueColumn;
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

			switch (columnIndex) {
				case QueryHintColumnAdapter.NAME_COLUMN_INDEX: {
					return queryHint.getName();
				}

				case QueryHintColumnAdapter.VALUE_COLUMN_INDEX: {
					return queryHint.getValue();
				}

				default: {
					return null;
				}
			}
		}
	}
}
