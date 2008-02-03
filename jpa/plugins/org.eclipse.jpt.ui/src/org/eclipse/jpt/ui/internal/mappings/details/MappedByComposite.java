/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * 	 Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.INonOwningMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
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
 * @see INonOwningMapping
 * @see ManyToManyMappingComposite - A container of this pane
 * @see OneToManyMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class MappedByComposite extends AbstractFormPane<INonOwningMapping>
{
	private CCombo combo;

	/**
	 * Creates a new <code>MappedByComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	protected MappedByComposite(AbstractFormPane<? extends INonOwningMapping> parentPane,
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
	public MappedByComposite(PropertyValueModel<? extends INonOwningMapping> subjectHolder,
	                         Composite parent,
	                         IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(INonOwningMapping.MAPPED_BY_PROPERTY);
	}

	private ModifyListener buildComboModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (isPopulating()) {
					return;
				}

				String mappedBy = ((CCombo) e.getSource()).getText();
				String currentMappedBy = subject().getMappedBy();

				if (StringTools.stringIsEmpty(mappedBy)) {
					mappedBy = null;

					if (StringTools.stringIsEmpty(currentMappedBy)) {
						return;
					}
				}

				if (currentMappedBy == null || !currentMappedBy.equals(mappedBy)) {
					subject().setMappedBy(mappedBy);
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
		populateChoices();
		populateMappedByText();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		combo = buildCombo(container);
		combo.addModifyListener(buildComboModifyListener());

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.NonOwningMapping_mappedByLabel,
			combo.getParent(),
			IJpaHelpContextIds.MAPPING_MAPPED_BY
		);
	}

	private void populateChoices() {
		combo.removeAll();

		if (subject() != null) {
			for (Iterator<String> iter = subject().candidateMappedByAttributeNames(); iter.hasNext(); ) {
				combo.add(iter.next());
			}
		}
	}

	private void populateMappedByText() {
		if (subject() == null) {
			return;
		}

		if (subject() != null) {
			String mappedBy = subject().getMappedBy();

			if (mappedBy != null) {
				if (!combo.getText().equals(mappedBy)) {
					combo.setText(mappedBy);
				}
			}
			else {
				combo.setText("");
			}
		}
		else {
			combo.setText("");
		}
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == INonOwningMapping.MAPPED_BY_PROPERTY) {
			populateMappedByText();
		}
	}
}