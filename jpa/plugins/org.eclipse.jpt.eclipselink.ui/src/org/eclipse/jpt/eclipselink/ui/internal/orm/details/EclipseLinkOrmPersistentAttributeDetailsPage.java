/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.eclipselink.ui.internal.platform.EclipseLinkJpaPlatformUi;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.internal.orm.details.OrmPersistentAttributeDetailsPage;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkOrmPersistentAttributeDetailsPage
	extends OrmPersistentAttributeDetailsPage
{
	/**
	 * Creates a new <code>EclipseLinkOrmPersistentAttributeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public EclipseLinkOrmPersistentAttributeDetailsPage(
			Composite parent,
			WidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}
	
	
	@Override
	protected EclipseLinkJpaPlatformUi jpaPlatformUi() {
		return (EclipseLinkJpaPlatformUi) super.jpaPlatformUi();
	}
	
	@Override
	public Iterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		return jpaPlatformUi().eclipseLinkOrmAttributeMappingUiProviders();
	}
	
	@Override
	protected Pane buildMapAsPane(Composite parent) {
		return new EclipseLinkOrmPersistentAttributeMapAsComposite(this, parent);
	}
}
