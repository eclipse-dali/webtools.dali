/*******************************************************************************
 *  Copyright (c) 2010, 2011  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License v1.0, which accompanies this distribution
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.libval;

import static org.eclipse.jpt.common.core.internal.XPointUtil.*;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.expressions.Expression;
import org.eclipse.core.expressions.ExpressionConverter;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jpt.common.core.JptCommonCorePlugin;
import org.eclipse.jpt.common.core.internal.XPointUtil.XPointException;
import org.eclipse.jpt.common.core.libprov.JptLibraryProviderInstallOperationConfig;
import org.eclipse.jpt.common.core.libval.LibraryValidator;
import org.eclipse.jpt.common.utility.internal.KeyedSet;
import org.eclipse.jpt.common.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterables.TransformationIterable;

public class LibraryValidatorManager {
	
	public static final String EXTENSION_POINT_ID = "libraryValidators"; //$NON-NLS-1$
	public static final String QUALIFIED_EXTENSION_POINT_ID = JptCommonCorePlugin.PLUGIN_ID_ + EXTENSION_POINT_ID;
	public static final String LIBRARY_VALIDATOR_ELEMENT = "libraryValidator"; //$NON-NLS-1$
	public static final String ID_ATTRIBUTE = "id"; //$NON-NLS-1$
	public static final String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$
	public static final String ENABLEMENT_ELEMENT = "enablement"; //$NON-NLS-1$
	
	
	private static LibraryValidatorManager INSTANCE = new LibraryValidatorManager();
	
	
	public static LibraryValidatorManager instance() {
		return INSTANCE;
	}
	
	
	private KeyedSet<String, LibraryValidatorConfig> libraryValidatorConfigs;
	
	
	private LibraryValidatorManager() {
		this.libraryValidatorConfigs = new KeyedSet<String, LibraryValidatorConfig>();
		readExtensions();
	}
	
	
	/**
	 * Return the {@link LibraryValidator}s 
	 */
	public Iterable<LibraryValidator> getLibraryValidators(final JptLibraryProviderInstallOperationConfig config) {
		return new FilteringIterable<LibraryValidator>(
				new TransformationIterable<LibraryValidatorConfig, LibraryValidator>(
						new FilteringIterable<LibraryValidatorConfig>(
								this.libraryValidatorConfigs.getItemSet()) {
							@Override
							protected boolean accept(LibraryValidatorConfig o) {
								return o.isEnabledFor(config);
							}
						}) {
					@Override
					protected LibraryValidator transform(LibraryValidatorConfig o) {
						return o.getLibraryValidator();
					}
				}) {
			@Override
			protected boolean accept(LibraryValidator o) {
				return o != null;
			}
		};
	}
	
	private void readExtensions() {
		final IExtensionRegistry registry = Platform.getExtensionRegistry();
		
		final IExtensionPoint xpoint 
				= registry.getExtensionPoint(QUALIFIED_EXTENSION_POINT_ID);
		
		if (xpoint == null) {
			throw new IllegalStateException();
		}
		
		final List<IConfigurationElement> configs = new ArrayList<IConfigurationElement>();
		
		for (IExtension extension : xpoint.getExtensions()) {
        	for (IConfigurationElement element : extension.getConfigurationElements()) {
        		configs.add(element);
        	}
		}
		
		for (IConfigurationElement element : configs) {
			if (element.getName().equals(LIBRARY_VALIDATOR_ELEMENT)) {
                readExtension(element);
			}
		}
	}
	
	private void readExtension(IConfigurationElement element) {
		try {
			final LibraryValidatorConfig lvConfig = new LibraryValidatorConfig();
			
			// plug-in id
			lvConfig.setPluginId(element.getContributor().getName());
			
			// resource locator id
			lvConfig.setId(findRequiredAttribute(element, ID_ATTRIBUTE));
			
			if (this.libraryValidatorConfigs.containsKey(lvConfig.getId())) {
				logDuplicateExtension(QUALIFIED_EXTENSION_POINT_ID, ID_ATTRIBUTE, lvConfig.getId());
				throw new XPointException();
			}
			
			// resource locator class name
			lvConfig.setClassName(findRequiredAttribute(element, CLASS_ATTRIBUTE));
			
			// enablement
			for (IConfigurationElement child : element.getChildren()) {
				String childName = child.getName();
				if (childName.equals(ENABLEMENT_ELEMENT)) {
					Expression expr;
					try {
						expr = ExpressionConverter.getDefault().perform(child);
					}
					catch (CoreException e) {
						log(e);
						throw new XPointException();
					}
					lvConfig.setEnablementCondition(expr);
				}
			}
			
			this.libraryValidatorConfigs.addItem(lvConfig.getId(), lvConfig);
		}
		catch (XPointException e) {
			// Ignore and continue. The problem has already been reported to the user
			// in the log.
		}
	}
}
