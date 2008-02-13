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

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.internal.IJpaNode;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.IWidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
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
	/**
	 * The editor used to perform the common behaviors defined in the list pane.
	 */
	private IJoinColumnsEditor<T> joinColumnsEditor;

	/**
	 * Keeps track of the list pane in order to update its enablement state when
	 * required.
	 */
	private AddRemoveListPane<T> joinColumnsListPane;

	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 * @param joinColumnsEditor The editor used to perform the common behaviors
	 * defined in the list pane
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
	 * @param joinColumnsEditor The editor used to perform the common behaviors
	 * defined in the list pane
	 */
	public JoinColumnsComposite(AbstractFormPane<?> parentPane,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            IJoinColumnsEditor<T> joinColumnsEditor,
	                            boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
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

	private WritablePropertyValueModel<IJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<IJoinColumn>();
	}

	private String buildJoinColumnLabel(IJoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiMappingsMessages.JoinTableComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
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

	private ListValueModel<IJoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<IJoinColumn>(buildJoinColumnsListHolder(),
			INamedColumn.SPECIFIED_NAME_PROPERTY,
			INamedColumn.DEFAULT_NAME_PROPERTY,
			IAbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			IAbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}

	private ListValueModel<IJoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<IJoinColumn>> list = new ArrayList<ListValueModel<IJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<IJoinColumn>, IJoinColumn>(list);
	}

	private ListValueModel<IJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, IJoinColumn>(getSubjectHolder(), joinColumnsEditor.specifiedListPropertyName()) {
			@Override
			protected ListIterator<IJoinColumn> listIterator_() {
				return joinColumnsEditor.specifiedJoinColumns(subject);
			}

			@Override
			protected int size_() {
				return joinColumnsEditor.specifiedJoinColumnsSize(subject);
			}
		};
	}


	private ListValueModel<IJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<IJoinColumn>(buildDefaultJoinColumnHolder());

	}

	private PropertyValueModel<IJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<T, IJoinColumn>(getSubjectHolder(), joinColumnsEditor.defaultPropertyName()) {
			@Override
			protected IJoinColumn buildValue_() {
				return joinColumnsEditor.defaultJoinColumn(subject);
			}
		};
	}


	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IJoinColumn joinColumn = (IJoinColumn) element;

				return buildJoinColumnLabel(joinColumn);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		joinColumnsListPane.enableWidgets(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
	}

	private void initializeLayout2() {

		// Join Columns list pane
		joinColumnsListPane = new AddRemoveListPane<T>(
			this,
			getControl(),
			buildJoinColumnsAdapter(),
			buildJoinColumnsListModel(),
			buildJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	/**
	 * The editor is used to complete the behavior of this pane.
	 */
	public static interface IJoinColumnsEditor<T> {

		void addJoinColumn(T subject);
		void editJoinColumn(T subject, IJoinColumn joinColumn);
		boolean hasSpecifiedJoinColumns(T subject);
		ListIterator<IJoinColumn> specifiedJoinColumns(T subject);
		int specifiedJoinColumnsSize(T subject);
		IJoinColumn defaultJoinColumn(T subject);
		String specifiedListPropertyName();
		String defaultPropertyName();
		void removeJoinColumns(T subject, int[] selectedIndices);
	}
}