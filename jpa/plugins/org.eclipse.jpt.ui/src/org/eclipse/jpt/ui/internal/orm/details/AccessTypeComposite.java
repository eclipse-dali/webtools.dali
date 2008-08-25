/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |              ------------------------------------------------------------ |
 * | Access Type: |                                                        |v| |
 * |              ------------------------------------------------------------ |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see XmlTypeMapping
 * @see OrmPersistentTypeDetailsPage - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class AccessTypeComposite extends FormPane<OrmTypeMapping> {

	/**
	 * Creates a new <code>AccessTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public AccessTypeComposite(FormPane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private EnumFormComboViewer<OrmTypeMapping, AccessType> addAccessTypeComboViewer(Composite container) {

		return new EnumFormComboViewer<OrmTypeMapping, AccessType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(OrmTypeMapping.DEFAULT_ACCESS_PROPERTY);
				propertyNames.add(OrmTypeMapping.SPECIFIED_ACCESS_PROPERTY);
			}

			@Override
			protected AccessType[] getChoices() {
				return AccessType.values();
			}

			@Override
			protected AccessType getDefaultValue() {
				return getSubject().getDefaultAccess();
			}

			@Override
			protected String displayString(AccessType value) {
				return buildDisplayString(
					JptUiOrmMessages.class,
					AccessTypeComposite.this,
					value
				);
			}

			@Override
			protected AccessType getValue() {
				return getSubject().getSpecifiedAccess();
			}

			@Override
			protected void setValue(AccessType value) {
				getSubject().setSpecifiedAccess(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		EnumFormComboViewer<OrmTypeMapping, AccessType> comboViewer =
			addAccessTypeComboViewer(container);

		addLabeledComposite(
			container,
			JptUiOrmMessages.AccessTypeComposite_access,
			comboViewer.getControl()
		);
	}
}