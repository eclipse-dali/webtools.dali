/*******************************************************************************
* Copyright (c) 2006, 2012 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserComboPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

/**
 *  target entity hyperlink label, combo and browse button 
 */
public class TargetEntityClassChooser
	extends ClassChooserComboPane<RelationshipMapping>
{
	/**
	 * Creates a new <code>TargetEntityComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public TargetEntityClassChooser(
			Pane<? extends RelationshipMapping> parentPane,
	        Composite parent,
	        Hyperlink hyperlink) {
		
		super(parentPane, parent, hyperlink);
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
    protected String getFullyQualifiedClassName() {
    	return getSubject().getFullyQualifiedTargetEntity();
    }

    @Override
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<RelationshipMapping, String>(
			this.getSubjectHolder(),
			RelationshipMapping.SPECIFIED_TARGET_ENTITY_PROPERTY,
			RelationshipMapping.DEFAULT_TARGET_ENTITY_PROPERTY) {
			@Override
			protected String buildValue_() {

				String name = this.subject.getSpecifiedTargetEntity();
				if (name == null) {
					name = TargetEntityClassChooser.this.getDefaultValue(this.subject);
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
				return TargetEntityClassChooser.this.getDefaultValue(this.subject);
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