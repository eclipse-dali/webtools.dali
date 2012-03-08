/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractIdMappingComposite<T extends IdMapping>
	extends Pane<T>
    implements JpaComposite
{
	public AbstractIdMappingComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		initializeIdCollapsibleSection(container);
		initializeTypeCollapsibleSection(container);
		initializeGenerationCollapsibleSection(container);
	}
	
	protected void initializeIdCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.IdSection_title,
				new SimplePropertyValueModel<Boolean>(Boolean.TRUE));

		this.initializeIdSection(container);
	}
	
	protected abstract void initializeIdSection(Composite container);
	
	protected void initializeTypeCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
				container,
				JptUiDetailsMessages.TypeSection_type);
		this.initializeTypeSection(container);
	}
	
	protected void initializeTypeSection(Composite container) {
		((GridLayout) container.getLayout()).numColumns = 2;
		
		// No converter
		Button noConverterButton = addRadioButton(
				container, 
				JptUiDetailsMessages.TypeSection_default, 
				buildConverterBooleanHolder(null), 
				null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
				
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
				container, 
				JptUiDetailsMessages.TypeSection_temporal, 
				buildConverterBooleanHolder(BaseTemporalConverter.class), 
				null);
		registerSubPane(new TemporalTypeComposite(buildTemporalConverterHolder(converterHolder), container, getWidgetFactory()));
	}
	
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGenerationComposite(this, container);
	}
	
	protected PropertyValueModel<? extends Column> buildColumnHolder() {
		return new TransformationPropertyValueModel<T, Column>(getSubjectHolder())  {
			@Override
			protected Column transform_(T value) {
				return value.getColumn();
			}
		};
	}
	
	protected ModifiablePropertyValueModel<Boolean> buildConverterBooleanHolder(final Class<? extends Converter> converterType) {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getType() == converterType);
			}
			
			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(converterType);
				}
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
				return converter.getType() == BaseTemporalConverter.class ? (BaseTemporalConverter) converter : null;
			}
		};
	}
}
