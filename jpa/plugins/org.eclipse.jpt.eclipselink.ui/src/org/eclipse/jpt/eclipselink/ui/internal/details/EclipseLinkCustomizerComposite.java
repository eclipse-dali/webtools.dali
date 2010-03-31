/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.ui.internal.details.JptUiDetailsMessages;
import org.eclipse.jpt.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @see EclipseLinkCustomizer
 *
 * @version 2.3
 * @since 2.1
 */
public class EclipseLinkCustomizerComposite extends Pane<EclipseLinkCustomizer>
{
	/**
	 * Creates a new <code>CustomizerComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public EclipseLinkCustomizerComposite(Pane<?> parentPane, 
		PropertyValueModel<? extends EclipseLinkCustomizer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {
		new CustomizerClassChooserComboPane(this, container);
	}

	private class CustomizerClassChooserComboPane
		extends ClassChooserComboPane<EclipseLinkCustomizer>
	{
		public CustomizerClassChooserComboPane(Pane<EclipseLinkCustomizer> parentPane, Composite parent) {
			super(parentPane, parent);
		}		

		@Override
		protected String getClassName() {
			return getSubject().getSpecifiedCustomizerClass();
		}

		@Override
		protected void setClassName(String className) {
			getSubject().setSpecifiedCustomizerClass(className);
		}

		@Override
		protected String getLabelText() {
			return EclipseLinkUiDetailsMessages.EclipseLinkCustomizerComposite_classLabel;
		}

		@Override
		protected JpaProject getJpaProject() {
			return getSubject().getJpaProject();
		}

		@Override
		protected String getSuperInterfaceName() {
			return EclipseLinkCustomizer.ECLIPSELINK_DESCRIPTOR_CUSTOMIZER_CLASS_NAME;
		}

		@Override
		protected char getEnclosingTypeSeparator() {
			return getSubject().getCustomizerClassEnclosingTypeSeparator();
		}

		@Override
		protected WritablePropertyValueModel<String> buildTextHolder() {
			return new PropertyAspectAdapter<EclipseLinkCustomizer, String>(
					getSubjectHolder(), 
					EclipseLinkCustomizer.SPECIFIED_CUSTOMIZER_CLASS_PROPERTY,
					EclipseLinkCustomizer.DEFAULT_CUSTOMIZER_CLASS_PROPERTY) {

				@Override
				protected String buildValue_() {
					String value = this.subject.getSpecifiedCustomizerClass();
					return (value == null) ? defaultText(this.subject) : value;
				}

				@Override
				protected void setValue_(String value) {
					if (value == null 
							|| value.length() == 0 
							|| value.equals(defaultText(this.subject))) {
						value = null;
					}
					this.subject.setSpecifiedCustomizerClass(value);
				}
			};
		}

		protected String defaultText(EclipseLinkCustomizer customizer) {
			String defaultClassName = customizer.getDefaultCustomizerClass();
			return (defaultClassName == null) ?
					JptUiDetailsMessages.NoneSelected
					: NLS.bind(JptUiDetailsMessages.DefaultWithOneParam, defaultClassName);
		}

		@Override
		protected ListValueModel<String> buildClassListHolder() {
			return new PropertyListValueModelAdapter<String>(
				new PropertyAspectAdapter<EclipseLinkCustomizer, String>(
						getSubjectHolder(), EclipseLinkCustomizer.DEFAULT_CUSTOMIZER_CLASS_PROPERTY) {
					@Override
					protected String buildValue_() {
						return defaultText(this.subject);
					}
				});
		}
	}
}
