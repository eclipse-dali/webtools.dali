/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.ArrayList;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkNamedConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Enum Type: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConvert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.0
 * @since 1.0
 */
public class ConvertComposite extends FormPane<EclipseLinkConvert>
{

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?";
	
	
	/**
	 * Creates a new <code>EnumTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ConvertComposite(PropertyValueModel<? extends EclipseLinkConvert> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		CCombo combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiMappingsMessages.ConvertComposite_convertNameLabel,
			buildConvertNameListHolder(),
			buildConvertNameHolder(),
			buildNameConverter(),
			null
		);
		SWTUtil.attachDefaultValueHandler(combo);
		
		Composite subSection = addCollapsableSubSection(
			container, 
			EclipseLinkUiMappingsMessages.ConvertComposite_defineConverterSection, 
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE));
		
		// No Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_noConverter, 
			buildNoConverterHolder(), 
			null);
		PropertyValueModel<EclipseLinkNamedConverter> converterHolder = buildConverterHolder();
		// Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_converter, 
			buildConverterHolder(EclipseLinkNamedConverter.CONVERTER), 
			null);
		ConverterComposite converterComposite = new ConverterComposite(buildConverterHolder(converterHolder), subSection, getWidgetFactory());
		GridData gridData = (GridData) converterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(converterComposite);
		
		// Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_typeConverter, 
			buildConverterHolder(EclipseLinkNamedConverter.TYPE_CONVERTER), 
			null);
		TypeConverterComposite typeConverterComposite = new TypeConverterComposite(buildTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) typeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(typeConverterComposite);

		// Object Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_objectTypeConverter, 
			buildConverterHolder(EclipseLinkNamedConverter.OBJECT_TYPE_CONVERTER), 
			null);
		ObjectTypeConverterComposite objectTypeConverterComposite = new ObjectTypeConverterComposite(buildObjectTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) objectTypeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(objectTypeConverterComposite);

		// Struct Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_structConverter, 
			buildConverterHolder(EclipseLinkNamedConverter.STRUCT_CONVERTER), 
			null);
		StructConverterComposite structConverterComposite = new StructConverterComposite(buildStructConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) structConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(structConverterComposite);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected final WritablePropertyValueModel<String> buildConvertNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.SPECIFIED_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getSpecifiedConverterName();
			}

			@Override
			protected void setValue_(String value) {
				// Convert the default value or an empty string to null
				if ((value != null) &&
				   ((value.length() == 0) || value.startsWith(DEFAULT_KEY))) {

					value = null;
				}
				this.subject.setSpecifiedConverterName(value);
			}
		};
	}
	private ListValueModel<String> buildConvertNameListHolder() {
		java.util.List<ListValueModel<String>> list = new ArrayList<ListValueModel<String>>();
		list.add(buildDefaultNameListHolder());
		list.add(buildReservedConverterNameListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(list);
	}
	
	protected ListValueModel<String> buildDefaultNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultNameHolder()
		);
	}

	private WritablePropertyValueModel<String> buildDefaultNameHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, String>(getSubjectHolder(), EclipseLinkConvert.DEFAULT_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = subject.getDefaultConverterName();

				if (name == null) {
					name = DEFAULT_KEY;
				}
				else {
					name = DEFAULT_KEY + name;
				}

				return name;
			}
		};
	}

	private StringConverter<String> buildNameConverter() {
		return new StringConverter<String>() {
			public String convertToString(String value) {

				if (getSubject() == null) {
					return value;
				}

				if (value == null) {
					value = getSubject().getDefaultConverterName();

					if (value != null) {
						value = DEFAULT_KEY + value;
					}
					else {
						value = DEFAULT_KEY;
					}
				}

				if (value.startsWith(DEFAULT_KEY)) {
					String defaultName = value.substring(DEFAULT_KEY.length());

					if (defaultName.length() > 0) {
						value = NLS.bind(
							JptUiMappingsMessages.DefaultWithValue,
							defaultName
						);
					}
					else {
						value = JptUiMappingsMessages.DefaultWithoutValue;
					}
				}

				return value;
			}
		};
	}

	//TODO converter name repository, have another ListValueModel for these
	protected ListValueModel<String> buildReservedConverterNameListHolder() {
		return new StaticListValueModel<String>(CollectionTools.list(EclipseLinkConvert.RESERVED_CONVERTER_NAMES));
	}
	
	private WritablePropertyValueModel<Boolean> buildNoConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, Boolean>(getSubjectHolder(), EclipseLinkConvert.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getConverter() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(EclipseLinkNamedConverter.NO_CONVERTER);
				}
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildConverterHolder(final String converterType) {
		return new PropertyAspectAdapter<EclipseLinkConvert, Boolean>(getSubjectHolder(), EclipseLinkConvert.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				EclipseLinkNamedConverter converter = this.subject.getConverter();
				if (converter == null) {
					return Boolean.FALSE;
				}
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
	
	private PropertyValueModel<EclipseLinkNamedConverter> buildConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkConvert, EclipseLinkNamedConverter>(getSubjectHolder(), EclipseLinkConvert.CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkNamedConverter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkConverter> buildConverterHolder(PropertyValueModel<EclipseLinkNamedConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkNamedConverter, EclipseLinkConverter>(converterHolder) {
			@Override
			protected EclipseLinkConverter transform_(EclipseLinkNamedConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkNamedConverter.CONVERTER) ? (EclipseLinkConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkTypeConverter> buildTypeConverterHolder(PropertyValueModel<EclipseLinkNamedConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkNamedConverter, EclipseLinkTypeConverter>(converterHolder) {
			@Override
			protected EclipseLinkTypeConverter transform_(EclipseLinkNamedConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkNamedConverter.TYPE_CONVERTER) ? (EclipseLinkTypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConverterHolder(PropertyValueModel<EclipseLinkNamedConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkNamedConverter, EclipseLinkObjectTypeConverter>(converterHolder) {
			@Override
			protected EclipseLinkObjectTypeConverter transform_(EclipseLinkNamedConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkNamedConverter.OBJECT_TYPE_CONVERTER) ? (EclipseLinkObjectTypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkStructConverter> buildStructConverterHolder(PropertyValueModel<EclipseLinkNamedConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkNamedConverter, EclipseLinkStructConverter>(converterHolder) {
			@Override
			protected EclipseLinkStructConverter transform_(EclipseLinkNamedConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkNamedConverter.STRUCT_CONVERTER) ? (EclipseLinkStructConverter) converter : null;
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EclipseLinkConvert, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkConvert value) {
				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(value != null);
			}
		};
	}
}
