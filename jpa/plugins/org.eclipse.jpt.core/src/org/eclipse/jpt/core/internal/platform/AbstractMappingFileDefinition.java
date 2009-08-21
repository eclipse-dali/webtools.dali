/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ListIterator;
import org.eclipse.jpt.core.context.MappingFileDefinition;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Tools;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the definition should be "static" (i.e. unchanging once it is initialized).
 */
public abstract class AbstractMappingFileDefinition
	implements MappingFileDefinition
{
	private OrmTypeMappingProvider[] ormTypeMappingProviders;

	private OrmAttributeMappingProvider[] ormAttributeMappingProviders;
	
	
	/**
	 * zero-argument constructor
	 */
	protected AbstractMappingFileDefinition() {
		super();
	}
	
	
	// ********** ORM type mappings **********
	
	public OrmTypeMappingProvider getOrmTypeMappingProvider(String mappingKey) {
		for (OrmTypeMappingProvider provider : CollectionTools.iterable(ormTypeMappingProviders())) {
			if (Tools.valuesAreEqual(provider.getKey(), mappingKey)) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public ListIterator<OrmTypeMappingProvider> ormTypeMappingProviders() {
		return new ArrayListIterator<OrmTypeMappingProvider>(getOrmTypeMappingProviders());
	}
	
	protected synchronized OrmTypeMappingProvider[] getOrmTypeMappingProviders() {
		if (this.ormTypeMappingProviders == null) {
			this.ormTypeMappingProviders = this.buildOrmTypeMappingProviders();
		}
		return this.ormTypeMappingProviders;
	}
	
	/**
	 * Return an array of mapping providers to use for types in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected abstract OrmTypeMappingProvider[] buildOrmTypeMappingProviders();
	
	
	// ********** ORM attribute mappings **********
	
	public OrmAttributeMappingProvider getOrmAttributeMappingProvider(String mappingKey) {
		for (OrmAttributeMappingProvider provider : CollectionTools.iterable(ormAttributeMappingProviders())) {
			if (Tools.valuesAreEqual(provider.getKey(), mappingKey)) {
				return provider;
			}
		}
		throw new IllegalArgumentException("Illegal type mapping key: " + mappingKey); //$NON-NLS-1$
	}
	
	public ListIterator<OrmAttributeMappingProvider> ormAttributeMappingProviders() {
		return new ArrayListIterator<OrmAttributeMappingProvider>(getOrmAttributeMappingProviders());
	}
	
	protected synchronized OrmAttributeMappingProvider[] getOrmAttributeMappingProviders() {
		if (this.ormAttributeMappingProviders == null) {
			this.ormAttributeMappingProviders = this.buildOrmAttributeMappingProviders();
		}
		return this.ormAttributeMappingProviders;
	}
	
	/**
	 * Return an array of mapping providers to use for attributes in mapping files of this type.  
	 * The order is unimportant.
	 */
	protected abstract OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders();
}
