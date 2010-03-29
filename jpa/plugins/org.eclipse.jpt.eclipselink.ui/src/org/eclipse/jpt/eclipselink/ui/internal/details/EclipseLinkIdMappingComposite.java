/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.core.context.IdMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkIdMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkMutable;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.details.AbstractIdMappingComposite;
import org.eclipse.jpt.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.details.TemporalTypeComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkIdMappingComposite<T extends IdMapping>
	extends AbstractIdMappingComposite<T>
	implements JpaComposite
{
	public EclipseLinkIdMappingComposite(
			PropertyValueModel<? extends T> subjectHolder,
	        Composite parent,
	        WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeIdSection(Composite container) {
		new ColumnComposite(this, buildColumnHolder(), container);
		new EclipseLinkMutableComposite(this, buildMutableHolder(), container);
	}	
	
	@Override
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

		// EclipseLink Converter
		Button elConverterButton = addRadioButton(
			container, 
			EclipseLinkUiDetailsMessages.TypeSection_converted, 
			buildConverterBooleanHolder(EclipseLinkConvert.ECLIPSE_LINK_CONVERTER), 
			null);
		((GridData) elConverterButton.getLayoutData()).horizontalSpan = 2;
		
		Pane<EclipseLinkConvert> convertComposite = buildConvertComposite(buildEclipseLinkConverterHolder(converterHolder), container);
		GridData gridData = (GridData) convertComposite.getControl().getLayoutData();
		gridData.horizontalSpan = 2;
		gridData.horizontalIndent = 20;
		registerSubPane(convertComposite);
	}
	
	protected Pane<EclipseLinkConvert> buildConvertComposite(PropertyValueModel<EclipseLinkConvert> convertHolder, Composite container) {
		return new EclipseLinkConvertComposite(convertHolder, container, getWidgetFactory());
	}
	
	protected PropertyValueModel<EclipseLinkConvert> buildEclipseLinkConverterHolder(PropertyValueModel<Converter> converterHolder) {
		return new TransformationPropertyValueModel<Converter, EclipseLinkConvert>(converterHolder) {
			@Override
			protected EclipseLinkConvert transform_(Converter converter) {
				return converter.getType() == EclipseLinkConvert.ECLIPSE_LINK_CONVERTER ? (EclipseLinkConvert) converter : null;
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkMutable> buildMutableHolder() {
		return new PropertyAspectAdapter<T, EclipseLinkMutable>(getSubjectHolder()) {
			@Override
			protected EclipseLinkMutable buildValue_() {
				return ((EclipseLinkIdMapping) this.subject).getMutable();
			}
		};
	}
}
