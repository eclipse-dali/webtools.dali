/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.EclipseLinkJavaConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.CustomConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ObjectTypeConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.StructConverterComposite;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.TypeConverterComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | X CustomConverter                                                         |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | CustomConverterComposite                                            | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * | X Type Converter                                                          |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | TypeConverterComposite                                              | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * | X Oject Type Converter                                                    |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | ObjectTypeConverterComposite                                        | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * |                                                                           |
 * | X Struct Converter                                                        |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | StructConverterComposite                                            | |
 * |   |                                                                     | |
 * |   ----------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 *
 * @version 2.1
 * @since 2.1
 */
public class ConvertersComposite extends Pane<EclipseLinkJavaConverterHolder>
{

	/**
	 * Creates a new <code>ConversionComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ConvertersComposite(Pane<?> parentPane,
								PropertyValueModel<? extends EclipseLinkJavaConverterHolder> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		initializeConverterPane(container);
	}
	
	private void initializeConverterPane(Composite container) {
		// Custom Converter check box
		Button customConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_customConverter,
			buildCustomConverterBooleanHolder(),
			null
		);

		// Custom Converter pane
		CustomConverterComposite customConverterComposite = new CustomConverterComposite(
			buildCustomConverterHolder(),
			addSubPane(container, 0, customConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		registerSubPane(customConverterComposite);
	
		
		// Type Converter check box
		Button typeConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_typeConverter,
			buildTypeConverterBooleanHolder(),
			null
		);

		// Type Converter pane
		TypeConverterComposite typeConverterComposite = new TypeConverterComposite(
			buildTypeConverterHolder(),
			addSubPane(container, 0, typeConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		registerSubPane(typeConverterComposite);
		
		// Object Type Converter check box
		Button objectTypeConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_objectTypeConverter,
			buildObjectTypeConverterBooleanHolder(),
			null
		);

		// Object Type Converter pane
		ObjectTypeConverterComposite objectTypeConverterComposite = new ObjectTypeConverterComposite(
			buildObjectTypeConverterHolder(),
			addSubPane(container, 0, objectTypeConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		registerSubPane(objectTypeConverterComposite);
		
		// Struct Converter check box
		Button structConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_structConverter,
			buildStructConverterBooleanHolder(),
			null
		);

		// Struct Converter pane
		StructConverterComposite structConverterComposite = new StructConverterComposite(
			buildStructConverterHolder(),
			addSubPane(container, 0, structConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		registerSubPane(structConverterComposite);
	}
	
	private WritablePropertyValueModel<Boolean> buildCustomConverterBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, Boolean>(getSubjectHolder(), EclipseLinkJavaConverterHolder.CUSTOM_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getCustomConverter() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue() && (this.subject.getCustomConverter() == null)) {
					this.subject.addCustomConverter();
				}
				else if (!value.booleanValue() && (this.subject.getCustomConverter() != null)) {
					this.subject.removeCustomConverter();
				}
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkCustomConverter> buildCustomConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, EclipseLinkCustomConverter>(getSubjectHolder(), EclipseLinkJavaConverterHolder.CUSTOM_CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkCustomConverter buildValue_() {
				return this.subject.getCustomConverter();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildTypeConverterBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, Boolean>(getSubjectHolder(), EclipseLinkJavaConverterHolder.TYPE_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getTypeConverter() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue() && (this.subject.getTypeConverter() == null)) {
					this.subject.addTypeConverter();
				}
				else if (!value.booleanValue() && (this.subject.getTypeConverter() != null)) {
					this.subject.removeTypeConverter();
				}
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkTypeConverter> buildTypeConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, EclipseLinkTypeConverter>(getSubjectHolder(), EclipseLinkJavaConverterHolder.TYPE_CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkTypeConverter buildValue_() {
				return this.subject.getTypeConverter();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildObjectTypeConverterBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, Boolean>(getSubjectHolder(), EclipseLinkJavaConverterHolder.OBJECT_TYPE_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getObjectTypeConverter() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue() && (this.subject.getObjectTypeConverter() == null)) {
					this.subject.addObjectTypeConverter();
				}
				else if (!value.booleanValue() && (this.subject.getObjectTypeConverter() != null)) {
					this.subject.removeObjectTypeConverter();
				}
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkObjectTypeConverter> buildObjectTypeConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, EclipseLinkObjectTypeConverter>(getSubjectHolder(), EclipseLinkJavaConverterHolder.OBJECT_TYPE_CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkObjectTypeConverter buildValue_() {
				return this.subject.getObjectTypeConverter();
			}
		};
	}
	
	
	private WritablePropertyValueModel<Boolean> buildStructConverterBooleanHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, Boolean>(getSubjectHolder(), EclipseLinkJavaConverterHolder.STRUCT_CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getStructConverter() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue() && (this.subject.getStructConverter() == null)) {
					this.subject.addStructConverter();
				}
				else if (!value.booleanValue() && (this.subject.getStructConverter() != null)) {
					this.subject.removeStructConverter();
				}
			}
		};
	}
	
	private PropertyValueModel<EclipseLinkStructConverter> buildStructConverterHolder() {
		return new PropertyAspectAdapter<EclipseLinkJavaConverterHolder, EclipseLinkStructConverter>(getSubjectHolder(), EclipseLinkJavaConverterHolder.STRUCT_CONVERTER_PROPERTY) {
			@Override
			protected EclipseLinkStructConverter buildValue_() {
				return this.subject.getStructConverter();
			}
		};
	}
}
