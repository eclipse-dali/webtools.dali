/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * @see Entity
 * @see EntityComposite - The parent container
 * @see ColumnComposite
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OverridesComposite extends AbstractFormPane<Entity>
{
	private Composite columnPane;
	private Composite joinColumnsPane;
	private WritablePropertyValueModel<BaseOverride> overrideHolder;

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OverridesComposite(AbstractFormPane<? extends Entity> parentPane,
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
	public OverridesComposite(PropertyValueModel<? extends Entity> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(AssociationOverride subject) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(shell(), subject, null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumn(JoinColumnInAssociationOverrideStateObject stateObject) {

		AssociationOverride associationOverride = stateObject.getOwner();
		int index = associationOverride.specifiedJoinColumnsSize();

		JoinColumn joinColumn = associationOverride.addSpecifiedJoinColumn(index);
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

	private WritablePropertyValueModel<AssociationOverride> buildAssociationOverrideHolder() {
		return new TransformationWritablePropertyValueModel<BaseOverride, AssociationOverride>(overrideHolder) {
			@Override
			protected AssociationOverride transform_(BaseOverride value) {
				return (value instanceof AssociationOverride) ? (AssociationOverride) value : null;
			}
		};
	}

	private WritablePropertyValueModel<AttributeOverride> buildAttributeOverrideHolder() {
		return new TransformationWritablePropertyValueModel<BaseOverride, AttributeOverride>(overrideHolder) {
			@Override
			protected AttributeOverride transform_(BaseOverride value) {
				return (value instanceof AttributeOverride) ? (AttributeOverride) value : null;
			}
		};
	}

	private PropertyValueModel<Column> buildColumnHolder(PropertyValueModel<AttributeOverride> attributeOverrideHolder) {
		return new TransformationPropertyValueModel<AttributeOverride, Column>(attributeOverrideHolder) {
			@Override
			protected Column transform_(AttributeOverride value) {
				return value.getColumn();
			}
		};
	}

	private ListValueModel<AssociationOverride> buildDefaultAssociationOverridesListHolder() {
		return new ListAspectAdapter<Entity, AssociationOverride>(getSubjectHolder(), Entity.DEFAULT_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AssociationOverride> listIterator_() {
				return subject.defaultAssociationOverrides();
			}

			@Override
			protected int size_() {
				return subject.defaultAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<AttributeOverride> buildDefaultAttributeOverridesListHolder() {
		return new ListAspectAdapter<Entity, AttributeOverride>(getSubjectHolder(), Entity.DEFAULT_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
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

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultAssociationOverrideHolder() {
		return new TransformationWritablePropertyValueModel<AssociationOverride, Boolean>(buildAssociationOverrideHolder()) {
			@Override
			public void setValue(Boolean value) {
				updateAssociationOverride(value);
			}

			@Override
			protected Boolean transform_(AssociationOverride value) {
				return !value.isVirtual();
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultAttributeOverrideHolder() {
		return new TransformationWritablePropertyValueModel<AttributeOverride, Boolean>(buildAttributeOverrideHolder()) {
			@Override
			public void setValue(Boolean value) {
				updateAttributeOverride(value);
			}

			@Override
			protected Boolean transform_(AttributeOverride value) {
				return !value.isVirtual();
			}
		};
	}

	private String buildOverrideDisplayString(BaseOverride override) {
		String overrideType;

		// Retrieve the type
		if (override instanceof AssociationOverride) {
			overrideType = JptUiMappingsMessages.OverridesComposite_association;
		}
		else {
			overrideType = JptUiMappingsMessages.OverridesComposite_attribute;
		}

		// Format the name
		String name = override.getName();

		if (StringTools.stringIsEmpty(name)) {
			name = JptUiMappingsMessages.OverridesComposite_noName;
		}

		// Format: <name> (Attribute/Association Override)
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" (");
		sb.append(overrideType);
		sb.append(") ");
		return sb.toString();
	}

	private WritablePropertyValueModel<BaseOverride> buildOverrideHolder() {
		return new SimplePropertyValueModel<BaseOverride>();
	}

	private ILabelProvider buildOverrideLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildOverrideDisplayString((BaseOverride) element);
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

	private ListValueModel<BaseOverride> buildOverridesListHolder() {
		List<ListValueModel<? extends BaseOverride>> list = new ArrayList<ListValueModel<? extends BaseOverride>>();
		list.add(buildSpecifiedAttributeOverridesListHolder());
		list.add(buildDefaultAttributeOverridesListHolder());
		list.add(buildSpecifiedAssociationOverridesListHolder());
		list.add(buildDefaultAssociationOverridesListHolder());
		return new CompositeListValueModel<ListValueModel<? extends BaseOverride>, BaseOverride>(list);
	}

	private ListValueModel<BaseOverride> buildOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<BaseOverride>(
			buildOverridesListHolder(),
			BaseOverride.NAME_PROPERTY
		);
	}

	private Transformer<BaseOverride, Control> buildPaneTransformer() {
		return new Transformer<BaseOverride, Control>() {
			public Control transform(BaseOverride override) {

				if (override instanceof AttributeOverride) {
					return columnPane;
				}

				if (override instanceof AssociationOverride) {
					return joinColumnsPane;
				}

				return null;
			}
		};
	}

	private ListValueModel<AssociationOverride> buildSpecifiedAssociationOverridesListHolder() {
		return new ListAspectAdapter<Entity, AssociationOverride>(getSubjectHolder(), Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AssociationOverride> listIterator_() {
				return subject.specifiedAssociationOverrides();
			}

			@Override
			protected int size_() {
				return subject.specifiedAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder() {
		return new ListAspectAdapter<Entity, AttributeOverride>(getSubjectHolder(), Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return subject.specifiedAttributeOverrides();
			}

			@Override
			protected int size_() {
				return subject.specifiedAttributeOverridesSize();
			}
		};
	}

	private void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInAssociationOverrideDialog dialog =
			new JoinColumnInAssociationOverrideDialog(
				shell(),
				(AssociationOverride) overrideHolder.value(),
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
		buildCheckBox(
			buildSubPane(columnPane, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			buildOverrideDefaultAttributeOverrideHolder()
		);

		// Column widgets (for IOverrideAttribute)
		ColumnComposite columnComposite = new ColumnComposite(
			this,
			buildColumnHolder(buildAttributeOverrideHolder()),
			columnPane,
			false
		);

		columnPane.setVisible(false);
		installColumnsPaneEnabler(columnComposite);
	}

	private void initializeJoinColumnsPane(PageBook pageBook) {

		joinColumnsPane = buildSubPane(pageBook);

		// Override Default check box
		buildCheckBox(
			buildSubPane(joinColumnsPane, 5, groupBoxMargin()),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			buildOverrideDefaultAssociationOverrideHolder()
		);

		Group joinColumnsGroupPane = buildTitledPane(
			joinColumnsPane,
			JptUiMappingsMessages.OverridesComposite_joinColumn
		);

		// Join Columns list pane (for IOverrideAssociation)
		JoinColumnsComposite<AssociationOverride> joinColumnsComposite =
			new JoinColumnsComposite<AssociationOverride>(
				this,
				buildAssociationOverrideHolder(),
				joinColumnsGroupPane,
				buildJoinColumnsEditor(),
				false
			);

		joinColumnsPane.setVisible(false);
		installJoinColumnsPaneEnabler(joinColumnsComposite);
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

	private AddRemoveListPane<Entity> initializeOverridesList(Composite container) {

		return new AddRemoveListPane<Entity>(
			this,
			buildSubPane(container, 8),
			buildOverridesAdapter(),
			buildOverridesListModel(),
			overrideHolder,
			buildOverrideLabelProvider(),
			JpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES
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

	private void installColumnsPaneEnabler(ColumnComposite pane) {
		new PaneEnabler(
			buildOverrideDefaultAttributeOverrideHolder(),
			pane
		);
	}

	private void installJoinColumnsPaneEnabler(JoinColumnsComposite<AssociationOverride> pane) {
		new PaneEnabler(
			buildOverrideDefaultAssociationOverrideHolder(),
			pane
		);
	}

	private void installOverrideControlSwitcher(PropertyValueModel<BaseOverride> overrideHolder,
	                                            PageBook pageBook) {

		new ControlSwitcher(
			overrideHolder,
			buildPaneTransformer(),
			pageBook
		);
	}

	private void removeOverrides(ObjectListSelectionModel listSelectionModel) {

		Entity subject = subject();
		Object[] selectedItems = listSelectionModel.selectedValues();

		for (int index = selectedItems.length; --index >= 0; ) {

			Object override = selectedItems[index];

			if (override instanceof AttributeOverride) {
				subject.removeSpecifiedAttributeOverride((AttributeOverride) override);
			}
			else if (override instanceof AssociationOverride) {
				subject.removeSpecifiedAssociationOverride((AssociationOverride) override);
			}
		}
	}

	private void updateAssociationOverride(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			Entity subject = subject();
			AssociationOverride override = (AssociationOverride) overrideHolder.value();

			// Add a new association override
			if (selected) {
				int index = subject.specifiedAssociationOverridesSize();
				AssociationOverride associationOverride = subject.addSpecifiedAssociationOverride(index);
				associationOverride.setName(override.getName());

				overrideHolder.setValue(associationOverride);
			}
			// Remove the specified association override
			else {
				String name = override.getName();
				subject.removeSpecifiedAssociationOverride(override);

				// Select the default association override
				for (Iterator<AssociationOverride> iter = subject.defaultAssociationOverrides(); iter.hasNext(); ) {
					AssociationOverride associationOverride = iter.next();

					if (associationOverride.getName().equals(name)) {
						overrideHolder.setValue(associationOverride);
						break;
					}
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private void updateAttributeOverride(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			Entity subject = subject();
			AttributeOverride override = (AttributeOverride) overrideHolder.value();

			// Add a new attribute override
			if (selected) {
				int index = subject.specifiedAttributeOverridesSize();
				AttributeOverride attributeOverride = subject.addSpecifiedAttributeOverride(index);
				attributeOverride.setName(override.getName());
				attributeOverride.getColumn().setSpecifiedName(override.getColumn().getName());

				overrideHolder.setValue(attributeOverride);
			}
			// Remove the specified attribute override
			else {
				String name = override.getName();
				subject.removeSpecifiedAttributeOverride(override);

				// Select the default attribute override
				for (Iterator<AttributeOverride> iter = subject.defaultAttributeOverrides(); iter.hasNext(); ) {
					AttributeOverride attributeOverride = iter.next();

					if (attributeOverride.getName().equals(name)) {
						overrideHolder.setValue(attributeOverride);
						break;
					}
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<AssociationOverride> {

		public void addJoinColumn(AssociationOverride subject) {
			OverridesComposite.this.addJoinColumn(subject);
		}

		public JoinColumn defaultJoinColumn(AssociationOverride subject) {
			return null;
		}

		public String defaultPropertyName() {
			return AssociationOverride.DEFAULT_JOIN_COLUMNS_LIST;
		}

		public void editJoinColumn(AssociationOverride subject, JoinColumn joinColumn) {
			OverridesComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(AssociationOverride subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public void removeJoinColumns(AssociationOverride subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(AssociationOverride subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(AssociationOverride subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return AssociationOverride.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
}