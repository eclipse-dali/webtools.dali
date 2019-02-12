/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.facet;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jst.common.project.facet.core.JavaFacet;
import org.eclipse.jst.j2ee.internal.plugin.JavaEEPreferencesInitializer;
import org.eclipse.jst.j2ee.project.facet.IJ2EEFacetConstants;
import org.eclipse.wst.common.project.facet.core.IDynamicPreset;
import org.eclipse.wst.common.project.facet.core.IFacetedProjectBase;
import org.eclipse.wst.common.project.facet.core.IPresetFactory;
import org.eclipse.wst.common.project.facet.core.IProjectFacet;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;
import org.eclipse.wst.common.project.facet.core.PresetDefinition;
import org.eclipse.wst.common.project.facet.core.internal.DefaultFacetsExtensionPoint;
import org.eclipse.wst.common.project.facet.core.runtime.IRuntime;

public class DefaultJpaConfigurationPresetFactory
		implements IPresetFactory {
	
	public static final String PRESET_ID = "default.jpa.configuration"; //$NON-NLS-1$
	
	public PresetDefinition createPreset(String presetId, Map<String,Object> context) 
			throws CoreException {
		
		IFacetedProjectBase fproj 
				= (IFacetedProjectBase) context.get(IDynamicPreset.CONTEXT_KEY_FACETED_PROJECT);
		
		IRuntime runtime = fproj.getPrimaryRuntime();
		
		String label = "Default JPA Configuration";
		String description = "A good starting point for JPA development.  Additional facets may later be installed to add new functionality to the project.";
		
		Set<IProjectFacetVersion> facets = new HashSet();
		Set<IProjectFacetVersion> defaultFacets = DefaultFacetsExtensionPoint.getDefaultFacets(fproj);
		Set<IProjectFacet> defaultJpaFacets = new HashSet();
		defaultJpaFacets.add(JavaFacet.FACET);
		defaultJpaFacets.add(JpaProject.FACET);
		if (JavaEEPreferencesInitializer.getDefaultBoolean(JavaEEPreferencesInitializer.Keys.ADD_TO_EAR_BY_DEFAULT)) {
			defaultJpaFacets.add(IJ2EEFacetConstants.UTILITY_FACET);
		}
		
		for (IProjectFacet pf : defaultJpaFacets) {
			facets.add(findProjectFacetVersion(defaultFacets, pf, runtime));
		}
		
		return new PresetDefinition(label, description, facets);
	}
	
	
	private static IProjectFacetVersion findProjectFacetVersion(
			Set<IProjectFacetVersion> facets, IProjectFacet facet, IRuntime runtime) {
		
		for (IProjectFacetVersion fv : facets) {
			if (fv.getProjectFacet() == facet) {
				return fv;
			}
		}
		
		IProjectFacetVersion defaultPFVersion = facet.getDefaultVersion();
		if (runtime != null) {
			try {
				IProjectFacetVersion pfv = facet.getLatestSupportedVersion(runtime);
				if (pfv != null) {
					return pfv;
				}
			}
			catch (CoreException ce) {
				// fall through, return default
			}
		}
		
		return defaultPFVersion;
	}
}
