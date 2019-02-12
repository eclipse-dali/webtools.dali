/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.node.Node;
import org.eclipse.swt.widgets.Composite;

/**
 * The abstract pane to use when the pane is shown in a {@link Dialog}.
 */
public abstract class DialogPane<T extends Node>
	extends Pane<T>
{
	/**
	 * Construct a dialog pane that uses the specified parent pane's:<ul>
	 * <li>subject model
	 * <li><em>enabled</em> model
	 * </ul>
	 */
	protected DialogPane(
			DialogPane<? extends T> parent,
			Composite parentComposite) {
		super(parent, parentComposite);
	}

	/**
	 * Construct a dialog pane that uses the specified parent pane's:<ul>
	 * <li><em>enabled</em> model
	 * </ul>
	 */
	protected DialogPane(
			DialogPane<?> parent,
			PropertyValueModel<? extends T> subjectModel,
			Composite parentComposite) {
		super(parent, subjectModel, parentComposite);
	}

	/**
	 * Construct a <em>root</em> dialog pane with the specified subject model
	 * and resource manager.
	 * The pane will use the default (non-form) widget factory.
	 * The pane will be <em>disabled</em> whenever the subject is
	 * <code>null</code>.
	 */
	protected DialogPane(
			PropertyValueModel<? extends T> subjectModel,
			Composite parentComposite,
			ResourceManager resourceManager) {
		super(subjectModel, parentComposite, DefaultWidgetFactory.instance(), resourceManager);
	}
}
