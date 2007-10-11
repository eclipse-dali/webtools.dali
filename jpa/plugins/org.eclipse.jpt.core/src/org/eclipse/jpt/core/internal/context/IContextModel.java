package org.eclipse.jpt.core.internal.context;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IJpaProject;

public interface IContextModel
{
	/**
	 * Update the context model with the content of the JPA project
	 */
	void update(IJpaProject jpaProject, IProgressMonitor monitor);
}
