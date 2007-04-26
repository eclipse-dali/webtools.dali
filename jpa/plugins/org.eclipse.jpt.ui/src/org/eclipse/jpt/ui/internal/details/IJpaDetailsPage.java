/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.swt.widgets.Control;

public interface IJpaDetailsPage
{
	Control getControl();

	/**
	 * Set the content for the page and populate widgets
	 */
	void populate(IJpaContentNode contentNode);
	
	
	/**
	 * Perform any other disposal needed
	 */
	void dispose();
}
