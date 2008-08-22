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
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.CachingTransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Button;
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
 * |                                                                           |
 * |   x Override Default                                                      |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EmbeddedMapping
 * @see EmbeddedMappingComposite - The parent container
 * @see ColumnComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class EmbeddedAttributeOverridesComposite extends AbstractFormPane<BaseEmbeddedMapping>
{
	private WritablePropertyValueModel<AttributeOverride> selectedAttributeOverrideHolder;

	private WritablePropertyValueModel<Boolean> overrideVirtualAttributeOverrideHolder;
	
	/**
	 * Creates a new <code>EmbeddedAttributeOverridesComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EmbeddedAttributeOverridesComposite(AbstractFormPane<? extends BaseEmbeddedMapping> parentPane,
	                                           Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>EmbeddedAttributeOverridesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEmbeddedMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EmbeddedAttributeOverridesComposite(PropertyValueModel<? extends BaseEmbeddedMapping> subjectHolder,
	             	                            Composite parent,
	            	                            WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedAttributeOverrideHolder = buildAttributeOverrideHolder();
	}

	private AddRemoveListPane<BaseEmbeddedMapping> initializeAttributeOverridesList(Composite container) {

		return new AddRemoveListPane<BaseEmbeddedMapping>(
			this,
			buildSubPane(container, 8),
			buildAttributeOverridesAdapter(),
			buildAttributeOverridesListModel(),
			this.selectedAttributeOverrideHolder,
			buildAttributeOverrideLabelProvider(),
			JpaHelpContextIds.MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES
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

	@Override
	protected void initializeLayout(Composite container) {

		// Attribute Overrides group box
		container = buildTitledPane(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		// Attribute Overrides list
		initializeAttributeOverridesList(container);

		// Property pane
		initializePropertyPane(buildSubPane(container, 5, 0));
	}

	private void initializePropertyPane(Composite container) {

		// Override Default check box
		Button overrideDefaultButton = buildCheckBox(
			buildSubPane(container, 0, groupBoxMargin()),
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			getOverrideVirtualAttributeOverrideHolder()
		);

		removeFromEnablementControl(overrideDefaultButton);
		installOverrideDefaultButtonEnabler(overrideDefaultButton);

		// Column widgets
		ColumnComposite columnComposite = new ColumnComposite(
			this,
			buildColumnHolder(this.selectedAttributeOverrideHolder),
			container
		);

		installColumnCompositeEnabler(columnComposite);
		removeFromEnablementControl(columnComposite.getControl());
	}

	private void installColumnCompositeEnabler(ColumnComposite columnComposite) {
		new PaneEnabler(
			getOverrideVirtualAttributeOverrideHolder(),
			columnComposite
		);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {
		new ControlEnabler(
			buildOverrideVirtualAttributeOverrideEnablerHolder(),
			overrideDefaultButton
		);
	}
	
	private WritablePropertyValueModel<AttributeOverride> buildAttributeOverrideHolder() {
		return new SimplePropertyValueModel<AttributeOverride>();
	}

	private ILabelProvider buildAttributeOverrideLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildOverrideDisplayString((AttributeOverride) element);
			}
		};
	}

	private Adapter buildAttributeOverridesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
			}
		};
	}

	private ListValueModel<AttributeOverride> buildAttributeOverridesListHolder() {
		List<ListValueModel<AttributeOverride>> list = new ArrayList<ListValueModel<AttributeOverride>>();
		list.add(buildSpecifiedAttributeOverridesListHolder());
		list.add(buildVirtualAttributeOverridesListHolder());
		return new CompositeListValueModel<ListValueModel<AttributeOverride>, AttributeOverride>(list);
	}

	private ListValueModel<AttributeOverride> buildAttributeOverridesListModel() {
		return new ItemPropertyListValueModelAdapter<AttributeOverride>(
			buildAttributeOverridesListHolder(),
			BaseOverride.NAME_PROPERTY
		);
	}

	private PropertyValueModel<Column> buildColumnHolder(WritablePropertyValueModel<AttributeOverride> attributeOverrideHolder) {
		return new TransformationPropertyValueModel<AttributeOverride, Column>(attributeOverrideHolder) {
			@Override
			protected Column transform_(AttributeOverride value) {
				return value.getColumn();
			}
		};
	}

	private ListValueModel<AttributeOverride> buildVirtualAttributeOverridesListHolder() {
		return new ListAspectAdapter<BaseEmbeddedMapping, AttributeOverride>(
			this.getSubjectHolder(),
			BaseEmbeddedMapping.VIRTUAL_ATTRIBUTE_OVERRIDES_LIST)
		{
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return subject.virtualAttributeOverrides();
			}

			@Override
			protected int size_() {
				return subject.virtualAttributeOverridesSize();
			}
		};
	}

	private PropertyValueModel<Boolean> buildOverrideVirtualAttributeOverrideEnablerHolder() {
		return new TransformationPropertyValueModel<AttributeOverride, Boolean>(this.selectedAttributeOverrideHolder) {
			@Override
			protected Boolean transform(AttributeOverride value) {
				return (value != null);
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> getOverrideVirtualAttributeOverrideHolder() {
		if (this.overrideVirtualAttributeOverrideHolder == null) {
			this.overrideVirtualAttributeOverrideHolder = buildOverrideVirtualAttributeOverrideHolder();
		}
		return this.overrideVirtualAttributeOverrideHolder;
	}
	
	private WritablePropertyValueModel<Boolean> buildOverrideVirtualAttributeOverrideHolder() {
		return new CachingTransformationWritablePropertyValueModel<AttributeOverride, Boolean>(this.selectedAttributeOverrideHolder) {
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

	private String buildOverrideDisplayString(AttributeOverride override) {

		String name = override.getName();

		if (StringTools.stringIsEmpty(name)) {
			name = JptUiMappingsMessages.OverridesComposite_noName;
		}
		else {
			name = name.trim();
		}

		return name;
	}

	private ListValueModel<AttributeOverride> buildSpecifiedAttributeOverridesListHolder() {
		return new ListAspectAdapter<BaseEmbeddedMapping, AttributeOverride>(
			this.getSubjectHolder(),
			BaseEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST)
		{
			@Override
			protected ListIterator<AttributeOverride> listIterator_() {
				return this.subject.specifiedAttributeOverrides();
			}

			@Override
			public int size_() {
				return this.subject.specifiedAttributeOverridesSize();
			}
		};
	}


	private void updateAttributeOverride(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			AttributeOverride selectedOverride = this.selectedAttributeOverrideHolder.getValue();
			AttributeOverride newOverride = selectedOverride.setVirtual(!selected);
			
			//select the new override so the UI remains consistent
			this.selectedAttributeOverrideHolder.setValue(newOverride);
		}
		finally {
			setPopulating(false);
		}
	}
}