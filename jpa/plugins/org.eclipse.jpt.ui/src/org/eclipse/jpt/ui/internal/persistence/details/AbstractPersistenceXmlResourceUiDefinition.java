/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.persistence.details;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.ui.ResourceUiDefinition;
import org.eclipse.jpt.ui.PersistenceXmlResourceUiDefinition;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaPageComposite;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractPersistenceXmlResourceUiDefinition
	implements ResourceUiDefinition, PersistenceXmlResourceUiDefinition
{
	
	
	private final PersistenceXmlUiFactory factory;
	
	
	/**
	 * zero-argument constructor
	 */
	protected AbstractPersistenceXmlResourceUiDefinition() {
		super();
		this.factory = buildPersistenceXmlUiFactory();
	}
	
	
	protected abstract PersistenceXmlUiFactory buildPersistenceXmlUiFactory();
	
	public PersistenceXmlUiFactory getFactory() {
		return this.factory;
	}
	
	public ListIterator<JpaPageComposite> buildPersistenceUnitComposites(PropertyValueModel<PersistenceUnit> subjectHolder, Composite parent, WidgetFactory widgetFactory) {
		return this.factory.createPersistenceUnitComposites(subjectHolder, parent, widgetFactory);
	}
}
