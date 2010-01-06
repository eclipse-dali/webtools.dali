/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | - Attribute Overrides --------------------------------------------------- |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | AddRemoveListPane                                                 | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | |                                                                       | |
 * | |   x Override Default                                                  | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | PageBook (AttributeOverrideComposite/AssociationOverrideComposite)| | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see EntityComposite - The parent container
 * @see AttributeOverrideComposite
 * @see AssociationOverrideComposite
 *
 * @version 3.0
 * @since 1.0
 */
public class EntityOverridesComposite extends AbstractEntityOverridesComposite
{

	/**
	 * Creates a new <code>OverridesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public EntityOverridesComposite(Pane<? extends Entity> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

}