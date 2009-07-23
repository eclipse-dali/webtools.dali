/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import org.eclipse.jpt.core.context.VersionMapping;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConvert;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkVersionMappingComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

//Temporary to remove the Converters section from orm basic mapping.
//This is supported in EclipseLink in version 1.1, but not 1.0
public class EclipseLinkOrmVersionMappingComposite extends EclipseLinkVersionMappingComposite implements JpaComposite
{
	/**
	 * Creates a new <code>EclipseLinkOrmVersionMappingComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>VersionMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOrmVersionMappingComposite(PropertyValueModel<? extends VersionMapping> subjectHolder,
	                               Composite parent,
	                               WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}


	@Override
	//everything but the 'Defaine Converter' section.  This is not supported in eclipselink 1.0, but is in 1.1
	protected Pane<EclipseLinkConvert> buildConvertComposite(PropertyValueModel<EclipseLinkConvert> convertHolder, Composite container) {
		return new OrmConvertComposite(convertHolder, container, getWidgetFactory());
	}
}