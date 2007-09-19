/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

public abstract class BaseJpaPlatform implements JpaPlatform
{
	private List<TypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders;
	
	private Collection<TypeAnnotationProvider> javaTypeAnnotationProviders;
	
	private Collection<TypeAnnotationProvider> entityAnnotationProviders;
	
	private Collection<TypeAnnotationProvider> embeddableAnnotationProviders;
	
	private Collection<TypeAnnotationProvider> mappedSuperclassAnnotationProviders;

	protected BaseJpaPlatform() {
		super();
	}
	
	public ListIterator<TypeMappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		if (this.javaTypeMappingAnnotationProviders == null) {
			this.javaTypeMappingAnnotationProviders = new ArrayList<TypeMappingAnnotationProvider>();
			this.addJavaTypeMappingAnnotationProvidersTo(this.javaTypeMappingAnnotationProviders);
		}
		return new CloneListIterator<TypeMappingAnnotationProvider>(this.javaTypeMappingAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeMappingAnnotationProvidersTo(Collection<TypeMappingAnnotationProvider> providers) {
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
	}
	
	public TypeMappingAnnotationProvider javaTypeMappingAnnotationProvider(String annotationName) {
		for (Iterator<TypeMappingAnnotationProvider> i = this.javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			TypeMappingAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	
	public Iterator<TypeAnnotationProvider> javaTypeAnnotationProviders() {
		if (this.javaTypeAnnotationProviders == null) {
			this.javaTypeAnnotationProviders = new ArrayList<TypeAnnotationProvider>();
			this.addJavaTypeAnnotationProvidersTo(this.javaTypeAnnotationProviders);
		}
		return new CloneIterator<TypeAnnotationProvider>(this.javaTypeAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeAnnotationProvidersTo(Collection<TypeAnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
		providers.add(JavaSecondaryTableProvider.instance());
		providers.add(JavaSecondaryTablesProvider.instance());
	}
	
	public TypeAnnotationProvider javaTypeAnnotationProvider(String annotationName) {
		for (Iterator<TypeAnnotationProvider> i = this.javaTypeAnnotationProviders(); i.hasNext(); ) {
			TypeAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	public Iterator<TypeAnnotationProvider> entityAnnotationProviders() {
		if (this.entityAnnotationProviders == null) {
			this.entityAnnotationProviders = new ArrayList<TypeAnnotationProvider>();
			this.addEntityAnnotationProvidersTo(this.entityAnnotationProviders);
		}
		return new CloneIterator<TypeAnnotationProvider>(this.entityAnnotationProviders);
	}
	
	protected void addEntityAnnotationProvidersTo(Collection<TypeAnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
	}
	
	public Iterator<TypeAnnotationProvider> embeddableAnnotationProviders() {
		if (this.embeddableAnnotationProviders == null) {
			this.embeddableAnnotationProviders = new ArrayList<TypeAnnotationProvider>();
			this.addEmbeddableAnnotationProvidersTo(this.embeddableAnnotationProviders);
		}
		return new CloneIterator<TypeAnnotationProvider>(this.embeddableAnnotationProviders);
	}
	
	protected void addEmbeddableAnnotationProvidersTo(Collection<TypeAnnotationProvider> providers) {
	}
	
	public Iterator<TypeAnnotationProvider> mappedSuperclassAnnotationProviders() {
		if (this.mappedSuperclassAnnotationProviders == null) {
			this.mappedSuperclassAnnotationProviders = new ArrayList<TypeAnnotationProvider>();
			this.addMappedSuperclassAnnotationProvidersTo(this.mappedSuperclassAnnotationProviders);
		}
		return new CloneIterator<TypeAnnotationProvider>(this.mappedSuperclassAnnotationProviders);
	}
	
	protected void addMappedSuperclassAnnotationProvidersTo(Collection<TypeAnnotationProvider> providers) {
	}

}
