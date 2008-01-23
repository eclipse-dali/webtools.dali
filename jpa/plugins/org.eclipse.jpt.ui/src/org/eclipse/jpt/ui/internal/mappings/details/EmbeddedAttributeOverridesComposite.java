/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.IEmbeddedMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.swt.ListBoxModelAdapter;
import org.eclipse.jpt.ui.internal.util.BaseJpaControllerEnabler;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | --------------------------                                                |
 * | | OverrideAttribute 1    |  x Override Default                            |
 * | | ...                    |                                                |
 * | | OverrideAttribute n    | ---------------------------------------------- |
 * | |                        | |                                            | |
 * | |                        | | ColumnComposite                            | |
 * | |                        | |                                            | |
 * | -------------------------- ---------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IEmbeddedMapping
 * @see BaseJpaUiFactory
 * @see ColumnComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class EmbeddedAttributeOverridesComposite extends BaseJpaController<IEmbeddedMapping>
{
	private WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder;
	private List list;

	/**
	 * Creates a new <code>EmbeddedAttributeOverridesComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public EmbeddedAttributeOverridesComposite(BaseJpaController<? extends IEmbeddedMapping> parentController,
	                                           Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>EmbeddedAttributeOverridesComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IEmbeddedMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EmbeddedAttributeOverridesComposite(PropertyValueModel<? extends IEmbeddedMapping> subjectHolder,
	             	                            Composite parent,
	            	                            TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private WritablePropertyValueModel<IAttributeOverride> buildAttributeOverrideHolder() {
		return new SimplePropertyValueModel<IAttributeOverride>();
	}

	private CollectionValueModel/*<IAttributeOverride>*/ buildAttributeOverridesCollectionHolder() {
		return new CollectionAspectAdapter<IEmbeddedMapping>/*<IAttributeOverride, IEmbeddedMapping>*/(
			this.getSubjectHolder(),
			IEmbeddedMapping.DEFAULT_ATTRIBUTE_OVERRIDES_LIST,
			IEmbeddedMapping.SPECIFIED_ATTRIBUTE_OVERRIDES_LIST)
		{
			@Override
			protected Iterator<IAttributeOverride> iterator_() {
				return subject.attributeOverrides();
			}
		};
	}

	private List buildAttributeOverridesList(Composite parent,
	                                         WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder) {

		List list = this.buildList(
			parent,
			buildSelectedAttributeOverrideHolder(attributeOverrideHolder),
			IJpaHelpContextIds.MAPPING_EMBEDDED_ATTRIBUTE_OVERRIDES
		);

		ListBoxModelAdapter.adapt(
			buildAttributeOverridesStringListHolder(),
			attributeOverrideHolder,
			list
		);

		return list;
	}

	private ListValueModel/*<IAttributeOverride>*/ buildAttributeOverridesListHolder() {
		return new SortedListValueModelAdapter/*<IAttributeOverride>*/(
			this.buildAttributeOverridesCollectionHolder()
		);
	}

	private ListValueModel/*<IAttributeOverride>*/ buildAttributeOverridesStringListHolder() {
		return new TransformationListValueModelAdapter/*<String, IAttributeOverride>*/(this.buildAttributeOverridesListHolder()) {
			@Override
			protected String transformItem(Object/*IAttributeOverride*/ item) {
				IAttributeOverride attributeOverride = (IAttributeOverride) item;
				return attributeOverride.getName();
			}
		};
	}

	private PropertyValueModel<Boolean> buildColumnEnablementHolder(PropertyValueModel<IColumn> columnHolder) {
		return new TransformationPropertyValueModel<IColumn, Boolean>(columnHolder) {
			@Override
			protected Boolean transform(IColumn value) {
				return (value != null);
			}
		};
	}

	private PropertyValueModel<IColumn> buildColumnHolder(WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder) {
		return new TransformationPropertyValueModel<IAttributeOverride, IColumn>(attributeOverrideHolder) {
			@Override
			protected IColumn transform_(IAttributeOverride value) {
				return value.getColumn();
			}
		};
	}

	private PropertyValueModel<Boolean> buildOverrideDefaultButtonBooleanHolder(PropertyValueModel<IAttributeOverride> attributeOverrideHolder) {
		return new TransformationPropertyValueModel<IAttributeOverride, Boolean>(attributeOverrideHolder) {
			@Override
			protected Boolean transform(IAttributeOverride value) {
				return (value != null);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder(WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder) {
		return new TransformationWritablePropertyValueModel<IAttributeOverride, Boolean>(attributeOverrideHolder) {
			@Override
			public void setValue(Boolean value) {
				// Not done here
			}

			@Override
			protected Boolean transform_(IAttributeOverride value) {
				return value.isVirtual();
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				overrideDefaultButtonSelected(button.getSelection());
			}
		};
	}

	private void buildPropertiesPane(Composite container,
	                                 WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder) {

		// Override Default check box
		Button overrideDefaultButton = buildCheckBox(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_overrideDefault,
			this.buildOverrideDefaultHolder(attributeOverrideHolder)
		);

		GridData data = new GridData();
		data.horizontalIndent = groupBoxMargin();
		data.verticalIndent   = 0;
		overrideDefaultButton.setLayoutData(data);

		overrideDefaultButton.addSelectionListener(
			this.buildOverrideDefaultSelectionListener()
		);

		this.installOverrideDefaultButtonEnabler(
			attributeOverrideHolder,
			overrideDefaultButton
		);

		// Column widgets
		PropertyValueModel<IColumn> columnHolder = this.buildColumnHolder(attributeOverrideHolder);

		ColumnComposite columnComposite = new ColumnComposite(
			columnHolder,
			container,
			this.getWidgetFactory()
		);

		data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment       = GridData.FILL;
		columnComposite.getControl().setLayoutData(data);

		this.registerSubPane(columnComposite);
		this.installColumnCompositeEnabler(columnHolder, columnComposite);
	}

	private WritablePropertyValueModel<String> buildSelectedAttributeOverrideHolder(WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder) {
		return new TransformationWritablePropertyValueModel<IAttributeOverride, String>(attributeOverrideHolder) {
			@Override
			protected IAttributeOverride reverseTransform_(String value) {
				for (Iterator<IAttributeOverride> iter = subject().attributeOverrides(); iter.hasNext(); ) {
					IAttributeOverride attributeOverride = iter.next();
					if (attributeOverride.getName().equals(value)) {
						return attributeOverride;
					}
				}
				return null;
			}

			@Override
			protected String transform_(IAttributeOverride value) {
				return value.getName();
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		WritablePropertyValueModel<IAttributeOverride> attributeOverrideHolder =
			buildAttributeOverrideHolder();

		// Attribute Overrides group box
		Composite groupBox = buildTitledPane(
			container,
			JptUiMappingsMessages.AttributeOverridesComposite_attributeOverrides
		);

		// Sub-pane to align the check box and ColumnComposite
		// to the right of the list
		container = buildSubPane(groupBox, 1, 5, 0, 0, 0);

		// Attribute Overrides list
		this.list = buildAttributeOverridesList(
			container,
			attributeOverrideHolder
		);

		GridData data = new GridData();
		data.verticalSpan            = 2;
		data.grabExcessVerticalSpace = true;
		data.horizontalAlignment     = GridData.FILL;
		data.verticalAlignment       = GridData.FILL;
		this.list.setLayoutData(data);

		// Properties for the selected attribute overrides
		this.buildPropertiesPane(
			this.buildSubPane(container, 5, 0),
			attributeOverrideHolder
		);
	}

	private void installColumnCompositeEnabler(PropertyValueModel<IColumn> columnHolder,
	                                           ColumnComposite columnComposite) {

		new BaseJpaControllerEnabler(
			this.buildColumnEnablementHolder(columnHolder),
			columnComposite
		);
	}

	private void installOverrideDefaultButtonEnabler(PropertyValueModel<IAttributeOverride> attributeOverrideHolder,
	                                                 Button overrideDefaultButton) {

		new ControlEnabler(
			this.buildOverrideDefaultButtonBooleanHolder(attributeOverrideHolder),
			overrideDefaultButton
		);
	}

	private void overrideDefaultButtonSelected(boolean overrideDefault) {

		if (overrideDefault) {
			int index = this.subject().specifiedAttributeOverridesSize();

			IAttributeOverride defaultAttributeOverride = this.attributeOverrideHolder.value();
			IAttributeOverride attributeOverride = this.subject().addSpecifiedAttributeOverride(index);

			attributeOverride.setName(defaultAttributeOverride.getName());
			attributeOverride.getColumn().setSpecifiedName(defaultAttributeOverride.getColumn().getName());
		}
		else {
			this.subject().removeSpecifiedAttributeOverride(this.list.getSelectionIndex());
		}
	}
}