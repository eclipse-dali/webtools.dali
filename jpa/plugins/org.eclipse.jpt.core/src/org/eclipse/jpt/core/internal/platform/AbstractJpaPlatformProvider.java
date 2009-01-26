/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.jpt.core.JpaPlatformProvider;
import org.eclipse.jpt.core.JpaResourceModelProvider;
import org.eclipse.jpt.core.context.MappingFileProvider;
import org.eclipse.jpt.core.context.java.DefaultJavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaAttributeMappingProvider;
import org.eclipse.jpt.core.context.java.JavaTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.ExtendedOrmTypeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmAttributeMappingProvider;
import org.eclipse.jpt.core.context.orm.OrmTypeMappingProvider;
import org.eclipse.jpt.core.internal.context.java.JavaNullAttributeMappingProvider;
import org.eclipse.jpt.utility.internal.iterators.ArrayListIterator;

/**
 * All the state in the JPA platform should be "static" (i.e. unchanging once
 * it is initialized).
 */
public abstract class AbstractJpaPlatformProvider implements JpaPlatformProvider
{
	private JpaResourceModelProvider[] resourceModelProviders;

	private JavaTypeMappingProvider[] javaTypeMappingProviders;

	private JavaAttributeMappingProvider[] javaAttributeMappingProviders;

	private MappingFileProvider[] mappingFileProviders;

	private DefaultJavaAttributeMappingProvider[] defaultJavaAttributeMappingProviders;

	private ExtendedOrmTypeMappingProvider[] extendedOrmTypeMappingProviders;

	private OrmTypeMappingProvider[] ormTypeMappingProviders;

	private ExtendedOrmAttributeMappingProvider[] extendedOrmAttributeMappingProviders;

	private OrmAttributeMappingProvider[] ormAttributeMappingProviders;


	/**
	 * zero-argument constructor
	 */
	public AbstractJpaPlatformProvider() {
		super();
	}


	// ********** resource models **********

	public ListIterator<JpaResourceModelProvider> resourceModelProviders() {
		return new ArrayListIterator<JpaResourceModelProvider>(getResourceModelProviders());
	}
	
	protected synchronized JpaResourceModelProvider[] getResourceModelProviders() {
		if (this.resourceModelProviders == null) {
			this.resourceModelProviders = this.buildResourceModelProviders();
		}
		return this.resourceModelProviders;
	}

	protected JpaResourceModelProvider[] buildResourceModelProviders() {
		ArrayList<JpaResourceModelProvider> providers = new ArrayList<JpaResourceModelProvider>();
		this.addResourceModelProvidersTo(providers);
		return providers.toArray(new JpaResourceModelProvider[providers.size()]);
	}

	/**
	 * Implement this to specify JPA resource model providers.
	 */
	protected abstract void addResourceModelProvidersTo(List<JpaResourceModelProvider> providers);


	// ********** Java type mappings **********
	
	public ListIterator<JavaTypeMappingProvider> javaTypeMappingProviders() {
		return new ArrayListIterator<JavaTypeMappingProvider>(getJavaTypeMappingProviders());
	}
	
	protected synchronized JavaTypeMappingProvider[] getJavaTypeMappingProviders() {
		if (this.javaTypeMappingProviders == null) {
			this.javaTypeMappingProviders = this.buildJavaTypeMappingProviders();
		}
		return this.javaTypeMappingProviders;
	}

	protected JavaTypeMappingProvider[] buildJavaTypeMappingProviders() {
		ArrayList<JavaTypeMappingProvider> providers = new ArrayList<JavaTypeMappingProvider>();
		this.addJavaTypeMappingProvidersTo(providers);
		return providers.toArray(new JavaTypeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify type mapping providers.
	 */
	protected abstract void addJavaTypeMappingProvidersTo(List<JavaTypeMappingProvider> providers);


	// ********** Java attribute mappings **********
	
	public ListIterator<JavaAttributeMappingProvider> javaAttributeMappingProviders() {
		return new ArrayListIterator<JavaAttributeMappingProvider>(getJavaAttributeMappingProviders());
	}
	
	protected synchronized JavaAttributeMappingProvider[] getJavaAttributeMappingProviders() {
		if (this.javaAttributeMappingProviders == null) {
			this.javaAttributeMappingProviders = this.buildJavaAttributeMappingProviders();
		}
		return this.javaAttributeMappingProviders;
	}

	protected JavaAttributeMappingProvider[] buildJavaAttributeMappingProviders() {
		ArrayList<JavaAttributeMappingProvider> providers = new ArrayList<JavaAttributeMappingProvider>();
		this.addJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new JavaAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify attribute mapping providers.
	 */
	protected abstract void addJavaAttributeMappingProvidersTo(List<JavaAttributeMappingProvider> providers);


	// ********** default Java attribute mappings **********
	
	public ListIterator<DefaultJavaAttributeMappingProvider> defaultJavaAttributeMappingProviders() {
		return new ArrayListIterator<DefaultJavaAttributeMappingProvider>(getDefaultJavaAttributeMappingProviders());
	}

	protected synchronized DefaultJavaAttributeMappingProvider[] getDefaultJavaAttributeMappingProviders() {
		if (this.defaultJavaAttributeMappingProviders == null) {
			this.defaultJavaAttributeMappingProviders = this.buildDefaultJavaAttributeMappingProviders();
		}
		return this.defaultJavaAttributeMappingProviders;
	}

	protected DefaultJavaAttributeMappingProvider[] buildDefaultJavaAttributeMappingProviders() {
		ArrayList<DefaultJavaAttributeMappingProvider> providers = new ArrayList<DefaultJavaAttributeMappingProvider>();
		this.addDefaultJavaAttributeMappingProvidersTo(providers);
		return providers.toArray(new DefaultJavaAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify default attribute mapping providers.
	 */
	protected abstract void addDefaultJavaAttributeMappingProvidersTo(List<DefaultJavaAttributeMappingProvider> providers);

	/**
	 * the "null" attribute mapping is used when the attribute is neither
	 * modified with a mapping annotation nor mapped by a "default" mapping
	 */
	protected JavaAttributeMappingProvider getNullAttributeMappingProvider() {
		return JavaNullAttributeMappingProvider.instance();
	}


	// ********** Mapping File **********
	
	public ListIterator<MappingFileProvider> mappingFileProviders() {
		return new ArrayListIterator<MappingFileProvider>(getMappingFileProviders());
	}
	
	protected synchronized MappingFileProvider[] getMappingFileProviders() {
		if (this.mappingFileProviders == null) {
			this.mappingFileProviders = this.buildMappingFileProviders();
		}
		return this.mappingFileProviders;
	}

	protected MappingFileProvider[] buildMappingFileProviders() {
		ArrayList<MappingFileProvider> providers = new ArrayList<MappingFileProvider>();
		this.addMappingFileProvidersTo(providers);
		return providers.toArray(new MappingFileProvider[providers.size()]);
	}

	/**
	 * Implement this to specify mapping file providers.
	 */
	protected abstract void addMappingFileProvidersTo(List<MappingFileProvider> providers);


	// ********** extended ORM type mappings **********
	
	public ListIterator<ExtendedOrmTypeMappingProvider> extendedOrmTypeMappingProviders() {
		return new ArrayListIterator<ExtendedOrmTypeMappingProvider>(getExtendedOrmTypeMappingProviders());
	}
	
	protected synchronized ExtendedOrmTypeMappingProvider[] getExtendedOrmTypeMappingProviders() {
		if (this.extendedOrmTypeMappingProviders == null) {
			this.extendedOrmTypeMappingProviders = this.buildExtendedOrmTypeMappingProviders();
		}
		return this.extendedOrmTypeMappingProviders;
	}

	protected ExtendedOrmTypeMappingProvider[] buildExtendedOrmTypeMappingProviders() {
		ArrayList<ExtendedOrmTypeMappingProvider> providers = new ArrayList<ExtendedOrmTypeMappingProvider>();
		this.addExtendedOrmTypeMappingProvidersTo(providers);
		return providers.toArray(new ExtendedOrmTypeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify extended type mapping providers.
	 */
	protected abstract void addExtendedOrmTypeMappingProvidersTo(List<ExtendedOrmTypeMappingProvider> providers);

	// ********** "default" ORM type mappings **********
	
	public ListIterator<OrmTypeMappingProvider> ormTypeMappingProviders() {
		return new ArrayListIterator<OrmTypeMappingProvider>(getOrmTypeMappingProviders());
	}
	
	protected synchronized OrmTypeMappingProvider[] getOrmTypeMappingProviders() {
		if (this.ormTypeMappingProviders == null) {
			this.ormTypeMappingProviders = this.buildOrmTypeMappingProviders();
		}
		return this.ormTypeMappingProviders;
	}

	protected OrmTypeMappingProvider[] buildOrmTypeMappingProviders() {
		ArrayList<OrmTypeMappingProvider> providers = new ArrayList<OrmTypeMappingProvider>();
		this.addOrmTypeMappingProvidersTo(providers);
		return providers.toArray(new OrmTypeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify type mapping providers.
	 */
	protected abstract void addOrmTypeMappingProvidersTo(List<OrmTypeMappingProvider> providers);


	// ********** extended ORM attribute mappings **********
	
	public ListIterator<ExtendedOrmAttributeMappingProvider> extendedOrmAttributeMappingProviders() {
		return new ArrayListIterator<ExtendedOrmAttributeMappingProvider>(getExtendedOrmAttributeMappingProviders());
	}

	protected synchronized ExtendedOrmAttributeMappingProvider[] getExtendedOrmAttributeMappingProviders() {
		if (this.extendedOrmAttributeMappingProviders == null) {
			this.extendedOrmAttributeMappingProviders = this.buildExtendedOrmAttributeMappingProviders();
		}
		return this.extendedOrmAttributeMappingProviders;
	}

	protected ExtendedOrmAttributeMappingProvider[] buildExtendedOrmAttributeMappingProviders() {
		ArrayList<ExtendedOrmAttributeMappingProvider> providers = new ArrayList<ExtendedOrmAttributeMappingProvider>();
		this.addExtendedOrmAttributeMappingProvidersTo(providers);
		return providers.toArray(new ExtendedOrmAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify extended attribute mapping providers.
	 */
	protected abstract void addExtendedOrmAttributeMappingProvidersTo(List<ExtendedOrmAttributeMappingProvider> providers);


	// ********** "default" ORM attribute mappings **********

	public ListIterator<OrmAttributeMappingProvider> ormAttributeMappingProviders() {
		return new ArrayListIterator<OrmAttributeMappingProvider>(getOrmAttributeMappingProviders());
	}

	protected synchronized OrmAttributeMappingProvider[] getOrmAttributeMappingProviders() {
		if (this.ormAttributeMappingProviders == null) {
			this.ormAttributeMappingProviders = this.buildOrmAttributeMappingProviders();
		}
		return this.ormAttributeMappingProviders;
	}

	protected OrmAttributeMappingProvider[] buildOrmAttributeMappingProviders() {
		ArrayList<OrmAttributeMappingProvider> providers = new ArrayList<OrmAttributeMappingProvider>();
		this.addOrmAttributeMappingProvidersTo(providers);
		return providers.toArray(new OrmAttributeMappingProvider[providers.size()]);
	}

	/**
	 * Implement this to specify attribute mapping providers.
	 */
	protected abstract void addOrmAttributeMappingProvidersTo(List<OrmAttributeMappingProvider> providers);
}
