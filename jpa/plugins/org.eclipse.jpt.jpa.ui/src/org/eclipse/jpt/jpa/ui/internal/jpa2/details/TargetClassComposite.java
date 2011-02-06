/*******************************************************************************
* Copyright (c) 2009, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  target entity hyperlink label, combo and browse button 
 */
public class TargetClassComposite extends ClassChooserComboPane<ElementCollectionMapping2_0>
{

	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetClassComposite(
								Pane<? extends ElementCollectionMapping2_0> parentPane,
	                           Composite parent) {

		super(parentPane, parent);
	}

	@Override
	protected String getClassName() {
		return getSubject().getTargetClass();
	}

	@Override
	protected void setClassName(String className) {
		this.getSubject().setSpecifiedTargetClass(className);
	}

	@Override
	protected char getEnclosingTypeSeparator() {
		return getSubject().getTargetClassEnclosingTypeSeparator();
	}
	
    @Override
    protected String getLabelText() {
    	return JptUiDetailsMessages2_0.TargetClassComposite_label;
    }
   
    @Override
    protected String getHelpId() {
    	return JpaHelpContextIds.MAPPING_ELEMENT_COLLECTION_TARGET_CLASS;
    }
   
	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

    @Override
	protected WritablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, String>(this.getSubjectHolder(), ElementCollectionMapping2_0.SPECIFIED_TARGET_CLASS_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSpecifiedTargetClass();
				if (name == null) {
					name = TargetClassComposite.this.getDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setSpecifiedTargetClass(value);
			}
		};
    }

	@Override
	protected ListValueModel<String> buildClassListHolder() {
		return this.buildDefaultProfilerListHolder();
	}

	private ListValueModel<String> buildDefaultProfilerListHolder() {
		return new PropertyListValueModelAdapter<String>(
			this.buildDefaultProfilerHolder()
		);
	}

	private PropertyValueModel<String> buildDefaultProfilerHolder() {
		return new PropertyAspectAdapter<ElementCollectionMapping2_0, String>(this.getSubjectHolder(), ElementCollectionMapping2_0.DEFAULT_TARGET_CLASS_PROPERTY) {
			@Override
			protected String buildValue_() {
				return TargetClassComposite.this.getDefaultValue(this.subject);
			}
		};
	}

	private String getDefaultValue(ElementCollectionMapping2_0 subject) {
		String defaultValue = subject.getDefaultTargetClass();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}
}