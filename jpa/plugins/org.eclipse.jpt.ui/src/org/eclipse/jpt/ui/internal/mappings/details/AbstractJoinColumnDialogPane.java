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
import java.util.Iterator;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractDialogPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                         ------------------------------------------------- |
 * | Name:                   |                                             |v| |
 * |                         ------------------------------------------------- |
 * |                         ------------------------------------------------- |
 * | Referenced Column Name: |                                             |v| |
 * |                         ------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see AbstractJoinColumnStateObject
 * @see JoinColumnInJoinTableDialog - A container of this pane
 * @see PrimaryKeyJoinColumnInSecondaryTableDialog - A container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class AbstractJoinColumnDialogPane<T extends AbstractJoinColumnStateObject> extends AbstractDialogPane<T>
{
	private CCombo nameCombo;
	private CCombo referencedColumnNameCombo;

	/**
	 * Creates a new <code>AbstractJoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public AbstractJoinColumnDialogPane(PropertyValueModel<? extends T> subjectHolder,
	                                    Composite parent)
	{
		super(subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(AbstractJoinColumnStateObject.SELECTED_REFERENCED_COLUMN_NAME_PROPERTY);
		propertyNames.add(AbstractJoinColumnStateObject.SELECTED_NAME_PROPERTY);
	}

	private ModifyListener buildNameComboListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CCombo combo = (CCombo) e.widget;
				subject().setSelectedName(combo.getText());
				subject().setDefaultNameSelected(combo.getSelectionIndex() == 0);
			}
		};
	}

	private ModifyListener buildReferencedColumnNameComboListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				CCombo combo = (CCombo) e.widget;
				subject().setSelectedReferencedColumnName(combo.getText());
				subject().setDefaultReferencedColumnNameSelected(combo.getSelectionIndex() == 0);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		populateNameCombo();
	}

	public final CCombo getNameCombo() {
		return nameCombo;
	}

	public final CCombo getReferencedColumnNameCombo() {
		return referencedColumnNameCombo;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Name widgets
		nameCombo = buildCombo(container);
		nameCombo.addModifyListener(buildNameComboListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_name,
			nameCombo,
			IJpaHelpContextIds.MAPPING_JOIN_COLUMN_NAME
		);

		// Referenced Column Name widgets
		referencedColumnNameCombo = buildCombo(container);
		referencedColumnNameCombo.addModifyListener(buildReferencedColumnNameComboListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.JoinColumnDialog_referencedColumnName,
			referencedColumnNameCombo,
			IJpaHelpContextIds.MAPPING_JOIN_REFERENCED_COLUMN
		);
	}

	public void populateNameCombo() {

		AbstractJoinColumnStateObject subject = subject();
		this.nameCombo.removeAll();

		if (subject.getDefaultName() != null) {
			this.nameCombo.add(NLS.bind(JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam, subject.getDefaultName()));
		}

		Table table = subject.getNameTable();

		if (table != null) {
			for (Iterator<String> iter = table.columnNames(); iter.hasNext(); ) {
				this.nameCombo.add(iter.next());
			}
		}

		if (subject.getJoinColumn() != null) {
			if (subject.getSpecifiedName() != null) {
				this.nameCombo.setText(subject.getSpecifiedName());
			}
			else {
				this.nameCombo.select(0);
			}
		}
	}

	public void populateReferencedNameCombo() {

		AbstractJoinColumnStateObject subject = subject();

		if (subject.getDefaultReferencedColumnName() != null) {
			this.referencedColumnNameCombo.add(NLS.bind(JptUiMappingsMessages.JoinColumnDialog_defaultWithOneParam, subject.getDefaultReferencedColumnName()));
		}

		Table referencedNameTable = subject.getReferencedNameTable();

		if (referencedNameTable != null) {
			for (Iterator<String> iter = referencedNameTable.columnNames(); iter.hasNext(); ) {
				this.referencedColumnNameCombo.add(iter.next());
			}
		}

		if (subject.getJoinColumn() != null) {
			if (subject.getSpecifiedReferencedColumnName() != null) {
				this.referencedColumnNameCombo.setText(subject().getSpecifiedReferencedColumnName());
			}
			else {
				this.referencedColumnNameCombo.select(0);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == AbstractJoinColumnStateObject.SELECTED_NAME_PROPERTY) {
			populateNameCombo();
		}
		else if (propertyName == AbstractJoinColumnStateObject.SELECTED_REFERENCED_COLUMN_NAME_PROPERTY) {
			populateReferencedNameCombo();
		}
	}
}
