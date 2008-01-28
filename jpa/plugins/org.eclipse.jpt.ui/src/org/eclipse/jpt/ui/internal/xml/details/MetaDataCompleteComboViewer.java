/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import java.util.Collection;
import org.eclipse.jpt.core.internal.context.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.EnumComboViewer;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * </pre>
 *
 * @see XmlTypeMapping
 * @see XmlPersistentTypeDetailsPage - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class MetaDataCompleteComboViewer extends AbstractFormPane<XmlTypeMapping<? extends TypeMapping>>
{
	/**
	 * Creates a new <code>MetaDataCompleteComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public MetaDataCompleteComboViewer(AbstractFormPane<?> parentPane,
	                                   PropertyValueModel<? extends XmlTypeMapping<? extends TypeMapping>> subjectHolder,
	                                   Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>MetaDataCompleteComboViewer</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public MetaDataCompleteComboViewer(PropertyValueModel<? extends XmlTypeMapping<? extends TypeMapping>> subjectHolder,
	                                   Composite parent,
	                                   TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private EnumComboViewer<XmlTypeMapping<? extends TypeMapping>, Boolean> buildEnumTypeCombo(Composite container) {

		return new EnumComboViewer<XmlTypeMapping<? extends TypeMapping>, Boolean>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(XmlTypeMapping.DEFAULT_METADATA_COMPLETE_PROPERTY);
				propertyNames.add(XmlTypeMapping.SPECIFIED_METADATA_COMPLETE_PROPERTY);
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
					MetaDataCompleteComboViewer.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedMetadataComplete();
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedMetadataComplete(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {
		buildEnumTypeCombo(container);
	}
}