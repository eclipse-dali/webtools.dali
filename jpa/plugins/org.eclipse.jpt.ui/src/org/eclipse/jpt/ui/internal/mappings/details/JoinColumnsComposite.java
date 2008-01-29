/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinTableComposite - A container of this pane
 * @see OverridesComposite - A container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnsComposite<T extends IJpaNode> extends AbstractFormPane<T>
{
	private IJoinColumnsEditor<T> joinColumnsEditor;

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnsComposite(AbstractFormPane<? extends T> parentPane,
	                            Composite parent,
	                            IJoinColumnsEditor<T> joinColumnsEditor) {

		super(parentPane, parent);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnsComposite(AbstractFormPane<?> parentPane,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            IJoinColumnsEditor<T> joinColumnsEditor) {

		super(parentPane, subjectHolder, parent);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnsComposite(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            IWidgetFactory widgetFactory,
	                            IJoinColumnsEditor<T> joinColumnsEditor) {

		super(subjectHolder, parent, widgetFactory);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	private String buildDefaultJoinColumnLabel(IJoinColumn joinColumn) {
		return NLS.bind(
			JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsDefault,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private WritablePropertyValueModel<IJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<IJoinColumn>();
	}

	private String buildJoinColumnLabel(IJoinColumn joinColumn) {

		if (joinColumn.getSpecifiedName() == null) {

			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter buildJoinColumnsAdapter() {
		return new AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				joinColumnsEditor.addJoinColumn(subject());
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.JoinColumnComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				IJoinColumn joinColumn = (IJoinColumn) listSelectionModel.selectedValue();
				joinColumnsEditor.editJoinColumn(subject(), joinColumn);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				joinColumnsEditor.removeJoinColumns(subject(), listSelectionModel.selectedIndices());
			}
		};
	}

	private ListValueModel<IJoinColumn> buildJoinColumnsListHolder() {
		return new ListAspectAdapter<T, IJoinColumn>(getSubjectHolder(), joinColumnsEditor.propertyNames()) {
			@Override
			protected ListIterator<IJoinColumn> listIterator_() {
				return joinColumnsEditor.joinColumns(subject);
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IJoinColumn joinColumn = (IJoinColumn) element;

				return joinColumnsEditor.hasSpecifiedJoinColumns(subject()) ?
					buildJoinColumnLabel(joinColumn) :
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}

	private ListValueModel<IJoinColumn> buildSortedJoinColumnsListHolder() {
		return new SortedListValueModelAdapter<IJoinColumn>(
			buildJoinColumnsListHolder()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
	}

	private void initializeLayout2() {

		// Join Columns list pane
		new AddRemoveListPane<T>(
			this,
			getControl(),
			buildJoinColumnsAdapter(),
			buildSortedJoinColumnsListHolder(),
			buildJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	public static interface IJoinColumnsEditor<T> {

		void addJoinColumn(T subject);
		void editJoinColumn(T subject, IJoinColumn joinColumn);
		boolean hasSpecifiedJoinColumns(T subject);
		ListIterator<IJoinColumn> joinColumns(T subject);
		String[] propertyNames();
		void removeJoinColumns(T subject, int[] selectedIndices);
	}
}