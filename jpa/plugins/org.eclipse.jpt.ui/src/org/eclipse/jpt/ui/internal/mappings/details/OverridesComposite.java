/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IOverride;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.IWidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.part.PageBook;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - Attribute Overrides --------------------------------------------------- |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | AddRemoveListPane                                                 | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | |                                                                       | |
 * | |   x Override Default                                                  | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | PageBook (JoinColumnsComposite or ColumnComposite)                | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEntity
 * @see EntityComposite - The parent container
 * @see ColumnComposite
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OverridesComposite extends AbstractFormPane<IEntity>
{
	private ColumnComposite columnComposite;
	private Composite columnPane;
	private JoinColumnsComposite<IAssociationOverride> joinColumnsComposite;
	private Composite joinColumnsPane;
	private Button overrideDefaultButton;
	private WritablePropertyValueModel<IOverride> overrideHolder;

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OverridesComposite(AbstractFormPane<? extends IEntity> parentPane,
	                          Composite parent) {

		super(parentPane, parent, false);
	}

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OverridesComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                          Composite parent,
	                          IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(IAssociationOverride subject) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(shell(), subject, null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumn(JoinColumnInAssociationOverrideStateObject stateObject) {

		IAssociationOverride associationOverride = stateObject.getOwner();
		int index = associationOverride.specifiedJoinColumnsSize();

		IJoinColumn joinColumn = associationOverride.addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private PostExecution<JoinColumnInAssociationOverrideDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInAssociationOverrideDialog>() {
			public void execute(JoinColumnInAssociationOverrideDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PropertyValueModel<IAssociationOverride> buildAssociationOverrideHolder(PropertyValueModel<IOverride> overrideHolder) {
		return new TransformationPropertyValueModel<IOverride, IAssociationOverride>(overrideHolder) {
			@Override
			protected IAssociationOverride transform_(IOverride value) {
				return (value instanceof IAssociationOverride) ? (IAssociationOverride) value : null;
			}
		};
	}

	private PropertyValueModel<IAttributeOverride> buildAttributeOverrideHolder(PropertyValueModel<IOverride> overrideHolder) {
		return new TransformationPropertyValueModel<IOverride, IAttributeOverride>(overrideHolder) {
			@Override
			protected IAttributeOverride transform_(IOverride value) {
				return (value instanceof IAttributeOverride) ? (IAttributeOverride) value : null;
			}
		};
	}

	private PropertyValueModel<IColumn> buildColumnHolder(PropertyValueModel<IAttributeOverride> attributeOverrideHolder) {
		return new TransformationPropertyValueModel<IAttributeOverride, IColumn>(attributeOverrideHolder) {
			@Override
			protected IColumn transform_(IAttributeOverride value) {
				return value.getColumn();
			}
		};
	}

	private ListValueModel<IAssociationOverride> buildDefaultAssociationOverridesListHolder() {
		return new ListAspectAdapter<IEntity, IAssociationOverride>(getSubjectHolder(), IEntity.DEFAULT_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<IAssociationOverride> listIterator_() {
				return subject.defaultAssociationOverrides();
			}

			@Override
			protected int size_() {
				return subject.defaultAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<IAttributeOverride> buildDefaultAttributeOverridesListHolder() {
		return new ListAspectAdapter<IEntity, IAttributeOverride>(getSubjectHolder(), IEntity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<IAttributeOverride> listIterator_() {
				return subject.defaultAttributeOverrides();
			}

			@Override
			protected int size_() {
				return subject.defaultAttributeOverridesSize();
			}
		};
	}

	private PostExecution<JoinColumnInAssociationOverrideDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInAssociationOverrideDialog>() {
			public void execute(JoinColumnInAssociationOverrideDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		return new TransformationWritablePropertyValueModel<IOverride, Boolean>(overrideHolder) {
			@Override
			public void setValue(Boolean value) {
				// Done in the button selection listener;
			}

			@Override
			protected Boolean transform_(IOverride value) {
				return !value.isVirtual();
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (!isPopulating()) {
					Button button = (Button) e.widget;
					overrideDefaultButtonSelected(button.getSelection());
				}
			}
		};
	}

	private String buildOverrideDisplayString(IOverride override) {

		IEntity subject = subject();
		String overrideType;
		int index = 0;

		// Retrieve the index and type
		if (override instanceof IAssociationOverride) {
			index = CollectionTools.indexOf(subject.associationOverrides(), override);
			overrideType = JptUiMappingsMessages.OverridesComposite_association;
		}
		else {
			index = CollectionTools.indexOf(subject.attributeOverrides(), override);
			overrideType = JptUiMappingsMessages.OverridesComposite_attribute;
		}

		// Format the name
		String name = override.getName();

		if (StringTools.stringIsEmpty(name)) {
			name = JptUiMappingsMessages.OverridesComposite_noName;
		}
		else {
			name = name.trim();
		}

		// Format: <name> (Association <index>), we show the index since
		// it's possible to have one than one override with the same name
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" (");
		sb.append(overrideType);
		sb.append(" ");
		sb.append(index + 1);
		sb.append(") ");
		return sb.toString();
	}

	private WritablePropertyValueModel<IOverride> buildOverrideHolder() {
		return new SimplePropertyValueModel<IOverride>();
	}

	private ILabelProvider buildOverrideLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildOverrideDisplayString((IOverride) element);
			}
		};
	}

	private Adapter buildOverridesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				removeOverrides(listSelectionModel);
			}
		};
	}

	private ListValueModel<IOverride> buildOverridesListHolder() {
		List<ListValueModel<? extends IOverride>> list = new ArrayList<ListValueModel<? extends IOverride>>();
		list.add(buildSpecifiedAttributeOverridesListHolder());
		list.add(buildDefaultAttributeOverridesListHolder());
		list.add(buildSpecifiedAssociationOverridesListHolder());
		list.add(buildDefaultAssociationOverridesListHolder());
		return new CompositeListValueModel<ListValueModel<? extends IOverride>, IOverride>(list);
	}

	private ListValueModel<IOverride> buildOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<IOverride>(
			buildOverridesListHolder(),
			IOverride.NAME_PROPERTY
		);
	}

	private Transformer<IOverride, Control> buildPaneTransformer() {
		return new Transformer<IOverride, Control>() {
			public Control transform(IOverride override) {

				if (override instanceof IAttributeOverride) {
					return columnPane;
				}

				if (override instanceof IAssociationOverride) {
					return joinColumnsPane;
				}

				return null;
			}
		};
	}

	private ListValueModel<IAssociationOverride> buildSpecifiedAssociationOverridesListHolder() {
		return new ListAspectAdapter<IEntity, IAssociationOverride>(getSubjectHolder(), IEntity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<IAssociationOverride> listIterator_() {
				return subject.specifiedAssociationOverrides();
			}

			@Override
			protected int size_() {
				return subject.specifiedAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<IAttributeOverride> buildSpecifiedAttributeOverridesListHolder() {
		return new ListAspectAdapter<IEntity, IAttributeOverride>(getSubjectHolder(), IEntity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<IAttributeOverride> listIterator_() {
				return subject.specifiedAttributeOverrides();
			}

			@Override
			protected int size_() {
				return subject.specifiedAttributeOverridesSize();
			}
		};
	}

	private void editJoinColumn(IJoinColumn joinColumn) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(
				shell(),
				(IAssociationOverride) overrideHolder.value(),
				joinColumn
			);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInAssociationOverrideStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		overrideHolder = buildOverrideHolder();
	}

	private void initializeColumnPane(PageBook pageBook) {

		int groupBoxMargin = groupBoxMargin();
		columnPane = buildSubPane(pageBook, 5);

		// Override Default check box
		overrideDefaultButton = buildCheckBox(
			buildSubPane(columnPane, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			buildOverrideDefaultHolder()
		);

		overrideDefaultButton.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		// Column widgets (for IOverrideAttribute)
		columnComposite = new ColumnComposite(
			this,
			buildColumnHolder(buildAttributeOverrideHolder(overrideHolder)),
			columnPane,
			false
		);

		columnPane.setVisible(false);
		columnComposite.enableWidgets(false);
	}

	private void initializeJoinColumnsPane(PageBook pageBook) {

		joinColumnsPane = buildSubPane(pageBook);

		// Override Default check box
		overrideDefaultButton = buildCheckBox(
			buildSubPane(joinColumnsPane, 5),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			buildOverrideDefaultHolder()
		);

		overrideDefaultButton.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		Group joinColumnsGroupPane = buildTitledPane(
			joinColumnsPane,
			JptUiMappingsMessages.OverridesComposite_joinColumn
		);

		// Join Columns list pane (for IOverrideAssociation)
		joinColumnsComposite = new JoinColumnsComposite<IAssociationOverride>(
			this,
			buildAssociationOverrideHolder(overrideHolder),
			joinColumnsGroupPane,
			buildJoinColumnsEditor(),
			false
		);

		joinColumnsPane.setVisible(false);
		joinColumnsComposite.enableWidgets(false);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Overrides group pane
		container = buildTitledPane(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		// Overrides list pane
		initializeOverridesList(container);

		// Property pane
		PageBook pageBook = buildPageBook(container);
		initializeJoinColumnsPane(pageBook);
		initializeColumnPane(pageBook);
		installOverrideControlSwitcher(overrideHolder, pageBook);
	}

	private AddRemoveListPane<IEntity> initializeOverridesList(Composite container) {

		return new AddRemoveListPane<IEntity>(
			this,
			buildSubPane(container, 8),
			buildOverridesAdapter(),
			buildOverridesListModel(),
			overrideHolder,
			buildOverrideLabelProvider(),
			IJpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES
		)
		{
			@Override
			protected void initializeButtonPane(Composite container, String helpId) {
			}

			@Override
			protected void updateButtons() {
			}
		};
	}

	private void installOverrideControlSwitcher(PropertyValueModel<IOverride> overrideHolder,
	                                            PageBook pageBook) {

		new ControlSwitcher(
			overrideHolder,
			buildPaneTransformer(),
			pageBook
		);
	}

	private void overrideDefaultButtonSelected(boolean selected) {

		IEntity subject = subject();
		IOverride override = overrideHolder.value();

		joinColumnsComposite.enableWidgets(selected);
		columnComposite.enableWidgets(selected);

		setPopulating(true);

		try {
			// Add a new override
			if (selected) {

				if (override instanceof IAttributeOverride) {
					int index = subject.specifiedAttributeOverridesSize();
					IAttributeOverride attributeOverride = subject.addSpecifiedAttributeOverride(index);
					attributeOverride.setName(override.getName());
					attributeOverride.getColumn().setSpecifiedName(((IAttributeOverride) override).getColumn().getName());
					overrideHolder.setValue(attributeOverride);
				}
				else {
					int index = subject.specifiedAssociationOverridesSize();
					IAssociationOverride associationOverride = subject.addSpecifiedAssociationOverride(index);
					associationOverride.setName(override.getName());
					//attributeOverride.getColumn().setSpecifiedName(this.attributeOverride.getColumn().getName());
					overrideHolder.setValue(associationOverride);
				}
			}
			// Remove the specified overrides
			else {

				if (override instanceof IAttributeOverride) {
					subject.removeSpecifiedAttributeOverride((IAttributeOverride) override);
				}
				else {
					subject.removeSpecifiedAssociationOverride((IAssociationOverride) override);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private void removeOverrides(ObjectListSelectionModel listSelectionModel) {

		IEntity subject = subject();
		Object[] selectedItems = listSelectionModel.selectedValues();

		for (int index = selectedItems.length; --index >= 0; ) {

			IOverride override = (IOverride) selectedItems[index];

			if (override instanceof IAttributeOverride) {
				subject.removeSpecifiedAttributeOverride((IAttributeOverride) override);
			}
			else if (override instanceof IAssociationOverride) {
				subject.removeSpecifiedAssociationOverride((IAssociationOverride) override);
			}
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<IAssociationOverride> {

		public void addJoinColumn(IAssociationOverride subject) {
			OverridesComposite.this.addJoinColumn(subject);
		}

		public IJoinColumn defaultJoinColumn(IAssociationOverride subject) {
			return null;
		}

		public String defaultPropertyName() {
			return IAssociationOverride.DEFAULT_JOIN_COLUMNS_LIST;
		}

		public void editJoinColumn(IAssociationOverride subject, IJoinColumn joinColumn) {
			OverridesComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IAssociationOverride subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public void removeJoinColumns(IAssociationOverride subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<IJoinColumn> specifiedJoinColumns(IAssociationOverride subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(IAssociationOverride subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return IAssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
}