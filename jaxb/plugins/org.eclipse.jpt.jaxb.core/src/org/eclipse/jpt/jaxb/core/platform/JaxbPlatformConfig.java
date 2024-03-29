/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.platform;

import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * Metadata that describes a JAXB platform as defined in an
 * extension to the <code>org.eclipse.jpt.jaxb.core.jaxbPlatforms</code>
 * extension point.
 * <p>
 * See <code>org.eclipse.jpt.jaxb.core/plugin.xml:jaxbPlatforms</code>.
 * <p>
 * Not intended to be implemented by clients.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface JaxbPlatformConfig {
	/**
	 * Return the config's manager.
	 */
	JaxbPlatformManager getJaxbPlatformManager();

	/**
	 * Return the config's extension-supplied ID.
	 * This is unique among all the JAXB platform configs.
	 */
	String getId();

	/**
	 * Return the config's extension-supplied label.
	 */
	String getLabel();

	Transformer<JaxbPlatformConfig, String> LABEL_TRANSFORMER = new LabelTransformer();
	static class LabelTransformer
		extends TransformerAdapter<JaxbPlatformConfig, String>
	 {
		@Override
		public String transform(JaxbPlatformConfig config) {
			return config.getLabel();
		}
	}

	/**
	 * Return the config's extension-supplied factory class name.
	 * The class must be instantiable and implement the
	 * {@link org.eclipse.jpt.jaxb.core.platform.JaxbPlatformDefinitionFactory} interface.
	 */
	String getFactoryClassName();

	/**
	 * Return whether the config's JAXB platform supports the specified
	 * JAXB facet version. If the extension specifies a JAXB facet version, it
	 * must be the same as the specified JAXB facet version. If the extension
	 * does <em>not</em> specify a JAXB facet verion, the config's JAXB
	 * platform supports all JAXB facet versions.
	 * @exception IllegalArgumentException if the specified facet version is
	 * not for a JAXB facet
	 */
	boolean supportsJaxbFacetVersion(IProjectFacetVersion jaxbFacetVersion);

	class SupportsJaxbFacetVersion
		extends CriterionPredicate<JaxbPlatformConfig, IProjectFacetVersion>
	{
		public SupportsJaxbFacetVersion(IProjectFacetVersion jaxbFacetVersion) {
			super(jaxbFacetVersion);
		}
		public boolean evaluate(JaxbPlatformConfig config) {
			return config.supportsJaxbFacetVersion(this.criterion);
		}
	}

	/**
	 * Return whether the config's JAXB platform can be used as the default
	 * JAXB platform for its {@link #supportsJaxbFacetVersion(IProjectFacetVersion)
	 * supported JAXB facet versions}.
	 */
	boolean isDefault();

	Predicate<JaxbPlatformConfig> IS_DEFAULT = new IsDefault();
	class IsDefault
		extends PredicateAdapter<JaxbPlatformConfig>
	{
		@Override
		public boolean evaluate(JaxbPlatformConfig config) {
			return config.isDefault();
		}
	}

	/**
	 * Return config's group config.
	 */
	JaxbPlatformGroupConfig getGroupConfig();

	/**
	 * Return the ID of the plug-in that contributed the JAXB platform
	 * config.
	 */
	String getPluginId();
}
