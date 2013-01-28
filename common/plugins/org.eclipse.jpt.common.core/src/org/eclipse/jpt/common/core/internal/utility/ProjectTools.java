/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jst.j2ee.internal.J2EEConstants;
import org.eclipse.wst.common.componentcore.internal.util.IModuleConstants;
import org.eclipse.wst.common.project.facet.core.FacetedProjectFramework;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;

/**
 * Utility methods for {@link IProject Eclipse projects}.
 */
public class ProjectTools {

	/**
	 * Return the runtime path to which <code>.jar</code> files are relatively
	 * specified for the specified project.
	 * A web project has a runtime root path of <code>"/WEB-INF"</code>;
	 * while for a non-web project it is simply <code>"/"</code>.
	 */
	public static IPath getJarRuntimeRootPath(IProject project) {
		String path = "/"; //$NON-NLS-1$
		if (hasWebFacet(project)) {
			path = path + J2EEConstants.WEB_INF;
		}
		return new Path(path);
	}

	/**
	 * Return whether the specified project has a
	 * {@link IModuleConstants#JST_WEB_MODULE Web facet}.
	 */
	public static boolean hasWebFacet(IProject project) {
		return hasFacet(project, IModuleConstants.JST_WEB_MODULE);
	}

	/**
	 * Return whether the specified project has the specified facet.
	 */
	public static boolean hasFacet(IProject project, IProjectFacet facet) {
		return hasFacet(project, facet.getId());
	}

	/**
	 * Return whether the specified project has the specified facet.
	 */
	public static boolean hasFacet(IProject project, String facetID) {
		try {
			return FacetedProjectFramework.hasProjectFacet(project, facetID);
		} catch (CoreException ex) {
			// problem reading the project metadata - assume facet does not exist and return 'false'
			JptCommonCorePlugin.instance().logError(ex);
			return false;
		}
	}

	public static final Transformer<IProject, String> NAME_TRANSFORMER = new NameTransformer();
	public static class NameTransformer
		extends TransformerAdapter<IProject, String>
	{
		@Override
		public String transform(IProject project) {
			return project.getName();
		}
	}

	private ProjectTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
