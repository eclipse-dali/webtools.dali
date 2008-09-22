/*******************************************************************************
* Copyright (c) 2008 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *  CustomizerComposite
 */
public class CustomizerComposite extends Pane<EntityCustomizerProperties>
{
	/**
	 * Creates a new <code>CustomizerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public CustomizerComposite(Pane<? extends EntityCustomizerProperties> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<EntityCustomizerProperties> initializeClassChooser(Composite container) {

		return new ClassChooserPane<EntityCustomizerProperties>(this, container) {

			@Override
			protected WritablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<EntityCustomizerProperties, String>(
							getSubjectHolder(), Customization.DESCRIPTOR_CUSTOMIZER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return this.subject.getDescriptorCustomizer();
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						this.subject.setDescriptorCustomizer(value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubject().getDescriptorCustomizer();
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_customizerLabel;
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
					getSubject().setDescriptorCustomizer(className);
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		this.initializeClassChooser(container);
	}
}
