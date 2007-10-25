package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IContextModel
{
	/**
	 * Update the context model with the content of the JPA project
	 */
	void update(IProgressMonitor monitor);
}
