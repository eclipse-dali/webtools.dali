/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.ConvertibleMapping;
import org.eclipse.jpt.core.context.TemporalConverter;
import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkVersionMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.TemporalTypeComposite;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
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
 * | | MutableComposite                                                      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TemporalTypeComposite                                                 | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see VersionMapping
 * @see ColumnComposite
 * @see TemporalTypeComposite
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkVersionMappingComposite extends FormPane<VersionMapping>
                                     implements JpaComposite
{
	/**
	 * Creates a new <code>VersionMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IVersionMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkVersionMappingComposite(PropertyValueModel<? extends VersionMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected PropertyValueModel<Column> buildColumnHolder() {
		return new TransformationPropertyValueModel<VersionMapping, Column>(getSubjectHolder()) {
			@Override
			protected Column transform_(VersionMapping value) {
				return value.getColumn();
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeGeneralPane(container);
		initializeTypePane(container);
	}
	
	protected void initializeGeneralPane(Composite container) {
		int groupBoxMargin = getGroupBoxMargin();

		// Column widgets
		new ColumnComposite(this, buildColumnHolder(), container);
		
		// Align the widgets under the ColumnComposite
		container = addSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
		
		// Mutable widgets
		new EclipseLinkMutableComposite(this, buildMutableHolder(), container);
	}
	
	protected void initializeTypePane(Composite container) {

		container = addCollapsibleSection(
			container,
			JptUiDetailsMessages.TypeSection_type
		);
		((GridLayout) container.getLayout()).numColumns = 2;

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptUiDetailsMessages.TypeSection_default, 
			buildNoConverterHolder(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
				
		PropertyValueModel<Converter> specifiedConverterHolder = buildSpecifiedConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptUiDetailsMessages.TypeSection_temporal, 
			buildTemporalBooleanHolder(), 
			null);
		registerSubPane(new TemporalTypeComposite(buildTemporalConverterHolder(specifiedConverterHolder), container, getWidgetFactory()));

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			EclipseLinkUiDetailsMessages.TypeSection_converted, 
			buildEclipseLinkConverterBooleanHolder(), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;

		Pane<EclipseLinkConvert> convertComposite = buildConvertComposite(buildEclipseLinkConverterHolder(specifiedConverterHolder), container);
		GridData gridData = (GridData) convertComposite.getControl().getLayoutData();
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 20;
		registerSubPane(convertComposite);
	}
	
	protected Pane<EclipseLinkConvert> buildConvertComposite(PropertyValueModel<EclipseLinkConvert> convertHolder, Composite container) {
		return new EclipseLinkConvertComposite(convertHolder, container, getWidgetFactory());
	}

	protected WritablePropertyValueModel<Boolean> buildNoConverterHolder() {
		return new PropertyAspectAdapter<VersionMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
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

	protected PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, EclipseLinkConvert>(converterHolder) {
			@Override
			protected EclipseLinkConvert transform_(Converter converter) {
				return (converter != null && converter.getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER) ? (EclipseLinkConvert) converter : null;
			}
		};
	}

	protected WritablePropertyValueModel<Boolean> buildTemporalBooleanHolder() {
		return new PropertyAspectAdapter<VersionMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getSpecifiedConverter();
				if (converter == null) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(converter.getType() == Converter.TEMPORAL_CONVERTER);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setSpecifiedConverter(Converter.TEMPORAL_CONVERTER);
				}
			}
		};
	}
	
	protected WritablePropertyValueModel<Boolean> buildEclipseLinkConverterBooleanHolder() {
		return new PropertyAspectAdapter<VersionMapping, Boolean>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getSpecifiedConverter();
				if (converter == null) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(converter.getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setSpecifiedConverter(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER);
				}
			}
		};
	}

	protected PropertyValueModel<Converter> buildSpecifiedConverterHolder() {
		return new PropertyAspectAdapter<VersionMapping, Converter>(getSubjectHolder(), ConvertibleMapping.SPECIFIED_CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getSpecifiedConverter();
			}
		};
	}
	
	protected PropertyValueModel<TemporalConverter> buildTemporalConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, TemporalConverter>(converterHolder) {
			@Override
			protected TemporalConverter transform_(Converter converter) {
				return (converter != null && converter.getType() == Converter.TEMPORAL_CONVERTER) ? (TemporalConverter) converter : null;
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkMutable> buildMutableHolder() {
		return new PropertyAspectAdapter<VersionMapping, EclipseLinkMutable>(getSubjectHolder()) {
			@Override
			protected EclipseLinkMutable buildValue_() {
				return ((EclipseLinkVersionMapping) this.subject).getMutable();
			}
		};
	}
}