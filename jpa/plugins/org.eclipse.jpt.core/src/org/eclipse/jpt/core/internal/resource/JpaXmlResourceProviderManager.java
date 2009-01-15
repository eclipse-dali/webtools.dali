/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource;

import java.util.HashMap;
import java.util.Iterator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.internal.JptCoreMessages;
import org.eclipse.jpt.core.internal.utility.PlatformTools;
import org.eclipse.jpt.core.resource.JpaXmlResourceProvider;
import org.eclipse.jpt.core.resource.JpaXmlResourceProviderFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.osgi.util.NLS;

/**
 * Cache the resource model provider factories as they are requested.
 */
public class JpaXmlResourceProviderManager {

	/**
	 * key: file content type
	 * value: resource model provider factory provider(!)
	 */
	private final HashMap<String, FactoryProvider> factoryProviders;

	private static final String EXTENSION_ID = "resourceModelProviders"; //$NON-NLS-1$
	private static final String EL_MODEL_PROVIDER = "modelProvider"; //$NON-NLS-1$
	private static final String AT_FILE_CONTENT_TYPE = "fileContentType"; //$NON-NLS-1$
	private static final String AT_FACTORY_CLASS = "factoryClass"; //$NON-NLS-1$


	// singleton
	private static final JpaXmlResourceProviderManager INSTANCE = new JpaXmlResourceProviderManager();

	/**
	 * Return the singleton.
	 */
	public static JpaXmlResourceProviderManager instance() {
		return INSTANCE;
	}


	// ********** construction **********

	private JpaXmlResourceProviderManager() {
		super();
		this.factoryProviders = this.buildFactoryProviders();
	}

	private HashMap<String, FactoryProvider> buildFactoryProviders() {
		HashMap<String, FactoryProvider> providers = new HashMap<String, FactoryProvider>();
		for (Iterator<IConfigurationElement> stream = this.configElements(); stream.hasNext(); ) {
			this.addFactoryProviderTo(stream.next(), providers);
		}
		return providers;
	}
	
	/**
	 * Return the configuration elements from the Eclipse platform extension
	 * registry.
	 */
	private Iterator<IConfigurationElement> configElements() {
		return new CompositeIterator<IConfigurationElement>(this.configElementIterators());
	}
	
	private Iterator<Iterator<IConfigurationElement>> configElementIterators() {
		return new TransformationIterator<IExtension, Iterator<IConfigurationElement>>(this.extensions()) {
			@Override
			protected Iterator<IConfigurationElement> transform(IExtension extension) {
				return CollectionTools.iterator(extension.getConfigurationElements());
			}
		};
	}
	
	private Iterator<IExtension> extensions() {
		return CollectionTools.iterator(this.getExtensionPoint().getExtensions());
	}

	private IExtensionPoint getExtensionPoint() {
		return Platform.getExtensionRegistry().getExtensionPoint(JptCorePlugin.PLUGIN_ID, EXTENSION_ID);
	}

	/**
	 * check *all* attributes before returning;
	 * log errors, but keep on truckin'
	 */
	private void addFactoryProviderTo(IConfigurationElement configElement, HashMap<String, FactoryProvider> providers) {
		if ( ! configElement.getName().equals(EL_MODEL_PROVIDER)) {
			return;
		}
		boolean missingAttribute = false;
		String fileContentType = configElement.getAttribute(AT_FILE_CONTENT_TYPE);
		if (fileContentType == null) {
			logMissingAttribute(configElement, AT_FILE_CONTENT_TYPE);
			missingAttribute = false;
		}
		if (configElement.getAttribute(AT_FACTORY_CLASS) == null) {
			logMissingAttribute(configElement, AT_FACTORY_CLASS);
			missingAttribute = false;
		}
		if (missingAttribute) {
			return;
		}
		FactoryProvider prev = providers.get(fileContentType);
		if (prev == null) {
			providers.put(fileContentType, new FactoryProvider(configElement));
		} else {
			// first config element wins - ignore later elements
			logDuplicateFileContentType(prev.getConfigurationElement(), configElement);
		}
	}


	// ********** public API **********

	/**
	 * Returns a resource model provider for the specified file, based on its
	 * content type, if there is one registered for that content type, or one
	 * of its base content types if no resource model provider is registered
	 * for that content type.
	 * 
	 * @param file the file the resource model represents
	 * @return the resource model provider for the file
	 */
	public JpaXmlResourceProvider getXmlResourceProvider(IFile file) {
		IProject project = file.getProject();
		IPath path = file.getFullPath();

		IContentType contentType = PlatformTools.getContentType(file);
		while (contentType != null) {
			JpaXmlResourceProvider modelProvider = this.getXmlResourceProvider(project, path, contentType);
			if (modelProvider != null) {
				return modelProvider;
			}
			contentType = contentType.getBaseType();
		}

		return null;
	}

	/**
	 * Returns a resource model provider for the specified file path of the given content type
	 * in the given project.
	 * @param project the project in which the file path exists
	 * @param filePath the path of the file for which the model provider should
	 * 	be created
	 * @param contentType the content type for which to create a model provider
	 * @return the model provider for the file
	 */
	public JpaXmlResourceProvider getXmlResourceProvider(IProject project, IPath filePath, IContentType fileContentType) {
		JpaXmlResourceProviderFactory factory = this.getFactory(fileContentType);
		return (factory == null) ? null : factory.create(project, filePath);
	}

	private JpaXmlResourceProviderFactory getFactory(IContentType fileContentType) {
		FactoryProvider fp = this.factoryProviders.get(fileContentType.getId());
		return (fp == null) ? null : fp.getFactory();
	}


	// ********** errors **********

	private static void logMissingAttribute(IConfigurationElement configElement, String attributeName) {
		JptCorePlugin.log(
			NLS.bind(
				JptCoreMessages.RESOURCE_MODEL_PROVIDER_REGISTRY_MISSING_ATTRIBUTE, 
				new String[] {
						configElement.getName(),
						configElement.getContributor().getName(),
						attributeName
				}
			)
		);
	}

	private static void logDuplicateFileContentType(IConfigurationElement configElement1, IConfigurationElement configElement2) {
		JptCorePlugin.log(
			NLS.bind(
				JptCoreMessages.RESOURCE_MODEL_PROVIDER_REGISTRY_DUPLICATE_FILE_CONTENT_TYPE, 
				new String[] {
						configElement1.getContributor().getName(),
						configElement2.getContributor().getName(),
						AT_FILE_CONTENT_TYPE,
						EL_MODEL_PROVIDER,
						configElement1.getAttribute(AT_FILE_CONTENT_TYPE)
				}
			)
		);
	}


	// ********** factory provider **********

	private static class FactoryProvider {
		private final IConfigurationElement configurationElement;
		private JpaXmlResourceProviderFactory factory;
		private boolean factoryBuilt;  // factory can be null, so use flag

		FactoryProvider(IConfigurationElement configurationElement) {
			super();
			this.configurationElement = configurationElement;
			this.factoryBuilt = false;
		}

		IConfigurationElement getConfigurationElement() {
			return this.configurationElement;
		}

		synchronized JpaXmlResourceProviderFactory getFactory() {
			if ( ! this.factoryBuilt) {
				this.factoryBuilt = true;
				this.factory = this.buildFactory();
			}
			return this.factory;
		}

		private JpaXmlResourceProviderFactory buildFactory() {
			try {
				return (JpaXmlResourceProviderFactory) this.configurationElement.createExecutableExtension(AT_FACTORY_CLASS);
			} catch (CoreException ex) {
				this.logFailedInstantiation(ex);
				return null;  // returning null seems to be expected
			}
		}

		private void logFailedInstantiation(CoreException ex) {
			JptCorePlugin.log(ex);
			JptCorePlugin.log(
				NLS.bind(
					JptCoreMessages.RESOURCE_MODEL_PROVIDER_REGISTRY_FAILED_INSTANTIATION, 
					new String[] {
							this.configurationElement.getAttribute(AT_FACTORY_CLASS),
							this.configurationElement.getName(),
							this.configurationElement.getContributor().getName()
					}
				)
			);
		}

	}

}
