/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 * See <code>AbstractJoinColumnDialogPane</code> for the layout.
 *
 * @see PrimaryKeyJoinColumnStateObject
 * @see PrimaryKeyJoinColumnDialog - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnDialogPane extends AbstractJoinColumnDialogPane<PrimaryKeyJoinColumnStateObject>
{
	/**
	 * Creates a new <code>PrimaryKeyJoinColumnDialogPane</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnDialogPane(PropertyValueModel<PrimaryKeyJoinColumnStateObject> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}
}
