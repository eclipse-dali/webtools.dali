/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.context.NonOwningMapping;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            -------------------------------------------------------------- |
 * | Mapped By: |                                                          |v| |
 * |            -------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see NonOwningMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class MappedByComposite extends AbstractFormPane<NonOwningMapping>
{
	private CCombo combo;

	/**
	 * Creates a new <code>MappedByComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	public MappedByComposite(AbstractFormPane<? extends NonOwningMapping> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>MappedByComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>INonOwningMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public MappedByComposite(PropertyValueModel<? extends NonOwningMapping> subjectHolder,
	                         Composite parent,
	                         WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);

		propertyNames.add(RelationshipMapping.RESOLVED_TARGET_ENTITY_PROPERTY);

		propertyNames.add(NonOwningMapping.MAPPED_BY_PROPERTY);
	}

	private ModifyListener buildComboModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					CCombo combo = (CCombo) e.widget;
					valueChanged(combo.getText());
				}
			}
		};
	}

	private FocusListener buildFocusListener() {
		return new FocusListener() {

			public void focusGained(FocusEvent e) {

				setPopulating(true);

				try {
					if (combo.getSelectionIndex() == 0) {
						combo.setText("");
					}
				}
				finally {
					setPopulating(false);
				}
			}

			public void focusLost(FocusEvent e) {

				setPopulating(true);

				try {
					if (combo.getText().length() == 0) {
						combo.select(0);
					}
				}
				finally {
					setPopulating(false);
				}
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {

		super.doPopulate();
		populateCombo();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		combo = buildLabeledEditableCCombo(
			container,
			JptUiMappingsMessages.NonOwningMapping_mappedByLabel,
			buildComboModifyListener(),
			JpaHelpContextIds.MAPPING_MAPPED_BY
		);

		combo.addFocusListener(buildFocusListener());
	}

	private void populateCombo() {

		combo.removeAll();
		combo.add(JptUiMappingsMessages.NoneSelected);

		NonOwningMapping subject = subject();

		if (subject != null) {
			Iterator<String> iter = subject.candidateMappedByAttributeNames();

			for (iter = CollectionTools.sort(iter); iter.hasNext(); ) {
				combo.add(iter.next());
			}
		}

		updateSelectedItem();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == NonOwningMapping.MAPPED_BY_PROPERTY ||
		    propertyName == RelationshipMapping.RESOLVED_TARGET_ENTITY_PROPERTY) {

			populateCombo();
		}
	}

	/**
	 * Updates the selected item by selected the current value, if not
	 * <code>null</code>, or select the default value if one is available,
	 * otherwise remove the selection.
	 * <p>
	 * <b>Note:</b> It seems the text can be shown as truncated, changing the
	 * selection to (0, 0) makes the entire text visible.
	 */
	private void updateSelectedItem() {

		NonOwningMapping subject = subject();
		String value = (subject != null) ? subject.getMappedBy() : null;

		if (value != null) {
			combo.setText(value);
		}
		else {
			combo.select(0);
		}

		combo.setSelection(new Point(0, 0));
	}

	private void valueChanged(String value) {

		NonOwningMapping subject = subject();
		String oldValue = (subject != null) ? subject.getMappedBy() : null;

		// Check for null value
		if (StringTools.stringIsEmpty(value)) {
			value = null;

			if (StringTools.stringIsEmpty(oldValue)) {
				return;
			}
		}

		// The default value
		if (value == JptUiMappingsMessages.NoneSelected) {
			value = null;
		}

		// Nothing to change
		if ((oldValue == value) && value == null) {
			return;
		}

		// Set the new value
		if ((value != null) && (oldValue == null) ||
		   ((oldValue != null) && !oldValue.equals(value))) {

			setPopulating(true);

			try {
				subject.setMappedBy(value);
			}
			finally {
				setPopulating(false);
			}
		}
	}
}