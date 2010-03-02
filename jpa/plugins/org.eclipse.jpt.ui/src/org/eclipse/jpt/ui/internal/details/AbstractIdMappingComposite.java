/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.ConvertibleMapping;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

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
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | GenerationComposite                                                   | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IdMapping
 * @see ColumnComposite
 * @see TemporalTypeComposite
 * @see IdMappingGenerationComposite
 *
 * @version 2.3
 * @since 1.0
 */
public abstract class AbstractIdMappingComposite<T extends IdMapping>
	extends Pane<T>
    implements JpaComposite
{
	/**
	 * Creates a new <code>IdMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IIdMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public AbstractIdMappingComposite(PropertyValueModel<? extends T> subjectHolder,
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
			new SimplePropertyValueModel<Boolean>(Boolean.TRUE)
		);

		this.initializeIdSection(container);
	}

	protected abstract void initializeIdSection(Composite container);

	protected void initializeTypeCollapsibleSection(Composite container) {
		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.TypeSection_type
		);
		this.initializeTypeSection(container);
	}
	
	protected void initializeTypeSection(Composite container) {
		((GridLayout) container.getLayout()).numColumns = 2;

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptUiDetailsMessages.TypeSection_default, 
			buildConverterBooleanHolder(Converter.NO_CONVERTER), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
				
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptUiDetailsMessages.TypeSection_temporal, 
			buildConverterBooleanHolder(Converter.TEMPORAL_CONVERTER), 
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

	protected WritablePropertyValueModel<Boolean> buildConverterBooleanHolder(final String converterType) {
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
	
	protected PropertyValueModel<TemporalConverter> buildTemporalConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, TemporalConverter>(converterHolder) {
			@Override
			protected TemporalConverter transform_(Converter converter) {
				return converter.getType() == Converter.TEMPORAL_CONVERTER ? (TemporalConverter) converter : null;
			}
		};
	}
}