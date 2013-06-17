/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class JoinColumnDialogPane<T extends JoinColumnStateObject>
	extends BaseJoinColumnDialogPane<T>
{
	public JoinColumnDialogPane(
			PropertyValueModel<? extends T> subjectModel,
			Composite parentComposite,
			ResourceManager resourceManager)
	{
		super(subjectModel, parentComposite, resourceManager);
	}

	private ModifiablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), JoinColumnStateObject.INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setInsertable(value);
			}
		};
	}

	private PropertyValueModel<String> buildInsertableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultInsertableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_INSERTABLE_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_INSERTABLE;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultInsertableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.INSERTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getInsertable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultInsertable());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getNullable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setNullable(value);
			}
		};
	}

	private PropertyValueModel<String> buildNullableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNullableHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_NULLABLE_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_NULLABLE;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultNullableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getNullable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultNullable());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getUnique();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setUnique(value);
			}
		};
	}

	private PropertyValueModel<String> buildUniqueStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUniqueHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UNIQUE_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UNIQUE;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultUniqueHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getUnique() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUnique());
			}
		};
	}

	private ModifiablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(getSubjectHolder(), JoinColumnStateObject.UPDATABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setUpdatable(value);
			}
		};
	}

	private PropertyValueModel<String> buildUpdatableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUpdatableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE_WITH_DEFAULT, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE;
			}
		};
	}

	private PropertyValueModel<Boolean> buildDefaultUpdatableHolder() {
		return new PropertyAspectAdapter<T, Boolean>(
			getSubjectHolder(),
			JoinColumnStateObject.UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getUpdatable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUpdatable());
			}
		};
	}

	@Override
	protected void initializeLayout(Composite container) {

		super.initializeLayout(container);

		// Insertable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_INSERTABLE,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		// Unique tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UNIQUE,
			buildUniqueHolder(),
			buildUniqueStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		// Nullable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE,
			buildNullableHolder(),
			buildNullableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected boolean isTableEditable() {
		return true;
	}
}