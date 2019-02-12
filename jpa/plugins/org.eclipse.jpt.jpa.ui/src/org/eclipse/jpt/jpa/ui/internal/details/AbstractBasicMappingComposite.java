/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.BasicMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnComposite                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | FetchTypeComposite                                                    | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EnumTypeComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | OptionalComposite                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | LobComposite                                                          | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 */
public abstract class AbstractBasicMappingComposite<T extends BasicMapping> 
	extends Pane<T>
	implements JpaComposite
{
	protected AbstractBasicMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeBasicCollapsibleSection(container);
		initializeTypeCollapsibleSection(container);
	}
	
	protected void initializeBasicCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.BASIC_SECTION_TITLE);
		section.setClient(this.initializeBasicSection(section));
	}
	
	protected Control initializeBasicSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		ColumnComposite columnComposite = new ColumnComposite(this, buildColumnModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		columnComposite.getControl().setLayoutData(gridData);

		this.addLabel(container, JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_FETCH_LABEL);
		new FetchTypeComboViewer(this, container);

		OptionalTriStateCheckBox optionalCheckBox = new OptionalTriStateCheckBox(this, container);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		optionalCheckBox.getControl().setLayoutData(gridData);

		return container;
	}
	
	protected void initializeTypeCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.TYPE_SECTION_TYPE);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractBasicMappingComposite.this.initializeTypeSection(section));
				}
			}
		});
	}

	protected Control initializeTypeSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_DEFAULT, 
			buildConverterBooleanHolder(null), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
		
		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_LOB, 
			buildConverterBooleanHolder(LobConverter.class), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;
		
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_TEMPORAL, 
			buildConverterBooleanHolder(BaseTemporalConverter.class), 
			null);
		new TemporalTypeCombo(this, this.buildTemporalConverterHolder(converterHolder), container);
		
		
		// Enumerated
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_ENUMERATED, 
			buildConverterBooleanHolder(BaseEnumeratedConverter.class), 
			null);
		new EnumTypeComboViewer(this, this.buildEnumeratedConverterHolder(converterHolder), container);
		return container;
	}

	protected PropertyValueModel<SpecifiedColumn> buildColumnModel() {
		return new TransformationPropertyValueModel<T, SpecifiedColumn>(getSubjectHolder()) {
			@Override
			protected SpecifiedColumn transform_(T mapping) {
				return mapping.getColumn();
			}
		};
	}
	
	protected PropertyValueModel<Converter> buildConverterHolder() {
		return new PropertyAspectAdapter<T, Converter>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	protected PropertyValueModel<BaseTemporalConverter> buildTemporalConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, BaseTemporalConverter>(converterHolder) {
			@Override
			protected BaseTemporalConverter transform_(Converter converter) {
				return converter.getConverterType() == BaseTemporalConverter.class ? (BaseTemporalConverter) converter : null;
			}
		};
	}
	
	protected PropertyValueModel<BaseEnumeratedConverter> buildEnumeratedConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, BaseEnumeratedConverter>(converterHolder) {
			@Override
			protected BaseEnumeratedConverter transform_(Converter converter) {
				return converter.getConverterType() == BaseEnumeratedConverter.class ? (BaseEnumeratedConverter) converter : null;
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildConverterBooleanHolder(final Class<? extends Converter> converterType) {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getConverterType() == converterType);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(converterType);
				}
			}
		};
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, SpecifiedAccessReference>(getSubjectHolder()) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}