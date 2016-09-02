/*******************************************************************************
 * Copyright (c) 2012, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AccessReference;
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
	@SuppressWarnings("restriction")
	protected JavaTypeCompletionProcessor buildJavaTypeCompletionProcessor() {
		return new JavaTypeCompletionProcessor(true, true);
	}

	@Override
	protected ModifiablePropertyValueModel<String> buildTextModel() {
		// return 'attributeType' instead of 'specifiedAttributeType'
		// as this widget is enabled only if the mapping is VIRTUAL access;
		// so we don't need to display the default differently
		return PropertyValueModelTools.modifiableSubjectModelAspectAdapter(
				this.getSubjectHolder(),
				OrmAttributeMapping.ATTRIBUTE_TYPE_PROPERTY,
				m -> m.getPersistentAttribute().isVirtual() ? m.getPersistentAttribute().getTypeName() : ((OrmAttributeMapping) m).getAttributeType(),
				(m, value) -> {
					if (m.getPersistentAttribute().isSpecified()) {
						// we can safely cast to an orm.xml mapping since it is not virtual
						((OrmAttributeMapping) m).setSpecifiedAttributeType(StringTools.isBlank(value) ? null : value);
					}
				}
			);
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

	private static PropertyValueModel<SpecifiedAccessReference> buildAccessReferenceModel(PropertyValueModel<? extends AttributeMapping> mappingModel) {
		return PropertyValueModelTools.transform(mappingModel, m -> m.getPersistentAttribute());
	}

	private static PropertyValueModel<Boolean> buildVirtualAttributeModel(PropertyValueModel<? extends AttributeMapping> mappingModel) {
		return PropertyValueModelTools.subjectModelAspectAdapter_(
				buildAccessReferenceModel(mappingModel),
				AccessReference.ACCESS_PROPERTY,
				m -> Boolean.valueOf((m != null) && (m.getAccess() == EclipseLinkAccessType.VIRTUAL))
			);
	}
}
