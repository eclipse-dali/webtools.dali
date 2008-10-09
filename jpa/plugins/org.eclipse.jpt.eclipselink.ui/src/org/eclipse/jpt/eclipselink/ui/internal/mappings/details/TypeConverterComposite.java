/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.TypeConverter;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Name:      |                                                             ||
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EclipseLinkConverter
 * @see ConvertComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class TypeConverterComposite extends FormPane<TypeConverter>
{

	/**
	 * Creates a new <code>ConverterComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TypeConverterComposite(PropertyValueModel<? extends TypeConverter> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		addLabeledText(
			container, 
			EclipseLinkUiMappingsMessages.ConverterComposite_nameTextLabel, 
			buildNameTextHolder());
		
		addDataTypeChooser(container);
		addObjectTypeChooser(container);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<TypeConverter, String>(
				getSubjectHolder(), EclipseLinkConverter.NAME_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getName();
			}
		
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setName(value);
			}
		};
	}

	
	private ClassChooserPane<TypeConverter> addDataTypeChooser(Composite container) {

		return new ClassChooserPane<TypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<TypeConverter, String>(getSubjectHolder(), TypeConverter.DATA_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getDataType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						subject.setDataType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getDataType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMappingsMessages.TypeConverterComposite_dataTypeLabel;
			}

			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}

			@Override
			protected void promptType() {
				IType type = chooseType();

				if (type != null) {
					String className = type.getFullyQualifiedName('.');
					getSubject().setDataType(className);
				}
			}
		};
	}

	private ClassChooserPane<TypeConverter> addObjectTypeChooser(Composite container) {

		return new ClassChooserPane<TypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<TypeConverter, String>(getSubjectHolder(), TypeConverter.OBJECT_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return subject.getObjectType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						subject.setObjectType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getObjectType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMappingsMessages.TypeConverterComposite_objectTypeLabel;
			}

			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}

			@Override
			protected void promptType() {
				IType type = chooseType();

				if (type != null) {
					String className = type.getFullyQualifiedName('.');
					getSubject().setObjectType(className);
				}
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<TypeConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(TypeConverter value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
}
