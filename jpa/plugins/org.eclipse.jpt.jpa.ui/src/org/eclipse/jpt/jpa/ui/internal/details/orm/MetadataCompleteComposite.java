/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class MetadataCompleteComposite extends Pane<OrmTypeMapping> {

	public MetadataCompleteComposite(Pane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initializeLayout(Composite container) {

		// TODO not sure the is the right thing to do; since metadata complete
		// has an "override" (from the persistence unit), not a default...
		addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsOrmMessages.MetadataCompleteComposite_metadataComplete,
			buildMetadataCompleteHolder(),
			buildMetadataCompleteStringHolder(),
			null
		);
	}
	
	private WritablePropertyValueModel<Boolean> buildMetadataCompleteHolder() {
		return new PropertyAspectAdapter<OrmTypeMapping, Boolean>(
			getSubjectHolder(),
			OrmTypeMapping.SPECIFIED_METADATA_COMPLETE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedMetadataComplete();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedMetadataComplete(value);
			}
		};
	}

	private PropertyValueModel<String> buildMetadataCompleteStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildOverrideMetadataCompleteHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsOrmMessages.MetadataCompleteComposite_metadataCompleteWithDefault, defaultStringValue);
				}
				return JptUiDetailsOrmMessages.MetadataCompleteComposite_metadataComplete;
			}
		};
	}
	private PropertyValueModel<Boolean> buildOverrideMetadataCompleteHolder() {
		return new PropertyAspectAdapter<OrmTypeMapping, Boolean>(
			getSubjectHolder(),
			OrmTypeMapping.SPECIFIED_METADATA_COMPLETE_PROPERTY,
			OrmTypeMapping.OVERRIDE_METADATA_COMPLETE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedMetadataComplete() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isOverrideMetadataComplete());
			}
		};
	}
}