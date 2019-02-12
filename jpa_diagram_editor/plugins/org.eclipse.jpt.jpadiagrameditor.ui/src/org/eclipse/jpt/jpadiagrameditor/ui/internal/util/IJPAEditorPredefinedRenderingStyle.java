/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2012 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Stefan Dimov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.graphiti.util.IPredefinedRenderingStyle;

public interface IJPAEditorPredefinedRenderingStyle extends IPredefinedRenderingStyle {

	/**
	 * The ID for a green-to-white gradient with a gloss-effect.
	 */
	public static final String GREEN_WHITE_GLOSS_ID = "green-white-gloss"; //$NON-NLS-1$
	
	/**
	 * The ID for a violet-to-white gradient with a gloss-effect.
	 */
	public static final String VIOLET_WHITE_GLOSS_ID = "violet-white-gloss"; //$NON-NLS-1$
	
}
