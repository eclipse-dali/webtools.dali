/*******************************************************************************
* Copyright (c) 2006, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 *  target entity hyperlink label, combo and browse button 
 */
public class TargetEntityComposite
	extends ClassChooserComboPane<RelationshipMapping>
{
	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetEntityComposite(
			Pane<? extends RelationshipMapping> parentPane,
	        Composite parent) {
		
		super(parentPane, parent);
	}
	
	
	@Override
	protected String getClassName() {
		return getSubject().getTargetEntity();
	}

	@Override
	protected void setClassName(String className) {
		this.getSubject().setSpecifiedTargetEntity(className);
	}

    @Override
    protected String getLabelText() {
    	return JptUiDetailsMessages.TargetEntityChooser_label;
    }
   
    @Override
    protected String getHelpId() {
    	return JpaHelpContextIds.MAPPING_TARGET_ENTITY;
    }
   
	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

    @Override
    protected char getEnclosingTypeSeparator() {
    	return getSubject().getTargetEntityEnclosingTypeSeparator();
    }
    
    @Override
	protected WritablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<RelationshipMapping, String>(this.getSubjectHolder(), RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSpecifiedTargetEntity();
				if (name == null) {
					name = TargetEntityComposite.this.getDefaultValue(this.subject);
				}
				return name;
			}

			@Override
			protected void setValue_(String value) {

				if (getDefaultValue(this.subject).equals(value)) {
					value = null;
				}
				this.subject.setSpecifiedTargetEntity(value);
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
		return new PropertyAspectAdapter<RelationshipMapping, String>(this.getSubjectHolder(), RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {
				return TargetEntityComposite.this.getDefaultValue(this.subject);
			}
		};
	}

	private String getDefaultValue(RelationshipMapping subject) {
		String defaultValue = subject.getDefaultTargetEntity();

		if (defaultValue != null) {
			return NLS.bind(
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
		}
		return JptCommonUiMessages.DefaultEmpty;
	}
}