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

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.ui.JpaPlatformUiProvider;
import org.eclipse.jpt.ui.details.AttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.DefaultTypeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.details.TypeMappingUiProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the JPA platform ui provider should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformUiProvider implements JpaPlatformUiProvider
{
	private JpaDetailsProvider[] detailsProviders;

	private JpaStructureProvider[] mappingFileStructureProviders;
	
	private TypeMappingUiProvider<?>[] typeMappingUiProviders;
	
	private DefaultTypeMappingUiProvider<?>[] defaultTypeMappingUiProviders;

	private AttributeMappingUiProvider<? extends AttributeMapping>[] attributeMappingUiProviders;
	
	private DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] defaultAttributeMappingUiProviders;
	
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

	public ListIterator<JpaStructureProvider> mappingFileStructureProviders() {
		return new ArrayListIterator<JpaStructureProvider>(getMappingFileStructureProviders());
	}
	
	protected synchronized JpaStructureProvider[] getMappingFileStructureProviders() {
		if (this.mappingFileStructureProviders == null) {
			this.mappingFileStructureProviders = this.buildMappingFileStructureProviders();
		}
		return this.mappingFileStructureProviders;
	}

	protected JpaStructureProvider[] buildMappingFileStructureProviders() {
		ArrayList<JpaStructureProvider> providers = new ArrayList<JpaStructureProvider>();
		this.addMappingFileStructureProvidersTo(providers);
		return providers.toArray(new JpaStructureProvider[providers.size()]);
	}

	/**
	 * Implement this to specify JPA mapping file structure providers.
	 */
	protected abstract void addMappingFileStructureProvidersTo(List<JpaStructureProvider> providers);
	
	
	// ********** type mapping ui providers **********

	public ListIterator<TypeMappingUiProvider<?>> typeMappingUiProviders() {
		return new ArrayListIterator<TypeMappingUiProvider<?>>(getTypeMappingUiProviders());
	}
	
	protected synchronized TypeMappingUiProvider<?>[] getTypeMappingUiProviders() {
		if (this.typeMappingUiProviders == null) {
			this.typeMappingUiProviders = this.buildTypeMappingUiProviders();
		}
		return this.typeMappingUiProviders;
	}

	protected TypeMappingUiProvider<?>[] buildTypeMappingUiProviders() {
		ArrayList<TypeMappingUiProvider<?>> providers = new ArrayList<TypeMappingUiProvider<?>>();
		this.addTypeMappingUiProvidersTo(providers);
		return providers.toArray(new TypeMappingUiProvider[providers.size()]);
	}

	/**
	 * Implement this to specify JPA type mapping ui providers.
	 */
	protected abstract void addTypeMappingUiProvidersTo(List<TypeMappingUiProvider<?>> providers);
	
	
	
	
	public ListIterator<DefaultTypeMappingUiProvider<?>> defaultTypeMappingUiProviders() {
		return new ArrayListIterator<DefaultTypeMappingUiProvider<?>>(getDefaultTypeMappingUiProviders());
	}
	
	protected synchronized DefaultTypeMappingUiProvider<?>[] getDefaultTypeMappingUiProviders() {
		if (this.defaultTypeMappingUiProviders == null) {
			this.defaultTypeMappingUiProviders = this.buildDefaultTypeMappingUiProviders();
		}
		return this.defaultTypeMappingUiProviders;
	}

	protected DefaultTypeMappingUiProvider<?>[] buildDefaultTypeMappingUiProviders() {
		ArrayList<DefaultTypeMappingUiProvider<?>> providers = new ArrayList<DefaultTypeMappingUiProvider<?>>();
		this.addDefaultTypeMappingUiProvidersTo(providers);
		return providers.toArray(new DefaultTypeMappingUiProvider[providers.size()]);
	}

	/**
	 * Implement this to specify JPA default type mapping ui providers.
	 */
	protected abstract void addDefaultTypeMappingUiProvidersTo(List<DefaultTypeMappingUiProvider<?>> providers);
	
	
	
	// ********** attribute mapping ui providers **********
	
	
	public ListIterator<AttributeMappingUiProvider<? extends AttributeMapping>> attributeMappingUiProviders() {
		if (this.attributeMappingUiProviders == null) {
			this.attributeMappingUiProviders = this.buildAttributeMappingUiProviders();
		}
		return new ArrayListIterator<AttributeMappingUiProvider<? extends AttributeMapping>>(this.attributeMappingUiProviders);
	}

	protected AttributeMappingUiProvider<? extends AttributeMapping>[] buildAttributeMappingUiProviders() {
		ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<AttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		AttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new AttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}


	protected abstract void addAttributeMappingUiProvidersTo(List<AttributeMappingUiProvider<? extends AttributeMapping>> providers);


	// ********** default Java attribute mapping UI providers **********

	public ListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> defaultAttributeMappingUiProviders() {
		if (this.defaultAttributeMappingUiProviders == null) {
			this.defaultAttributeMappingUiProviders = this.buildDefaultAttributeMappingUiProviders();
		}
		return new ArrayListIterator<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>(this.defaultAttributeMappingUiProviders);
	}

	protected DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] buildDefaultAttributeMappingUiProviders() {
		ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers = new ArrayList<DefaultAttributeMappingUiProvider<? extends AttributeMapping>>();
		this.addDefaultAttributeMappingUiProvidersTo(providers);
		@SuppressWarnings("unchecked")
		DefaultAttributeMappingUiProvider<? extends AttributeMapping>[] providerArray = providers.toArray(new DefaultAttributeMappingUiProvider[providers.size()]);
		return providerArray;
	}


	protected abstract void addDefaultAttributeMappingUiProvidersTo(List<DefaultAttributeMappingUiProvider<? extends AttributeMapping>> providers);

}
