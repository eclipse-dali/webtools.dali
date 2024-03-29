/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.jpa.ui.details.orm.JptJpaUiDetailsOrmMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class MetadataCompleteTriStateCheckBox extends Pane<OrmTypeMapping> {

	private TriStateCheckBox checkBox;

	public MetadataCompleteTriStateCheckBox(Pane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Control getControl() {
		return this.checkBox.getCheckBox();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// TODO not sure the is the right thing to do; since metadata complete
		// has an "override" (from the persistence unit), not a default...
		this.checkBox = addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsOrmMessages.METADATA_COMPLETE_COMPOSITE_METADATA_COMPLETE,
			buildSpecifiedMetadataCompleteHolder(),
			buildMetadataCompleteStringHolder(),
			null
		);
	}

	private ModifiablePropertyValueModel<Boolean> buildSpecifiedMetadataCompleteHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsOrmMessages.METADATA_COMPLETE_COMPOSITE_METADATA_COMPLETE_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsOrmMessages.METADATA_COMPLETE_COMPOSITE_METADATA_COMPLETE;
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