/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.ColumnMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
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

public abstract class AbstractIdMappingComposite<M extends IdMapping>
	extends Pane<M>
    implements JpaComposite
{
	public AbstractIdMappingComposite(
			PropertyValueModel<? extends M> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		initializeIdCollapsibleSection(container);
		initializeTypeCollapsibleSection(container);
		initializeGenerationCollapsibleSection(container);
	}
	
	protected void initializeIdCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ID_SECTION_TITLE);
		section.setClient(this.initializeIdSection(section));
	}
	
	protected abstract Control initializeIdSection(Composite container);
	
	protected void initializeTypeCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.TYPE_SECTION_TYPE);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractIdMappingComposite.this.initializeTypeSection(section));
				}
			}
		});
	}
	
	@SuppressWarnings("unused")
	protected Control initializeTypeSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);
		
		// No converter
		Button noConverterButton = addRadioButton(
				container, 
				JptJpaUiDetailsMessages.TYPE_SECTION_DEFAULT, 
				buildConverterBooleanModel(null), 
				null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
				
		PropertyValueModel<Converter> converterModel = buildConverterModel();
		// Temporal
		addRadioButton(
				container, 
				JptJpaUiDetailsMessages.TYPE_SECTION_TEMPORAL, 
				buildConverterBooleanModel(BaseTemporalConverter.class), 
				null);
		new TemporalTypeCombo(this, this.buildTemporalConverterModel(converterModel), container);

		return container;
	}
	
	@SuppressWarnings("unused")
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGenerationComposite(this, container);
	}
	
	protected PropertyValueModel<? extends SpecifiedColumn> buildColumnModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), TransformerTools.downcast(ColumnMapping.COLUMN_TRANSFORMER));
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildConverterBooleanModel(final Class<? extends Converter> converterType) {
		return new PropertyAspectAdapterXXXX<M, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
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
	
	protected PropertyValueModel<Converter> buildConverterModel() {
		return new PropertyAspectAdapterXXXX<M, Converter>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	protected PropertyValueModel<BaseTemporalConverter> buildTemporalConverterModel(PropertyValueModel<Converter> converterModel) {
		return PropertyValueModelTools.transform(converterModel, BaseTemporalConverter.CONVERTER_TRANSFORMER); 
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), AttributeMapping.PERSISTENT_ATTRIBUTE_TRANSFORMER);
	}
}
