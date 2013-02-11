/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

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
 * @see EclipseLinkConvertCombo - A container of this widget
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkTypeConverterComposite
	extends Pane<EclipseLinkTypeConverter>
{
	public EclipseLinkTypeConverterComposite(Pane<?> parentPane, PropertyValueModel<? extends EclipseLinkTypeConverter> subjectModel, Composite parentComposite) {
		super(parentPane, subjectModel, parentComposite);
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.addLabel(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERTER_COMPOSITE_NAME_TEXT_LABEL);
		this.addText(container, buildNameTextHolder());
		
		Hyperlink dataTypeHyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_CONVERTER_COMPOSITE_DATA_TYPE_LABEL);
		this.addDataTypeChooser(container, dataTypeHyperlink);
		
		Hyperlink objectTypeHyperlink = this.addHyperlink(container,  JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_TYPE_CONVERTER_COMPOSITE_OBJECT_TYPE_LABEL);
		this.addObjectTypeChooser(container, objectTypeHyperlink);
	}
	
	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkTypeConverter, String>(
				getSubjectHolder(), JpaNamedContextNode.NAME_PROPERTY) {
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

	
	private ClassChooserPane<EclipseLinkTypeConverter> addDataTypeChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkTypeConverter>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
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
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				getSubject().setDataType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedDataType();
			}
		};
	}

	private ClassChooserPane<EclipseLinkTypeConverter> addObjectTypeChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkTypeConverter>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
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
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setObjectType(className);
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedObjectType();
			}
		};
	}
}
