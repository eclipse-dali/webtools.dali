/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationPropertyValueModel;
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
 * | | | PageBook (attribute/association override composite)               | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 3.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class AbstractOverridesComposite<T extends JpaContextNode> extends FormPane<T>
{
	private Pane<AttributeOverride> attributeOverridePane;
	private Pane<AssociationOverride> associationOverridePane;
	
	private WritablePropertyValueModel<BaseOverride> selectedOverrideHolder;
	private WritablePropertyValueModel<Boolean> overrideVirtualOverrideHolder;

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	protected AbstractOverridesComposite(FormPane<? extends T> parentPane,
	                          Composite parent) {

		super(parentPane, parent, false);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedOverrideHolder = buildSelectedOverrideHolder();
	}

	private WritablePropertyValueModel<BaseOverride> buildSelectedOverrideHolder() {
		return new SimplePropertyValueModel<BaseOverride>();
	}

	protected abstract boolean supportsAssociationOverrides();

	@Override
	protected void initializeLayout(Composite container) {

		// Overrides group pane
		container = addTitledGroup(
			container,
			JptUiDetailsMessages.OverridesComposite_attributeOverridesGroup
		);

		// Overrides list pane
		initializeOverridesList(container);

		int groupBoxMargin = getGroupBoxMargin();
		
		// Override Default check box
		Button overrideCheckBox = addCheckBox(
			addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiDetailsMessages.OverridesComposite_overrideDefault,
			getOverrideVirtualOverrideHolder(),
			null
		);
		SWTTools.controlVisibleState(buildSelectedOverrideBooleanHolder(), overrideCheckBox);
		
		// Property pane
		PageBook pageBook = addPageBook(container);
		initializeOverridePanes(pageBook);
		installOverrideControlSwitcher(this.selectedOverrideHolder, pageBook);
	}
	
	protected void initializeOverridePanes(PageBook pageBook) {
		initializeAttributeOverridePane(pageBook);
		if (supportsAssociationOverrides()) {
			initializeAssociationOverridePane(pageBook);
		}
	}
	
	private PropertyValueModel<Boolean> buildSelectedOverrideBooleanHolder() {
		return new TransformationPropertyValueModel<BaseOverride, Boolean>(this.selectedOverrideHolder) {
			@Override
			protected Boolean transform(BaseOverride value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	
	private void initializeOverridesList(Composite container) {

		new AddRemoveListPane<T>(
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
	
	protected void initializeAttributeOverridePane(PageBook pageBook) {
		PropertyValueModel<AttributeOverride>  attributeOverrideHolder = buildAttributeOverrideHolder();
		this.attributeOverridePane = buildAttributeOverridePane(pageBook, attributeOverrideHolder);
		installAttributeOverridePaneEnabler(this.attributeOverridePane, attributeOverrideHolder);
	}

	protected Pane<AttributeOverride> buildAttributeOverridePane(PageBook pageBook, PropertyValueModel<AttributeOverride> attributeOverrideHolder) {
		return new AttributeOverrideComposite(this, attributeOverrideHolder, pageBook);
	}
	
	private void installAttributeOverridePaneEnabler(Pane<AttributeOverride> pane, PropertyValueModel<AttributeOverride> overrideHolder) {
		new PaneEnabler(
			buildOverrideBooleanHolder(overrideHolder),
			pane
		);
	}

	private PropertyValueModel<Boolean> buildOverrideBooleanHolder(PropertyValueModel<? extends BaseOverride> overrideHolder) {
		return new CachingTransformationPropertyValueModel<BaseOverride, Boolean>(overrideHolder) {
			@Override
			protected Boolean transform_(BaseOverride value) {
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}
	
	protected void initializeAssociationOverridePane(PageBook pageBook) {
		PropertyValueModel<AssociationOverride>  associationOverrideHolder = buildAssociationOverrideHolder();
		this.associationOverridePane = buildAssociationOverridePane(pageBook, associationOverrideHolder);
		installAssociationOverridePaneEnabler(this.associationOverridePane, associationOverrideHolder);
	}

	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> associationOverrideHolder) {
		return new AssociationOverrideComposite(this, associationOverrideHolder, pageBook);		
	}

	private void installAssociationOverridePaneEnabler(Pane<AssociationOverride> pane, PropertyValueModel<AssociationOverride> overrideHolder) {
		new PaneEnabler(
			buildOverrideBooleanHolder(overrideHolder),
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
	
	private ListValueModel<AssociationOverride> buildDefaultAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, AssociationOverride>(containerHolder, AssociationOverrideContainer.VIRTUAL_ASSOCIATION_OVERRIDES_LIST) {
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

	private ListValueModel<AttributeOverride> buildDefaultAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, AttributeOverride>(containerHolder, AttributeOverrideContainer.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST) {
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
			overrideType = JptUiDetailsMessages.OverridesComposite_association;
		}
		else {
			overrideType = JptUiDetailsMessages.OverridesComposite_attribute;
		}

		// Format the name
		String name = override.getName();

		if (StringTools.stringIsEmpty(name)) {
			name = JptUiDetailsMessages.OverridesComposite_noName;
		}

		// Format: <name> (Attribute/Association Override)
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(" (");
		sb.append(overrideType);
		sb.append(") ");
		return sb.toString();
	}

	protected ILabelProvider buildOverrideLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildOverrideDisplayString((BaseOverride) element);
			}
		};
	}

	protected Adapter buildOverridesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				//no way to add/remove/edit overrides, they are all defaulted in
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				//no way to add/remove/edit overrides, they are all defaulted in
			}
		};
	}

	protected ListValueModel<BaseOverride> buildOverridesListHolder() {
		PropertyValueModel<AttributeOverrideContainer> attributeOverrideContainerHolder = buildAttributeOverrideContainerHolder();
		List<ListValueModel<? extends BaseOverride>> list = new ArrayList<ListValueModel<? extends BaseOverride>>();
		
		list.add(buildSpecifiedAttributeOverridesListHolder(attributeOverrideContainerHolder));
		list.add(buildDefaultAttributeOverridesListHolder(attributeOverrideContainerHolder));
		
		if (supportsAssociationOverrides()) {
			PropertyValueModel<AssociationOverrideContainer> associationOverrideContainerHolder = buildAssociationOverrideContainerHolder();
			list.add(buildSpecifiedAssociationOverridesListHolder(associationOverrideContainerHolder));
			list.add(buildDefaultAssociationOverridesListHolder(associationOverrideContainerHolder));
		}
		
		return new CompositeListValueModel<ListValueModel<? extends BaseOverride>, BaseOverride>(list);
	}

	protected abstract PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerHolder();

	protected abstract PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerHolder();

	private ListValueModel<BaseOverride> buildOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<BaseOverride>(
			buildOverridesListHolder(),
			BaseOverride.NAME_PROPERTY
		);
	}

	private Transformer<BaseOverride, Control> buildPaneTransformer() {
		return new Transformer<BaseOverride, Control>() {
			public Control transform(BaseOverride override) {
				return AbstractOverridesComposite.this.transformSelectedOverride(override);
			}
		};
	}

	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformSelectedOverride(BaseOverride selectedOverride) {
		if (selectedOverride instanceof AttributeOverride) {
			return AbstractOverridesComposite.this.attributeOverridePane.getControl();
		}

		if (selectedOverride instanceof AssociationOverride) {
			return AbstractOverridesComposite.this.associationOverridePane.getControl();
		}

		return null;
	}
	
	private ListValueModel<AssociationOverride> buildSpecifiedAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, AssociationOverride>(containerHolder, AssociationOverrideContainer.SPECIFIED_ASSOCIATION_OVERRIDES_LIST) {
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

	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, AttributeOverride>(containerHolder, AttributeOverrideContainer.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST) {
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
}