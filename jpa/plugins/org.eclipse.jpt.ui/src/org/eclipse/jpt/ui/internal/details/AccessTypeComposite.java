/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;

import org.eclipse.jpt.common.ui.internal.widgets.EnumFormComboViewer;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.core.context.AccessHolder;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.ui.internal.JptUiMessages;
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
 * @see AccessHolder
 * @see OrmEntityComposite - A container of this pane
 * @see OrmEmbeddableComposite - A container of this pane
 * @see OrmMappedSuperclassComposite - A container of this pane
 *
 * @version 2.2
 * @since 1.0
 */
public class AccessTypeComposite extends Pane<AccessHolder> {

	/**
	 * Creates a new <code>AccessTypeComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public AccessTypeComposite(Pane<?> parentPane,
	                           PropertyValueModel<? extends AccessHolder> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}
	
	public AccessTypeComposite(Pane<?> parentPane,
        PropertyValueModel<? extends AccessHolder> subjectHolder,
        Composite parent,
        boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	@Override
	protected void initializeLayout(Composite container) {

		EnumFormComboViewer<AccessHolder, AccessType> comboViewer =
			addAccessTypeComboViewer(container);

		addLabeledComposite(
			container,
			JptUiMessages.AccessTypeComposite_access,
			comboViewer.getControl()
		);
	}
	
	private EnumFormComboViewer<AccessHolder, AccessType> addAccessTypeComboViewer(Composite container) {

		return new EnumFormComboViewer<AccessHolder, AccessType>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(AccessHolder.DEFAULT_ACCESS_PROPERTY);
				propertyNames.add(AccessHolder.SPECIFIED_ACCESS_PROPERTY);
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
					JptUiMessages.class,
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
}