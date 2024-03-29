/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkAccessType;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class EclipseLinkOrmAttributeTypeClassChooser
	extends ClassChooserPane<AttributeMapping>
{
	public EclipseLinkOrmAttributeTypeClassChooser(Pane<?> parentPane,
	                               PropertyValueModel<? extends AttributeMapping> subjectHolder,
	                               Composite parent,
	                               Hyperlink hyperlink) {

		this(parentPane, subjectHolder, buildVirtualAttributeModel(subjectHolder), parent, hyperlink);
	}

	public EclipseLinkOrmAttributeTypeClassChooser(Pane<?> parentPane,
	        PropertyValueModel<? extends AttributeMapping> subjectHolder,
	        PropertyValueModel<Boolean> enabledModel,
	        Composite parent,
	        Hyperlink hyperlink) {

		super(parentPane, subjectHolder, enabledModel, parent, hyperlink);
	}

	@Override
	protected JavaTypeCompletionProcessor buildJavaTypeCompletionProcessor() {
		return new JavaTypeCompletionProcessor(true, true);
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

	private static PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel(PropertyValueModel<? extends AttributeMapping> mappingHolder) {
		return new PropertyAspectAdapter<AttributeMapping, SpecifiedAccessReference>(mappingHolder) {
			@Override
			protected SpecifiedAccessReference buildValue_() {
				return this.subject.getPersistentAttribute();
			}
		};
	}

	private static PropertyValueModel<Boolean> buildVirtualAttributeModel(PropertyValueModel<? extends AttributeMapping> mappingHolder) {
		return new PropertyAspectAdapter<SpecifiedAccessReference, Boolean>(
			buildAccessReferenceModel(mappingHolder),
			SpecifiedAccessReference.SPECIFIED_ACCESS_PROPERTY,
			SpecifiedAccessReference.DEFAULT_ACCESS_PROPERTY) {
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
