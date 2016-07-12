/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.BooleanStringTransformer;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
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

	private ModifiablePropertyValueModel<Boolean> buildInsertableModel() {
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

	private PropertyValueModel<String> buildInsertableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultInsertableModel(), INSERTABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> INSERTABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_INSERTABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_INSERTABLE
		);

	private PropertyValueModel<Boolean> buildDefaultInsertableModel() {
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

	private ModifiablePropertyValueModel<Boolean> buildNullableModel() {
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

	private PropertyValueModel<String> buildNullableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultNullableModel(), NULLABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> NULLABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_NULLABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_NULLABLE
		);

	private PropertyValueModel<Boolean> buildDefaultNullableModel() {
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

	private ModifiablePropertyValueModel<Boolean> buildUniqueModel() {
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

	private PropertyValueModel<String> buildUniqueStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultUniqueModel(), UNIQUE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> UNIQUE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UNIQUE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UNIQUE
		);

	private PropertyValueModel<Boolean> buildDefaultUniqueModel() {
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

	private ModifiablePropertyValueModel<Boolean> buildUpdatableModel() {
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

	private PropertyValueModel<String> buildUpdatableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultUpdatableModel(), UPDATABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> UPDATABLE_TRANSFORMER = new BooleanStringTransformer(
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE
		);

	private PropertyValueModel<Boolean> buildDefaultUpdatableModel() {
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
			buildInsertableModel(),
			buildInsertableStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.JOIN_COLUMN_DIALOG_PANE_UPDATABLE,
			buildUpdatableModel(),
			buildUpdatableStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		// Unique tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UNIQUE,
			buildUniqueModel(),
			buildUniqueStringModel(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		// Nullable tri-state check box
		addTriStateCheckBoxWithDefault(
			container,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE,
			buildNullableModel(),
			buildNullableStringModel(),
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