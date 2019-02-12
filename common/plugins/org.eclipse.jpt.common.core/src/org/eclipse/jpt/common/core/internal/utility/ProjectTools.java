/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jpt.common.core.internal.plugin.JptCommonCorePlugin;
import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
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

	public static class HasFacet
		extends CriterionPredicate<IProject, String>
	{
		public HasFacet(String facetID) {
			super(facetID);
		}
		public boolean evaluate(IProject project) {
			return hasFacet(project, this.criterion);
		}
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

	public static final Predicate<IProject> IS_JAVA_PROJECT = new HasNature(JavaCore.NATURE_ID);
	public static class HasNature
		extends CriterionPredicate<IProject, String>
	{
		public HasNature(String natureID) {
			super(natureID);
		}
		public boolean evaluate(IProject project) {
			return hasNature(project, this.criterion);
		}
	}

	/**
	 * Return whether the specified project has the Java nature.
	 */
	public static boolean isJavaProject(IProject project) {
		return hasNature(project, JavaCore.NATURE_ID);
	}

	/**
	 * Return whether the specified project has the specified nature.
	 */
	public static boolean hasNature(IProject project, String natureID) {
		try {
			return project.exists() && project.isOpen() && project.hasNature(natureID);
		} catch (CoreException ex) {
			// problem reading the project - assume nature does not exist and return 'false'
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
