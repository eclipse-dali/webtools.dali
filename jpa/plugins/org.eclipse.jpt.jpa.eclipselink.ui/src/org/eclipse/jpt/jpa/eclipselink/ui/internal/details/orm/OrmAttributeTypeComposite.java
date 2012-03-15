/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jpt.common.ui.internal.widgets.ClassChooserPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessHolder;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;

public class OrmAttributeTypeComposite
	extends ClassChooserPane<AttributeMapping>
{
	public OrmAttributeTypeComposite(Pane<?> parentPane,
	                               PropertyValueModel<? extends AttributeMapping> subjectHolder,
	                               Composite parent) {

		this(parentPane, subjectHolder, parent, buildVirtualAttributeModel(subjectHolder));
	}

	public OrmAttributeTypeComposite(Pane<?> parentPane,
	        PropertyValueModel<? extends AttributeMapping> subjectHolder,
	        Composite parent,
	        PropertyValueModel<Boolean> enabledModel) {

		super(parentPane, subjectHolder, parent, enabledModel);
	}

	@Override
	public void enableWidgets(boolean enabled) {
		// TODO Auto-generated method stub
		super.enableWidgets(enabled);
	}
	@Override
	protected JavaTypeCompletionProcessor buildJavaTypeCompletionProcessor() {
		return new JavaTypeCompletionProcessor(true, true);
	}

	@Override
	protected String getLabelText() {
		return EclipseLinkUiDetailsMessages.OrmAttributeTypeComposite_attributeType;
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextHolder() {
		return new PropertyAspectAdapter<AttributeMapping, String>(
			getSubjectHolder(), 
			OrmAttributeMapping.DEFAULT_ATTRIBUTE_TYPE_PROPERTY,
			OrmAttributeMapping.SPECIFIED_ATTRIBUTE_TYPE_PROPERTY) {
			@Override
			protected String buildValue_() {
				if (this.subject.getPersistentAttribute().isVirtual()) {
					return this.subject.getPersistentAttribute().getTypeName();
				}
				//get the attributeType instead of the specifiedAttributeType.
				//this widget will only be enabled if the mapping is VIRTUAL access 
				//so we don't need to display the default differently
				return ((OrmAttributeMapping) this.subject).getAttributeType();
			}

			@Override
			protected void setValue_(String value) {
				if (this.subject.getPersistentAttribute().isVirtual()) {
					return;
				}
				if (value.length() == 0) {
					value = null;
				}
				// we can safely cast to an orm.xml mapping since it is not virtual
				((OrmAttributeMapping) this.subject).setSpecifiedAttributeType(value);
			}
		};
	}

	@Override
	protected String getClassName() {
		return ((OrmAttributeMapping) getSubject()).getAttributeType();
	}

	@Override
	protected void setClassName(String className) {
		((OrmAttributeMapping) getSubject()).setSpecifiedAttributeType(className);
	}

	@Override
	protected IJavaProject getJavaProject() {
		return getSubject().getJpaProject().getJavaProject();
	}

	private static PropertyValueModel<AccessHolder> buildAccessHolderHolder(PropertyValueModel<? extends AttributeMapping> mappingHolder) {
		return new PropertyAspectAdapter<AttributeMapping, AccessHolder>(mappingHolder) {
			@Override
			protected AccessHolder buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

	private static PropertyValueModel<Boolean> buildVirtualAttributeModel(PropertyValueModel<? extends AttributeMapping> mappingHolder) {
		return new PropertyAspectAdapter<AccessHolder, Boolean>(
			buildAccessHolderHolder(mappingHolder),
			AccessHolder.SPECIFIED_ACCESS_PROPERTY,
			AccessHolder.DEFAULT_ACCESS_PROPERTY) {
			@Override
				protected Boolean buildValue() {
					if (this.subject == null) {
						return Boolean.FALSE;
					}
					return this.buildValue_();
				}
				@Override
				protected Boolean buildValue_() {
					return Boolean.valueOf(this.subject.getAccess() == EclipseLinkAccessType.VIRTUAL);
			}
		};
	}

}