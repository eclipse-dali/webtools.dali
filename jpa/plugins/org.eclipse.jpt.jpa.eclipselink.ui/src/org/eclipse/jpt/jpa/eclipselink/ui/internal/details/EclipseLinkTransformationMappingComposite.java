/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkTransformationMappingComposite extends Pane<EclipseLinkTransformationMapping>
                                       implements JpaComposite
{
	/**
	 * Creates a new <code>EclipseLinkBasicCollectionMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IManyToOneMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkTransformationMappingComposite(PropertyValueModel<? extends EclipseLinkTransformationMapping> subjectHolder,
	                                 Composite parent,
	                                 WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite container) {

	}
}
