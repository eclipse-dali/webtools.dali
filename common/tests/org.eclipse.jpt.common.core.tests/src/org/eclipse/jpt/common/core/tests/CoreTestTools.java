/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.tests;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;

/**
 * Various tools that can be used by test cases.
 */
@SuppressWarnings("nls")
public final class CoreTestTools {

	/**
	 * Delete the specified project, making multiple attempts because open
	 * files etc. can prevent a project from being deleted.
	 */
	public static void delete(IProject project) throws Exception {
		int i = 0;
		boolean deleted = false;
		while ( ! deleted) {
			try {
				project.delete(true, true, null);
				deleted = true;
			} catch (CoreException ex) {
				if (i == 3) {
					throw new RuntimeException("unable to delete project: " + project.getName(), ex);
				}
				Thread.sleep((1 << i) * 1000);
				i++;
			}
		}
	}

	/**
	 * suppressed constructor
	 */
	private CoreTestTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
