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
import java.util.ListIterator;
import org.eclipse.jpt.eclipselink.core.context.Convert;
import org.eclipse.jpt.eclipselink.core.context.CustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
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
 * @see Convert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class ConvertComposite extends FormPane<Convert>
{

	/**
	 * A key used to represent the default value, this is required to convert
	 * the selected item from a combo to <code>null</code>. This key is most
	 * likely never typed the user and it will help to convert the value to
	 * <code>null</code> when it's time to set the new selected value into the
	 * model.
	 */
	protected static String DEFAULT_KEY = "?!#!?#?#?default?#?!#?!#?"; //$NON-NLS-1$
	
	
	/**
	 * Creates a new <code>EnumTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ConvertComposite(PropertyValueModel<? extends Convert> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		CCombo combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiMappingsMessages.ConvertComposite_converterNameLabel,
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
			EclipseLinkUiMappingsMessages.ConvertComposite_default, 
			buildNoConverterHolder(), 
			null);
		PropertyValueModel<EclipseLinkConverter> converterHolder = buildConverterHolder();
		// CustomConverter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_custom, 
			buildConverterHolder(EclipseLinkConverter.CUSTOM_CONVERTER), 
			null);
		CustomConverterComposite converterComposite = new CustomConverterComposite(buildCustomConverterHolder(converterHolder), subSection, getWidgetFactory());
		GridData gridData = (GridData) converterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(converterComposite);
		
		// Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_type, 
			buildConverterHolder(EclipseLinkConverter.TYPE_CONVERTER), 
			null);
		TypeConverterComposite typeConverterComposite = new TypeConverterComposite(buildTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) typeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(typeConverterComposite);

		// Object Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_objectType, 
			buildConverterHolder(EclipseLinkConverter.OBJECT_TYPE_CONVERTER), 
			null);
		ObjectTypeConverterComposite objectTypeConverterComposite = new ObjectTypeConverterComposite(buildObjectTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) objectTypeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(objectTypeConverterComposite);

		// Struct Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiMappingsMessages.ConvertComposite_struct, 
			buildConverterHolder(EclipseLinkConverter.STRUCT_CONVERTER), 
			null);
		StructConverterComposite structConverterComposite = new StructConverterComposite(buildStructConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) structConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(structConverterComposite);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected final WritablePropertyValueModel<String> buildConvertNameHolder() {
		return new PropertyAspectAdapter<Convert, String>(getSubjectHolder(), Convert.SPECIFIED_CONVERTER_NAME_PROPERTY) {
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
		list.add(buildConverterNameListHolder());
		return new CompositeListValueModel<ListValueModel<String>, String>(list);
	}
	
	protected ListValueModel<String> buildDefaultNameListHolder() {
		return new PropertyListValueModelAdapter<String>(
			buildDefaultNameHolder()
		);
	}

	private WritablePropertyValueModel<String> buildDefaultNameHolder() {
		return new PropertyAspectAdapter<Convert, String>(getSubjectHolder(), Convert.DEFAULT_CONVERTER_NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				String name = this.subject.getDefaultConverterName();

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
							JptUiMappingsMessages.DefaultWithOneParam,
							defaultName
						);
					}
					else {
						value = JptUiMappingsMessages.DefaultEmpty;
					}
				}

				return value;
			}
		};
	}

	protected ListValueModel<String> buildReservedConverterNameListHolder() {
		return new StaticListValueModel<String>(Convert.RESERVED_CONVERTER_NAMES);
	}
	
	protected ListValueModel<String> buildConverterNameListHolder() {
		return new ListAspectAdapter<EclipseLinkPersistenceUnit, String>(
			buildPersistenceUnitHolder(),
			EclipseLinkPersistenceUnit.CONVERTERS_LIST)//TODO need EclipseLinkPersistenceUnit interface
		{
			@Override
			protected ListIterator<String> listIterator_() {
				return CollectionTools.listIterator(CollectionTools.sort(this.subject.uniqueConverterNames()));
			}
		};
	}
	
	protected PropertyValueModel<EclipseLinkPersistenceUnit> buildPersistenceUnitHolder() {
		return new PropertyAspectAdapter<Convert, EclipseLinkPersistenceUnit>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPersistenceUnit buildValue_() {
				return (EclipseLinkPersistenceUnit) getSubject().getPersistenceUnit();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildNoConverterHolder() {
		return new PropertyAspectAdapter<Convert, Boolean>(getSubjectHolder(), Convert.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getConverter() == null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue()) {
					this.subject.setConverter(EclipseLinkConverter.NO_CONVERTER);
				}
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildConverterHolder(final String converterType) {
		return new PropertyAspectAdapter<Convert, Boolean>(getSubjectHolder(), Convert.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				EclipseLinkConverter converter = this.subject.getConverter();
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
	
	private PropertyValueModel<EclipseLinkConverter> buildConverterHolder() {
		return new PropertyAspectAdapter<Convert, EclipseLinkConverter>(getSubjectHolder(), Convert.CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkConverter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	private PropertyValueModel<CustomConverter> buildCustomConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, CustomConverter>(converterHolder) {
			@Override
			protected CustomConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) ? (CustomConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<TypeConverter> buildTypeConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, TypeConverter>(converterHolder) {
			@Override
			protected TypeConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) ? (TypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<ObjectTypeConverter> buildObjectTypeConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, ObjectTypeConverter>(converterHolder) {
			@Override
			protected ObjectTypeConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) ? (ObjectTypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<StructConverter> buildStructConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, StructConverter>(converterHolder) {
			@Override
			protected StructConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) ? (StructConverter) converter : null;
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<Convert, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(Convert value) {
				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
					return Boolean.FALSE;
				}
				return Boolean.valueOf(value != null);
			}
		};
	}
}
