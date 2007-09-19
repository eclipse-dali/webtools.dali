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
	private List<MappingAnnotationProvider> javaTypeMappingAnnotationProviders;
	
	private Collection<AnnotationProvider> javaTypeAnnotationProviders;
	
	private Collection<AnnotationProvider> entityAnnotationProviders;
	
	private Collection<AnnotationProvider> embeddableAnnotationProviders;
	
	private Collection<AnnotationProvider> mappedSuperclassAnnotationProviders;

	protected BaseJpaPlatform() {
		super();
	}
	
	public ListIterator<MappingAnnotationProvider> javaTypeMappingAnnotationProviders() {
		if (this.javaTypeMappingAnnotationProviders == null) {
			this.javaTypeMappingAnnotationProviders = new ArrayList<MappingAnnotationProvider>();
			this.addJavaTypeMappingAnnotationProvidersTo(this.javaTypeMappingAnnotationProviders);
		}
		return new CloneListIterator<MappingAnnotationProvider>(this.javaTypeMappingAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeMappingAnnotationProvidersTo(Collection<MappingAnnotationProvider> providers) {
		providers.add(JavaEmbeddableProvider.instance());
		providers.add(JavaEntityProvider.instance());
		providers.add(JavaMappedSuperclassProvider.instance());
	}
	
	public MappingAnnotationProvider javaTypeMappingAnnotationProvider(String annotationName) {
		for (Iterator<MappingAnnotationProvider> i = this.javaTypeMappingAnnotationProviders(); i.hasNext(); ) {
			MappingAnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	
	public Iterator<AnnotationProvider> javaTypeAnnotationProviders() {
		if (this.javaTypeAnnotationProviders == null) {
			this.javaTypeAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addJavaTypeAnnotationProvidersTo(this.javaTypeAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.javaTypeAnnotationProviders);
	}
	
	/**
	 * Override this to specify more or different attribute mapping providers.
	 * The default includes the JPA spec-defined attribute mappings of 
	 * Basic, Id, Transient OneToOne, OneToMany, ManyToOne, ManyToMany, Embeddable, EmbeddedId, Version.
	 */
	protected void addJavaTypeAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
		providers.add(JavaSecondaryTableProvider.instance());
		providers.add(JavaSecondaryTablesProvider.instance());
	}
	
	public AnnotationProvider javaTypeAnnotationProvider(String annotationName) {
		for (Iterator<AnnotationProvider> i = this.javaTypeAnnotationProviders(); i.hasNext(); ) {
			AnnotationProvider provider = i.next();
			if (provider.getAnnotationName().equals(annotationName)) {
				return provider;
			}
		}
		return null;
	}

	public Iterator<AnnotationProvider> entityAnnotationProviders() {
		if (this.entityAnnotationProviders == null) {
			this.entityAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addEntityAnnotationProvidersTo(this.entityAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.entityAnnotationProviders);
	}
	
	protected void addEntityAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
		providers.add(JavaTableProvider.instance());
	}
	
	public Iterator<AnnotationProvider> embeddableAnnotationProviders() {
		if (this.embeddableAnnotationProviders == null) {
			this.embeddableAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addEmbeddableAnnotationProvidersTo(this.embeddableAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.embeddableAnnotationProviders);
	}
	
	protected void addEmbeddableAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
	}
	
	public Iterator<AnnotationProvider> mappedSuperclassAnnotationProviders() {
		if (this.mappedSuperclassAnnotationProviders == null) {
			this.mappedSuperclassAnnotationProviders = new ArrayList<AnnotationProvider>();
			this.addMappedSuperclassAnnotationProvidersTo(this.mappedSuperclassAnnotationProviders);
		}
		return new CloneIterator<AnnotationProvider>(this.mappedSuperclassAnnotationProviders);
	}
	
	protected void addMappedSuperclassAnnotationProvidersTo(Collection<AnnotationProvider> providers) {
	}

}
