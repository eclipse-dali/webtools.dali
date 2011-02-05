/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.swt.widgets.Composite;

/**
 * The pane used for an ORM entity.
 *
 * @see OrmEntity
 * @see BaseJavaUiFactory - The factory creating this pane
 * @see OrmSecondaryTablesComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class OrmEntityComposite extends AbstractOrmEntityComposite
{
	/**
	 * Creates a new <code>OrmEntityComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>OrmEntity</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmEntityComposite(PropertyValueModel<? extends OrmEntity> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}
	
}