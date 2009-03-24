/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.context.Customizer;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @see Customizer
 *
 * @version 2.1
 * @since 2.1
 */
public class CustomizerComposite extends FormPane<Customizer>
{
	/**
	 * Creates a new <code>CustomizerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public CustomizerComposite(FormPane<?> parentPane, 
		PropertyValueModel<? extends Customizer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		addCustomizerClassChooser(container);
	}
	
	private ClassChooserPane<Customizer> addCustomizerClassChooser(Composite container) {

		return new ClassChooserPane<Customizer>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<Customizer, String>(getSubjectHolder(), Customizer.SPECIFIED_CUSTOMIZER_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getSpecifiedCustomizerClass();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}

						this.subject.setSpecifiedCustomizerClass(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getSpecifiedCustomizerClass();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMappingsMessages.CustomizerComposite_classLabel;
			}

			@Override
			protected JpaProject getJpaProject() {
				return getSubject().getJpaProject();
			}
			
			@Override
			protected void setClassName(String className) {
				getSubject().setSpecifiedCustomizerClass(className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return Customizer.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME;
			}
			
			@Override
			protected char getEnclosingTypeSeparator() {
				return getSubject().getCustomizerClassEnclosingTypeSeparator();
			}
		};
	}

}
