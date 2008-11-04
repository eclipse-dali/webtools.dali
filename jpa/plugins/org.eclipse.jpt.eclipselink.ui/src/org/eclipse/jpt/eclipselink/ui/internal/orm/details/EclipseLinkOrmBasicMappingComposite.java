/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.BasicMapping;
import org.eclipse.jpt.core.context.Converter;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkBasicMappingComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.EnumTypeComposite;
import org.eclipse.jpt.ui.internal.mappings.details.TemporalTypeComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

//Temporary to remove the Converters section from orm basic mapping.
//This is supported in EclipseLink in version 1.1, but not 1.0
public class EclipseLinkOrmBasicMappingComposite extends EclipseLinkBasicMappingComposite
{
	/**
	 * Creates a new <code>EclipseLinkOrmBasicMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>BasicMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOrmBasicMappingComposite(PropertyValueModel<? extends BasicMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	//everything but the eclipseLink Converter option (@Convert).  This is not supported in eclipselink 1.0, but is in 1.1
	protected void initializeTypePane(Composite container) {

		container = addCollapsableSection(
			container,
			JptUiMappingsMessages.TypeSection_type
		);
		((GridLayout) container.getLayout()).numColumns = 2;

		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptUiMappingsMessages.TypeSection_default, 
			buildNoConverterHolder(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
		
		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptUiMappingsMessages.TypeSection_lob, 
			buildConverterBooleanHolder(Converter.LOB_CONVERTER), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;
		
		PropertyValueModel<Converter> specifiedConverterHolder = buildSpecifiedConverterHolder();
		// Temporal
		addRadioButton(
			container, 
			JptUiMappingsMessages.TypeSection_temporal, 
			buildConverterBooleanHolder(Converter.TEMPORAL_CONVERTER), 
			null);
		registerSubPane(new TemporalTypeComposite(buildTemporalConverterHolder(specifiedConverterHolder), container, getWidgetFactory()));
		
		
		// Enumerated
		addRadioButton(
			container, 
			JptUiMappingsMessages.TypeSection_enumerated, 
			buildConverterBooleanHolder(Converter.ENUMERATED_CONVERTER), 
			null);
		registerSubPane(new EnumTypeComposite(buildEnumeratedConverterHolder(specifiedConverterHolder), container, getWidgetFactory()));
	}
}