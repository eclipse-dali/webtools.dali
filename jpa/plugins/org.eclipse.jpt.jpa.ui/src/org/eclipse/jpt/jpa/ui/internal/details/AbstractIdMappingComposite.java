/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ModifiableAccessReference;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.IdMapping;
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

public abstract class AbstractIdMappingComposite<T extends IdMapping>
	extends Pane<T>
    implements JpaComposite
{
	public AbstractIdMappingComposite(
			PropertyValueModel<? extends T> mappingModel,
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
		section.setText(JptJpaUiDetailsMessages.IdSection_title);
		section.setClient(this.initializeIdSection(section));
	}
	
	protected abstract Control initializeIdSection(Composite container);
	
	protected void initializeTypeCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.TypeSection_type);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractIdMappingComposite.this.initializeTypeSection(section));
				}
			}
		});
	}
	
	protected Control initializeTypeSection(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);
		
		// No converter
		Button noConverterButton = addRadioButton(
				container, 
				JptJpaUiDetailsMessages.TypeSection_default, 
				buildConverterBooleanHolder(null), 
				null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;
				
		PropertyValueModel<Converter> converterHolder = buildConverterHolder();
		// Temporal
		addRadioButton(
				container, 
				JptJpaUiDetailsMessages.TypeSection_temporal, 
				buildConverterBooleanHolder(BaseTemporalConverter.class), 
				null);
		new TemporalTypeCombo(this, this.buildTemporalConverterHolder(converterHolder), container);

		return container;
	}
	
	protected void initializeGenerationCollapsibleSection(Composite container) {
		new IdMappingGenerationComposite(this, container);
	}
	
	protected PropertyValueModel<? extends Column> buildColumnModel() {
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

	protected PropertyValueModel<ModifiableAccessReference> buildAccessReferenceModel() {
		return new PropertyAspectAdapter<T, ModifiableAccessReference>(getSubjectHolder()) {
			@Override
			protected ModifiableAccessReference buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}
}
