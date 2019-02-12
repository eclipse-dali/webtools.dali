/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterClassConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
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
public class EclipseLinkCustomConverterComposite
	extends Pane<EclipseLinkCustomConverter>
{
	public EclipseLinkCustomConverterComposite(Pane<?> parentPane, PropertyValueModel<? extends EclipseLinkCustomConverter> subjectModel, Composite parentComposite) {
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
		
		Hyperlink hyperlink = this.addHyperlink(container, JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERTER_COMPOSITE_CLASS_LABEL);
		this.addClassChooser(container, hyperlink);
	}
	
	protected ModifiablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkCustomConverter, String>(
				getSubjectHolder(), JpaNamedContextModel.NAME_PROPERTY) {
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

	
	private ClassChooserPane<EclipseLinkCustomConverter> addClassChooser(Composite container, Hyperlink hyperlink) {

		return new ClassChooserPane<EclipseLinkCustomConverter>(this, container, hyperlink) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkCustomConverter, String>(getSubjectHolder(), EclipseLinkConverterClassConverter.CONVERTER_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getConverterClass();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setConverterClass(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getConverterClass();
			}

			@Override
			protected IJavaProject getJavaProject() {
				return getSubject().getJpaProject().getJavaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setConverterClass(className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return EclipseLinkCustomConverter.ECLIPSELINK_CONVERTER_CLASS_NAME;
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getEnclosingTypeSeparator();
			}

			@Override
			protected String getFullyQualifiedClassName() {
				return getSubject().getFullyQualifiedConverterClass();
			}
		};
	}
}
