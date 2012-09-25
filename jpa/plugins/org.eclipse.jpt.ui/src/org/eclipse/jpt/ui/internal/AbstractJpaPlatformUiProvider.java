/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.ResourceUiDefinition;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the JPA platform ui provider should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformUiProvider implements JpaPlatformUiProvider
{
	private JpaDetailsProvider[] detailsProviders;

	private ResourceUiDefinition[] resourceUiDefinitions;
	
	/**
	 * zero-argument constructor
	 */
	public AbstractJpaPlatformUiProvider() {
		super();
	}


	// ********** details providers **********

	public ListIterator<JpaDetailsProvider> detailsProviders() {
		return new ArrayListIterator<JpaDetailsProvider>(getDetailsProviders());
	}
	
	protected synchronized JpaDetailsProvider[] getDetailsProviders() {
		if (this.detailsProviders == null) {
			this.detailsProviders = this.buildDetailsProviders();
		}
		return this.detailsProviders;
	}

	protected JpaDetailsProvider[] buildDetailsProviders() {
		ArrayList<JpaDetailsProvider> providers = new ArrayList<JpaDetailsProvider>();
		this.addDetailsProvidersTo(providers);
		return providers.toArray(new JpaDetailsProvider[providers.size()]);
	}

	/**
	 * Implement this to specify JPA details providers.
	 */
	protected abstract void addDetailsProvidersTo(List<JpaDetailsProvider> providers);
	
	
	
	// ********** structure providers **********

	public ListIterator<ResourceUiDefinition> resourceUiDefinitions() {
		return new ArrayListIterator<ResourceUiDefinition>(getResourceUiDefinitions());
	}
	
	protected synchronized ResourceUiDefinition[] getResourceUiDefinitions() {
		if (this.resourceUiDefinitions == null) {
			this.resourceUiDefinitions = this.buildResourceUiDefinitions();
		}
		return this.resourceUiDefinitions;
	}

	protected ResourceUiDefinition[] buildResourceUiDefinitions() {
		ArrayList<ResourceUiDefinition> definitions = new ArrayList<ResourceUiDefinition>();
		this.addResourceUiDefinitionsTo(definitions);
		return definitions.toArray(new ResourceUiDefinition[definitions.size()]);
	}

	/**
	 * Implement this to specify JPA mapping file ui definitions.
	 */
	protected abstract void addResourceUiDefinitionsTo(List<ResourceUiDefinition> definitions);
	
}
