/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomConverter;
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
public class EclipseLinkCustomConverterComposite extends Pane<EclipseLinkCustomConverter>
{

	/**
	 * Creates a new <code>ConverterComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkCustomConverterComposite(PropertyValueModel<? extends EclipseLinkCustomConverter> subjectHolder,
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
		
		addClassChooser(container);
		
		new PaneEnabler(buildBooleanHolder(), this);
	}
	
	protected WritablePropertyValueModel<String> buildNameTextHolder() {
		return new PropertyAspectAdapter<EclipseLinkCustomConverter, String>(
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

	
	private ClassChooserPane<EclipseLinkCustomConverter> addClassChooser(Composite container) {

		return new ClassChooserPane<EclipseLinkCustomConverter>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EclipseLinkCustomConverter, String>(getSubjectHolder(), EclipseLinkCustomConverter.CONVERTER_CLASS_PROPERTY) {
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
			protected String getLabelText() {
				return EclipseLinkUiDetailsMessages.EclipseLinkConverterComposite_classLabel;
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
		};
	}


	protected PropertyValueModel<Boolean> buildBooleanHolder() {
		return new TransformationPropertyValueModel<EclipseLinkCustomConverter, Boolean>(getSubjectHolder()) {
			@Override
			protected Boolean transform(EclipseLinkCustomConverter value) {
//				if (getSubject() != null && getSubject().getParent().getPersistentAttribute().isVirtual()) {
//					return Boolean.FALSE;
//				}
				return Boolean.valueOf(value != null);
			}
		};
	}
}
