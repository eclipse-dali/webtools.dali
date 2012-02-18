/*******************************************************************************
* Copyright (c) 2008, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.Customization;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.CustomizationEntity;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.swt.widgets.Composite;

/**
 *  CustomizerComposite
 */
public class CustomizerComposite extends Pane<CustomizationEntity>
{
	/**
	 * Creates a new <code>CustomizerComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public CustomizerComposite(Pane<? extends CustomizationEntity> parentPane,
                           Composite parent) {

		super(parentPane, parent);
	}

	private ClassChooserPane<CustomizationEntity> initializeClassChooser(Composite container) {

		return new ClassChooserPane<CustomizationEntity>(this, container) {

			@Override
			protected ModifiablePropertyValueModel<String> buildTextHolder() {
				return new PropertyAspectAdapter<CustomizationEntity, String>(
					this.getSubjectHolder(), CustomizationEntity.DESCRIPTOR_CUSTOMIZER_PROPERTY) {
					@Override
					protected String buildValue_() {
						return getSubjectParent().getDescriptorCustomizerOf(getSubjectName());
					}

					@Override
					protected void setValue_(String value) {

						if (value.length() == 0) {
							value = null;
						}
						getSubjectParent().setDescriptorCustomizerOf(getSubjectName(), value);
					}
				};
			}

			@Override
			protected String getClassName() {
				return getSubjectParent().getDescriptorCustomizerOf(getSubjectName());
			}

			@Override
			protected String getLabelText() {
				return EclipseLinkUiMessages.PersistenceXmlCustomizationTab_customizerLabel;
			}
			
			@Override
			protected IJavaProject getJavaProject() {
				return getSubjectParent().getJpaProject().getJavaProject();
			}

			@Override
			protected void setClassName(String className) {
				getSubjectParent().setDescriptorCustomizerOf(getSubjectName(), className);
			}
			
			@Override
			protected String getSuperInterfaceName() {
				return EclipseLinkCustomizer.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME;
			}
		};
	}
	
	private String getSubjectName() {
		return this.getSubjectHolder().getValue().getName();
	}
	
	private Customization getSubjectParent() {
		return this.getSubjectHolder().getValue().getParent();
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.initializeClassChooser(container);
	}
}
