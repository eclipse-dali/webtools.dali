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
 *    Petya Sabeva - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.facade;

import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.jpadiagrameditor.ui.internal.util.IStaticIDE;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

public class StaticIDE implements IStaticIDE {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jpt.jpadiagrameditor.ui.provider.IStaticIDE#openEditor(org.eclipse
	 * .core.resources.IFile)
	 */
	public void openEditor(IFile file) throws PartInitException {
		IDE.openEditor(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage(), file);
	}

}
