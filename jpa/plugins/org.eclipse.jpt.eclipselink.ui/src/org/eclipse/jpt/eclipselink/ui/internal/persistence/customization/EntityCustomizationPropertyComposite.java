/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.persistence.customization;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.eclipselink.core.context.persistence.customization.Customization;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.customization.Entity;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * EntityCustomizationPropertyComposite
 */
public class EntityCustomizationPropertyComposite extends Pane<Entity> {

	/**
	 * Creates a new <code>EntityCustomizationPropertyComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public EntityCustomizationPropertyComposite(Pane<Customization> parentComposite,
	                                      PropertyValueModel<Entity> subjectHolder,
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
