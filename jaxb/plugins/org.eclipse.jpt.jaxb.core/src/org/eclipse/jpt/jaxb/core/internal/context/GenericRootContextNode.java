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
import org.eclipse.jpt.utility.internal.ClassName;
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
		final Set<String> initialPersistentClasses = calculateInitialPersistentClasses();
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackages(initialPersistentClasses);
		
		final Set<String> packagesToBuild = CollectionTools.set(initialPackages);
		final Set<String> persistentClassesToBuild = CollectionTools.set(initialPersistentClasses);
		
		for (String packageToBuild : packagesToBuild) {
			this.packages.put(packageToBuild, buildPackage(packageToBuild));
		}
		
		for (String classToBuild : persistentClassesToBuild) {
			this.persistentClasses.put(classToBuild, buildPersistentClass(classToBuild));
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
		final Set<String> initialPersistentClasses = calculateInitialPersistentClasses();
		
		// determine initial set of packages
		final Set<String> initialPackages = calculateInitialPackages(initialPersistentClasses);
		
		final Set<String> packagesToBuild = CollectionTools.set(initialPackages);
		final Set<String> packagesToUpdate = CollectionTools.<String>set();
		final Set<String> persistentClassesToBuild = CollectionTools.set(initialPersistentClasses);
		final Set<String> persistentClassesToUpdate = CollectionTools.<String>set();
		
		for (String packageToBuild : packagesToBuild) {
			if (this.packages.containsKey(packageToBuild)) {
				packagesToUpdate.add(packageToBuild);
			}
			else {
				this.packages.put(packageToBuild, buildPackage(packageToBuild));
			}
		}
		
		for (String classToBuild : persistentClassesToBuild) {
			if (this.persistentClasses.containsKey(classToBuild)) {
				persistentClassesToUpdate.add(classToBuild);
			}
			else {
				this.persistentClasses.put(classToBuild, buildPersistentClass(classToBuild));
			}
		}
		
		for (String packageToUpdate : packagesToUpdate) {
			this.packages.get(packageToUpdate).update();
		}
		
		for (String classToUpdate : persistentClassesToUpdate) {
			this.persistentClasses.get(classToUpdate).update();
		}
	}
	
	/*
	 * calculate set of packages that can be determined purely by resource model and the given
	 * set of classes.
	 * This should include:
	 *  - any annotated package 
	 *  - any package containing an included class
	 */
	protected Set<String> calculateInitialPackages(final Set<String> initialClasses) {
		final Set<String> packages = CollectionTools.set(
				new TransformationIterable<JavaResourcePackage, String>(
						getJaxbProject().getAnnotatedJavaResourcePackages()) {
					@Override
					protected String transform(JavaResourcePackage o) {
						return o.getName();
					}
				});
		for (String className : initialClasses) {
			packages.add(ClassName.getPackageName(className));
		}
		return packages;
	}
	
	/*
	 * calculate set of persistent classes that can be determined purely by resource model
	 * (so far, this should be all persistentClasses with the @XmlType annotation)
	 */
	protected Set<String> calculateInitialPersistentClasses() {
		return CollectionTools.set(
				new TransformationIterable<JavaResourceType, String>(
						new FilteringIterable<JavaResourceType>(
								getJaxbProject().getJavaSourceResourceTypes()) {
							@Override
							protected boolean accept(JavaResourceType o) {
								return o.getAnnotation(JAXB.XML_TYPE) != null;
							}
						}) {
					@Override
					protected String transform(JavaResourceType o) {
						return o.getName();
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
			throw new IllegalArgumentException("Package with that name already exists.");
		}
		this.packages.put(contextPackage.getName(), contextPackage);
		fireItemAdded(PACKAGES_COLLECTION, contextPackage);
		return contextPackage;
	}
	
	protected void removePackage(JaxbPackage contextPackage) {
		if (! this.packages.containsKey(contextPackage.getName())) {
			throw new IllegalArgumentException("No package with that name exists.");
		}
		this.packages.remove(contextPackage.getName());
		fireItemRemoved(PACKAGES_COLLECTION, contextPackage);
	}
	
	protected JaxbPackage buildPackage(String packageName) {
		return this.getFactory().buildPackage(this, packageName);
	}
	
	protected boolean isEmpty(JaxbPackage jaxbPackage) {
		return jaxbPackage.isEmpty();
	}
	
	
	// ************* persistentClasses ***************
	
	public Iterable<JaxbPersistentClass> getPersistentClasses() {
		return new LiveCloneIterable<JaxbPersistentClass>(this.persistentClasses.values());
	}
	
	public int getPersistentClassesSize() {
		return this.persistentClasses.size();
	}
	
	protected JaxbPersistentClass addPersistentClass(JaxbPersistentClass persistentClass) {
		if (this.persistentClasses.containsKey(persistentClass.getName())) {
			throw new IllegalArgumentException("Class with that name already exists.");
		}
		this.persistentClasses.put(persistentClass.getName(), persistentClass);
		fireItemAdded(PERSISTENT_CLASSES_COLLECTION, persistentClass);
		return persistentClass;
	}
	
	protected void removePersistentClass(JaxbPersistentClass persistentClass) {
		if (! this.persistentClasses.containsKey(persistentClass.getName())) {
			throw new IllegalArgumentException("No class with that name exists.");
		}
		this.persistentClasses.remove(persistentClass.getName());
		fireItemRemoved(PERSISTENT_CLASSES_COLLECTION, persistentClass);
	}
	
	protected JaxbPersistentClass buildPersistentClass(String className) {
		JavaResourceType resourceType = getJaxbProject().getJavaResourceType(className);
		if (resourceType == null) {
			throw new IllegalArgumentException("No resource type exists for class named " + className);
		}
		return this.getFactory().buildPersistentClass(this, resourceType);
	}
}
