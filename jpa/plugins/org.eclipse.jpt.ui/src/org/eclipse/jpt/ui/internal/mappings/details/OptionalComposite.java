/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IBasicMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??
 * �??        �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?��??�?? �??
 * �?? Fetch: �??                                                              �??�?��?? �??
 * �??        �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�?��??�?? �??
 * �??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??�??</pre>
 *
 * @see IBasicMapping
 * @see BasicComposite - A container of this pane
 *
 * @version 1.0
 * @since 2.0
 */
public class OptionalComposite extends BaseJpaComposite<IBasicMapping>
{
	private EnumComboViewer<IBasicMapping, Boolean> optionalCombo;

	/**
	 * Creates a new <code>OptionalComposite</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 */
	public OptionalComposite(BaseJpaComposite<IBasicMapping> parentComposite,
	                         Composite parent)
	{
		super(parentComposite, parent);
	}

	private EnumComboViewer<IBasicMapping, Boolean> buildOptionalCombo(Composite container) {

		return new EnumComboViewer<IBasicMapping, Boolean>(getSubjectHolder(), container, getWidgetFactory()) {
			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultOptional();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					OptionalComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedOptional();
			}

			@Override
			protected String propertyName() {
				return IBasicMapping.SPECIFIED_OPTIONAL_PROPERTY;
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedOptional(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();
		optionalCombo.disengageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		optionalCombo.populate();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();
		optionalCombo.engageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		optionalCombo = buildOptionalCombo(container);

		buildLabeledComposite(
			JptUiMappingsMessages.BasicGeneralSection_optionalLabel,
			container,
			optionalCombo.getControl(),
			IJpaHelpContextIds.MAPPING_OPTIONAL
		);
	}
}
