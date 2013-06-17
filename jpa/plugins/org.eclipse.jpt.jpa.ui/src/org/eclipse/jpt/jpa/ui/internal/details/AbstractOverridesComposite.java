/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.swt.bind.SWTBindTools;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ModifiablePropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.SpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.SpecifiedOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

@SuppressWarnings("nls")
public abstract class AbstractOverridesComposite<T extends JpaContextModel>
	extends Pane<T>
{
	private Pane<AttributeOverride> attributeOverridePane;
	private Pane<AssociationOverride> associationOverridePane;
	
	private ModifiablePropertyValueModel<Override_> selectedOverrideModel;
	private ModifiablePropertyValueModel<Boolean> overrideVirtualOverrideHolder;
	
	
	protected AbstractOverridesComposite(
			Pane<? extends T> parentPane,
			Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.selectedOverrideModel = this.buildSelectedOverrideModel();
	}

	private ModifiablePropertyValueModel<Override_> buildSelectedOverrideModel() {
		return new SimplePropertyValueModel<Override_>();
	}
	
	protected abstract boolean supportsAssociationOverrides();

	@Override
	protected void initializeLayout(Composite container) {
		// Overrides list pane
		initializeOverridesList(container);
		
		// Override Default check box
		Button overrideCheckBox = addCheckBox(
			container,
				JptJpaUiDetailsMessages.OVERRIDES_COMPOSITE_OVERRIDE_DEFAULT,
				getOverrideVirtualOverrideHolder(),
				null);
		SWTBindTools.controlVisibleState(buildSelectedOverrideBooleanHolder(), overrideCheckBox);
		
		// Property pane
		PageBook pageBook = addPageBook(container);
		installOverrideControlSwitcher(this.selectedOverrideModel, pageBook);
	}
	
	private PropertyValueModel<Boolean> buildSelectedOverrideBooleanHolder() {
		return new TransformationPropertyValueModel<Override_, Boolean>(this.selectedOverrideModel) {
			@Override
			protected Boolean transform(Override_ value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	
	private void initializeOverridesList(Composite container) {
		new AddRemoveListPane<T, Override_>(
				this,
				container,
				buildOverridesAdapter(),
				buildOverridesListModel(),
				new ModifiablePropertyCollectionValueModelAdapter<Override_>(this.selectedOverrideModel),
				buildOverrideLabelProvider(),
				JpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES) {
			
			@Override
			protected void initializeButtonPane(Composite c, String helpId) {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}
		};
	}

	protected Pane<AttributeOverride> getAttributeOverridePane(PageBook pageBook) {
		if (this.attributeOverridePane == null) {
			PropertyValueModel<AttributeOverride>  attributeOverrideHolder = buildAttributeOverrideHolder();
			this.attributeOverridePane = buildAttributeOverridePane(pageBook, attributeOverrideHolder);
		}
		return this.attributeOverridePane;
	}
	
	protected Pane<AttributeOverride> buildAttributeOverridePane(PageBook pageBook, PropertyValueModel<AttributeOverride> overrideHolder) {
		return new AttributeOverrideComposite(this, overrideHolder, buildOverrideBooleanHolder(overrideHolder), pageBook);
	}
	
	private PropertyValueModel<Boolean> buildOverrideBooleanHolder(PropertyValueModel<? extends Override_> overrideHolder) {
		return new TransformationPropertyValueModel<Override_, Boolean>(overrideHolder) {
			@Override
			protected Boolean transform_(Override_ v) {
				return Boolean.valueOf(!v.isVirtual());
			}
		};
	}

	protected Pane<AssociationOverride> getAssociationOverridePane(PageBook pageBook) {
		if (this.associationOverridePane == null) {
			PropertyValueModel<AssociationOverride> associationOverrideModel = buildAssociationOverrideModel();
			this.associationOverridePane = buildAssociationOverridePane(pageBook, associationOverrideModel);
		}
		return this.associationOverridePane;
	}

	protected Pane<AssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<AssociationOverride> overrideHolder) {
		return new AssociationOverrideComposite(this, overrideHolder, buildOverrideBooleanHolder(overrideHolder), pageBook);		
	}
	
	private void installOverrideControlSwitcher(PropertyValueModel<Override_> overrideHolder, PageBook pageBook) {
		SWTBindTools.bind(overrideHolder, buildPaneTransformer(pageBook), pageBook);
	}
	
	private PropertyValueModel<AssociationOverride> buildAssociationOverrideModel() {
		return new TransformationPropertyValueModel<Override_, AssociationOverride>(this.selectedOverrideModel) {
			@Override
			protected AssociationOverride transform_(Override_ v) {
				return (v instanceof AssociationOverride) ? (AssociationOverride) v : null;
			}
		};
	}
	
	private PropertyValueModel<AttributeOverride> buildAttributeOverrideHolder() {
		return new TransformationPropertyValueModel<Override_, AttributeOverride>(this.selectedOverrideModel) {
			@Override
			protected AttributeOverride transform_(Override_ v) {
				return (v instanceof AttributeOverride) ? (AttributeOverride) v : null;
			}
		};
	}
	
	private ListValueModel<VirtualAssociationOverride> buildDefaultAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, VirtualAssociationOverride>(containerHolder, OverrideContainer.VIRTUAL_OVERRIDES_LIST) {
			@Override
			protected ListIterable<VirtualAssociationOverride> getListIterable() {
				return new SuperListIterableWrapper<VirtualAssociationOverride>(this.subject.getVirtualOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.getVirtualOverridesSize();
			}
		};
	}
	
	private ListValueModel<VirtualAttributeOverride> buildDefaultAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, VirtualAttributeOverride>(containerHolder, OverrideContainer.VIRTUAL_OVERRIDES_LIST) {
			@Override
			protected ListIterable<VirtualAttributeOverride> getListIterable() {
				return new SuperListIterableWrapper<VirtualAttributeOverride>(this.subject.getVirtualOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.getVirtualOverridesSize();
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> getOverrideVirtualOverrideHolder() {
		if (this.overrideVirtualOverrideHolder == null) {
			this.overrideVirtualOverrideHolder = buildOverrideVirtualOverrideHolder();
		}
		return this.overrideVirtualOverrideHolder;
	}
	
	private ModifiablePropertyValueModel<Boolean> buildOverrideVirtualOverrideHolder() {
		return new TransformationModifiablePropertyValueModel<Override_, Boolean>(this.selectedOverrideModel) {
			@Override
			public void setValue(Boolean value) {
				updateOverride(value.booleanValue());
			}
			
			@Override
			protected Boolean transform_(Override_ v) {
				return Boolean.valueOf( ! v.isVirtual());
			}
		};
	}
	
	String buildOverrideDisplayString(Override_ override) {
		String overrideType;
		
		// Retrieve the type
		if (override instanceof AssociationOverride) {
			overrideType = JptJpaUiDetailsMessages.OVERRIDES_COMPOSITE_ASSOCIATION;
		}
		else {
			overrideType = JptJpaUiDetailsMessages.OVERRIDES_COMPOSITE_ATTRIBUTE;
		}
		
		// Format the name
		String name = override.getName();
		
		if (StringTools.isBlank(name)) {
			name = JptJpaUiDetailsMessages.OVERRIDES_COMPOSITE_NO_NAME;
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
				return buildOverrideDisplayString((Override_) element);
			}
		};
	}
	
	protected Adapter<Override_> buildOverridesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<Override_>() {
			public Override_ addNewItem() {
				//no way to add/remove/edit overrides, they are all defaulted in
				throw new UnsupportedOperationException();
			}
			public void removeSelectedItems(CollectionValueModel<Override_> selectedItemsModel) {
				//no way to add/remove/edit overrides, they are all defaulted in
				throw new UnsupportedOperationException();
			}
		};
	}
	
	protected ListValueModel<Override_> buildOverridesListHolder() {
		PropertyValueModel<AttributeOverrideContainer> attributeOverrideContainerHolder = buildAttributeOverrideContainerHolder();
		List<ListValueModel<? extends Override_>> list = new ArrayList<ListValueModel<? extends Override_>>();
		
		list.add(buildSpecifiedAttributeOverridesListHolder(attributeOverrideContainerHolder));
		list.add(buildDefaultAttributeOverridesListHolder(attributeOverrideContainerHolder));
		
		if (supportsAssociationOverrides()) {
			PropertyValueModel<AssociationOverrideContainer> associationOverrideContainerHolder = buildAssociationOverrideContainerHolder();
			list.add(buildSpecifiedAssociationOverridesListHolder(associationOverrideContainerHolder));
			list.add(buildDefaultAssociationOverridesListHolder(associationOverrideContainerHolder));
		}
		
		return CompositeListValueModel.forModels(list);
	}
	
	protected abstract PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerHolder();
	
	protected abstract PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerHolder();
	
	private ListValueModel<Override_> buildOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<Override_>(
				buildOverridesListHolder(),
				Override_.NAME_PROPERTY);
	}
	
	private Transformer<Override_, Control> buildPaneTransformer(final PageBook pageBook) {
		return new PaneTransformer(pageBook);
	}
	
	protected class PaneTransformer
		extends TransformerAdapter<Override_, Control>
	{
		private final PageBook pageBook;

		protected PaneTransformer(PageBook pageBook) {
			this.pageBook = pageBook;
		}

		@Override
		public Control transform(Override_ override) {
			return AbstractOverridesComposite.this.transformSelectedOverride(override, this.pageBook);
		}
	}

	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformSelectedOverride(Override_ selectedOverride, PageBook pageBook) {
		if (selectedOverride instanceof AttributeOverride) {
			return AbstractOverridesComposite.this.getAttributeOverridePane(pageBook).getControl();
		}
		
		if (selectedOverride instanceof AssociationOverride) {
			return AbstractOverridesComposite.this.getAssociationOverridePane(pageBook).getControl();
		}
		
		return null;
	}
	
	private ListValueModel<SpecifiedAssociationOverride> buildSpecifiedAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, SpecifiedAssociationOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterable<SpecifiedAssociationOverride> getListIterable() {
				return new SuperListIterableWrapper<SpecifiedAssociationOverride>(this.subject.getSpecifiedOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.getSpecifiedOverridesSize();
			}
		};
	}
	
	private ListValueModel<SpecifiedAttributeOverride> buildSpecifiedAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, SpecifiedAttributeOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterable<SpecifiedAttributeOverride> getListIterable() {
				return new SuperListIterableWrapper<SpecifiedAttributeOverride>(this.subject.getSpecifiedOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.getSpecifiedOverridesSize();
			}
		};
	}
	
	void updateOverride(boolean convertToSpecified) {
		if (isPopulating()) {
			return;
		}
		
		setPopulating(true);
		
		try {
			Override_ override = this.selectedOverrideModel.getValue();
			
			Override_ newOverride = convertToSpecified ?
					((VirtualOverride) override).convertToSpecified() :
					((SpecifiedOverride) override).convertToVirtual();
			this.selectedOverrideModel.setValue(newOverride);
		}
		finally {
			setPopulating(false);
		}
	}
}
