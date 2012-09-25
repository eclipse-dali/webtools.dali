/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ArrayList;
import java.util.ListIterator;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here is the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoiningStrategyJoinColumnsComposite - A container of this pane
 * @see JoinTableComposite - A container of this pane
 * @see EntityOverridesComposite - A container of this pane
 *
 * @version 3.0
 * @since 2.0
 */
public class JoinColumnsComposite<T extends JpaNode> extends Pane<T>
{
	/**
	 * The editor used to perform the common behaviors defined in the list pane.
	 */
	private JoinColumnsEditor<T> joinColumnsEditor;

	private AddRemoveListPane<T> listPane;
	
	/**
	 * Creates a new <code>JoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 * @param joinColumnsEditor The editor used to perform the common behaviors
	 * defined in the list pane
	 */
	public JoinColumnsComposite(Pane<? extends T> parentPane,
	                            Composite parent,
	                            JoinColumnsEditor<T> joinColumnsEditor) {

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
	public JoinColumnsComposite(Pane<?> parentPane,
	                            PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent,
	                            JoinColumnsEditor<T> joinColumnsEditor,
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
	                            WidgetFactory widgetFactory,
	                            JoinColumnsEditor<T> joinColumnsEditor) {

		super(subjectHolder, parent, widgetFactory);
		this.joinColumnsEditor = joinColumnsEditor;
		initializeLayout2();
	}

	@Override
	protected void initializeLayout(Composite container) {
		//see intiailizeLayout2()
	}

	private void initializeLayout2() {
		this.listPane = new AddRemoveListPane<T>(
			this,
			getControl(),
			buildJoinColumnsAdapter(),
			buildJoinColumnsListModel(),
			buildSelectedJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS,
			false
		);
	}

	private WritablePropertyValueModel<JoinColumn> buildSelectedJoinColumnHolder() {
		return new SimplePropertyValueModel<JoinColumn>();
	}

	private String buildJoinColumnLabel(JoinColumn joinColumn) {

		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
		if (joinColumn.getSpecifiedName() == null) {

			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiDetailsMessages.JoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter buildJoinColumnsAdapter() {
		return new AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				JoinColumnsComposite.this.joinColumnsEditor.addJoinColumn(getSubject());
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiDetailsMessages.JoinColumnsComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				JoinColumn joinColumn = (JoinColumn) listSelectionModel.selectedValue();
				JoinColumnsComposite.this.joinColumnsEditor.editJoinColumn(getSubject(), joinColumn);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				JoinColumnsComposite.this.joinColumnsEditor.removeJoinColumns(getSubject(), listSelectionModel.selectedIndices());
			}
		};
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<JoinColumn>(buildJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			BaseJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			BaseJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY);
	}

	private ListValueModel<JoinColumn> buildJoinColumnsListHolder() {
		java.util.List<ListValueModel<JoinColumn>> list = new ArrayList<ListValueModel<JoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<JoinColumn>, JoinColumn>(list);
	}

	private ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, JoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getSpecifiedJoinColumnsListPropertyName()) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return JoinColumnsComposite.this.joinColumnsEditor.specifiedJoinColumns(this.subject);
			}

			@Override
			protected int size_() {
				return JoinColumnsComposite.this.joinColumnsEditor.specifiedJoinColumnsSize(this.subject);
			}
		};
	}


	private ListValueModel<JoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<JoinColumn>(buildDefaultJoinColumnHolder());

	}

	private PropertyValueModel<JoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<T, JoinColumn>(getSubjectHolder(), this.joinColumnsEditor.getDefaultPropertyName()) {
			@Override
			protected JoinColumn buildValue_() {
				return JoinColumnsComposite.this.joinColumnsEditor.getDefaultJoinColumn(this.subject);
			}
		};
	}


	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				JoinColumn joinColumn = (JoinColumn) element;

				return buildJoinColumnLabel(joinColumn);
			}
		};
	}
	
	public void installJoinColumnsPaneEnabler(PropertyValueModel<Boolean> joinColumnsPaneEnablerHolder) {
		new PaneEnabler(joinColumnsPaneEnablerHolder, this.listPane);
	}
	
	public void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.listPane.setSelectedItem(joinColumn);
	}
	
	/**
	 * The editor is used to complete the behavior of this pane.
	 */
	public static interface JoinColumnsEditor<T> {

		/**
		 * Add a join column to the given subject and return it
		 */
		void addJoinColumn(T subject);
		
		/**
		 * Edit the given join column, the Edit button was pressed
		 * while this join column was selected.
		 */
		void editJoinColumn(T subject, JoinColumn joinColumn);
		
		/**
		 * Return whether the subject has specified join columns
		 */
		boolean hasSpecifiedJoinColumns(T subject);
		
		/**
		 * Return the spcified join columns from the given subject
		 */
		ListIterator<JoinColumn> specifiedJoinColumns(T subject);
		
		/**
		 * Return the number of specified join columns on the given subject
		 */
		int specifiedJoinColumnsSize(T subject);
		
		/**
		 * Return the default join column from the given subject or null.
		 */
		JoinColumn getDefaultJoinColumn(T subject);
		
		/**
		 * Return the property name of the specified join columns list
		 */
		String getSpecifiedJoinColumnsListPropertyName();
		
		/**
		 * Return the property name of the specified join columns list
		 */
		String getDefaultPropertyName();
		
		/**
		 * Remove the join columns at the specified indices from the subject
		 */
		void removeJoinColumns(T subject, int[] selectedIndices);
	}
}