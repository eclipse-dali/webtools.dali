/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.IdClassReference;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Id class hyperlink label, combo, and browse button
 *
 */
public class IdClassComposite
	extends Pane<IdClassReference>
{
	/**
	 * Creates a new <code>IdClassComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public IdClassComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends IdClassReference> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	public IdClassComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends IdClassReference> subjectHolder,
			Composite parent,
        	boolean automaticallyAlignWidgets) {
		
		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		new IdClassChooserComboPane(this, container);
	}
	
	
	private class IdClassChooserComboPane
		extends ClassChooserComboPane<IdClassReference>
	{
		public IdClassChooserComboPane(Pane<IdClassReference> parentPane, Composite parent) {
			super(parentPane, parent);
		}
		
		
		@Override
		protected String getClassName() {
			return getSubject().getIdClassName();
		}
		
		@Override
		protected void setClassName(String className) {
			getSubject().setSpecifiedIdClassName(className);
		}
		
		@Override
		protected String getLabelText() {
			return JptUiDetailsMessages.IdClassComposite_label;
		}
		
		@Override
		protected IJavaProject getJavaProject() {
			return getSubject().getJpaProject().getJavaProject();
		}
		
		@Override
		protected char getEnclosingTypeSeparator() {
			return getSubject().getIdClassEnclosingTypeSeparator();
		}

		@Override
		protected String getFullyQualifiedClassName() {
			return getSubject().getFullyQualifiedIdClassName();
		}

		@Override
		protected WritablePropertyValueModel<String> buildTextHolder() {
			return new PropertyAspectAdapter<IdClassReference, String>(
					getSubjectHolder(), 
					IdClassReference.SPECIFIED_ID_CLASS_NAME_PROPERTY,
					IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {
				
				@Override
				protected String buildValue_() {
					String value = this.subject.getSpecifiedIdClassName();
					return (value == null) ? defaultText(this.subject) : value;
				}
				
				@Override
				protected void setValue_(String value) {
					if (value == null 
							|| value.length() == 0 
							|| value.equals(defaultText(this.subject))) {
						value = null;
					}
					this.subject.setSpecifiedIdClassName(value);
				}
			};
		}
		
		protected String defaultText(IdClassReference idClassReference) {
			String defaultClassName = idClassReference.getDefaultIdClassName();
			return (defaultClassName == null) ?
					JptCommonUiMessages.NoneSelected
					: NLS.bind(JptCommonUiMessages.DefaultWithOneParam, defaultClassName);
		}
		
		@Override
		protected ListValueModel<String> buildClassListHolder() {
			return new PropertyListValueModelAdapter<String>(
				new PropertyAspectAdapter<IdClassReference, String>(
						getSubjectHolder(), IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {
					@Override
					protected String buildValue_() {
						return defaultText(this.subject);
					}
				});
		}
	}
}
