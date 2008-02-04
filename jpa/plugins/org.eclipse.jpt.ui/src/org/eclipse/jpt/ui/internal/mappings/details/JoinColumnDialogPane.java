/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.EnumComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ------------------------------------------------------------------ |
 * | Table: | TableCombo                                                   |v| |
 * |        ------------------------------------------------------------------ |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EnumComboViewer                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | EnumComboViewer                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see JoinColumnStateObject
 * @see EnumComboViewer
 * @see JoinColumnDialog - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinColumnDialogPane extends AbstractJoinColumnDialogPane<JoinColumnStateObject>
{
	private CCombo tableCombo;

	/**
	 * Creates a new <code>JoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinColumnDialogPane(PropertyValueModel<? extends JoinColumnStateObject> subjectHolder,
	                            Composite parent)
	{
		super(subjectHolder, parent);
	}

	private EnumComboViewer<JoinColumnStateObject, Boolean> buildInsertableCombo(Composite container) {

		return new EnumComboViewer<JoinColumnStateObject, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinColumnStateObject.INSERTABLE_PROPERTY);
			}

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return null;
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					JoinColumnDialogPane.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getInsertable();
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setInsertable(value);
			}
		};
	}

	private SelectionListener buildTableComboSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				populateNameCombo();
			}
		};
	}

	private EnumComboViewer<JoinColumnStateObject, Boolean> buildUpdatableCombo(Composite container) {

		return new EnumComboViewer<JoinColumnStateObject, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(JoinColumnStateObject.UPDATABLE_PROPERTY);
			}

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return true;
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					JoinColumnDialogPane.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getUpdatable();
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setUpdatable(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		super.initializeLayout(container);

		// Join Referenced Column widgets
		tableCombo = buildEditableCombo(container);
		tableCombo.addSelectionListener(buildTableComboSelectionListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_table,
			tableCombo,
			IJpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);

		// Insertable widgets
		EnumComboViewer<JoinColumnStateObject, Boolean> insertableCombo =
			buildInsertableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_insertable,
			insertableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable widgets
		EnumComboViewer<JoinColumnStateObject, Boolean> updatableCombo =
			buildUpdatableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_updatable,
			updatableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
	}
}
