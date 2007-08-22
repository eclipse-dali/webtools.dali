/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IPersistentType;

public interface TypeContext
{
	IPersistentType getPersistentType();
	
	void refreshDefaults(DefaultsContext defaultsContext, IProgressMonitor monitor);
	
	boolean isRefreshed();
}
