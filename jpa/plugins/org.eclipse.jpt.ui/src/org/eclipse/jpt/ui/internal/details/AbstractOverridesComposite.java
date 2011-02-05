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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.utility.swt.SWTTools;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.iterators.SuperListIteratorWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CachingTransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CachingTransformationWritablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.OverrideContainer;
import org.eclipse.jpt.core.context.Override_;
import org.eclipse.jpt.core.context.ReadOnlyAssociationOverride;
import org.eclipse.jpt.core.context.ReadOnlyAttributeOverride;
import org.eclipse.jpt.core.context.ReadOnlyOverride;
import org.eclipse.jpt.core.context.VirtualAssociationOverride;
import org.eclipse.jpt.core.context.VirtualAttributeOverride;
import org.eclipse.jpt.core.context.VirtualOverride;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
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
	
	private WritablePropertyValueModel<ReadOnlyOverride> selectedOverrideHolder;
	private WritablePropertyValueModel<Boolean> overrideVirtualOverrideHolder;
	
	
	protected AbstractOverridesComposite(
			Pane<? extends T> parentPane,
			Composite parent) {
		
		super(parentPane, parent, false);
	}
	
	
	@Override
	protected void initialize() {
		super.initialize();
		this.selectedOverrideHolder = buildSelectedOverrideHolder();
	}
	
	private WritablePropertyValueModel<ReadOnlyOverride> buildSelectedOverrideHolder() {
		return new SimplePropertyValueModel<ReadOnlyOverride>();
	}
	
	protected abstract boolean supportsAssociationOverrides();
	
	@Override
	protected void initializeLayout(Composite container) {
		// Overrides group pane
		container = addTitledGroup(
				container,
				JptUiDetailsMessages.OverridesComposite_attributeOverridesGroup);
		
		// Overrides list pane
		initializeOverridesList(container);
		
		int groupBoxMargin = getGroupBoxMargin();
		
		// Override Default check box
		Button overrideCheckBox = addCheckBox(
				addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
				JptUiDetailsMessages.OverridesComposite_overrideDefault,
				getOverrideVirtualOverrideHolder(),
				null);
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
		return new TransformationPropertyValueModel<ReadOnlyOverride, Boolean>(this.selectedOverrideHolder) {
			@Override
			protected Boolean transform(ReadOnlyOverride value) {
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
				JpaHelpContextIds.ENTITY_ATTRIBUTE_OVERRIDES) {
			
			@Override
			protected void initializeButtonPane(Composite c, String helpId) {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}
			
			@Override
			protected void updateButtons() {
				//no buttons: no way to add/remove/edit overrides, they are all defaulted in
			}
		};
	}
	
	protected void initializeAttributeOverridePane(PageBook pageBook) {
		PropertyValueModel<ReadOnlyAttributeOverride>  attributeOverrideHolder = buildAttributeOverrideHolder();
		this.attributeOverridePane = buildAttributeOverridePane(pageBook, attributeOverrideHolder);
		installAttributeOverridePaneEnabler(this.attributeOverridePane, attributeOverrideHolder);
	}
	
	protected Pane<ReadOnlyAttributeOverride> buildAttributeOverridePane(PageBook pageBook, PropertyValueModel<ReadOnlyAttributeOverride> attributeOverrideHolder) {
		return new AttributeOverrideComposite(this, attributeOverrideHolder, pageBook);
	}
	
	private void installAttributeOverridePaneEnabler(Pane<ReadOnlyAttributeOverride> pane, PropertyValueModel<ReadOnlyAttributeOverride> overrideHolder) {
		new PaneEnabler(
				buildOverrideBooleanHolder(overrideHolder),
				pane);
	}
	
	private PropertyValueModel<Boolean> buildOverrideBooleanHolder(PropertyValueModel<? extends ReadOnlyOverride> overrideHolder) {
		return new CachingTransformationPropertyValueModel<ReadOnlyOverride, Boolean>(overrideHolder) {
			@Override
			protected Boolean transform_(ReadOnlyOverride value) {
				return Boolean.valueOf(!value.isVirtual());
			}
		};
	}
	
	protected void initializeAssociationOverridePane(PageBook pageBook) {
		PropertyValueModel<ReadOnlyAssociationOverride>  associationOverrideHolder = buildAssociationOverrideHolder();
		this.associationOverridePane = buildAssociationOverridePane(pageBook, associationOverrideHolder);
		installAssociationOverridePaneEnabler(this.associationOverridePane, associationOverrideHolder);
	}
	
	protected Pane<ReadOnlyAssociationOverride> buildAssociationOverridePane(PageBook pageBook, PropertyValueModel<ReadOnlyAssociationOverride> associationOverrideHolder) {
		return new AssociationOverrideComposite(this, associationOverrideHolder, pageBook);		
	}
	
	private void installAssociationOverridePaneEnabler(Pane<ReadOnlyAssociationOverride> pane, PropertyValueModel<ReadOnlyAssociationOverride> overrideHolder) {
		new PaneEnabler(
				buildOverrideBooleanHolder(overrideHolder),
				pane);
	}
	
	private void installOverrideControlSwitcher(
			PropertyValueModel<ReadOnlyOverride> overrideHolder,
			PageBook pageBook) {
		
		new ControlSwitcher(
				overrideHolder,
				buildPaneTransformer(),
				pageBook);
	}
	
	private WritablePropertyValueModel<ReadOnlyAssociationOverride> buildAssociationOverrideHolder() {
		return new TransformationWritablePropertyValueModel<ReadOnlyOverride, ReadOnlyAssociationOverride>(this.selectedOverrideHolder) {
			@Override
			protected ReadOnlyAssociationOverride transform_(ReadOnlyOverride value) {
				return (value instanceof ReadOnlyAssociationOverride) ? (ReadOnlyAssociationOverride) value : null;
			}
		};
	}
	
	private WritablePropertyValueModel<ReadOnlyAttributeOverride> buildAttributeOverrideHolder() {
		return new TransformationWritablePropertyValueModel<ReadOnlyOverride, ReadOnlyAttributeOverride>(this.selectedOverrideHolder) {
			@Override
			protected ReadOnlyAttributeOverride transform_(ReadOnlyOverride value) {
				return (value instanceof ReadOnlyAttributeOverride) ? (ReadOnlyAttributeOverride) value : null;
			}
		};
	}
	
	private ListValueModel<VirtualAssociationOverride> buildDefaultAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, VirtualAssociationOverride>(containerHolder, OverrideContainer.VIRTUAL_OVERRIDES_LIST) {
			@Override
			protected ListIterator<VirtualAssociationOverride> listIterator_() {
				return new SuperListIteratorWrapper<VirtualAssociationOverride>(this.subject.virtualOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.virtualOverridesSize();
			}
		};
	}
	
	private ListValueModel<VirtualAttributeOverride> buildDefaultAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, VirtualAttributeOverride>(containerHolder, OverrideContainer.VIRTUAL_OVERRIDES_LIST) {
			@Override
			protected ListIterator<VirtualAttributeOverride> listIterator_() {
				return new SuperListIteratorWrapper<VirtualAttributeOverride>(this.subject.virtualOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.virtualOverridesSize();
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
		return new CachingTransformationWritablePropertyValueModel<ReadOnlyOverride, Boolean>(this.selectedOverrideHolder) {
			@Override
			public void setValue(Boolean value) {
				updateOverride(value.booleanValue());
			}
			
			@Override
			protected Boolean transform_(ReadOnlyOverride value) {
				return Boolean.valueOf(!value.isVirtual());
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
	
	private Transformer<ReadOnlyOverride, Control> buildPaneTransformer() {
		return new Transformer<ReadOnlyOverride, Control>() {
			public Control transform(ReadOnlyOverride override) {
				return AbstractOverridesComposite.this.transformSelectedOverride(override);
			}
		};
	}
	
	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformSelectedOverride(ReadOnlyOverride selectedOverride) {
		if (selectedOverride instanceof ReadOnlyAttributeOverride) {
			return AbstractOverridesComposite.this.attributeOverridePane.getControl();
		}
		
		if (selectedOverride instanceof ReadOnlyAssociationOverride) {
			return AbstractOverridesComposite.this.associationOverridePane.getControl();
		}
		
		return null;
	}
	
	private ListValueModel<AssociationOverride> buildSpecifiedAssociationOverridesListHolder(PropertyValueModel<AssociationOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AssociationOverrideContainer, AssociationOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AssociationOverride> listIterator_() {
				return new SuperListIteratorWrapper<AssociationOverride>(this.subject.specifiedOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.specifiedOverridesSize();
			}
		};
	}
	
	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder(PropertyValueModel<AttributeOverrideContainer> containerHolder) {
		return new ListAspectAdapter<AttributeOverrideContainer, AttributeOverride>(containerHolder, OverrideContainer.SPECIFIED_OVERRIDES_LIST) {
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return new SuperListIteratorWrapper<AttributeOverride>(this.subject.specifiedOverrides());
			}
			
			@Override
			protected int size_() {
				return this.subject.specifiedOverridesSize();
			}
		};
	}
	
	void updateOverride(boolean convertToSpecified) {
		if (isPopulating()) {
			return;
		}
		
		setPopulating(true);
		
		try {
			ReadOnlyOverride override = this.selectedOverrideHolder.getValue();
			
			ReadOnlyOverride newOverride = convertToSpecified ?
					((VirtualOverride) override).convertToSpecified() :
					((Override_) override).convertToVirtual();
			this.selectedOverrideHolder.setValue(newOverride);
		}
		finally {
			setPopulating(false);
		}
	}
}
