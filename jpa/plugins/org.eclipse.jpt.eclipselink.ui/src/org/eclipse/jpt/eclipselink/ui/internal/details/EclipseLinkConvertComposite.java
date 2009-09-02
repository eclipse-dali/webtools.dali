/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import java.util.ArrayList;
import java.util.ListIterator;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
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
 * @see EclipseLinkConvert
 * @see EclipseLinkBasicMappingComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkConvertComposite extends FormPane<EclipseLinkConvert>
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
	public EclipseLinkConvertComposite(PropertyValueModel<? extends EclipseLinkConvert> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		CCombo combo = addLabeledEditableCCombo(
			container,
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_converterNameLabel,
			buildConvertNameListHolder(),
			buildConvertNameHolder(),
			buildNameConverter(),
			null
		);
		SWTUtil.attachDefaultValueHandler(combo);
		
		Composite subSection = addCollapsableSubSection(
			container, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_defineConverterSection, 
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE));
		
		// No Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_default, 
			buildNoConverterHolder(), 
			null);
		PropertyValueModel<EclipseLinkConverter> converterHolder = buildConverterHolder();
		// CustomConverter
		addRadioButton(
			subSection, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_custom, 
			buildConverterHolder(EclipseLinkConverter.CUSTOM_CONVERTER), 
			null);
		EclipseLinkCustomConverterComposite converterComposite = new EclipseLinkCustomConverterComposite(buildCustomConverterHolder(converterHolder), subSection, getWidgetFactory());
		GridData gridData = (GridData) converterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(converterComposite);
		
		// Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_type, 
			buildConverterHolder(EclipseLinkConverter.TYPE_CONVERTER), 
			null);
		EclipseLinkTypeConverterComposite typeConverterComposite = new EclipseLinkTypeConverterComposite(buildTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) typeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(typeConverterComposite);

		// Object Type Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_objectType, 
			buildConverterHolder(EclipseLinkConverter.OBJECT_TYPE_CONVERTER), 
			null);
		EclipseLinkObjectTypeConverterComposite objectTypeConverterComposite = new EclipseLinkObjectTypeConverterComposite(buildObjectTypeConverterHolder(converterHolder), subSection, getWidgetFactory());
		gridData = (GridData) objectTypeConverterComposite.getControl().getLayoutData();
		gridData.horizontalIndent = 20;
		registerSubPane(objectTypeConverterComposite);

		// Struct Converter
		addRadioButton(
			subSection, 
			EclipseLinkUiDetailsMessages.EclipseLinkConvertComposite_struct, 
			buildConverterHolder(EclipseLinkConverter.STRUCT_CONVERTER), 
			null);
		EclipseLinkStructConverterComposite structConverterComposite = new EclipseLinkStructConverterComposite(buildStructConverterHolder(converterHolder), subSection, getWidgetFactory());
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
		list.add(buildConverterNameListHolder());
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
							JptUiDetailsMessages.DefaultWithOneParam,
							defaultName
						);
					}
					else {
						value = JptUiDetailsMessages.DefaultEmpty;
					}
				}

				return value;
			}
		};
	}

	protected ListValueModel<String> buildReservedConverterNameListHolder() {
		return new StaticListValueModel<String>(EclipseLinkConvert.RESERVED_CONVERTER_NAMES);
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
		return new PropertyAspectAdapter<EclipseLinkConvert, EclipseLinkPersistenceUnit>(getSubjectHolder()) {
			@Override
			protected EclipseLinkPersistenceUnit buildValue_() {
				return (EclipseLinkPersistenceUnit) getSubject().getPersistenceUnit();
			}
		};
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
					this.subject.setConverter(EclipseLinkConverter.NO_CONVERTER);
				}
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildConverterHolder(final String converterType) {
		return new PropertyAspectAdapter<EclipseLinkConvert, Boolean>(getSubjectHolder(), EclipseLinkConvert.CONVERTER_PROPERTY) {
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
		return new PropertyAspectAdapter<EclipseLinkConvert, EclipseLinkConverter>(getSubjectHolder(), EclipseLinkConvert.CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkConverter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkCustomConverter> buildCustomConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkCustomConverter>(converterHolder) {
			@Override
			protected EclipseLinkCustomConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.CUSTOM_CONVERTER) ? (EclipseLinkCustomConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkTypeConverter> buildTypeConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkTypeConverter>(converterHolder) {
			@Override
			protected EclipseLinkTypeConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.TYPE_CONVERTER) ? (EclipseLinkTypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkObjectTypeConverter>(converterHolder) {
			@Override
			protected EclipseLinkObjectTypeConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) ? (EclipseLinkObjectTypeConverter) converter : null;
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkStructConverter> buildStructConverterHolder(PropertyValueModel<EclipseLinkConverter> converterHolder) {
		return new TransformationPropertyValueModel<EclipseLinkConverter, EclipseLinkStructConverter>(converterHolder) {
			@Override
			protected EclipseLinkStructConverter transform_(EclipseLinkConverter converter) {
				return (converter != null && converter.getType() == EclipseLinkConverter.STRUCT_CONVERTER) ? (EclipseLinkStructConverter) converter : null;
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
