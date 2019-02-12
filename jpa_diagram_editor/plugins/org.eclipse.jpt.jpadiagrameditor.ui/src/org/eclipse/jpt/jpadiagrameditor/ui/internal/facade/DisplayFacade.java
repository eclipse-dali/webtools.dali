/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.facade;

import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IDisplayFacade;
import org.eclipse.swt.widgets.Display;

public class DisplayFacade implements IDisplayFacade {

	public void asyncExec(Runnable runnable) {
		Display.getDefault().asyncExec(runnable);
	}

}
