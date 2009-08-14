/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
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
import org.eclipse.jpt.core.context.JoinColumnJoiningStrategy;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationWritablePropertyValueModel;
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
 * @see Entity
 * @see EntityComposite - The parent container
 * @see ColumnComposite
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OverridesComposite extends FormPane<Entity>
{
	private Composite columnPane;
	private Composite joinColumnsPane;
	private JoinColumnsComposite<JoinColumnJoiningStrategy> joinColumnsComposite;
	private WritablePropertyValueModel<BaseOverride> selectedOverrideHolder;
	private WritablePropertyValueModel<Boolean> overrideVirtualOverrideHolder;

	private WritablePropertyValueModel<JoinColumnJoiningStrategy> selectedJoinColumnJoiningStrategyHolder;
	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public OverridesComposite(FormPane<? extends Entity> parentPane,
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

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedOverrideHolder = buildSelectedOverrideHolder();
		this.selectedJoinColumnJoiningStrategyHolder = buildJoinColumnJoiningStrategyHolder();
	}

	private WritablePropertyValueModel<BaseOverride> buildSelectedOverrideHolder() {
		return new SimplePropertyValueModel<BaseOverride>();
	}


	@Override
	protected void initializeLayout(Composite container) {

		// Overrides group pane
		container = addTitledGroup(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverridesGroup
		);

		// Overrides list pane
		initializeOverridesList(container);

		int groupBoxMargin = getGroupBoxMargin();
		
		// Override Default check box
		Button overrideCheckBox = addCheckBox(
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			getOverrideVirtualOverrideHolder(),
			null
		);
		SWTTools.controlVisibleState(buildSelectedOverrideBooleanHolder(), overrideCheckBox);
		
		// Property pane
		PageBook pageBook = addPageBook(container);
		initializeJoinColumnsPane(pageBook);
		initializeColumnPane(pageBook);
		installOverrideControlSwitcher(this.selectedOverrideHolder, pageBook);
	}

	private PropertyValueModel<Boolean> buildSelectedOverrideBooleanHolder() {
		return new TransformationPropertyValueModel<BaseOverride, Boolean>(this.selectedOverrideHolder) {
			@Override
			protected Boolean transform(BaseOverride value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	
	private AddRemoveListPane<Entity> initializeOverridesList(Composite container) {

		return new AddRemoveListPane<Entity>(
			this,
			addSubPane(container, 8),
			buildOverridesAdapter(),
			buildOverridesListModel(),
			this.selectedOverrideHolder,
			buildOverrideLabelProvider(),
			JpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES
		)
		{
			@Override
			protected void initializeButtonPane(Composite container, String helpId) {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}

			@Override
			protected void updateButtons() {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}
		};
	}
	
	private void initializeColumnPane(PageBook pageBook) {
		this.columnPane = addSubPane(pageBook, 5);

		// Column widgets (for IOverrideAttribute)
		ColumnComposite columnComposite = new ColumnComposite(
			this,
			buildColumnHolder(buildAttributeOverrideHolder()),
			this.columnPane,
			false
		);

		installColumnsPaneEnabler(columnComposite, buildAttributeOverrideHolder());
	}

	private void installColumnsPaneEnabler(ColumnComposite pane, PropertyValueModel<AttributeOverride> overrideHolder) {
		new PaneEnabler(
			buildOverrideBooleanHolder(overrideHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildOverrideBooleanHolder(PropertyValueModel<? extends BaseOverride> overrideHolder) {
		return new TransformationPropertyValueModel<BaseOverride, Boolean>(overrideHolder) {
			@Override
			protected Boolean transform_(BaseOverride value) {
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}

	private void initializeJoinColumnsPane(PageBook pageBook) {
		this.joinColumnsPane = addSubPane(pageBook);

		Group joinColumnsGroupPane = addTitledGroup(
			this.joinColumnsPane,
			JptUiMappingsMessages.OverridesComposite_joinColumn
		);

		// Join Columns list pane (for AssociationOverride)
		this.joinColumnsComposite =
			new JoinColumnsComposite<JoinColumnJoiningStrategy>(
				this,
				this.selectedJoinColumnJoiningStrategyHolder,
				joinColumnsGroupPane,
				buildJoinColumnsEditor(),
				false
			);

		installJoinColumnsPaneEnabler(this.joinColumnsComposite, buildAssociationOverrideHolder());
	}

	private void installJoinColumnsPaneEnabler(JoinColumnsComposite<JoinColumnJoiningStrategy> pane, PropertyValueModel<AssociationOverride> overrideHolder) {
		pane.installJoinColumnsPaneEnabler(buildOverrideBooleanHolder(overrideHolder));
	}

	private void installOverrideControlSwitcher(PropertyValueModel<BaseOverride> overrideHolder,
	                                            PageBook pageBook) {

		new ControlSwitcher(
			overrideHolder,
			buildPaneTransformer(),
			pageBook
		);
	}

	private void addJoinColumn(JoinColumnJoiningStrategy subject) {

		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(getShell(), subject, null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private PostExecution<JoinColumnInJoiningStrategyDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private void addJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {

		JoinColumnJoiningStrategy joiningStrategy = stateObject.getOwner();
		int index = joiningStrategy.specifiedJoinColumnsSize();

		JoinColumn joinColumn = joiningStrategy.addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}

	private WritablePropertyValueModel<JoinColumnJoiningStrategy> buildJoinColumnJoiningStrategyHolder() {
		return new TransformationWritablePropertyValueModel<AssociationOverride, JoinColumnJoiningStrategy>(this.buildAssociationOverrideHolder()) {
			@Override
			protected JoinColumnJoiningStrategy transform_(AssociationOverride value) {
				return value.getRelationshipReference().getJoinColumnJoiningStrategy();
			}
		};
	}
	
	private WritablePropertyValueModel<AssociationOverride> buildAssociationOverrideHolder() {
		return new TransformationWritablePropertyValueModel<BaseOverride, AssociationOverride>(this.selectedOverrideHolder) {
			@Override
			protected AssociationOverride transform_(BaseOverride value) {
				return (value instanceof AssociationOverride) ? (AssociationOverride) value : null;
			}
		};
	}
	
	private WritablePropertyValueModel<AttributeOverride> buildAttributeOverrideHolder() {
		return new TransformationWritablePropertyValueModel<BaseOverride, AttributeOverride>(this.selectedOverrideHolder) {
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
		return new ListAspectAdapter<Entity, AssociationOverride>(getSubjectHolder(), Entity.VIRTUAL_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AssociationOverride> listIterator_() {
				return this.subject.virtualAssociationOverrides();
			}

			@Override
			protected int size_() {
				return this.subject.virtualAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<AttributeOverride> buildDefaultAttributeOverridesListHolder() {
		return new ListAspectAdapter<Entity, AttributeOverride>(getSubjectHolder(), Entity.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return this.subject.virtualAttributeOverrides();
			}

			@Override
			protected int size_() {
				return this.subject.virtualAttributeOverridesSize();
			}
		};
	}

	private PostExecution<JoinColumnInJoiningStrategyDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoiningStrategyDialog>() {
			public void execute(JoinColumnInJoiningStrategyDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	protected WritablePropertyValueModel<Boolean> getOverrideVirtualOverrideHolder() {
		if (this.overrideVirtualOverrideHolder == null) {
			this.overrideVirtualOverrideHolder = buildOverrideVirtualOverrideHolder();
		}
		return this.overrideVirtualOverrideHolder;
	}


	private WritablePropertyValueModel<Boolean> buildOverrideVirtualOverrideHolder() {
		return new CachingTransformationWritablePropertyValueModel<BaseOverride, Boolean>(this.selectedOverrideHolder) {
			@Override
			public void setValue(Boolean value) {
				updateOverride(value.booleanValue());
			}

			@Override
			protected Boolean transform_(BaseOverride value) {
				return Boolean.valueOf(!value.isVirtual());
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
				//no way to add/remove/edit overrides, they are all defaulted in
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				//no way to add/remove/edit overrides, they are all defaulted in
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
					return OverridesComposite.this.columnPane;
				}

				if (override instanceof AssociationOverride) {
					return OverridesComposite.this.joinColumnsPane;
				}

				return null;
			}
		};
	}

	private ListValueModel<AssociationOverride> buildSpecifiedAssociationOverridesListHolder() {
		return new ListAspectAdapter<Entity, AssociationOverride>(getSubjectHolder(), Entity.SPECIFIED_ASSOCIATION_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AssociationOverride> listIterator_() {
				return this.subject.specifiedAssociationOverrides();
			}

			@Override
			protected int size_() {
				return this.subject.specifiedAssociationOverridesSize();
			}
		};
	}

	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder() {
		return new ListAspectAdapter<Entity, AttributeOverride>(getSubjectHolder(), Entity.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return this.subject.specifiedAttributeOverrides();
			}

			@Override
			protected int size_() {
				return this.subject.specifiedAttributeOverridesSize();
			}
		};
	}

	private void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInJoiningStrategyDialog dialog =
			new JoinColumnInJoiningStrategyDialog(
				getShell(),
				this.selectedJoinColumnJoiningStrategyHolder.getValue(),
				joinColumn
			);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInJoiningStrategyStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}


	private void updateOverride(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			BaseOverride override = this.selectedOverrideHolder.getValue();

			BaseOverride newOverride = override.setVirtual(!selected);
			this.selectedOverrideHolder.setValue(newOverride);
		}
		finally {
			setPopulating(false);
		}
	}

	private class JoinColumnsProvider implements JoinColumnsEditor<JoinColumnJoiningStrategy> {

		public void addJoinColumn(JoinColumnJoiningStrategy subject) {
			OverridesComposite.this.addJoinColumn(subject);
		}

		public JoinColumn getDefaultJoinColumn(JoinColumnJoiningStrategy subject) {
			//association overrides have no default join column
			return null;
		}

		public String getDefaultPropertyName() {
			return JoinColumnJoiningStrategy.DEFAULT_JOIN_COLUMN_PROPERTY;
		}

		public void editJoinColumn(JoinColumnJoiningStrategy subject, JoinColumn joinColumn) {
			OverridesComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinColumnJoiningStrategy subject) {
			return subject.hasSpecifiedJoinColumns();
		}

		public void removeJoinColumns(JoinColumnJoiningStrategy subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinColumnJoiningStrategy subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinColumnJoiningStrategy subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String getSpecifiedJoinColumnsListPropertyName() {
			return JoinColumnJoiningStrategy.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
}