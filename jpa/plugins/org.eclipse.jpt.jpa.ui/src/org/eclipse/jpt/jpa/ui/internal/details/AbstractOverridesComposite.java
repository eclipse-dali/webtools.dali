/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ModifiablePropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.AttributeOverride;
import org.eclipse.jpt.jpa.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.OverrideContainer;
import org.eclipse.jpt.jpa.core.context.Override_;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.jpa.core.context.ReadOnlyOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.VirtualOverride;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

@SuppressWarnings("nls")
public abstract class AbstractOverridesComposite<T extends JpaContextNode>
	extends Pane<T>
{
	private Pane<ReadOnlyAttributeOverride> attributeOverridePane;
	private Pane<ReadOnlyAssociationOverride> associationOverridePane;
	
	private ModifiablePropertyValueModel<ReadOnlyOverride> selectedOverrideModel;
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

	private ModifiablePropertyValueModel<ReadOnlyOverride> buildSelectedOverrideModel() {
		return new SimplePropertyValueModel<ReadOnlyOverride>();
	}
	
	protected abstract boolean supportsAssociationOverrides();

	@Override
	protected void initializeLayout(Composite container) {
		// Overrides list pane
		initializeOverridesList(container);
		
		// Override Default check box
		Button overrideCheckBox = addCheckBox(
			container,
				JptUiDetailsMessages.OverridesComposite_overrideDefault,
				getOverrideVirtualOverrideHolder(),
				null);
		SWTTools.controlVisibleState(buildSelectedOverrideBooleanHolder(), overrideCheckBox);
		
		// Property pane
		PageBook pageBook = addPageBook(container);
		installOverrideControlSwitcher(this.selectedOverrideModel, pageBook);
	}
	
	private PropertyValueModel<Boolean> buildSelectedOverrideBooleanHolder() {
		return new TransformationPropertyValueModel<ReadOnlyOverride, Boolean>(this.selectedOverrideModel) {
			@Override
			protected Boolean transform(ReadOnlyOverride value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
	
	private void initializeOverridesList(Composite container) {
		new AddRemoveListPane<T, ReadOnlyOverride>(
				this,
				container,
				buildOverridesAdapter(),
				buildOverridesListModel(),
				new ModifiablePropertyCollectionValueModelAdapter<ReadOnlyOverride>(this.selectedOverrideModel),
				buildOverrideLabelProvider(),
				JpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES) {
			
			@Override
			protected void initializeButtonPane(Composite c, String helpId) {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}
		};
	}

	protected Pane<ReadOnlyAttributeOverride> getAttributeOverridePane(PageBook pageBook) {
		if (this.attributeOverridePane == null) {
			PropertyValueModel<ReadOnlyAttributeOverride>  attributeOverrideHolder = buildAttributeOverrideHolder();
			this.attributeOverridePane = buildAttributeOverridePane(pageBook, attributeOverrideHolder);
		}
		return this.attributeOverridePane;
	}
	
	protected Pane<ReadOnlyAttributeOverride> buildAttributeOverridePane(PageBook pageBook, PropertyValueModel<ReadOnlyAttributeOverride> overrideHolder) {
		return new AttributeOverrideComposite(this, overrideHolder, buildOverrideBooleanHolder(overrideHolder), pageBook);
	}
	
	private PropertyValueModel<Boolean> buildOverrideBooleanHolder(PropertyValueModel<? extends ReadOnlyOverride> overrideHolder) {
		return new TransformationPropertyValueModel<ReadOnlyOverride, Boolean>(overrideHolder) {
			@Override
			protected Boolean transform_(ReadOnlyOverride v) {
				return Boolean.valueOf(!v.isVirtual());
			}
		};
	}

	protected Pane<ReadOnlyAssociationOverride> getAssociationOverridePane(PageBook pageBook) {
		if (this.associationOverridePane == null) {
			PropertyValueModel<ReadOnlyAssociationOverride> associationOverrideModel = buildAssociationOverrideModel();
			this.associationOverridePane = buildAssociationOverridePane(pageBook, associationOverrideModel);
		}
		return this.associationOverridePane;
	}

	protected Pane<ReadOnlyAssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<ReadOnlyAssociationOverride> overrideHolder) {
		return new AssociationOverrideComposite(this, overrideHolder, buildOverrideBooleanHolder(overrideHolder), pageBook);		
	}
	
	private void installOverrideControlSwitcher(
			PropertyValueModel<ReadOnlyOverride> overrideHolder,
			PageBook pageBook) {
		
		new ControlSwitcher(
				overrideHolder,
				buildPaneTransformer(pageBook),
				pageBook);
	}
	
	private PropertyValueModel<ReadOnlyAssociationOverride> buildAssociationOverrideModel() {
		return new TransformationPropertyValueModel<ReadOnlyOverride, ReadOnlyAssociationOverride>(this.selectedOverrideModel) {
			@Override
			protected ReadOnlyAssociationOverride transform_(ReadOnlyOverride v) {
				return (v instanceof ReadOnlyAssociationOverride) ? (ReadOnlyAssociationOverride) v : null;
			}
		};
	}
	
	private PropertyValueModel<ReadOnlyAttributeOverride> buildAttributeOverrideHolder() {
		return new TransformationPropertyValueModel<ReadOnlyOverride, ReadOnlyAttributeOverride>(this.selectedOverrideModel) {
			@Override
			protected ReadOnlyAttributeOverride transform_(ReadOnlyOverride v) {
				return (v instanceof ReadOnlyAttributeOverride) ? (ReadOnlyAttributeOverride) v : null;
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
		return new TransformationWritablePropertyValueModel<ReadOnlyOverride, Boolean>(this.selectedOverrideModel) {
			@Override
			public void setValue(Boolean value) {
				updateOverride(value.booleanValue());
			}
			
			@Override
			protected Boolean transform_(ReadOnlyOverride v) {
				return Boolean.valueOf( ! v.isVirtual());
			}
		};
	}
	
	String buildOverrideDisplayString(ReadOnlyOverride override) {
		String overrideType;
		
		// Retrieve the type
		if (override instanceof ReadOnlyAssociationOverride) {
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
				return buildOverrideDisplayString((ReadOnlyOverride) element);
			}
		};
	}
	
	protected Adapter<ReadOnlyOverride> buildOverridesAdapter() {
		return new AddRemoveListPane.AbstractAdapter<ReadOnlyOverride>() {
			public ReadOnlyOverride addNewItem() {
				//no way to add/remove/edit overrides, they are all defaulted in
				throw new UnsupportedOperationException();
			}
			public void removeSelectedItems(CollectionValueModel<ReadOnlyOverride> selectedItemsModel) {
				//no way to add/remove/edit overrides, they are all defaulted in
				throw new UnsupportedOperationException();
			}
		};
	}
	
	protected ListValueModel<ReadOnlyOverride> buildOverridesListHolder() {
		PropertyValueModel<AttributeOverrideContainer> attributeOverrideContainerHolder = buildAttributeOverrideContainerHolder();
		List<ListValueModel<? extends ReadOnlyOverride>> list = new ArrayList<ListValueModel<? extends ReadOnlyOverride>>();
		
		list.add(buildSpecifiedAttributeOverridesListHolder(attributeOverrideContainerHolder));
		list.add(buildDefaultAttributeOverridesListHolder(attributeOverrideContainerHolder));
		
		if (supportsAssociationOverrides()) {
			PropertyValueModel<AssociationOverrideContainer> associationOverrideContainerHolder = buildAssociationOverrideContainerHolder();
			list.add(buildSpecifiedAssociationOverridesListHolder(associationOverrideContainerHolder));
			list.add(buildDefaultAssociationOverridesListHolder(associationOverrideContainerHolder));
		}
		
		return new CompositeListValueModel<ListValueModel<? extends ReadOnlyOverride>, ReadOnlyOverride>(list);
	}
	
	protected abstract PropertyValueModel<AttributeOverrideContainer> buildAttributeOverrideContainerHolder();
	
	protected abstract PropertyValueModel<AssociationOverrideContainer> buildAssociationOverrideContainerHolder();
	
	private ListValueModel<ReadOnlyOverride> buildOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<ReadOnlyOverride>(
				buildOverridesListHolder(),
				ReadOnlyOverride.NAME_PROPERTY);
	}
	
	private Transformer<ReadOnlyOverride, Control> buildPaneTransformer(final PageBook pageBook) {
		return new Transformer<ReadOnlyOverride, Control>() {
			public Control transform(ReadOnlyOverride override) {
				return AbstractOverridesComposite.this.transformSelectedOverride(override, pageBook);
			}
		};
	}
	
	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformSelectedOverride(ReadOnlyOverride selectedOverride, PageBook pageBook) {
		if (selectedOverride instanceof ReadOnlyAttributeOverride) {
			return AbstractOverridesComposite.this.getAttributeOverridePane(pageBook).getControl();
		}
		
		if (selectedOverride instanceof ReadOnlyAssociationOverride) {
			return AbstractOverridesComposite.this.getAssociationOverridePane(pageBook).getControl();
		}
		
		return null;
	}
	
	private ListValueModel<AssociationOverride> buildSpecifiedAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, AssociationOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterable<AssociationOverride> getListIterable() {
				return new SuperListIterableWrapper<AssociationOverride>(this.subject.getSpecifiedOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.getSpecifiedOverridesSize();
			}
		};
	}
	
	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, AttributeOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterable<AttributeOverride> getListIterable() {
				return new SuperListIterableWrapper<AttributeOverride>(this.subject.getSpecifiedOverrides());
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
			ReadOnlyOverride override = this.selectedOverrideModel.getValue();
			
			ReadOnlyOverride newOverride = convertToSpecified ?
					((VirtualOverride) override).convertToSpecified() :
					((Override_) override).convertToVirtual();
			this.selectedOverrideModel.setValue(newOverride);
		}
		finally {
			setPopulating(false);
		}
	}
}
