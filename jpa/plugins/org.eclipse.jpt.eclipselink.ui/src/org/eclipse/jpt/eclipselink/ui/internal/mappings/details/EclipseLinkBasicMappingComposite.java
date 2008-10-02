/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.ConvertibleMapping;
import org.eclipse.jpt.core.context.EnumeratedConverter;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.BaseJpaUiFactory;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.mappings.details.EnumTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.FetchTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.OptionalComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TemporalTypeComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
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
 *
 * @see BasicMapping
 * @see BaseJpaUiFactory - The factory creating this pane
 * @see ColumnComposite
 * @see EnumTypeComposite
 * @see FetchTypeComposite
 * @see LobComposite
 * @see OptionalComposite
 * @see TemporalTypeComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkBasicMappingComposite extends FormPane<BasicMapping>
                                   implements JpaComposite
{
	/**
	 * Creates a new <code>BasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IBasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkBasicMappingComposite(PropertyValueModel<BasicMapping> subjectHolder,
	                             Composite parent,
	                             WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeConversionPane(container);
	}
	
	private void initializeGeneralPane(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();

		new ColumnComposite(this, buildColumnHolder(), container);

		// Align the widgets under the ColumnComposite
		container = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);

		new FetchTypeComposite(this, container);
		new OptionalComposite(this, addSubPane(container, 4));

	}
	private void initializeConversionPane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.BasicMappingComposite_conversion
		);
		((GridLayout) container.getLayout()).numColumns = 2;

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptUiMappingsMessages.BasicMappingComposite_noConverter, 
			buildNoConverterHolder(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
		
		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptUiMappingsMessages.BasicMappingComposite_lobConverter, 
			buildConverterBooleanHolder(Converter.LOB_CONVERTER), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;
		
		PropertyValueModel<Converter> specifiedConverterHolder = buildSpecifiedConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptUiMappingsMessages.BasicMappingComposite_temporalConverter, 
			buildConverterBooleanHolder(Converter.TEMPORAL_CONVERTER), 
			null);
		registerSubPane(new TemporalTypeComposite(buildTemporalConverterHolder(specifiedConverterHolder), container, getWidgetFactory()));
		
		
		// Enumerated
		addRadioButton(
			container, 
			JptUiMappingsMessages.BasicMappingComposite_enumeratedConverter, 
			buildConverterBooleanHolder(Converter.ENUMERATED_CONVERTER), 
			null);
		registerSubPane(new EnumTypeComposite(buildEnumeratedConverterHolder(specifiedConverterHolder), container, getWidgetFactory()));

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			EclipseLinkUiMappingsMessages.EclipseLinkBasicMappingComposite_eclipseLinkConverter, 
			buildConverterBooleanHolder(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		ConvertComposite convertComposite = new ConvertComposite(buildEclipseLinkConverterHolder(specifiedConverterHolder), container, getWidgetFactory());
		GridData gridData = (GridData) convertComposite.getControl().getLayoutData();
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 20;
		registerSubPane(convertComposite);
	}

	private PropertyValueModel<Column> buildColumnHolder() {
		return new TransformationPropertyValueModel<BasicMapping, Column>(getSubjectHolder()) {
			@Override
			protected Column transform_(BasicMapping value) {
				return value.getColumn();
			}
		};
	}
	
	private PropertyValueModel<Converter> buildSpecifiedConverterHolder() {
		return new PropertyAspectAdapter<BasicMapping, Converter>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getSpecifiedConverter();
			}
		};
	}
	
	private PropertyValueModel<TemporalConverter> buildTemporalConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, TemporalConverter>(converterHolder) {
			@Override
			protected TemporalConverter transform_(Converter converter) {
				return (converter != null && converter.getType() == Converter.TEMPORAL_CONVERTER) ? (TemporalConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EnumeratedConverter> buildEnumeratedConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, EnumeratedConverter>(converterHolder) {
			@Override
			protected EnumeratedConverter transform_(Converter converter) {
				return (converter != null && converter.getType() == Converter.ENUMERATED_CONVERTER) ? (EnumeratedConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, EclipseLinkConvert>(converterHolder) {
			@Override
			protected EclipseLinkConvert transform_(Converter converter) {
				return (converter != null && converter.getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) ? (EclipseLinkConvert) converter : null;
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildNoConverterHolder() {
		return new PropertyAspectAdapter<BasicMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getSpecifiedConverter() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setSpecifiedConverter(Converter.NO_CONVERTER);
				}
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildConverterBooleanHolder(final String converterType) {
		return new PropertyAspectAdapter<BasicMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getSpecifiedConverter();
				if (converter == null) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(converter.getType() == converterType);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setSpecifiedConverter(converterType);
				}
			}
		};
	}
}