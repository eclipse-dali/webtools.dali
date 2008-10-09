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

import org.eclipse.jpt.eclipselink.core.context.Converter;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.core.context.StructConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.core.context.java.JavaConverterHolder;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.ConverterComposite;
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
 * | X Converter                                                               |
 * |                                                                           |
 * |   ----------------------------------------------------------------------- |
 * |   |                                                                     | |
 * |   | ConverterComposite                                                  | |
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
public class ConvertersComposite extends Pane<JavaConverterHolder>
{

	/**
	 * Creates a new <code>ConversionComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public ConvertersComposite(Pane<?> parentPane,
								PropertyValueModel<? extends JavaConverterHolder> subjectHolder,
								Composite parent) {

		super(parentPane, subjectHolder, parent);
	}
	
	@Override
	protected void initializeLayout(Composite container) {

		initializeConverterPane(container);
	}
	
	private void initializeConverterPane(Composite container) {
		// Converter check box
		Button converterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_converter,
			buildConverterBooleanHolder(),
			null
		);

		// Converter pane
		new ConverterComposite(
			buildConverterHolder(),
			addSubPane(container, 0, converterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		
		// Type Converter check box
		Button typeConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_typeConverter,
			buildTypeConverterBooleanHolder(),
			null
		);

		// Type Converter pane
		new TypeConverterComposite(
			buildTypeConverterHolder(),
			addSubPane(container, 0, typeConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		
		// Object Type Converter check box
		Button objectTypeConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_objectTypeConverter,
			buildObjectTypeConverterBooleanHolder(),
			null
		);

		// Object Type Converter pane
		new ObjectTypeConverterComposite(
			buildObjectTypeConverterHolder(),
			addSubPane(container, 0, objectTypeConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
		
		// Struct Converter check box
		Button structConverterCheckBox = addCheckBox(
			addSubPane(container, 5),
			EclipseLinkUiMappingsMessages.ConvertersComposite_structConverter,
			buildStructConverterBooleanHolder(),
			null
		);

		// Struct Converter pane
		new StructConverterComposite(
			buildStructConverterHolder(),
			addSubPane(container, 0, structConverterCheckBox.getBorderWidth() + 16),
			getWidgetFactory()
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildConverterBooleanHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, Boolean>(getSubjectHolder(), JavaConverterHolder.CONVERTER_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return Boolean.valueOf(this.subject.getConverter() != null);
			}

			@Override
			protected void setValue_(Boolean value) {
				if (value.booleanValue() && (this.subject.getConverter() == null)) {
					this.subject.addConverter();
				}
				else if (!value.booleanValue() && (this.subject.getConverter() != null)) {
					this.subject.removeConverter();
				}
			}
		};
	}
	
	private PropertyValueModel<Converter> buildConverterHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, Converter>(getSubjectHolder(), JavaConverterHolder.CONVERTER_PROPERTY) {
			@Override
			protected Converter buildValue_() {
				return this.subject.getConverter();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildTypeConverterBooleanHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, Boolean>(getSubjectHolder(), JavaConverterHolder.TYPE_CONVERTER_PROPERTY) {
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
	
	private PropertyValueModel<TypeConverter> buildTypeConverterHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, TypeConverter>(getSubjectHolder(), JavaConverterHolder.TYPE_CONVERTER_PROPERTY) {
			@Override
			protected TypeConverter buildValue_() {
				return this.subject.getTypeConverter();
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildObjectTypeConverterBooleanHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, Boolean>(getSubjectHolder(), JavaConverterHolder.OBJECT_TYPE_CONVERTER_PROPERTY) {
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
	
	private PropertyValueModel<ObjectTypeConverter> buildObjectTypeConverterHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, ObjectTypeConverter>(getSubjectHolder(), JavaConverterHolder.OBJECT_TYPE_CONVERTER_PROPERTY) {
			@Override
			protected ObjectTypeConverter buildValue_() {
				return this.subject.getObjectTypeConverter();
			}
		};
	}
	
	
	private WritablePropertyValueModel<Boolean> buildStructConverterBooleanHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, Boolean>(getSubjectHolder(), JavaConverterHolder.STRUCT_CONVERTER_PROPERTY) {
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
	
	private PropertyValueModel<StructConverter> buildStructConverterHolder() {
		return new PropertyAspectAdapter<JavaConverterHolder, StructConverter>(getSubjectHolder(), JavaConverterHolder.STRUCT_CONVERTER_PROPERTY) {
			@Override
			protected StructConverter buildValue_() {
				return this.subject.getStructConverter();
			}
		};
	}
}
