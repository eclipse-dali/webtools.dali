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
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.BaseEmbeddedMapping;
import org.eclipse.jpt.core.context.BaseOverride;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.StringTools;
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
	private WritablePropertyValueModel<AttributeOverride> attributeOverrideHolder;

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
		list.add(buildDefaultAttributeOverridesListHolder());
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

	private ListValueModel<AttributeOverride> buildDefaultAttributeOverridesListHolder() {
		return new ListAspectAdapter<BaseEmbeddedMapping, AttributeOverride>(
			this.getSubjectHolder(),
			BaseEmbeddedMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST)
		{
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

	private PropertyValueModel<Boolean> buildOverrideDefaultAttributeOverrideEnablerHolder() {
		return new TransformationPropertyValueModel<AttributeOverride, Boolean>(attributeOverrideHolder) {
			@Override
			protected Boolean transform(AttributeOverride value) {
				return (value != null);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultAttributeOverrideHolder() {
		return new TransformationWritablePropertyValueModel<AttributeOverride, Boolean>(attributeOverrideHolder) {
			@Override
			public void setValue(Boolean value) {
				updateAssociationOverride(value);
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
				return subject.specifiedAttributeOverrides();
			}

			@Override
			public int size_() {
				return subject.specifiedAttributeOverridesSize();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.attributeOverrideHolder = buildAttributeOverrideHolder();
	}

	private AddRemoveListPane<BaseEmbeddedMapping> initializeAttributeOverridesList(Composite container) {

		return new AddRemoveListPane<BaseEmbeddedMapping>(
			this,
			buildSubPane(container, 8),
			buildAttributeOverridesAdapter(),
			buildAttributeOverridesListModel(),
			attributeOverrideHolder,
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

	/*
	 * (non-Javadoc)
	 */
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
			buildOverrideDefaultAttributeOverrideHolder()
		);

		installOverrideDefaultButtonEnabler(overrideDefaultButton);

		// Column widgets
		ColumnComposite columnComposite = new ColumnComposite(
			this,
			buildColumnHolder(attributeOverrideHolder),
			container
		);

		installColumnCompositeEnabler(columnComposite);
	}

	private void installColumnCompositeEnabler(ColumnComposite columnComposite) {
		new PaneEnabler(
			buildOverrideDefaultAttributeOverrideHolder(),
			columnComposite
		);
	}

	private void installOverrideDefaultButtonEnabler(Button overrideDefaultButton) {
		new ControlEnabler(
			buildOverrideDefaultAttributeOverrideEnablerHolder(),
			overrideDefaultButton
		);
	}

	private void updateAssociationOverride(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			BaseEmbeddedMapping subject = subject();
			AttributeOverride override = attributeOverrideHolder.value();

			// Add a new association override
			if (selected) {
				int index = this.subject().specifiedAttributeOverridesSize();

				AttributeOverride attributeOverride = subject.addSpecifiedAttributeOverride(index);
				attributeOverride.setName(override.getName());
				attributeOverride.getColumn().setSpecifiedName(override.getColumn().getName());

				attributeOverrideHolder.setValue(attributeOverride);
			}
			// Remove the specified association override
			else {
				String name = override.getName();
				subject.removeSpecifiedAttributeOverride(override);

				// Select the default attribute override
				for (Iterator<AttributeOverride> iter = subject.defaultAttributeOverrides(); iter.hasNext(); ) {
					AttributeOverride attributeOverride = iter.next();

					if (attributeOverride.getName().equals(name)) {
						attributeOverrideHolder.setValue(attributeOverride);
						break;
					}
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}
}