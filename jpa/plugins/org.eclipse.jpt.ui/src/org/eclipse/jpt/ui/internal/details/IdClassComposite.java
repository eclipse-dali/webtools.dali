/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.context.IdClassReference;
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
 * Id class hyperlink label, combo, and browse button
 *
 */
public class IdClassComposite<T extends IdClassReference>
	extends Pane<T>
{
	/**
	 * Creates a new <code>IdClassComposite</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public IdClassComposite(
			Pane<?> parentPane,
			PropertyValueModel<T> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	public IdClassComposite(
			Pane<?> parentPane,
			PropertyValueModel<T> subjectHolder,
			Composite parent,
        	boolean automaticallyAlignWidgets) {
		
		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}
	
	
	@Override
	protected void initializeLayout(Composite container) {
		new IdClassChooserComboPane(this, container);
	}
	
	
	private class IdClassChooserComboPane
		extends ClassChooserComboPane<T>
	{
		public IdClassChooserComboPane(Pane<T> parentPane, Composite parent) {
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
		protected JpaProject getJpaProject() {
			return getSubject().getJpaProject();
		}
		
		@Override
		protected char getEnclosingTypeSeparator() {
			return getSubject().getIdClassEnclosingTypeSeparator();
		}
		
		@Override
		protected WritablePropertyValueModel<String> buildTextHolder() {
			return new PropertyAspectAdapter<T, String>(
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
		
		protected String defaultText(T idClassReference) {
			String defaultClassName = idClassReference.getDefaultIdClassName();
			return (defaultClassName == null) ?
					JptUiDetailsMessages.NoneSelected
					: NLS.bind(JptUiDetailsMessages.DefaultWithOneParam, defaultClassName);
		}
		
		@Override
		protected ListValueModel<String> buildClassListHolder() {
			return new PropertyListValueModelAdapter<String>(
				new PropertyAspectAdapter<T, String>(
						getSubjectHolder(), IdClassReference.DEFAULT_ID_CLASS_NAME_PROPERTY) {
					@Override
					protected String buildValue_() {
						return defaultText(this.subject);
					}
				});
		}
	}
}
