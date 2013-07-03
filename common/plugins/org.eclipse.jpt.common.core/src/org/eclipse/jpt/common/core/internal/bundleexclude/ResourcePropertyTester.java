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
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;

/**
 * <strong>NB:</strong>
 * This class is in a package that is excluded from bundle
 * activation in the <code>META-INF/MANIFEST.MF</code> file.
 * To be excluded, it must be self-contained and
 * not trigger bundle auto-activation.
 * @see org.eclipse.wst.common.project.facet.core.internal.FacetedProjectPropertyTester
 */
public final class ResourcePropertyTester
	extends PropertyTester
{
	private static final String ANY_REFERENCING_PROJECT_HAS_FACET_PROPERTY = "anyReferencingProjectHasFacet"; //$NON-NLS-1$

	public boolean test(Object receiver, String property, Object[] args, Object value) {
		if (receiver instanceof IResource) {
			return this.test((IResource) receiver, property, value);
		}
		return false;
	}

	private boolean test(IResource resource, String property, Object value) {
		if (property.equals(ANY_REFERENCING_PROJECT_HAS_FACET_PROPERTY)) {
			return this.anyReferencingProjectHasFacet(resource.getProject(), (String) value);
		}
		return false;
	}

	private boolean anyReferencingProjectHasFacet(IProject project, String facetVersionExpression) {
		int colon = facetVersionExpression.indexOf(':');
		String facetID;
		String versionExpression;
		if ((colon == -1) || (colon == facetVersionExpression.length() - 1)) {
			facetID = facetVersionExpression;
			versionExpression = null;
		} else {
			facetID = facetVersionExpression.substring(0, colon);
			versionExpression = facetVersionExpression.substring(colon + 1);
		}
		try {
			return this.anyReferencingProjectHasFacet(project, facetID, versionExpression);
		} catch (CoreException ex) {
			// ignore - so we don't load the plug-in unnecessarily
			return false;
		}
	}

	private boolean anyReferencingProjectHasFacet(IProject project, String facetID, String versionExpression) throws CoreException {
		if (FacetedProjectFramework.hasProjectFacet(project, facetID, versionExpression)) {
			return true;
		}
		for (IProject refProject : project.getReferencingProjects()) {
			if (FacetedProjectFramework.hasProjectFacet(refProject, facetID, versionExpression)) {
				return true;
			}
		}
		return false;
	}
}
