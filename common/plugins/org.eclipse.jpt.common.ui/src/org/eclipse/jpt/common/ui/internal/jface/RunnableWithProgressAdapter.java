/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Convenience implementation of {@link IRunnableWithProgress}.
 */
public class RunnableWithProgressAdapter
	implements IRunnableWithProgress
{
	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		// NOP
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}
}
