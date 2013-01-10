/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.bundleexclude;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

public final class FacetedReferencingProjectsPropertyTester
	extends PropertyTester
{
	private static final String ANY_FACETED_REFERENCING_PROJECTS_PROPERTY = "anyFacetedReferencingProjects"; //$NON-NLS-1$

	//partially copied from org.eclipse.wst.common.project.facet.core.internal.FacetedProjectPropertyTester
	public boolean test(final Object receiver, final String property, final Object[] args, final Object value) {
		if (property.equals(ANY_FACETED_REFERENCING_PROJECTS_PROPERTY)) {
			final String val = (String) value;
			final int colonIndex = val.indexOf(':');
			final String facetId;
			final String versionExpression;
			if (colonIndex == -1 || colonIndex == val.length() - 1) {
				facetId = val;
				versionExpression = null;
			}
			else {
				facetId = val.substring(0, colonIndex);
				versionExpression = val.substring(colonIndex + 1);
			}
			if (receiver instanceof IResource) {
				try {
					for (IProject project : ((IResource) receiver).getProject().getReferencingProjects()) {
						if (FacetedProjectFramework.hasProjectFacet(project, facetId, versionExpression)) {
							return true;
						}
					}
				}
				catch (CoreException e) {
					JptCommonCorePlugin.instance().logError(e);
				}
			}
		}
		return false;
	}
}
