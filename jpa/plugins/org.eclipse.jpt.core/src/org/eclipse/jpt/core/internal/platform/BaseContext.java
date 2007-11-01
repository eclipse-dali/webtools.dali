/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public abstract class BaseContext implements IContext
{
	private IContext parentContext;
	
	
	public BaseContext(IContext parentContext) {
		super();
		this.parentContext = parentContext;
	}
	
	/**
	 * All inter-context based initialization should be done here
	 * (i.e. all initialization based on other contexts, parent or otherwise)
	 */
	protected abstract void initialize();
	
	public IJpaPlatform getPlatform() {
		return getParentContext().getPlatform();
	}
	
	public IContext getParentContext() {
		return parentContext;
	}
	
	/**
	 * All subclass implementation {@link #refreshDefaults(DefaultsContext)} 
	 * should be preceded by a "super" call to this method
	 */
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		initialize();
	}
	
	/**
	 * All subclass implementation {@link #refreshDefaults(DefaultsContext)} 
	 * should be preceded by a "super" call to this method
	 */
	public void addToMessages(List<IMessage> messages) {
	}
}
