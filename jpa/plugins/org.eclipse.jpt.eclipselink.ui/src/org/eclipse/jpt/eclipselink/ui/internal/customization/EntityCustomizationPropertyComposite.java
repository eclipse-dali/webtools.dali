/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.customization;

import org.eclipse.jpt.eclipselink.core.internal.context.customization.Customization;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * EntityCustomizationPropertyComposite
 */
public class EntityCustomizationPropertyComposite extends AbstractPane<EntityCustomizerProperties> {

	/**
	 * Creates a new <code>EntityCustomizationPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public EntityCustomizationPropertyComposite(AbstractPane<Customization> parentComposite,
	                                      PropertyValueModel<EntityCustomizerProperties> subjectHolder,
	                                      Composite parent) {

		super(parentComposite, subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Customizer
		new CustomizerComposite(this, container);
		
	}
}
