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

import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | BaseJoinColumnDialogPane                                              | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | x Insertable                                                              |
 * |                                                                           |
 * | x Nullable                                                                |
 * |                                                                           |
 * | x Unique                                                                  |
 * |                                                                           |
 * | x Updatable                                                               |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnStateObject
 * @see JoinColumnDialog - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinColumnDialogPane<T extends JoinColumnStateObject> extends BaseJoinColumnDialogPane<T>
{
	/**
	 * Creates a new <code>JoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinColumnDialogPane(PropertyValueModel<? extends T> subjectHolder,
	                            Composite parent)
	{
		super(subjectHolder, parent);
	}

	private WritablePropertyValueModel<Boolean> buildInsertableHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.JoinColumnDialogPane_insertableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.JoinColumnDialogPane_insertable;
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

	private WritablePropertyValueModel<Boolean> buildNullableHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.JoinColumnDialogPane_nullableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.JoinColumnDialogPane_nullable;
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

	private WritablePropertyValueModel<Boolean> buildUniqueHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.JoinColumnDialogPane_uniqueWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.JoinColumnDialogPane_unique;
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

	private WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
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
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.JoinColumnDialogPane_updatableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.JoinColumnDialogPane_updatable;
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
			addSubPane(container, 4),
			JptUiDetailsMessages.JoinColumnDialogPane_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsMessages.JoinColumnDialogPane_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		// Unique tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsMessages.ColumnComposite_unique,
			buildUniqueHolder(),
			buildUniqueStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		// Nullable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptUiDetailsMessages.ColumnComposite_nullable,
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