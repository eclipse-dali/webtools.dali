/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
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
 * @see EclipseLinkConvertComposite - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkTypeConverterComposite extends Pane<EclipseLinkTypeConverter>
{

	/**
	 * Creates a new <code>TypeConverterComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkTypeConverterComposite(PropertyValueModel<? extends EclipseLinkTypeConverter> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {
		
		addLabeledText(
			container, 
			EclipseLinkUiDetailsMessages.EclipseLinkConverterComposite_nameTextLabel, 
			buildNameTextHolder());
		
		addDataTypeChooser(container);
		addObjectTypeChooser(container);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkTypeConverter, String>(
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

	
	private ClassChooserPane<EclipseLinkTypeConverter> addDataTypeChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkTypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkTypeConverter, String>(getSubjectHolder(), EclipseLinkTypeConverter.DATA_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDataType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setDataType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getDataType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkTypeConverterComposite_dataTypeLabel;
			}

			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}

			@Override
			protected void setClassName(String className) {
				getSubject().setDataType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}
		};
	}

	private ClassChooserPane<EclipseLinkTypeConverter> addObjectTypeChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkTypeConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkTypeConverter, String>(getSubjectHolder(), EclipseLinkTypeConverter.OBJECT_TYPE_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getObjectType();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setObjectType(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getObjectType();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkTypeConverterComposite_objectTypeLabel;
			}

			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setObjectType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}
		};
	}

	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EclipseLinkTypeConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkTypeConverter value) {
				return Boolean.valueOf(value != null);
			}
		};
	}
}
