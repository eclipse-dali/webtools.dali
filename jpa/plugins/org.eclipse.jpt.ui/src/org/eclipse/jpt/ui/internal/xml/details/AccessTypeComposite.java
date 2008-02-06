/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
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
 * @see XmlPersistentTypeDetailsPage - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class AccessTypeComposite extends AbstractFormPane<XmlTypeMapping<? extends TypeMapping>> {

	/**
	 * Creates a new <code>AccessTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public AccessTypeComposite(AbstractFormPane<?> parentPane,
	                           PropertyValueModel<? extends XmlTypeMapping<? extends TypeMapping>> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	private EnumFormComboViewer<XmlTypeMapping<? extends TypeMapping>, AccessType> buildAccessTypeComboViewer(Composite container) {

		return new EnumFormComboViewer<XmlTypeMapping<? extends TypeMapping>, AccessType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(XmlTypeMapping.DEFAULT_ACCESS_PROPERTY);
				propertyNames.add(XmlTypeMapping.SPECIFIED_ACCESS_PROPERTY);
			}

			@Override
			protected AccessType[] choices() {
				return AccessType.values();
			}

			@Override
			protected AccessType defaultValue() {
				return subject().getDefaultAccess();
			}

			@Override
			protected String displayString(AccessType value) {
				return buildDisplayString(
					JptUiXmlMessages.class,
					AccessTypeComposite.this,
					value
				);
			}

			@Override
			protected AccessType getValue() {
				return subject().getSpecifiedAccess();
			}

			@Override
			protected void setValue(AccessType value) {
				subject().setSpecifiedAccess(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		EnumFormComboViewer<XmlTypeMapping<? extends TypeMapping>, AccessType> comboViewer =
			buildAccessTypeComboViewer(container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.PersistentTypePage_AccessLabel,
			comboViewer.getControl()
		);
	}
}