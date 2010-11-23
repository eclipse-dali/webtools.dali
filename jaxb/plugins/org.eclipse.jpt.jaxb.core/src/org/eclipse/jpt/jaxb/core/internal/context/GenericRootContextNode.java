/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.internal.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.jaxb.core.JaxbProject;
import org.eclipse.jpt.jaxb.core.context.JaxbPackage;
import org.eclipse.jpt.jaxb.core.context.JaxbPersistentClass;
import org.eclipse.jpt.jaxb.core.context.JaxbRootContextNode;
import org.eclipse.jpt.jaxb.core.resource.java.JAXB;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourcePackage;
import org.eclipse.jpt.jaxb.core.resource.java.JavaResourceType;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.LiveCloneIterable;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

/**
 * the context model root
 */
public class GenericRootContextNode
		extends AbstractJaxbContextNode
		implements JaxbRootContextNode {
	
	/* This object has no parent, so it must point to the JAXB project explicitly. */
	protected final JaxbProject jaxbProject;
	
	/* The map of package name to JaxbPackage objects */
	protected final Map<String, JaxbPackage> packages;
	
	/* The map of class name to JaxbPersistentClass objects */
	protected final Map<String, JaxbPersistentClass> persistentClasses;
	
	
	public GenericRootContextNode(JaxbProject jaxbProject) {
		super(null);  // the JPA project is not really a "parent"...
		if (jaxbProject == null) {
			throw new NullPointerException();
		}
		this.jaxbProject = jaxbProject;
		this.packages = new HashMap<String, JaxbPackage>();
		this.persistentClasses = new HashMap<String, JaxbPersistentClass>();
		initialize();
	}
	
	
	@Override
	protected boolean requiresParent() {
		return false;
	}
	
	protected void initialize() {
		// determine initial set of persistent classes
		// (persistent classes that can be determined purely by resource model)
		final Set<JavaResourceType> initialPersistentClasses = calculateInitialPersistentClasses();
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackageNames(initialPersistentClasses);
		
		for (String pkg : initialPackages) {
			this.packages.put(pkg, buildPackage(pkg));
		}
		
		for (JavaResourceType resourceType : initialPersistentClasses) {
			this.persistentClasses.put(resourceType.getName(), buildPersistentClass(resourceType));
		}
	}
	
	public void synchronizeWithResourceModel() {
		for (JaxbPackage each : getPackages()) {
			each.synchronizeWithResourceModel();
		}
		for (JaxbPersistentClass each : getPersistentClasses()) {
			each.synchronizeWithResourceModel();
		}
	}
	
	public void update() {
		// determine initial set of persistent classes
		// (persistent classes that can be determined purely by resource model)
		final Set<JavaResourceType> initialPersistentClasses = calculateInitialPersistentClasses();
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackageNames(initialPersistentClasses);
		
		final Set<String> packagesToUpdate = CollectionTools.<String>set();
		final Set<String> packagesToRemove = CollectionTools.set(this.packages.keySet());
		final Set<String> persistentClassesToUpdate = CollectionTools.<String>set();
		final Set<String> persistentClassesToRemove = CollectionTools.set(this.persistentClasses.keySet());
		
		for (String pkg : initialPackages) {
			if (this.packages.containsKey(pkg)) {
				packagesToUpdate.add(pkg);
				packagesToRemove.remove(pkg);
			}
			else {
				this.addPackage(this.buildPackage(pkg));
			}
		}
		
		for (JavaResourceType resourceType : initialPersistentClasses) {
			String className = resourceType.getQualifiedName();
			if (this.persistentClasses.containsKey(className)) {
				persistentClassesToUpdate.add(className);
				persistentClassesToRemove.remove(className);
			}
			else {
				this.addPersistentClass(this.buildPersistentClass(resourceType));
			}
		}
		
		for (String packageToUpdate : packagesToUpdate) {
			this.packages.get(packageToUpdate).update();
		}
		
		for (String classToUpdate : persistentClassesToUpdate) {
			this.persistentClasses.get(classToUpdate).update();
		}
		
		for (String packageToRemove : packagesToRemove) {
			this.removePackage(packageToRemove);
		}
		
		for (String persistentClassToRemove : persistentClassesToRemove) {
			this.removePersistentClass(persistentClassToRemove);
		}
	}
	
	/*
	 * calculate set of packages that can be determined purely by resource model and the given
	 * set of classes.
	 * This should include:
	 *  - any annotated package 
	 *  - any package containing an included class
	 */
	protected Set<String> calculateInitialPackageNames(final Set<JavaResourceType> initialClasses) {
		final Set<String> packages = CollectionTools.set(
				new TransformationIterable<JavaResourcePackage, String>(
						getJaxbProject().getAnnotatedJavaResourcePackages()) {
					@Override
					protected String transform(JavaResourcePackage o) {
						return o.getName();
					}
				});
		for (JavaResourceType clazz : initialClasses) {
			packages.add(clazz.getPackageName());
		}
		return packages;
	}
	
	/*
	 * Calculate set of persistent classes that can be determined purely by resource model
	 * (so far, this should be all resource types with the @XmlType annotation)
	 */
	protected Set<JavaResourceType> calculateInitialPersistentClasses() {
		return CollectionTools.set(
				new FilteringIterable<JavaResourceType>(
						getJaxbProject().getJavaSourceResourceTypes()) {
					@Override
					protected boolean accept(JavaResourceType o) {
						return o.getAnnotation(JAXB.XML_TYPE) != null;
					}
				});
	}
	
	
	// ********** AbstractJaxbNode overrides **********
	
	@Override
	public JaxbProject getJaxbProject() {
		return this.jaxbProject;
	}
	
	@Override
	public IResource getResource() {
		return this.getProject();
	}
	
	protected IProject getProject() {
		return this.jaxbProject.getProject();
	}
	
	
	// ************* packages ***************
	
	public Iterable<JaxbPackage> getPackages() {
		return new LiveCloneIterable<JaxbPackage>(this.packages.values());
	}
	
	public int getPackagesSize() {
		return this.packages.size();
	}
	
	protected JaxbPackage addPackage(JaxbPackage contextPackage) {
		if (this.packages.containsKey(contextPackage.getName())) {
			throw new IllegalArgumentException("Package with that name already exists."); //$NON-NLS-1$
		}
		this.packages.put(contextPackage.getName(), contextPackage);
		fireItemAdded(PACKAGES_COLLECTION, contextPackage);
		return contextPackage;
	}
	
	protected void removePackage(JaxbPackage contextPackage) {
		this.removePackage(contextPackage.getName());
	}
	
	protected void removePackage(String packageName) {
		if (! this.packages.containsKey(packageName)) {
			throw new IllegalArgumentException("No package with that name exists."); //$NON-NLS-1$
		}
		JaxbPackage removedPackage = this.packages.remove(packageName);
		fireItemRemoved(PACKAGES_COLLECTION, removedPackage);
	}
	
	protected JaxbPackage buildPackage(String packageName) {
		return this.getFactory().buildPackage(this, packageName);
	}
	
	protected boolean isEmpty(JaxbPackage jaxbPackage) {
		return jaxbPackage.isEmpty();
	}
	
	public Iterable<JaxbPersistentClass> getPersistentClasses(final JaxbPackage jaxbPackage) {
		return new FilteringIterable<JaxbPersistentClass>(getPersistentClasses()) {
			@Override
			protected boolean accept(JaxbPersistentClass o) {
				return o.getPackageName().equals(jaxbPackage.getName());
			}
		};
	}
	
	
	// ************* persistentClasses ***************
	
	public Iterable<JaxbPersistentClass> getPersistentClasses() {
		return new LiveCloneIterable<JaxbPersistentClass>(this.persistentClasses.values());
	}
	
	public int getPersistentClassesSize() {
		return this.persistentClasses.size();
	}
	
	protected JaxbPersistentClass addPersistentClass(JaxbPersistentClass persistentClass) {
		if (this.persistentClasses.containsKey(persistentClass.getFullyQualifiedName())) {
			throw new IllegalArgumentException("Class with that name already exists."); //$NON-NLS-1$
		}
		this.persistentClasses.put(persistentClass.getFullyQualifiedName(), persistentClass);
		fireItemAdded(PERSISTENT_CLASSES_COLLECTION, persistentClass);
		return persistentClass;
	}
	
	protected void removePersistentClass(JaxbPersistentClass persistentClass) {
		this.removePersistentClass(persistentClass.getFullyQualifiedName());
	}
	
	protected void removePersistentClass(String persistentClassName) {
		if (! this.persistentClasses.containsKey(persistentClassName)) {
			throw new IllegalArgumentException("No class with that name exists."); //$NON-NLS-1$
		}
		JaxbPersistentClass removedPersistentClass = this.persistentClasses.remove(persistentClassName);
		fireItemRemoved(PERSISTENT_CLASSES_COLLECTION, removedPersistentClass);
	}
	
	protected JaxbPersistentClass buildPersistentClass(JavaResourceType resourceType) {
		return this.getFactory().buildPersistentClass(this, resourceType);
	}
}
