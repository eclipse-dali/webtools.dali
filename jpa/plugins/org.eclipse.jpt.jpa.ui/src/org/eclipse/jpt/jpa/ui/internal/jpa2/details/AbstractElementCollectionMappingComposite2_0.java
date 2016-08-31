/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.BaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.BaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.CollectionMapping;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.ConvertibleMapping;
import org.eclipse.jpt.jpa.core.context.LobConverter;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.ColumnComposite;
import org.eclipse.jpt.jpa.ui.internal.details.EnumTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.FetchTypeComboViewer;
import org.eclipse.jpt.jpa.ui.internal.details.TemporalTypeCombo;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.PageBook;

public abstract class AbstractElementCollectionMappingComposite2_0<M extends ElementCollectionMapping2_0> 
	extends Pane<M>
	implements JpaComposite
{
	private Control basicValueComposite;
	
	private Control embeddableValueComposite;
	
	protected AbstractElementCollectionMappingComposite2_0(
			PropertyValueModel<? extends M> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager) {
		super(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		initializeElementCollectionCollapsibleSection(container);
		initializeValueCollapsibleSection(container);
		initializeKeyCollapsibleSection(container);
		initializeOrderingCollapsibleSection(container);
	}
	
	protected void initializeElementCollectionCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR |
				ExpandableComposite.TWISTIE |
				ExpandableComposite.EXPANDED);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_SECTION_TITLE);
		section.setClient(this.buildElementCollectionSectionClient(section));
	}

	@SuppressWarnings("unused")
	protected Control buildElementCollectionSectionClient(Composite container) {
		container = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Target class widgets
		Hyperlink hyperlink = this.addHyperlink(container, JptJpaUiDetailsMessages2_0.TARGET_CLASS_COMPOSITE_LABEL);
		new TargetClassChooser2_0(this, container, hyperlink);

		// Fetch type widgets
		this.addLabel(container, JptJpaUiDetailsMessages.BASIC_GENERAL_SECTION_FETCH_LABEL);
		new FetchTypeComboViewer(this, container);

		// Collection table widgets
		CollectionTableComposite2_0 collectionTableComposite = new CollectionTableComposite2_0(this, buildCollectionTableModel(), container);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		collectionTableComposite.getControl().setLayoutData(gridData);

		return container;
	}
	
	protected void initializeOrderingCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.ORDERING_COMPOSITE_ORDERING_GROUP);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractElementCollectionMappingComposite2_0.this.initializeOrderingSection(section));
				}
			}
		});
	}
	
	protected Control initializeOrderingSection(Composite container) {
		return new OrderingComposite2_0(this, container).getControl();
	}
	
	protected void initializeValueCollapsibleSection(Composite container) {
		final Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages2_0.ABSTRACT_ELEMENT_COLLECTION_MAPPING_COMPOSITE_VALUE_SECTION_TITLE);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					section.setClient(AbstractElementCollectionMappingComposite2_0.this.initializeValueSection(section));
				}
			}
		});
	}
	
	@SuppressWarnings("unused")
	protected void initializeKeyCollapsibleSection(Composite container) {
		//nothing yet
	}

	protected Control initializeValueSection(Composite container) {
		PageBook pageBook = new PageBook(container, SWT.NULL);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		pageBook.setLayoutData(gd);
		
		installValueControlSwitcher(pageBook);
		return pageBook;
	}

	protected Control getBasicValueComposite(Composite container) {
		if (this.basicValueComposite == null) {
			this.basicValueComposite = buildBasicValueSection(container);
		}
		return this.basicValueComposite;
	}

	@SuppressWarnings("unused")
	protected Control buildBasicValueSection(Composite container) {
		Composite basicComposite = addSubPane(container);

		new ColumnComposite(this, buildValueColumnModel(), basicComposite);

		// type section
		final Section section = this.getWidgetFactory().createSection(basicComposite, ExpandableComposite.TWISTIE);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiDetailsMessages.TYPE_SECTION_TYPE);
		section.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && section.getClient() == null) {
					Composite converterClient = buildBasicValueTypeSectionClient(section);
					converterClient.setLayoutData(new GridData(GridData.FILL_BOTH));
					section.setClient(converterClient);
				}
			}
		});

		return basicComposite;
	}

	@SuppressWarnings("unused")
	protected Composite buildBasicValueTypeSectionClient(Section section) {
		Composite container = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		container.setLayout(layout);
		// No converter
		Button noConverterButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_DEFAULT, 
			buildNoConverterModel(), 
			null);
		((GridData) noConverterButton.getLayoutData()).horizontalSpan = 2;

		// Lob
		Button lobButton = addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_LOB, 
			buildLobConverterModel(), 
			null);
		((GridData) lobButton.getLayoutData()).horizontalSpan = 2;


		PropertyValueModel<Converter> converterModel = buildConverterModel();
		// Temporal
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_TEMPORAL, 
			buildTemporalBooleanModel(), 
			null);
		new TemporalTypeCombo(this, this.buildTemporalConverterModel(converterModel), container);


		// Enumerated
		addRadioButton(
			container, 
			JptJpaUiDetailsMessages.TYPE_SECTION_ENUMERATED, 
			buildEnumeratedBooleanModel(), 
			null);
		new EnumTypeComboViewer(this, this.buildEnumeratedConverterModel(converterModel), container);

		return container;
	}

	protected Control getEmbeddableValueComposite(Composite container) {
		if (this.embeddableValueComposite == null) {
			this.embeddableValueComposite = buildEmbeddableValueSection(container);
		}
		return this.embeddableValueComposite;
	}

	protected Control buildEmbeddableValueSection(Composite container) {
		return new ElementCollectionValueOverridesComposite2_0(this, container).getControl();
	}

	private void installValueControlSwitcher(PageBook pageBook) {
		SWTBindingTools.bind(buildValueModel(), buildPaneTransformer(pageBook), pageBook);
	}
	
	protected PropertyValueModel<M.Type> buildValueModel() {
		return new PropertyAspectAdapterXXXX<M, M.Type>(
				this.getSubjectHolder(), CollectionMapping.VALUE_TYPE_PROPERTY) {
			@Override
			protected ElementCollectionMapping2_0.Type buildValue_() {
				return this.subject.getValueType();
			}
		};
	}

	private Transformer<M.Type, Control> buildPaneTransformer(Composite container) {
		return new PaneTransformer(container);
	}

	protected class PaneTransformer
		extends TransformerAdapter<M.Type, Control>
	{
		private final Composite container;

		protected PaneTransformer(Composite container) {
			this.container = container;
		}

		@Override
		public Control transform(M.Type type) {
			return AbstractElementCollectionMappingComposite2_0.this.transformValueType(type, this.container);
		}
	}

	/**
	 * Given the selected override, return the control that will be displayed
	 */
	protected Control transformValueType(M.Type type, Composite container) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case BASIC_TYPE :
				return this.getBasicValueComposite(container);
			case EMBEDDABLE_TYPE :
				return this.getEmbeddableValueComposite(container);
			default :
				return null;
		}
	}
	
	protected PropertyValueModel<CollectionTable2_0> buildCollectionTableModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getCollectionTable());
	}
	
	protected PropertyValueModel<SpecifiedColumn> buildValueColumnModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), m -> m.getValueColumn());
	}

	private ModifiablePropertyValueModel<Boolean> buildNoConverterModel() {
		return new PropertyAspectAdapterXXXX<M, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getConverter().getConverterType() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(null);
				}
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildLobConverterModel() {
		return new PropertyAspectAdapterXXXX<M, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getConverterType() == LobConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(LobConverter.class);
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
	
	private PropertyValueModel<BaseTemporalConverter> buildTemporalConverterModel(PropertyValueModel<Converter> converterModel) {
		return PropertyValueModelTools.transform(converterModel, BaseTemporalConverter.CONVERTER_TRANSFORMER);
	}
	
	private PropertyValueModel<BaseEnumeratedConverter> buildEnumeratedConverterModel(PropertyValueModel<Converter> converterModel) {
		return PropertyValueModelTools.transform(converterModel, BaseEnumeratedConverter.CONVERTER_TRANSFORMER);
	}

	private ModifiablePropertyValueModel<Boolean> buildTemporalBooleanModel() {
		return new PropertyAspectAdapterXXXX<M, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getConverterType() == BaseTemporalConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(BaseTemporalConverter.class);
				}
			}
		};
	}
	
	private ModifiablePropertyValueModel<Boolean> buildEnumeratedBooleanModel() {
		return new PropertyAspectAdapterXXXX<M, Boolean>(getSubjectHolder(), ConvertibleMapping.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				Converter converter = this.subject.getConverter();
				return Boolean.valueOf(converter.getConverterType() == BaseEnumeratedConverter.class);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(BaseEnumeratedConverter.class);
				}
			}
		};
	}

	protected PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel() {
		return PropertyValueModelTools.transform(this.getSubjectHolder(), AttributeMapping.PERSISTENT_ATTRIBUTE_TRANSFORMER);
	}
}
